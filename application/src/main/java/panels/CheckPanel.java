package panels;

import frame.RecordFrame;
import models.Menu;
import models.Nutrition;
import models.Restaurant;
import utils.RecordLoader;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class CheckPanel extends JPanel {
    private List<Menu> menus;
    private List<Nutrition> nutritions;
    private RecordFrame recordFrame;
    private RecordLoader recordLoader;

    public CheckPanel(List<Menu> menus, List<Nutrition> nutritions, RecordFrame recordFrame) {
        this.menus = menus;
        this.nutritions = nutritions;
        this.recordFrame = recordFrame;
        this.setLayout(new BorderLayout());

        this.add(buttonPanel(), BorderLayout.NORTH);

        this.add(checkBoxPanel(Restaurant.GEUMJEONG));
//        this.add(checkBoxPanel(Restaurant.STUDENTHALL));
//        this.add(checkBoxPanel(Restaurant.STAFFCAFETERIA));
    }

    private JPanel buttonPanel() {
        JPanel panel = new JPanel();

        panel.add(submitButton());
        panel.add(closeButton());

        return panel;
    }

    private JButton submitButton() {
        JButton button = new JButton("제출");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                recordLoader = new RecordLoader();

                try {
                    recordLoader.saveMenu(menus.get(0));
                    recordLoader.saveNutrition(nutritions.get(0));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                recordFrame.dispose();
            }
        });
        return button;
    }

    private JButton closeButton() {
        JButton button = new JButton("돌아가기");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                recordFrame.dispose();
            }
        });
        return button;
    }

    private JPanel checkBoxPanel(String cafeteria) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3));
        panel.add(createCheckBox());
        panel.add(new JLabel(cafeteria));
        return panel;
    }

    private JCheckBox createCheckBox() {
        JCheckBox checkBox = new JCheckBox();

        return checkBox;
    }
}
