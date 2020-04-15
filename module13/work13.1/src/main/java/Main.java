import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.embedded.RedisServer;

import java.io.IOException;
import java.util.stream.Collectors;

public class Main {
    private static final String PROC_NAME = "redis-server-2.8.19.exe"; //имя процесса с сервером redis

    public static void main(String[] args) throws IOException {
        //инициализируем сервер
        RedisServer server = new RedisServer();
        //ищем уже запущенный процесс с сервером
        ProcessHandle proc = getProcByName(PROC_NAME);
        if (proc == null) { //если процесс не найден, то стартуем сервер
            server.start();
            proc = getProcByName(PROC_NAME);
        }

        //создаем клиента редис
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
        try (Jedis jedis = pool.getResource()) {
            //Используем комманды SET, GET, KEYS, SADD, SREM, SMEMBERS для списка дел (Задание 1)
            jedis.set("дело №1", "купить кефир"); //задаем новый сет с помощью команды SET
            System.out.println(jedis.get("дело №1")); //получаем значение ключа, с помощью команды GET (дело №1 = купить кефир)
            jedis.set("дело №1", "купить молока"); //повторно добавляем дело №1
            System.out.println(jedis.get("дело №1")); //дело №1 изменилось = купить молока
            jedis.set("дело №2", "заправить машину");
            jedis.set("дело №3", "покормить кота");
            System.out.println(jedis.keys("*").stream().collect(Collectors.joining(", "))); //выводим список всех ключей с помощью команды KEYS *
            jedis.keys("*").forEach(k -> jedis.sadd("to-do", jedis.get(k))); //добавляем по ключу 'to-do' массив из всех дел, командой SADD
            System.out.println(jedis.smembers("to-do")); //получаем список дел, командой SMEMBERS
            jedis.smembers("to-do").forEach(k -> jedis.srem("to-do", k)); //удаляем дела по одному командой SREM
            System.out.println("Количество дел: " + jedis.smembers("to-do").size());

            //Используем команды HSET, HGET для хранения выполненных заданий студента (Задание 2)
            jedis.hset("Иванов И.И.", "Web-Разработчик", "1"); //Используя команду HSET указываем количество выполненых д.з. по курсу для студента
            jedis.hset("Иванов И.И.", "Data Science", "4");
            System.out.println(jedis.hget("Иванов И.И.", "Data Science")); //Получаем количество выполненных д.з. по курсу, командой HGET
            System.out.println(jedis.hgetAll("Иванов И.И.")); //Получаем все содержимое HashMap, командой HGETALL
            jedis.hincrBy("Иванов И.И.", "Data Science", 1); //увеличиваем количество выполненных д.з. на 1, командой HINCRBY
            System.out.println(Integer.parseInt(jedis.hget("Иванов И.И.", "Data Science")) == 5); //проверяем увеличилось ли количество выполненных д.з.
        } catch (Exception e) {
            e.printStackTrace();
        }

        //закрываем клиента, сервер и выходим из программы
        pool.close();
        if (proc != null) {
            proc.destroy();
        } else {
            server.stop();
        }
        System.exit(0);
    }

    /**
     * @return возвращает первый найденный процесс с именем name, либо null
     */
    private static ProcessHandle getProcByName(String name){
        return ProcessHandle.allProcesses().
                filter(proc -> proc.info().command().orElse("").matches(".+\\\\" + name)).
                findFirst().orElse(null);
    }
}
