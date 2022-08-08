package models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {
    @Test
    void creation (){
        Menu menu = new Menu("rice", "mainMenu","sideMenu","soup" , "gimchi");
    }


    @Test
    void findGeumJeongNutrition(){
        Menu menu = new Menu("","순살치킨/갈비맛소스","","","");

        Nutrition nutrition = menu.findNutrition();

        assertNotNull(nutrition);

        assertEquals(new Nutrition(10, 0, 15, 16, 5, 252), nutrition);
    }
}