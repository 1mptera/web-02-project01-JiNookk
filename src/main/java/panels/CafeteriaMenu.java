package panels;

import models.Menu;
import models.Restaurant;

import javax.swing.*;
import java.awt.*;

public class CafeteriaMenu extends JPanel{
    public CafeteriaMenu(Restaurant restaurant, Menu menu){
            this.setOpaque(false);
            this.setLayout(new GridLayout(8, 1, 0, 30));

            this.add(restaurantNameLabel(restaurant));
            this.add(new JLabel(restaurant.price() + "원"));
            this.add(new JLabel(menu.rice()));
            this.add(mainMenuLabel(menu));
            this.add(new JLabel(menu.sideMenu()));
            this.add(new JLabel(menu.soup()));
            this.add(new JLabel(menu.gimchi()));

            JButton button = new JButton();
            button.setBorderPainted(false);
            this.add(button);

    }

    private JLabel restaurantNameLabel(Restaurant restaurant) {
        JLabel label = new JLabel(restaurant.name());
        label.setFont(new Font("Courier",Font.BOLD,17));
        return label;
    }

    private JLabel mainMenuLabel(Menu menu) {
        JLabel label = new JLabel(menu.mainMenu());
        label.setFont(new Font("Courier",Font.BOLD,14));
        label.setForeground(Color.ORANGE);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return label;
    }
}
