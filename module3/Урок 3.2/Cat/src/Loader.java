
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
        barsik.goTray();
        System.out.println("Barsik weight: " + barsik.getWeight() + ", status:" + barsik.getStatus());
        System.out.println("Vasya weight: " + vasya.getWeight() + ", status:" + vasya.getStatus());
        vasya.feed(10000.0);
        System.out.println("Vasya weight: " + vasya.getWeight() +" eat " + vasya.getFoodEaten() + ", status:" + vasya.getStatus());
    }
}