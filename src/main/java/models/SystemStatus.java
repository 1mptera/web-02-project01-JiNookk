package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SystemStatus {
    private static String date = "";
    private static int index = 0;
    private boolean isRecorded = false;

    private int carbonHydrateCount = 0;
    private int sugarCount = 0;
    private int proteinCount = 0;
    private int fatCount = 0;
    private int saturatedFatCount = 0;
    private int caloriesCount = 0;

    public void today() {
        LocalDate now = LocalDate.now();

        date = now.format(DateTimeFormatter.ofPattern("yyMMdd"));
    }

    public String date() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public int index() {
        return index;
    }

    public void initIndex(List<Menu> menus) {
        for (Menu menu : menus) {
            if (menu.date().equals(date())) {
                index = menus.indexOf(menu);
            }
        }
    }

    public void initRecorded() {
        isRecorded = true;
    }

    public boolean isRecorded() {
        return isRecorded;
    }

    public void isRecorded(List<Menu> recordedMenus) {
        for (Menu menu : recordedMenus) {
            if (menu.date().equals(date)) {
                initRecorded();
            }
        }
    }

    public void initNutritionCounts(List<Nutrition> recordedNutritions) {
        for (Nutrition nutrition : recordedNutritions) {
            if (nutrition.carbonHydrate() >= Nutrition.CARBONHYDRATESTANDARD) {
                carbonHydrateCount += 1;
            }
            if (nutrition.sugar() >= Nutrition.SUGARSTANDARD) {
                sugarCount += 1;
            }
            if (nutrition.protein() >= Nutrition.PROTEINSTANDARD) {
                proteinCount += 1;
            }
            if (nutrition.fat() >= Nutrition.FATSTANDARD) {
                fatCount += 1;
            }
            if (nutrition.saturatedFat() >= Nutrition.SATURATEDFATSTANDARD) {
                saturatedFatCount += 1;
            }
            if (nutrition.calories() >= Nutrition.CALORIESTANDARD) {
                caloriesCount += 1;
            }
        }
    }

    public void initCurrentRestaurant(List<Restaurant> restaurants, List<Restaurant> currentRestaurants) {
        List<Menu> menus = restaurants.get(0).menus();
        for (int i = 0; i < menus.size(); i += 1) {
            if (date.equals(menus.get(i).date())) {
                int index = i;
                currentRestaurants = List.of(
                        new Restaurant(restaurants.get(0).name(),
                                List.of(restaurants.get(0).menus().get(index)),
                                List.of(restaurants.get(0).nutritions().get(index)),
                                restaurants.get(0).price()
                        ),
                        new Restaurant(restaurants.get(1).name(),
                                List.of(restaurants.get(1).menus().get(index)),
                                List.of(restaurants.get(1).nutritions().get(index)),
                                restaurants.get(1).price()
                        ),
                        new Restaurant(restaurants.get(2).name(),
                                List.of(restaurants.get(2).menus().get(index)),
                                List.of(restaurants.get(2).nutritions().get(index)),
                                restaurants.get(2).price()
                        )
                );
            }
        }
    }

    public String compareNutritionCounts() {
        int[] nutritionCounts = new int[]{carbonHydrateCount, sugarCount, proteinCount,
                fatCount, saturatedFatCount, caloriesCount};

        int max = 0;

        for (int nutritionCount : nutritionCounts) {
            if (nutritionCount > max) {
                max = nutritionCount;
            }
        }

        String nutritionCount = "";

        if (max == carbonHydrateCount) {
            nutritionCount = Nutrition.CARBONHYDRATE;
        }
        if (max == sugarCount) {
            nutritionCount = Nutrition.SUGAR;
        }
        if (max == proteinCount) {
            nutritionCount = Nutrition.PROTEIN;
        }
        if (max == fatCount) {
            nutritionCount = Nutrition.FAT;
        }
        if (max == saturatedFatCount) {
            nutritionCount = Nutrition.SATURATEDFAT;
        }
        if (max == caloriesCount) {
            nutritionCount = Nutrition.CALORIES;
        }

        return nutritionCount;
    }

    public Restaurant recommendRestaurant(List<Restaurant> todayRestaurants, String favoriteNutrition) {
        List<Integer> favoriteNutritions = new ArrayList<>();

        for (Restaurant todayRestaurant : todayRestaurants) {
            switch (favoriteNutrition) {
                case Nutrition.CARBONHYDRATE ->
                        favoriteNutritions.add(todayRestaurant.nutritions().get(0).carbonHydrate());
                case Nutrition.SUGAR ->
                        favoriteNutritions.add(todayRestaurant.nutritions().get(0).sugar());
                case Nutrition.PROTEIN ->
                        favoriteNutritions.add(todayRestaurant.nutritions().get(0).protein());
                case Nutrition.FAT ->
                        favoriteNutritions.add(todayRestaurant.nutritions().get(0).fat());
                case Nutrition.SATURATEDFAT ->
                        favoriteNutritions.add(todayRestaurant.nutritions().get(0).saturatedFat());
                case Nutrition.CALORIES ->
                        favoriteNutritions.add(todayRestaurant.nutritions().get(0).calories());
                default -> favoriteNutritions.add(null);
            }
        }

        int [] favoriteNutritionArray = favoriteNutritions.stream().
                mapToInt(Integer::intValue).
                toArray();

        Arrays.sort(favoriteNutritionArray);

        int max = favoriteNutritionArray[favoriteNutritionArray.length-1];

        int i = favoriteNutritions.indexOf(max);

        Restaurant recommendRestaurant = todayRestaurants.get(i);
        return recommendRestaurant;
    }
}