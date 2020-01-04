package core;

public class Camera
{
    public static Car getNextCar()
    {
        String randomNumber = Double.toString(Math.random()).substring(2, 5); //строковая переменная, номер машины
        int randomHeight = (int) (1000 + 3500. * Math.random()); //генерируем слуайную высоту машины
        double randomWeight = 600 + 10000 * Math.random(); //генерируем случайный вес машины

        Car car = new Car();
        car.number = randomNumber;
        car.height = randomHeight;
        car.weight = randomWeight;
        car.hasVehicle = Math.random() > 0.5;
        car.isSpecial = Math.random() < 0.15;

        return car;
    }
}