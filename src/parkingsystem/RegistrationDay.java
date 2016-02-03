package parkingsystem;

import com.jtattoo.plaf.noire.NoireLookAndFeel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

class RegistrationDay extends JInternalFrame implements ActionListener {

    private JLabel vehicleNumberLabel,  addressLabel,  cellNumberLabel, slotnumberLabel,  vehicleTypeLabel,  slotNumberField;
    private JTextField vehicleNumberField,  cellNumberField;
    private JTextArea addressArea;
    private JComboBox  vehicleTypeBox;
    private JButton submitButton,  resetButton;
    private String   vehcileTypes[] = {"Car", "Bike"};
    static int openFrameCount = 0;
    static final int xOffset = 30,  yOffset = 30;
    private String fields[];
    private int i;//numbers[];
    private LookAndFeelInfo[] looks;
    public RegistrationDay() {
        super("Registration For Daily", false, true,false, true);
        ++openFrameCount;

        setLayout(new GridLayout(6, 2));
        setBounds(50,50,350,200);
        System.out.println("entered");
          looks=UIManager.getInstalledLookAndFeels();
        try{
        UIManager.setLookAndFeel(new NoireLookAndFeel());
        SwingUtilities.updateComponentTreeUI(this);
        }
        catch(Exception e){}

        fields = new String[6];

        vehicleNumberLabel = new JLabel("Vehicle Number:");
        //vehicleNumberLabel.setBorder(BorderFactory.createEtchedBorder());
        vehicleNumberField = new JTextField(20);
        vehicleNumberField.setToolTipText("Enter the vehicle number");
        vehicleNumberField.setActionCommand("numberField");
        add(vehicleNumberLabel);
        add(vehicleNumberField);
        vehicleNumberField.addActionListener(this);

        vehicleTypeLabel = new JLabel("Vehicle Type");
        vehicleTypeBox = new JComboBox(vehcileTypes);
        add(vehicleTypeLabel);
        add(vehicleTypeBox);


        addressLabel = new JLabel("Address:");
        //addressLabel.setBorder(BorderFactory.createEtchedBorder());
        addressArea = new JTextArea(2, 3);
        addressArea.setToolTipText("Enter User Address");
        add(addressLabel);
        add(addressArea);

        cellNumberLabel = new JLabel("Contact Number:");
        //cellNumberLabel.setBorder(BorderFactory.createEtchedBorder());
        cellNumberField = new JTextField(20);
        cellNumberField.setToolTipText("Enter Contact Number");
        add(cellNumberLabel);
        add(cellNumberField);

        

        slotnumberLabel = new JLabel("Slot Number:");
        //slotnumberLabel.setBorder(BorderFactory.createEtchedBorder());
        slotNumberField = new JLabel();
        slotNumberField.setToolTipText("Enter Slot Number");
        add(slotnumberLabel);
        add(slotNumberField);

        submitButton = new JButton("Submit");
        submitButton.setToolTipText("Click to submit the details");
        submitButton.setActionCommand("submit");
        add(submitButton);
        submitButton.addActionListener(this);

        resetButton = new JButton("Reset");
        resetButton.setToolTipText("Click to reset all fields");
        resetButton.setActionCommand("reset");
        add(resetButton);
        resetButton.addActionListener(this);

        //registerStatus = new JLabel();
        //add(registerStatus);

        pack();
        setLocation(xOffset * openFrameCount, yOffset * openFrameCount);
    }

    public void actionPerformed(ActionEvent e) {
        if ("reset".equals(e.getActionCommand())) {
            vehicleNumberField.setText("");
            cellNumberField.setText("");
            slotNumberField.setText("");
            addressArea.setText("");

        } else if ("submit".equals(e.getActionCommand())) {

            fields[0] = vehicleNumberField.getText();
            i = vehicleTypeBox.getSelectedIndex();
            if (i == 0) {
                fields[1] = "Car";
            } else {
                fields[1] = "Bike";
            }
            fields[2] = addressArea.getText();
            fields[3]=cellNumberField.getText();
            
            if(fields[3].length()<=10)
            {
            //fields[4]=slotNumberField.getText();
            SlotGeneration sg=new SlotGeneration();
            fields[4]=""+sg.month_slot();
            slotNumberField.setText(fields[4]);
            }
            try {
                if (fields[0].isEmpty() || fields[2].isEmpty() || fields[3].isEmpty() || fields[4].isEmpty()) {
                    JOptionPane.showMessageDialog(new RegistrationMonth(),
                            "Some or all the Fields are empty,please enter correctly.",
                            "Inane error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                    Connection con = DriverManager.getConnection("jdbc:odbc:parking", "sekhar", "sekhar");
                    PreparedStatement pst = con.prepareStatement("insert into daily_register values(?,?,?,?,?,?)");
                    pst.setString(1, fields[0]);
                    pst.setString(2, fields[1]);
                    pst.setString(3, fields[2]);
                    pst.setString(4, fields[3]);
                    pst.setString(5, fields[4]);
                    pst.setInt(6,1);
                    boolean b;
                    b = pst.execute();
                    if (b != true) {
                        //registerStatus.setText("registered successfully");
                        JOptionPane.showMessageDialog(new RegistrationMonth(),
                            "Registered Successfully",
                            "Registration Status",
                            JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        //registerStatus.setText("registration failed");
                           JOptionPane.showMessageDialog(new RegistrationMonth(),
                            "Registration Failed",
                            "Registration Status",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SQLException sql) {
                JOptionPane.showMessageDialog(new RegistrationMonth(),
                ".Sql Exception"+sql.getMessage(),
                "DataBase Error",
                JOptionPane.ERROR_MESSAGE);
                //System.out.println("Sql Exception" + sql.getMessage());
            } catch (Exception exp) {
                JOptionPane.showMessageDialog(new RegistrationMonth(),
                "Error Occured"+exp.getMessage(),
                "Exception",
                JOptionPane.ERROR_MESSAGE);
                //System.out.println("Exception" + exp.getMessage());
            }

        }
    }
}