package application;// 내가 원하는 것
// 1. 학생식당 메인메뉴 비교 (4 개)
// 2.
// 3.
// 4.
// 도메인 모델 : menu


import javax.swing.*;
import java.awt.*;

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

    private JButton createStartButton() {
        JButton button = new JButton("시작하기!");
        setBorder(contentPanel, 300, 400, 300, 400);
        button.addActionListener(e -> {
            buttonPanel.add(createMenuPanel());
        });
        return button;
    }

    private void setBorder(JPanel panel, int top, int left, int bottom, int right) {
        panel.setBorder(BorderFactory.createEmptyBorder(top,left,bottom,right));
    }

    private void initButtonPanel() {
        buttonPanel = new JPanel();
        frame.add(buttonPanel, BorderLayout.NORTH);
    }

    private JLabel createTitleLabel() {
        JLabel label = new JLabel("학식메뉴 알리미");
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    private void initContentPanel() {
        contentPanel = new JPanel();

        contentPanel.setLayout(new BorderLayout());

        frame.add(contentPanel);
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel();

        panel.add(todayMenus());
        panel.add(recordTodayMenu());
        panel.add(reccomendPersonalMenu());
        panel.add(addDataBase());

        return panel;
    }

    private JButton todayMenus() {
        JButton button = new JButton();

        return button;
    }

    private JButton recordTodayMenu() {
        return new JButton();
    }

    private JButton reccomendPersonalMenu() {
        return new JButton();
    }

    private JButton addDataBase() {
        JButton button = new JButton();
        return button;
    }
}
