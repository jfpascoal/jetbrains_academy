import java.io.InputStream;

class Main {
    public static void main(String[] args) throws Exception {
        InputStream inputStream = System.in;
        int c = inputStream.read();
        String out = "";
        while (c != -1) {
            out += c;
            c = inputStream.read();
        }
        System.out.println(out);
    }
}