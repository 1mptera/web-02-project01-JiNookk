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
import utils.URLRepository;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class CafeteriaMenuReccomendator {

    private JFrame frame;
    private JPanel contentPanel;
    private JPanel buttonPanel;
    private Parser parser;
    private URLRepository urlRepsitory;

    public static void main(String[] args) {
        CafeteriaMenuReccomendator application = new CafeteriaMenuReccomendator();

        application.run();
    }

    private void run() {
        urlRepsitory = new URLRepository();
        parser = new Parser();

        frame = new JFrame("학식메뉴 알리미");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);

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

        panel.setLayout(new GridLayout(1, 3));
        panel.add(geumjeongPanel());
        panel.add(studentHallMenuPanel());
        panel.add(staffCafeteriaMenuPanel());

        return panel;
    }


    // 금정회관, 교직원식당, 학생회관

    private JPanel geumjeongPanel() throws FileNotFoundException {
        Restaurant geumjeong = new Restaurant("금정회관", new File("/Users/jingwook/Desktop/study/programming/megaptera/web-02-project01-JiNookk/application/src/main/resources/menus/금정회관.csv"), new File("/Users/jingwook/Desktop/study/programming/megaptera/web-02-project01-JiNookk/application/src/main/resources/nutritions/금정회관영양성분.csv"), 3500);

        return cafeteriaPanel(geumjeong);
    }

    private JPanel studentHallMenuPanel() throws FileNotFoundException {
        Restaurant studentHall = new Restaurant("학생회관", new File("/Users/jingwook/Desktop/study/programming/megaptera/web-02-project01-JiNookk/application/src/main/resources/menus/학생회관.csv"), new File("/Users/jingwook/Desktop/study/programming/megaptera/web-02-project01-JiNookk/application/src/main/resources/nutritions/학생회관영양성분.csv"), 5500);

        return cafeteriaPanel(studentHall);
    }

    private JPanel staffCafeteriaMenuPanel() throws FileNotFoundException {
        Restaurant staffCafeteria = new Restaurant("교직원식당", new File("/Users/jingwook/Desktop/study/programming/megaptera/web-02-project01-JiNookk/application/src/main/resources/menus/교직원식당.csv"), new File("/Users/jingwook/Desktop/study/programming/megaptera/web-02-project01-JiNookk/application/src/main/resources/nutritions/교직원식당영양성분.csv"), 5500);

        return cafeteriaPanel(staffCafeteria);
    }

    private JPanel cafeteriaPanel(Restaurant restaurant) throws FileNotFoundException {
        JPanel panel = new JPanel();

        Menu menu = parser.parseMenu(restaurant.menuFile());

        Nutrition nutrition = parser.parseNutrition(restaurant.nutritionFile());

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
        panel.add(new JLabel("탄수화물: " + nutrition.carbonHydratePer100g()));
        panel.add(new JLabel("당: " + nutrition.sugarPer100g()));
        panel.add(new JLabel("단백질: " + nutrition.proteinPer100g()));
        panel.add(new JLabel("지방: " + nutrition.fatPer100g()));
        panel.add(new JLabel("포화지방: " + nutrition.saturatedFatPer100g()));
        panel.add(new JLabel("열량: " + nutrition.caloriesPer100g()));

        panel.add(informationUrlButton(menu));

        return panel;
    }

    private JButton informationUrlButton(Menu menu) {
        JButton button = new JButton("자세히");
        button.setForeground(Color.BLUE);
        button.addActionListener(e -> {
            String url = urlRepsitory.url(menu.mainMenu());

            try{
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
