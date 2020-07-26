package OracleConnection;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard {

    private JFrame frame;
    private JPanel mainPanel, reportPanel;
    private Font f1, f2;
    private JTabbedPane tabbedPane;


    public AdminDashboard(JFrame frame) {
        this.frame = frame;
        initComponents();
    }

    private void initComponents() {
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

        Inventory inventory = new Inventory(frame);
        Paybills paybills = new Paybills(frame);
        Report report = new Report(frame);
        Salary salary = new Salary(frame);

        tabbedPane.addTab("Inventory", inventory.initComponents(mainPanel));
        tabbedPane.addTab("PayBills", paybills.initComponents(mainPanel));
        tabbedPane.addTab("Salary", salary.initComponents());
        tabbedPane.addTab("Report", report.initComponents(mainPanel));

        frame.add(mainPanel);
        frame.setAlwaysOnTop(true);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Inventory Management");

    }
}