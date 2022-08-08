package application;// 내가 원하는 것
// 1. 학생식당 메인메뉴 비교 (4 개)
// 2.
// 3.
// 4.
// 도메인 모델 : menu


import models.Menu;
import models.Nutrition;
import models.Restaurant;
//import panel.MenuPanel;
import utils.Parser;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;

public class CafeteriaMenuReccomendator {

    private JFrame frame;
    private JPanel contentPanel;
    private JPanel buttonPanel;
    private Parser parser;

    public static void main(String[] args) {
        CafeteriaMenuReccomendator application = new CafeteriaMenuReccomendator();

        application.run();
    }

    private void run() {
        parser = new Parser();

        frame = new JFrame("학식메뉴 알리미");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);

        initButtonPanel();

        initContentPanel();

        buttonPanel.add(createTitleLabel());

        contentPanel.add(createStartButton());


        frame.setVisible(true);
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

        panel.add(todayMenus());

        return panel;
    }

    private JButton todayMenus() {
        JButton button = new JButton("오늘의 메뉴 확인하기");
        button.addActionListener(e -> {
            try {
                removeContainer(buttonPanel);

                buttonPanel.add(backToMenuButton());

                contentPanel.add(menuPanel());
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        return button;
    }

    private JButton backToMenuButton() {
        JButton button = new JButton("돌아가기");
        button.addActionListener(e -> {
            removeContainer(buttonPanel);
            removeContainer(contentPanel);
            buttonPanel.add(createMenuPanel());

            updateDisplay();
        });
        return button;
    }

    private JPanel menuPanel() throws FileNotFoundException {
        JPanel panel = new JPanel();
//        panel.setLayout(new GridLayout(2,2));
        panel.add(geumjeongPanel());
//        panel.add(studentHallMenuPanel());
//        panel.add(staffCafeteriaMenuPanel());

        return panel;
    }


    // 금정회관, 교직원식당, 학생회관

    private JPanel geumjeongPanel() throws FileNotFoundException {
        Restaurant restaurant = new Restaurant("금정회관",
                new File("./src/main/resources/menus/금정회관.csv"),
                new File("/Users/jingwook/Desktop/study/programming/megaptera/web-02-project01-JiNookk/application/src/main/resources/nutritions/금정회관영양성분.csv")
                , 3500);

        JPanel panel = new JPanel();

        Menu menu = parser.parseMenu(restaurant.menuFile());

        Nutrition nutrition = parser.parseNutrition(restaurant.nutritionFile());

        panel.add(cafeteriaMenu(restaurant, menu));
        panel.add(menuNutritions(nutrition));

        setBorder(contentPanel, 0, 0, 0, 0);
        updateDisplay();
        return panel;
    }
    private JPanel cafeteriaMenu(Restaurant restaurant, Menu menu) throws FileNotFoundException {
        JPanel panel = new JPanel();
        panel.setBackground(Color.cyan);
        panel.setLayout(new GridLayout(7, 1, 0, 30));

        panel.add(new JLabel(restaurant.name()));
        panel.add(new JLabel(restaurant.price() + "원"));
        panel.add(new JLabel(menu.rice()));
        panel.add(new JLabel(menu.mainMenu()));
        panel.add(new JLabel(menu.sideMenu()));
        panel.add(new JLabel(menu.soup()));
        panel.add(new JLabel(menu.gimchi()));

        return panel;
    }

    private JPanel menuNutritions(Nutrition nutrition) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1,10,30));

        panel.add(new JLabel("메인 메뉴 영양성분 (100g당)"));
        panel.add(new JLabel("탄수화물: " + nutrition.carbonHydratePer100g()));
        panel.add(new JLabel("당: " + nutrition.sugarPer100g()));
        panel.add(new JLabel("단백질: " + nutrition.proteinPer100g()));
        panel.add(new JLabel("지방: " + nutrition.fatPer100g()));
        panel.add(new JLabel("포화지방: " + nutrition.saturatedFatPer100g()));
        panel.add(new JLabel("열량: " + nutrition.caloriesPer100g()));

        return panel;
    }


    //
//    private JPanel studentHallMenuPanel() {
//        return new JPanel();
//    }
//
//    private JPanel staffCafeteriaMenuPanel() {
//        return new JPanel();
//    }
    public void updateDisplay() {
        buttonPanel.setVisible(false);
        buttonPanel.setVisible(true);
        contentPanel.setVisible(false);
        contentPanel.setVisible(true);
        frame.setVisible(true);
    }
}
