import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Loader
{
    /**
     * Метод возвращает совпадения подстрок в строке в соответствии с регулярным выражением (аналог match JS)
     * @param text входная строка, в которой будет производится поиск
     * @param pattern регулярное выражение
     * @return возвращает массив типа String[] совпавших подстрок. Если совпадений не найдено, то возвращается массив 0 длины
     */
    public static String[] match(String text, String pattern) {
        Matcher matcher = Pattern.compile(pattern).matcher(text); //иницилизируем регулярку
        ArrayList<String> arrayStrings = new ArrayList<>(); //создаем список указателей строк под найденные подстроки
        while (matcher.find()) { //получаем все найденные подстроки (параметр /g в JS)
            arrayStrings.add(text.substring(matcher.start(), matcher.end())); //вырезаем подстроку из текста и добавляем в список
        }
        return arrayStrings.toArray(new String[arrayStrings.size()]); //конвертируем список указателей в массив строк
    }

    /**
     * Задание 4.5 - № 1
     * Метод печатает сумму заработка каждого и в конце выводит общую сумму.
     */
    public static void summIncome(String str){
        System.out.println(str);
        int summ = 0;
        String[] names = match(str,"[А-Я]{1}[а-я]{1,14}");
        String[] money = match(str,"[0-9]+");
        if (names.length > 0 && money.length > 0 && names.length == money.length) {
            System.out.println("Заработок друзей: ");
            for (int i = 0, l = names.length, m; i < l; i++){
                m = Integer.parseInt(money[i]);
                System.out.println(names[i] + " " + m + " руб.");
                summ += m;
            }
        }
        System.out.println("Общая сумма заработка: " + summ + " руб.");
    }

    /**
     * Задание 4.5 - № 2
     * Метод разбивает строку на слова и печатает в консоли
     */
    public static void printByWordsNews(){
        String news = "PENSACOLA, Fla. — Four people died, including the suspect, and seven people were wounded Friday morning in a shooting at Naval Air Station Pensacola — the second shooting at a U.S. Navy base this week.\n" +
                "The shooting began around 6:30 a.m. central time Friday and was reported around 7 a.m., authorities said. \n" +
                "Two units responded to a classroom building on the base within \"a couple of minutes\" of receiving reports, said Chip Simmons, sheriff's office deputy. The shooting happened on two floors of the building.\n" +
                "Two deputies stopped the shooter and sustained injuries. One was shot in arm and treated at a local hospital, and one was shot in knee and was undergoing surgery.\n" +
                "Authorities could not publicly say if the suspect was a member of the militaryor if the was shooting was being considered an act of terrorism.\n" +
                "Eight patients, including the deputie were accepted at nearby Baptist Hospital. One died at the hospital, and three died on the base, including the shooter.\n" +
                "The names of the victims will not be released until the next of kin have been notified, authorities said.\n" +
                "\"Walking through the crime scene was like being on the set of a movie,\" said Sheriff's Office spokeswoman Amber Southard. \"This doesn't happen in Escambia County. This doesn’t happen in Pensacola. ... So now we’re here to pick up the pieces.\" \n" +
                "The Navy base will be closed all day Friday.";
        String[] words = news.replaceAll("[^a-zA-Z ]","").replaceAll("[ ]{2,}", "").split(" ");
        System.out.println("Print news " + words.length + " words:");
        for (String word:words){
            System.out.println(word);
        }
    }

    /**
     * Задание 4.5 - № 3
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

    /**
     * Задание 4.5 - № 4
     * Метод считывает строку из консоли и пытается привести ее к унифицированному формату номера телефона
     */
    public static void inputPhoneNumber(){
        String str;
        while(true) {
            System.out.println("Введите номер телефона в любом формате");
            str = new Scanner(System.in).nextLine();
            str = str.replaceAll("[^0-9]", "");
            if (str.length() < 10 || str.length() > 11) {
                System.out.println("Неккоректный номер телефона");
                continue;
            }
            if (str.length() == 11) str = str.substring(1, 11);
            str = "+7 " + str.substring(0, 3) + " " + str.substring(3, 6) + "-" + str.substring(6, 8) + "-" + str.substring(8, 10);
            System.out.println("Номер телефона ✆ " + str);
            break;
        }
    }

    public static void main(String[] args)
    {
        String text = "Вася заработал 5000 рублей, Петя - 7563 рубля, а Маша - 30000 рублей";
        summIncome(text);
        printByWordsNews();
        //inputMan();
        inputPhoneNumber();
    }
}