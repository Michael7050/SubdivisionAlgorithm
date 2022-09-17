package adaAsgnmnt2;

import java.util.Arrays;

public class Main {

    private static int[][] landValue = {{20, 40, 100, 130, 150, 200},{40, 140, 250, 320, 400, 450}, {100, 250, 350, 420, 450, 500},
            {130, 320, 420, 500, 600, 700}, {150, 400, 450, 600, 700, 800},{200, 450, 500, 700, 800, 900}};
    private static int cost = 50;

    public static void main(String[] args) {

        //test method for checking array
     //arrayToString(landValue);



    }

    //Brute Force

    //Greedy Approach

    //Exact Approach

    //print out array for debugging

    private static void arrayToString(int[][] x)
    {
        for (int n = 0 ; n < x.length ; n++)
        {
            System.out.println(Arrays.toString(x[n]));
        }
    }



}
