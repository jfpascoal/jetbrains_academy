import java.util.*;

class Main {
    public static void main(String[] args) {
        // Init scanner
        Scanner scanner = new Scanner(System.in);

        // Get array size
        int arraySize = scanner.nextInt();

        // Init array
        int[] array = new int[arraySize];

        // Use loop to get array
        for (int i = 0; i < arraySize; i++) {
            array[i] = scanner.nextInt();
        }

        // Get check numbers
        int n1 = scanner.nextInt(); 
        int n2 = scanner.nextInt();

        // Loop through array and print result
        for (int i = 0; i < arraySize - 1; i++) {
            if (array[i] == n1  && array[i + 1] == n2 ||
                    array[i] == n2 && array[i + 1] == n1) {
                System.out.println(true);
                break;
            } else if (i == arraySize - 2) {
                System.out.println(false);
            }
        }
    }
}
