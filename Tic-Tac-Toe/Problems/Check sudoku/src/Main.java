import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // get table N
        int n = scanner.nextInt();

        // Scan table
        // Get arrays of rows, columns, and squares that can be validated
        int[][] squares = new int[n * n][n * n];
        int[][] rows = new int[n * n][n * n];
        int[][] cols = new int[n * n][n * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    for (int l = 0; l < n; l++) {
                        int number = scanner.nextInt();
                        rows[j + i * n][l + k * n] = number;
                        cols[l + k * n][j + i * n] = number;
                        squares[k + i * n][l + j * n] = number;
                    }
                }
            }
        }

        // Validate arrays
        if (isValid(squares, n) && isValid(rows, n) && isValid(cols, n)) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }

    private static boolean isValid(int[][] element, int n) {
        for (int[] array : element) {
            if (n == 1 && array[0] != 1) {
                return false;
            } else {
                for (int i = 0; i < n * n - 1; i++) {
                    if (array[i] < 1 || array[i] > n * n) {
                        return false;
                    } else if (Arrays.binarySearch(array, i + 1, n * n, array[i]) >= 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}