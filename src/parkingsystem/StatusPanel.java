package parkingsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class StatusPanel extends JInternalFrame 
{

    private JLabel total_label,  filled_label,  free_label;
    private JTextField total_car,  filled_car,  free_car;
    //private JTextField total_bike,  filled_bike,  free_bike;
    private JLabel space1,  space2;
    public  JDesktopPane theDesktop;
    private JButton widget;
    public StatusPanel() {
        super("Status Of The Vehicles",false, true, false, true);
        setLayout(new GridLayout(3,2));
       
        total_label = new JLabel("Total Number Of Slots");
        total_car = new JTextField();
        total_label.setLabelFor(total_car);
        add(total_label);
        add(total_car);
        filled_label = new JLabel("Filled Slots");
        filled_car = new JTextField();
        filled_label.setLabelFor(filled_car);
        add(filled_label);
        add(filled_car);
        free_label = new JLabel("Free Slots");
        free_car = new JTextField();
        free_label.setLabelFor(free_car);
        add(free_label);
        add(free_car);
        try{
            int free=0,filled=0,total=0;
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                    Connection con = DriverManager.getConnection("jdbc:odbc:parking", "sekhar", "sekhar");
                    Statement st=con.createStatement();
                    String sql="select * from slot";
                    ResultSet rs=st.executeQuery(sql);
                    while(rs.next())
                    {total++;
                        if(rs.getString(2).equals("filled"))
                        {
                            filled++;
                        }
                        else
                        {
                         free++;
                        }
                    }
                    total_car.setText(""+total);
                    free_car.setText(""+free);
                    filled_car.setText(""+filled);


        }
        catch(Exception ex){
        JOptionPane.showMessageDialog(new VehicleArrivalPanel(),
                            "Error:" +ex.getMessage(),
                            "Status Panel",
                            JOptionPane.INFORMATION_MESSAGE);
        }
        setBounds(600,100,100,600);
        /* setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Display Phase"),
            BorderFactory.createEmptyBorder(5,5,5,5)));
        */pack();
    
        setVisible(true);

    }
    
}