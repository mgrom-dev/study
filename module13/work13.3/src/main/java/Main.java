import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.Document;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class Main {
    public static final String CSV_FILE = "mongo.csv";

    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient( "127.0.0.1" , 27017 );
        MongoDatabase database = mongoClient.getDatabase("local");
        // Создаем коллекцию
        MongoCollection<Document> collection = database.getCollection("books");
        // Удаляем из коллекции все документы
        collection.drop();
        // Добавляем в коллекцию книги
        collection.insertOne(new Document()
                .append("Name", "11/22/63")
                .append("Author", "Stephen King")
                .append("Year", 2011));
        collection.insertOne(new Document()
                .append("Name", "Desperation")
                .append("Author", "Stephen King")
                .append("Year", 1996));
        // Добавляем в коллекцию книги с использованием JSON-синтаксиса
        collection.insertOne(Document.parse(
                "{Name: \"100% MARKETING\", Author:\"Igor Mann\", Year: 2016}"
        ));
        collection.insertMany(List.of(
                Document.parse("{Name: \"WAR AND PEACE\", Author:\"Lev Tolstoy\", Year: 1869}"),
                Document.parse("{Name: \"The master and Margarita\", Author:\"Mikhail Afanasievich Bulgakov\", Year: 1966}")
                ));

//        // Создание вложенного объекта
//        Document nestedObject = new Document()
//                .append("Course", "NoSQL Базы Данных")
//                .append("Author", "Mike Ovchinnikov");
//        firstDocument.append("Skillbox", nestedObject);

        collection.find().sort(BsonDocument.parse("{Year: 1}")).limit(1).forEach((Consumer<Document>) document ->
                System.out.println("Самая старая книга:\n" + document));

        System.out.println("Самые любимые книги:");
        collection.find(BsonDocument.parse("{Author: {$eq: \"Stephen King\"}}")).
                forEach((Consumer<Document>) System.out::println);

        //Читаем csv файл и добавляем студентов в базу
        collection.drop();
        try {
            Files.readAllLines(Path.of(CSV_FILE)).forEach(line -> {
                String[] params = line.split(",", 3);
                Document doc = new Document();
                doc.append("Name", params[0]);
                doc.append("Age", Integer.parseInt(params[1]));
                doc.append("Courses", List.of(params[2].replaceAll("\"","").split(",")));
                collection.insertOne(doc);
            });
        } catch(Exception ex){
            ex.printStackTrace();
        }
        System.out.println("Количество студентов в базе: " + collection.countDocuments());

        System.out.println("Количество студентов старше 40 лет: " + collection.countDocuments(BsonDocument.parse("{Age: {$gt: 40}}")));

        System.out.println("Самый молодой студент: " + Objects.requireNonNull(collection.find().sort(BsonDocument.parse("{Age: 1}")).first()).get("Name"));

        System.out.println("Список курсов самого старшего студента: " + Objects.requireNonNull(collection.find().sort(BsonDocument.parse("{Age: -1}")).first()).get("Courses"));
        mongoClient.close();
    }
}
