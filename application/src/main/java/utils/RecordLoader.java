package utils;

import models.Menu;
import models.Nutrition;

import java.io.FileWriter;
import java.io.IOException;

public class RecordLoader {
    public void saveMenu(Menu menu) throws IOException {
        FileWriter fileWriter = new FileWriter("/Users/jingwook/Desktop/study/programming/megaptera/web-02-project01" +
                "-JiNookk/application/src/main/resources/records/menus/먹었던메뉴.csv");

        String line = toCsvRowMenu(menu);

        fileWriter.write(line);

        fileWriter.close();
    }

    public void saveNutrition(Nutrition nutrition) throws IOException {
        FileWriter fileWriter = new FileWriter("/Users/jingwook/Desktop/study/programming/megaptera/web-02-project01" +
                "-JiNookk/application/src/main/resources/records/nutritions/영양성분.csv");

        String line = toCsvRowNutrition(nutrition);

        fileWriter.write(line);

        fileWriter.close();
    }

    private String toCsvRowNutrition(Nutrition nutrition) {
        return nutrition.carbonHydrate() + "," + nutrition.sugar() + "," + nutrition.protein() + ","
                + nutrition.fat() + "," + nutrition.saturatedFat() + "," + nutrition.cafeteriaName();
    }

    private String toCsvRowMenu(Menu menu) {
        return menu.rice() + "," + menu.mainMenu() + "," + menu.sideMenu() + ","
                + menu.soup() + "," + menu.gimchi() + "," + menu.date();
    }
}
