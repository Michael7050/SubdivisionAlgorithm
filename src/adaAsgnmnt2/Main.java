package adaAsgnmnt2;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Arrays;
import java.util.*;

public class Main
{
    //set land values for 6x6
    private static final int[][] landValue = {
            {20, 40, 100, 130, 150, 200},
            {40, 140, 250, 320, 400, 450},
            {100, 250, 350, 420, 450, 500},
            {130, 320, 420, 500, 600, 700},
            {150, 400, 450, 600, 700, 800},
            {200, 450, 500, 700, 800, 900}};

    //initialise subdividing cost, land area and land plot 2D array
    private final static int cost = 20;
    static int height = 6;
    static int length = 6;
    private int initialLandValue = 0;
    public int currentLandValue = 0;
    private int splitCounter = 0;
    int[][] landPlot = new int[height][length];
    int currentBestValue = 0;
    int[][] currentBestPlot = new int[height][length];

    public static void main(String[] args)
    {
        Main main = new Main();
        System.out.println("Press 1 for bruteforce, \nPress 2 for greedy, \nPress 3 for exact");
        Scanner scanner = new Scanner(System.in);
        String run = scanner.next();
        if(Objects.equals(run, "1")){
            main.bruteForce(length, height);
        }
        if(Objects.equals(run, "2")){
            main.greedySolution(length, height);
        }
        if(Objects.equals(run, "3")){
            main.exactSolution(length, height);
        }
    }

    //Brute Force
    public void bruteForce(int x, int y)
    {
        //this should be a working brute force method, that goes through each combination i think.
        currentLandValue = getLandPrice(x, y);
        initialLandValue = currentLandValue;
        splitCounter = 0;
        int[][] floatingLandPlot = new int[y][x];
        currentBestValue = currentLandValue;

        int xStart = 0;
        int xEnd = x;
        int yStart = 0;
        int yEnd = y;

        updateArray(xStart, xEnd, yStart, yEnd, splitCounter);
        System.out.println(currentLandValue);
        System.out.println("Starting recursion");

        bruteForceMethod(xStart, xEnd, yStart, yEnd, splitCounter, currentLandValue, floatingLandPlot);

        System.out.println("Current best Value, and Plot");
        System.out.println(currentBestValue);
        print2D(currentBestPlot);

        int[] row1 = currentBestPlot[0];
        int[] row2 = currentBestPlot[1];
        int[] row3 = currentBestPlot[2];
        int[] row4 = currentBestPlot[3];
        int[] row5 = currentBestPlot[4];
        int[] row6 = currentBestPlot[5];
        int[] value = {currentBestValue, 0, 0, 0, 0, 0};
        GUI(row1, row2, row3, row4, row5, row6, value);
    }

    //greedy
    private void greedySolution(int x, int y){

        initialLandValue = getLandPrice(x, y);
        currentLandValue = initialLandValue;

        int xStart = 0;
        int yStart = 0;
        greedyMethod(xStart, x, yStart, y);

        int[] row1 = landPlot[0];
        int[] row2 = landPlot[1];
        int[] row3 = landPlot[2];
        int[] row4 = landPlot[3];
        int[] row5 = landPlot[4];
        int[] row6 = landPlot[5];
        int[] value = {currentLandValue, 0, 0, 0, 0, 0};
        GUI(row1, row2, row3, row4, row5, row6, value);
    }

    //exact
    private void exactSolution(int x, int y) {

        initialLandValue = getLandPrice(x, y);
        currentLandValue = initialLandValue;

        int xStart = 0;
        int yStart = 0;
        exactMethod(xStart, x, yStart, y);

        int[] row1 = landPlot[0];
        int[] row2 = landPlot[1];
        int[] row3 = landPlot[2];
        int[] row4 = landPlot[3];
        int[] row5 = landPlot[4];
        int[] row6 = landPlot[5];
        int[] value = {currentLandValue, 0, 0, 0, 0, 0};
        GUI(row1, row2, row3, row4, row5, row6, value);
    }

    private void exactMethod(int xStart, int xEnd, int yStart, int yEnd) {
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
        int x = ((xEnd) - (xStart));
        int y = ((yEnd) - (yStart));

        //go through vertical splits
        if (x > 1) //checks for if land is wider than one
        {
            //this runs through vertical splits of the given chunk of land
            for (int l = xStart+1; l < xEnd; l++) {
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
        if (currentHighestValue != currentLandValue) {

            splitCounter++;
            //apply to the landplot - the land to the bottom right of the split is the new increment up one
            for (int a = (result2XStart); a < result2XEnd; a++) {
                for (int b = (result2YStart); b < result2YEnd; b++) {
                    landPlot[b][a] = splitCounter;
                }
            }
            currentLandValue = currentHighestValue;

            System.out.println(currentLandValue);
            print2D(landPlot);

            //then do recursive method with our resultant splits
            exactMethod(result1XStart, result1XEnd, result1YStart, result1YEnd);
            exactMethod(result2XStart, result2XEnd, result2YStart, result2YEnd);
        }
    }


    private void bruteForceMethod(int xStart, int xEnd, int yStart, int yEnd, int splitCount, int value, int[][] floatingLandPlot)
    {
        splitCount++;
        splitCounter++;

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
            for (int l = xStart + 1; l < xEnd; l++)
            {
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
                int tempLandValue = value - getLandPrice(x, y);
                //for each split, add new value
                tempLandValue = tempLandValue + (getLandPrice(result1XArea, result1YArea) + getLandPrice(result2XArea, result2YArea));
                //reduce cost of split
                tempLandValue = tempLandValue - subdivideCost(vertSplit, x, y);

                //function here that prints current land array and value to arraylist
                System.out.println("Splitting vertically first: ");
                System.out.println("Land value: " + tempLandValue);
                updateFloatingArray(result2XStart, result2XEnd, result2YStart, result2YEnd, result1XStart, result1XEnd, result1YStart, result1YEnd, floatingLandPlot, tempLandValue);
                print2D(floatingLandPlot);
                System.out.println("Land value: " + tempLandValue);
                System.out.println();

                //record new values in arraylist(?) TODO

                //bruteForceMethod(result1XStart, result1XEnd, result1YStart, result1YEnd, splitCount, tempLandValue, floatingLandPlot);
                bruteForceMethod(result2XStart, result2XEnd, result2YStart, result2YEnd, splitCount, tempLandValue, floatingLandPlot);
            }
        }

        //go through horizontal splits now
        if (y > 1)
        {
            for (int w = yStart + 1; w < yEnd; w++)
            {
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
                int tempLandValue = value - getLandPrice(x, y);
                //for each split, add new value
                tempLandValue = tempLandValue + (getLandPrice(result1XArea, result1YArea) + getLandPrice(result2XArea, result2YArea));
                //reduce cost of split
                tempLandValue = tempLandValue - subdivideCost(vertSplit, x, y);

                //function here that prints current landarray and value
                System.out.println("Splitting Horizontally second:");
                updateFloatingArray(result2XStart, result2XEnd, result2YStart, result2YEnd, result1XStart, result1XEnd, result1YStart, result1YEnd, floatingLandPlot, tempLandValue);
                print2D(floatingLandPlot);
                System.out.println("Land value: " + tempLandValue);
                System.out.println();

                //bruteForceMethod(result1XStart, result1XEnd, result1YStart, result1YEnd, splitCount, tempLandValue, floatingLandPlot);
                bruteForceMethod(result2XStart, result2XEnd, result2YStart, result2YEnd, splitCount, tempLandValue, floatingLandPlot);
            }
        }
    }

    //Greedy Approach
    private void greedyMethod(int xStart, int xEnd, int yStart, int yEnd) {
        //this is the greedy method
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
        int x = ((xEnd) - (xStart));
        int y = ((yEnd) - (yStart));


        //go through vertical splits
        if (x > 1) //checks for if land is wider than one
        {
            //this runs through vertical splits of the given chunk of land
            for (int l = xStart + 1; l < xEnd; l++) {
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
                int tempLandValue = currentLandValue - getLandPrice(x, y);
                //for each split, add new value
                tempLandValue = tempLandValue + (getLandPrice(temp1XArea, temp1YArea) + getLandPrice(temp2XArea, temp2YArea));
                //reduce cost of split
                tempLandValue = tempLandValue - subdivideCost(vertSplit, x, y);

                //if the split is the current best, save current split results to current best
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
                int tempLandValue = currentLandValue - getLandPrice(x, y);
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
        if (currentHighestValue == currentLandValue) {

        } else {
            //checks to see if result is the same as what was given
            if (currentHighestValue == currentLandValue) {
                return;
            } else {

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
                greedyMethod(result1XStart, result1XEnd, result1YStart, result1YEnd);
                greedyMethod(result2XStart, result2XEnd, result2YStart, result2YEnd);
            }
        }
    }
    //print out array for debugging
    private int getLandPrice(int x, int y)
    {
        if ((x == 0) || (y == 0))
        {
            return 0;
        }
        return landValue[x - 1][y - 1];
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
    public void GUI(int[]row1, int[]row2, int[]row3, int[]row4, int[]row5, int[]row6, int[]value) {

        JFrame frame = new JFrame("GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        frame.setLayout(new GridLayout(0, height, 0, 0));
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
        JLabel label19 = new JLabel(String.valueOf(row4[0]), SwingConstants.CENTER);
        JLabel label20 = new JLabel(String.valueOf(row4[1]), SwingConstants.CENTER);
        JLabel label21 = new JLabel(String.valueOf(row4[2]), SwingConstants.CENTER);
        JLabel label22 = new JLabel(String.valueOf(row4[3]), SwingConstants.CENTER);
        JLabel label23 = new JLabel(String.valueOf(row4[4]), SwingConstants.CENTER);
        JLabel label24 = new JLabel(String.valueOf(row4[5]), SwingConstants.CENTER);
        JLabel label25 = new JLabel(String.valueOf(row5[0]), SwingConstants.CENTER);
        JLabel label26 = new JLabel(String.valueOf(row5[1]), SwingConstants.CENTER);
        JLabel label27 = new JLabel(String.valueOf(row5[2]), SwingConstants.CENTER);
        JLabel label28 = new JLabel(String.valueOf(row5[3]), SwingConstants.CENTER);
        JLabel label29 = new JLabel(String.valueOf(row5[4]), SwingConstants.CENTER);
        JLabel label30 = new JLabel(String.valueOf(row5[5]), SwingConstants.CENTER);
        JLabel label31 = new JLabel(String.valueOf(row6[0]), SwingConstants.CENTER);
        JLabel label32 = new JLabel(String.valueOf(row6[1]), SwingConstants.CENTER);
        JLabel label33 = new JLabel(String.valueOf(row6[2]), SwingConstants.CENTER);
        JLabel label34 = new JLabel(String.valueOf(row6[3]), SwingConstants.CENTER);
        JLabel label35 = new JLabel(String.valueOf(row6[4]), SwingConstants.CENTER);
        JLabel label36 = new JLabel(String.valueOf(row6[5]), SwingConstants.CENTER);
        JLabel totalvalue = new JLabel(String.valueOf(value[0]), SwingConstants.CENTER);

        Border border = BorderFactory.createLineBorder(Color.black);
        frame.add(label1);
        label1.setBorder(border);
        label1.setBackground(getColour(row1[0]));
        label1.setOpaque(true);
        frame.add(label2);
        label2.setBorder(border);
        label2.setBackground(getColour(row1[1]));
        label2.setOpaque(true);
        frame.add(label3);
        label3.setBorder(border);
        label3.setBackground(getColour(row1[2]));
        label3.setOpaque(true);
        frame.add(label4);
        label4.setBorder(border);
        label4.setBackground(getColour(row1[3]));
        label4.setOpaque(true);
        frame.add(label5);
        label5.setBorder(border);
        label5.setBackground(getColour(row1[4]));
        label5.setOpaque(true);
        frame.add(label6);
        label6.setBorder(border);
        label6.setBackground(getColour(row1[5]));
        label6.setOpaque(true);
        frame.add(label7);
        label7.setBorder(border);
        label7.setBackground(getColour(row2[0]));
        label7.setOpaque(true);
        frame.add(label8);
        label8.setBorder(border);
        label8.setBackground(getColour(row2[1]));
        label8.setOpaque(true);
        frame.add(label9);
        label9.setBorder(border);
        label9.setBackground(getColour(row2[2]));
        label9.setOpaque(true);
        frame.add(label10);
        label10.setBorder(border);
        label10.setBackground(getColour(row2[3]));
        label10.setOpaque(true);
        frame.add(label11);
        label11.setBorder(border);
        label11.setBackground(getColour(row2[4]));
        label11.setOpaque(true);
        frame.add(label12);
        label12.setBorder(border);
        label12.setBackground(getColour(row2[5]));
        label12.setOpaque(true);
        frame.add(label13);
        label13.setBorder(border);
        label13.setBackground(getColour(row3[0]));
        label13.setOpaque(true);
        frame.add(label14);
        label14.setBorder(border);
        label14.setBackground(getColour(row3[1]));
        label14.setOpaque(true);
        frame.add(label15);
        label15.setBorder(border);
        label15.setBackground(getColour(row3[2]));
        label15.setOpaque(true);
        frame.add(label16);
        label16.setBorder(border);
        label16.setBackground(getColour(row3[3]));
        label16.setOpaque(true);
        frame.add(label17);
        label17.setBorder(border);
        label17.setBackground(getColour(row3[4]));
        label17.setOpaque(true);
        frame.add(label18);
        label18.setBorder(border);
        label18.setBackground(getColour(row3[5]));
        label18.setOpaque(true);
        frame.add(label19);
        label19.setBorder(border);
        label19.setBackground(getColour(row4[0]));
        label19.setOpaque(true);
        frame.add(label20);
        label20.setBorder(border);
        label20.setBackground(getColour(row4[1]));
        label20.setOpaque(true);
        frame.add(label21);
        label21.setBorder(border);
        label21.setBackground(getColour(row4[2]));
        label21.setOpaque(true);
        frame.add(label22);
        label22.setBorder(border);
        label22.setBackground(getColour(row4[3]));
        label22.setOpaque(true);
        frame.add(label23);
        label23.setBorder(border);
        label23.setBackground(getColour(row4[4]));
        label23.setOpaque(true);
        frame.add(label24);
        label24.setBorder(border);
        label24.setBackground(getColour(row4[5]));
        label24.setOpaque(true);
        frame.add(label25);
        label25.setBorder(border);
        label25.setBackground(getColour(row5[0]));
        label25.setOpaque(true);
        frame.add(label26);
        label26.setBorder(border);
        label26.setBackground(getColour(row5[1]));
        label26.setOpaque(true);
        frame.add(label27);
        label27.setBorder(border);
        label27.setBackground(getColour(row5[2]));
        label27.setOpaque(true);
        frame.add(label28);
        label28.setBorder(border);
        label28.setBackground(getColour(row5[3]));
        label28.setOpaque(true);
        frame.add(label29);
        label29.setBorder(border);
        label29.setBackground(getColour(row5[4]));
        label29.setOpaque(true);
        frame.add(label30);
        label30.setBorder(border);
        label30.setBackground(getColour(row5[5]));
        label30.setOpaque(true);
        frame.add(label31);
        label31.setBorder(border);
        label31.setBackground(getColour(row6[0]));
        label31.setOpaque(true);
        frame.add(label32);
        label32.setBorder(border);
        label32.setBackground(getColour(row6[1]));
        label32.setOpaque(true);
        frame.add(label33);
        label33.setBorder(border);
        label33.setBackground(getColour(row6[2]));
        label33.setOpaque(true);
        frame.add(label34);
        label34.setBorder(border);
        label34.setBackground(getColour(row6[3]));
        label34.setOpaque(true);
        frame.add(label35);
        label35.setBorder(border);
        label35.setBackground(getColour(row6[4]));
        label35.setOpaque(true);
        frame.add(label36);
        label36.setBorder(border);
        label36.setBackground(getColour(row6[5]));
        label36.setOpaque(true);
        frame.add(totalvalue);
    }

    //code from stackoverflow to print out 2d array for bug testing.
    public static void print2D(int[][] mat)
    {
        for (int[] row : mat)
        {
            System.out.println(Arrays.toString(row));
        }
    }

    public void updateArray(int xStart, int xEnd, int yStart, int yEnd, int splitCount)
    {
        //record current splits in data structure

        //apply to the landplot - the land to the bottom right of the split is the new increment up one
        for (int a = (xStart); a < xEnd; a++)
        {
            for (int b = (yStart); b < yEnd; b++)
            {
                landPlot[b][a] = splitCount;
            }
        }
        System.out.println("Split depth " + splitCount);
        //debug code here
        print2D(landPlot);
    }

    //change this so it updates the full amount of the split?
    public int[][] updateFloatingArray(int xStart, int xEnd, int yStart, int yEnd, int x2Start, int x2End, int y2Start, int y2End, int[][] floatingLandPlot, int value)
    {
        splitCounter++;
        int split2 = splitCounter + 1;
        //record current splits in data structure

        //apply to the landplot - the land to the bottom right of the split is the new increment up one
        for (int a = (xStart); a < xEnd; a++)
        {
            for (int b = (yStart); b < yEnd; b++)
            {
                floatingLandPlot[b][a] = splitCounter;
            }
        }
        for (int a = (x2Start); a < x2End; a++)
        {
            for (int b = (y2Start); b < y2End; b++)
            {
                floatingLandPlot[b][a] = split2;
            }
        }

        if (value > currentBestValue)
        {
            currentBestValue = value;
            currentBestPlot = Arrays.stream(floatingLandPlot).map(int[]::clone).toArray(int[][]::new);;
        }

        System.out.println("Split depth " + splitCounter);
        //debug code here
        return floatingLandPlot;
    }


    private static Color getColour(int a) {
        if(a == 0) {
            return new Color(255, 255, 0);
        }
        if(a == 1) {
            return new Color(255, 0, 0);
        }
        if(a == 2) {
            return new Color(255, 128, 0);
        }
        if(a == 3) {
            return new Color(125, 255, 0);
        }
        if(a == 4) {
            return new Color(0, 255, 255);
        }
        if(a == 5) {
            return new Color(0, 128, 255);
        }
        if(a == 6) {
            return new Color(255, 0, 127);
        }
        if(a == 7) {
            return new Color(100, 0, 0);
        }
        if(a == 8) {
            return new Color(0, 100, 0);
        }
        if(a == 9) {
            return new Color(0, 10, 255);
        }
        if(a == 10) {
            return new Color(128, 100, 0);
        }
        if(a == 11) {
            return new Color(180, 100, 50);
        }
        if(a == 12) {
            return new Color(153, 0, 153);
        }
        if(a == 13) {
            return new Color(229, 204, 255);
        }
        if(a == 14) {
            return new Color(229, 255, 204);
        }
        if(a == 15) {
            return new Color(64, 64, 64);
        }
        if(a == 16) {
            return new Color(255, 100, 60);
        }
        if(a == 17) {
            return new Color(153, 204, 255);
        }
        if(a == 118) {
            return new Color(192, 192, 192);
        }
        if(a == 19) {
            return new Color(178, 255, 102);
        }
        if(a == 20) {
            return new Color(51, 0, 102);
        }
        else return Color.white;
    }
}
