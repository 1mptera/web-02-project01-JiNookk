package models;

public class Menu {

    private String rice;
    private String mainMenu;
    private String sideMenu;
    private String soup;
    private String gimchi;
    private String restaurant;

    public Menu(String rice, String mainMenu, String sideMenu, String soup, String gimchi) {
        this.rice = rice;
        this.mainMenu = mainMenu;
        this.sideMenu = sideMenu;
        this.soup = soup;
        this.gimchi = gimchi;
    }
}
