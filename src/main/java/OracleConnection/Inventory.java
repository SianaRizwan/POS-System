package OracleConnection;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Inventory {
    private JFrame frame;
    private JPanel panel;
    private Font f1, f2;
    private JButton inventoryAddButton, inventoryDeleteButton;
    private JTable table;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    // private StatusBar statusBar;
    private String[] columns = {"Name", "Id", "MRP","Quantity"};
    private String[] rows = new String[4];


    public Inventory() {
        initComponents();
    }

    private void initComponents(){
        frame = new JFrame();

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.lightGray);

        /*statusBar = new StatusBar();
        statusBar.setZoneBorder(BorderFactory.createLineBorder(Color.GRAY));
        statusBar.setZones(
                new String[] { "first_zone", "second_zone", "remaining_zones" },
                new Component[] {
                        new JLabel("first"),
                        new JLabel("second"),
                        new JLabel("remaining")
                },
                new String[] {"25%", "25%", "*"}
        );*/

        f1 = new Font("Arial", Font.BOLD, 15);
        f2 = new Font("Arial", Font.BOLD, 11);

        /*inventoryAddButton = new JButton("Add New");
        inventoryAddButton.setBounds(520, 240, 90, 25);
        inventoryAddButton.setBackground(Color.cyan);
        inventoryAddButton.setFont(f2);
        panel.add(inventoryAddButton);*/

        inventoryDeleteButton = new JButton("Delete");
        inventoryDeleteButton.setBounds(600, 240, 90, 25);
        inventoryDeleteButton.setBackground(Color.cyan);
        inventoryDeleteButton.setFont(f2);
        panel.add(inventoryDeleteButton);

        table = new JTable();
        model = new DefaultTableModel();
        scrollPane = new JScrollPane(table);
        model.setColumnIdentifiers(columns);
        table.setModel(model);
        table.setFont(f1);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(Color.GRAY);
        table.setRowHeight(30);

        scrollPane.setBounds(120,350,1000,300);
        panel.add(scrollPane);


        frame.add(panel);
        frame.setAlwaysOnTop(true);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Inventory Management");

        Toolkit toolkit = Toolkit.getDefaultToolkit();

        int xsize = (int) toolkit.getScreenSize().getWidth();
        int ysize = (int) toolkit.getScreenSize().getHeight();
        frame.setSize(xsize, ysize);
    }

    public static void main(String[] args) {
        new Inventory();
    }
}
