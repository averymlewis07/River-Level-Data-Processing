import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner inputFile = new Scanner(new File("data"));

        double waterLevel = 0;

        // The overall month that we are on
        String bigMonth = "2024-09";

        double max = waterLevel;
        double min = 100; // This is just set to a random high number so that the first currentInt that is tested will be guaranteed to be lower than this value.
                          // If this was set to 0 instead of a high number, we would run into a lot of issues.

        double total = 0;
        double average = 0;
        int n = 0;

        String maxTime = "";
        String minTime = "";

        ArrayList<String> fourTimes = new ArrayList<>();

        while(inputFile.hasNextLine()){
            String input = inputFile.nextLine();

            String[] data = input.split(",");

            // The month for one data variable that the cursor is currently checking
            String timestamp = data[1];
            String[] currentMonth = timestamp.split("-");

            // Establishes currentInt and acts in case of a gap
            if(data[2].isEmpty()){
                System.out.println("* Gap detected! Expected " + timestamp + ",  Got " + timestamp + " with missing water level");
            } else {
                waterLevel = Double.parseDouble(data[2]);
            }


            // Finds max
            if(waterLevel > max){
                max = waterLevel;
                maxTime = data[1];
            }

            // Finds min
            if(waterLevel < min && waterLevel >= 0){
                min = waterLevel;
                minTime = data[1];
            }

            // Finds water levels that are 4x the lowest(min)
            if(waterLevel >= 4*min){
                String[] data2 = input.split(",| ");
                if(!fourTimes.contains(data2[1])) {
                    fourTimes.add(data2[1]);
                }
            }


            if(timestamp.contains(bigMonth)){
                total = total + waterLevel;
                n = n + 1;
            } else {
                average = total/n;
                System.out.println("Average of " + bigMonth + ": "+ average);

                n = 0;
                total = 0;
                bigMonth = currentMonth[0] + "-" + currentMonth[1];
                timestamp = data[1];
            }
       }
        
        System.out.println("Average of " + bigMonth + ": "+ average);
        System.out.println("\n" + "Highest Water Level: " + max + "; Date/Time: " + maxTime);
        System.out.println("Lowest (Positive) Water Level: " + min + "; Date/Time: " + minTime);
        System.out.println("Day/Times when the water level is 4x the lowest: " + fourTimes);
    }
}