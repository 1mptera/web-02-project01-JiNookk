package utils;

import models.Box;
import models.Nutrition;
import models.Restaurant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Sort {
    public String[] sortByNutrition(List<Restaurant> restaurants, String nutritionName) {
        List<String> sortedRestaurants = sortRestaurants(restaurants, nutritionName);

//        List<String> sortedRestaurants = newOrderedRestaurantsName(restaurantLists);

        String[] sortedArray = sortedRestaurants.toArray(new String[sortedRestaurants.size()]);

        return sortedArray;
    }

    public List<String> sortRestaurants(List<Restaurant> restaurants, String nutritionName) {
        List<String> sortedRestaurants = new ArrayList<>();

        List<Box> boxes = List.of(
                new Box(restaurants.get(0).name(), restaurants.get(0).nutritions().get(0)),
                new Box(restaurants.get(1).name(), restaurants.get(1).nutritions().get(0)),
                new Box(restaurants.get(2).name(), restaurants.get(2).nutritions().get(0))
        );

        int[] nutritions = switch (nutritionName) {
            case Nutrition.PROTEIN -> new int[]{
                    boxes.get(0).nutrition().protein(),
                    boxes.get(1).nutrition().protein(),
                    boxes.get(2).nutrition().protein()
            };

            case Nutrition.SATURATEDFAT -> new int[]{
                    boxes.get(0).nutrition().saturatedFat(),
                    boxes.get(1).nutrition().saturatedFat(),
                    boxes.get(2).nutrition().saturatedFat()
            };

            case Nutrition.CALORIES -> new int[]{
                    boxes.get(0).nutrition().calories(),
                    boxes.get(1).nutrition().calories(),
                    boxes.get(2).nutrition().calories()
            };
            default -> null;
        };

        Arrays.sort(nutritions);


        for (int nutrition : nutritions) {
            for (Box box : boxes) {
                if (nutritionName.equals(Nutrition.PROTEIN)) {
                    if (box.nutrition().protein() == nutrition){
                        sortedRestaurants.add(box.name());
                    }
                }
                if (nutritionName.equals(Nutrition.SATURATEDFAT)) {
                    if (box.nutrition().saturatedFat() == nutrition){
                        sortedRestaurants.add(box.name());
                    }
                }
                if (nutritionName.equals(Nutrition.CALORIES)) {
                    if (box.nutrition().calories() == nutrition){
                        sortedRestaurants.add(box.name());
                    }
                }
            }

        }

        return sortedRestaurants;

        // restaurants -> 금정, 학생, 교직원
        // 각 식당의 일단 현재 메뉴를 가져와야 됨.
        // 현재 메뉴를 어떻게 받아올지부터 고민! -> currentRestaurants로 관리
        // 언제 받아올까? -> 오늘의 메뉴 볼때, 메뉴 날짜 변경할때!
        // 언제 초기화 할까? ->
    }

    public List<String> newOrderedRestaurantsName(List<Restaurant> restaurants) {
        List<String> stringList = new ArrayList<>();

        for (Restaurant restaurant : restaurants) {
            stringList.add(restaurant.name());
        }

        return stringList;
    }
}
