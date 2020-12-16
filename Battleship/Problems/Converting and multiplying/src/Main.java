import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.next();
            try {
                int result = Integer.parseInt(input) * 10;
                if (result != 0) {
                    System.out.println(result);
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.printf("Invalid user input: %s\n", input);
            }
        }
    }
}