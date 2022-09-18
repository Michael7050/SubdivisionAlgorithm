package adaAsgnmnt2;

import java.util.Arrays;
import java.util.*;

public class Main
{

    private static int[][] landValue = {{20, 40, 100, 130, 150, 200}, {40, 140, 250, 320, 400, 450}, {100, 250, 350, 420, 450, 500},
            {130, 320, 420, 500, 600, 700}, {150, 400, 450, 600, 700, 800}, {200, 450, 500, 700, 800, 900}};

    //initialise cost for subdividing a metre of land, and number of divides we want,
    //also initialising our bits of land
    private static int cost = 50;
    int height = 3;
    int length = 6;
    private int totalLandValue = 0;

    public static void main(String[] args)
    {

        //test method for checking array
        //arrayToString(landValue);


    }

    //Brute Force

    private void bruteForce(int x, int y)
    {
        this.totalLandValue = landValue[x][y];
        bruteForceMethod(x, y);
        //then traverse data structure and return result with highest data value.
    }

    private void bruteForceMethod(int x, int y)
    {
        //first reduce the total value by what we are given -
        this.totalLandValue = this.totalLandValue - landValue[x][y];

        int result1X = x;
        int result2X = x;
        int result1Y = y;
        int result2Y = y;
        boolean verticalSplit = true;

        //we use recursion
        //first split checks each possible split and gives us value of the two chunks of land, and total value of land so far.

        //first split vertically, run from left to right
        if (x > 1)
        {
            for (int l = 1; l < x; l++)
            {
                result1X = l;
                result2X = x-l;
                result1Y = y;
                result2Y = y;
                verticalSplit = true;

                //for each split, add new value
                this.totalLandValue = this.totalLandValue + (landValue[result1X][result1Y] + landValue[result2X][result2Y]);
                //reduce cost of split
                this.totalLandValue = this.totalLandValue - subdivideCost(verticalSplit, x, y);

                //record new values in arraylist(?) TODO

                //run recursive function on the left side, then run it on the right side
                //have land value be floating??? TODO
                bruteForceMethod(result1X, result1Y);
                bruteForceMethod(result2X,result2Y);
            }
        }

        //then we split it horizontally from bottom to top
        if (y > 1)
        {
            for (int w = 1; w < y; w++)
            {
                result1Y = w;
                result2Y = y-w;
                result1X = x;
                result2X = x;
                verticalSplit = false;

                //for each split, add new value
                this.totalLandValue = this.totalLandValue + (landValue[result1X][result1Y] + landValue[result2X][result2Y]);
                //reduce cost of split
                this.totalLandValue = this.totalLandValue - subdivideCost(verticalSplit, x, y);

                //record new values in arraylist(?) TODO
                bruteForceMethod(result1X, result1Y);
                bruteForceMethod(result2X,result2Y);
            }
        }

        //if both x and y are 1, return.

        return;

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
    private void exactMethod(int x, int y)
    {
        //this is an exact method
        //goes through each split, returns land value
        //takes highest value and continues with it.

        boolean vertSplit = true;
        int temp1X = x;
        int temp1Y = y;
        int temp2X = x;
        int temp2Y = y;
        int result1X = x;
        int result2X = x;
        int result1Y = y;
        int result2Y = y;
        int tempLandValue = this.totalLandValue;
        int currentHighestValue = this.totalLandValue;

        //subtract current chunk of land from value
        this.totalLandValue = this.totalLandValue - landValue[x][y];

        //go through vertical splits
        for (int l = 1; l < x; l++)
        {
            temp1X = l;
            temp2X = x-l;
            temp1Y = y;
            temp2Y = y;
            vertSplit = true;

            //for each split, add new value
            tempLandValue = this.totalLandValue + (landValue[result1X][result1Y] + landValue[result2X][result2Y]);
            //reduce cost of split
            tempLandValue = this.totalLandValue - subdivideCost(vertSplit, x, y);

            //if the split is the current best, save current split results to current best
            if (tempLandValue > currentHighestValue)
            {
                currentHighestValue = tempLandValue;
                result1X = temp1X;
                result1Y = temp1Y;
                result2X = temp2X;
                result2Y = temp1Y;
            }

        }

        //go through horizontal splits now
        if (y > 1)
        {
            for (int w = 1; w < y; w++)
            {
                temp1X = x;
                temp2X = x;
                temp1Y = w;
                temp2Y = y-w;
                vertSplit = false;

                //for each split, add new value
                tempLandValue = this.totalLandValue + (landValue[result1X][result1Y] + landValue[result2X][result2Y]);
                //reduce cost of split
                tempLandValue = this.totalLandValue - subdivideCost(vertSplit, x, y);

                if (tempLandValue > currentHighestValue)
                {
                    currentHighestValue = tempLandValue;
                    result1X = temp1X;
                    result1Y = temp1Y;
                    result2X = temp2X;
                    result2Y = temp1Y;
                }
            }

            if ((result1X == x) && (result1Y == y))
            {
                return;
            }

            //record current splits in data structure TODO

            //then do recursive method with our resultant splits
            exactMethod(result1X, result1Y);
            exactMethod(result2X, result2Y);
        }




        //if result1x = x and result1y = y, then no split, and return
    }

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
