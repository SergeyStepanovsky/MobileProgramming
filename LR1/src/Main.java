import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.zip.CRC32;

public class Main {
    private static final long TARGET_HASH = 0x0BA02B6E1L;
    private static List<String> words = new ArrayList<>();
    private static final AtomicReference<String> foundPassword = new AtomicReference<>();//атомарная ссылка на объект(потокобезопасная вещь, чтобы не возникало никаких непонятных перезаписей между потоками)

    public static void main(String[] args) throws IOException, InterruptedException {

        File f = new File("10k-most-common.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line.trim()); //убираем пробелы из строк
            }
        }

        //Этап с многопоточностью
        int numberOfThreads = 5;
        int wordsPerThread = words.size() / numberOfThreads; // количество слов которое каждый
        //поток будет обрабатывать

        List<Thread> threads = IntStream.range(0, numberOfThreads)
                .mapToObj(i -> {
                    int startIndex = i * wordsPerThread;
                    int endIndex = (i == numberOfThreads - 1) ? words.size() : startIndex + wordsPerThread;
                    return new PasswordCracker(words.subList(startIndex, endIndex));
                })// перегнали все числа объект в PasswoedCracker
                .map(Thread::new) // передали крекер в конструктор Thread
                .peek(Thread::start)
                .collect(Collectors.toList());

        for (Thread thread : threads) {
            thread.join(); // заблокировали главный поток
        }

        if (foundPassword.get() != null) {
            System.out.println("Password: " + foundPassword.get());
        } else {
            System.out.println("Password not found.");
        }
    }

    static class PasswordCracker implements Runnable {
        private List<String> wordList;// сюда добавляются подсписки основного списка слов

        PasswordCracker(List<String> wordList) {
            this.wordList = wordList;
        }

        @Override
        public void run() {
            CRC32 crc32 = new CRC32();
            for (String word : wordList) {
                for (int i = 1; i <= 9999; i++) {
                    String possiblePassword = word + i;
                    crc32.reset();
                    crc32.update(possiblePassword.getBytes());
                    if (foundPassword.get() != null) { //условие чтобы остальные потоки закрылись в случае успеха одного из них
                        return;
                    }
                    if (crc32.getValue() == TARGET_HASH) {
                        System.out.println("Found password: " + possiblePassword);
                        foundPassword.set(possiblePassword);
                        return;
                    }
                }
            }
        }
    }
}
