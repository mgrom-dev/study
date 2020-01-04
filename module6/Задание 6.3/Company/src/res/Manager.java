package res;

/**
 * Класс сотрудника менеджер
 */
public class Manager extends Worker
{
    private double earningMoney; //заработанные деньги для компании

    Manager(){
        super.setSalary(50000);
        super.setPost(WORKERS.MANAGER);
    }

    public double getEarningMoney() {
        return earningMoney;
    }

    public void setEarningMoney(double earningMoney) {
        this.earningMoney = earningMoney;
    }

    public String getTermsOfEarning()
    {
        return "Зарплата складывается из фиксированной части и бонуса в виде 5% от заработанных денег для компании";
    }

    /**
     * зарплата складывается из фиксированной части и бонуса в виде 5% от заработанных денег для компании
     * @return - зарплата за месяц
     */
    public Double getMonthSalary()
    {
        return super.getSalary() + earningMoney * 0.05;
    }
}