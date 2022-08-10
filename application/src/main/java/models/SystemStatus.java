package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SystemStatus {
    private static String date = "";
    private static int index = 0;
    private boolean isRecorded = false;
    private int todayMenuIndex = 0;

    public SystemStatus(){
    }

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
}
