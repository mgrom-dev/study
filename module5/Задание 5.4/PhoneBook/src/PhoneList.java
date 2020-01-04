import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class PhoneList
{
    public String initMsg = "Input phone number, or Name for create new record. Other commands: LIST, DELETE, EXIT";
    public String viewAllCmd = "LIST";
    public String deleteCmd = "DELETE";
    public String exitCmd = "EXIT";
    public String deleteErrCmd = "Records not found. Input existing number records. Example: DELETE 1";
    public String addPhoneMsg = "Input phone number in any format (length number 10...11)";
    public String addNameMsg = "Input record name (length from 1 to 30)";
    public String err0length = "No records in phone book";
    public String recEditMsg = "An existing record has been modified";
    public String addedMsg = "Records added to base: ";

    private TreeMap<String, String> names = new TreeMap<>(); //Для хранения имен в нижнем регистре. Имя - ключ, номер телефона - значение
    private HashMap<String, String> phones = new HashMap<>(); //Для хранения имен в том регистре, в котором были введены пользователем. Номер телефона - ключ, имя - значение

    //показываем все записи в телефонной книге
    public void viewAll()
    {
        int i = 1;
        for (Map.Entry<String, String> name : names.entrySet())
        {
            System.out.println(i++ + ". " + phones.get(name.getValue()) + ", number: ✆ " + getFormattedPhone(name.getValue()));
        }
        if (i == 1)
        {
            System.out.println(err0length);
        }
    }

    //получаем число из строки
    public int getNumber(String number)
    {
        if (number.matches("^[0-9]+$"))
        {
            return Integer.parseInt(number);
        }
        return -1; //переданная строка не является числом
    }

    //удаление записи из телефонной книги
    public void deleteItem(String position)
    {
        int numPosition = getNumber(position);
        if (numPosition != -1 && numPosition <= names.size())
        {   //запись с номером 0 и 1 это одна и таже запись.
            numPosition = numPosition == 0 ? 1 : numPosition;
            int i = 1;
            for (Map.Entry<String, String> name : names.entrySet())
            {   //проходим по именам в алфавитном порядке до нужной позиции и удаляем связанный номер с записью и саму запись
                if (i == numPosition)
                {
                    phones.remove(name.getValue());
                    names.remove(name.getKey());
                    break;
                }
                i++;
            }
        }
        else
        { //если записи с таким номером в телефонном справочнике не существует
            System.out.println(deleteErrCmd);
        }
    }

    //проверка если текущая строка содержит номер телефона
    public boolean isNumber(String phone)
    {
        return phone.replaceAll("[^0-9]", "").matches("[0-9]{10,11}");
    }

    //проверка если текущая строка содержит имя
    public boolean isName(String name)
    {
        return name.matches("^[a-zA-Zа-яА-ЯеЁ ]{1,30}$");
    }

    //добавляем запись в справочник по номеру телефона
    public void addByNumber(String phone)
    {   //очищаем номер телефона от лишних символов
        phone = getClearPhone(phone);
        String name = phones.get(phone);
        if (name != null)
        {   //если есть уже запись с таким номером телефона, то выводим информацию по ней
            System.out.println("Find record\nName: " + name + ", phone: ✆ " + getFormattedPhone(phone));
        }
        else
        {   //запись не найдена, просим ввести имя
            Scanner scanner = new Scanner(System.in);
            while (true)
            {   //повторяем цикл пока не будет введен строка подходящая для имени
                System.out.println(addNameMsg);
                name = scanner.nextLine().trim();
                if (isName(name))
                {   //строка подходит для имени
                    if (names.get(name.toLowerCase()) != null)
                    {   //если введеное имя уже есть в дереве имен, то удаляем существующие записи
                        System.out.println(recEditMsg + ": " + phones.get(names.get(name.toLowerCase())));
                        names.remove(name.toLowerCase());
                        phones.remove(names.get(name.toLowerCase()));
                    }
                    //добавляем новые связанные между собой записи
                    phones.put(phone, name);
                    names.put(name.toLowerCase(), phone);
                    System.out.println(addedMsg + name + " (" + getFormattedPhone(phone) + ")");
                    break;
                }
            }
        }
    }

    //получаем из строки чистый номер телефона, длиной 10 символов, например 9234567890
    public String getClearPhone(String phone)
    {
        phone = phone.replaceAll("[^0-9]", "");
        if (phone.length() == 11) {
            phone = phone.substring(1, 11);
        }
        return phone;
    }

    //получаем стандартизированную строку с номером телефона
    public String getFormattedPhone(String phone)
    {
        return "+7 " + phone.substring(0, 3) + " " + phone.substring(3, 6) + "-" + phone.substring(6, 8) + "-" + phone.substring(8, 10);
    }

    //добавляем запись в справочник по имени
    public void addByName(String name)
    {   //ищем существующую запись в дереве имен
        String phone = names.get(name.toLowerCase());
        if (phone != null)
        {   //запись найдена, выводим данные по записи
            System.out.println("Find record\nName: " + phones.get(phone)+ ", phone: ✆ " + getFormattedPhone(phone));
        }
        else
            {   //запись не найдена, просим ввести номер телефона
                Scanner scanner = new Scanner(System.in);
                while (true)
                {   //повторяем цикл пока не будет введен номер телефона подходящий для базы
                    System.out.println(addPhoneMsg);
                    phone = scanner.nextLine().trim();
                    if (isNumber(phone))
                    {   //если введена строка, котоую можно преобразовать в номер телефона, преобразовываем
                        phone = getClearPhone(phone);
                        if (phones.get(phone) != null)
                        {   //если введеный телефон уже есть в таблице телефонов, то удаляем существующие записи
                            System.out.println(recEditMsg + ": " + phones.get(phone));
                            names.remove(phones.get(phone).toLowerCase());
                            phones.remove(phone);
                        }
                        //добавляем новые связанные между собой записи
                        names.put(name.toLowerCase(), phone);
                        phones.put(phone, name);
                        System.out.println(addedMsg + name + " (" + getFormattedPhone(phone) + ")");
                        break;
                    }
                }
            }
    }

    //проверяем введеную комманду в консоль
    public boolean checkCmd(String command)
    {
        String[] params = command.split(" ",2);
        if (params[0].toUpperCase().contains(viewAllCmd))
        {
            viewAll();
        }
        else if (params[0].toUpperCase().contains(deleteCmd))
        {
            deleteItem(params[1].trim());
        }
        else if (params[0].toUpperCase().contains(exitCmd))
        {
            System.exit(0); //выход из программы
        }
        else
        { //если не стандартная команда
            if (isNumber(command))
            { //если комманда содержит номер телефона
                addByNumber(command);
            }
            else if (isName(command))
            { //если комманда содержит имя человека
                addByName(command);
            }
            else
                { //переданная команда не расспознана
                    return false;
                }
        }
        return true;
    }

    //основное меню, осуществляется ввод с клавиатуры
    public void menu()
    {
        Scanner scanner = new Scanner(System.in);
        String command = "";
        while (true)
        {
            //если комманда не расспознана, то выводим список доступных комманд
            if (command.length() == 0 || !checkCmd(command))
            {
                System.out.println(initMsg);
            }
            command = scanner.nextLine().trim();
        }
    }
}
