package utils;

import models.Menu;
import models.Nutrition;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Loader {

    private Scanner scanner;

    public List<Menu> loadMenus(File file) throws FileNotFoundException {
        List<Menu> menus = new ArrayList<>();

        scanner = new Scanner(file);

        while (scanner.hasNextLine()){
            String line = scanner.nextLine();

            String[] categories = line.split(",");

            Menu menu = new Menu(categories[0], categories[1], categories[2], categories[3], categories[4],categories[5]);

            menus.add(menu);
        }

        return menus;
    }

    public List<Nutrition> loadNutritions(File nutritionFile) throws FileNotFoundException {
        List<Nutrition> nutritions =  new ArrayList<>();

        scanner = new Scanner(nutritionFile);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            int[] categories = parseIntArray(line);

            Nutrition nutrition = new Nutrition(categories[0], categories[1], categories[2],
                    categories[3], categories[4], categories[5]);

            nutritions.add(nutrition);
        }
        return nutritions;
    }

    public int[] parseIntArray(String line) {
        String [] words = line.split(",");

        int[] intArray = new int[words.length];

        for (int i = 0 ; i < intArray.length ; i +=1){
            intArray[i] = Integer.parseInt(words[i]);
        }

        return intArray;
    }
}
