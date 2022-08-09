package utils;

import models.Menu;
import models.Nutrition;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    @Test
    void parseMenus() throws FileNotFoundException {
        Parser parser = new Parser();

        Menu parsedMenu = parser.parseMenu(new File("./src/main/resources/menus/금정회관.csv"));

        assertEquals(new Menu("백미밥", "순살치킨",
                "카레", "쇠고기미역국", "배추김치"), parsedMenu);
    }

    @Test
    void parseNutrition() throws FileNotFoundException {
        Parser parser = new Parser();

        Nutrition nutrition = parser.parseNutrition(
                new File("/Users/jingwook/Desktop/study/programming/megaptera/web-02-project01-JiNookk" +
                        "/application/src/main/resources/nutritions/금정회관영양성분.csv"));

        assertEquals(new Nutrition(10, 0, 15, 16, 5, 252, "금정회관"), nutrition);
    }

    @Test
    void parseIntArray() {
        Parser parser = new Parser();

        int[] intArray = parser.parseIntArray("1,2,금정회관");

        assertArrayEquals(new int[]{1, 2, 0}, intArray);
    }

}