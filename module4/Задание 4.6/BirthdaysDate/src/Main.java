import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Calendar birthday = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        int year = 0;
        while (true) {
            System.out.println("Введите дату рождения в формате дд.мм.гггг");
            try {
                birthday.setTime(new SimpleDateFormat("dd.MM.yyyy").parse(new Scanner(System.in).nextLine()));
                while (birthday.compareTo(now) < 0){
                    System.out.println(year + " - " + new SimpleDateFormat("dd.MM.yyyy - EEE").format(birthday.getTime()));
                    birthday.add(Calendar.YEAR, 1);
                    year++;
                }
                break;
            } catch (ParseException e) {
                System.out.println("неверный формат даты рождения");
            }
        }
    }
}
