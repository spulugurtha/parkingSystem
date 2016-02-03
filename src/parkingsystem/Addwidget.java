package parkingsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Addwidget extends JPanel implements ActionListener {

    private JLabel total_label,  filled_label,  free_label;
    private JButton total_car,  filled_car,  free_car;
    private JLabel space1,  space2;
    public JDesktopPane theDesktop;
    private JButton widget;
    public JPanel main;
    private JButton update;

    public Addwidget() {
        this.theDesktop = (JDesktopPane) theDesktop;
        JLabel space3;
        JLabel click, click2;
        main = new JPanel();
        space3 = new JLabel();
        space1 = new JLabel();
        space2 = new JLabel();
        main.setLayout(new GridLayout(5, 2, 5, 15));
        total_label = new JLabel("Total Slots:  ");
        total_car = new JButton();
        total_car.setBackground(Color.ORANGE);
        main.add(total_label);
        main.add(total_car);

        filled_label = new JLabel("Filled Slots:    ");
        filled_car = new JButton();
        filled_car.setBackground(Color.RED);
        filled_label.setLabelFor(filled_car);
        main.add(filled_label);
        main.add(filled_car);

        free_label = new JLabel("Free Slots:    ");
        free_car = new JButton();
        free_car.setBackground(Color.GREEN);
        main.add(free_label);
        main.add(free_car);

        update = new JButton("Update");
        main.add(space3);
        main.add(update);
        update.setActionCommand("update");
        update.addActionListener(this);
        click = new JLabel("Click  Update  to", JLabel.RIGHT);
        main.add(click);
        click2 = new JLabel("Update Status");
        main.add(click2);

        {
            try {
                int free = 0, filled = 0, total = 0;
                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                Connection con = DriverManager.getConnection("jdbc:odbc:parking", "sekhar", "sekhar");
                Statement st = con.createStatement();
                String sql = "select * from slot";
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    total++;
                    if (rs.getString(2).equals("filled")) {
                        filled++;
                    } else {
                        free++;
                    }
                }
                total_car.setText("        " + total + "    ");
                free_car.setText("        " + free + "     ");
                filled_car.setText("        " + filled + "   ");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(new MainFrame(),
                        "Error:" + ex.getMessage(),
                        "Status Panel",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            main.setVisible(true);
            main.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createTitledBorder("Status"),
                    BorderFactory.createEmptyBorder()));


            main.setBounds(850,50, 250, 250);
            }
    }
    public void actionPerformed(ActionEvent e) {
        if ("update".equals(e.getActionCommand()))
        {
            try {
                int free = 0, filled = 0, total = 0;
                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                Connection con = DriverManager.getConnection("jdbc:odbc:parking", "sekhar", "sekhar");
                Statement st = con.createStatement();
                String sql = "select * from slot";
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    total++;
                    if (rs.getString(2).equals("filled")) {
                        filled++;
                    } else {
                        free++;
                    }
                }
                total_car.setText("        " + total + "    ");
                free_car.setText("        " + free + "     ");
                filled_car.setText("        " + filled + "   ");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(new MainFrame(),
                        "Error:" + ex.getMessage(),
                        "Status Panel",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    public JPanel  getPanel() {
        return main;
    }
}