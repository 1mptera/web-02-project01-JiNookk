package panels;

import application.CafeteriaMenuReccomendator;
import frame.AlertFrame;
import frame.DatePicker;
import frame.WarningFrame;
import models.Menu;
import models.Nutrition;
import models.Restaurant;
import models.SystemStatus;
import models.User;
import utils.RecordLoader;
import utils.Sort;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class CreateMenuPanel extends JPanel {

    private final Sort sort;
    private final RecordLoader recordLoader = new RecordLoader();
    private CafeteriaMenuReccomendator cafeteriaMenuReccomendator;
    private JPanel contentPanel;
    private SystemStatus systemStatus;
    private List<Restaurant> currentRestaurants;
    private List<Restaurant> todayRestaurants;
    private JPanel buttonPanel;
    private JPanel displayPanel;
    private List<Restaurant> restaurants;
    private User user;
    private Restaurant recommendedRestaurant;
    private String favoriteNutrition;
    private List<Menu> recordedMenus;
    private List<Nutrition> recordedNutritions;
    private JPanel menuPanel;
    private JFrame recordFrame;
    private JFrame alertFrame;

    public CreateMenuPanel(CafeteriaMenuReccomendator cafeteriaMenuReccomendator,
                           JPanel contentPanel,
                           SystemStatus systemStatus,
                           List<Restaurant> currentRestaurants,
                           List<Restaurant> todayRestaurants,
                           JPanel buttonPanel,
                           JPanel displayPanel,
                           List<Restaurant> restaurants,
                           User user,
                           Restaurant recommendedRestaurant,
                           String favoriteNutrition,
                           List<Menu> recordedMenus,
                           List<Nutrition> recordedNutritions) {
        this.cafeteriaMenuReccomendator = cafeteriaMenuReccomendator;
        this.contentPanel = contentPanel;
        this.systemStatus = systemStatus;
        this.currentRestaurants = currentRestaurants;
        this.todayRestaurants = todayRestaurants;
        this.buttonPanel = buttonPanel;
        this.displayPanel = displayPanel;
        this.restaurants = restaurants;
        this.user = user;
        this.recommendedRestaurant = recommendedRestaurant;
        this.favoriteNutrition = favoriteNutrition;
        this.recordedMenus = recordedMenus;
        this.recordedNutritions = recordedNutritions;
        sort = new Sort();

        this.setOpaque(false);
        this.setBackground(Color.LIGHT_GRAY);
        this.add(todayMenuButton());
        this.add(recordMenuButton());

    }

    public JButton todayMenuButton() {
        JButton button = new JButton("오늘의 메뉴 확인하기");
        button.addActionListener(e -> {
            try {
                cafeteriaMenuReccomendator.setBorder(contentPanel, 20, 0, 0, 0);
                systemStatus.today();

                currentRestaurants = todayRestaurants;

                cafeteriaMenuReccomendator.removeContainer(buttonPanel);
                cafeteriaMenuReccomendator.removeContainer(contentPanel);
                cafeteriaMenuReccomendator.removeContainer(displayPanel);

                contentPanel.add(menuPanel());

                MenuOptionPanel menuOptionPanel = new MenuOptionPanel(this,
                        systemStatus,
                        cafeteriaMenuReccomendator,
                        restaurants,
                        currentRestaurants,
                        contentPanel,
                        menuPanel,
                        displayPanel,
                        sort,
                        buttonPanel,
                        user,
                        recommendedRestaurant,
                        favoriteNutrition);

                DatePanel datePanel = new DatePanel(systemStatus);

                buttonPanel.add(menuOptionPanel);
                contentPanel.add(datePanel, BorderLayout.NORTH);
                displayPanel.add(cafeteriaMenuReccomendator.pageLabel("-오늘의 메뉴-"));

            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        return button;
    }

    public JPanel menuPanel() throws FileNotFoundException {
        menuPanel = new JPanel();
        menuPanel.setOpaque(false);
        menuPanel.setLayout(new GridLayout(1, 3));
        menuPanel.add(geumjeongPanel());
        menuPanel.add(studentHallMenuPanel());
        menuPanel.add(staffCafeteriaMenuPanel());

        return menuPanel;
    }

    public JPanel geumjeongPanel() throws FileNotFoundException {
        return cafeteriaPanel(restaurants.get(0));
    }

    public JPanel studentHallMenuPanel() throws FileNotFoundException {
        return cafeteriaPanel(restaurants.get(1));
    }

    public JPanel staffCafeteriaMenuPanel() throws FileNotFoundException {
        return cafeteriaPanel(restaurants.get(2));
    }

    public JPanel cafeteriaPanel(Restaurant restaurant) throws FileNotFoundException {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setBackground(Color.LIGHT_GRAY);

        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));

        panel.setLayout(new FlowLayout(FlowLayout.LEADING, 50, 50));

        Menu menu = restaurant.selectMenu();

        Nutrition nutrition = restaurant.selectNutrition();

        CafeteriaMenu cafeteriaMenu = new CafeteriaMenu(restaurant, menu);
        MenuNutritions menuNutritions = new MenuNutritions(nutrition, menu);

        panel.add(cafeteriaMenu);
        panel.add(menuNutritions);

        cafeteriaMenuReccomendator.updateDisplay();
        return panel;
    }

    public JButton recordMenuButton() {
        JButton button = new JButton("오늘의 메뉴 기록하기");
        button.addActionListener(e -> {
            if (todayRestaurants.size() == 0) {
                initRecordFrame();

                JPanel panel = new JPanel();
                panel.add(new JLabel("오늘의 메뉴부터 확인해주세요!"));
                JButton disposeButton = new JButton("확인");
                disposeButton.addActionListener(dispose -> {
                    recordFrame.dispose();
                });
                panel.add(disposeButton);
                recordFrame.add(panel);

                recordFrame.pack();
                recordFrame.setVisible(true);
            }
            if (todayRestaurants.size() == 3) {
                if (!systemStatus.isRecorded()) {
                    initRecordFrame();

                    CheckPanel checkPanel = new CheckPanel(cafeteriaMenuReccomendator,
                            recordFrame,
                            todayRestaurants,
                            recordedMenus,
                            recordedNutritions,
                            systemStatus);
                    recordFrame.add(checkPanel);

                    recordFrame.setVisible(true);
                }

                if (systemStatus.isRecorded()) {
                    WarningFrame warningFrame = new WarningFrame();
                    warningFrame.setVisible(true);
                }
            }
        });
        return button;
    }

    private void initRecordFrame() {
        recordFrame = new JFrame("기록하기");
        recordFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        recordFrame.setSize(400, 300);
        recordFrame.setLocationRelativeTo(null);
    }
}
