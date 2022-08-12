package panels;

import application.CafeteriaMenuReccomendator;
import frame.AlertFrame;
import models.Menu;
import models.Nutrition;
import models.Restaurant;
import models.SystemStatus;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CheckPanel extends JPanel {
    private CafeteriaMenuReccomendator cafeteriaMenuReccomendator;
    private JFrame recordFrame;
    private List<Restaurant> todayRestaurants;
    private List<Menu> recordedMenus;
    private List<Nutrition> recordedNutritions;
    private SystemStatus systemStatus;

    public CheckPanel(CafeteriaMenuReccomendator cafeteriaMenuReccomendator,
                      JFrame recordFrame,
                      List<Restaurant> todayRestaurants,
                      List<Menu> recordedMenus,
                      List<Nutrition> recordedNutritions,
                      SystemStatus systemStatus) {
        this.cafeteriaMenuReccomendator = cafeteriaMenuReccomendator;
        this.recordFrame = recordFrame;
        this.todayRestaurants = todayRestaurants;
        this.recordedMenus = recordedMenus;
        this.recordedNutritions = recordedNutritions;
        this.systemStatus = systemStatus;

        this.setLayout(new BorderLayout());

        this.add(buttonPanel(), BorderLayout.NORTH);

        this.add(checkBoxContainer());
    }

    private JPanel buttonPanel() {
        JPanel panel = new JPanel();

        panel.add(cafeteriaMenuReccomendator.closeButton(recordFrame));

        return panel;
    }

    private JPanel checkBoxContainer() {
        JPanel panel = new JPanel();
        panel.add(checkBoxPanel(Restaurant.GEUMJEONG));
        panel.add(checkBoxPanel(Restaurant.STUDENTHALL));
        panel.add(checkBoxPanel(Restaurant.STAFFCAFETERIA));
        return panel;
    }

    private JPanel checkBoxPanel(String cafeteria) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3));
        panel.add(createCheckBox(cafeteria));
        panel.add(new JLabel(cafeteria));
        return panel;
    }

    private JCheckBox createCheckBox(String cafeteria) {
        JCheckBox checkBox = new JCheckBox();

        checkBox.addActionListener(e -> {
            AlertFrame alertFrame = new AlertFrame(checkBox,
                    cafeteria,
                    todayRestaurants,
                    recordedMenus,
                    recordedNutritions,
                    systemStatus,
                    recordFrame
            );

            alertFrame.setVisible(true);
        });

        return checkBox;
    }
}
