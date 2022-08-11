package models;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SystemStatusTest {

    @Test
    void staticTest(){
        SystemStatus systemStatus = new SystemStatus();

        LocalDate now = LocalDate.now();

        String today = now.format(DateTimeFormatter.ofPattern("yyMMdd"));

        assertEquals(today,systemStatus.date());
    }

    @Test
    void today(){
        SystemStatus systemStatus = new SystemStatus();

        LocalDate now = LocalDate.now();

        String today = now.format(DateTimeFormatter.ofPattern("yyMMdd"));

        assertEquals(today,systemStatus.date());
    }


    @Test
    void index0808(){
        SystemStatus systemStatus = new SystemStatus();

        int index = systemStatus.index();

        assertEquals(0,index);
    }

    @Test
    void index0810(){
        SystemStatus systemStatus = new SystemStatus();

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

        systemStatus.setDate("220810");

        systemStatus.initIndex(menus);

        int index = systemStatus.index();

        assertEquals(2,index);
    }

    @Test
    void compareNutritionCounts(){
        SystemStatus systemStatus = new SystemStatus();

        List<Nutrition> recordedNutritions = List.of(
                new Nutrition(0,10,0,0,0,0),
                new Nutrition(0,10,0,0,0,0),
                new Nutrition(0,10,0,0,0,0),
                new Nutrition(0,10,0,0,0,0),
                new Nutrition(0,10,0,0,0,0)
        );

        systemStatus.initNutritionCounts(recordedNutritions);

        assertEquals("당류",systemStatus.compareNutritionCounts());
    }
}