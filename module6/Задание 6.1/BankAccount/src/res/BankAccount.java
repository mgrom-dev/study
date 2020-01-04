package res;

//класс расчетный счет в банке
class BankAccount
{
    private double money;

    public boolean putMoney(double money)
    {
        if (money > 0) {
            this.money += money;
            return true;
        }
        return false;
    }

    public boolean withdrawMoney(double money)
    {
        if (money > 0 && this.money - money > 0) {
            this.money -= money;
            return true;
        }
        return false;
    }

    public double getMoney()
    {
        return money;
    }
}
