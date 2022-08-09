package models;

import org.junit.jupiter.api.Test;

import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    @Test
    void creation(){
        Restaurant restaurant = new Restaurant("restaurantName", new File("./src/main/resources/menus/금정회관.csv"), new File(""), 3500);
    }

}
