package application;// 내가 원하는 것
// 1. 학생식당 메인메뉴 비교 (4 개)
// 2.
// 3.
// 4.
// 도메인 모델 : menu


import models.Restaurant;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;

public class CafeteriaMenuReccomendator {

    private JFrame frame;
    private JPanel contentPanel;
    private JPanel buttonPanel;

    public static void main(String[] args) {
        CafeteriaMenuReccomendator application = new CafeteriaMenuReccomendator();

        application.run();
    }

    private void run() {


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

            buttonPanel.add(createMenuPanel());

            updateDisplay();
        });
        return button;
    }

    private void setBorder(JPanel panel, int top, int left, int bottom, int right) {
        panel.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
    }

    private void removeContainer(JPanel panel) {
        panel.removeAll();
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel();

        panel.add(todayMenus());

        return panel;
    }

    private void updateDisplay() {
        buttonPanel.setVisible(false);
        buttonPanel.setVisible(true);
        contentPanel.setVisible(false);
        contentPanel.setVisible(true);
        frame.setVisible(true);
    }

    private JButton todayMenus() {
        JButton button = new JButton("오늘의 메뉴 확인하기");
        button.addActionListener(e -> {
            try {
                contentPanel.add(menuPanel());
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        return button;
    }

    private JPanel menuPanel() throws FileNotFoundException {
        JPanel panel = new JPanel();
//        panel.setLayout(new GridLayout(2,2));
        panel.add(geumjeongMenuPanel());
//        panel.add(studentHallMenuPanel());
//        panel.add(staffCafeteriaMenuPanel());

        return panel;
    }


    // 금정회관, 교직원식당, 학생회관
    private JPanel geumjeongMenuPanel() throws FileNotFoundException {
        Restaurant restaurant = new Restaurant("금정회관", new File("./src/main/resources/금정회관.csv"), 3500);

        JPanel panel = new JPanel();
        panel.setBackground(Color.cyan);
        panel.setLayout(new GridLayout(2 + restaurant.categories().length, 1, 0, 30));

        panel.add(cafeteriaNameLabel(restaurant.name()));
        panel.add(cafeteriaPriceLabel(restaurant.price()));

        for (String category : restaurant.categories()) {
            panel.add(new JLabel(category));
        }

        setBorder(contentPanel, 0, 0, 0, 0);
        contentPanel.setLayout(new GridLayout(1,3));
        updateDisplay();
        return panel;
    }

    private JLabel cafeteriaPriceLabel(int price) {
        return new JLabel(price + "원");
    }

    private JLabel cafeteriaNameLabel(String name) {
        return new JLabel(name);
    }

//
//    private JPanel studentHallMenuPanel() {
//        return new JPanel();
//    }
//
//    private JPanel staffCafeteriaMenuPanel() {
//        return new JPanel();
//    }
}
