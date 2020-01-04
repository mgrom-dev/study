
public class Loader
{
    public static void main(String[] args)
    {
        Cat barsik = new Cat();
        Cat vasya = new Cat();
        Cat baks = new Cat();
        Cat baton = new Cat();
        Cat cat = new Cat();

        System.out.println("Barsik weight: " + barsik.getWeight() + ", status:" + barsik.getStatus());
        System.out.println("Baton weight: " + baton.getWeight() + ", status:" + baton.getStatus());
        barsik.feed(1000.0);
        baton.meow();
        System.out.println("Barsik weight: " + barsik.getWeight() + ", status:" + barsik.getStatus());
        System.out.println("Baton weight: " + baton.getWeight() + ", status:" + baton.getStatus());
        System.out.println("Vasya weight: " + vasya.getWeight() + ", status:" + vasya.getStatus());
        vasya.feed(10000.0);
        System.out.println("Vasya weight: " + vasya.getWeight() + ", status:" + vasya.getStatus());
        System.out.println("Baks weight: " + baks.getWeight() + ", status:" + baks.getStatus());
        for (int i = 0; i < 10000; i++) {
            baks.meow();
        }
        System.out.println("Baks weight: " + baks.getWeight() + ", status:" + baks.getStatus());
    }
}