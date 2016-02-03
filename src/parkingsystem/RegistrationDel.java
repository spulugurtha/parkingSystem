package parkingsystem;

import com.jtattoo.plaf.noire.NoireLookAndFeel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class RegistrationDel extends JInternalFrame implements ActionListener {

    private String userType;
    private JLabel slotNumberLabel,space;
    private JTextField slotNumberField;
    private JButton submitButton;
    private int slotNumber;
    static final int xOffset = 30,  yOffset = 30;
    static int openFrameCount = 0;
    private LookAndFeelInfo[] looks;

    public RegistrationDel(String userType){
              super("Registration Delete of "+userType,false,true,false,true);
              ++openFrameCount;
              this.userType=userType;

              setLayout(new GridLayout(2, 2));
              setBounds(60,60,350,200);
                looks=UIManager.getInstalledLookAndFeels();
        try{
        UIManager.setLookAndFeel(new NoireLookAndFeel());
        SwingUtilities.updateComponentTreeUI(this);
        }
        catch(Exception e){}

              slotNumberLabel=new JLabel("Slot Number:");
              slotNumberField=new JTextField(20);
              add(slotNumberLabel);
              add(slotNumberField);

              submitButton=new JButton("Submit");
              space=new JLabel();
              add(space);
              add(submitButton);
              //add(space);
              submitButton.setActionCommand("Submit");
              submitButton.addActionListener(this);

              pack();
              setLocation(xOffset * openFrameCount, yOffset * openFrameCount);
    }

    public void actionPerformed(ActionEvent e){
        if("Submit".equals(e.getActionCommand())){
            System.out.println("entered");
            slotNumber=Integer.parseInt(slotNumberField.getText());
            System.out.println(slotNumber);
            try{
                Connection con=DriverManager.getConnection("jdbc:odbc:parking","sekhar","sekhar");
                Statement st=con.createStatement();
                String query;
                if(userType.equals("Monthly")){
                    query="delete from monthly_register where slot_number="+slotNumber;
                }
                else{
                    query="delete from daily_register where slot_number="+slotNumber;
                }
                boolean b;
                b=st.execute(query);
                if(!b){
                    JOptionPane.showMessageDialog(new RegistrationDel(userType),
                        "Deleted",
                        "Status of Deletion",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
            catch(SQLException se){
                JOptionPane.showMessageDialog(new RegistrationDel(userType),
                        "Error in Deletion",
                        "Error:" + se.getMessage(),
                        JOptionPane.ERROR_MESSAGE);
            }
            catch(Exception e1){
                                JOptionPane.showMessageDialog(new RegistrationDel(userType),
                        "Error in Deletion",
                        "Error:" + e1.getMessage(),
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}