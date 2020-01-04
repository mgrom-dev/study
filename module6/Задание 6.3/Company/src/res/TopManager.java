package res;

/**
 * Класс сотрудника главный менеджер
 */
public class TopManager extends Worker
{
    TopManager(){
        super.setSalary(90000);
        super.setPost(WORKERS.TOP_MANAGER);
    }

    public String getTermsOfEarning()
    {
        return "зарплата складывается из фиксированной части и бонуса в виде 150% от заработной платы, если доход компании более 10 млн рублей";
    }

    /**
     * зарплата складывается из фиксированной части и бонуса в виде 150%
     * от заработной платы, если доход компании более 10 млн рублей
     * @return - зарплата за месяц
     */
    public Double getMonthSalary()
    {
        return super.getSalary() + (super.getCompany().getIncome() > 10000000. ? super.getSalary() * 1.5 : 0.);
    }
}
