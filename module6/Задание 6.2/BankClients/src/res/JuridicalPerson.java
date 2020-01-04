package res;

/**
 * Юридических лицо - клиент банка
 */
public class JuridicalPerson extends Client
{
    JuridicalPerson(String numberAccount)
    {
        setNumberAccount(numberAccount);
    }

    /**
     * комиссия за снятие денежных средств 1%
     */
    @Override
    public boolean withdrawCash(double money) {
        return super.withdrawCash(money * 1.01);
    }
}
