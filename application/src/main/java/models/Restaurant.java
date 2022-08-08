package models;

// TODO : 메뉴를 참조해서 저장.
// 식당의 이름과 식당의 메뉴가 저장된 파일 경로 참조


import loader.MenuLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Restaurant {


    private String restaurantName;
    private File file;
    private int foodPrice;

    public Restaurant(String restaurantName, File file, int foodPrice) {
        this.restaurantName = restaurantName;
        this.file = file;
        this.foodPrice = foodPrice;
    }

    public String[] categories() throws FileNotFoundException {
        MenuLoader menuLoader = new MenuLoader();

        Scanner scanner = new Scanner(file);

        String line = scanner.nextLine();

        String[] categories = menuLoader.parseStringArray(line);

        return categories;
    }

    public String name() {
        return restaurantName;
    }

    public int price() {
        return foodPrice;
    }
}
