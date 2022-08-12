package panels;

import application.CafeteriaMenuReccomendator;
import frame.CalendarFrame;
import frame.DatePicker;
import models.Nutrition;
import models.Restaurant;
import models.SystemStatus;
import models.User;
import utils.Sort;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.List;

public class MenuOptionPanel extends JPanel {
    private CreateMenuPanel createMenuPanel;
    private SystemStatus systemStatus;
    private CafeteriaMenuReccomendator cafeteriaMenuReccomendator;
    private List<Restaurant> restaurants;
    private List<Restaurant> currentRestaurants;
    private JPanel contentPanel;
    private JPanel menuPanel;
    private JPanel displayPanel;
    private Sort sort;
    private JPanel buttonPanel;
    private User user;
    private Restaurant recommendedRestaurant;
    private String favoriteNutrition;

    public MenuOptionPanel(CreateMenuPanel createMenuPanel,
                           SystemStatus systemStatus,
                           CafeteriaMenuReccomendator cafeteriaMenuReccomendator,
                           List<Restaurant> restaurants,
                           List<Restaurant> currentRestaurants,
                           JPanel contentPanel,
                           JPanel menuPanel,
                           JPanel displayPanel,
                           Sort sort,
                           JPanel buttonPanel,
                           User user,
                           Restaurant recommendedRestaurant,
                           String favoriteNutrition) {
        this.createMenuPanel = createMenuPanel;
        this.systemStatus = systemStatus;
        this.cafeteriaMenuReccomendator = cafeteriaMenuReccomendator;
        this.restaurants = restaurants;
        this.currentRestaurants = currentRestaurants;
        this.contentPanel = contentPanel;
        this.menuPanel = menuPanel;
        this.displayPanel = displayPanel;
        this.sort = sort;
        this.buttonPanel = buttonPanel;
        this.user = user;
        this.recommendedRestaurant = recommendedRestaurant;
        this.favoriteNutrition = favoriteNutrition;

        this.setOpaque(false);
            this.setBackground(Color.LIGHT_GRAY);
            this.add(selectDateButton());
            this.add(sortButton(Nutrition.PROTEIN));
            this.add(sortButton(Nutrition.CALORIES));
            this.add(backToMenuButton());
    }

    public JButton selectDateButton() {
        JButton button = new JButton("날짜 선택");
        button.addActionListener(e -> {
            CalendarFrame calendarFrame = new CalendarFrame(systemStatus,
                    restaurants,
                    currentRestaurants,
                    cafeteriaMenuReccomendator,
                    contentPanel,
                    menuPanel,
                    displayPanel,
                    createMenuPanel);

            calendarFrame.setVisible(true);
        });
        return button;
    }

    public JButton sortButton(String nutrition) {
        JButton button = new JButton(nutrition + " 정렬");
        button.addActionListener(e -> {

            String[] sortedCafeteriaNames = sort.sortByNutrition(currentRestaurants, nutrition);

            cafeteriaMenuReccomendator.removeContainer(contentPanel);
            cafeteriaMenuReccomendator.removeContainer(menuPanel);
            cafeteriaMenuReccomendator.removeContainer(displayPanel);

            DatePanel datePanel = new DatePanel(systemStatus);
            contentPanel.add(datePanel, BorderLayout.NORTH);
            contentPanel.add(menuPanel);

            displayPanel.add(cafeteriaMenuReccomendator.pageLabel("-" + nutrition + " 정렬-"));

            for (String sortedCafeteriaName : sortedCafeteriaNames) {
                if (sortedCafeteriaName.equals("금정회관")) {
                    try {
                        menuPanel.add(createMenuPanel.geumjeongPanel());
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                if (sortedCafeteriaName.equals("학생회관")) {
                    try {
                        menuPanel.add(createMenuPanel.studentHallMenuPanel());
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                if (sortedCafeteriaName.equals("교직원식당")) {
                    try {
                        menuPanel.add(createMenuPanel.staffCafeteriaMenuPanel());
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

            cafeteriaMenuReccomendator.updateDisplay();
        });
        return button;
    }

    public JButton backToMenuButton() {
        JButton button = new JButton("돌아가기");
        button.addActionListener(e -> {
            cafeteriaMenuReccomendator.removeContainer(menuPanel);
            backToHome();
        });
        return button;
    }

    private void backToHome() {
        cafeteriaMenuReccomendator.removeContainer(buttonPanel);
        cafeteriaMenuReccomendator.removeContainer(contentPanel);
        cafeteriaMenuReccomendator.removeContainer(displayPanel);

        cafeteriaMenuReccomendator.setBorder(contentPanel, 250, 0, 0, 0);

        buttonPanel.add(createMenuPanel);

        RecommendPanel recommendPanel = new RecommendPanel(cafeteriaMenuReccomendator,
                user,
                recommendedRestaurant,
                favoriteNutrition);
        contentPanel.add(recommendPanel);
        displayPanel.add(cafeteriaMenuReccomendator.pageLabel("-홈 메뉴-"));

        cafeteriaMenuReccomendator.updateDisplay();
    }
}
