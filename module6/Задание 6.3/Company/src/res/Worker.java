package res;

import java.util.Formatter;

/**
 * абстрактный класс рабочего, который имплементирует интерфейс сотдруника и который наследуют все типы сотдруников
 */
public abstract class Worker implements Employee
{
    private String name;
    private Company company;
    private double salary;
    private Enum post;
    //список случайных имен для генерации нового сотрудника
    public final static String[] standartName = {"Мария", "Александр", "Григорий", "Михаил", "Татьяна", "Леонид", "Владимир", "Дмитрий", "Елена", "Федор", "Тамара", "Константин", "Максим", "Денис", "Павел", "Сергей", "Галина", "Анастасия", "Роман", "Валентин", "Виталий", "Юлия", "Ира", "Светлана"};

    //конструктор по умолчанию, генерируем случайное имя
    {
        name = standartName[(int) (Math.random() * standartName.length)];
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Company getCompany()
    {
        return company;
    }

    public void setCompany(Company company)
    {
        this.company = company;
    }

    public void setSalary(double salary)
    {
        this.salary = salary;
    }

    public double getSalary()
    {
        return salary;
    }

    public void setPost(Enum post) {
        this.post = post;
    }

    public Enum getPost()
    {
        return post;
    }

    /**
     * меняем метод toString для рабочего
     * @return - базовая информация в виде строки по сотруднику
     */
    @Override
    public String toString() {
        String isManager = post == WORKERS.MANAGER ? new Formatter().format(", заработал(а) для компании %,.02f", ((Manager) this).getEarningMoney()).toString() : "";
        return new Formatter().format("%s %s $%,.02f руб.", post, name, ((Employee)this).getMonthSalary()) + isManager;
    }
}
