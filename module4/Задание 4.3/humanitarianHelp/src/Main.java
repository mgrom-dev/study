import java.util.Scanner;

public class Main {
    private static int количествоЯщиков = 500;
    private static int вместимостьКонтейнера = 27;
    private static int вместимостьГрузовика = 12;

    public static void main(String[] args) {
        while(true) {
            try {
                System.out.println("Введите количество ящиков, которое нужно распределить: ");
                количествоЯщиков = new Scanner(System.in).nextInt();
                break;
            } catch (Exception e) {
                System.out.println("Неверное введено количество ящиков.");
            }
        }
        while(true) {
            try {
                System.out.println("Введите вместимость количества ящиков в контейнер: ");
                вместимостьКонтейнера = new Scanner(System.in).nextInt();
                if (вместимостьКонтейнера <= 0) throw new Exception();
                break;
            } catch (Exception e) {
                System.out.println("Неверная вместимость контейнера.");
            }
        }
        while(true) {
            try {
                System.out.println("Введите количество контейнеров, которое входит в грузовик: ");
                вместимостьГрузовика = new Scanner(System.in).nextInt();
                if (вместимостьГрузовика <= 0) throw new Exception();
                break;
            } catch (Exception e) {
                System.out.println("Неверная вместимость грузовика.");
            }
        }
        for (int ящик = 0, грузовик = 0, контейнер = 0; ящик < количествоЯщиков;) {
            if (ящик % (вместимостьГрузовика * вместимостьКонтейнера) == 0) {
                грузовик++;
                System.out.println("Грузовик " + грузовик);
            }
            if (ящик % вместимостьКонтейнера == 0) {
                контейнер++;
                System.out.println("\tКонтейнер " + контейнер);
            }
            ящик++;
            System.out.println("\t\tЯщик " + ящик);
        }
    }
}
