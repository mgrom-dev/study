import java.util.stream.Stream;

public class Main {
    public static final int size = 50;

    public static void main(String[] args) {
        int max = size > 99 ? 99 : size %2 == 0 ? size - 1 : size;
        String[][] X = Stream.iterate( 0, x -> x < max, x -> ++x).map(x -> //some ninja code...
            Stream.iterate( 0, y -> y < max, y -> ++y).map(y -> y == x || y == max - 1 - x ? "X" : " ").toArray(String[]::new)
        ).toArray(String[][]::new);
        Stream.of(X).forEach(x->{System.out.println();
            Stream.of(x).forEach(System.out::print);});
        /*String[][] string = {
                {"X", " ", " ", " ", " ", " ", "X"},
                {" ", "X", " ", " ", " ", "X", " "},
                {" ", " ", "X", " ", "X", " ", " "},
                {" ", " ", " ", "X", " ", " ", " "},
                {" ", " ", "X", " ", "X", " ", " "},
                {" ", "X", " ", " ", " ", "X", " "},
                {"X", " ", " ", " ", " ", " ", "X"},
        };
        for (String[] strings : X) {
            for (String s : strings) {
                System.out.print(s);
            }
            System.out.println();
        }*/
    }
}
