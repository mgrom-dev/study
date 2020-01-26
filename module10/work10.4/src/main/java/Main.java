import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        //remove info logs to console for hibernate
        LogManager logManager = LogManager.getLogManager();
        Logger logger = logManager.getLogger("");
        logger.setLevel(Level.SEVERE);

        //init hibernate and session
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.openSession();

        //get 5 rows from purchaseList
        List<PurchaseList> purchaseLists = session.createCriteria(PurchaseList.class).setMaxResults(5).list();
        purchaseLists.forEach(System.out::println);
        System.out.println(purchaseLists.get(1).getStudent().getName());

        //add new record to subscription
//        Subscription sub = session.get(Subscription.class, new SubscriptionPK(1,1));
//        if (sub != null){
//            session.delete(sub);
//        }
//        sub = new Subscription();
//        sub.setId(new SubscriptionPK(1,1));
//        sub.setSubscriptionDate(new Date());
//        Transaction transaction = session.beginTransaction();
//        session.save(sub);
//        transaction.commit();

        //close session
        session.close();
        sessionFactory.close();
    }
}