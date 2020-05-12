public class Main
{
    private static final String SYMBOLS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static void main(String[] args)
    {
        System.setProperty("HADOOP_USER_NAME", "root");
        FileAccess fs = new FileAccess("ed60d48b3593");
        fs.delete("/test/file.txt");
        fs.create("/test/file.txt");
        fs.create("/test/1.txt");
        fs.create("/test/2/");
        for(int i = 0; i < 100; i++) {
            fs.append("/test/file.txt", getRandomWord() + " ");
        }
        System.out.println(fs.read("/test/file.txt"));
        System.out.println(fs.list("/test/"));
    }

    private static String getRandomWord()
    {
        StringBuilder builder = new StringBuilder();
        int length = 2 + (int) Math.round(10 * Math.random());
        int symbolsCount = SYMBOLS.length();
        for(int i = 0; i < length; i++) {
            builder.append(SYMBOLS.charAt((int) (symbolsCount * Math.random())));
        }
        return builder.toString();
    }
}
