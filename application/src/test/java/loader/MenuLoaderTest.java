package loader;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuLoaderTest {
    @Test
    void parseNoString() {
        MenuLoader menuLoader = new MenuLoader();

        String line = "";

        String[] parsedStringArray = menuLoader.parseStringArray(line);

        assertArrayEquals(new String[]{""}, parsedStringArray);
    }

    @Test
    void parseOneString() {
        MenuLoader menuLoader = new MenuLoader();

        String line = "백미밥";

        String[] parsedStringArray = menuLoader.parseStringArray(line);

        assertArrayEquals(new String[]{"백미밥"}, parsedStringArray);
    }

    @Test
    void parseStrings() {
        MenuLoader menuLoader = new MenuLoader();

        String line = "백미밥,순살치킨/갈비맛소스";

        String[] parsedStringArray = menuLoader.parseStringArray(line);

        assertArrayEquals(new String[]{"백미밥","순살치킨/갈비맛소스"}, parsedStringArray);
    }
}