package application;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CafeteriaMenuReccomendatorTest {
    @Test
    void removeListElements(){
        List<String> lists = new ArrayList<>();

        lists.add("1");
        lists.add("2");
        lists.add("3");

        for (int i = 0; i < lists.size(); i++) {
            lists.remove(lists.get(0));
        }

        assertNotNull(lists);
    }
}