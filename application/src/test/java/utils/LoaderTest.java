package utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoaderTest {
    @Test
    void parseIntArray() {
        Loader loader = new Loader();

        int[] intArray = loader.parseIntArray("1,2,3");

        assertArrayEquals(new int[]{1, 2, 3}, intArray);
    }
}
