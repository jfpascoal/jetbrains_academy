import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Get array dimensions
        int[] arrayDims = {scanner.nextInt(), scanner.nextInt()};

        // Scan array
        int[][] array = new int[arrayDims[0]][arrayDims[1]];
        for (int i = 0; i < arrayDims[0]; i++) {
            for (int j = 0; j < arrayDims[1]; j++) {
                array[i][j] = scanner.nextInt();
            }
        }

        // Get swap indexes
        int[] swap = {scanner.nextInt(), scanner.nextInt()};

        // Print array with swapped columns
        for (int i = 0; i < arrayDims[0]; i++) {
            for (int j = 0; j < arrayDims[1]; j++) {
                if (j == swap[0]) {
                    System.out.print(array[i][swap[1]]);
                } else if (j == swap[1]) {
                    System.out.print(array[i][swap[0]]);
                } else {
                    System.out.print(array[i][j]);
                }
                if (j == arrayDims[1] - 1) {
                    System.out.print("\n");
                } else {
                    System.out.print(" ");
                }
            }
        }
    }
}