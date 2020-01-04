
public class Loader
{
    public static void main(String[] args)
    {
        Cat barsik = new Cat(1000.0);
        Cat vasya = new Cat(500.0);
        Cat baks = new Cat();
        Cat baton = new Cat();
        Cat cat = new Cat();

        System.out.println("Count cat: " + Cat.getCount());
        System.out.println("Vasya weight: " + vasya.getWeight() + ", status:" + vasya.getStatus());
        System.out.println("Barsik weight: " + barsik.getWeight() + ", status:" + barsik.getStatus());
        barsik.feed(10000.0);
        System.out.println("Barsik weight: " + barsik.getWeight() + ", status:" + barsik.getStatus());
        System.out.println("Count cat: " + Cat.getCount());
    }
}