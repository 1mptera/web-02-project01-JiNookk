package models;

public class Box {

    private String name;
    private Nutrition nutrition;

    public Box(String name, Nutrition nutrition) {

        this.name = name;
        this.nutrition = nutrition;
    }

    public Nutrition nutrition() {
        return nutrition;
    }

    public String name() {
        return name;
    }
}
