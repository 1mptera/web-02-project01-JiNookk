package models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    private final SystemStatus status = new SystemStatus();

    @Test
    void creation() {
        Restaurant restaurant = new Restaurant("cafeteriaName",
                List.of(),
                List.of(),
                3500);
    }

//    @Test
//    void selectNoDate(){
//        Restaurant restaurant = new Restaurant("금정회관",null,null,3500);
//
//        Menu menu = restaurant.selectMenu(null);
//
//        assertNull(menu);
//    }


    @Test
    void select220808() {
        List<Menu> menus = new ArrayList<>();

        Menu geumjeong = new Menu("백미밥", "순살치킨", "카레", "쇠고기미역국", "배추김치", "220810");
        menus.add(geumjeong);

        Restaurant restaurant = new Restaurant(
                "금정회관",
                menus,
                null,
                3500
        );

        Menu menu = restaurant.selectMenu();

//        assertNull(menu.date());
//        assertNull(status.date());

        assertEquals(geumjeong, menu);
    }

    @Test
    void select220810() {
        Menu geumjeong220808 = new Menu("백미밥", "순살치킨", "카레", "쇠고기미역국", "배추김치", "220808");
        Menu geumjeong220809 = new Menu("백미밥", "통살새우가스", "우동청경채볶음", "모듬어묵국", "배추김치", "220809");
        Menu geumjeong220810 = new Menu("백미밥", "도톰함박", "두부조림", "쇠고기무국", "배추김치", "220810");
        Menu geumjeong220811 = new Menu("백미밥","치킨가스","감자조림","오이미역냉국","배추김치","220811");
        Menu geumjeong220812 = new Menu("백미밥","됸육장조림","계란찜","김치만두국","배추김치","220812");

        List<Menu> menus = List.of(
                geumjeong220808,
                geumjeong220809,
                geumjeong220810,
                geumjeong220811,
                geumjeong220812
        );

        Nutrition geumjeongNutrition220808 = new Nutrition(10,0,15,16,5,252);
        Nutrition geumjeongNutrition220809 = new Nutrition(17,3,15,0,0,125);
        Nutrition geumjeongNutrition220810 = new Nutrition(14,6,12,9,3,187);
        Nutrition geumjeongNutrition220811 = new Nutrition(12,1,20,16,4,285);
        Nutrition geumjeongNutrition220812 = new Nutrition(3,1,16,10,3,166);

        List<Nutrition> nutritions = List.of(
                geumjeongNutrition220808,
                geumjeongNutrition220809,
                geumjeongNutrition220810,
                geumjeongNutrition220811,
                geumjeongNutrition220812
        );

        Restaurant restaurant = new Restaurant(
                "금정회관",
                menus,
                nutritions,
                3500
        );

        status.setDate("220810"); // textField의 getText로 받아올 것!

        Menu menu = restaurant.selectMenu();

        Nutrition nutrition = restaurant.selectNutrition();

        assertEquals(geumjeong220810, menu);
        assertEquals(geumjeongNutrition220810, nutrition);
    }
}
