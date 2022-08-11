package panels;

import models.Menu;
import models.Nutrition;
import utils.URLRepository;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MenuNutritions extends JPanel {
    private final URLRepository urlRepository;
    private Nutrition nutrition;
    private Menu menu;

    public MenuNutritions(Nutrition nutrition, Menu menu) {
        urlRepository = new URLRepository();

        this.nutrition = nutrition;
        this.menu = menu;

        this.setOpaque(false);
        this.setLayout(new GridLayout(9, 1, 0, 21));

        this.add(new JLabel());
        this.add(new JLabel("메인 메뉴 영양성분 (100g당)"));
        this.add(new JLabel(Nutrition.CARBONHYDRATE + ": " + nutrition.carbonHydrate()));
        this.add(new JLabel(Nutrition.SUGAR + ": " + nutrition.sugar()));
        this.add(new JLabel(Nutrition.PROTEIN + ": " + nutrition.protein()));
        this.add(new JLabel(Nutrition.FAT + ": " + nutrition.fat()));
        this.add(new JLabel(Nutrition.SATURATEDFAT + ": " + nutrition.saturatedFat()));
        this.add(new JLabel(Nutrition.CALORIES + ": " + nutrition.calories()));

        this.add(informationUrlButton(menu));
    }

    public JButton informationUrlButton(Menu menu) {
        JButton button = new JButton("자세히");
        button.setForeground(Color.BLUE);
        button.addActionListener(e -> {
            String url = urlRepository.url(menu.mainMenu());

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
}
