import com.skillbox.airport.Airport;
import com.skillbox.airport.Flight;
import com.skillbox.airport.Terminal;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Main
{
    private static String staffFile = "data/staff.txt";
    private static String dateFormat = "dd.MM.yyyy";

// TODO: (Complete) В проекте с сотрудниками с помощью Stream API рассчитать максимальную зарплату тех, кто пришёл в 2017 году.
// TODO: (Complete) Используя библиотеку airport.jar, распечатать время вылета и модели самолётов, вылетающие в ближайшие 2 часа.

    public static void main(String[] args)
    {
        ArrayList<Employee> staff = loadStaffFromFile();
        //I. C помощью Stream API рассчитать максимальную зарплату тех, кто пришёл в 2017 году.
        int year = 2017;
        //получаем список сотрудников 2017 года
        List<Employee> filtered = staff.stream().filter(m->m.getWorkStart().after(Date.valueOf(year + "-1-1")) && m.getWorkStart().before(Date.valueOf(year + "-12-31"))).collect(Collectors.toList());
        //общая сумма заработка
        int sum = filtered.stream().map(Employee::getSalary).reduce((m1, m2) -> m1 + m2).get();
        //максимальная сумма заработка у сотрудника
        int max = filtered.stream().map(Employee::getSalary).max(Integer::compareTo).get();
        System.out.println("Общая сумма заработка за " + year +  " год " + sum + " руб.\nМаксимальная сумма заработка у сотрудника за " + year + " год " + max + " руб.");

        //II. Используя библиотеку airport.jar, распечатать время вылета и модели самолётов, вылетающие в ближайшие 2 часа.
        Airport airport = Airport.getInstance();
        ArrayList<Flight> flights = new ArrayList<>();
        //получаем список всех полетов в аэропорту по всем терминалам
        airport.getTerminals().forEach(t->flights.addAll(t.getFlights()));
        System.out.println("\nВсего в аэропорту зарегистрировано " + flights.size() + " рейсов. Список самолетов, которые вылетают в ближайшие 2 часа:");
        //фильтруем по типу полета и времени
        flights.stream().filter(f->{
            if (f.getType() != Flight.Type.DEPARTURE) return false;
            long dateDifferenceHour = (f.getDate().getTime() - System.currentTimeMillis()) / 1000;
            if (dateDifferenceHour > 2 * 60 * 60 || dateDifferenceHour < 0) return false;
            return true;
        }).sorted(Comparator.comparing(Flight::getDate)).forEach(System.out::println); //сортируем и печатаем
    }

    private static ArrayList<Employee> loadStaffFromFile()
    {
        ArrayList<Employee> staff = new ArrayList<>();
        try
        {
            List<String> lines = Files.readAllLines(Paths.get(staffFile));
            for(String line : lines)
            {
                String[] fragments = line.split("\t");
                if(fragments.length != 3) {
                    System.out.println("Wrong line: " + line);
                    continue;
                }
                staff.add(new Employee(
                    fragments[0],
                    Integer.parseInt(fragments[1]),
                    (new SimpleDateFormat(dateFormat)).parse(fragments[2])
                ));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return staff;
    }
}