package models;

public class Nutrition {

    private int carbonHydrate;
    private int sugar;
    private int protein;
    private int fat;
    private int saturatedFat;
    private int calorie;
    private String cafeteriaName;

    public Nutrition(int carbonHydrate, int sugar,
                     int protein, int fat,
                     int saturatedFat, int calorie, String cafeteriaName) {

        this.carbonHydrate = carbonHydrate;
        this.sugar = sugar;
        this.protein = protein;
        this.fat = fat;
        this.saturatedFat = saturatedFat;
        this.calorie = calorie;
        this.cafeteriaName = cafeteriaName;
    }

    public int carbonHydratePer100g() {
        return carbonHydrate;
    }

    public int sugarPer100g() {
        return sugar;
    }

    public int proteinPer100g() {
        return protein;
    }

    public int fatPer100g() {
        return fat;
    }

    public int saturatedFatPer100g() {
        return saturatedFat;
    }

    public int caloriesPer100g() {
        return calorie;
    }

    public String restaurantName() {
        return cafeteriaName;
    }

    @Override
    public boolean equals(Object other) {
        Nutrition otherNutrition = (Nutrition) other;

        return (this.carbonHydrate == otherNutrition.carbonHydrate)
                && (this.protein == otherNutrition.protein)
                && (this.sugar == otherNutrition.sugar)
                && (this.fat == otherNutrition.fat)
                && (this.saturatedFat == otherNutrition.saturatedFat)
                && (this.calorie == otherNutrition.calorie);
    }

    @Override
    public String toString() {
        return "\n탄수화물: " + carbonHydrate + ", 당류: " + sugar + ", 단백질: " + protein + ", 지방: " + fat +
                ", 포화지방: " + saturatedFat + ", 열량: " + calorie + ", 식당이름: " + cafeteriaName;
    }
}
