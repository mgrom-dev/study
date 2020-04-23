import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Loader
{
    private static final String PATH = "res/numbers.txt"; //путь к файлу для сохранения номеров
    private static final String[] LETTERS = {"У", "К", "Е", "Н", "Х", "В", "А", "Р", "О", "С", "М", "Т"}; //допустимые буквы в номере автомобиля
    private static AtomicInteger THREADS_COUNT = new AtomicInteger(4); //Количество потоков для генерации номеров

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();

        //распределяем буквы по потокам
        String[] lettersForThread = new String[THREADS_COUNT.get()];
        for (int i = 0; i < LETTERS.length; i++){
            int index = i % THREADS_COUNT.get();
            if (lettersForThread[index] == null) {
                lettersForThread[index] = "";
            }
            lettersForThread[index] += LETTERS[i];
        }

        //запускаем отдельные потоки
        for (String letters : lettersForThread){
            new GenerateNumberThread(letters, PATH).run(THREADS_COUNT::getAndDecrement);
        }

        //ждем заверешения всех потоков
        while(!THREADS_COUNT.compareAndSet(0,0)) {
            Thread.sleep(10);
        }
        System.out.println((System.currentTimeMillis() - start) + " ms");
    }
}
