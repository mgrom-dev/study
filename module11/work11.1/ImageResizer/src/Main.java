import java.io.File;

public class Main
{
    public static final int size = 300;
    public static final String srcFolder = "res";
    public static final String dstFolder = "dst";

    public static void main(String[] args)
    {
        File srcDir = new File(srcFolder);
        File[] files = srcDir.listFiles();

        new Thread(new ResizeImg(size, dstFolder, files)).start();
    }
}
