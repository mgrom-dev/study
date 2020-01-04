package core;

public class Car
{
    public String number; //общедоступная строковая переменная, номер машины
    public int height; //высота машины целое
    public double weight; //вес машины с запятой
    public boolean hasVehicle; //логическая переменная, если это автовоз (true/false)
    public boolean isSpecial; //если это спецтранспорт

    public String toString()
    {
        String special = isSpecial ? "СПЕЦТРАНСПОРТ " : ""; //строковая переменная спецтранспорт. С помощью тернарного оператора определяем, либо переменная будет равна "", либо "СПЕЦТРАНСПОРТ "
        return "\n=========================================\n" +
            special + "Автомобиль с номером " + number +
            ":\n\tВысота: " + height + " мм\n\tМасса: " + weight + " кг";
    }
}