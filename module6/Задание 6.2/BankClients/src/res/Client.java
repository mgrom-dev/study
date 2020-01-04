package res;

/**
 * Абстрактный класс клиенты банка
 */
public abstract class Client implements Comparable<Client>
{
    private String numberAccount; //номер расчетного счета 20 цифр
    private double cash;

    public String getNumberAccount()
    {
        return numberAccount;
    }

    public boolean setNumberAccount(String numberAccount)
    {
        if (numberAccount.length() == 20)
        {
            this.numberAccount = numberAccount;
            return true;
        }
        return false;
    }

    public double getCash()
    {
        return cash;
    }

    public boolean withdrawCash(double money)
    {
        if (money > 0 && cash - money > 0)
        {
           cash -= money;
           return true;
        }
        return false;
    }

    public boolean putCash(double money)
    {
        if (money > 0)
        {
            cash += money;
            return true;
        }
        return false;
    }

    public int compareTo(Client c)
    {
        return numberAccount.compareTo(c.numberAccount);
    }

    @Override
    public String toString() {
        return "numberAccount='" + numberAccount + '\'' +
                ", cash=" + cash;
    }
}
