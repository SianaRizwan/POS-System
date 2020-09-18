package OracleConnection;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class Dashboard {

    private JFrame frame;
    private JPanel mainPanel, logOutPanel;
    private Font f1, f2;
    public JTabbedPane tabbedPane;

    Dashboard(JFrame frame) {
        this.frame = frame;
        initComponents();

    }

    private void initComponents() {

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(0x5085E3));

        f1 = new Font("Arial", Font.PLAIN, 20);
        f2 = new Font("Arial", Font.BOLD, 11);

        Toolkit toolkit = Toolkit.getDefaultToolkit();

        int xsize = (int) toolkit.getScreenSize().getWidth();
        int ysize = (int) toolkit.getScreenSize().getHeight();
        frame.setSize(xsize, ysize);


        tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(0, 0, xsize, ysize);
        tabbedPane.setTabPlacement(JTabbedPane.LEFT);
        tabbedPane.setFont(f1);
        tabbedPane.setBackground(new Color(0x9DC9F5));
        mainPanel.add(tabbedPane);

        final Inventory inventory = new Inventory(frame);
        final Sell sell = new Sell(frame, inventory);
        final Buy buy = new Buy(frame, inventory,sell);
    /*    logOutPanel= new JPanel();
        logOutPanel.setLayout(null);
        logOutPanel.setBackground(new Color(0xA66BD4));*/

        tabbedPane.addTab("Inventory", inventory.initComponents(mainPanel));
        tabbedPane.addTab("Buy", buy.initComponents(mainPanel));
        tabbedPane.addTab("Sell", sell.initComponents(mainPanel));
        tabbedPane.addTab("Logout", logOutPanel);


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
                else if(tabbedPane.getSelectedIndex()==3){
                    int warningMsg = JOptionPane.showConfirmDialog(frame, "Do you want to Logout?", "Logout", JOptionPane.YES_NO_OPTION);
                    if(warningMsg==JOptionPane.YES_OPTION){
                        new LoginPage(frame);
                        mainPanel.setVisible(false);
                    }
                }
            }
        });


        frame.add(mainPanel);
        frame.setAlwaysOnTop(true);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Inventory Management");

    }


}