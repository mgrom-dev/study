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

        //Printing results
        System.out.println("Voting station work times: ");
        parser.getVoteStationWorkTimes().forEach((votingStation, workTime) -> System.out.println("\t" + votingStation + " - " + workTime));

        System.out.println("Duplicated voters: ");
        parser.getVoterCounts().entrySet().stream().filter(entry -> entry.getValue() > 1).
                forEach(entry -> System.out.println("\t" + entry.getKey() + " - " + entry.getValue()));

        size = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - size;
        time = System.currentTimeMillis() - time;
        System.out.printf("Объем использованной памяти: %,d МБ\nВремя выполения программы: %,d мс", size / 1024 / 1024, time);
    }
}