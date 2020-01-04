
public class Loader
{
    public static void main(String[] args)
    {
        Cat barsik = new Cat(1000.0);
        Cat vasya = new Cat(500.0);
        Cat baks = new Cat();
        Cat baton = new Cat();
        Cat cat = new Cat();

        System.out.println("Barsik " + barsik);
        System.out.println("Vasya " + vasya);
        System.out.println("Baks " + baks);
        System.out.println("Baton " + baton);
        System.out.println("Cat " + cat);
        System.out.println("Count cat: " + Cat.getCount());

        barsik.feed(10000.0);
        vasya.drink(vasya.getWeight()/100);
        baks.meow();
        baton.goTray();
        cat.feed(-5000.0);

        System.out.println("Barsik " + barsik);
        System.out.println("Vasya " + vasya);
        System.out.println("Baks " + baks);
        System.out.println("Baton " + baton);
        System.out.println("Cat " + cat);
        System.out.println("Count cat: " + Cat.getCount());
    }
}