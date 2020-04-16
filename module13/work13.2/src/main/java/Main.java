import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static final int USERS = 20; //количество зарегистрированных пользователей
    public static final int SLEEP = 1; // задержка в цикле в сек.
    private static boolean isExit = false; //для выхода из цикла

    public static void main(String[] args) {
        //создаем клиента редис
        try (Jedis jedis = new Jedis("redis://localhost:6379")){
            //Берем 3 самые дешевые пездки и 3 самые дорогие
            jedis.zadd("Travel", 5600, "Нижний Новгород");
            jedis.zadd("Travel", 16000, "Владивосток");
            jedis.zadd("Travel", 5600, "Пермь");
            jedis.zadd("Travel", 6800, "Сочи");
            jedis.zadd("Travel", 10200, "Москва");
            jedis.zadd("Travel", 4300, "Санкт-Петербург");
            jedis.zadd("Travel", 8000, "Иркутск");
            jedis.zadd("Travel", 19400, "Воркута");
            jedis.zadd("Travel", 9100, "Анапа");
            jedis.zadd("Travel", 9500, "Казань");
            System.out.println("Самые дешевые путешествия: " + jedis.zpopmin("Travel", 3));
            System.out.println("Самые дорогие путешествия: " + jedis.zpopmax("Travel", 3));

            //запускаем паралельный поток, для возможности выхода из программы
            new Thread(() -> {
                System.out.println("Для выхода из программы нажмите ENTER");
                new Scanner(System.in).nextLine();
                isExit = true;
            }).start();

            //создаем пользователей сайта (на главной странице отображается самый первый по времени)
            String key = "Users"; //ключ treeSet
            String name = "Пользователь "; //префикс пользователей
            for (int i = 1; i <= USERS; i++) {
                jedis.zadd(key, System.currentTimeMillis(), name + i);
                Thread.sleep(SLEEP * 50);
            }

            Random random = new Random();
            while(!isExit){
                //С вероятностью 10% пользователь оплачивает платную услугу (повышаем очки)
                if (random.nextInt(10) == 9) {
                    //пропускаем если пользователь уже в приоритете
                    String randUser;
                    do {
                        randUser = name + (1 + random.nextInt(USERS));
                    } while(jedis.zrank(key, randUser) == 0);

                    //ставим пользователя первым
                    jedis.zadd(key, 1, randUser);
                    System.out.println(randUser + " оплатил платную услугу");
                }

                //извлекаем первого пользователя по времени
                Tuple user = jedis.zpopmin(key);
                System.out.println("На главной странице показываем " + user.getElement());

                //добавляем его обратно с текущим временем
                jedis.zadd(key, System.currentTimeMillis(), user.getElement());
                Thread.sleep(SLEEP * 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
