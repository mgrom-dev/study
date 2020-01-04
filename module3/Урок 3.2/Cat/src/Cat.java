
public class Cat
{
    private double originWeight;
    private double weight;

    private double minWeight;
    private double maxWeight;
    private double foodEaten; //the food the cat ate

    public Cat()
    {
        weight = 1500.0 + 3000.0 * Math.random();
        originWeight = weight;
        minWeight = 1000.0;
        maxWeight = 9000.0;
        foodEaten = 0.0; //the food the cat ate
    }

    private void modWeight(Double amount)
    { //modify weight cat +/-
        if (amount > 0) foodEaten += amount;
        weight = weight + amount;
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