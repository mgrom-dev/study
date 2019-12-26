public class Customer
{
    private String name;
    private String phone;
    private String eMail;

    public Customer(String name, String phone, String eMail)
    {
        this.name = name;

        //если номер телефона не соответствует формату, бросаем исключение неверного аргумента
        if (!phone.replaceAll("[^0-9]", "").matches("[0-9]{10,11}"))
        {
            throw new IllegalArgumentException("Error, incorrect phone number. Phone number accepted in any format (number of symbols 10...11), for example: +79231234567");
        }

        //убираем лишние символы из номера телефона
        phone = phone.replaceAll("[^0-9]", "");
        if (phone.length() == 11)
        {
            phone = phone.substring(1, 11);
        }
        this.phone = phone;

        //проверяем на соответствие форматуе email
        if (!eMail.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$"))
        {
            throw new IllegalArgumentException("Error, incorrect email. Format email example: vasily.petrov@gmail.com");
        }
        this.eMail = eMail;
    }

    public String toString()
    {
        return name + " - " + eMail + " - " + phone;
    }
}
