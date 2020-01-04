package res;

/**
 * интерфейс сотдруника, содержит такие методы как:
 * получение месячной зарплаты сотрудника
 * получение имени сотрудника
 * получение компании, в которой устроен сотрудник
 * получение наименования должности сотдруника
 * получения условии заработной платы для сотрудника
 *
 */
public interface Employee
{
    Double getMonthSalary();
    String getName();
    Company getCompany();
    Enum getPost();
    String getTermsOfEarning();
}