package res;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Класс содержащий сотрудников
 */
public class Company
{
    private double income; //прибыль компании
    private ArrayList<Employee> workers = new ArrayList<>(); //список работников

    public double getIncome() {
        return income;
    }

    public ArrayList<Employee> getWorkers() {
        return workers;
    }

    /**
     * метод генерации прибыли компании и зарплаты сотрудников за месяц
     */
    public void newMonth(){
        double incomeMonth = Math.random() * 50000 +
                workers.size() * Math.random() * 40000;
        income += incomeMonth;
        for (Employee man : workers){
            if (man.getPost() == WORKERS.MANAGER) {
                //генерируем деньги которые заработал менеджер для компании
                double earningMoneys = Math.random() * 50000;
                ((Manager) man).setEarningMoney(((Manager) man).getEarningMoney() + earningMoneys);
                income += earningMoneys;
            }
        }
    }

    /**
     * метод найма 1 работника
     * @param typeWorker - тип работника, которого нужно нанять, из Enum Worker
     */
    public void hire(WORKERS typeWorker){
        Employee man = null;
        switch (typeWorker){
            case MANAGER:
                man = new Manager();
                break;
            case OPERATOR:
                man = new Operator();
                break;
            case TOP_MANAGER:
                man = new TopManager();
                break;
        }
        ((Worker)man).setCompany(this);
        workers.add(man);
    }

    /**
     * метод найма нескольких сотрудников в компанию
     * @param typeWorker - тип работника
     * @param count - количество работников, которое нужно нанять
     */
    public void hireAll(WORKERS typeWorker, int count){
        for (int i = 0; i < count; i++) {
            hire(typeWorker);
        }
    }

    /**
     * метод увольнения сотрудника
     * @param man - сотдруник, которого необходимо уволить
     */
    public void fire(Employee man){
        workers.remove(man);
    }

    /**
     * возвращает список сотдруников с самыми низкими зарплатами
     * @param count - количество сотрудников, которых нужно отобрать
     */
    public List<Employee> getLowestSalaryStaff(int count){
        //защита на случай, если количество сотрудников которых нужно получить, указано больше чем количество сотрудников
        count = count > workers.size() ? workers.size() : count;
        //сортируем список по возрастанию зарплаты
        workers.sort(Comparator.comparing(Employee::getMonthSalary));
        //извлекаем новый список сотрудников с необходимым количеством и возвращаем его
        //также заменяем метод toString для ArrayList, чтобы можно было получить список в читабельном виде
        return new ArrayList<Employee>(workers.subList(0, count)){
            @Override
            public String toString() {
                String ret = "Список " + this.size() + " самых низких зарплат\n";
                for (int i = 0; i < this.size(); ret += i + 1 + ". " + this.get(i) + "\n", i++) ;
                return ret;
            }
        };
    }

    /**
     * возвращает список сотрудников с самыми высокими зарплатами
     * @param count - количество сотрудников, которых нужно отобрать
     */
    public List<Employee> getTopSalaryStaff(int count){
        //защита на случай, если количество сотрудников которых нужно получить, указано больше чем количество сотрудников
        count = count > workers.size() ? workers.size() : count;
        //сортируем список по убыванию зарплаты
        workers.sort((man1, man2) -> man2.getMonthSalary().compareTo(man1.getMonthSalary()));
        //извлекаем новый список сотрудников с необходимым количеством и возвращаем его
        //также заменяем метод toString для ArrayList, чтобы можно было получить список в читабельном виде
        return new ArrayList<Employee>(workers.subList(0, count)){
            @Override
            public String toString() {
                String ret = "Список " + this.size() + " самых высоких зарплат\n";
                for (int i = 0; i < this.size(); ret += i + 1 + ". " + this.get(i) + "\n", i++) ;
                return ret;
            }
        };
    }
}
