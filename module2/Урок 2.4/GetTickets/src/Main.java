public class Main {

    public static void loopFor(int[] ... fromTo){ //give array numbers with label From - To (Print Tickets [[From:1, To:10], [From:100, To:200]])
        boolean first; // need for remove extra comma
        for (int[] startEnd : fromTo) { //run all arrays From - To
            if (startEnd.length != 2) continue; //miss if incorrectly count params
            first = true;
            System.out.print("Print tickets with help 'for' № " + startEnd[0] + " - " + startEnd[1] + ": (");
            for (int i = startEnd[0]; i <= startEnd[1]; i++) {
                System.out.print((!first ? ", " : "") + i);
                if (first) first = false;
            }
            System.out.println(")");
        }
    }

    public static void loopWhile(int[] ... fromTo){
        boolean first;
        int i = 0;
        while(i < fromTo.length){
            if (fromTo[i].length != 2) {
                i++;
                continue;
            }
            first = true;
            System.out.print("Print tickets with help 'while' № " + fromTo[i][0] + " - " + fromTo[i][1] + ": (");
            while (fromTo[i][0] <= fromTo[i][1]) {
                System.out.print((!first?", ":"") + fromTo[i][0]);
                if (first) first = false;
                fromTo[i][0]++;
            }
            System.out.println(")");
            i++;
        }
    }

    public static void main(String[] args) {
        loopFor(new int[][]{new int[] {200000, 210000}, new int[] {220000, 235000}});
        loopWhile(new int[][]{new int[] {200000, 210000}, new int[] {220000, 235000}});
    }
}
