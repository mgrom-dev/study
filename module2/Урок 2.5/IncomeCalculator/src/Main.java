import java.util.Scanner; //импортируем класс для считывания ввода с клавиатуры

//Программа считает доход компании, зарплату менеджера, налоги, определяет ее финансовое состояние и проверяет возможность инвестирования
public class Main //основной класс, совпадает с именем файла
{
    private static int minIncome = 200000; //минимальная сумма доходов компании за месяц
    private static int maxIncome = 900000; //максимальная сумма доходов компании за месяц

    private static int officeRentCharge = 140000; //стоимость аренды офиса
    private static int telephonyCharge = 12000; //стоимость телефонного обслуживания
    private static int internetAccessCharge = 7200; //стоимость абонентской платы за интернет

    private static int assistantSalary = 45000; //зарплата помощника
    private static int financeManagerSalary = 90000; //зарплата финансового менеджера

    private static double mainTaxPercent = 0.24; //размер ставки налога с доходов
    private static double managerPercent = 0.15; //процент с доходов, который получает менеджер в качестве зарплаты

    private static double minInvestmentsAmount = 100000; //минимальная сумма, с которой можно инвестировать

    public static void main(String[] args) //основная функция с которой начинается выполнение программы
    {
        //считаем минимальный размер суммы дохода, с которого компания может начать инвестировать
        int minIncome = (int)(((minInvestmentsAmount / (1 - mainTaxPercent)) + //считаем необходимую сумму для инвестиции с учетом налогов
                officeRentCharge + telephonyCharge + internetAccessCharge + assistantSalary + //добавляем постоянные расходы
                financeManagerSalary) / (1 - managerPercent) + 1); //и увеличиваем необходимую прибыль на зарплату менеджера
        System.out.println("Необходимая сумма дохода компании в месяц, чтобы начать ивестировать: " + minIncome); //выводим сообщение
        while(true) //бесконечный цикл
        {
            System.out.println("Введите сумму доходов компании за месяц " +
                "(от 200 до 900 тысяч рублей): "); //Выводим сообщение
            int income = (new Scanner(System.in)).nextInt(); //целая переменная месячного дохода компании, считываем с консоли значение

            if(!checkIncomeRange(income)) { //вызываем функцию проверки дохода за месяц в соответсвии с границами
                continue; //если доход за месяц за пределами границ, то возвращаемся в начало цикла
            }

            double managerSalary = income * managerPercent; //считаем зарплату менеджера
            double pureIncome = income - managerSalary - //считаем доход за месяц
                calculateFixedCharges(); //вызываем функцию подсчета суммы постоянных расходов
            double taxAmount = mainTaxPercent * pureIncome; //считаем налог с доходов
            double pureIncomeAfterTax = pureIncome - taxAmount; //считаем прибыль после уплаты налогов

            boolean canMakeInvestments = pureIncomeAfterTax >= //проверяем больше ли чистая прибыль, минимального размера суммы инвестиции
                minInvestmentsAmount; //истина - есть возможность инвестировать

            System.out.println("Зарплата менеджера: " + managerSalary); //выводим сообщение с зарплатой менеджера
            System.out.println("Общая сумма налогов: " + //выводим сообщение с суммой налога
                (taxAmount > 0 ? taxAmount : 0)); //если есть налоги (прибыль > 0), то выводим сумму налогов, если налоги ушли в минус, то выводим 0
            System.out.println("Компания может инвестировать: " + //выводим сообщение о возможности инвестирования
                (canMakeInvestments ? "да" : "нет")); //если canMakeInvestments истина, то "да", иначе "нет"
            if(pureIncome < 0) { //если доход меньше 0
                System.out.println("Бюджет в минусе! Нужно срочно зарабатывать!"); //выводим сообщение
            }
        }
    }

    private static boolean checkIncomeRange(int income) //функция проверки доходов за месяц в соответствии с заданными границами
    {
        if(income < minIncome) //если доходы меньше нижней границы
        {
            System.out.println("Доход меньше нижней границы"); //выводим сообщение
            return false; //возвращаем ложь
        }
        if(income > maxIncome) //если доходы больше верхней границы
        {
            System.out.println("Доход выше верхней границы"); //выводим сообщение
            return false; //возвращаем ложь
        }
        return true; //если доход находится в пределах границы, возвращаем истину
    }

    private static int calculateFixedCharges() //функция подсчета постоянных расходов
    {
        return officeRentCharge + //возвращаем сумму постоянных расходов
                telephonyCharge +
                internetAccessCharge +
                assistantSalary +
                financeManagerSalary;
    }
}
