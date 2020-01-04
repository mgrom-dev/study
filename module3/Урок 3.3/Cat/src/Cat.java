
public class Cat
{
    private static int count; //count cats
    private double originWeight;
    private double weight;

    private double minWeight;
    private double maxWeight;
    private double foodEaten; //the food the cat ate

    public static int getCount()
    { //get counts alive cat
        return count;
    }

    public Cat()
    {
        count++; //new cat
        weight = 1500.0 + 3000.0 * Math.random();
        originWeight = weight;
        minWeight = 1000.0;
        maxWeight = 9000.0;
        foodEaten = 0.0; //the food the cat ate
    }

    private void modWeight(Double amount)
    { //modify weight cat +/-
        if (weight >= minWeight &&  weight <= maxWeight) { //if cat alive
            if (amount > 0) foodEaten += amount; //the cat ate
            weight = weight + amount; //change weight
            if (weight < minWeight || weight > maxWeight) count--; //cat is dead
        }
    }

    public void meow()
    {
        modWeight(- 1.0);
        System.out.println("Meow");
    }

    public void goTray()
    { //go Toilet cat
        Double dumpWeight = Math.random() * weight / 2; //drop extra weight
        modWeight(-dumpWeight);
        System.out.println(String.format("Cat pilled on %.2f kg", dumpWeight/1000));
    }

    public void feed(Double amount)
    {
        modWeight(amount);
    }

    public void drink(Double amount)
    {
        modWeight(amount);
    }

    public Double getFoodEaten()
    { //getter foodEaten
        return foodEaten;
    }

    public Double getWeight()
    {
        return weight;
    }

    public String getStatus()
    {
        if(weight < minWeight) {
            return "Dead";
        }
        else if(weight > maxWeight) {
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