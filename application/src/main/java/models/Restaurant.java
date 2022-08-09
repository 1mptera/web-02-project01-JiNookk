package models;

// TODO : 메뉴를 참조해서 저장, 영양성분도 참조
// 식당의 이름과 식당의 메뉴가 저장된 파일 경로 참조


import java.io.File;

public class Restaurant {
    public static final String GEUMJEONG = "금정회관";
    public static final String STUDENTHALL = "학생회관";
    public static final String STAFFCAFETERIA = "교직원식당";

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
