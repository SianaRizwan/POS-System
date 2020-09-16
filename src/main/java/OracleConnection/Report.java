package OracleConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Report {
    private JFrame frame;
    private JPanel panel;
    private JComboBox monthComboBox, yearComboBox;
    private Font f1, f2;
    private JButton rsales, rbuy, rexpenses, summary;
    private JTextField tyear, tmonth, tcost, tsales, tprofit, tloss;
    private JLabel lyear, lmonth, lcost, lsales, lprofit, lloss;
    private JTable buyTable, salesTable, expensesTable;
    private DefaultTableModel buyModel, salesModel, expensesModel;
    private JScrollPane buyScrollPane, salesScrollPane, expensesScrollPane;

    private String[] buyColumns = {"Product ID", "Name", "Supplier", "Date", "Buying price", "Quantity", "Unit MRP", "Total"};
    private String[] buyRows = new String[8];

    private String[] salesColumns = {"Date", "Product Name", "Seller", "Quantity", "MRP", "Total"};
    private String[] salesRows = new String[5];

    private String[] expensesColumns = {"Expense Id", "Purpose", "Date", "Amount (taka)", "Description"};
    private String[] expensesRows = new String[5];

    private static String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};//month list.


    public Report(JFrame frame) {
        this.frame = frame;
        //    salesYearComboFillUp();

    }

    public JPanel initComponents() {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int xsize = (int) toolkit.getScreenSize().getWidth();
        int ysize = (int) toolkit.getScreenSize().getHeight();
        frame.setSize(xsize, ysize);

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
        rsales.setBounds(250, 150, 120, 50);
        rsales.setBackground(Color.cyan);
        rsales.setFont(f2);
        rsales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salesYearComboFillUp();
                salesTable = new JTable();
                salesModel = new DefaultTableModel();
                salesScrollPane = new JScrollPane(salesTable);
                salesModel.setColumnIdentifiers(salesColumns);
                salesTable.setModel(salesModel);
                salesTable.setFont(f1);
                salesTable.setBackground(Color.WHITE);
                salesTable.setSelectionBackground(Color.GRAY);
                salesTable.setRowHeight(30);
                salesTable.setAutoCreateRowSorter(true);

                salesScrollPane.setBounds(200, 450, 1000, 300);
                salesTable();
                panel.add(salesScrollPane);
            }
        });
        panel.add(rsales);

        rbuy = new JButton("Buy");
        rbuy.setBounds(650, 150, 120, 50);
        rbuy.setBackground(Color.cyan);
        rbuy.setFont(f2);
        rbuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buyYearComboFillUp();
                buyTable = new JTable();
                buyModel = new DefaultTableModel();
                buyScrollPane = new JScrollPane(buyTable);
                buyModel.setColumnIdentifiers(buyColumns);
                buyTable.setModel(buyModel);
                buyTable.setFont(f1);
                buyTable.setBackground(Color.WHITE);
                buyTable.setSelectionBackground(Color.GRAY);
                buyTable.setRowHeight(30);
                buyTable.setAutoCreateRowSorter(true);
                buyScrollPane.setBounds(200, 450, 1000, 300);
                buyTable();
                panel.add(buyScrollPane);
            }
        });
        panel.add(rbuy);

        rexpenses = new JButton("Expenses");
        rexpenses.setBounds(1050, 150, 120, 50);
        rexpenses.setBackground(Color.cyan);
        rexpenses.setFont(f2);
        rexpenses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                expenseYearComboFillUp();
                expensesTable = new JTable();
                expensesModel = new DefaultTableModel();
                expensesScrollPane = new JScrollPane(expensesTable);
                expensesModel.setColumnIdentifiers(expensesColumns);
                expensesTable.setModel(expensesModel);
                expensesTable.setFont(f1);
                expensesTable.setBackground(Color.WHITE);
                expensesTable.setSelectionBackground(Color.GRAY);
                expensesTable.setRowHeight(30);
                expensesTable.setAutoCreateRowSorter(true);
                expensesScrollPane.setBounds(200, 450, 1000, 300);
                expenseTable();
                panel.add(expensesScrollPane);
            }
        });
        panel.add(rexpenses);

        summary = new JButton("Summary");
        summary.setBounds(640, 260, 150, 30);
        summary.setBackground(Color.cyan);
        summary.setFont(f2);

        summary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(frame,"Profit or loss is (query)");
                panel.remove(expensesScrollPane);
                panel.remove(buyScrollPane);
                panel.remove(salesScrollPane);
                panel.updateUI();
                lcost = new JLabel("Net Cost : ");
                lcost.setBounds(450, 450, 150, 50);
                lcost.setFont(f2);
                panel.add(lcost);

                lsales = new JLabel("Net Sales : ");
                lsales.setBounds(450, 500, 150, 50);
                lsales.setFont(f2);
                panel.add(lsales);

                lprofit = new JLabel("Profit : ");
                lprofit.setBounds(450, 550, 150, 50);
                lprofit.setFont(f2);
                panel.add(lprofit);

                lloss = new JLabel("Loss : ");
                lloss.setBounds(450, 600, 150, 50);
                lloss.setFont(f2);
                panel.add(lloss);


                tcost = new JTextField();
                tcost.setBounds(600, 460, 250, 30);
                tcost.setFont(f2);
                panel.add(tcost);

                tsales = new JTextField();
                tsales.setBounds(600, 510, 250, 30);
                tsales.setFont(f2);
                panel.add(tsales);

                tprofit = new JTextField();
                tprofit.setBounds(600, 560, 250, 30);
                tprofit.setFont(f2);
                panel.add(tprofit);

                tloss = new JTextField();
                tloss.setBounds(600, 610, 250, 30);
                tloss.setFont(f2);
                panel.add(tloss);


            }
        });
        panel.add(summary);

        lmonth = new JLabel("Month : ");
        lmonth.setBounds(xsize / 4, 320, 150, 50);
        lmonth.setFont(f1);
        panel.add(lmonth);

        lyear = new JLabel("Year : ");
        lyear.setBounds(xsize / 2, 320, 150, 50);
        lyear.setFont(f1);
        panel.add(lyear);

        monthComboBox = new JComboBox(months);
        monthComboBox.setBounds((xsize / 4) + 60, 330, 200, 30);
        monthComboBox.setEditable(false);
        monthComboBox.setFont(f1);
        panel.add(monthComboBox);

        yearComboBox = new JComboBox();
        yearComboBox.setBounds((xsize / 2) + 60, 330, 200, 30);
        yearComboBox.setEditable(false);
        yearComboBox.setFont(f1);
        panel.add(yearComboBox);

        frame.add(panel);
        frame.setAlwaysOnTop(true);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Report");


        return panel;


    }

    public void buyTable() {
        int n;
        try {
            int monthNumber = getMonthNumber();
            int yearName = Integer.parseInt(yearComboBox.getSelectedItem().toString());

            String sql = "select P_ID,SUPPLY_ORDER.S_NAME,SUPPLIER,SUP_DATE,S_PRICE,initial_qty,MRP, S_PRICE*initial_qty AS TOTAL FROM SUPPLY_ORDER,product" +
                    " where SUPPLY_ORDER.s_name=product.s_name and extract ( month from to_date(SUP_DATE,'yyyy-month-dd'))='" + monthNumber +
                    "' AND extract (year from to_date(SUP_DATE,'dd-mon-yy'))='" + yearName +
                    "' ORDER BY p_ID,SUPPLY_ORDER.S_NAME,SUP_DATE";
            OracleConnection oc = new OracleConnection();
            PreparedStatement ps = oc.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsd = rs.getMetaData();
            n = rsd.getColumnCount();

            DefaultTableModel d = (DefaultTableModel) buyTable.getModel();
            d.setRowCount(0);

            while (rs.next()) {
                Vector v = new Vector();

                for (int i = 1; i <= n; i++) {

                    v.add(rs.getInt(1));
                    v.add(rs.getString(2));
                    v.add(rs.getString(3));
                    v.add(rs.getDate(4));
                    v.add(rs.getInt(5));
                    v.add(rs.getInt(6));
                    v.add(rs.getInt(7));
                    v.add(rs.getInt(8));

                }
                d.addRow(v);
            }


        } catch (Exception e) {
            System.out.println(e + " buy table report");
        }

    }

    public void salesTable() {
        int n;

        try {
            int monthNumber = getMonthNumber();
            int yearName = Integer.parseInt(yearComboBox.getSelectedItem().toString());


            OracleConnection oc = new OracleConnection();
            String sql = "select S.SALE_DATE ,SO.S_NAME , USERS.NAME,sum(SD.P_QUANTITY), SO.MRP,sum(SD.P_QUANTITY*SO.MRP) AS TOTAL " +
                    "FROM SALES S,USERS,SUPPLY_ORDER SO,PRODUCT,SALES_DETAILS SD " +
                    "where S.U_ID=USERS.U_ID AND S.SALE_ID = SD.SALE_ID AND SD.P_ID =PRODUCT.P_ID " +
                    "AND PRODUCT.S_ID=SO.S_ID AND extract (year from to_date(SALE_DATE,'dd-mon-yy'))='" + yearName +
                    "' AND extract (month from to_date(SALE_DATE,'yyyy-month-dd'))='" + monthNumber +
                    "' GROUP BY S.SALE_DATE ,SO.S_NAME , USERS.NAME, SO.MRP" +
                    " ORDER BY S.SALE_DATE ,SO.S_NAME";
            PreparedStatement ps = oc.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsd = rs.getMetaData();
            n = rsd.getColumnCount();

            DefaultTableModel d = (DefaultTableModel) salesTable.getModel();
            d.setRowCount(0);

            while (rs.next()) {
                Vector v = new Vector();

                for (int i = 1; i <= n; i++) {

                    v.add(rs.getDate(1));
                    v.add(rs.getString(2));
                    v.add(rs.getString(3));
                    v.add(rs.getInt(4));
                    v.add(rs.getInt(5));
                    v.add(rs.getInt(6));

                }
                d.addRow(v);
            }


        } catch (Exception e) {
            System.out.println(e + " sales table");
        }

    }

    public void expenseTable() {
        int n;

        try {
            int monthNumber = getMonthNumber();
            int yearName = Integer.parseInt(yearComboBox.getSelectedItem().toString());

            OracleConnection oc = new OracleConnection();
            String sql = "select e_id,purpose,sup_date,amount,description from expenses where  extract (year from to_date(SUP_DATE,'dd-mon-yy'))='" + yearName + "' and extract (month from to_date(SUP_DATE,'yyyy-month-dd'))='" + monthNumber + "' ORDER BY e_id";

            PreparedStatement ps = oc.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsd = rs.getMetaData();
            n = rsd.getColumnCount();

            DefaultTableModel d = (DefaultTableModel) expensesTable.getModel();
            d.setRowCount(0);

            while (rs.next()) {
                Vector v = new Vector();

                for (int i = 1; i <= n; i++) {

                    v.add(rs.getInt(1));
                    v.add(rs.getString(2));
                    v.add(rs.getDate(3));
                    v.add(rs.getInt(4));
                    v.add(rs.getString(5));

                }
                d.addRow(v);
            }


        } catch (Exception e) {
            System.out.println(e + " expense table");
        }

    }

    private int getMonthNumber() throws ParseException {
        String monthName = monthComboBox.getSelectedItem().toString();
        //System.out.println(monthName);

        Date date = new SimpleDateFormat("MMMM").parse(monthName);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //System.out.println(monthNumber);
        return cal.get(Calendar.MONTH) + 1;
    }


    public void salesYearComboFillUp() {
        String sql = "SELECT sale_date FROM SALES order by sale_date ASC";
        yearComboFillup(sql);
    }

    public void buyYearComboFillUp() {
        String sql = "SELECT distinct sup_date FROM SUPPLY_ORDER order by sup_date ASC";
        yearComboFillup(sql);
    }

    public void expenseYearComboFillUp() {
        String sql = "SELECT sup_date FROM EXPENSES order by sup_date ASC";
        yearComboFillup(sql);
    }

    private void yearComboFillup(String sql) {
        yearComboBox.removeAllItems();
        try {
            OracleConnection oc2 = new OracleConnection();
            PreparedStatement ps2 = oc2.conn.prepareStatement(sql);
            ResultSet rs2 = ps2.executeQuery();
            ArrayList<Integer> arrayList = new ArrayList<Integer>();

            while (rs2.next()) {
                Date d1 = new Date(rs2.getDate(1).getTime());
                int year = d1.getYear() + 1900;

                arrayList.add(year);

            }
            //remove duplicate
            LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<Integer>(arrayList);
            arrayList.clear();
            arrayList.addAll(linkedHashSet);
            while (!arrayList.isEmpty()) {
                int year = arrayList.remove(arrayList.size() - 1);
                System.out.println(year);
                yearComboBox.addItem(year);
            }

        } catch (Exception e) {
            System.out.println(e + " yearComboFillUp");
        }
    }

}