package utils;

import models.Menu;
import models.Nutrition;
import models.Restaurant;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SortTest {
    @Test
    void sortNoNutrition() {
        Sort sort = new Sort();

        List<Restaurant> sortedRestaurants = sort.sortRestaurants(List.of(), "단백질");

        assertEquals(List.of(), sortedRestaurants);
    }

    @Test
    void sortOneNutrition() {
        Sort sort = new Sort();

        Restaurant restaurant = new Restaurant("금정회관",
                List.of(),
                List.of(new Nutrition(0, 0, 15, 0, 0, 0)),
                3500);

        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(restaurant);

        List<Restaurant> sortedRestaurants =
                sort.sortRestaurants(restaurants, "단백질");

        assertEquals(List.of(restaurant), sortedRestaurants);
    }

    @Test
    void index() {
        Restaurant studentHall = new Restaurant(
                "학생회관",
                List.of(),
                List.of(new Nutrition(0, 0, 13, 0, 0, 0)),
                5500
        );

        Restaurant staffCafeteria = new Restaurant(
                "교직원식당",
                List.of(),
                List.of(new Nutrition(0, 0, 12, 0, 0, 0)),
                5500);

        Restaurant geumjeong = new Restaurant(
                "금정회관",
                List.of(),
                List.of(new Nutrition(0, 0, 15, 0, 0, 0)),
                3500);


        List<Restaurant> restaurants = List.of(studentHall,staffCafeteria,geumjeong);

        Restaurant maximum = new Restaurant("금정회관",
                List.of(),
                List.of(new Nutrition(0, 0, 15, 0, 0, 0)),
                3500);

        int index = restaurants.indexOf(maximum);
        assertEquals(2, index);
    }

    @Test
    void sortRestaurants() {
        Sort sort = new Sort();

        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant studentHall = new Restaurant(
                "학생회관",
                List.of(),
                List.of(new Nutrition(0, 0, 13, 0, 0, 0)),
                5500
        );
        restaurants.add(studentHall);

        Restaurant staffCafeteria = new Restaurant(
                "교직원식당",
                List.of(),
                List.of(new Nutrition(0, 0, 12, 0, 0, 0)),
                5500);
        restaurants.add(staffCafeteria);

        Restaurant geumjeong = new Restaurant(
                "금정회관",
                List.of(),
                List.of(new Nutrition(0, 0, 15, 0, 0, 0)),
                3500);
        restaurants.add(geumjeong);

        List<Restaurant> sorted = List.of(geumjeong, studentHall, staffCafeteria);

        List<Restaurant> sortedRestaurants =
                sort.sortRestaurants(restaurants, Nutrition.PROTEIN);

        assertEquals(sorted, sortedRestaurants);
    }

    @Test
    void getList() {
        Sort sort = new Sort();

        List<Restaurant> restaurants = List.of();

        List<String> restaurantNames = sort.getList(restaurants);

        assertEquals(List.of(), restaurantNames);
    }

    @Test
    void getOneList() {
        Sort sort = new Sort();

        List<Restaurant> nutritionLists = List.of(
                new Restaurant(
                        "금정회관",
                        List.of(),
                        List.of(new Nutrition(0, 0, 15, 0, 0, 0)),
                        3500
                )
        );

        List<String> restaurantNames = sort.getList(nutritionLists);

        assertEquals(List.of("금정회관"), restaurantNames);
    }

    @Test
    void getLists() {
        Sort sort = new Sort();

        List<Restaurant> nutritionLists = List.of(
                new Restaurant("학생회관",
                        List.of(),
                        List.of(new Nutrition(0, 0, 13, 0, 0, 0)),
                        5500),
                new Restaurant("교직원식당",
                        List.of(),
                        List.of(new Nutrition(0, 0, 12, 0, 0, 0)),
                        5500),
                new Restaurant("금정회관",
                        List.of(),
                        List.of(new Nutrition(0, 0, 15, 0, 0, 0)),
                        3500)
        );

        List<String> restaurantNames = sort.getList(nutritionLists);

        assertEquals(List.of("학생회관", "교직원식당", "금정회관"), restaurantNames);
    }


    @Test
    void sortNoProteinList() {
        Sort sort = new Sort();

        List<Restaurant> restaurants = List.of();

        String[] sortedByprotein = sort.sortByNutrition(restaurants, "단백질");

        assertArrayEquals(new String[]{}, sortedByprotein);
    }

    @Test
    void sortOneProteinList() {
        Sort sort = new Sort();

        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant("금정회관",
                List.of(),
                List.of(new Nutrition(0, 0, 15, 0, 0, 0)),
                3500));

        String[] sortedByprotein = sort.sortByNutrition(restaurants, "단백질");

        assertArrayEquals(new String[]{"금정회관"}, sortedByprotein);
    }

    @Test
    void sortProteinLists() {
        Sort sort = new Sort();

        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant("금정회관",
                List.of(),
                List.of(new Nutrition(0, 0, 13, 0, 0, 0)),
                3500));
        restaurants.add(new Restaurant("학생회관",
                List.of(),
                List.of(new Nutrition(0, 0, 12, 0, 0, 0)),
                5500));
        restaurants.add(new Restaurant("교직원식당",
                List.of(),
                List.of(new Nutrition(0, 0, 15, 0, 0, 0)),
                5500));


        String[] sortedByprotein = sort.sortByNutrition(restaurants, Nutrition.PROTEIN);

        assertArrayEquals(new String[]{"교직원식당", "금정회관", "학생회관"}, sortedByprotein);
    }
}