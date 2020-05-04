public class Loader
{
    public static final String FILE_NAME = "res/data-1572M.xml"; // файл для парсинга

    public static void main(String[] args)
    {
        //Делаем замеры времени выполнения и используемой памяти
        long size = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long time = System.currentTimeMillis();

        InputStreamParser inputStreamParser = new InputStreamParser(FILE_NAME);
        inputStreamParser.setMethodOverflow(() -> {
            //new Thread(() -> {
                DBConnection.executeMultiInsert(inputStreamParser.getInsertVoters());
            //}).start();
        });
        inputStreamParser.parse();
        DBConnection.printVoterCounts();

        size = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - size;
        time = System.currentTimeMillis() - time;
        System.out.printf("Объем использованной памяти: %,d МБ\nВремя выполения программы: %,d мс", size / 1024 / 1024, time);
    }
}