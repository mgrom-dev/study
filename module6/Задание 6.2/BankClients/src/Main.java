import res.Bank;
import res.Client;

import java.util.TreeSet;

// Реализуйте классы, представляющие клиентов банка: абстрактный класс Client, а также классы для физических лиц,
// юридических лиц и индивидуальных предпринимателей. У каждого клиента есть расчётный счёт (число),
// который можно пополнять, с которого можно снимать, и баланс на котором можно смотреть. Реализовать методы таким образом,
// чтобы у физических лиц пополнение и снятие происходило без комиссии, у юридических лиц — снятие с комиссией 1%,
// а у ИП — пополнение с комиссией 1%, если сумма меньше 1000 рублей, и 0,5%, если сумма больше либо равна 1000 рублей.

public class Main
{
    public static void main(String[] args)
    {
        //создаем новый банк и генерируем 30 клиентов
        Bank bank = new Bank();
        for (int i = 0; i < 30; i++)
        {
            Client client;
            if (i < 10) {
                client = bank.newPhysicalPerson();
            } else if (i < 20)
            {
                client = bank.newIndividualPerson();
            } else
            {
                client = bank.newJuridicalPerson();
            }
            //кладем на расчетный счет клиента 1000 и снимаем 100
            client.putCash(1000 + i + Math.random() * i * 10);
            client.withdrawCash(100 + Math.random() * i * 10);
        }

        //печатаем клиентов банка
        TreeSet<Client> clients = bank.getClients();
        for (Client client : clients)
        {
            System.out.println(client);
        }
    }
}
