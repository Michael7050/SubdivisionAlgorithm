package adaAsgnmnt2;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Arrays;
import java.util.*;

public class Main {

    private static int[][] landValue = {{20, 40, 100, 130, 150, 200}, {40, 140, 250, 320, 400, 450}, {100, 250, 350, 420, 450, 500},
            {130, 320, 420, 500, 600, 700}, {150, 400, 450, 600, 700, 800}, {200, 450, 500, 700, 800, 900}};

    //initialise cost for subdividing a metre of land, and number of divides we want,
    //also initialising our bits of land
    private static int cost = 5;
    static int height = 3;
    static int length = 3;
    private int initialLandValue = 0;
    public int currentLandValue = 0;
    private int splitCounter = 0;
    int[][] landPlot = new int[height][length];
    int floatingValue = 0;

    public static void main(String[] args) {

//        GUI();

        //test method for checking array
        //arrayToString(landValue);
        Main main = new Main();
        int plotx = length;
        int ploty = height;

//        ArrayList<Integer[][]> splitData = new ArrayList<Integer[][]>();
//
//        int[][] storeSplitData = {{450, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}};
//        int[][] storeSplitData2 = {{500, 0, 0, 0, 0, 0}, {1, 1, 1, 1, 1, 1}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}};
//
//
////        splitData.add(new ArrayList<int[][]>);
//
//        List<int[][]> list = new ArrayList<>();
//
//        int[][] copiedArray = new int[storeSplitData.length][storeSplitData[0].length];
//        for (int i = 0; i < storeSplitData.length; i++) {
//            for(int j = 0; j < storeSplitData[0].length; j++) {
//                copiedArray[i][j] = storeSplitData[i][j];
//            }
//        }
//
//        int[][] copiedArray2 = new int[storeSplitData2.length][storeSplitData2[0].length];
//        for (int i = 0; i < storeSplitData2.length; i++) {
//            for(int j = 0; j < storeSplitData2[0].length; j++) {
//                copiedArray2[i][j] = storeSplitData2[i][j];
//            }
//        }
//
//        list.add(copiedArray);
//        list.add(copiedArray2);
//
//        for (int[][] array : list ){
//            int[][] temp = array;
//            for (int[] element : temp) {
//                System.out.println(Arrays.toString(element));
//            }
//            System.out.println("\n");
//    }
//
//
//        int[][] area;
//        area = new int[3][6];
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 6; j++) {
//                area[i][j] = 1;
//            }
//        }
//
//        System.out.println(Arrays.deepToString(area));

        main.bruteForce(plotx, ploty);
    }

    //Brute Force

    //TODO
    //create a value for when it isn't splitting and save
    //track land value

    public void bruteForce(int x, int y) {
        //this should be a working brute force method, that goes through each combination i think.

        currentLandValue = landValue[x - 1][y - 1];
        initialLandValue = currentLandValue;
        splitCounter = 0;
        //debug code here
        System.out.println(currentLandValue);

        int xStart = 0;
        int xEnd = x;
        int yStart = 0;
        int yEnd = y;

        bruteForceMethod(xStart,xEnd,yStart,yEnd, splitCounter, currentLandValue);

        //do i take a snapshot here?
    }



    private void exactSolution(int x, int y)
    {
        //takes in a chunk of land XY
        initialLandValue = getLandPrice(x, y);
        
        currentLandValue = initialLandValue;
        //debug code
        System.out.println(initialLandValue);
        int xStart = 0;
        int xEnd = x;
        int yStart = 0;
        int yEnd = y;
        exactMethod(xStart, xEnd, yStart, yEnd);
    }


    private void bruteForceMethod(int xStart, int xEnd, int yStart, int yEnd, int splitCount, int value)
    {
        splitCount++;

        boolean vertSplit = true;
        //temp result values

        //final result values
        int result1XStart = xStart;
        int result1XEnd = xEnd;
        int result1YStart = yEnd;
        int result1YEnd = yEnd;
        int result2XStart = xStart;
        int result2XEnd = xEnd;
        int result2YStart = yEnd;
        int result2YEnd = yEnd;

        int x = ((xEnd) - (xStart));
        int y = ((yEnd) - (yStart));

        if (x > 1) //checks for if land is wider than one
        {

            for (int l = xStart+1; l < xEnd; l++) {

                result1XStart = xStart;
                result1XEnd = l;
                result1YStart = yStart;
                result1YEnd = yEnd;

                //temp 2 is results to the right
                result2XStart = l;
                result2XEnd = xEnd;
                result2YStart = yStart;
                result2YEnd = yEnd;
                vertSplit = true;

                //find area of each result
                int result1XArea = ((result1XEnd) - (result1XStart));
                int result1YArea = ((result1YEnd) - (result1YStart));
                int result2XArea = ((result2XEnd) - (result2XStart));
                int result2YArea = ((result2YEnd) - (result2YStart));

                //subtract current chunk of land from value
                int tempLandValue = value - getLandPrice(x,y);
                //for each split, add new value
                tempLandValue = tempLandValue + (getLandPrice(result1XArea, result1YArea) + getLandPrice(result2XArea, result2YArea));
                //reduce cost of split
                tempLandValue = tempLandValue - subdivideCost(vertSplit, x, y);

                //TODO - function here that prints current landarray and value
                updateArray(result2XStart, result2XEnd, result2YStart, result2YEnd, splitCount);
                System.out.println(tempLandValue);

                bruteForceMethod(result1XStart, result1XEnd, result1YStart, result1YEnd, splitCount, tempLandValue);
                bruteForceMethod(result2XStart, result2XEnd, result2YStart, result2YEnd, splitCount, tempLandValue);

            }
        }

        //go through horizontal splits now
        if (y > 1) {
            for (int w = yStart + 1; w < yEnd; w++) {
                //set x values as they don't change
                result1XStart = xStart;
                result2XStart = xStart;
                result1XEnd = xEnd;
                result2XEnd = xEnd;

                //set y values
                //temp 1 is above, temp 2 is below
                result1YStart = yStart;
                result1YEnd = w;

                result2YStart = w;
                result2YEnd = yEnd;

                vertSplit = false;

                //find area of each result
                int result1XArea = ((result1XEnd) - (result1XStart));
                int result1YArea = ((result1YEnd) - (result1YStart));
                int result2XArea = ((result2XEnd) - (result2XStart));
                int result2YArea = ((result2YEnd) - (result2YStart));

                //subtract current chunk of land from value
                int tempLandValue = value - getLandPrice(x,y);
                //for each split, add new value
                tempLandValue = tempLandValue + (getLandPrice(result1XArea, result1YArea) + getLandPrice(result2XArea, result2YArea));
                //reduce cost of split
                tempLandValue = tempLandValue - subdivideCost(vertSplit, x, y);

                //TODO - function here that prints current landarray and value
                System.out.println(tempLandValue);
                updateArray(result2XStart, result2XEnd, result2YStart, result2YEnd, splitCount);

                bruteForceMethod(result1XStart, result1XEnd, result1YStart, result1YEnd, splitCount, tempLandValue);
                bruteForceMethod(result2XStart, result2XEnd, result2YStart, result2YEnd, splitCount, tempLandValue);

            }
        }
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

        int currentHighestValue = currentLandValue;

        //area of current landplot:
        //make sure to check to make sure 0's are accounted for TODO
        int x = ((xEnd) - (xStart));
        int y = ((yEnd) - (yStart));


        //go through vertical splits
        if (x > 1) //checks for if land is wider than one
        {
            //this runs through vertical splits of the given chunk of land
            //does this have the split in the right place? should I do xstart + 1? TODO
            for (int l = xStart+1; l < xEnd; l++) {
//                System.out.println(l);
                //temp 1 is results to the left
                temp1XStart = xStart;
                temp1XEnd = l;
                temp1YStart = yStart;
                temp1YEnd = yEnd;

                //temp 2 is results to the right
                temp2XStart = l;
                temp2XEnd = xEnd;
                temp2YStart = yStart;
                temp2YEnd = yEnd;
                vertSplit = true;

                //find area of each result
                int temp1XArea = ((temp1XEnd) - (temp1XStart));
                int temp1YArea = ((temp1YEnd) - (temp1YStart));
                int temp2XArea = ((temp2XEnd) - (temp2XStart));
                int temp2YArea = ((temp2YEnd) - (temp2YStart));

                //subtract current chunk of land from value
                int tempLandValue = currentLandValue - getLandPrice(x,y);
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
        if (y > 0) {
            for (int w = yStart + 1; w < yEnd; w++) {
                //set x values as they don't change
                temp1XStart = xStart;
                temp2XStart = xStart;
                temp1XEnd = xEnd;
                temp2XEnd = xEnd;

                //set y values
                //temp 1 is above, temp 2 is below
                temp1YStart = yStart;
                temp1YEnd = w;

                temp2YStart = w;
                temp2YEnd = yEnd;

                vertSplit = false;

                //find area of each result
                int temp1XArea = ((temp1XEnd) - (temp1XStart));
                int temp1YArea = ((temp1YEnd) - (temp1YStart));
                int temp2XArea = ((temp2XEnd) - (temp2XStart));
                int temp2YArea = ((temp2YEnd) - (temp2YStart));

                //subtract current chunk of land from value
                int tempLandValue = currentLandValue - getLandPrice(x,y);
                //for each split, add new value
                tempLandValue = tempLandValue + (getLandPrice(temp1XArea, temp1YArea) + getLandPrice(temp2XArea, temp2YArea));
                //reduce cost of split
                tempLandValue = tempLandValue - subdivideCost(vertSplit, x, y);

                if (tempLandValue > currentHighestValue) {
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

            //checks to see if result is the same as what was given
            if (currentHighestValue == currentLandValue)
            {
                return;
            }
            else {

                splitCounter++;
                //record current splits in data structure TODO

                //apply to the landplot - the land to the bottom right of the split is the new increment up one
                for (int a = (result2XStart); a < result2XEnd; a++) {
                    for (int b = (result2YStart); b < result2YEnd; b++) {
                        landPlot[b][a] = splitCounter;
                    }
                }
                currentLandValue = currentHighestValue;
                System.out.println(splitCounter);
                System.out.println(currentLandValue);
                //debug code here TODO for easier finding
                print2D(landPlot);


                //put snapshot of this and templandvalue in hashmap TODO

                //then do recursive method with our resultant splits
                exactMethod(result1XStart, result1XEnd, result1YStart, result1YEnd);
                exactMethod(result2XStart, result2XEnd, result2YStart, result2YEnd);
            }
        }




        //if result1x = x and result1y = y, then no split, and return


    //print out array for debugging

    private int getLandPrice(int x, int y)
    {
        if((x == 0) || (y == 0))
        {
            return 0;
        }
        return landValue[x-1][y-1];

    }


//    private int getLandPrice(int x, int y)
//    {
////        System.out.println(landValue[x][y]);
//        return landValue[x][y];
//    }

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
            result = y;
        }
        else
        {
            result = x;
        }
        return (result * cost);
    }

    //gui
    public static void GUI() {
        final int rows = 3;
        final int col = 6;

        int[][] storeSplitData = {{500, 0, 0, 0, 0, 0}, {1, 1, 1, 1, 1, 1}, {2, 3, 4, 4, 4, 5}, {2, 3, 4, 4, 4, 6}};
        int[] row1 = storeSplitData[1];
        int[] row2 = storeSplitData[2];
        int[] row3 = storeSplitData[3];
        int[] value = storeSplitData[0];


        JFrame frame = new JFrame("GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        frame.setLayout(new GridLayout(0, 6, 0, 0));
        JLabel label1 = new JLabel(String.valueOf(row1[0]), SwingConstants.CENTER);
        JLabel label2 = new JLabel(String.valueOf(row1[1]), SwingConstants.CENTER);
        JLabel label3 = new JLabel(String.valueOf(row1[2]), SwingConstants.CENTER);
        JLabel label4 = new JLabel(String.valueOf(row1[3]), SwingConstants.CENTER);
        JLabel label5 = new JLabel(String.valueOf(row1[4]), SwingConstants.CENTER);
        JLabel label6 = new JLabel(String.valueOf(row1[5]), SwingConstants.CENTER);
        JLabel label7 = new JLabel(String.valueOf(row2[0]), SwingConstants.CENTER);
        JLabel label8 = new JLabel(String.valueOf(row2[1]), SwingConstants.CENTER);
        JLabel label9 = new JLabel(String.valueOf(row2[2]), SwingConstants.CENTER);
        JLabel label10 = new JLabel(String.valueOf(row2[3]), SwingConstants.CENTER);
        JLabel label11 = new JLabel(String.valueOf(row2[4]), SwingConstants.CENTER);
        JLabel label12 = new JLabel(String.valueOf(row2[5]), SwingConstants.CENTER);
        JLabel label13 = new JLabel(String.valueOf(row3[0]), SwingConstants.CENTER);
        JLabel label14 = new JLabel(String.valueOf(row3[1]), SwingConstants.CENTER);
        JLabel label15 = new JLabel(String.valueOf(row3[2]), SwingConstants.CENTER);
        JLabel label16 = new JLabel(String.valueOf(row3[3]), SwingConstants.CENTER);
        JLabel label17 = new JLabel(String.valueOf(row3[4]), SwingConstants.CENTER);
        JLabel label18 = new JLabel(String.valueOf(row3[5]), SwingConstants.CENTER);
        JLabel label19 = new JLabel("Value: " + String.valueOf(value[0]), SwingConstants.CENTER);

        Border border = BorderFactory.createLineBorder(Color.black);
        frame.add(label1);
        label1.setBorder(border);
        frame.add(label2);
        label2.setBorder(border);
        frame.add(label3);
        label3.setBorder(border);
        frame.add(label4);
        label4.setBorder(border);
        frame.add(label5);
        label5.setBorder(border);
        frame.add(label6);
        label6.setBorder(border);
        frame.add(label7);
        label7.setBorder(border);
        frame.add(label8);
        label8.setBorder(border);
        frame.add(label9);
        label9.setBorder(border);
        frame.add(label10);
        label10.setBorder(border);
        frame.add(label11);
        label11.setBorder(border);
        frame.add(label12);
        label12.setBorder(border);
        frame.add(label13);
        label13.setBorder(border);
        frame.add(label14);
        label14.setBorder(border);
        frame.add(label15);
        label15.setBorder(border);
        frame.add(label16);
        label16.setBorder(border);
        frame.add(label17);
        label17.setBorder(border);
        frame.add(label18);
        label18.setBorder(border);
        frame.add(label19);


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

    public void updateArray(int xStart, int xEnd, int yStart, int yEnd, int splitCount)
    {
        //record current splits in data structure TODO

        //apply to the landplot - the land to the bottom right of the split is the new increment up one
        for (int a = (xStart); a < xEnd; a++) {
            for (int b = (yStart); b < yEnd; b++) {
                landPlot[b][a] = splitCount;
            }
        }
        System.out.println(splitCount);
        //debug code here TODO for easier finding
        print2D(landPlot);
    }


}
