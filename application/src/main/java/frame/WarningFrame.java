package frame;

import javax.swing.*;

public class WarningFrame extends JFrame{
    public WarningFrame(){
        this.setTitle("warning");

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(alertPanel(this));

        this.pack();
        this.setLocationRelativeTo(null);
    }

    private JPanel alertPanel(JFrame alert) {
        JPanel panel = new JPanel();
        panel.add(new JLabel("오늘은 이미 입력하셨습니다!"));

        JButton button = new JButton("확인");
        button.addActionListener(e -> {
            alert.dispose();
        });
        panel.add(button);
        return panel;
    }

}
