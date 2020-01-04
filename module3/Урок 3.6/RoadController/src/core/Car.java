package core; //класс находится внутри пакета core

public class Car //Создаем класс машину
{
    private String number; //общедоступная строковая переменная, номер машины
    private int height; //высота машины, целое число 4 байта
    private double weight; //вес машины, число с запятой 8 байт
    private boolean hasVehicle; //логическая переменная, если это прицеп (true/false)
    private boolean special; //если это спецтранспорт, булевая переменная

    public void setNumber(String number)
    {
        this.number = number;
    }

    public String getNumber(){
        return number;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public int getHeight(){
        return height;
    }

    public void setWeight(double weight)
    {
        this.weight = weight;
    }

    public double getWeight(){
        return weight;
    }

    public void setHasVehicle(boolean hasVehicle)
    {
        this.hasVehicle = hasVehicle;
    }

    public boolean isHasVehicle(){
        return hasVehicle;
    }

    public void setSpecial(boolean special)
    {
        this.special = special;
    }

    public boolean isSpecial(){
        return special;
    }

    public String toString() //определяем метод преобразования объекта в строку
    {
        String special = isSpecial() ? "СПЕЦТРАНСПОРТ " : ""; //строковая переменная спецтранспорт. С помощью тернарного оператора определяем, либо переменная будет равна "", либо "СПЕЦТРАНСПОРТ "
        return "\n=========================================\n" + //возвращаем текстовое представление объекта
            special + "Автомобиль с номером " + getNumber() +
            ":\n\tВысота: " + getHeight() + " мм\n\tМасса: " + getWeight() + " кг";
    }
}