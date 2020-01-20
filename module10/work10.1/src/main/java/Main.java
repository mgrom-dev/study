import java.sql.*;

public class Main {
    static final String url = "jdbc:mysql://localhost:3306/skillbox";
    static final String user = "root";
    static final String password = "testtest";

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            //вычисляем количество месяцев с момента первой покупки и последней
            statement.execute("SELECT @count_months := " +
                    "MAX(YEAR(subscription_date) * 100 + MONTH(subscription_date)) - " +
                    "MIN(YEAR(subscription_date) * 100 + MONTH(subscription_date)) + 1 FROM purchaselist;");
            System.out.println("course_name - average_number_of_purchases_per_month");
            //среднее количество покупок в месяц по курсу = общее количество покупок курса за все время / количество прошедших месяцев за период
            ResultSet resultSet = statement.executeQuery("SELECT course_name, " +
                    "COUNT(course_name) / @count_months AS average_number_of_purchases_per_month " +
                    "FROM purchaselist GROUP BY course_name ORDER BY course_name;");
            ResultSetMetaData rsmd = resultSet.getMetaData();
            //выводим заголовок таблицы
            for (int i = 1, c = rsmd.getColumnCount(); i <= c; i++){
                System.out.printf("| %-38s", rsmd.getColumnName(i));
            }
            System.out.println("|\n" + String.format("%" + (rsmd.getColumnCount() * 40 + 1) + "s", "").replace(' ', '-'));
            //выводим строки таблицы
            while(resultSet.next()){
                for (int i = 1, c = rsmd.getColumnCount(); i <= c; i++){
                    System.out.printf("| %-38s", resultSet.getString(i));
                }
                System.out.println("|");
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
