package models;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    @Test
    void creation(){
        Restaurant restaurant = new Restaurant("restaurantName", new File("./src/main/resources/menus/금정회관.csv"), new File(""), 3500);
    }

    @Test
    void geumjeongCategories() throws FileNotFoundException {
        Restaurant restaurant = new Restaurant("금정회관", new File("./src/main/resources/menus/금정회관.csv"), new File("./src/main/resources/menus/금정회관.csv"), 3500);

        String[] categories = restaurant.categories();

        assertArrayEquals(new String[]{"백미밥","순살치킨/갈비맛소스","카레","쇠고기미역국","배추김치"}, categories);
    }

//    @Test
//    void studentHallCategories() throws FileNotFoundException {
//        Restaurant restaurant = new Restaurant("학생회관",new File("./src/main/resources/학생회관.csv"));
//
//        String[] categories = restaurant.categories();
//
//        assertArrayEquals(new String[]{220808,백미밥,청양풍제육볶음,오징어가스/머스터드/떡볶이,버섯들깨탕,배추김치/얼갈이겉절이}, categories);
//    }
//
//    @Test
//    void geumjeongCategories() throws FileNotFoundException {
//        Restaurant restaurant = new Restaurant("교직원식당",new File("./src/main/resources/교직원식당.csv"));
//
//        String[] categories = restaurant.categories();
//
//        assertArrayEquals(new String[]{220808,백미밥,생선가스,소면야채무침+양상추토마토샐러드,반계탕,배추김치}, categories);
//    }
}