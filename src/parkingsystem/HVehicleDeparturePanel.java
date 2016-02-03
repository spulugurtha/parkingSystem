package parkingsystem;

import com.jtattoo.plaf.noire.NoireLookAndFeel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.sql.*;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class HVehicleDeparturePanel extends JInternalFrame implements ActionListener {

    private JLabel UserTypeLabel,  tokenNumberLabel,  vehicleNumberLabel,  timePeriodLabel,  costLabel,  VehicleTypeLabel;
    private JTextField tokenNumberField;
    private JLabel   vehicleNumberField,  timePeriodField,  costField,  VehicleTypeField;
    private JButton billButton,  submitButton;
    private JComboBox UserTypeBox;
    private String UserTypes[] = {"Monthly", "Daily"};
    static final int xOffset = 30,  yOffset = 30;
    static int openFrameCount = 0;
    private JLabel space1,  space2,  space3,  bill_lab,  space4,  space5;
    private String fields[],timeString,resultTime,costString;
   
    private float timeResult;
    private LookAndFeelInfo[] looks;
    
    public HVehicleDeparturePanel() {
        super("Hourly Users Vehicle Departure",false, true,false, true);
        ++openFrameCount;

        fields = new String[6];

        setLayout(new GridLayout(6, 2));
        setBounds(55,55,350,200);
          looks=UIManager.getInstalledLookAndFeels();
        try{
        UIManager.setLookAndFeel(new NoireLookAndFeel());
        SwingUtilities.updateComponentTreeUI(this);
        }
        catch(Exception e){}
        /*try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
        */
       
        tokenNumberLabel = new JLabel("Slot Number:");
        tokenNumberField = new JTextField(20);
        tokenNumberField.setToolTipText("Enter Token Number");
        tokenNumberLabel.setLabelFor(tokenNumberField);
       
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

    public void actionPerformed(ActionEvent e) {
        int temp = 0,temp2,temp3;
        String query;
        if ("Submit".equals(e.getActionCommand()))
        {
            System.out.println("enter");
            fields[0] = tokenNumberField.getText();
            fields[5]="Hourly User";
            try {
                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                Connection con = DriverManager.getConnection("jdbc:odbc:parking","sekhar","sekhar");
                Statement st = con.createStatement();
                query = "select * from Hourly_Arrived";
                ResultSet rose = st.executeQuery(query);
                while (rose.next())
                {
                    if (fields[0].equals(rose.getString(2)))
                    {
                        fields[1] = rose.getString(1);
                        fields[2] = rose.getString(3);
                        fields[3] = rose.getString(4);

                    }

                }
               System.out.println(fields[3]);
               
                String time = "select to_char(sysdate,'hh:mi:ss a.m.') from dual";
                ResultSet timers = st.executeQuery(time);
                while (timers.next())
                {
                  fields[4]=timers.getString(1);
                   System.out.println(fields[4]);
                }
                 
                 resultTime=time_convert();
                costString=cost_of_parking();
                if(fields[2].isEmpty())
                {
                    throw new Exception("Vehicle Not Parked");
                }
                VehicleTypeField.setText(fields[1]);
                vehicleNumberField.setText(fields[2]);
                timePeriodField.setText(resultTime);
                costField.setText(costString);
                String del="delete from Hourly_Arrived where slot_number="+fields[0];
                 st.execute(del);

                /* String find="select * from slot";
                       ResultSet ros=st.executeQuery(find);
                       while(ros.next())
                       {
                           String free="free";
                           if(ros.getString(1).equals(fields[0]))
                           {
                  */             String reset="update slot set status='free' where slot_number="+fields[0];
                               st.execute(reset);
                    //           break;
                           }
                       

            
            catch (Exception vehdep) {
                JOptionPane.showMessageDialog(new VehicleDeparturePanel(),
                        "Error "+ vehdep.getMessage(),
                        "Error:in VehicleDeparture" ,
                        JOptionPane.ERROR_MESSAGE);
            }


        }
        if ("Dep_Bill".equals(e.getActionCommand()))
        {
            new Billing(fields[1],fields[5],fields[0],fields[2],resultTime,costString);
        }

    }

    private String cost_of_parking() {
        System.out.println("cost of parking");
        float a;
        String cp;
        a=timeResult*2;
        cp=a+" "+"Rupees";
       return cp;

    }

    private String time_convert() {
        System.out.println("time convert");
        String res1[]={"0"},res2[]={"0"};

        float min1,min2;
        float minHrs1,minHrs2;
        
        res1=fields[3].split(":", 0);
       
        res2=fields[4].split(":", 0);
      
        float hrs1=Float.parseFloat(res1[0]);
        System.out.println(hrs1);
        float hrs2=Float.parseFloat(res2[0]);
        System.out.println(hrs2);
        min1=Float.parseFloat(res1[1]);
        minHrs1=min1/60;
        min2=Float.parseFloat(res2[1]);
        minHrs2=min2/60;
        
       
        int pmeridian1=fields[3].indexOf('p');
       
        int pmeridian2=fields[4].indexOf('p');

       
        if(pmeridian1==9)
        {
        hrs1+=12;
        System.out.println(hrs1);
        }
        if(pmeridian2==9)
        {
        hrs2+=12;
        System.out.println(hrs2);
        }
        hrs2+=minHrs2;
        hrs1+=minHrs1;
        timeResult=hrs2-hrs1;
        System.out.println(timeResult);
      
        timeString=""+timeResult+" "+"Hours";

        return timeString;
    }
    
}
