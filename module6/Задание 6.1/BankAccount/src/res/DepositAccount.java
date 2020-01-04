package res;

import java.util.Calendar;
import java.util.GregorianCalendar;

//депозитарный расчетный счет
public class DepositAccount extends BankAccount
{
    private Calendar lastDeposit = new GregorianCalendar();

    //в конструкторе ставим дату последнего внесения месяц назад
    {
        lastDeposit.add(Calendar.MONTH, -1);
    }

    public Calendar getDateLastDeposit()
    {
        return lastDeposit;
    }

    @Override
    public boolean putMoney(double money)
    {
        //если деньги удалось внести, то ставим новую дату последнего внесения
        if (super.putMoney(money)) {
            lastDeposit = new GregorianCalendar();
            return true;
        }
        return false;
    }

    @Override
    public boolean withdrawMoney(double money)
    {
        //создаем текущую дату и отматываем месяц назад
        Calendar monthBefore = new GregorianCalendar();
        monthBefore.add(Calendar.MONTH, -1);
        //для снятия дата последнего депозита, должна быть меньше даты monthBefore
        if (lastDeposit.compareTo(monthBefore) < 0)
        {
            return super.withdrawMoney(money);
        }
        return false;
    }
}
