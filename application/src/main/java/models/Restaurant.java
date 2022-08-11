package models;

// TODO : 메뉴를 참조해서 저장, 영양성분도 참조
// 식당의 이름과 식당의 메뉴가 저장된 파일 경로 참조


import java.util.List;

public class Restaurant {
    public static final String GEUMJEONG = "금정회관";
    public static final String STUDENTHALL = "학생회관";
    public static final String STAFFCAFETERIA = "교직원식당";
    public static final int GEUMJEONGPRICE = 3500;
    public static final int STUDENTHALLPRICE = 5500;
    public static final int STAFFCAFETERIAPRICE = 5500;

    private final SystemStatus systemStatus = new SystemStatus();

    private String restaurantName;
    private List<Menu> menus;
    private List<Nutrition> nutritions;
    private int foodPrice;

    public Restaurant(String restaurantName, List<Menu> menus, List<Nutrition> nutritions, int foodPrice) {
        this.restaurantName = restaurantName;
        this.menus = menus;
        this.nutritions = nutritions;
        this.foodPrice = foodPrice;
    }

    public String name() {
        return restaurantName;
    }

    public int price() {
        return foodPrice;
    }

    public List<Menu> menus() {
        return menus;
    }

    public List<Nutrition> nutritions() {
        return nutritions;
    }

    public Menu selectMenu() {
        systemStatus.initIndex(menus);

        Menu selectedMenu = menus.get(this.systemStatus.index());

        return selectedMenu;
    }

    public Nutrition selectNutrition() {
        systemStatus.initIndex(menus);

        Nutrition selectedNutrition = nutritions.get(systemStatus.index());

        return selectedNutrition;
    }

    public Menu getCheckedMenu(){
        return null;
    }

    // 달력에서 date 설정 -> menuPanel() -> add(geumjeongPanel())
    // -> menus, nutritions파일에서 읽어옴(식당별로) -> Restaurant geumjeong생성
    // -> cafeteriaPanel(geumjeong) -> menu -> restaurant.selectMenu
    // -> initIndex(menus) -> menus의 menu의date와 시스템의 date가 같은지 비교
    // -> 인덱스 반환

    @Override
    public boolean equals(Object other) {
        Restaurant otherRestaurant = (Restaurant) other;

        return this.restaurantName.equals(otherRestaurant.restaurantName)
                && this.menus.toString().equals(otherRestaurant.menus.toString())
                && this.nutritions.toString().equals(otherRestaurant.nutritions.toString())
                && this.foodPrice == otherRestaurant.foodPrice;
    }

    @Override
    public String toString() {
        return restaurantName + ", " + menus + ", " + nutritions + ", " + foodPrice;
    }
}
