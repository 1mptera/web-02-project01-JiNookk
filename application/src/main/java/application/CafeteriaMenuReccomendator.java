package application;// 내가 원하는 것
// 1. 학생식당 메인메뉴 비교 (4 개)
// 2.
// 3.
// 4.
// 도메인 모델 : menu


import frame.DatePicker;
import models.Menu;
import models.Nutrition;
import models.Restaurant;
import models.SystemStatus;
import models.User;
import panels.CafeteriaMenu;
import panels.CreateMenuPanel;
import panels.ImagePanel;
import panels.MenuNutritions;
import panels.RecommendPanel;
import utils.Loader;
import utils.RecordLoader;
import utils.Sort;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CafeteriaMenuReccomendator {

    private static JFrame frame;
    private static JPanel contentPanel;
    private static JPanel buttonPanel;
    private static JPanel menuPanel;
    private RecordLoader recordLoader;
    private SystemStatus systemStatus;

    private Loader loader;
    private Sort sort;

    private static List<Restaurant> restaurants;
    private List<Restaurant> todayRestaurants;
    private JFrame recordFrame;
    private JFrame alertFrame;
    private List<Menu> recordedMenus;
    private List<Nutrition> recordedNutritions;
    private JPanel displayPanel;
    private List<File> menuFiles;
    private List<File> nutritionFiles;
    private List<Restaurant> currentRestaurants;
    private String favoriteNutrition;
    private Restaurant recommendedRestaurant;
    private User user;
    private ImagePanel imagePanel;

    public static void main(String[] args) throws FileNotFoundException {
        CafeteriaMenuReccomendator application = new CafeteriaMenuReccomendator();

        application.run();
    }

    public void run() throws FileNotFoundException {
        preparation();

        initMainFrame();

        initImagePanel();

        initButtonPanel();

        initContentPanel();

        initDisplayPanel();

        frame.setVisible(true);
    }

    private void preparation() throws FileNotFoundException {
        prepareObjects();

        initFiles();

        initRestaurants();
    }

    private void prepareObjects() throws FileNotFoundException {
        user = new User("진욱");
        systemStatus = new SystemStatus();
        systemStatus.today();

        recordLoader = new RecordLoader();
        recordedMenus = recordLoader.loadMenus();
        systemStatus.isRecorded(recordedMenus);

        recordedNutritions = recordLoader.loadNutritions();
        systemStatus.initNutritionCounts(recordedNutritions);
        favoriteNutrition = systemStatus.compareNutritionCounts();

        loader = new Loader();
        sort = new Sort();

        restaurants = new ArrayList<>();
        currentRestaurants = new ArrayList<>();
        todayRestaurants = new ArrayList<>();

        menuFiles = new ArrayList<>();
        nutritionFiles = new ArrayList<>();
    }

    private void initFiles() {
        menuFiles.add(new File("./src/main/resources/menus/금정회관.csv"));
        menuFiles.add(new File("./src/main/resources/menus/학생회관.csv"));
        menuFiles.add(new File("./src/main/resources/menus/교직원식당.csv"));

        nutritionFiles.add(new File("./src/main/resources/nutritions/금정회관영양성분.csv"));
        nutritionFiles.add(new File("./src/main/resources/nutritions/학생회관영양성분.csv"));
        nutritionFiles.add(new File("./src/main/resources/nutritions/교직원식당영양성분.csv"));
    }

    private void loadGeumjeong() throws FileNotFoundException {
        List<Menu> menus = loader.loadMenus(menuFiles.get(0));

        List<Nutrition> nutritions = loader.loadNutritions(nutritionFiles.get(0));

        Restaurant geumjeong = new Restaurant("금정회관", menus, nutritions, 3500);

        restaurants.add(geumjeong);
    }

    private void loadStudentHall() throws FileNotFoundException {
        List<Menu> menus = loader.loadMenus(menuFiles.get(1));

        List<Nutrition> nutritions = loader.loadNutritions(nutritionFiles.get(1));

        Restaurant studentHall = new Restaurant("학생회관", menus, nutritions, 5500);

        restaurants.add(studentHall);
    }

    private void loadStaffCafeteria() throws FileNotFoundException {
        List<Menu> menus = loader.loadMenus(menuFiles.get(2));

        List<Nutrition> nutritions = loader.loadNutritions(nutritionFiles.get(2));

        Restaurant staffCafeteria = new Restaurant("교직원식당", menus, nutritions, 5500);

        restaurants.add(staffCafeteria); // TODO : 밑에서 더해준 restaurants add 지워줘야함
    }

    private void initTodayRestaurants() {
        systemStatus.initIndex(restaurants.get(0).menus());

        todayRestaurants = List.of(
                new Restaurant(Restaurant.GEUMJEONG,
                        List.of(restaurants.get(0).menus().get(systemStatus.index())),       //TODO
                        List.of(restaurants.get(0).nutritions().get(systemStatus.index())),
                        Restaurant.GEUMJEONGPRICE
                ),
                new Restaurant(Restaurant.STUDENTHALL,
                        List.of(restaurants.get(1).menus().get(systemStatus.index())),       //TODO
                        List.of(restaurants.get(1).nutritions().get(systemStatus.index())),
                        Restaurant.STUDENTHALLPRICE
                ),
                new Restaurant(Restaurant.STAFFCAFETERIA,
                        List.of(restaurants.get(2).menus().get(systemStatus.index())),       //TODO
                        List.of(restaurants.get(2).nutritions().get(systemStatus.index())),
                        Restaurant.STAFFCAFETERIAPRICE
                )
        );
    }

    private void initRestaurants() throws FileNotFoundException {
        loadGeumjeong();

        loadStudentHall();

        loadStaffCafeteria();

        initTodayRestaurants();

        recommendedRestaurant = systemStatus.recommendRestaurant(todayRestaurants, favoriteNutrition);
    }

    private void initMainFrame() {
        frame = new JFrame("학식메뉴 알리미");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 700);
        frame.setLocationRelativeTo(null);
    }

    private void initImagePanel() {
        imagePanel = new ImagePanel("./src/main/resources/images/foodImage1.png");

        imagePanel.setLayout(new BorderLayout());

        frame.add(imagePanel);
    }

    public void initDisplayPanel() {
        displayPanel = new JPanel();

        displayPanel.setPreferredSize(new Dimension(0, 30));

        displayPanel.setBackground(Color.YELLOW);

        displayPanel.setOpaque(false);
    }

    public void initButtonPanel() {
        buttonPanel = new JPanel();

        buttonPanel.add(createTitleLabel());

        imagePanel.add(buttonPanel, BorderLayout.NORTH);

        buttonPanel.setOpaque(false);
    }

    public void initContentPanel() {
        contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BorderLayout());

        setBorder(contentPanel, 250, 400, 300, 400);

        contentPanel.add(createStartButton());

        imagePanel.add(contentPanel);
    }

    public JLabel createTitleLabel() {
        JLabel label = new JLabel("학식메뉴 알리미");
        label.setFont(new Font("",Font.CENTER_BASELINE,50));
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    public JButton createStartButton() {
        JButton button = new JButton("시작하기!");
        button.addActionListener(e -> {
            buttonPanel.setBackground(Color.LIGHT_GRAY);

            removeContainer(buttonPanel);
            removeContainer(contentPanel);

            setBorder(contentPanel, 250, 0, 0, 0);

            RecommendPanel recommendPanel = new RecommendPanel(this,
                    user,
                    recommendedRestaurant,
                    favoriteNutrition);

            contentPanel.add(recommendPanel);

            displayPanel.add(pageLabel("-홈 메뉴-"));
            imagePanel.add(displayPanel, BorderLayout.SOUTH);

            CreateMenuPanel createMenuPanel = new CreateMenuPanel(
                    this,
                    contentPanel,
                    systemStatus,
                    currentRestaurants,
                    todayRestaurants,
                    buttonPanel,
                    displayPanel,
                    restaurants,
                    user,
                    recommendedRestaurant,
                    favoriteNutrition,
                    recordedMenus,
                    recordedNutritions
            );

            buttonPanel.add(createMenuPanel);

            updateDisplay();
        });
        return button;
    }

    public void setBorder(JPanel panel, int top, int left, int bottom, int right) {
        panel.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
    }

    public void removeContainer(JPanel panel) {
        panel.removeAll();
    }

    public JLabel pageLabel(String pageContent) {
        JLabel label = new JLabel(pageContent);
        label.setForeground(Color.RED);
        return label;
    }


    public void updateDisplay() {
        buttonPanel.setVisible(false);
        buttonPanel.setVisible(true);
        contentPanel.setVisible(false);
        contentPanel.setVisible(true);
        frame.setVisible(true);
    }

    public JButton closeButton(JFrame frame) {
        JButton button = new JButton("돌아가기");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                frame.dispose();
            }
        });
        return button;
    }
}
