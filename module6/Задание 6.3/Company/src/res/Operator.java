package res;

/**
 * Класс сотрудника оператор
 */
public class Operator extends Worker
{
    Operator(){
        super.setSalary(35000);
        super.setPost(WORKERS.OPERATOR);
    }

    public String getTermsOfEarning()
    {
        return "зарплата складывается только из фиксированной части";
    }

    /**
     * зарплата складывается только из фиксированной части
     * @return - зарплата за месяц
     */
    public Double getMonthSalary()
    {
        return super.getSalary();
    }
}
