package OracleConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Report {
    private JFrame frame;
    private JPanel panel;
    private JComboBox monthComboBox,yearComboBox;
    private Font f1, f2;
    private JButton rsales, rbuy, rexpenses,summary;
    private JTextField tyear, tmonth;
    private JLabel lyear,lmonth;
    private JTable buyTable,salesTable,expensesTable;
    private DefaultTableModel buyModel,salesModel,expensesModel;
    private JScrollPane buyScrollPane,salesScrollPane,expensesScrollPane;

    private String[] buyColumns = {"Product ID", "Name", "Supplier", "Date", "Quantity", "Amount (taka)"};
    private String[] buyRows = new String[6];

   private String[] salesColumns = {"Sale Id", "Product Name", "Date", "Quantity", "MRP"};
    private String[] salesRows = new String[5];

    private String[] expensesColumns = {"Expense Id", "Purpose", "Date", "Amount (taka)"};
    private String[] expensesRows = new String[4];


    public Report(JFrame frame) {
        this.frame = frame;

    }

    public JPanel initComponents() {

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(0xD9B9F2));

        f1 = new Font("Arial", Font.BOLD, 15);
        f2 = new Font("Arial", Font.BOLD, 16);



            JLabel head = new JLabel("Report");
            head.setHorizontalAlignment(SwingConstants.CENTER);
            head.setFont(new Font("Lato Medium", Font.PLAIN, 40));
            head.setBounds(450, 0, 600, 70);
            panel.add(head);


            rsales = new JButton("Sales");
            rsales.setBounds(250, 250, 120, 50);
            rsales.setBackground(Color.cyan);
            rsales.setFont(f2);
            rsales.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    salesTable = new JTable();
                    salesModel = new DefaultTableModel();
                    salesScrollPane = new JScrollPane(salesTable);
                    salesModel.setColumnIdentifiers(salesColumns);
                    salesTable.setModel(salesModel);
                    salesTable.setFont(f1);
                    salesTable.setBackground(Color.WHITE);
                    salesTable.setSelectionBackground(Color.GRAY);
                    salesTable.setRowHeight(30);

                    salesScrollPane.setBounds(200, 450, 1000, 300);
                    panel.add(salesScrollPane);
                }
            });
            panel.add(rsales);

            rbuy = new JButton("Buy");
            rbuy.setBounds(650, 250, 120, 50);
            rbuy.setBackground(Color.cyan);
            rbuy.setFont(f2);
            rbuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buyTable = new JTable();
                buyModel = new DefaultTableModel();
                buyScrollPane = new JScrollPane(buyTable);
                buyModel.setColumnIdentifiers(buyColumns);
                buyTable.setModel(buyModel);
                buyTable.setFont(f1);
                buyTable.setBackground(Color.WHITE);
                buyTable.setSelectionBackground(Color.GRAY);
                buyTable.setRowHeight(30);

                buyScrollPane.setBounds(200, 450, 1000, 300);
                panel.add(buyScrollPane);
            }
        });
            panel.add(rbuy);

            rexpenses = new JButton("Expenses");
            rexpenses.setBounds(1050, 250, 120, 50);
            rexpenses.setBackground(Color.cyan);
            rexpenses.setFont(f2);
            rexpenses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                expensesTable = new JTable();
                expensesModel = new DefaultTableModel();
                expensesScrollPane = new JScrollPane(expensesTable);
                expensesModel.setColumnIdentifiers(expensesColumns);
                expensesTable.setModel(expensesModel);
                expensesTable.setFont(f1);
                expensesTable.setBackground(Color.WHITE);
                expensesTable.setSelectionBackground(Color.GRAY);
                expensesTable.setRowHeight(30);

                expensesScrollPane.setBounds(200, 450, 1000, 300);
                panel.add(expensesScrollPane);
            }
        });
            panel.add(rexpenses);

        summary = new JButton("Summary");
        summary.setBounds(640, 370, 150, 30);
        summary.setBackground(Color.cyan);
        summary.setFont(f2);
        summary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame,"Profit or loss is (query)");
            }
        });
        panel.add(summary);

            lmonth = new JLabel("Month : ");
            lmonth.setBounds(200, 150, 150, 50);
            lmonth.setFont(f1);
            panel.add(lmonth);
            lyear = new JLabel("Year : ");
            lyear.setBounds(900, 150, 150, 50);
            lyear.setFont(f1);
            panel.add(lyear);
            monthComboBox = new JComboBox();
            monthComboBox.setBounds(280, 160, 200, 30);
            monthComboBox.setEditable(false);
            monthComboBox.setFont(f1);
            panel.add(monthComboBox);
            yearComboBox = new JComboBox();
            yearComboBox.setBounds(980, 160, 200, 30);
            yearComboBox.setEditable(false);
            yearComboBox.setFont(f1);
            panel.add( yearComboBox);

        frame.add(panel);
        frame.setAlwaysOnTop(true);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Report");

        Toolkit toolkit = Toolkit.getDefaultToolkit();

        int xsize = (int) toolkit.getScreenSize().getWidth();
        int ysize = (int) toolkit.getScreenSize().getHeight();
        frame.setSize(xsize, ysize);

            return  panel;


        }



}