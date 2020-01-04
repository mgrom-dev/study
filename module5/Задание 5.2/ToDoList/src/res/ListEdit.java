package res;

import java.util.ArrayList;
import java.util.Scanner;

public class ListEdit
{
    public String initMsg = "Enter one of the commands: LIST, ADD, EDIT, DELETE, EXIT";
    public String errCmd = "Wrong command";
    public String addCmd = "ADD";
    public String viewAllCmd = "LIST";
    public String editCmd = "EDIT";
    public String deleteCmd = "DELETE";
    public String exitCmd = "EXIT";
    public String addErrCmd = "Сommand syntax: ADD № ToDo or ADD ToDo";
    public String editErrCmd = "Сommand syntax: EDIT № ToDo";
    public String deleteErrCmd = "Сommand syntax: DELETE № ToDo";
    public String errItem = "Error incorrect ToDo";

    private boolean onExit;
    private ArrayList<String> items = new ArrayList<>();
    private String command = "";
    private String currentCmd = "";
    private String currentParam = "";

    //возвращаем весь список items
    public void viewAll()
    {
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i));
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
        return true;
    }

    //добавляем новый item
    public void addItem()
    {
        int numPosition = getNumPos();
        //если в команде отсутствует параметр, в какой индекс элемента items вставить новое значение,
        //то берем всю строку после команды в качестве значения item, иначе делаем сдвиг на длину первого параметра
        //и получаем оставшуюся строку
        String addingItem = numPosition == -1 ? getValue(0) : getValue((" " + currentParam + " ").length());
        if (addingItem.length() > 0 )
        {
            if (checkItem(addingItem))
            {
                //если номер позиции, куда добавить новый item больше размера items, то меняем позицию на последний элемент items
                numPosition = numPosition > items.size() || numPosition == -1 ? items.size() + 1 : numPosition;
                //делаем поправку, 0 и 1 - один и тот же первый элемент массива items
                numPosition = numPosition == 0 ? 1 : numPosition;
                items.add(numPosition - 1, addingItem);
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

    //редактируем item, в качестве нового значения берем всю строку после команды и 1 параметра
    public void editItem()
    {
        int numPosition = getNumPos();
        String editItem = getValue((" " + currentParam + " ").length());
        if (editItem.length() > 0 && numPosition != -1 && numPosition <= items.size())
        {
            if (checkItem(editItem))
            {
                numPosition = numPosition == 0 ? 1 : numPosition;
                items.set(numPosition - 1, editItem);
            }
            else
            {
                System.out.println(errItem);
            }
        }
        else
        {
            System.out.println(editErrCmd);
        }
    }

    //удаляем item из items по значению в 1 параметре
    public void deleteItem()
    {
        int numPosition = getNumPos();
        if (numPosition != -1 && numPosition <= items.size() - 1)
        {
            numPosition = numPosition == 0 ? 1 : numPosition;
            items.remove(numPosition - 1);
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
        else if (currentCmd.contains(editCmd))
        {
            editItem();
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
        String[] setCmd= command.split(" ");
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
