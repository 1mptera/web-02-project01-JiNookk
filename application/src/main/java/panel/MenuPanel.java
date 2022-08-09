//package panel;
//
//import application.CafeteriaMenuReccomendator;
//import models.Menu;
//import models.Nutrition;
//import models.Restaurant;
//
//import javax.swing.*;
//import java.awt.*;
//import java.io.File;
//import java.io.FileNotFoundException;
//
//public class MenuPanel extends JPanel {
//    CafeteriaMenuReccomendator menuReccomendator = new CafeteriaMenuReccomendator();
//    private JFrame frame;
//    private JPanel buttonPanel;
//    private JPanel contentPanel;
//
//    public MenuPanel(JFrame frame, JPanel buttonPanel, JPanel contentPanel) {
//        this.frame = frame;
//        this.buttonPanel = buttonPanel;
//        this.contentPanel = contentPanel;
//
//        this.add(todayMenusButton());
//
//
//
//
//    }
//    private JButton todayMenusButton() {
//        JButton button = new JButton("오늘의 메뉴 확인하기");
//        button.addActionListener(e -> {
//            try {
//                menuReccomendator.removeContainer(buttonPanel);
//
//                buttonPanel.add(backToMenuButton());
//
//                contentPanel.add(menuPanel());
//            } catch (FileNotFoundException ex) {
//                throw new RuntimeException(ex);
//            }
//        });
//        return button;
//    }
//
//
//    private JButton backToMenuButton() {
//        JButton button = new JButton("돌아가기");
//        button.addActionListener(e -> {
//            menuReccomendator.removeContainer(buttonPanel);
//            menuReccomendator.removeContainer(contentPanel);
//
////            buttonPanel.add()
//        });
//        return button;
//    }
//
//    private JPanel menuPanel() throws FileNotFoundException {
//        JPanel panel = new JPanel();
////        panel.setLayout(new GridLayout(2,2));
//        panel.add(geumjeongPanel());
////        panel.add(studentHallMenuPanel());
////        panel.add(staffCafeteriaMenuPanel());
//
//        return panel;
//    }
//
//
//    // 금정회관, 교직원식당, 학생회관
//
//    private JPanel geumjeongPanel() throws FileNotFoundException {
//        Restaurant restaurant = new Restaurant("금정회관",
//                new File("./src/main/resources/menus/금정회관.csv"),
//                new File("/Users/jingwook/Desktop/study/programming/megaptera/web-02-project01-JiNookk/application/src/main/resources/nutritions/금정회관영양성분.csv")
//                , 3500);
//
//        JPanel panel = new JPanel();
//
//        Menu menu = parser.parseMenu(restaurant.menuFile());
//
//        Nutrition nutrition = parser.parseNutrition(restaurant.nutritionFile());
//
//        panel.add(cafeteriaMenu(restaurant, menu));
//        panel.add(menuNutritions(nutrition));
//
//        menuReccomendator.setBorder(contentPanel, 0, 0, 0, 0);
//        menuReccomendator.updateDisplay();
//        return panel;
//    }
//    private JPanel cafeteriaMenu(Restaurant restaurant, Menu menu) throws FileNotFoundException {
//        JPanel panel = new JPanel();
//        panel.setBackground(Color.cyan);
//        panel.setLayout(new GridLayout(7, 1, 0, 30));
//
//        panel.add(new JLabel(restaurant.name()));
//        panel.add(new JLabel(restaurant.price() + "원"));
//        panel.add(new JLabel(menu.rice()));
//        panel.add(new JLabel(menu.mainMenu()));
//        panel.add(new JLabel(menu.sideMenu()));
//        panel.add(new JLabel(menu.soup()));
//        panel.add(new JLabel(menu.gimchi()));
//
//        return panel;
//    }
//
//    private JPanel menuNutritions(Nutrition nutrition) {
//        JPanel panel = new JPanel();
//        panel.setLayout(new GridLayout(7, 1,10,30));
//
//        panel.add(new JLabel("메인 메뉴 영양성분 (100g당)"));
//        panel.add(new JLabel("탄수화물: " + nutrition.carbonHydrate()));
//        panel.add(new JLabel("당: " + nutrition.sugar()));
//        panel.add(new JLabel("단백질: " + nutrition.protein()));
//        panel.add(new JLabel("지방: " + nutrition.fat()));
//        panel.add(new JLabel("포화지방: " + nutrition.saturatedFat()));
//        panel.add(new JLabel("열량: " + nutrition.calories()));
//
//        return panel;
//    }
//}
//
