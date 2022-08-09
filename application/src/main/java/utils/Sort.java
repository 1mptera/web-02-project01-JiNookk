package utils;

import models.Nutrition;

import java.util.ArrayList;
import java.util.List;

public class Sort {
    public String[] sortByProtein(List<Nutrition> nutritions) {
        List<Nutrition> nutritionLists = sortNutritions(nutritions);

        List<String> list = getList(nutritionLists);

        String[] sortedArray = list.toArray(new String[list.size()]);

        return sortedArray;
    }

    public List<Nutrition> sortNutritions(List<Nutrition> nutritions) {
        List<Nutrition> sortedNutritions = new ArrayList<>();

        int nutritionSize = nutritions.size();

        for (int i = 0; i < nutritionSize; i += 1) {
            int index = 0;
            Nutrition maximum = new Nutrition(0, 0, 0, 0, 0, 0, "");
            for (int j = 0; j < nutritions.size(); j += 1) {
                if (nutritions.get(0).proteinPer100g() <= nutritions.get(j).proteinPer100g()) {
                    maximum = nutritions.get(j);
                    index = j;
                }
            }

            nutritions.remove(nutritions.get(index));
            sortedNutritions.add(maximum);
        }

        return sortedNutritions;
    }

    public List<String> getList(List<Nutrition> nutritionLists) {
        List<String> stringList = new ArrayList<>();

        for (Nutrition nutrition : nutritionLists) {
            stringList.add(nutrition.restaurantName());
        }

        return stringList;
    }
}
