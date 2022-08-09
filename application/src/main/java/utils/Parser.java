package utils;

import models.Menu;
import models.Nutrition;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {

    private Scanner scanner;

    public Menu parseMenu(File file) throws FileNotFoundException {
        scanner = new Scanner(file);

        String line = scanner.nextLine();

        String[] categories = line.split(",");

        Menu menu = new Menu(categories[0], categories[1], categories[2], categories[3], categories[4]);

        return menu;
    }

    public Nutrition parseNutrition(File nutritionFile) throws FileNotFoundException {
        scanner = new Scanner(nutritionFile);

        String line = scanner.nextLine();

        int[] categories = parseIntArray(line);

        String cafeteriaName = line.split(",")[6];

        Nutrition nutrition = new Nutrition(categories[0], categories[1], categories[2],
                categories[3], categories[4], categories[5],cafeteriaName);

        return nutrition;
    }

    public int[] parseIntArray(String line) {
        String [] words = line.split(",");

        int[] intArray = new int[words.length];

        for (int i = 0 ; i < intArray.length-1 ; i +=1){
            intArray[i] = Integer.parseInt(words[i]);
        }

        return intArray;
    }
}
