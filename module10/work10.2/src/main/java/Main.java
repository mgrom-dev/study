import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        LogManager logManager = LogManager.getLogManager();
        Logger logger = logManager.getLogger("");
        logger.setLevel(Level.SEVERE); //could be Level.OFF
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.openSession();

        //получаем информацию о каком-нибудь курсе
        Course course = session.get(Course.class, 1);
        System.out.println(course.getName());

        //*получаем информацию из другой таблицы
        Teacher teacher = session.get(Teacher.class, 1);
        System.out.println(teacher.getName());
        
        sessionFactory.close();
    }
}
