package utils;

import models.Nutrition;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SortTest {
    @Test
    void sortNoNutrition() {
        Sort sort = new Sort();

        List<Nutrition> sortedNutritions = sort.sortNutritions(List.of(), "단백질");

        assertEquals(List.of(), sortedNutritions);
    }

    @Test
    void sortOneNutrition() {
        Sort sort = new Sort();

        Nutrition nutrition = new Nutrition(0, 0, 1, 0, 0, 0, "금정회관");

        List<Nutrition> nutritions = new ArrayList<>();
        nutritions.add(nutrition);

        List<Nutrition> sortedNutritions =
                sort.sortNutritions(nutritions, "단백질");

        assertEquals(List.of(nutrition), sortedNutritions);
    }

    @Test
    void index() {
        List<Nutrition> nutritions = List.of(
                new Nutrition(0, 0, 11, 0, 0, 0, "금정회관"),
                new Nutrition(0, 0, 12, 0, 0, 0, "학생회관"),
                new Nutrition(0, 0, 13, 0, 0, 0, "교직원식당")
        );

        Nutrition maximum = new Nutrition(0, 0, 13, 0, 0, 0, "교직원식당");

        int index = nutritions.indexOf(maximum);
        assertEquals(2, index);
    }

    @Test
    void sortNutritions() {
        Sort sort = new Sort();

        List<Nutrition> nutritions = new ArrayList<>();
        nutritions.add(new Nutrition(0, 0, 11, 0, 0, 0, "금정회관"));
        nutritions.add(new Nutrition(0, 0, 12, 0, 0, 0, "학생회관"));
        nutritions.add(new Nutrition(0, 0, 13, 0, 0, 0, "교직원식당"));
        nutritions.add(new Nutrition(0, 0, 14, 0, 0, 0, "금정회관"));
        nutritions.add(new Nutrition(0, 0, 15, 0, 0, 0, "학생회관"));
        nutritions.add(new Nutrition(0, 0, 16, 0, 0, 0, "교직원식당"));


        List<Nutrition> sorted = List.of(
                new Nutrition(0, 0, 16, 0, 0, 0, "교직원식당"),
                new Nutrition(0, 0, 15, 0, 0, 0, "학생회관"),
                new Nutrition(0, 0, 14, 0, 0, 0, "금정회관"),
                new Nutrition(0, 0, 13, 0, 0, 0, "교직원식당"),
                new Nutrition(0, 0, 12, 0, 0, 0, "학생회관"),
                new Nutrition(0, 0, 11, 0, 0, 0, "금정회관")
        );

        List<Nutrition> sortedNutritions =
                sort.sortNutritions(nutritions, "단백질");

        assertEquals(sorted, sortedNutritions);
    }

    @Test
    void getList() {
        Sort sort = new Sort();

        List<Nutrition> nutritionLists = List.of();

        List<String> restaurantNames = sort.getList(nutritionLists);

        assertEquals(List.of(), restaurantNames);
    }

    @Test
    void getOneList() {
        Sort sort = new Sort();

        List<Nutrition> nutritionLists = List.of(
                new Nutrition(0, 0, 0, 0, 0, 0, "금정회관")
        );

        List<String> restaurantNames = sort.getList(nutritionLists);

        assertEquals(List.of("금정회관"), restaurantNames);
    }

    @Test
    void getLists() {
        Sort sort = new Sort();

        List<Nutrition> nutritionLists = List.of(
                new Nutrition(0, 0, 0, 0, 0, 0, "금정회관"),
                new Nutrition(0, 0, 0, 0, 0, 0, "학생회관"),
                new Nutrition(0, 0, 0, 0, 0, 0, "교직원회관")
        );

        List<String> restaurantNames = sort.getList(nutritionLists);

        assertEquals(List.of("금정회관", "학생회관", "교직원회관"), restaurantNames);
    }


    @Test
    void sortNoProteinList() {
        Sort sort = new Sort();

        List<Nutrition> nutritions = List.of();

        String[] sortedByprotein = sort.sortByProtein(nutritions, "단백질");

        assertArrayEquals(new String[]{}, sortedByprotein);
    }

    @Test
    void sortOneProteinList() {
        Sort sort = new Sort();

        List<Nutrition> nutritions = new ArrayList<>();
        nutritions.add(new Nutrition(0, 0, 0, 0, 0, 0, "금정"));

        String[] sortedByprotein = sort.sortByProtein(nutritions, "단백질");

        assertArrayEquals(new String[]{"금정"}, sortedByprotein);
    }

    @Test
    void sortProteinLists() {
        Sort sort = new Sort();

        List<Nutrition> nutritions = new ArrayList<>();
        nutritions.add(new Nutrition(0, 0, 14, 0, 0, 0, "금정회관"));
        nutritions.add(new Nutrition(0, 0, 13, 0, 0, 0, "학생회관"));
        nutritions.add(new Nutrition(0, 0, 15, 0, 0, 0, "교직원식당"));


        String[] sortedByprotein = sort.sortByProtein(nutritions, "단백질");

        assertArrayEquals(new String[]{"교직원식당", "금정회관", "학생회관"}, sortedByprotein);
    }
}