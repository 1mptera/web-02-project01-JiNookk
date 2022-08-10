package frame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalendarFrame{

    private final JTextField text;
    private final JFrame frame;

    public static void main(String[] args) {
        CalendarFrame application = new CalendarFrame();

    }

    public CalendarFrame() {
        frame = new JFrame("달력");

        JPanel panel = new JPanel();

        JLabel label = new JLabel("선택된 날짜");
        panel.add(label);

        text = new JTextField(20);
        panel.add(text);

        panel.add(createDateButton());

        panel.add(showMenusButton());

        frame.getContentPane().add(panel);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JButton createDateButton() {
        JButton button = new JButton("날짜보기");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                text.setText(new DatePicker(frame).setPickedDate());
            }
        });
        return button;
    }

    private JButton showMenusButton() {
        JButton button = new JButton("식단 보기");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //해당 날짜에 맞는 식단을 보여준다.
                frame.dispose();
            }
        });
        return button;
    }
}
