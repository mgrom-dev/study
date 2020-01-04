import java.text.DateFormat;
import java.text.ParsePosition;
import java.util.*;
import java.text.SimpleDateFormat;

public class Main {
    public static void main(String[] args) {
        Calendar birthday = new GregorianCalendar();
        Date inputDate;
        boolean February29;
        while (true) { //читаем строку, пока не будет введены данные доступные для приведения в формат даты
            System.out.println("Input your birthday (dd.MM.yyyy) : ");
            inputDate = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).
                    parse(new Scanner(System.in).nextLine(), new ParsePosition(0));
            if (inputDate == null) {
                System.out.println("Invalid date");
                continue;
            }
            break;
        }
        birthday.setTime(inputDate);
        February29 = is29February(birthday); //если день рождения в високосном году
        Calendar now = new GregorianCalendar();
        for (int i = 0; !birthday.after(now); i++) {
            System.out.println(i + " - " + new SimpleDateFormat("dd.MM.yyyy - EEE", Locale.US).
                    format(birthday.getTime()));
            birthday.add(Calendar.YEAR, 1);
            if(February29 && birthday.get(Calendar.YEAR) % 4 == 0) { //прибавляем день в високосный год, если день рождения выпал на 29 февраля
                birthday.add(Calendar.DATE, 1);
            }
        }
    }

    public static boolean is29February(Calendar calendar) { //если дата рождения в високосном году
        return calendar.get(Calendar.MONTH) == Calendar.FEBRUARY && calendar.get(Calendar.DATE) == 29;
    }
}