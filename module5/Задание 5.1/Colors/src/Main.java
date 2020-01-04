import java.util.Arrays;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        String text = "Каждый охотник желает знать, где сидит фазан";
        String[] colors = text.split(",?\\s+");
        /*String[] colorsReverse = new String[colors.length];
        //System.out.println("Массив colorsReverse");*/
        System.out.println("Массив colors:\n");
        for (int i = colors.length - 1, t = 0; i > t; i--, t++){
            String tmp = colors[t];
            colors[t] = colors[i];
            colors[i] = tmp;
        }
        /*for (String s : colorsReverse) {
            System.out.println(s);
        }
        System.out.println("\nМассив colors");
        colors = colorsReverse.clone();*/
        for (String s : colors) {
            System.out.println(s);
        }
        colors = text.split(",?\\s+");
        Collections.reverse(Arrays.asList(colors));
        System.out.println("\nИли так:\n" + Arrays.asList(colors).toString());
    }
}
