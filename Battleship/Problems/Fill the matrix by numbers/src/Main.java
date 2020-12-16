import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int dim = scanner.nextInt();

        for (int i = 0, j = 0; i < dim; i++) {
            while (j != 0) {
                System.out.print(j + " ");
                j--;
            }
            while (j < dim - i) {
                System.out.print(j + " ");
                j++;
            }
            j = i + 1;
            System.out.print("\n");
        }
    }
}