/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package parkingsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Gandhi
 */
public class SlotGeneration extends IntializeSlot {
    Statement st;
    public SlotGeneration()
    {
        try
          {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                Connection con = DriverManager.getConnection("jdbc:odbc:parking","sekhar","sekhar");
                 st = con.createStatement();

          }
        catch(Exception slotexp)
           {
            JOptionPane.showMessageDialog(new VehicleDeparturePanel(),
                        "Slot Information Error" +slotexp.getMessage(),
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE);
            }
     
    }
    public int month_slot()
    {   int slot=0,p=0;

        String monthq="select * from Slot where status='free'";

        try
        {
            ResultSet ms=st.executeQuery(monthq);

        while(ms.next())
        {
            //if("free".equals(ms.getString(2)))
            //{
                slot=ms.getInt(1);
                System.out.println("slot num"+slot);
                p=1;
                break;
              
            //}
        }
            if(p==0)
            {
                throw new Exception("NO FREE SLOT IS AVAILABLE");
            }
            String filling="update Slot set status='filled' where slot_number="+slot;
            st.execute(filling);

        }
        catch(Exception me)
        {
            JOptionPane.showMessageDialog(new VehicleDeparturePanel(),
                        "Slot Generation Error" +me.getMessage(),
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE);

        }
        if(p==1)
            return slot;
        else
            return -1;
    }

}
