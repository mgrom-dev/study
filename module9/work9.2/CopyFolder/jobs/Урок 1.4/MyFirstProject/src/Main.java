import java.util.Calendar;

public class Main {
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        System.out.println("Hour is "+calendar.get(11)); //получаем текущий час в 24 часовом формате
        System.out.println("Hour is "+calendar.get(10)); //получаем текущий час в 12 часовом формате
    }
}
