import java.lang.Math;
import java.util.Scanner;

public class Main {
    //array of names
    public static class Man {
        public String name;
        public int age;
        { //constructor class Man
            name = Main.names[(int) (Math.random() * Main.names.length)]; //getting random name
        }
        @Override
        public String toString(){
            return name + ", age: " + age;
        }
    }
    public static String[] names = {"Steve", "Oliver", "Max", "John", "Tanya", "Denis", "Jessi", "Richard", "Alex", "Evan", "Linn", "Mia"};
    public static void main(String[] args) {
        int PeopleCount = 5; //count peoples
        Man junior = null; //youngest people
        Man senior = null; //older people
        int average = 0, summAges = 0; //for calculate average age
        Man[] mans = new Man[PeopleCount]; //create some People
        System.out.println("Enter ages for People:");
        Scanner scanner = new Scanner(System.in);
        for(int i = 0;  i < PeopleCount; i++) { //init all people
            mans[i] = new Man(); //init man
            System.out.println("Age of " + mans[i].name + ":");
            mans[i].age = scanner.nextInt(); //input age for current man from console
            //identify youngest and oldest people
            junior = junior == null ? mans[i] : mans[i].age < junior.age ? mans[i] : junior;
            senior = senior == null ? mans[i] : mans[i].age > senior.age ? mans[i] : senior;
            summAges += mans[i].age; //calculate ages all peoples
        }
        System.out.println("Youngest is " + junior);
        System.out.println("Older is " + senior);
        System.out.println("Average age is " + (int)(summAges / PeopleCount));
    }
}
