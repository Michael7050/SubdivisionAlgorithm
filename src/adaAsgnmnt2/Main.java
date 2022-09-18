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
    static int height = 3;
    static int length = 6;
    private int totalLandValue = 0;
    private int splitCounter = 0;
    int[][] landPlot = new int[height][length];

    public static void main(String[] args)
    {
        Main main = new Main();
        int plotx = length;
        int ploty = height;

        main.exactSolution(plotx, ploty);
    }

    //Brute Force

    private void bruteForce(int x, int y)
    {
        totalLandValue = getLandPrice(x, y);
        bruteForceMethod(x, y);
        //then traverse data structure and return result with highest data value.
    }

    private void exactSolution(int x, int y)
    {
        totalLandValue = getLandPrice(x, y);
        //debug code
        System.out.println(totalLandValue);
        int xStart = 0;
        int xEnd = x-1;
        int yStart = 0;
        int yEnd = y-1;
        exactMethod(xStart, xEnd, yStart, yEnd);
    }

    private void bruteForceMethod(int x, int y)
    {
        //first reduce the total value by what we are given -
        totalLandValue = totalLandValue - getLandPrice(x, y);

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
                totalLandValue = totalLandValue + (getLandPrice(result1X, result1Y) + getLandPrice(result2X, result2Y));
                //reduce cost of split
                totalLandValue = totalLandValue - subdivideCost(verticalSplit, x, y);

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
                totalLandValue = totalLandValue + (landValue[result1X][result1Y] + landValue[result2X][result2Y]);
                //reduce cost of split
                totalLandValue = totalLandValue - subdivideCost(verticalSplit, x, y);

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
    private void exactMethod(int xStart, int xEnd, int yStart, int yEnd)
    {
        //this is an exact method
        //goes through each split, returns land value
        //takes highest value and continues with it.

        //so this takes in the coordinates of a chunk of land in the array.

        boolean vertSplit = true;


        //temp result values
        int temp1XStart = xStart;
        int temp1YStart = yStart;
        int temp1XEnd = xEnd;
        int temp1YEnd = yEnd;
        int temp2XStart = xStart;
        int temp2YStart = yStart;
        int temp2XEnd = xEnd;
        int temp2YEnd = yEnd;

        //final result values
        int result1XStart = xStart;
        int result1XEnd = xEnd;
        int result1YStart = yEnd;
        int result1YEnd = yEnd;
        int result2XStart = xStart;
        int result2XEnd = xEnd;
        int result2YStart = yEnd;
        int result2YEnd = yEnd;

        int tempLandValue = totalLandValue;
        int currentHighestValue = totalLandValue;

        //area of current landplot:
        //make sure to check to make sure 0's are accounted for TODO
        int x = ((xEnd + 1) - (xStart + 1));
        int y = ((yEnd + 1) - (yStart + 1));

        //subtract current chunk of land from value
        tempLandValue = totalLandValue - getLandPrice(x,y);

        //go through vertical splits
        if (x > 1) //checks for if land is wider than one
        {
            //this runs through vertical splits of the given chunk of land
            //does this have the split in the right place? should I do xstart + 1? TODO
            for (int l = xStart; l < xEnd; l++)
            {
                //temp 1 is results to the left
                temp1XStart = xStart;
                temp1XEnd = l;
                temp1YStart = yStart;
                temp1YEnd = yEnd;

                //temp 2 is results to the right
                temp2XStart = l+1;
                temp2XEnd = xEnd;
                temp2YStart = yStart;
                temp2YEnd = yEnd;
                vertSplit = true;

                //find area of each result
                int temp1XArea = ((temp1XEnd + 1) - (temp1XStart + 1));
                int temp1YArea = ((temp1YEnd + 1) - (temp1YStart + 1));
                int temp2XArea = ((temp2XEnd + 1) - (temp2XStart + 1));
                int temp2YArea = ((temp2YEnd + 1) - (temp2YStart + 1));

                //for each split, add new value
                tempLandValue = tempLandValue + (getLandPrice(temp1XArea, temp1YArea) + getLandPrice(temp2XArea, temp2YArea));
                //reduce cost of split
                tempLandValue = tempLandValue - subdivideCost(vertSplit, x, y);

                //if the split is the current best, save current split results to current best
                if (tempLandValue > currentHighestValue)
                {
                    currentHighestValue = tempLandValue;
                    //set result 1
                    result1XStart = temp1XStart;
                    result1YStart = temp1YStart;
                    result1XEnd = temp1XEnd;
                    result1YEnd = temp1YEnd;
                    //set result 2
                    result2XStart = temp2XStart;
                    result2XEnd = temp2XEnd;
                    result2YStart = temp2YStart;
                    result2YEnd = temp2YEnd;

                }

            }
        }

        //go through horizontal splits now
        if (y > 1)
        {
            for (int w = yStart; w < yEnd; w++)
            {
                //set x values as they don't change
                temp1XStart = xStart;
                temp2XStart = xStart;
                temp1XEnd = xEnd;
                temp2XEnd = xEnd;
                //set y values
                //temp 1 is above, temp 2 is below
                temp1YStart = yStart;
                temp1YEnd = w;

                temp2YStart = w+1;
                temp2YEnd = yEnd;

                vertSplit = false;

                //find area of each result
                int temp1XArea = ((temp1XEnd + 1) - (temp1XStart + 1));
                int temp1YArea = ((temp1YEnd + 1) - (temp1YStart + 1));
                int temp2XArea = ((temp2XEnd + 1) - (temp2XStart + 1));
                int temp2YArea = ((temp2YEnd + 1) - (temp2YStart + 1));

                //for each split, add new value
                tempLandValue = totalLandValue + (getLandPrice(temp1XArea,temp1YArea) + getLandPrice(temp2XArea, temp2YArea));
                //reduce cost of split
                tempLandValue = totalLandValue - subdivideCost(vertSplit, x, y);

                if (tempLandValue > currentHighestValue)
                {
                    currentHighestValue = tempLandValue;
                    //set result 1
                    result1XStart = temp1XStart;
                    result1YStart = temp1YStart;
                    result1XEnd = temp1XEnd;
                    result1YEnd = temp1YEnd;
                    //set result 2
                    result2XStart = temp2XStart;
                    result2XEnd = temp2XEnd;
                    result2YStart = temp2YStart;
                    result2YEnd = temp2YEnd;
                }
            }

            //checks to see if result is the same as what was given
            if ((result1XStart == xStart) && (result1YStart == yStart) && (result1YEnd == yEnd) && (result1XEnd == xEnd))
            {
                return;
            }

            splitCounter++;
            //record current splits in data structure TODO

            //apply to the landplot - the land to the bottom right of the split is the new increment up one
            for (int a = (temp2XStart); a <= temp2XEnd; a++)
            {
                for (int b = (temp2YStart); b <= temp2YEnd; b++)
                {
                    landPlot[a][b] = splitCounter;
                }
            }
            //debug code here TODO for easier finding
            print2D(landPlot);

            //put snapshot of this and templandvalue in hashmap TODO

            //then do recursive method with our resultant splits
            exactMethod(result1XStart, result1XEnd, result1YStart, result1YEnd);
            exactMethod(result2XStart, result2XEnd, result2YStart, result2YEnd);
        }




        //if result1x = x and result1y = y, then no split, and return
    }

    //print out array for debugging

    private int getLandPrice(int x, int y)
    {
        return landValue[x-1][y-1];
    }

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

    //code from stackoverflow to print out 2d array for bugtesting.
    public static void print2D(int mat[][])
    {
        for (int[] row : mat)
        {

            // converting each row as string
            // and then printing in a separate line
            System.out.println(Arrays.toString(row));
        }
        System.out.println();
    }
}
