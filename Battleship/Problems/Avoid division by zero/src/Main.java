import java.util.Scanner;

class FixingArithmeticException {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        int d = scanner.nextInt();

        if (b + c == 0 || d == 0) {
            System.out.println("Division by zero!");
        } else {
            System.out.println(a / ((b + c) / d));
        }
    }
}