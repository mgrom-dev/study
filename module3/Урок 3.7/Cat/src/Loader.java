
public class Loader
{
    public static void main(String[] args)
    {
        Cat barsik = new Cat("Barsik", 1000.0);
        Cat vasya = new Cat("Vasya", 500.0);
        Cat baks = new Cat("Baks");
        Cat baton = new Cat("Baton");
        Cat cat = new Cat("Cat");

        System.out.println(Cat.getAllCats());
        System.out.println("Count alive cat: " + Cat.getCount());

        barsik.feed(10000.0);
        vasya.drink(vasya.getWeight() / 100);
        baks.meow();
        baton.goTray();
        baton.clone(barsik);

        System.out.println(Cat.getAllCats());
        System.out.println("Count alive cat: " + Cat.getCount());
    }
}