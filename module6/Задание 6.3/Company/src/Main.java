import res.Company;
import res.WORKERS;

public class Main
{
    public static void main(String[] args)
    {
        Company apple = new Company();
        //Создайте и наймите в компанию:180 операторов Operator, 80 менеджеров по продажам Manager, 10 топ менеджеров TopManager
        apple.hireAll(WORKERS.OPERATOR, 180);
        apple.hireAll(WORKERS.MANAGER, 80);
        apple.hireAll(WORKERS.TOP_MANAGER, 10);

        //Распечатайте список состоящий из 10-15 самых высоких зарплат в компании
        apple.newMonth(); //генерируем прибыль за месяц
        System.out.printf("Прибыль компании за месяц $%,.02f\nКоличество сотрудников в компании: %d\n", apple.getIncome(), apple.getWorkers().size());
        System.out.println(apple.getTopSalaryStaff(10));

        //Распечатайте список из 30 самых низких зарплат в компании
        System.out.println(apple.getLowestSalaryStaff(30));

        //Увольте 50% сотрудников
        ///увольняем случайного сотрудника
        for (int i = 0, l = apple.getWorkers().size() / 2; i < l; i++ ){
            apple.fire(apple.getWorkers().get((int) (apple.getWorkers().size() * Math.random())));
        }
        System.out.println("Уволили 50% сотрудников. Количество сотдруников в компании: " + apple.getWorkers().size());

        //Распечатайте список из 10-15 самых высоких зарплат в компании
        System.out.println(apple.getTopSalaryStaff(10));

        //Распечатайте список из 30 самых низких зарплат в компании
        System.out.print(apple.getLowestSalaryStaff(30));
    }
}
