import res.*;

public class Main
{

// Создайте класс, который будет представлять собой расчётный счёт в банке.
// на этот расчётный счёт деньги можно положить, с него их можно снять, и ещё можно посмотреть,
// сколько денег на счёте. Создайте два класса-наследника - депозитарный расчётный счёт,
// с которого нельзя снимать деньги в течение месяца после последнего внесения, и карточный счёт,
// при снятии денег с которого будет взиматься комиссия 1%.

    public static void main(String[] args) {
        CardAccount account = new CardAccount();
        account.putMoney(100);
        System.out.println("Сумма на карточном счете: " + account.getMoney() + "\nСнимаем 99 руб. + комиссия 1%");
        account.withdrawMoney(99);
        System.out.println("Остаток на карточном счете: " + account.getMoney());

        DepositAccount depositAccount = new DepositAccount();
        System.out.println("\nСоздаем депозитный счет и ложим 1000 руб.");
        depositAccount.putMoney(1000.);
        System.out.println("Сумма на депозитном счете: " + depositAccount.getMoney() + "\nПытаемся снять 100 руб.");
        depositAccount.withdrawMoney(100.0);
        System.out.println("Сумма на депозитном счете: " + depositAccount.getMoney() + "\nСнять можно только через месяц после последнего внесения.");
    }
}
