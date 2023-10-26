public class TextRoles {

    public static String printTextPerRole(String[] roles, String[] textLines) {
        StringBuilder result = new StringBuilder();
        for (String role : roles) {
            result.append(role).append(":\n");
            for (int i = 0; i < textLines.length; i++) {
                if (textLines[i].startsWith(role + ":")) {
                    String line = textLines[i].substring(role.length() + 2); // +2 для пропуска ": "
                    result.append((i + 1)).append(")").append(" ").append(line).append("\n");
                }
            }
            result.append("\n"); // Добавляем пустую строку между ролями
        }
        return result.toString().trim(); // Убираем лишние пробелы и переносы строк в конце
    }

    public static void main(String[] args) {
        String[] roles = {
                "Городничий",
                "Аммос Федорович",
                "Артемий Филиппович",
                "Лука Лукич"
        };

        String[] textLines = {
                "Городничий: Я пригласил вас, господа, с тем, чтобы сообщить вам пренеприятное известие: к нам едет ревизор.",
                "Аммос Федорович: Как ревизор?",
                "Артемий Филиппович: Как ревизор?",
                // ... (добавьте остальные строки)
        };

        System.out.println(printTextPerRole(roles, textLines));
    }
}
