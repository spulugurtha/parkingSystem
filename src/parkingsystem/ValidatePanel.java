package parkingsystem;

import com.jtattoo.plaf.noire.NoireLookAndFeel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.sql.RowSet;
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

public class ValidatePanel extends JInternalFrame implements ActionListener {

    private JLabel vehicleTypeLabel,  tokenNumberLabel;
    private JTextField tokenNumberField;
    private JButton submitButton,  resetButton;
    private JComboBox vehicleTypeBox;
    private String vehicleTypes[] = {"Monthly", "Daily", "Hourly"};
    static final int xOffset = 30,  yOffset = 30;
    static int openFrameCount = 0;
    private LookAndFeelInfo[] looks;
    String hourly[] , regs[];
    String usertype,found;

    public ValidatePanel() {
        super("Validation Of Vehicle", false, true, false, true);
        ++openFrameCount;
        hourly=new String[4];
        regs=new String[6];
        setLayout(new GridLayout(3, 2));
        setBounds(150, 150, 350, 200);
        looks = UIManager.getInstalledLookAndFeels();
        try {
            UIManager.setLookAndFeel(new NoireLookAndFeel());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
        }

        vehicleTypeLabel = new JLabel("VehicleType:");
        //vehicleTypeLabel.setBorder(BorderFactory.createEtchedBorder());
        vehicleTypeBox = new JComboBox(vehicleTypes);
        vehicleTypeBox.setToolTipText("Select Type Of Vehicle");
        add(vehicleTypeLabel);
        add(vehicleTypeBox);

        tokenNumberLabel = new JLabel("Token Number:");
        //tokenNumberLabel.setBorder(BorderFactory.createEtchedBorder());
        tokenNumberField = new JTextField(20);
        tokenNumberField.setToolTipText("Enter Token Number");
        add(tokenNumberLabel);
        add(tokenNumberField);

        submitButton = new JButton("Submit");
        submitButton.setToolTipText("Click to bill customer");
        submitButton.setActionCommand("Submit");
        add(submitButton);
        submitButton.addActionListener(this);

        resetButton = new JButton("Reset");
        resetButton.setToolTipText("Click to reset all fields");
        resetButton.setActionCommand("reset");
        add(resetButton);
        resetButton.addActionListener(this);
        setBounds(100, 100, 250, 250);
        setLocation(xOffset * openFrameCount, yOffset * openFrameCount);

        pack();

    }

    public void actionPerformed(ActionEvent e) {
        if ("reset".equals(e.getActionCommand())) {
            tokenNumberField.setText("");
        }
        if ("Submit".equals(e.getActionCommand())) {

        int a=0;
            if (vehicleTypeBox.getSelectedIndex() == 0) {
                usertype ="monthly_register";
            } else if (vehicleTypeBox.getSelectedIndex() == 1) {
                usertype ="daily_register";
            } else {
                usertype ="hourly_arrived";
            }
            found = tokenNumberField.getText();
            System.out.println(found);
            try {
                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                Connection con = DriverManager.getConnection("jdbc:odbc:parking", "sekhar", "sekhar");
                Statement st = con.createStatement();
                 String query = "select * from "+usertype ;
                  System.out.println(query);
                if (usertype.equals("hourly_arrived")) {
                    System.out.println("Hourly Validation");
                   
                    ResultSet rss = st.executeQuery(query);
                    while (rss.next()) {
                        if (rss.getString(2).equals(found)) {
                            hourly[0] = rss.getString(1);
                            //hourly[1] = rss.getString(2);
                            hourly[2] = rss.getString(3);
                            hourly[3] = rss.getString(4);
                            JOptionPane.showMessageDialog(new ValidatePanel(),
                            "\nVehicleType  " + hourly[0] +
                            "\nSlotNumber   " + found +
                            "\nVehicleNumber" + hourly[2] +
                            "\nInTime       " + hourly[3],
                            "Valid Hourly User",
                            JOptionPane.INFORMATION_MESSAGE);
                            a=1;
                            break;
                        }
                    }
                    
                } else {
                    
                            System.out.println("Reg user valllll");
                            ResultSet running = st.executeQuery(query);
                                    while (running.next()) {

                                         if (running.getString(5).equals(found)) {
                                            System.out.println("found");
                                            regs[0] = running.getString(1);
                                            System.out.println(regs[0]);
                                            regs[1] = running.getString(2);
                                            System.out.println(regs[1]);
                                            regs[2] = running.getString(3);
                                            System.out.println(regs[2]);
                                            regs[3] = running.getString(4);
                                            System.out.println(regs[3]);
                                            //regs[4] = running.getString(5);
                                            //System.out.println(regs[4]);
                                            regs[5] = running.getString(6);
                                            System.out.println(regs[5]);
                                             JOptionPane.showMessageDialog(new ValidatePanel(),
                            "\nVehicleNumber " + regs[0] +
                            "\n VehicleType   " + regs[1] +
                            "\n Address       " + regs[2] +
                            "\n ContactNumber " + regs[3] +
                            "\n SlotNumber    " + found +
                            "\n Number Of Days" + regs[5]

                            ,"Valid Registered User",
                            JOptionPane.INFORMATION_MESSAGE);
                                     a=1;
                                             break;
                                         }
                                    }

                       
                   
                }
                  if(a==0)
                  {
                      throw new Exception("Slot Not Found");
                  }
            }
            catch (Exception exp) {
                JOptionPane.showMessageDialog(new RegistrationMonth(),
                        "InValid User" + exp.getMessage(),
                        "Validation Of Users",
                        JOptionPane.ERROR_MESSAGE);

            }


        }
    }
}
