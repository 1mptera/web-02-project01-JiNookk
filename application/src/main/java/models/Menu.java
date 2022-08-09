package models;

import java.util.Objects;

public class Menu {

    private String rice;
    private String mainMenu;
    private String sideMenu;
    private String soup;
    private String gimchi;

    public Menu(String rice, String mainMenu, String sideMenu, String soup, String gimchi) {
        this.rice = rice;
        this.mainMenu = mainMenu;
        this.sideMenu = sideMenu;
        this.soup = soup;
        this.gimchi = gimchi;
    }

    public String rice() {
        return rice;
    }

    public String mainMenu() {
        return mainMenu;
    }

    public String sideMenu() {
        return sideMenu;
    }

    public String soup() {
        return soup;
    }

    public String gimchi() {
        return gimchi;
    }


    @Override
    public boolean equals(Object other) {
        Menu otherMenu = (Menu) other;

        return Objects.equals(this.rice, otherMenu.rice) && Objects.equals(this.mainMenu, otherMenu.mainMenu)
                && Objects.equals(this.sideMenu, otherMenu.sideMenu) && Objects.equals(this.soup, otherMenu.soup)
                && Objects.equals(this.gimchi, otherMenu.gimchi);
    }

    @Override
    public String toString() {
        return rice + ", " + mainMenu + ", " + sideMenu + ", " + soup + ", " + gimchi;
    }
}
