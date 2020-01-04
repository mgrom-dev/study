import java.util.Scanner;

public class Loader
{
    /**
     * task 4.4 - № 1
     * This methods print all symbols and codes for them (English, Russian, Numbers)[lowercase, UPERCASE]
     */
    public static void getCodesAlphabet(){
        String alphabetEn = "abcdefghijklmnopqrstuvwxyz";
        String alphabetRus = "абвгжеёжзийклмнопрстуфхцчшщъыьэюя";
        String numbers = "1234567890 ";
        System.out.println("Symbol codes:");
        PrintCode<Character> printChar = (c)-> System.out.print("['" + c + "' - code: " + (int) c + "] ");
        PrintCode<String> printString = (s)-> {
            for (char c:s.toCharArray()) {
                printChar.print(c);
            }
            System.out.println();
        };
        printString.print(alphabetEn);
        printString.print(alphabetEn.toUpperCase());
        printString.print(alphabetRus);
        printString.print(alphabetRus.toUpperCase());
        printString.print(numbers);
    }

    /**
     * Задание 4.4 - № 2
     * Метод печатает общую сумму заработка, выбирая из строки числа и суммируя их.
     * В качестве инструметов использовать indexOf(), lastIndexOf(), substring() и trim()
     */
    public static void summIncome(String str){
        System.out.println(str);
        int summ = 0;
        //i - индекс текущего символа в строке, l - длина строки,  e - индекс окончания числа в строке, c - текущий проверяемый символ на цифру
        for (int i = 0, l = str.length(), e, c; i < l; i++) {
            c = str.charAt(i);
            if (c >= 48 && c <= 57) { //если текущий символ - цифра
                e = str.indexOf(" ", i); //ищем пробел, означающий конец числа
                summ += Integer.parseInt(str.substring(i, e).trim()); //вырезаем число, обрезаем лишние пробелы, конвертируем в int
                i = e;
            }
        }
        System.out.println("Общая сумма заработка: " + summ + " руб.");
    }

    /**
     * Задание 4.4 - № 2
     * Метод печатает сумму заработка выбранного человека.
     * В качестве инструметов использовать indexOf(), lastIndexOf(), substring() и trim()
     */
    public static int summMan(String str, String name){
        System.out.print(name + ", сумма заработка: ");
        int earn = 0, i;
        str = str.toLowerCase();
        name = name.toLowerCase();
        while(true) {
            i = str.indexOf(name);
            if (i == -1) break;
            str = str.substring(i + name.length()).trim();
            i = str.indexOf(" руб");
            if (i == -1) break;
            str = str.substring(0, i).trim();
            i = str.lastIndexOf(" ");
            if (i == -1) break;
            earn = Integer.parseInt(str.substring(i, str.length()).trim());
        }
        System.out.println(earn + " руб.");
        return earn;
    }

    /**
     * Задание 4.4 - № 3
     * Метод читает из консоли строку и разбивает ее на Фамилию, Имя Отчество
     */
    public static void inputMan(){
        String str, fio[];
        while(true) {
            System.out.println("Введите Фамилию Имя Отчество через пробел");
            str = new Scanner(System.in).nextLine();
            if (!str.matches("[а-яА-ЯёЁa-zA-Z ]+")) continue;
            fio = str.split(" ");
            if (fio.length != 3) continue;
            System.out.println("Фамилия: " + fio[0]);
            System.out.println("Имя: " + fio[1]);
            System.out.println("Отчество: " + fio[2]);
            break;
        }
    }

    public static void main(String[] args)
    {
        getCodesAlphabet();
        String text = "Вася заработал 5000 рублей, Петя - 7563 рубля, а Маша - 30000 рублей";
        summIncome(text);
        int summ = summMan(text, "Вася");
        summ += summMan(text, "Маша");
        System.out.println("Сумма заработка Васи и Маши: " + summ + " руб.");
        inputMan();
    }
}

interface PrintCode<T>{
    void print(T s);;
}