
public class Loader
{
    public static void main(String[] args)
    {
        Cat barsik = new Cat();
        Cat vasya = new Cat();
        Cat baks = new Cat();
        Cat baton = new Cat();
        Cat cat = new Cat();

        System.out.println("Count cat: " + Cat.getCount());
        barsik.drink(-10000.0);
        System.out.println("Barsik weight: " + barsik.getWeight() + ", status:" + barsik.getStatus());
        System.out.println("Vasya weight: " + vasya.getWeight() + ", status:" + vasya.getStatus());
        vasya.feed(10000.0);
        System.out.println("Vasya weight: " + vasya.getWeight() +" eat " + vasya.getFoodEaten() + ", status:" + vasya.getStatus());
        System.out.println("Count cat: " + Cat.getCount());
        vasya.feed(10000.0);
        System.out.println("Vasya weight: " + vasya.getWeight() +" eat " + vasya.getFoodEaten() + ", status:" + vasya.getStatus());
        System.out.println("Count cat: " + Cat.getCount());
    }
}