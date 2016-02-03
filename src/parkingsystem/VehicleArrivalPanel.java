package parkingsystem;


import com.jtattoo.plaf.noire.NoireLookAndFeel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Printable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.sql.*;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class VehicleArrivalPanel extends JInternalFrame implements ActionListener {

    private JComboBox vehicleTypeBox,  userTypeBox;
    private JLabel vehicleTypeLabel,  userTypeLabel,  vehicleNumberLabel,  inTimeLabel,  slotNumberLabel,vehicleNumberField,  inTimeField;
    private JTextField   slotNumberField;
    private JButton billButton,  resetButton;
    private String vehicleTypes[] = {"Car", "Bike"},  fields[];
    private String userTypes[] = {"Monthly", "Daily"};
    static final int xOffset = 30,  yOffset = 30;
    static int openFrameCount = 0,  i,  temp;
    public int days,dailyday;
    private LookAndFeelInfo[] looks;
    public VehicleArrivalPanel() {
        super("Registered Users Vehicle Arrival",false, true, false, true);
        ++openFrameCount;

        setLayout(new GridLayout(6, 2));
        setBounds(100,100,350,200);
        fields = new String[6];
          looks=UIManager.getInstalledLookAndFeels();
        try{
        UIManager.setLookAndFeel(new NoireLookAndFeel());
        SwingUtilities.updateComponentTreeUI(this);
        }
        catch(Exception e){}
        vehicleTypeLabel = new JLabel("Vehicle Type:");
        //vehicleTypeLabel.setBorder(BorderFactory.createEtchedBorder());
        vehicleTypeBox = new JComboBox(vehicleTypes);
        vehicleTypeBox.setToolTipText("Select the Vehicle Type");
        add(vehicleTypeLabel);
        add(vehicleTypeBox);

        userTypeLabel = new JLabel("User Type:");
        //userTypeLabel.setBorder(BorderFactory.createEtchedBorder());
        userTypeBox = new JComboBox(userTypes);
        userTypeBox.setToolTipText("Select User Type");
        add(userTypeLabel);
        add(userTypeBox);

        slotNumberLabel = new JLabel("Slot Number:");
        //slotNumberLabel.setBorder(BorderFactory.createEtchedBorder());
        slotNumberField = new JTextField(20);
        slotNumberField.setToolTipText("Enter the Slot Number");
        add(slotNumberLabel);
        add(slotNumberField);

        vehicleNumberLabel = new JLabel("Vehicle Number:");
        //vehicleNumberLabel.setBorder(BorderFactory.createEtchedBorder());
        vehicleNumberField = new JLabel();
        vehicleNumberField.setToolTipText("Enter the Vehicle Number");
        add(vehicleNumberLabel);
        add(vehicleNumberField);

        inTimeLabel = new JLabel("In Time:");
        //inTimeLabel.setBorder(BorderFactory.createEtchedBorder());
        inTimeField = new JLabel();
        inTimeField.setToolTipText("Enter the In-Time");
        add(inTimeLabel);
        add(inTimeField);



        billButton = new JButton("Bill");
        billButton.setToolTipText("Click to bill customer");
        billButton.setActionCommand("Bill");
        add(billButton);
        billButton.addActionListener(this);

        resetButton = new JButton("Reset");
        resetButton.setToolTipText("Click to reset all fields");
        resetButton.setActionCommand("reset");
        add(resetButton);
        resetButton.addActionListener(this);

        pack();
        setLocation(xOffset * openFrameCount, yOffset * openFrameCount);
    }


    public void actionPerformed(ActionEvent e) {
        if ("reset".equals(e.getActionCommand()))
        {
            vehicleNumberField.setText("");
            inTimeField.setText("");
            slotNumberField.setText("");
          //  userTypeBox.setDropTarget(userTypes);
        }
        if ("Bill".equals(e.getActionCommand()))
        {
            boolean b;

            i = vehicleTypeBox.getSelectedIndex();
            if (i == 0)
            {
                fields[0] = "Car";
            }
            else
            {
                fields[0] = "Bike";
            }
                     i = userTypeBox.getSelectedIndex();
            if (i == 0)
            {
                fields[1] = "Monthly";
            } else
            {
                fields[1] = "Daily";
            }
            fields[2] = slotNumberField.getText();
            
            
            try {
                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                Connection con = DriverManager.getConnection("jdbc:odbc:parking", "sekhar", "sekhar");
                Statement st = con.createStatement();
                String time = "select to_char(sysdate,'hh:mi:ss a.m.') from dual";
                ResultSet timers = st.executeQuery(time);
                while (timers.next()) {
                    fields[4] = timers.getString(1);
                    inTimeField.setText(fields[4]);
                }

                if ("Monthly".equals(fields[1]))
                {
                    ResultSet rs = st.executeQuery("select * from monthly_register");
                    while (rs.next())
                    {
                       
                        if (fields[2].equals(rs.getString(5)))
                        {
                            fields[3] = rs.getString(1);
                        vehicleNumberField.setText(fields[3]);
                            days=rs.getInt(6);
                        }
                    }
                    days=days-1;
                   //new VehicleDeparturePanel(days);
                    String decrement="update  monthly_register set no_days="+days+" where slot_number="+fields[2];
                    st.execute(decrement);
                }
                    if ("Daily".equals(fields[1]))
                    {
                        ResultSet rsm = st.executeQuery("select * from daily_register");
                        while (rsm.next())
                        {
                            
                            if (fields[2].equals(rsm.getString(5)))
                            {
                                fields[3] = rsm.getString(1);
                                dailyday=rsm.getInt(6);
                                vehicleNumberField.setText(fields[3]);
                            }
                        }
                        if(dailyday==0)
                        {
                            throw new Exception("Your Period Expired");
                        }
                        else
                        {
                            String delete="delete from daily_register where slot_number="+fields[2];
                            st.execute(delete);
                        }
                    }
                        PreparedStatement pst = con.prepareStatement("insert into vehicle_Arrived values(?,?,?,?,?)");
                        pst.setString(1, fields[0]);
                        pst.setString(2, fields[1]);
                        pst.setString(3, fields[2]);
                        pst.setString(4, fields[3]);
                        pst.setString(5, fields[4]);
                     b=pst.execute();
              new Printing(fields[0],fields[1],fields[2],fields[3],fields[4]);
           
           System.out.println(b);
            }


            catch(SQLException sql1)
            {
                JOptionPane.showMessageDialog(new VehicleArrivalPanel(),
                        "Information Error" +sql1.getMessage(),
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE);
            }
             catch (Exception veharr) {
                JOptionPane.showMessageDialog(new VehicleArrivalPanel(),
                        "Information Error" + veharr.getMessage(),
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    }
     public int getDays()
    {
        return days;
    }
     public void setDays(int days)
     {


     }

    
}
