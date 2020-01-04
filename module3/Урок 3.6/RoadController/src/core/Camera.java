package core; //класс находится внутри пакета core

public class Camera //добавляем класс камера
{
    public static Car getNextCar() //метод генерации новой машины
    {
        String randomNumber = Double.toString(Math.random()).substring(2, 5); //строковая переменная, номер машины (генерируем случайное число и берем оттуда в строку с 2 по 5 число)
        int randomHeight = (int) (1000 + 3500. * Math.random()); //генерируем случайную высоту машины, целое число 4 байта
        double randomWeight = 600 + 10000 * Math.random(); //генерируем случайный вес машины, число с запятой 8 байт

        Car car = new Car(); //создаем новый объект класса Car
        car.setNumber(randomNumber); //устанавливаем номер машины
        car.setHeight(randomHeight); //устанавливаем высоту машины
        car.setWeight(randomWeight); //устанавливаем вес машины
        car.setHasVehicle(Math.random() > 0.5); //булевая переменная, которая озночает что машина с прицепом (вероятность 50%)
        car.setSpecial(Math.random() < 0.15); //булевая переменная, которая озночает что это спецтранспорт (вероятность 15%)

        return car; //возвращаем объект класса машины
    }
}