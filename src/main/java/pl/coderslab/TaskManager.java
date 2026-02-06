package pl.coderslab;

import com.sun.jdi.IntegerValue;
import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {

    public static void main(String[] args) {
        String[][] data = readFile("tasks.csv");
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println(ConsoleColors.BLUE + "\nPlease select an option" + ConsoleColors.RESET + "\n add\n remove\n list\n exit");
            String choice = scanner.nextLine();
            switch (choice) {
                case "add":
                    data = addFile(data);
                    writeToFile(data);
                    break;
                case "remove":
                    data = removeFile(data);
                    writeToFile(data);
                    break;
                case "list":
                    listFile(data);
                    break;
                case "exit":
                    System.out.println(ConsoleColors.RED + "Bye Bye!");
                    System.exit(0);
                default:
                    System.out.println("Please select correct option");
            }
        }

    }

    public static String[][] readFile(String filePath) {
        int rows = 0;

        try (Scanner scan = new Scanner(new File(filePath))) {
            while (scan.hasNextLine()) {
                scan.nextLine();
                rows++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("You passed a wrong file");
            return new String[0][0];
        }

        if (rows == 0) {
            System.out.println("The file is empty");
            return new String[0][0];
        }

        String[][] data = new String[rows][3];

        try (Scanner scanner = new Scanner(new File(filePath))) {
            int row = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",\\s*");

                if (parts.length != 3) {
                    System.out.println("Not enough data input");
                    continue;
                }
                data[row] = parts;
                row++;

            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return new String[0][0];
        }
        return data;
    }

    public static void listFile(String[][] arrayData) {
        for (int i = 0; i < arrayData.length; i++) {
            System.out.println();
            System.out.print(i + " : ");
            for (int j = 0; j < 3; j++) {
                System.out.print(arrayData[i][j] + "  ");
            }
        }
    }

    public static String[][] addFile(String[][] arrayData) {
        String[][] addedArray = new String[arrayData.length + 1][3];

        for (int i = 0; i < arrayData.length; i++) {
            addedArray[i] = arrayData[i];
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please add task description");
        String desc = scanner.nextLine();
        System.out.println("Please add task due date");
        String date = scanner.nextLine();
        System.out.println("Is your task important: true/false");
        
        String important = scanner.nextLine();
        addedArray[arrayData.length][0] = desc;
        addedArray[arrayData.length][1] = date;
        addedArray[arrayData.length][2] = important;


    return addedArray;
    }

    public static String[][] removeFile(String[][] arrayData) {
        String[][] removedArray = new String[arrayData.length - 1][3];


        System.out.println("Please select value to remove");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        if (choice < 0 || choice >= arrayData.length){
            System.out.println("Invalid choice");
            return arrayData;
        }

        for (int i = 0; i < choice; i++) {
            removedArray[i] = arrayData[i];
        }
        for (int i = choice + 1; i <= removedArray.length; i++) {
            removedArray[i - 1] = arrayData[i];
        }
        System.out.println("Value was successfully deleted");
    return removedArray;
    }

    public static void writeToFile(String[][] arrayData){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.csv"))){
            for (String[] row : arrayData) {
                if (row != null){
                    String line = String.join(", ", row);
                    writer.write(line);
                    writer.newLine();
                }
            }
        }catch (IOException e) {
            System.out.println("Failed saving");
        }
    }
}
