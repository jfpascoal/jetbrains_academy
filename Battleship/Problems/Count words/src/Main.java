import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean space = true;
        int counter = 0;
        int character = reader.read();
        while (character != -1) {
            if ((char) character != ' ' && space) {
                counter += 1;
                space = false;
            } else if ((char) character == ' ' && !space) {
                space = true;
            }
            character = reader.read();
        }
        reader.close();
        System.out.println(counter);
    }
}