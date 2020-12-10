import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n1 = scanner.nextLong();
        String op = scanner.next();
        long n2 = scanner.nextLong();
        long result;

        switch (op) {
            case "+":
                result = n1 + n2;
                break;
            case "-":
                result = n1 - n2;
                break;
            case "/":
                if (n2 == 0) {
                    System.out.println("Division by 0!");
                    return;
                } else {
                    result = n1 / n2;
                    break;
                }
            case "*":
                result = n1 * n2;
                break;
            default:
                System.out.println("Unknown operator");
                return;
        }
        System.out.println(result);
    }
}