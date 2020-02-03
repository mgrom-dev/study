import java.util.HashMap;
import java.util.Random;

public class Bank
{
    private HashMap<String, Account> accounts;
    private final Random random = new Random();

    {
        accounts = new HashMap<>();
    }

    public HashMap<String, Account> getAccounts() {
        return accounts;
    }

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
        throws InterruptedException
    {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    public void transfer(String fromAccountNum, String toAccountNum, long amount)
    {
        Account from = accounts.get(fromAccountNum); //счет с которого совершается перевод
        Account to = accounts.get(toAccountNum); //счет на который совершается перевод
        boolean isTrust = !from.isBlock() && !to.isBlock() && from.getMoney() >= amount; //перевод разрешен
        if (amount > 50000 && isTrust) {
            try {
                isTrust = isFraud(fromAccountNum, toAccountNum, amount); //вывзываем проверку перевода
                System.out.println("Перевод " + (isTrust ? "одобрен" : "запрещен"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //совершаем перевод
        if (isTrust) {
            from.setMoney(from.getMoney() - amount);
            to.setMoney(to.getMoney() + amount);
            System.out.printf("Совершен перевод на сумму: %,d руб. со счета № %s (%,d руб.) на счет № %s (%,d руб.)\n", amount, fromAccountNum, getBalance(fromAccountNum), toAccountNum, getBalance(toAccountNum));
        } else { //блокируем счета
            System.out.printf("Подозрительная операция перевода. Счета № %s (%,d руб.) и № %s (%,d руб.) заблокированы.\n", fromAccountNum, getBalance(fromAccountNum), toAccountNum, getBalance(toAccountNum));
            from.setBlock(true);
            to.setBlock(true);
        }
    }

    public long getBalance(String accountNum)
    {
        return accounts.get(accountNum).getMoney();
    }
}
