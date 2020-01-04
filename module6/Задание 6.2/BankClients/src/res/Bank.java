package res;

import java.util.TreeSet;

/**
 * Класс - банк, в котором хранятся его клиенты
 */
public class Bank
{
    private static TreeSet<Client> clients = new TreeSet<>();

    /**
     * Генерируем заданное количество случайных чисел
     * @param count - количество символов, которое нужно сгенерировать
     * @return возвращаем строку из заданного количества чисел
     */
    private static String generateDigit(int count)
    {
        StringBuilder digits = new StringBuilder();
        for (int i = 0; i < count; i++)
        {
            digits.append((int) (10 * Math.random()));
        }
        return digits.toString();
    }

    /**
     * Создаем нового клиента физическое лицо. Номер счета начинается с "40817810", подразделение банка "4405"
     */
    public static PhysicalPerson newPhysicalPerson()
    {
        String numberAccount = "40817810" + generateDigit(1) + "4405" + generateDigit(7);
        PhysicalPerson newClient = new PhysicalPerson(numberAccount);
        clients.add(newClient);
        return newClient;
    }

    /**
     * Создаем нового клиента юридическое лицо. Номер счета начинается с "40702810", подразделение банка "4405"
     */
    public static JuridicalPerson newJuridicalPerson()
    {
        String numberAccount = "40702810" + generateDigit(1) + "4405" + generateDigit(7);
        JuridicalPerson newClient = new JuridicalPerson(numberAccount);
        clients.add(newClient);
        return newClient;
    }

    /**
     * Создаем нового клиента индивидуальный предприниматель. Номер счета начинается с "40701810", подразделение банка "4405"
     */
    public static IndividualPerson newIndividualPerson()
    {
        String numberAccount = "40701810" + generateDigit(1) + "4405" + generateDigit(7);
        IndividualPerson newClient = new IndividualPerson(numberAccount);
        clients.add(newClient);
        return newClient;
    }

    public static TreeSet<Client> getClients()
    {
        return clients;
    }
}