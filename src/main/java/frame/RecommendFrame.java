package frame;

import application.CafeteriaMenuReccomendator;
import models.Menu;
import models.Nutrition;
import models.Restaurant;
import models.User;
import panels.CafeteriaMenu;
import panels.MenuNutritions;
import panels.SuggestionPanel;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

public class RecommendFrame extends JFrame {
    private Restaurant recommendedRestaurant;
    private User user;
    private String favoriteNutrition;

    public RecommendFrame(CafeteriaMenuReccomendator cafeteriaMenuReccomendator,
                          Restaurant recommendedRestaurant,
                          User user,
                          String favoriteNutrition){
        this.recommendedRestaurant = recommendedRestaurant;
        this.user = user;
        this.favoriteNutrition = favoriteNutrition;

        this.setTitle("오늘의 추천메뉴");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(400, 600);

        SuggestionPanel suggestionPanel = new SuggestionPanel(
                cafeteriaMenuReccomendator,
                this
        );

        try {
            this.add(suggestionPanel, BorderLayout.NORTH);
            this.add(reccomendMenuPanel());
            this.add(preferencePanel(), BorderLayout.SOUTH);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private JPanel reccomendMenuPanel() throws FileNotFoundException {
        JPanel panel = new JPanel();

        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        panel.setLayout(new FlowLayout(FlowLayout.LEADING, 50, 30));

        Menu menu = recommendedRestaurant.menus().get(0);
        Nutrition nutrition = recommendedRestaurant.nutritions().get(0);

        CafeteriaMenu cafeteriaMenu = new CafeteriaMenu(recommendedRestaurant, menu);
        panel.add(cafeteriaMenu);

        MenuNutritions menuNutritions = new MenuNutritions(nutrition, menu);
        panel.add(menuNutritions);

        return panel;
    }

    private JPanel preferencePanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(0, 30));
        panel.add(preferenceLabel());
        return panel;
    }

    private JLabel preferenceLabel() {
        JLabel label = new JLabel(user.name() + "님이 선호하는 영양성분: " + favoriteNutrition);
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }
}
