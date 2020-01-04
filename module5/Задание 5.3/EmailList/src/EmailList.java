import java.util.Scanner;
import java.util.TreeSet;

public class EmailList
{
    public String initMsg = "Enter one of the commands: LIST, ADD, DELETE, EXIT";
    public String errCmd = "Wrong command";
    public String addCmd = "ADD";
    public String viewAllCmd = "LIST";
    public String deleteCmd = "DELETE";
    public String exitCmd = "EXIT";
    public String addErrCmd = "Сommand syntax: ADD email";
    public String deleteErrCmd = "Сommand syntax: DELETE №email";
    public String errItem = "Error, incorrect email. Example email user152@mail.ru";
    public String err0length = "Error, 0 length of list email";

    private boolean onExit;
    private TreeSet<String> items = new TreeSet<>();
    private int count = 0;
    private String command = "";
    private String currentCmd = "";
    private String currentParam = "";

    //возвращаем весь список items
    public void viewAll()
    {
        int i = 1;
        for (String item : items)
        {
            System.out.println(i + ". " + item);
            i++;
        }
        if (i == 1)
        {
            System.out.println(err0length);
        }
    }

    //получаем номер позиции, если 1 параметр не число, то возвращаем -1
    public int getNumPos()
    {
        if (currentParam.matches("^[0-9]+$"))
        {
            return Integer.parseInt(currentParam);
        }
        return -1;
    }

    //получаем значение item
    public String getValue(int shift)
    {
        String value = "";
        if (currentCmd.length() + shift < command.length())
        {
            value = command.substring(currentCmd.length() + shift).trim();
        }
        return value;
    }

    //проверка значения item на соответсвие условиям
    public boolean checkItem(String item)
    {
        return item.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$");
    }

    //добавляем новый item
    public void addItem()
    {
        String addingItem = getValue(0);
        if (addingItem.length() > 0 )
        {
            if (checkItem(addingItem))
            {
                System.out.println("Email " + addingItem + " has been added to base");
                items.add(addingItem);
                count++;
            }
            else
            {
                System.out.println(errItem);
            }
        }
        else
        {
            System.out.println(addErrCmd);
        }
    }

    //удаляем item из items по значению в 1 параметре
    public void deleteItem()
    {
        int numPosition = getNumPos();
        if (numPosition != -1 && numPosition <= count)
        {
            numPosition = numPosition == 0 ? 1 : numPosition;
            int i = 1;
            for (String item : items) {
                if (i == numPosition) {
                    System.out.println("Email " + item + " has been removed from base");
                    items.remove(item);
                    break;
                }
                i++;
            }
            count--;
        }
        else
        {
            System.out.println(deleteErrCmd);
        }
    }

    //проверяем команду и вызываем соответствующий метод для выполнения данной команды
    public void checkCmd()
    {
        if (currentCmd.contains(addCmd))
        {
            addItem();
        }
        else if (currentCmd.contains(viewAllCmd))
        {
            viewAll();
        }
        else if (currentCmd.contains(deleteCmd))
        {
            deleteItem();
        }
        else if (currentCmd.contains(exitCmd))
        {
            onExit = true;
        }
        else
        {
            currentCmd = "";
        }
    }

    //разбиваем строку на команду и 1 параметр
    public void unpackCmd()
    {
        String[] setCmd = command.split(" ");
        currentCmd = setCmd.length > 0 ? setCmd[0] : "";
        currentParam = setCmd.length > 1 ? setCmd[1] : "";
    }

    //цикл меню, считываем данные с консоли
    public void menu()
    {
        onExit = false;
        Scanner scanner = new Scanner(System.in);
        while (!onExit)
        {
            if (command.length() == 0)
            {
                System.out.println(initMsg);
            }
            command = scanner.nextLine().trim();
            unpackCmd();
            checkCmd();
            if (currentCmd.length() == 0)
            {
                System.out.println(errCmd);
                command = "";
            }
        }
    }
}
