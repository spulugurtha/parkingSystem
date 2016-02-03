package parkingsystem;

import com.jtattoo.plaf.noire.NoireLookAndFeel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class HVehicleArrivalPanel extends JInternalFrame implements ActionListener {

    private JComboBox vehicleTypeBox,  userTypeBox;
    private JLabel vehicleTypeLabel,  userTypeLabel,  vehicleNumberLabel,  inTimeLabel,  slotNumberLabel,  inTimeField;
    private JTextField vehicleNumberField,  slotNumberField;
    private JButton billButton,  resetButton;
    private String vehicleTypes[] = {"Car", "Bike"},  fields[];
    private String userTypes[] = {"Monthly", "Daily"};
    static final int xOffset = 30,  yOffset = 30;
    static int openFrameCount = 0,  i,  temp;
    private Time inTime;
    private LookAndFeelInfo[] looks;

    public HVehicleArrivalPanel() {
        super("Hourly User Vehicle Arrival", false, true,false, true);
        ++openFrameCount;

        setLayout(new GridLayout(5, 2));
        setBounds(55,55,350,200);
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


        slotNumberLabel = new JLabel("Slot Number:");
        //slotNumberLabel.setBorder(BorderFactory.createEtchedBorder());
        slotNumberField = new JTextField(20);
        slotNumberField.setToolTipText("Enter the Slot Number");
        add(slotNumberLabel);
        add(slotNumberField);

        vehicleNumberLabel = new JLabel("Vehicle Number:");
        //vehicleNumberLabel.setBorder(BorderFactory.createEtchedBorder());
        vehicleNumberField = new JTextField(20);
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
        if ("reset".equals(e.getActionCommand())) {
            vehicleNumberField.setText("");
            inTimeField.setText("");
            slotNumberField.setText("");
        //  userTypeBox.setDropTarget(userTypes);
        }
        if ("Bill".equals(e.getActionCommand())) {
            boolean b;

            i = vehicleTypeBox.getSelectedIndex();
            if (i == 0) {
                fields[0] = "Car";
            } else {
                fields[0] = "Bike";
            }


            fields[1] = slotNumberField.getText();
            SlotGeneration sg=new SlotGeneration();
            fields[1]=""+sg.month_slot();
            slotNumberField.setText(fields[1]);
            fields[2] = vehicleNumberField.getText();
            try {
                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                Connection con = DriverManager.getConnection("jdbc:odbc:parking", "sekhar", "sekhar");
                Statement st = con.createStatement();
                String time = "select to_char(sysdate,'hh:mi:ss a.m.') from dual";
                ResultSet timers = st.executeQuery(time);
                while (timers.next()) {
                    fields[3] = timers.getString(1);
                    inTimeField.setText(fields[3]);
                }
 
                PreparedStatement pst = con.prepareStatement("insert into Hourly_Arrived values(?,?,?,?)");
                pst.setString(1, fields[0]);
                pst.setString(2, fields[1]);
                pst.setString(3, fields[2]);
                pst.setString(4, fields[3]);
               

                b = pst.execute();
                if(fields[1].equals("-1")){}
                else
                new HourlyPrinting(fields[0], fields[1], fields[2], fields[3]);

                System.out.println(b);
            } catch (SQLException sql1) {
                JOptionPane.showMessageDialog(new VehicleArrivalPanel(),
                        "Information Error" + sql1.getMessage(),
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception veharr) {
                JOptionPane.showMessageDialog(new VehicleArrivalPanel(),
                        "Information Error" + veharr.getMessage(),
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    }
}
