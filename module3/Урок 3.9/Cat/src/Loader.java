import java.util.Scanner;

public class Loader
{
    private static boolean exit = false;
    private static short menu = 0;
    private static Cat currentCat;

    private static void catDoMenu(){
        System.out.println("What do with " + currentCat.getName() + "?");
        System.out.println("0. Back to cats menu.");
        System.out.println("1. Look at the cat");
        System.out.println("2. Feed the cat");
        System.out.println("3. Move cat to tray");
        System.out.println("4. Pat the cat");
        try {
            int num = new Scanner(System.in).nextInt();
            if (num == 0) menu = 1;
            if (num == 1)  System.out.println(currentCat.getStatus() + " weight " + currentCat.getWeight() +
                    ", food eaten " + currentCat.getFoodEaten() + ", color " + currentCat.getColor());
            if (num == 2) {
                if (Math.random() > 0.7) currentCat.feed(Math.random() * 1000);
                else currentCat.drink(Math.random() * 500);
            }
            if (num == 3) currentCat.goTray();
            if (num == 4) currentCat.meow();
        } catch (Exception e){
            System.out.println("Not correct number!");
        }
    }

    private static void catsMenu(){
        System.out.println("Which cat to choose?");
        System.out.println("0. Back to main menu.");
        int i = 0;
        for (Cat cat : Cat.cats) {
            ++i;
            System.out.println(i + "." + cat.getName());
        }
        try {
            int num = new Scanner(System.in).nextInt();
            if (num == 0) menu = 0;
            if (num <= Cat.cats.size()) {
                currentCat = Cat.cats.get(num - 1);
                menu = 2;
            }
        } catch (Exception e){
            System.out.println("Not correct number!");
        }
    }

    private static void mainMenu(){
        System.out.println("In world: " + Cat.getCount() + " alive cats. Write number, what do?");
        System.out.println("1. See all the cats");
        System.out.println("2. Choose cat");
        System.out.println("3. Clean up the room");
        System.out.println("4. Exit");
        try {
            int num = new Scanner(System.in).nextInt();
            if (num == 1) System.out.println(Cat.getAllCats());
            if (num == 2) menu = 1;
            if (num == 3) new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            if (num == 4) exit = true;
        } catch (Exception e){
            System.out.println("Not correct number!");
        }
    }

    private static void getMenu(){
        if (menu == 0) mainMenu();
        if (menu == 1) catsMenu();
        if (menu == 2) catDoMenu();
    }

    public static void main(String[] args)
    {
        Cat barsik = new Cat("Barsik");
        Cat vasya = new Cat("Vasya");
        Cat baks = new Cat("Baks");
        Cat baton = new Cat("Baton");
        Cat cat = new Cat("Cat");
        baton.clone(barsik);
        while(!exit){
            getMenu();
        }
    }
}