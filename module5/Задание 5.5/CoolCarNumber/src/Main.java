import java.util.*;

public class Main {
    public static ArrayList<String> carNumber;
    public static HashSet<String> carNumberHash = new HashSet<>();
    public static TreeSet<String> carNumberTree = new TreeSet<>();
    public static final String seriesRus = "АВЕКМНОРСТУХ";
    public static final String seriesEn =  "ABEKMHOPCTYX";

// Написать генератор блатных автомобильных номеров и реализовать поиск элементов в списке прямым перебором,
// бинарным поиском, поиском с помощью HashSet и с помощью TreeSet. Измерить и сравнить длительность 4-х видов поиска
// и написать результат в качестве решения домашнего задания.

    public static void main(String[] args) {
        generateCoolNumber();
        Scanner scanner = new Scanner(System.in);
        System.out.println("In base " + carNumber.size() + " car numbers. Input car number for search or send command EXIT");
        String command;
        while(true)
        {
            command = toSingleFormat(scanner.nextLine().trim());
            if (command.matches("^ЕХIТ$"))
            {
                break;
            }
            getDirectSearch(command);
            getBinarySearch(command);
            getHashSearch(command);
            getTreeSearch(command);
        }
    }

    //генерируем автомобильные номера
    public static void generateCoolNumber()
    {
        System.out.print("Generate cool car number... ");
        //время начала генерации автомобильных номеров
        long start = System.currentTimeMillis();
        carNumber = new ArrayList<>();
        String region;
        //перебор регионов от 01 до 99
        for (int indexRegion = 1; indexRegion <= 99; indexRegion++)
        {
            region = indexRegion >= 10 ? String.valueOf(indexRegion) : "0" + indexRegion;
            //генерируем номера где все буквы серии одинаковые
            for (char letter : seriesRus.toCharArray())
            {
                //перебираем номера машины от 001 до 999
                for (int indexNumber = 1; indexNumber <= 999; indexNumber++)
                {
                    String number = indexNumber >= 100 ? String.valueOf(indexNumber) : indexNumber >= 10 ? "0" + indexNumber : "00" + indexNumber;
                    carNumber.add(letter + number + letter + letter + region);
                }
            }
            //генерируем номера где одинаковые цифры, но разная серия
            for (int indexNumber = 111; indexNumber <= 999; indexNumber += 111)
            {
                //берем все возможные комбинации 12 возможных букв ^ 3 буквы в номере (12-ричная с.с.)
                for (int indexNumberSystem = 1; indexNumberSystem < 1728; indexNumberSystem++)
                {
                    //пропускаем номера где в серии все буквы одинаковые, так как они уже есть в базе
                    if (indexNumberSystem % 157 == 0)
                    {
                        continue;
                    }
                    int index1 = indexNumberSystem % 12; //1 буква серии
                    int index2 = (indexNumberSystem / 12) % 12; //2 буква серии
                    int index3 = (indexNumberSystem / 144) % 12; //3 буква серии
                    carNumber.add("" + seriesRus.toCharArray()[index1] + indexNumber + seriesRus.toCharArray()[index2] + seriesRus.toCharArray()[index3] + region);
                }
            }
        }
        //делаем замеры времени на генерацию номеров, сбор в коллекции и сортировка
        System.out.println("Generate takes " + (System.currentTimeMillis() - start) + "ms");
        start = System.currentTimeMillis();
        Collections.sort(carNumber);
        System.out.println("Sort numbers for binary search takes " + (System.currentTimeMillis() - start) + "ms");
        start = System.currentTimeMillis();
        carNumberHash.addAll(carNumber);
        System.out.println("Adding to HashSet takes " + (System.currentTimeMillis() - start) + "ms");
        start = System.currentTimeMillis();
        carNumberTree.addAll(carNumber);
        System.out.println("Adding to TreeSet takes " + (System.currentTimeMillis() - start) + "ms");
    }

    //поиск методом бинарного поиска
    public static void getBinarySearch(String number)
    {
        System.out.print("Searching number by binary search. ");
        long start = System.currentTimeMillis();
        if (Collections.binarySearch(carNumber, number) >= 0)
        {
            System.out.print("Car number found for: ");
        }
        else
        {
            System.out.print("Car number not found. The search took: ");
        }
        long duration = System.currentTimeMillis() - start;
        System.out.println(duration + "ms");
    }

    //поиском в сортированном дереве
    public static void getTreeSearch(String number)
    {
        System.out.print("Searching number by TreeSet search. ");
        long start = System.currentTimeMillis();
        if (carNumberTree.contains(number))
        {
            System.out.print("Car number found for: ");
        }
        else
        {
            System.out.print("Car number not found. The search took: ");
        }
        long duration = System.currentTimeMillis() - start;
        System.out.println(duration + "ms");
    }

    //поиск в хешерованом списке
    public static void getHashSearch(String number)
    {
        System.out.print("Searching number by HashSet search. ");
        long start = System.currentTimeMillis();
        if (carNumberHash.contains(number))
        {
            System.out.print("Car number found for: ");
        }
        else
        {
            System.out.print("Car number not found. The search took: ");
        }
        long duration = System.currentTimeMillis() - start;
        System.out.println(duration + "ms");
    }

    //прямой поиск
    public static void getDirectSearch(String number)
    {
        System.out.print("Searching number by direct search. ");
        long start = System.currentTimeMillis();
        if (carNumber.contains(number))
        {
            System.out.print("Car number found for: ");
        }
        else
            {
                System.out.print("Car number not found. The search took: ");
            }
        long duration = System.currentTimeMillis() - start;
        System.out.println(duration + "ms");
    }

    //переводим строку в верхний регистр и к единому языку для схожих по написанию букв русского и английской алфавита
    public static String toSingleFormat(String str)
    {
        StringBuilder resultString = new StringBuilder();
        int indexEnglishLetter;
        for (char letter : str.toUpperCase().toCharArray())
        {
            indexEnglishLetter = seriesEn.indexOf(letter);
            if (indexEnglishLetter != -1)
            {
                resultString.append(seriesRus, indexEnglishLetter, indexEnglishLetter + 1);
            }
            else
                {
                    resultString.append(letter);
                }
        }
        return resultString.toString();
    }
}
