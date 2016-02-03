package parkingsystem;

import com.jtattoo.plaf.noire.NoireLookAndFeel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import parkingsystem.VehicleArrivalPanel;
public class VehicleDeparturePanel extends JInternalFrame implements ActionListener {

    private JLabel UserTypeLabel,  tokenNumberLabel,  vehicleNumberLabel,  timePeriodLabel,  costLabel,  VehicleTypeLabel;
    private JTextField tokenNumberField;
    private JLabel vehicleNumberField,  timePeriodField,  costField,  VehicleTypeField;
    private JButton billButton,  submitButton;
    private JComboBox UserTypeBox;
    private String UserTypes[] = {"Monthly", "Daily"};
    static final int xOffset = 30,  yOffset = 30;
    static int openFrameCount = 0;
    private int countdays;
    private JLabel space1,  space2,  space3,  bill_lab,  space4,  space5;
    private String fields[];
    private LookAndFeelInfo[] looks;
    
    public VehicleDeparturePanel() {
        super("Registered Vehicle Departure", false, true, false, true);
        ++openFrameCount;

        fields = new String[6];
       
        setLayout(new GridLayout(7, 2));
       
         setBounds(100,100,350,200);
       
           looks=UIManager.getInstalledLookAndFeels();
        try{
        UIManager.setLookAndFeel(new NoireLookAndFeel());
        SwingUtilities.updateComponentTreeUI(this);
        }
        catch(Exception e){}
        UserTypeLabel = new JLabel("User Type:");
        UserTypeBox = new JComboBox(UserTypes);
        UserTypeBox.setToolTipText("Select Type Of Vehicle");
        UserTypeLabel.setLabelFor(UserTypeBox);
        add(UserTypeLabel);
        add(UserTypeBox);

        tokenNumberLabel = new JLabel("Token Number:");
        tokenNumberField = new JTextField(20);
        tokenNumberField.setToolTipText("Enter Token Number");
        tokenNumberLabel.setLabelFor(tokenNumberField);
        space1 = new JLabel("");
        add(space1);
        add(tokenNumberLabel);
        add(tokenNumberField);

        submitButton = new JButton("Submit");
        submitButton.setActionCommand("Submit");
        System.out.println("submit");
        add(submitButton);
        submitButton.addActionListener(this);

        space5 = new JLabel();
        VehicleTypeLabel = new JLabel("Vehicle Type:");
        VehicleTypeField = new JLabel();
        add(VehicleTypeLabel);
        add(VehicleTypeField);
        add(space5);

        vehicleNumberLabel = new JLabel("Vehicle Number:");
        vehicleNumberField = new JLabel();
        vehicleNumberField.setToolTipText("Enter Vehicle Number");
        vehicleNumberLabel.setLabelFor(vehicleNumberField);
        add(vehicleNumberLabel);
        add(vehicleNumberField);

        timePeriodLabel = new JLabel("TimePeriod:");
        timePeriodField = new JLabel();
        timePeriodLabel.setLabelFor(timePeriodField);
        space2 = new JLabel("");
        add(space2);
        add(timePeriodLabel);
        add(timePeriodField);

        costLabel = new JLabel("Cost Of Parking:");
        costField = new JLabel();
        costLabel.setLabelFor(costField);
        space3 = new JLabel("");
        add(space3);
        add(costLabel);
        add(costField);

        billButton = new JButton("Bill");
        billButton.setActionCommand("Dep_Bill");
        bill_lab = new JLabel("");
        space4 = new JLabel("");
        add(space4);
        add(bill_lab);
        add(billButton);
        billButton.addActionListener(this);

        pack();
        setLocation(xOffset * openFrameCount, yOffset * openFrameCount);
    }

   

    public void actionPerformed(ActionEvent e)
    {
        int temp;
        String query;
        if ("Submit".equals(e.getActionCommand()))
        {

            System.out.println("enter");
            temp = UserTypeBox.getSelectedIndex();
             fields[1] = tokenNumberField.getText();
             System.out.println(temp);
            if (temp == 0)
            {
                fields[0] = "Monthly";
                
                try
                {
                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

                Connection con = DriverManager.getConnection("jdbc:odbc:parking","sekhar","sekhar");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select * from monthly_register");
                    while (rs.next())
                    {
                       
                        if (fields[1].equals(rs.getString(5)))
                        {
                            
                          countdays=rs.getInt(6);
                        }
                    }
                }
                    catch(Exception fetch)
                    {
                        JOptionPane.showMessageDialog(new VehicleDeparturePanel(),
                        "Error in VehicleDeparture",
                        "Fetching Days:" + fetch.getMessage(),
                        JOptionPane.ERROR_MESSAGE);
                         }

                fields[4] ="Days Remaining"+" "+countdays;
                if(countdays==0)
                    fields[5] = "Registration Expired";
                else
                    fields[5]="PAID";
                
             System.out.println(fields[0]+""+fields[4]);
            }
            else
            {
                fields[0] = "Daily";
                fields[4] = "24 hours";
                fields[5]="$5";
                
            }
           

            System.out.println(fields[1]);
            try {
                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                Connection con = DriverManager.getConnection("jdbc:odbc:parking","sekhar","sekhar");
                Statement st = con.createStatement();
                query = "select * from vehicle_Arrived";
                ResultSet rose = st.executeQuery(query);
                while (rose.next())
                {
                    if (fields[1].equals(rose.getString(3))&&fields[0].equals(rose.getString(2)))
                    {
                        fields[2] = rose.getString(1);
                        fields[3] = rose.getString(4);
                       
                    }
                   
                }
                if(fields[2].isEmpty())
                {
                    throw new Exception("Vehicle Not Parked");
                }
                VehicleTypeField.setText(fields[2]);
                vehicleNumberField.setText(fields[3]);
                timePeriodField.setText(fields[4]);
                costField.setText(fields[5]);
                String  del="delete from vehicle_Arrived where slot_number="+fields[1];


               st.execute(del);
               if(fields[0].equals("Daily")||countdays==0){
               /*String find="select * from slot";
                       ResultSet ros=st.executeQuery(find);
                       while(ros.next())
                       {
                           if(ros.getString(1).equals(fields[1]))
                           {
                             */  String reset="update slot set status='free' where slot_number="+fields[1];
                               st.execute(reset);
                              // break;
                           }
                       }
               
                   
            catch (Exception vehdep) {
                JOptionPane.showMessageDialog(new VehicleDeparturePanel(),
                        "Error in VehicleDeparture",
                        "Error:" + vehdep.getMessage(),
                        JOptionPane.ERROR_MESSAGE);
            }


        }


        if ("Dep_Bill".equals(e.getActionCommand()))
        {
           // new Billing(days);
            //new Billing(fields[0],fields[1],fields[2],fields[3],fields[4],fields[5]);
             new Billing(fields[2],fields[0],fields[1],fields[3],fields[4],fields[5]);

        }
        
    }
}

