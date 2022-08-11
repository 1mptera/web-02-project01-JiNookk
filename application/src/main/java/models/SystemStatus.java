package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SystemStatus {
    private static String date = "";
    private static int index = 0;
    private boolean isRecorded = false;
    private int todayMenuIndex = 0;

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

    public int todayMenuIndex() {
        return todayMenuIndex;
    }

    public void initTodayMenuIndex() {
        todayMenuIndex += 1;
    }

    public void isRecorded(List<Menu> recordedMenus) {
        for (Menu menu : recordedMenus){
            if (menu.date().equals(date)) {
                initRecorded();
            }
        }
    }

    public List<Integer> nutritionCounts(List<Nutrition> recordedNutritions) {
        for (Nutrition nutrition : recordedNutritions){
            if (nutrition.carbonHydrate() >= Nutrition.CARBONHYDRATESTANDARD){
                carbonHydrateCount += 1;
            }
            if (nutrition.sugar() >= Nutrition.SUGARSTANDARD){
                sugarCount += 1;
            }
            if (nutrition.protein() >= Nutrition.PROTEINSTANDARD){
                proteinCount += 1;
            }
            if (nutrition.fat() >= Nutrition.FATSTANDARD){
                fatCount += 1;
            }
            if (nutrition.saturatedFat() >= Nutrition.SATURATEDFATSTANDARD){
                saturatedFatCount += 1;
            }
            if (nutrition.calories() >= Nutrition.CALORIESTANDARD){
                caloriesCount += 1;
            }
        }

        List<Integer> nutritionCounts = List.of(carbonHydrateCount, sugarCount, proteinCount,
                fatCount, saturatedFatCount, caloriesCount);

        return nutritionCounts;
    }

    public int carbonHydrateCount() {
        return carbonHydrateCount;
    }

    public int sugarCount() {
        return sugarCount;
    }

    public int proteinCount() {
        return proteinCount;
    }

    public int fatCount() {
        return fatCount;
    }

    public int saturatedFatCount() {
        return saturatedFatCount;
    }

    public int caloriesCount() {
        return caloriesCount;
    }
}