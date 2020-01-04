
public class Loader
{
    public static void main(String[] args)
    {
        int milkAmount = 300; // ml
        int powderAmount = 300; // g
        int eggsCount = 5; // items
        int sugarAmount = 5; // g
        int oilAmount = 30; // ml
        int appleCount = 4;
        boolean nothing = true; //can't anything cook

        //Get all ingredients to string
        String ingredients = (milkAmount > 0 ? "\nMilk " + milkAmount + " ml" : "") +
                (powderAmount > 0 ? "\nFloor " + powderAmount + " g" : "") + (eggsCount > 0 ? "\nEggs " + eggsCount : "") +
                (sugarAmount > 0 ? "\nSugar " + sugarAmount + " g" : "") + (oilAmount > 0 ? "\nOil " + oilAmount + " ml" : "") +
                (appleCount > 0 ? "\nApple " + appleCount : "");
        System.out.println("You may cook from ingredients:" + ingredients + "\n_____________________________");
        //powder - 400 g, sugar - 10 g, milk - 1 l, oil - 30 ml
        if (powderAmount >= 400 && sugarAmount >= 10 && milkAmount >= 1000 && oilAmount >= 30) {
            System.out.println("Pancakes");
            nothing = false;
        }
        //milk - 300 ml, powder - 5 g, eggs - 5
        if (powderAmount >= 5 && milkAmount >= 300 && eggsCount >= 5) {
            System.out.println("Omelette");
            nothing = false;
        }
        //apples - 3, milk - 100 ml, powder - 300 g, eggs - 4
        if (powderAmount >= 300 && milkAmount >= 100 && eggsCount >= 4 && appleCount >= 3) {
            System.out.println("Apple pie");
            nothing = false;
        }
        if (nothing) System.out.println("nothing...");
    }
}