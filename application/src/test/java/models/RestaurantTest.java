package models;

import org.junit.jupiter.api.Test;

import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    @Test
    void creation(){
        Restaurant restaurant = new Restaurant("cafeteriaName",
                new Menu("","","","","",""),
                new Nutrition(0,0,0,0,0,0),
                3500);
    }

}
