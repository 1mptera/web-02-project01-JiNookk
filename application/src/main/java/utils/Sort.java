package utils;

import models.Menu;
import models.Nutrition;
import models.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class Sort {
    public String[] sortByNutrition(List<Restaurant> restaurants, String nutrition) {
        List<Restaurant> restaurantLists = sortRestaurants(restaurants, nutrition);

        List<String> list = getList(restaurantLists);

        String[] sortedArray = list.toArray(new String[list.size()]);

        return sortedArray;
    }

    public List<Restaurant> sortRestaurants(List<Restaurant> restaurants, String nutrition) {
        List<Restaurant> sortedRestaurants = new ArrayList<>();

        int nutritionSize = restaurants.size();

        for (int i = 0; i < nutritionSize; i += 1) {
            int index = 0;

            Restaurant maximum = new Restaurant("",
                    new Menu("","","","","",""),
                    new Nutrition(0,0,0,0,0,0),0);

            for (int j = 0; j < restaurants.size(); j += 1) {
                boolean nutritionCompare = switch (nutrition){
                    case "단백질" -> restaurants.get(0).nutrition().protein() <= restaurants.get(j).nutrition().protein();
                    case "칼로리" -> restaurants.get(0).nutrition().calories() <= restaurants.get(j).nutrition().calories();
                    case "포화지방" -> restaurants.get(0).nutrition().saturatedFat() <= restaurants.get(j).nutrition().saturatedFat();
                    default -> false;
                };

                if (nutritionCompare) {
                    maximum = restaurants.get(j);
                    index = j;
                }
            }

            restaurants.remove(restaurants.get(index));
            sortedRestaurants.add(maximum);
        }

        return sortedRestaurants;
    }

    public List<String> getList(List<Restaurant> restaurants) {
        List<String> stringList = new ArrayList<>();

        for (Restaurant restaurant : restaurants) {
            stringList.add(restaurant.name());
        }

        return stringList;
    }
}
