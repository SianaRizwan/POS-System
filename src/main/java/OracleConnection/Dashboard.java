package OracleConnection;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class Dashboard {

    private JFrame frame;
    private JPanel mainPanel, scrollPanel;
    private Font f1, f2;
    private JTabbedPane tabbedPane;
    private JScrollPane scrollPane;

    LoginPage loginPage;


    Dashboard(JFrame frame) {
        this.frame = frame;
        initComponents();

    }


    private void initComponents() {
        // System.out.println(loginPage.uID);


        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(0x7E0AB5));

        f1 = new Font("Arial", Font.BOLD, 15);
        f2 = new Font("Arial", Font.BOLD, 11);

        Toolkit toolkit = Toolkit.getDefaultToolkit();

        int xsize = (int) toolkit.getScreenSize().getWidth();
        int ysize = (int) toolkit.getScreenSize().getHeight();
        frame.setSize(xsize, ysize);


        tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(0, 0, xsize, ysize);
        tabbedPane.setTabPlacement(JTabbedPane.LEFT);
        mainPanel.add(tabbedPane);

        // loginPage=new LoginPage(null);
        final Inventory inventory = new Inventory(frame);
        final Sell sell = new Sell(frame, inventory);
        final Buy buy = new Buy(frame, inventory,sell);
        //  Paybills paybills = new Paybills(frame);

        tabbedPane.addTab("Inventory", inventory.initComponents(mainPanel));
        tabbedPane.addTab("Buy", buy.initComponents(mainPanel));
        tabbedPane.addTab("Sell", sell.initComponents(mainPanel));
        //  tabbedPane.addTab("PayBills", paybills.initComponents(mainPanel));

        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(tabbedPane.getSelectedIndex()==0){
                    inventory.updateInventoryTable();
                }
                else if(tabbedPane.getSelectedIndex()==1){
                    buy.addProductNameToJComboBox();
                }
                else if(tabbedPane.getSelectedIndex()==2){
                    sell.prodName();
                }
            }
        });

        //scrollPanel.add(mainPanel);

//        scrollPane = new JScrollPane(mainPanel);
//        scrollPanel.add(scrollPane);


        frame.add(mainPanel);
        frame.setAlwaysOnTop(true);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Inventory Management");

    }


}