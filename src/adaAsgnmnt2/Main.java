package adaAsgnmnt2;

import java.util.Arrays;

public class Main {

    private static int[][] landValue = {{20, 40, 100, 130, 150, 200},{40, 140, 250, 320, 400, 450}, {100, 250, 350, 420, 450, 500},
            {130, 320, 420, 500, 600, 700}, {150, 400, 450, 600, 700, 800},{200, 450, 500, 700, 800, 900}};

    //initialise cost for subdividing a metre of land, and number of divides we want,
    //also initialising our bits of land
    private static int cost = 50;
    int height = 3;
    int length = 6;
    private int totalLandValue = 0;

    public static void main(String[] args) {

        //test method for checking array
     //arrayToString(landValue);



    }

    //Brute Force

    private void bruteForceMethod(int x, int y)
    {
        int result1X = x;
        int result2X = x;
        int result1Y = y;
        int result2Y = y;
        boolean verticalSplit = true;

        //we use recursion
        //first split checks each possible split and gives us value of the two chunks of land, and total value of land so far.
        if (x > 1)
        {
            for (int l = 1; l > x; l++)
            {
                result1X = l;
                result2X = x-l;
                result1Y = y;
                result2Y = y;
                verticalSplit = true;
                //run recursive function
            }
        }
        if (y > 1)
        {
            for (int w = 1; w > y; w++)
            {
                result1Y = w;
                result2Y = y-w;
                result1X = x;
                result2X = x;
                verticalSplit = false;
            }
        }

        //split creates a tree to traverse?

        //take in an area of land with length x, and width y

        //split vertically first

            //do a for loop that iterates over each split
        //if x is over 1, split at 1, split++ until split is at x-1
        //then
        //if y is over 1, split at 1, split++ until split is at y-1
            //this will give us two pieces of land
            //do the split again on each piece of land

        //this will result in two subplots of land with x and y

        //value of each plot is landValue[x][y]

        //if


        //so for a plot land with length x and width y, total number of different splits will be ((x-1) + (y-1))

    }

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

    //Method to calculate the cost of a subdivide cost
    //if split is vertical, cost is times x, if not cost is times y;
    private int subdivideCost(boolean vertSplit, int x, int y)
    {
        int result;
        if (vertSplit)
        {
            result = x;
        }
        else
        {
            result = y;
        }
        return (result * cost);
    }



}
