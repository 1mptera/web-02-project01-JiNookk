package frame;

import models.Menu;
import models.Nutrition;
import models.Restaurant;
import models.SystemStatus;
import utils.RecordLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class AlertFrame extends JFrame{
    private final RecordLoader recordLoader = new RecordLoader();
    private JCheckBox checkBox;
    private String cafeteria;
    private List<Restaurant> todayRestaurants;
    private List<Menu> recordedMenus;
    private List<Nutrition> recordedNutritions;
    private SystemStatus systemStatus;
    private JFrame recordFrame;

    public AlertFrame(JCheckBox checkBox,
                      String cafeteria,
                      List<Restaurant> todayRestaurants,
                      List<Menu> recordedMenus,
                      List<Nutrition> recordedNutritions,
                      SystemStatus systemStatus,
                      JFrame recordFrame){
        this.checkBox = checkBox;
        this.cafeteria = cafeteria;
        this.todayRestaurants = todayRestaurants;
        this.recordedMenus = recordedMenus;
        this.recordedNutritions = recordedNutritions;
        this.systemStatus = systemStatus;
        this.recordFrame = recordFrame;

        this.setTitle("확인");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(300, 400);

        this.add(recordLabel(), BorderLayout.NORTH);
        this.add(confirmPanel(checkBox, cafeteria));

        this.pack();
        this.setLocationRelativeTo(null);
    }

    private JLabel recordLabel() {
        JLabel label = new JLabel("기록하시겠습니까?");
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    private JPanel confirmPanel(JCheckBox checkBox, String cafeteria) {
        JPanel panel = new JPanel();
        JButton confirmButton = new JButton("네");
        confirmButton.addActionListener(e -> {

            Menu recordedMenu = switch (cafeteria) {
                case "금정회관" -> todayRestaurants.get(0).menus().get(0);
                case "학생회관" -> todayRestaurants.get(1).menus().get(0);
                case "교직원식당" -> todayRestaurants.get(2).menus().get(0);
                default -> null;
            };

            recordedMenus.add(recordedMenu);

            Nutrition recordedNutrition = switch (cafeteria) {
                case "금정회관" -> todayRestaurants.get(0).nutritions().get(0);
                case "학생회관" -> todayRestaurants.get(1).nutritions().get(0);
                case "교직원식당" -> todayRestaurants.get(2).nutritions().get(0);
                default -> null;
            };

            recordedNutritions.add(recordedNutrition);

            try {
                recordLoader.saveMenu(recordedMenus);
                recordLoader.saveNutrition(recordedNutritions);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            systemStatus.initRecorded();
            recordFrame.dispose();
            this.dispose();
        });
        panel.add(confirmButton);

        JButton cancelButton = new JButton("아니오");
        cancelButton.addActionListener(e-> {
                checkBox.setSelected(false);

                this.dispose();
        });
        panel.add(cancelButton);

        return panel;
    }

}
