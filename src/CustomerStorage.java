import java.util.HashMap;

public class CustomerStorage
{
    private HashMap<String, Customer> storage;

    public CustomerStorage()
    {
        storage = new HashMap<>();
    }

    public void addCustomer(String data) throws IndexOutOfBoundsException
    {
        String[] components = data.split("\\s+");

        //если передано неверное количество параметров, бросаем исключение
        if (components.length < 4)
        {
            throw new IndexOutOfBoundsException("Not enough params for create new records. Enter \"help\" commands for more info.");
        }

        String name = components[0] + " " + components[1];
        storage.put(name, new Customer(name, components[3], components[2]));
    }

    public void listCustomers()
    {
        storage.values().forEach(System.out::println);
    }

    public void removeCustomer(String name)
    {
        storage.remove(name);
    }

    public int getCount()
    {
        return storage.size();
    }
}