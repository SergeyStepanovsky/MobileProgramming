import java.util.*;


public class SalesData {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, TreeMap<String, Integer>> salesData = new TreeMap<>();

        while (scanner.hasNext()) {
            String customer = scanner.next();
            String product = scanner.next();
            int quantity = scanner.nextInt();

            salesData.computeIfAbsent(customer, k -> new TreeMap<>());
            salesData.get(customer).merge(product, quantity, Integer::sum);
        }
        scanner.close();

        salesData.forEach((customer, products) -> {
            System.out.println(customer + ":");
            products.forEach((product, quantity) -> {
                System.out.println(product + " " + quantity);
            });
        });
    }
}
