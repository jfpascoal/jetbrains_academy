import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        List<Character>  chars = new ArrayList<>();
        int newChar = reader.read();
        while (newChar != -1) {
            chars.add(0, (char) newChar);
            newChar = reader.read();
        }
        reader.close();
        for (char c : chars) {
            System.out.print(c);
        }
    }
}