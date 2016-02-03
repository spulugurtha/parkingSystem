package parkingsystem;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JOptionPane;

public class Billing implements Printable {

    private String print_vehicletype,  print_usertype,  print_slotnumber,  print_vehiclenumber,  print_intime,print_cost;
    private String day,date,token1,token2,token3,token4,token5,token6,token7,token8;
    private String token9,token10,descition;


    public Billing(String vehicle_Type, String user_Type, String slot_Number, String vehicle_Number, String InTime,String Cost) {
        print_vehicletype = vehicle_Type;

        print_usertype = user_Type;

        print_slotnumber = slot_Number;

        print_vehiclenumber = vehicle_Number;

        print_intime = InTime;

        print_cost=Cost;


        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection con = DriverManager.getConnection("jdbc:odbc:parking", "sekhar", "sekhar");
            Statement st = con.createStatement();

            String time = "select to_char(sysdate,'Day') day,to_char(sysdate,'Mon dd, yyyy') today from dual";
            ResultSet timers = st.executeQuery(time);
            while (timers.next()) {
                day = timers.getString(1);
                date = timers.getString(2);

            }
        } catch (Exception print) {
            JOptionPane.showMessageDialog(new VehicleArrivalPanel(),
                    "Information Error" + print.getMessage(),
                    "Sql Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        token7 = "               In      Time    :   " + print_intime;
        token10= "        Status Of Registration :   " +print_intime;
        if(print_usertype.equals("Monthly"))
            descition=token10;
        else
            descition=token7;
        token1 = "                           Parking System                       ";
        token2 = "        Day    :" + day + "                Date    :" + date;
        token3 = "               Vehicle Type    :   " + print_vehicletype;
        token4 = "               User    Type    :   " + print_usertype;
        token5 = "               Slot    Number  :   " + print_slotnumber;
        token6 = "               Vehicle Number  :   " + print_vehiclenumber;
        
        token8 = "               Cost Of Parking :   " + print_cost;

        token9 = "       THANK YOU FOR PARKING HAVE A NICE DAY           ";
        PrinterJob printJob = PrinterJob.getPrinterJob();
        PrintService printer = printJob.getPrintService();

        if (printer == null) {
            JOptionPane.showMessageDialog(new VehicleArrivalPanel(),
                    "No default printer available.",
                    "Printer Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        aset.add(OrientationRequested.LANDSCAPE);
        aset.add(new Copies(2));
        aset.add(new JobName("Parking SYSTEM ", null));
        //aset.size(250)
        //printJob.setJobName("Parking System");

        printJob.setPrintable(this);

        if (printJob.printDialog()) {

            try {
                printJob.print();

            } catch (PrinterException pe) {
                System.out.println(pe);
                JOptionPane.showMessageDialog(new VehicleArrivalPanel(),
                        "Error printing a sketch.",
                        "Printer Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public int print(Graphics g, PageFormat pf, int pageIndex)
            throws PrinterException {
        if (pageIndex >= 1) {
            return Printable.NO_SUCH_PAGE;
        }

        PrinterGraphics p = (PrinterGraphics) g;

        System.out.println(p.getPrinterJob().getCopies());
        System.out.println(p.getPrinterJob().getJobName());

        Graphics2D g2 = (Graphics2D) g;
        Rectangle2D r = new Rectangle2D.Double(10, 10, 350, 500);

        g2.setColor(Color.DARK_GRAY);
        g2.draw(r);
        //for(int i=0;i<7;i++)
        //{
        //g2.drawString(billdata[i],100,100*(i+1));
        //System.out.println(billdata[0]);
       // g2.drawString(billdata, 100, 100);
        g2.drawString(token1,50,50);
        g2.drawString(token2,50,100);
        g2.drawString(token3,50,150);
        g2.drawString(token4,50,200);
        g2.drawString(token5,50,250);
        g2.drawString(token6,50,300);
        g2.drawString(descition,50,350);
        g2.drawString(token8,50,400);
        g2.drawString(token9,50,450);

        // }
        return Printable.PAGE_EXISTS;
    }
}