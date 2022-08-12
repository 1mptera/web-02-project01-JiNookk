package frame;

import application.CafeteriaMenuReccomendator;
import models.Restaurant;
import models.SystemStatus;
import panels.CreateMenuPanel;
import panels.DatePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.List;

public class CalendarFrame extends JFrame {
    private SystemStatus systemStatus;
    private List<Restaurant> restaurants;
    private List<Restaurant> currentRestaurants;
    private CafeteriaMenuReccomendator cafeteriaMenuReccomendator;
    private JPanel contentPanel;
    private JPanel menuPanel;
    private JPanel displayPanel;
    private CreateMenuPanel createMenuPanel;

    public CalendarFrame(SystemStatus systemStatus,
                         List<Restaurant> restaurants,
                         List<Restaurant> currentRestaurants,
                         CafeteriaMenuReccomendator cafeteriaMenuReccomendator,
                         JPanel contentPanel,
                         JPanel menuPanel,
                         JPanel displayPanel,
                         CreateMenuPanel createMenuPanel){
        this.systemStatus = systemStatus;
        this.restaurants = restaurants;
        this.currentRestaurants = currentRestaurants;
        this.cafeteriaMenuReccomendator = cafeteriaMenuReccomendator;
        this.contentPanel = contentPanel;
        this.menuPanel = menuPanel;
        this.displayPanel = displayPanel;
        this.createMenuPanel = createMenuPanel;
        this.setTitle("달력");
        JPanel panel = new JPanel();

        JLabel label = new JLabel("선택된 날짜");
        panel.add(label);

        JTextField text = new JTextField(20);
        text.setEditable(false);
        panel.add(text);

        panel.add(createDateButton(text, this));

        panel.add(showMenusButton(text, this));

        this.getContentPane().add(panel);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private JButton createDateButton(JTextField text, JFrame calendarFrame) {
        JButton button = new JButton("날짜보기");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                text.setText(new DatePicker(calendarFrame).setPickedDate());
            }
        });
        return button;
    }

    private JButton showMenusButton(JTextField text, JFrame calendarFrame) {
        JButton button = new JButton("식단 보기");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String date = text.getText();
                if (!date.equals("")) {
                    systemStatus.setDate(date);

                    systemStatus.initCurrentRestaurant(restaurants, currentRestaurants);

                    cafeteriaMenuReccomendator.removeContainer(contentPanel);
                    cafeteriaMenuReccomendator.removeContainer(menuPanel);
                    cafeteriaMenuReccomendator.removeContainer(displayPanel);

                    try {
                        DatePanel datePanel = new DatePanel(systemStatus);
                        contentPanel.add(datePanel, BorderLayout.NORTH);
                        contentPanel.add(createMenuPanel.menuPanel());
                        displayPanel.add(cafeteriaMenuReccomendator.pageLabel("-오늘의 메뉴-"));
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }

                    cafeteriaMenuReccomendator.updateDisplay();

                    calendarFrame.dispose();
                }
            }
        });
        return button;
    }
}
