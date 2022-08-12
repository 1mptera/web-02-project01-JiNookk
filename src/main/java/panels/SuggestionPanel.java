package panels;

import application.CafeteriaMenuReccomendator;

import javax.swing.*;
import java.awt.*;

public class SuggestionPanel extends JPanel {
    public SuggestionPanel(CafeteriaMenuReccomendator cafeteriaMenuReccomendator, JFrame recommendFrame){
            this.setLayout(new GridLayout(2, 1, 0, 15));
            this.add(cafeteriaMenuReccomendator.closeButton(recommendFrame));
            this.add(suggestionLabel());
            }

    private JLabel suggestionLabel() {
        JLabel label = new JLabel("오늘 이 메뉴는 어떠신가요?");
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

}
