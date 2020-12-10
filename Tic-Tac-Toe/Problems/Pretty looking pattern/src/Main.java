import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[][] matrix = new char[4][4];

        // Scan matrix
        for (int i = 0; i < 4; i++) {
            String str = scanner.nextLine();
            for (int j = 0; j < 4; j++) {
                matrix[i][j] = str.charAt(j);
            }
        }

        // Check matrix for 2x2 pattern
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrix[i][j] == matrix[i][j + 1] &&
                    matrix[i][j + 1] == matrix[i + 1][j] &&
                    matrix[i + 1][j] == matrix[i + 1][j + 1]) {
                    System.out.println("NO");
                    return;
                }
            }
        }
        System.out.println("YES");
    }
}