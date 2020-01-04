
public class Cat
{
    public static final int NUM_EYES = 2;
    public static final double MIN_WEIGHT = 1000.0;
    public static final double MAX_WEIGHT = 9000.0;

    private static int count; //count cats

    private double originWeight;
    private double weight;
    private double foodEaten; //the food the cat ate


    public static int getCount()
    {
        return count;
    } //get counts alive cat

    public Double getFoodEaten()
    {
        return foodEaten;
    } //getter foodEaten

    public Double getWeight()
    {
        return weight;
    }

    private void modWeight(Double amount)
    {
        if (weight >= MIN_WEIGHT &&  weight <= MAX_WEIGHT) { //if cat alive
            if (amount > 0) foodEaten += amount; //the cat ate
            weight = weight + amount; //change weight
            if (weight < MIN_WEIGHT || weight > MAX_WEIGHT) count--; //cat is dead
        }
    } //modify weight cat +/-

    public Cat()
    {
        count++; //new cat
        weight = 1500.0 + 3000.0 * Math.random();
        originWeight = weight;
        foodEaten = 0.0; //the food the cat ate
    }

    public void meow()
    {
        modWeight(- 1.0);
        System.out.println("Meow");
    }

    public void goTray()
    {
        Double dumpWeight = Math.random() * weight / 2; //drop extra weight
        modWeight(-dumpWeight);
        System.out.println(String.format("Cat pilled on %.2f kg", dumpWeight/1000));
    } //go to Toilet cat

    public void feed(Double amount)
    {
        modWeight(amount);
    }

    public void drink(Double amount)
    {
        modWeight(amount);
    }

    public String getStatus()
    {
        if(weight < MIN_WEIGHT) {
            return "Dead";
        }
        else if(weight > MAX_WEIGHT) {
            return "Exploded";
        }
        else if(weight > originWeight) {
            return "Sleeping";
        }
        else {
            return "Playing";
        }
    }
}