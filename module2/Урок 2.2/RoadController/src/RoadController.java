import core.*;
import core.Camera;

import java.util.Scanner;

public class RoadController
{
    private static double passengerCarMaxWeight = 3500.0; // kg локальная переменная класса с плавающей запятой - 8 байт, максимальный вес для легкового транспорта, если больше, то считается как грузовой транспорт
    private static int passengerCarMaxHeight = 2000; // mm локальная переменная класса типа целое число - 4 байта, максимальная высота для легкового транспорта, если выше, то счаитется как за грузовой транспорт
    private static int controllerMaxHeight = 4000; // mm локальная переменная класса типа целое число - 4 байта, высота пропускного пункта

    private static int passengerCarPrice = 100; // RUB локальная переменная класса типа целое число - 4 байта, стоимость проезда легкового транспорта
    private static int cargoCarPrice = 250; // RUB локальная переменная класса типа целое число - 4 байта, стоимость проезда грузового транспорта
    private static int vehicleAdditionalPrice = 200; // RUB локальная переменная класса типа целое число - 4 байта, доплата за автовоз

    public static void main(String[] args)
    {
        System.out.println("Сколько автомобилей сгенерировать?");

        Scanner scanner = new Scanner(System.in); //создаем объект класса Scanner для ввода из консоли
        int carsCount = scanner.nextInt(); //создаем целую переменную количества машин и считываем значение из консоли
        for(int i = 0; i < carsCount; i++) //i - счетчик для цикла
        {
            Car car = Camera.getNextCar(); //создаем объект класса Car вызывая метод из ресурса Camera, который генерирует машину случайным образом
            System.out.println(car);

            //Пропускаем автомобили спецтранспорта бесплатно
            if (car.isSpecial) {
                openWay();
                continue;
            }

            //Проверяем высоту и массу автомобиля, вычисляем стоимость проезда
            int price = calculatePrice(car); //целая переменная стоимости проезда
            if(price == -1) {
                continue;
            }

            System.out.println("Общая сумма к оплате: " + price + " руб.");
        }
    }

    /**
     * Расчёт стоимости проезда исходя из массы и высоты
     */
    private static int calculatePrice(Car car)
    {
        int carHeight = car.height; //целая переменная высота машины
        int price = 0; //стоимость проезда
        if (carHeight > controllerMaxHeight)
        {
            blockWay("высота вашего ТС превышает высоту пропускного пункта!");
            return -1;
        }
        else if (carHeight > passengerCarMaxHeight)
        {
            double weight = car.weight; //переменная с плавающей запятой, вес машины
            //Грузовой автомобиль
            if (weight > passengerCarMaxWeight)
            {
                price = passengerCarPrice;
                if (car.hasVehicle) {
                    price = price + vehicleAdditionalPrice;
                }
            }
            //Легковой автомобиль
            else {
                price = cargoCarPrice;
            }
        }
        else {
            price = passengerCarPrice;
        }
        return price;
    }

    /**
     * Открытие шлагбаума
     */
    private static void openWay()
    {
        System.out.println("Шлагбаум открывается... Счастливого пути!");
    }

    /**
     * Сообщение о невозможности проезда
     */
    private static void blockWay(String reason)
    {
        System.out.println("Проезд невозможен: " + reason);
    }
}