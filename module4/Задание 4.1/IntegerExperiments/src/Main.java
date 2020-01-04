public class Main
{
    public static void main(String[] args)
    {
        Container container = new Container();
        container.count += 7843;
        Integer num = 64379128;
        System.out.println("Сумма чисел числа " + num + " = " + sumDigits(num));
    }

    public static Integer sumDigits(Integer number) {
        String num = number.toString();
        int summ = 0;
        for (int i = 0; i < num.length(); i++) {
            summ += Integer.parseInt(String.valueOf(num.charAt(i)));
        }
        return summ;
    }
}
