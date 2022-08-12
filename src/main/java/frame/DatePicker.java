package frame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

 public class DatePicker {
    int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
    int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);;
    JLabel label = new JLabel("", JLabel.CENTER);
    String day = "";
    JDialog dialog;
    JButton[] button = new JButton[49];

    public DatePicker(JFrame parent) {
        dialog = new JDialog();
        dialog.setModal(true);
        String[] header = { "Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat" };

        JPanel monthPanel = new JPanel(new GridLayout(1, 3));
        JButton previous = new JButton("<<");
        previous.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                month--;
                displayDate();
            }
        });
        monthPanel.add(previous);

        monthPanel.add(label);

        JButton next = new JButton(">>");
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                month++;
                displayDate();
            }
        });
        monthPanel.add(next);

        JPanel dayPanel = new JPanel(new GridLayout(7, 7));
        dayPanel.setPreferredSize(new Dimension(430, 120));

        // for (int x = 0; x <button> 6) {
        for (int x = 0; x < button.length; x++) {
            final int selection = x;
            button[x] = new JButton();
            button[x].setFocusPainted(false);
            button[x].setBackground(Color.white);
            if (x > 6) {
                button[x].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        day = button[selection].getActionCommand();
                        dialog.dispose();
                    }
                });
            }
            if (x < 7) {
                button[x].setText(header[x]);
                button[x].setForeground(Color.red);
            }
            dayPanel.add(button[x]);
        }

        dialog.add(dayPanel, BorderLayout.CENTER);
        dialog.add(monthPanel, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        displayDate();
        dialog.setVisible(true);
    }

    public void displayDate() {
        for (int x = 7; x < button.length; x++)
            button[x].setText("");
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                "MMMM yyyy");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(year, month, 1);
        int dayOfWeek = cal.get(java.util.Calendar.DAY_OF_WEEK);
        int daysInMonth = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
        for (int x = 6 + dayOfWeek, day = 1; day <= daysInMonth; x++, day++)
            button[x].setText("" + day);
        label.setText(sdf.format(cal.getTime()));
        dialog.setTitle("Date Picker");
    }

    public String setPickedDate() {
        if (day.equals(""))
            return day;
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                "yyMMdd");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(year, month, Integer.parseInt(day));
        return sdf.format(cal.getTime());
    }
}
