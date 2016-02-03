package parkingsystem;

import com.jtattoo.plaf.noire.NoireLookAndFeel;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.BevelBorder;
//import javax.swing.plaf.metal.MetalSplitPaneDivider.MetalDividerLayout;
//import net.infonode.docking.RootWindow;

public class MainFrame extends JFrame {

    private JFrame jframe;
    private JMenuBar menubar;
    private JMenu registration,  service,  view,  help,  monthly_User,  daily_User,  tools,  deletion,  state;
    private JMenuItem monthlyRegister,  dailyRegister,  monthlyDelete,  dailyDelete,  quit,  vehicle_arr,  vehicle_depr,  validate,  status,  widget,  help_top,  about_us,  intial_slot;
    public JDesktopPane theDesktop;
    private LookAndFeelInfo[] looks;
    private JMenu hourly_User;
    private JLabel phaseIconLabel;
    private JPanel pn,  pon;
    ImageIcon[] images = new ImageIcon[8];
    private static JRootPane rooting;
    private ImageIcon images9;
    private int accessedwidget=0;
    public MainFrame() {
        super("S3N Parking Services");
        looks = UIManager.getInstalledLookAndFeels();
        try {
            UIManager.setLookAndFeel(new NoireLookAndFeel());
            SwingUtilities.updateComponentTreeUI(jframe);
        } catch (Exception e) {
        }

        jframe = new JFrame();
        menubar = new JMenuBar();
        jframe.add(menubar);

        jframe.setLayout(new FlowLayout());
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400, Short.MAX_VALUE));

        registration = new JMenu("Registration");
        registration.setMnemonic('R');
        registration.setToolTipText("To Register a Vehicle");
        menubar.add(registration);

        /*monthly_User = new JMenu("Monthly");
        monthly_User.setToolTipText("Registration for Monthly");
        monthly_User.setActionCommand("regmon");
        daily_User = new JMenu("Daily");
        daily_User.setToolTipText("Registration for Daily");
        daily_User.setActionCommand("regday");
       */
         monthlyRegister = new JMenuItem("Monthly Register");
        monthlyDelete = new JMenuItem("Monthly Delete");
        dailyRegister = new JMenuItem("Daily Register");
        dailyDelete = new JMenuItem("Daily Delete");
        quit = new JMenuItem("Quit");
        quit.setToolTipText("Close The Application");

        theDesktop = new JDesktopPane();
        registration.add(monthlyRegister);
        registration.add(dailyRegister);
        //monthly_User.add(monthlyRegister);
        //monthly_User.add(monthlyDelete);
        //daily_User.add(dailyRegister);
        //daily_User.add(dailyDelete);
        registration.addSeparator();
        registration.add(quit);
        setJMenuBar(menubar);
        monthlyRegister.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {

                        RegistrationMonth regpanel = new RegistrationMonth();
                        regpanel.setVisible(true);
                        theDesktop.add(regpanel);

                        phaseIconLabel.setIcon(images9);
                        pn.add(phaseIconLabel);
                        theDesktop.add(pn);
                         pn.setBounds(250, 325,730,480);
                         setContentPane(theDesktop);
                         /*if(accessedwidget==1)
                                new Update(pon);
                        */
                    }
                });

        dailyRegister.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {

                        RegistrationDay regpanel = new RegistrationDay();
                        regpanel.setVisible(true);
                        theDesktop.add(regpanel);
                        phaseIconLabel.setIcon(images9);
                        pn.add(phaseIconLabel);
                        theDesktop.add(pn);
                        pn.setBounds(250, 325,730,480);

                        setContentPane(theDesktop);
                    }
                });

        monthlyDelete.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {

                        RegistrationDel regpanel = new RegistrationDel("Monthly");
                        regpanel.setVisible(true);
                        theDesktop.add(regpanel);
                        phaseIconLabel.setIcon(images9);
                        pn.add(phaseIconLabel);
                        theDesktop.add(pn);
                        pn.setBounds(250, 325,730,480);
                        setContentPane(theDesktop);
                    }
                });

        dailyDelete.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {

                        RegistrationDel regpanel = new RegistrationDel("Daily");
                        regpanel.setVisible(true);
                        theDesktop.add(regpanel);
                        phaseIconLabel.setIcon(images9);
                        pn.add(phaseIconLabel);
                        theDesktop.add(pn);
                        pn.setBounds(250, 325,730,480);
                        setContentPane(theDesktop);
                    }
                });

        quit.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                            System.exit(0);
                    }
                });


        theDesktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);

        service = new JMenu("Services");
        service.setMnemonic('S');
        service.setToolTipText("Various Services For A User");
        menubar.add(service);

        intial_slot = new JMenuItem("Setting Slots");
        intial_slot.setActionCommand("Reset Slot");


        vehicle_arr = new JMenuItem("Vehicle Arrival");
        vehicle_arr.setToolTipText("Vehicle Arrival Details");
        service.add(vehicle_arr);
        vehicle_depr = new JMenuItem("Vehicle Departure");
        vehicle_depr.setToolTipText("Vehicle Departure Details");
        service.add(vehicle_depr);
        validate = new JMenuItem("Validate");
        validate.setToolTipText("Validation Of User");
        service.add(validate);

        intial_slot.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                IntializeSlot intslot = new IntializeSlot();
                intslot.setVisible(true);
                theDesktop.add(intslot);
                phaseIconLabel.setIcon(images9);
                pn.add(phaseIconLabel);
                theDesktop.add(pn);
                 pn.setBounds(250, 325,730,480);
                setContentPane(theDesktop);
            }
        });
        vehicle_arr.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                VehicleArrivalPanel arr_panel = new VehicleArrivalPanel();
                arr_panel.setVisible(true);
                theDesktop.add(arr_panel);
                phaseIconLabel.setIcon(images9);
                pn.add(phaseIconLabel);
                theDesktop.add(pn);
                 pn.setBounds(250, 325,730,480);
                setContentPane(theDesktop);
            }
        });

        vehicle_depr.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                VehicleDeparturePanel deppanel = new VehicleDeparturePanel();
                deppanel.setVisible(true);
                theDesktop.add(deppanel);
                phaseIconLabel.setIcon(images9);
                pn.add(phaseIconLabel);
                theDesktop.add(pn);
                 pn.setBounds(250, 325,730,480);
                setContentPane(theDesktop);
            }
        });

        validate.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ValidatePanel valid_panel = new ValidatePanel();
                valid_panel.setVisible(true);
                theDesktop.add(valid_panel);
                phaseIconLabel.setIcon(images9);
                pn.add(phaseIconLabel);
                theDesktop.add(pn);
                 pn.setBounds(250, 325,730,480);
                setContentPane(theDesktop);
            }
        });

        hourly_User = new JMenu("Hourly User");
        vehicle_arr = new JMenuItem("Vehicle Arrival");
        vehicle_arr.setToolTipText("Vehicle Arrival Details");
        hourly_User.add(vehicle_arr);
        hourly_User.addSeparator();
        vehicle_depr = new JMenuItem("Vehicle Departure");
        vehicle_depr.setToolTipText("Vehicle Departure Details");
        hourly_User.add(vehicle_depr);
        service.add(hourly_User);

        vehicle_arr.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                HVehicleArrivalPanel arrpanel = new HVehicleArrivalPanel();
                arrpanel.setVisible(true);
                theDesktop.add(arrpanel);
                phaseIconLabel.setIcon(images9);
                pn.add(phaseIconLabel);
                theDesktop.add(pn);
                 pn.setBounds(250, 325,730,480);
                setContentPane(theDesktop);
            }
        });

        vehicle_depr.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                HVehicleDeparturePanel deppanel = new HVehicleDeparturePanel();
                deppanel.setVisible(true);
                theDesktop.add(deppanel);
                phaseIconLabel.setIcon(images9);
                pn.add(phaseIconLabel);
                theDesktop.add(pn);
                pn.setBounds(250, 325,730,480);
                setContentPane(theDesktop);
            }
        });
        tools = new JMenu("Tools");
        menubar.add(tools);
        deletion = new JMenu("Deletion");
        tools.add(intial_slot);
        tools.add(deletion);
        deletion.add(monthlyDelete);
        deletion.add(dailyDelete);

        view = new JMenu("View");
        view.setMnemonic('V');
        view.setToolTipText("Current Status Of Available Slots");
        menubar.add(view);
        //state=new JMenu("Status");
        //view.add(state);
        status = new JMenuItem("View Status");
        view.add(status);
        status.setToolTipText("Status of the slots");
        //widemenu=new JMenu("Widget");
        widget = new JMenuItem("Add as Widget");
        view.add(widget);
        //widemenu.add(widget);
        //remove=new JMenuItem("Remove Widget");
        //widemenu.add(remove);
        
        status.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                StatusPanel deppanel = new StatusPanel();
                deppanel.setVisible(true);
                theDesktop.add(deppanel);
                phaseIconLabel.setIcon(images9);
                pn.add(phaseIconLabel);
                theDesktop.add(pn);
                 pn.setBounds(250, 325,730,480);
                setContentPane(theDesktop);
            }
        });
         widget.addActionListener(
                new ActionListener() {
                    int accessedwidget=0;
                    public void actionPerformed(ActionEvent e) {

                        Addwidget widgets = new Addwidget();
                        pon=widgets.getPanel();
                        theDesktop.add(pon);
                        setContentPane(theDesktop);
                        accessedwidget=1;
                    }
                });
               /* remove.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {


                        g.setColor(Color.BLACK);
                        theDesktop.paintAll(g);
                        setContentPane(theDesktop);

                        
                    }
                });*/

           
        help = new JMenu("Help");
        help.setToolTipText("To View Help Topics");
        help.setMnemonic('H');
        menubar.add(help);
        help_top = new JMenuItem("Help Topics");
        help_top.setToolTipText("Click For Help");
        help.add(help_top);
        help.addSeparator();
        about_us = new JMenuItem("About Us");
        about_us.setToolTipText("Click To Know About Us");
        help.add(about_us);
        menubar.setBorder(new BevelBorder(BevelBorder.RAISED));

        jframe.pack();
    }
   

    public static void main(String[] args) {

        MainFrame mf = new MainFrame();
        mf.addWidget();
        JFrame.setDefaultLookAndFeelDecorated(true);
        mf.setDefaultCloseOperation(EXIT_ON_CLOSE);
        java.net.URL imgURL = MainFrame.class.getResource("icon.jpg");
        mf.setIconImage(new ImageIcon(imgURL).getImage());
        //RootWindow.add(setIconImage(new ImageIcon(imgURL).getImage()));
        //JLabel jp=new JLabel("Raj");
//        rooting.add(jp);
        mf.setBounds(0, 0, 1200, 900);
        mf.setVisible(true);
        mf.setBackground(Color.BLUE);
    }

    public static ImageIcon createImageIcon(String path) {
        java.net.URL imageURL = MainFrame.class.getResource(path);

        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return new ImageIcon(imageURL);
        }
    }



    public void addWidget() {

        //for (int i = 0; i < 10; i++) {
            images9 = createImageIcon("images9.jpg");
            System.out.println(images9);
        //}
        pn = new JPanel();
        pn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(""),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));


        phaseIconLabel = new JLabel();
        phaseIconLabel.setIcon(images9);
        pn.add(phaseIconLabel);

        pn.setBackground(getBackground());


        setContentPane(pn);
        pn.setBounds(250, 325,730,480);
        setVisible(true);

    }
}


   