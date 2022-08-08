package models;

// TODO : 메뉴를 참조해서 저장.
// 식당의 이름과 식당의 메뉴가 저장된 파일 경로 참조


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Restaurant {

    private String restaurantName;
    private File menuFile;
    private File nutritionFile;
    private int foodPrice;

    public Restaurant(String restaurantName, File menuFile, File nutritionFile, int foodPrice) {
        this.restaurantName = restaurantName;
        this.menuFile = menuFile;
        this.nutritionFile = nutritionFile;
        this.foodPrice = foodPrice;
    }

    public String[] categories() throws FileNotFoundException {


        Scanner scanner = new Scanner(menuFile);

        String line = scanner.nextLine();

        String[] categories =line.split(",");

        return categories;
    }

    public String name() {
        return restaurantName;
    }

    public int price() {
        return foodPrice;
    }

    public File menuFile() {
        return menuFile;
    }

    public File nutritionFile() {
        return nutritionFile;
    }
}
