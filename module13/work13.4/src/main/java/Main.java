import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;

import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static final MongoClient CLIENT = new MongoClient("127.0.0.1" ,27017); // Клиент
    public static final MongoDatabase DATABASE = CLIENT.getDatabase("local"); // База данных
    public static final MongoCollection<Document> PRODUCTS = DATABASE.getCollection("products"); // Коллекция продуктов
    public static final MongoCollection<Document> SHOPS = DATABASE.getCollection("shops"); // Коллекция магазинов
    public static final String INFO_COMMANDS = "Допустимые комманды:\n" +
            "EXIT - выход из программы\n" +
            "МАГАЗИН+ Девяточка - добавить магазин с названием Девяточка\n" +
            "ТОВАР+ Вафли 54 - добавить товар вафли с ценой 54 руб.\n" +
            "ВЫСТАВИТЬ+ Вафли Девяточка - добавить товар в магазин\n" +
            "СТАТИСТИКА - отобразить статистику по товарам";

    public static void main(String[] args) {
        // Очищаем коллекции
        PRODUCTS.drop();
        SHOPS.drop();

        //Считываем комманды с клавиатуры
        String command;
        System.out.println(INFO_COMMANDS);
        while (!"EXIT".equals((command = new Scanner(System.in).nextLine().trim()).toUpperCase())){
            String[] arguments = command.split(" ");
            if (arguments.length == 2 && "МАГАЗИН+".equals(arguments[0].toUpperCase())){
                addShop(arguments);
            } else if (arguments.length == 3 && "ТОВАР+".equals(arguments[0].toUpperCase())) {
                addProduct(arguments);
            } else if (arguments.length == 3 && "ВЫСТАВИТЬ+".equals(arguments[0].toUpperCase())) {
                submitProductToShop(arguments);
            } else if (arguments.length == 1 && "СТАТИСТИКА".equals(arguments[0].toUpperCase())) {
                productStatistics();
            } else {
                System.out.println(INFO_COMMANDS);
            }
        }
        CLIENT.close();
    }

    /**
     * Добавляем магазин в базу
     */
    private static void addShop(String ... args){
        if (SHOPS.find(new BsonDocument().append("name", new BsonString(args[1]))).first() == null){
            SHOPS.insertOne(Document.parse("{name: \"" + args[1] + "\", products: []}"));
            System.out.println("Магазин: " +  args[1] + " добавлен в базу.");
        } else {
            System.out.println("Магазин: " +  args[1] + " уже есть в базе.");
        }
    }

    /**
     * Добавляем товар в базу
     */
    private static void addProduct(String ... args){
        if (!args[2].matches("^[0-9]+")) {
            System.out.println("Цена за товар должна быть указана в целых рублях.");
            return;
        }
        PRODUCTS.updateOne(BsonDocument.parse("{name: {$eq: \"" + args[1] + "\"}}"),
                BsonDocument.parse("{$set: {price: " + args[2] + "}}"), new UpdateOptions().upsert(true));
        System.out.println("Товар: " +  args[1] + " с ценой " + args[2] + "руб., добавлен в базу.");
    }

    /**
     * Выставляем товар в магазин
     */
    private static void submitProductToShop(String ... args){
        Document product = PRODUCTS.find(new BsonDocument().append("name", new BsonString(args[1]))).first();
        Document shop = SHOPS.find(new BsonDocument().append("name", new BsonString(args[2]))).first();
        if (product == null) {
            System.out.println("Товар: " + args[1] + " не найден в базе.");
        } else if (shop == null) {
            System.out.println("Магазин: " + args[2] + " не найден в базе.");
        } else {
            SHOPS.updateOne(BsonDocument.parse("{name: {$eq: \"" + args[2] + "\"}}"),
                    BsonDocument.parse("{$addToSet: {products: " + product.toJson() + "}}"));
            System.out.println("Товар: " + args[1] + " выставлен на продажу в магазине " + args[2]);
        }
    }

    /**
     * Получаем статистику для каждого магазина:
     * — Общее количество товаров
     * — Среднюю цену товара
     * — Самый дорогой и самый дешевый товар
     * — Количество товаров, дешевле 100 рублей.
     */
    private static void productStatistics(){
        long countProducts = PRODUCTS.countDocuments();
        double averagePrice = Objects.requireNonNull(PRODUCTS.aggregate(Collections.singletonList(
                Document.parse("{$group: { _id: null, average: {$avg: '$price'} } }")
        )).first()).getDouble("average");
        Document topPrice = Objects.requireNonNull(PRODUCTS.find().sort(BsonDocument.parse("{price: -1}")).first());
        Document lowPrice = Objects.requireNonNull(PRODUCTS.find().sort(BsonDocument.parse("{price: 1}")).first());
        long countCheapProducts = PRODUCTS.countDocuments(BsonDocument.parse("{price: {$lt: 100}}"));
        System.out.println("Общее количество товаров в базе: " + countProducts + " шт.");
        System.out.println("Средняя цена товара: " + averagePrice + " руб.");
        System.out.println("Самый дорогой товар: " + topPrice.get("name") + ", цена " + topPrice.get("price") + " руб.");
        System.out.println("Самый дешевый товар: " + lowPrice.get("name") + ", цена " + lowPrice.get("price") + " руб.");
        System.out.println("Количество товаров дешевле 100 руб.: " + countCheapProducts + " шт.");
    }
}
