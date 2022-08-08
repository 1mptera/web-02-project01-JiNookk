package loader;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuLoaderTest {
    @Test
    void parseNoString() {
        String line = "";

        String[] parsedStringArray = line.split(",");

        assertArrayEquals(new String[]{""}, parsedStringArray);
    }

    @Test
    void parseOneString() {


        String line = "백미밥";

        String[] parsedStringArray = line.split(",");

        assertArrayEquals(new String[]{"백미밥"}, parsedStringArray);
    }

    @Test
    void parseStrings() {
        String line = "백미밥,순살치킨/갈비맛소스";

        String[] parsedStringArray = line.split(",");

        assertArrayEquals(new String[]{"백미밥","순살치킨/갈비맛소스"}, parsedStringArray);
    }
}