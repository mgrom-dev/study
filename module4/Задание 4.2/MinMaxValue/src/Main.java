public class Main {

    public static void printMinMax(Class cls){
        try {
            System.out.println("Тип переменной '" + cls.getDeclaredField("TYPE").get(null) +
                    "', количество байт '" + (int) cls.getDeclaredField("SIZE").get(null) / 8 +
                    "', минимальное значение '" + cls.getDeclaredField("MIN_VALUE").get(null) +
                    "', максимальное значение '" + cls.getDeclaredField("MAX_VALUE").get(null) + "'");
        } catch (IllegalAccessException e) {
            System.out.println("Ошибка при попытке обратиться к полям констант");
        } catch (NoSuchFieldException e) {
            System.out.println("Поля констант не найдены");
        }
    }

    public static void main(String []args) {
        printMinMax(Byte.class);
        printMinMax(Short.class);
        printMinMax(Integer.class);
        printMinMax(Long.class);
        printMinMax(Float.class);
        printMinMax(Double.class);
    }
}
