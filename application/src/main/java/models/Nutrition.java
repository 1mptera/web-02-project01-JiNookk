package models;

public class Nutrition {
    public static final String PROTEIN = "단백질";
    public static final String CALORIES = "칼로리";
    public static final String SATURATEDFAT = "포화지방";


    private int carbonHydrate;
    private int sugar;
    private int protein;
    private int fat;
    private int saturatedFat;
    private int calorie;

    public Nutrition(int carbonHydrate, int sugar,
                     int protein, int fat,
                     int saturatedFat, int calorie) {

        this.carbonHydrate = carbonHydrate;
        this.sugar = sugar;
        this.protein = protein;
        this.fat = fat;
        this.saturatedFat = saturatedFat;
        this.calorie = calorie;
    }

    public int carbonHydrate() {
        return carbonHydrate;
    }

    public int sugar() {
        return sugar;
    }

    public int protein() {
        return protein;
    }

    public int fat() {
        return fat;
    }

    public int saturatedFat() {
        return saturatedFat;
    }

    public int calories() {
        return calorie;
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
                ", 포화지방: " + saturatedFat + ", 열량: " + calorie;
    }
}
