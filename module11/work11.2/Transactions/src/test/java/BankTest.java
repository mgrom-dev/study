import junit.framework.TestCase;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class BankTest extends TestCase {
   Bank bank; //тестируемый класс банка

    @Override
    protected void setUp() {
        bank = new Bank();
        HashMap<String, Account> accounts = bank.getAccounts();
        //инициализируем счета клиентов банка
        for (int i = 0; i < 1000 ; i++){
            String numberAccount = i + ""; //номер счета (0 - 999)
            int money = new Random().nextInt(1000000); //сумма на счете (0 - 1 000 000)
            Account account = new Account(money, numberAccount); //создаем новый счет
            accounts.put(numberAccount, account); //добавляем счет в счета банк
        }
    }

    /**
     * Проверяем работоспособность метода перевода между счетами клиентов банка
     */
    public void testTransfers() {
        //совершаем 200 переводов
        long sumInit = bank.getAccounts().values().stream().mapToLong(Account::getMoney).sum();
        for (AtomicInteger i = new AtomicInteger(0); i.get() < 200; i.incrementAndGet()) {
            String fromAccountNum = new Random().nextInt(1000) + ""; //номер счета с которого совершается перевод
            String toAccountNum = new Random().nextInt(1000) + ""; //номер счета на который будет совершен перевод
            long amount = new Random().nextInt(50000) + (new Random().nextInt(100) > 95 ? 50000 : 0); //переводимая сумма (0 - 99 999)
            //создаем новый поток для перевода перевод
            new Thread(() -> {
                Thread.currentThread().setName("bankTransfer" + i.get()); //задаем имя потока, для отслеживания завершения потоков
                bank.transfer(fromAccountNum, toAccountNum, amount);
            }).start();
        }
        //ждем пока завершаться все переводы
        while(Thread.getAllStackTraces().entrySet().stream().anyMatch(m->m.getKey().getName().contains("bankTransfer"))) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long sumActual = bank.getAccounts().values().stream().mapToLong(Account::getMoney).sum();
        Assert.assertEquals(sumInit, sumActual);
    }
}
