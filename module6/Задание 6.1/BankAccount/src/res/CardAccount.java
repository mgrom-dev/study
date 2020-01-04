package res;

//карточный счет
public class CardAccount extends BankAccount
{
    @Override
    public boolean withdrawMoney(double money)
    {
        return super.withdrawMoney(money * 1.01);
    }
}