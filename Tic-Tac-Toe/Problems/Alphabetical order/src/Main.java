import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Init String objects
        String str1 = scanner.next();
        String str2 = "";

        // Use while loop to compare strings in input
        while (scanner.hasNext()) {
            str2 = scanner.next();
            if (str1.compareTo(str2) > 0) {
                System.out.println(false);
                return;
            }
            str1 = str2;
        }
        System.out.println(true);
    }
}