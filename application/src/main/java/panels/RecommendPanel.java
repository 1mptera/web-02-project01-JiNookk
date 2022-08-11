package panels;

import application.CafeteriaMenuReccomendator;
import frame.RecommendFrame;
import models.Restaurant;
import models.User;

import javax.swing.*;

public class RecommendPanel extends JPanel {
    private CafeteriaMenuReccomendator cafeteriaMenuReccomendator;
    private User user;
    private Restaurant recommendedRestaurant;
    private String favoriteNutrition;
    private RecommendFrame recommendFrame;

    public RecommendPanel(CafeteriaMenuReccomendator cafeteriaMenuReccomendator,
                          User user,
                          Restaurant recommendedRestaurant,
                          String favoriteNutrition) {
        this.cafeteriaMenuReccomendator = cafeteriaMenuReccomendator;
        this.user = user;
        this.recommendedRestaurant = recommendedRestaurant;
        this.favoriteNutrition = favoriteNutrition;
        this.setOpaque(false);
        this.add(greetingLabel());
        this.add(confirmButton());
    }

    private JLabel greetingLabel() {
        JLabel label = new JLabel("안녕하세요 " + user.name() + "님, 오늘의 추천메뉴를 받아보시겠어요?");
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    private JButton confirmButton() {
        JButton button = new JButton("추천메뉴 보러가기");
        button.addActionListener(e -> {

            recommendFrame = new RecommendFrame(
                    cafeteriaMenuReccomendator,
                    recommendedRestaurant,
                    user,
                    favoriteNutrition);

        });
        return button;
    }
}

