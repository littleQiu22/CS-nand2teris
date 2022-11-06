import java.io.File;

public class Test {
    public static void main(String[] args) throws Exception {
        File file=new File("token.txt");
        System.out.println(file.exists());
    }
}
