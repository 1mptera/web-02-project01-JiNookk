package models;

// TODO : 메뉴를 참조해서 저장, 영양성분도 참조
// 식당의 이름과 식당의 메뉴가 저장된 파일 경로 참조


import java.io.File;

public class Restaurant {
    public static final String GEUMJEONG = "금정회관";
    public static final String STUDENTHALL = "학생회관";
    public static final String STAFFCAFETERIA = "교직원식당";

    private String restaurantName;
    private Menu menu;
    private Nutrition nutrition;
    private int foodPrice;

    public Restaurant(String restaurantName, Menu menu, Nutrition nutrition, int foodPrice) {
        this.restaurantName = restaurantName;
        this.menu = menu;
        this.nutrition = nutrition;
        this.foodPrice = foodPrice;
    }

    public String name() {
        return restaurantName;
    }

    public int price() {
        return foodPrice;
    }

    public Menu menu() {
        return menu;
    }

    public Nutrition nutrition() {
        return nutrition;
    }

    @Override
    public boolean equals(Object other) {
        Restaurant otherRestaurant = (Restaurant) other;

        return this.restaurantName.equals(otherRestaurant.restaurantName)
                && this.menu.toString().equals(otherRestaurant.menu.toString())
                && this.nutrition.toString().equals(otherRestaurant.nutrition.toString())
                && this.foodPrice == otherRestaurant.foodPrice;
    }

    @Override
    public String toString() {
        return restaurantName + ", " + menu + ", " + nutrition + ", " + foodPrice;
    }

}
