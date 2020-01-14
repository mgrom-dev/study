import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

// Написать код парсинга банковской выписки (файл movementsList.csv).
// Код должен выводить сводную информацию по этой выписке:
// общий приход, общий расход, а также разбивку расходов.

public class Main
{
    public static ArrayList<HashMap<String, String>> data = new ArrayList<>();
    public static String pathToFile = "data/movementList.csv";
    public static String separator = ",";

    public static void main(String[] args)
    {
        readFileWithSeparators();
        double totalExpenses = 0, totalIncome = 0;
        for (int i = 1; i < data.size(); i++)
        {
            //получаем i элемент ArrayList и берем значение аттрибута "Расход"
            double expenses = getValue(data.get(i).get("Расход"));
            double income = getValue(data.get(i).get("Приход"));
            totalExpenses += expenses;
            totalIncome += income;
            if (expenses > 0)
            {
                System.out.println(data.get(i).get("Дата операции") + ", сумма расхода: " + expenses);
            }
        }
        System.out.printf("\nОбщая сумма прихода: %,.02f\nОбщая сумма расхода: %,.02f\n", totalIncome, totalExpenses);
    }

    /**
     * @param value - числовое значение в строке
     * @return - возвращаем double числовое значение, полученное из строки
     */
    private static double getValue(String value)
    {
        return Double.parseDouble(value.replaceAll("[^0-9,]", "").replaceAll(",", "."));
    }

    /**
     * метод чтения файла с разделителями
     */
    private static void readFileWithSeparators()
    {
        try {
            Files.lines(Paths.get(pathToFile))
                    .forEach(line -> {
                        //разбиваем строку на аттрибуты с указанным разделителем с помощью регулярного выражения
                        String[] attributes = line.split(separator + "(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))");
                        HashMap<String, String> params = new HashMap<>();
                        if (data.size() == 0){ //задаем заголовки
                            for (int i = 0; i < attributes.length; params.put(i + "", attributes[i]), i++) {}
                        } else {
                            if (attributes.length == data.get(0).size()) { //защита по количеству атрибутов
                                //добавляем все аттрибуты в HashMap
                                for (int i = 0; i < attributes.length; params.put(data.get(0).get(i + ""), attributes[i]), i++) {}
                            }
                        }
                        data.add(params);
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
