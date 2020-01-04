package res;

/**
 * Индивидуальный предприниматель - клиент банка
 */
public class IndividualPerson extends Client
{
    IndividualPerson(String numberAccount)
    {
        setNumberAccount(numberAccount);
    }

    /**
     * Пополнение с комиссией 1%, если сумма меньше 1000 рублей, и 0,5%, если сумма больше либо равна 1000 рублей
     */
    @Override
    public boolean putCash(double money) {
        if (money > 0) {
            money = money >= 1000 ? money * 0.995 : money * 0.99;
        }
        return super.putCash(money);
    }
}
