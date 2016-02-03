package parkingsystem;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Update extends Addwidget  {

    private JLabel total_label,  filled_label,  free_label;
    private JButton total_car,  filled_car,  free_car;
    private JLabel space1,  space2;
    //public JDesktopPane theDesktop;
    private JButton widget;
    //public JPanel main;
    private JButton update;

    public Update(JPanel pon) {
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