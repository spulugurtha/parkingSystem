

package parkingsystem;

import com.jtattoo.plaf.noire.NoireLookAndFeel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 *
 * @author Gandhi
 */
public class IntializeSlot extends JInternalFrame implements ActionListener {
    private JLabel slot_label,space;
    private JTextField slot_input;
  public int slot_limit;
  private JButton submitButton;
    private LookAndFeelInfo[] looks;
    public IntializeSlot()
    {
         super("Intializing Slot",false, true,false, true);
         setLayout(new GridLayout(2, 2));
         setBounds(150,150,350,200);
           looks=UIManager.getInstalledLookAndFeels();
        try{
        UIManager.setLookAndFeel(new NoireLookAndFeel());
        SwingUtilities.updateComponentTreeUI(this);
        }
        catch(Exception e){}
         slot_label=new JLabel("Enter Slot limit");
         slot_input=new JTextField();
         add(slot_label);
         add(slot_input);

         space=new JLabel();
         submitButton=new JButton("Submit");
         add(space);
         add(submitButton);
         submitButton.setActionCommand("Submit");
         submitButton.addActionListener(this);
         //slot_limit=Integer.parseInt(slot_input.getText());

          pack();
    }
      public void actionPerformed(ActionEvent e) {
         if("Submit".equals(e.getActionCommand()))
         {
          slot_limit=Integer.parseInt(slot_input.getText());

        try
          {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                Connection con = DriverManager.getConnection("jdbc:odbc:parking","sekhar","sekhar");
                Statement st = con.createStatement();
             

               String del="delete slot";
               String del1="delete monthly_register";
               String del2="delete daily_register";
               String del3="delete vehicle_arrived";
               String del4="delete hourly_arrived";
                    /* try
                            {

                            }
                     catch(Exception drop1)
                            {

                            }
                     try
                     {
                         st.execute(del1);
                     }
                     catch()
               */
               st.execute(del);
               st.execute(del1);
               st.execute(del2);
               st.execute(del3);
               st.execute(del4);
               /*
             

               String monthly="create table monthly_register(vehicle_number varchar2(20) primary key,vehicle_type varchar2(10),address varchar2(50),contact_number number(10),slot_number number(3) unique,no_days number(2))";
               st.execute(monthly);
               String daily="create table daily_register(vehicle_number varchar2(20) primary key,vehicle_type varchar2(10),address varchar2(50),contact_number number(10),slot_number number(3) unique,no_days number)";
               st.execute(daily);
               String vehicle="create table vehicle_Arrived(vehicle_type varchar2(20),user_type varchar2(10),slot_number number(3) unique,vehicle_number varchar2(15) primary key,in_time varchar2(15))";
               st.execute(vehicle);
               String hourly="create table Hourly_Arrived(vehicle_type varchar2(20),slot_number number(3) unique,vehicle_number varchar2(15) primary key,in_time varchar2(15))";
               st.execute(hourly);
               String slot="create table Slot(slot_number number(3) primary key,status varchar2(12));";
               st.execute(slot);
               
               */
               for(int i=1;i<=slot_limit;i++)
                {
                String stmt="insert into Slot values("+i+",'free')";
                st.execute(stmt);
                }
                 JOptionPane.showMessageDialog(new VehicleArrivalPanel(),
                        "Slots are Reseted",
                        "Slot Status",
                        JOptionPane.INFORMATION_MESSAGE);
          }
        catch(Exception slotexp)
           {
            JOptionPane.showMessageDialog(new VehicleDeparturePanel(),
                        "Slot Information Error" +slotexp.getMessage(),
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE);
            }
       }

      }
}
