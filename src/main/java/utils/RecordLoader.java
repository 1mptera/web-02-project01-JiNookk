package utils;

import models.Menu;
import models.Nutrition;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RecordLoader {
    public static void main(String[] args) throws FileNotFoundException {
        RecordLoader recordLoader = new RecordLoader();

        List<Menu> recorededMenus =  recordLoader.loadMenus();

        for (Menu menu : recorededMenus){
            System.out.println(menu);
        }

        List<Nutrition> recorededNutritions =  recordLoader.loadNutritions();

        for (Nutrition nutrition : recorededNutritions){
            System.out.println(nutrition);
        }
    }


    public void saveMenu(List<Menu> menus) throws IOException {
        FileWriter fileWriter = new FileWriter("./src/main/resources/records/menus/먹었던메뉴.csv");

        for (Menu menu : menus){
            String line = toCsvRowMenu(menu);

            fileWriter.write(line + "\n");
        }

        fileWriter.close();
    }

    public void saveNutrition(List<Nutrition> nutritions) throws IOException {
        FileWriter fileWriter = new FileWriter("./src/main/resources/records/nutritions/영양성분.csv");

        for (Nutrition nutrition : nutritions){
            String line = toCsvRowNutrition(nutrition);

            fileWriter.write(line + "\n");
        }

        fileWriter.close();
    }

    private String toCsvRowNutrition(Nutrition nutrition) {
        return nutrition.carbonHydrate() + "," + nutrition.sugar() + "," + nutrition.protein() + ","
                + nutrition.fat() + "," + nutrition.saturatedFat() + "," + nutrition.calories();
    }

    private String toCsvRowMenu(Menu menu) {
        return menu.rice() + "," + menu.mainMenu() + "," + menu.sideMenu() + ","
                + menu.soup() + "," + menu.gimchi() + "," + menu.date();
    }

    public List<Menu> loadMenus() throws FileNotFoundException {
        List<Menu> loadedMenus = new ArrayList<>();

        File file = new File("./src/main/resources/records/menus/먹었던메뉴.csv");

        Scanner scanner = new Scanner(file);

        while(scanner.hasNextLine()){
            String line = scanner.nextLine();

            String[] words = line.split(",");

            Menu menu = new Menu(words[0], words[1], words[2], words[3], words[4], words[5]);

            loadedMenus.add(menu);
        }

        return loadedMenus;
    }

    public List<Nutrition> loadNutritions() throws FileNotFoundException {
        List<Nutrition> loadedNutritions = new ArrayList<>();

        File file = new File("./src/main/resources/records/nutritions/영양성분.csv");

        Scanner scanner = new Scanner(file);

        while(scanner.hasNextLine()){
            String line = scanner.nextLine();

            String[] words = line.split(",");

            int[] nutritionElements = new int[words.length];

            for (int i = 0; i < nutritionElements.length; i++) {
                nutritionElements[i] = Integer.parseInt(words[i]);
            }

            Nutrition nutrition = new Nutrition(nutritionElements[0],
                    nutritionElements[1],
                    nutritionElements[2],
                    nutritionElements[3],
                    nutritionElements[4],
                    nutritionElements[5]);

            loadedNutritions.add(nutrition);
        }
        return loadedNutritions;
    }
}