package application;// 내가 원하는 것
// 1. 학생식당 메인메뉴 비교 (4 개)
// 2.
// 3.
// 4.
// 도메인 모델 : menu


import frame.RecordFrame;
import models.Menu;
import models.Nutrition;
import models.Restaurant;
//import panel.MenuPanel;
import utils.Loader;
import utils.Sort;
import utils.URLRepository;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class CafeteriaMenuReccomendator {

    private JFrame frame;
    private JPanel contentPanel;
    private JPanel buttonPanel;
    private Loader loader;
    private URLRepository urlRepsitory;
    private List<Nutrition> nutritionLists;
    private Sort sort;
    private JPanel menuPanel;
    private List<Menu> menus;
    private List<Restaurant> restaurants;

    public static void main(String[] args) {
        CafeteriaMenuReccomendator application = new CafeteriaMenuReccomendator();

        application.run();
    }

    private void run() {
        urlRepsitory = new URLRepository();
        loader = new Loader();
        sort = new Sort();

        restaurants = new ArrayList<>();
        menus = new ArrayList<>();
        nutritionLists = new ArrayList<>();

        frame = new JFrame("학식메뉴 알리미");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);
        frame.setLocationRelativeTo(null);

        initButtonPanel();

        initContentPanel();

        initMenuPanel();

        buttonPanel.add(createTitleLabel());

        contentPanel.add(createStartButton());

        frame.setVisible(true);
    }

    private void initMenuPanel() {

    }

    private void initButtonPanel() {
        buttonPanel = new JPanel();
        frame.add(buttonPanel, BorderLayout.NORTH);
    }

    private void initContentPanel() {
        contentPanel = new JPanel();

        contentPanel.setLayout(new BorderLayout());

        frame.add(contentPanel);
    }

    private JLabel createTitleLabel() {
        JLabel label = new JLabel("학식메뉴 알리미");
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    private JButton createStartButton() {
        JButton button = new JButton("시작하기!");
        setBorder(contentPanel, 300, 400, 300, 400);
        button.addActionListener(e -> {
            removeContainer(buttonPanel);
            removeContainer(contentPanel);

//            MenuPanel menuPanel = new MenuPanel(frame, buttonPanel, contentPanel);

            buttonPanel.add(createMenuPanel());

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

    private JPanel createMenuPanel() { // TODO : 홈 패널
        JPanel panel = new JPanel();

        panel.add(todayMenuButton());
        panel.add(recordMenuButton());

        return panel;
    }

    private JButton recordMenuButton() {
        JButton button = new JButton("오늘의 메뉴 기록하기");
        button.addActionListener(e -> {
            RecordFrame recordFrame = new RecordFrame(menus, nutritionLists);

        });
        return button;
    }

    private JButton todayMenuButton() {
        JButton button = new JButton("오늘의 메뉴 확인하기");
        button.addActionListener(e -> {
            try {
                resetRestaurants();

                removeContainer(buttonPanel);
                removeContainer(contentPanel);

                buttonPanel.add(menuOptionPanel());

                contentPanel.add(menuPanel());
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        return button;
    }

    private void resetRestaurants() {
        for (int i = 0; i < restaurants.size(); i += 1) {
            restaurants.remove(restaurants.get(0));
        }
    }

    private JPanel menuOptionPanel() {              //TODO : 정렬 버튼이 담긴 패널
        JPanel panel = new JPanel();
        panel.add(sortButton(Nutrition.PROTEIN));
        panel.add(sortButton(Nutrition.CALORIES));
        panel.add(sortButton(Nutrition.SATURATEDFAT));
        panel.add(backToMenuButton());
        return panel;
    }

    private JButton sortButton(String nutrition) {
        JButton button = new JButton(nutrition + " 정렬");
        button.addActionListener(e -> {
            String[] sortedCafeteriaNames = sort.sortByNutrition(restaurants, nutrition);

            removeContainer(contentPanel);
            removeContainer(menuPanel);

            contentPanel.add(menuPanel);

            for (String sortedCafeteriaName : sortedCafeteriaNames) {
                if (sortedCafeteriaName.equals("금정회관")) {
                    try {
                        menuPanel.add(geumjeongPanel());
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                if (sortedCafeteriaName.equals("학생회관")) {
                    try {
                        menuPanel.add(studentHallMenuPanel());
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                if (sortedCafeteriaName.equals("교직원식당")) {
                    try {
                        menuPanel.add(staffCafeteriaMenuPanel());
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

            updateDisplay();
        });
        return button;
    }

//  메인메뉴 보기 -> 단백질 정렬 -> 돌아가기
//  -> 메인메뉴 보기 -> 단백질 정렬 : 뷰 2개

    private JButton backToMenuButton() {
        JButton button = new JButton("돌아가기");
        button.addActionListener(e -> {
            removeContainer(buttonPanel);
            removeContainer(menuPanel);
            removeContainer(contentPanel);

            buttonPanel.add(createMenuPanel());

            updateDisplay();
        });
        return button;
    }

    private JPanel menuPanel() throws FileNotFoundException {           // TODO : 메뉴와 영양성분이 배치되는 패널
        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(1, 3));
        menuPanel.add(geumjeongPanel());
        menuPanel.add(studentHallMenuPanel());
        menuPanel.add(staffCafeteriaMenuPanel());

        return menuPanel;
    }

    // 금정회관, 교직원식당, 학생회관
    private JPanel geumjeongPanel() throws FileNotFoundException {
        File menuFile = new File("/Users/jingwook/Desktop/study/programming/megaptera/web-02-project01-JiNookk/" +
                "application/src/main/resources/menus/금정회관.csv");

        File nutritionFile = new File("/Users/jingwook/Desktop/study/programming/megaptera/web-02-project01-JiNookk" +
                "/application/src/main/resources/nutritions/금정회관영양성분.csv");

        Menu menu = loader.loadMenu(menuFile);

        Nutrition nutrition = loader.loadNutrition(nutritionFile);

        Restaurant geumjeong = new Restaurant("금정회관", menu, nutrition, 3500);

        restaurants.add(geumjeong);

        return cafeteriaPanel(geumjeong);
    }

    private JPanel studentHallMenuPanel() throws FileNotFoundException {
        File menuFile = new File("/Users/jingwook/Desktop/study/programming/megaptera/web-02-project01-JiNookk/" +
                "application/src/main/resources/menus/학생회관.csv");

        File nutritionFile = new File("/Users/jingwook/Desktop/study/programming/megaptera/web-02-project01-JiNookk" +
                "/application/src/main/resources/nutritions/학생회관영양성분.csv");

        Menu menu = loader.loadMenu(menuFile);


        Nutrition nutrition = loader.loadNutrition(nutritionFile);


        Restaurant studentHall = new Restaurant("학생회관", menu, nutrition, 5500);

        restaurants.add(studentHall);

        return cafeteriaPanel(studentHall);
    }

    private JPanel staffCafeteriaMenuPanel() throws FileNotFoundException {
        File menuFile = new File("/Users/jingwook/Desktop/study/programming/megaptera/web-02-project01-JiNookk/" +
                "application/src/main/resources/menus/교직원식당.csv");

        File nutritionFile = new File("/Users/jingwook/Desktop/study/programming/megaptera/web-02-project01-JiNookk" +
                "/application/src/main/resources/nutritions/교직원식당영양성분.csv");

        Menu menu = loader.loadMenu(menuFile);

        Nutrition nutrition = loader.loadNutrition(nutritionFile);

        Restaurant staffCafeteria = new Restaurant("교직원식당", menu, nutrition, 5500);

        restaurants.add(staffCafeteria);

        return cafeteriaPanel(staffCafeteria);
    }

    private JPanel cafeteriaPanel(Restaurant restaurant) throws FileNotFoundException {
        JPanel panel = new JPanel();

        Menu menu = restaurant.menu();

        Nutrition nutrition = restaurant.nutrition();

        panel.add(cafeteriaMenu(restaurant, menu));
        panel.add(menuNutritions(nutrition, menu));

        setBorder(contentPanel, 0, 0, 0, 0);
        updateDisplay();
        return panel;
    }

    private JPanel cafeteriaMenu(Restaurant restaurant, Menu menu) throws FileNotFoundException {
        JPanel panel = new JPanel();
        panel.setBackground(Color.cyan);
        panel.setLayout(new GridLayout(8, 1, 0, 30));

        panel.add(new JLabel(restaurant.name()));
        panel.add(new JLabel(restaurant.price() + "원"));
        panel.add(new JLabel(menu.rice()));
        panel.add(new JLabel(menu.mainMenu()));
        panel.add(new JLabel(menu.sideMenu()));
        panel.add(new JLabel(menu.soup()));
        panel.add(new JLabel(menu.gimchi()));
        JButton button = new JButton();
        button.setBorderPainted(false);
        panel.add(button);

        return panel;
    }

    private JPanel menuNutritions(Nutrition nutrition, Menu menu) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 1, 0, 30));

        panel.setBackground(Color.YELLOW);

        panel.add(new JLabel("메인 메뉴 영양성분 (100g당)"));
        panel.add(new JLabel("탄수화물: " + nutrition.carbonHydrate()));
        panel.add(new JLabel("당: " + nutrition.sugar()));
        panel.add(new JLabel("단백질: " + nutrition.protein()));
        panel.add(new JLabel("지방: " + nutrition.fat()));
        panel.add(new JLabel("포화지방: " + nutrition.saturatedFat()));
        panel.add(new JLabel("열량: " + nutrition.calories()));

        panel.add(informationUrlButton(menu));

        return panel;
    }

    private JButton informationUrlButton(Menu menu) {
        JButton button = new JButton("자세히");
        button.setForeground(Color.BLUE);
        button.addActionListener(e -> {
            String url = urlRepsitory.url(menu.mainMenu());

            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (URISyntaxException ex) {
                ex.printStackTrace();
            }
        });

        button.setBorderPainted(false);

        return button;
    }

    public void updateDisplay() {
        buttonPanel.setVisible(false);
        buttonPanel.setVisible(true);
        contentPanel.setVisible(false);
        contentPanel.setVisible(true);
        frame.setVisible(true);
    }
}
