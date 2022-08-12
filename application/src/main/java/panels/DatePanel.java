package panels;

import models.SystemStatus;

import javax.swing.*;
import java.awt.*;

public class DatePanel extends JPanel {
    private SystemStatus systemStatus;

    public DatePanel(SystemStatus systemStatus) {
        this.systemStatus = systemStatus;

        this.setOpaque(false);
        this.add(dateLabel());
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
    }

    private JLabel dateLabel() {
        JLabel label = new JLabel(systemStatus.date() + "일 학식메뉴");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Courier", Font.BOLD, 20));
        return label;
    }
}
