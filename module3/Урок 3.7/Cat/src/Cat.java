import java.util.ArrayList;

public class Cat
{
    public static final int NUM_EYES = 2;
    public static final double MIN_WEIGHT = 1000.0;
    public static final double MAX_WEIGHT = 9000.0;
    public static ArrayList<Cat> cats = new ArrayList<>(); //all cats

    private static int count; //count alvie cats

    private String name; //cat name
    private double weight;
    private Color color; //color cat
    private double originWeight;
    private double foodEaten; //the food the cat ate

    public void setName(String name)
    {
        this.name = name;
    } //setter name

    public String getName(){
        return name;
    } //getter name

    public void setColor(Color color)
    {
        this.color = color;
    } //setter color

    public Color getColor(){
        return color;
    } //getter color

    public static int getCount()
    {
        count = 0;
        cats.forEach((cat) -> count += isAlive(cat) ? 1 : 0 ); //if cat alive ++;
        return count;
    } //get counts alive cat

    private static boolean isAlive(Cat cat){
        return cat.weight >= MIN_WEIGHT && cat.weight <= MAX_WEIGHT;
    } //check is cat alive

    public double getFoodEaten()
    {
        return foodEaten;
    } //getter foodEaten

    public double getWeight()
    {
        return weight;
    }

    private void modWeight(double amount)
    {
        if (isAlive(this)) { //if cat alive
            if (amount > 0) foodEaten += amount; //the cat ate
            weight = weight + amount; //change weight
        }
    } //modify weight cat +/-

    public Cat()
    {
        cats.add(this); //add cat to cats
        weight = 1500.0 + 3000.0 * Math.random();
        originWeight = weight;
        foodEaten = 0.0; //the food the cat ate
        setColor(Color.values()[(int)(Math.random() * Color.values().length)]); //generate random color
        getCount();
    }

    /**
     * Конструктор класса cat с переменным количеством параметров
     * @param args массив передаваемых значений по порядку: 1.Имя, 2.Вес, 3.Цвет, 4.Начальный вес, 5.Количество съеденной еды
     */
    public Cat(Object ...args)
    {
        this();
        this.name = (String) args[0];
        if (args.length > 1) {
            this.weight = (double) args[1];
            this.originWeight = this.weight;
        }
        if (args.length > 2) this.color = (Color) args[2];
        if (args.length > 3) this.originWeight = (double) args[3];
        if (args.length > 4) this.foodEaten = (double) args[4];
        getCount();
    } //constructor cat with all params

    public void clone(Cat copy) {
        this.name = copy.name;
        this.weight = copy.weight;
        this.color = copy.color;
        this.originWeight = copy.originWeight;
        this.foodEaten = copy.foodEaten;
        getCount(); //recalculate alive cats
    } //copy cat

    public static String getAllCats(){
        String allCats = "";
        boolean first = true;
        for (Cat cat : cats) {
            if (first) first = false;
            else allCats += "\n";
            allCats += cat;
        }
        return allCats;
    } //get a string representation of all object cat

    public String toString(){
        String color = "";
        if (getColor() == Color.BLACK) color = "\u001B[35m";
        if (getColor() == Color.BLUE) color = "\u001B[34m";
        if (getColor() == Color.YELLOW) color = "\u001B[33m";
        if (getColor() == Color.RED) color = "\u001B[31m";
        if (getColor() == Color.WHITE) color = "\u001B[37m";
        return color + getName() + " status: " + getStatus() + ", initial weight: " + originWeight +
                ", current weight: " + getWeight() + " , food eaten: " + getFoodEaten() + ", color: " + getColor() + "\u001B[0m";
    } //get a string representation of this object

    public void meow()
    {
        String[] words = {"Meow", "Meeeeow", "meowff ...", "mew mew", "*MEOW**MEOW**MEOW*", "Мяяяяяяяяяяяяяяяяяяяяяяяяу"};
        modWeight(- 1.0);
        System.out.println(this.name +" says: " + words[(int)(Math.random() * words.length)]);
    }

    public void goTray()
    {
        double dumpWeight = Math.random() * weight / 2; //drop extra weight
        modWeight(-dumpWeight);
        System.out.println(String.format("Cat pilled on %.2f kg", dumpWeight/1000));
    } //go to Toilet cat

    public void feed(double amount)
    {
        modWeight(amount);
    }

    public void drink(double amount)
    {
        modWeight(amount);
    }

    public String getStatus()
    {
        if(weight < MIN_WEIGHT) {
            return "Dead =(^X_X^)=";
        }
        else if(weight > MAX_WEIGHT) {
            return "Exploded ^(._´˵)^=";
        }
        else if(weight > originWeight) {
            return "Sleeping =(^ｪ^)=";
        }
        else {
            return "Playing =(^ᵔᴥᵔ^)=";
        }
    }
}