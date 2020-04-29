public class Loader
{
    public static final String FILE_NAME = "res/data-18M.xml"; // файл для парсинга

    public static void main(String[] args)
    {
        //Делаем замеры времени выполнения и используемой памяти
        long size = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long time = System.currentTimeMillis();

        //парсим файл с помощью SAXParser’а
        SAXParser parser = new SAXParser(FILE_NAME);
        parser.parse(() -> DBConnection.executeMultiInsert(parser.getVoters()));
        DBConnection.printVoterCounts();

        size = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - size;
        time = System.currentTimeMillis() - time;
        System.out.printf("Объем использованной памяти: %,d МБ\nВремя выполения программы: %,d мс", size / 1024 / 1024, time);
    }
}