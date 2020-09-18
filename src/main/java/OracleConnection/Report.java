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
    private JTextField totalExpenseTF, totalSalesTF, netIncomeTF;
    private JLabel lyear, lmonth, labelTotalExpense, labelTotalSales, labelNetIncome;
    private JTable buyTable, salesTable, expensesTable;
    private DefaultTableModel buyModel, salesModel, expensesModel;
    private JScrollPane buyScrollPane, salesScrollPane, expensesScrollPane;

    int totalBill = 0;
    int totalBuy = 0;

    private String[] buyColumns = {"Date", "Product ID", "Name", "Supplier", "Buying price", "Quantity", "Unit MRP", "Total"};

    private String[] salesColumns = {"Date", "Product Name", "Seller", "Quantity", "MRP", "Total"};

    private String[] expensesColumns = {"Date", "Expense Id", "Purpose", "Amount (taka)", "Description"};

    private static String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};//month list.

    private JLabel totalLabel;
    private JTextField totalTextField;
    private Font f3;
    private JLabel head;
    BackgroundColor backgroundColor;

    public Report(JFrame frame) {
        this.frame = frame;
        backgroundColor = new BackgroundColor(frame);
    }

    public JPanel initComponents() {

        backgroundColor.setScreenSize(frame);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(0x93B3EE));

        f1 = new Font("Arial", Font.BOLD, 15);
        f2 = new Font("Arial", Font.PLAIN, 18);
        f3 = new Font("Arial", Font.BOLD, 18);


        head = new JLabel();
        showReportLabel( "REPORT");

        head.setHorizontalAlignment(SwingConstants.CENTER);
        head.setFont(new Font("Lato Medium", Font.BOLD, 40));
        head.setBounds(450, 0, 600, 70);
        panel.add(head);

        salesTable = new JTable();
        salesModel = new DefaultTableModel();
        salesScrollPane = new JScrollPane(salesTable);
        salesModel.setColumnIdentifiers(salesColumns);
        salesTable.setModel(salesModel);
        reportTableDesign(salesTable, salesScrollPane);

        buyTable = new JTable();
        buyModel = new DefaultTableModel();
        buyScrollPane = new JScrollPane(buyTable);
        buyModel.setColumnIdentifiers(buyColumns);
        buyTable.setModel(buyModel);
        reportTableDesign(buyTable, buyScrollPane);

        expensesTable = new JTable();
        expensesModel = new DefaultTableModel();
        expensesScrollPane = new JScrollPane(expensesTable);
        expensesModel.setColumnIdentifiers(expensesColumns);
        expensesTable.setModel(expensesModel);
        reportTableDesign(expensesTable, expensesScrollPane);

        totalLabel = new JLabel("TOTAL : ");
        totalLabel.setBounds(850, 670, 150, 50);
        totalLabel.setFont(f3);

        totalTextField = new JTextField();
        totalTextField.setBounds(950, 680, 200, 30);
        totalTextField.setFont(f2);


        labelTotalExpense = new JLabel("Net Expense : ");
        labelTotalExpense.setBounds(450, 255, 150, 50);
        labelTotalExpense.setFont(f3);

        labelTotalSales = new JLabel("Net Sales   : ");
        labelTotalSales.setBounds(450, 325, 150, 50);
        labelTotalSales.setFont(f3);

        labelNetIncome = new JLabel("Net Income  : ");
        labelNetIncome.setBounds(450, 385, 150, 50);
        labelNetIncome.setFont(f3);


        totalExpenseTF = new JTextField();
        totalExpenseTF.setBounds(600, 260, 250, 50);
        totalExpenseTF.setFont(f2);

        totalSalesTF = new JTextField();
        totalSalesTF.setBounds(600, 330, 250, 50);
        totalSalesTF.setFont(f2);

        netIncomeTF = new JTextField();
        netIncomeTF.setBounds(600, 390, 250, 50);
        netIncomeTF.setFont(f2);


        rsales = new JButton("SALES");
        rsales.setBounds(50, 360, 180, 30);
        backgroundColor.setButtonColor(rsales);
        rsales.setFont(f2);
        rsales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.remove(expensesScrollPane);
                panel.remove(buyScrollPane);
                panel.updateUI();
                salesYearComboFillUp();
                setSalesReport();
                panel.add(salesScrollPane);
                panel.add(totalLabel);
                panel.add(totalTextField);
                totalTextField.setText(String.valueOf(setTotalSales()));
                setSummaryInfoVisibility(false);
                setVisibilityTotal(true);
                totalTextField.setEditable(false);
                showReportLabel( "SALES REPORT");

            }
        });
        panel.add(rsales);


        rbuy = new JButton("PURCHASE");
        rbuy.setBounds(50, 310, 180, 30);
        backgroundColor.setButtonColor(rbuy);
        rbuy.setFont(f2);
        rbuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.remove(expensesScrollPane);
                panel.remove(salesScrollPane);
                panel.updateUI();
                buyYearComboFillUp();
                setBuyReport();
                panel.add(buyScrollPane);
                panel.add(totalLabel);
                panel.add(totalTextField);
                setSummaryInfoVisibility(false);
                totalTextField.setText(String.valueOf(getTotalBuy()));
                totalTextField.setEditable(false);
                setVisibilityTotal(true);
                showReportLabel("PURCHASE REPORT");


            }
        });
        panel.add(rbuy);


        rexpenses = new JButton("UTILITIES");
        rexpenses.setBounds(50, 410, 180, 30);
        backgroundColor.setButtonColor(rexpenses);
        rexpenses.setFont(f2);
        rexpenses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                panel.remove(salesScrollPane);
                panel.remove(buyScrollPane);
                panel.updateUI();
                expenseYearComboFillUp();
                setExpenseReport();
                panel.add(expensesScrollPane);
                panel.add(totalTextField);
                panel.add(totalLabel);
                totalTextField.setText(String.valueOf(getTotalPayBill()));
                setVisibilityTotal(true);
                showReportLabel( "EXPENSE REPORT");

                totalTextField.setEditable(false);
                setSummaryInfoVisibility(false);
            }
        });
        panel.add(rexpenses);

        summary = new JButton("SUMMARY");
        summary.setBounds(50, 260, 180, 30);
        backgroundColor.setButtonColor(summary);
        summary.setFont(f2);

        summary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.remove(expensesScrollPane);
                panel.remove(buyScrollPane);
                panel.remove(salesScrollPane);
                setSummaryInfoVisibility(true);
                showReportLabel("SUMMARY");
                setVisibilityTotal(false);
                panel.updateUI();
                summaryYearComboFillUp();
                summaryInfoPanelAdd();
                setSummaryDetails();

            }
        });
        panel.add(summary);

        lmonth = new JLabel("Month : ");
        lmonth.setBounds(410, 120, 150, 50);
        lmonth.setFont(f1);
        panel.add(lmonth);

        lyear = new JLabel("Year : ");
        lyear.setBounds(710, 120, 150, 50);
        lyear.setFont(f1);
        panel.add(lyear);

        monthComboBox = new JComboBox(months);
        monthComboBox.setBounds(470, 130, 200, 30);
        monthComboBox.setEditable(false);
        monthComboBox.setFont(f1);
        panel.add(monthComboBox);

        yearComboBox = new JComboBox();
        yearComboBox.setBounds(770, 130, 200, 30);
        yearComboBox.setEditable(false);
        yearComboBox.setFont(f1);
        panel.add(yearComboBox);

        frame.add(panel);

        return panel;


    }

    private void reportTableDesign(JTable jTable, JScrollPane jScrollPane) {
        jTable.setFont(f1);
        jTable.setBackground(Color.WHITE);
        jTable.setSelectionBackground(Color.GRAY);
        jTable.setRowHeight(30);
        jTable.setAutoCreateRowSorter(true);
        jScrollPane.setBounds(260, 180, 1000, 450);
    }

    private void showReportLabel( String s) {
        head.setForeground(Color.white);
        head.setText(s);

    }

    private void setVisibilityTotal(boolean status) {
        totalLabel.setVisible(status);
        totalTextField.setVisible(status);
    }

    private void setSummaryInfoVisibility(boolean status) {
        labelTotalExpense.setVisible(status);
        labelTotalSales.setVisible(status);
        labelNetIncome.setVisible(status);
        totalExpenseTF.setVisible(status);
        totalSalesTF.setVisible(status);
        netIncomeTF.setVisible(status);
    }

    private void summaryInfoPanelAdd() {
        panel.add(labelTotalExpense);
        panel.add(labelTotalSales);
        panel.add(labelNetIncome);
        panel.add(totalExpenseTF);
        panel.add(totalSalesTF);
        panel.add(netIncomeTF);
    }

    public void setBuyReport() {
        int n;
        try {
            int monthNumber = getMonthNumber();
            int yearName = Integer.parseInt(yearComboBox.getSelectedItem().toString());

            String sql = "select SUP_DATE,P_ID,SUPPLY_ORDER.S_NAME,SUPPLIER,S_PRICE,initial_qty,MRP, S_PRICE*initial_qty AS TOTAL FROM SUPPLY_ORDER,product" +
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

                    v.add(rs.getDate(1));
                    v.add(rs.getInt(2));
                    v.add(rs.getString(3));
                    v.add(rs.getString(4));
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

    public void setSalesReport() {
        int n;

        try {
            int monthNumber = getMonthNumber();
            int year = Integer.parseInt(yearComboBox.getSelectedItem().toString());


            OracleConnection oc = new OracleConnection();
            String sql = "select S.SALE_DATE ,SO.S_NAME , USERS.NAME,sum(SD.P_QUANTITY), SO.MRP,sum(SD.P_QUANTITY*SO.MRP) AS TOTAL " +
                    "FROM SALES S,USERS,SUPPLY_ORDER SO,PRODUCT,SALES_DETAILS SD " +
                    "where S.U_ID=USERS.U_ID AND S.SALE_ID = SD.SALE_ID AND SD.P_ID =PRODUCT.P_ID " +
                    "AND PRODUCT.S_ID=SO.S_ID AND extract (year from to_date(SALE_DATE,'dd-mon-yy'))='" + year +
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

    public void setSummaryDetails() {

        totalSalesTF.setText(String.valueOf(setTotalSales()));
        totalExpenseTF.setText(String.valueOf(setTotalExpense()));
        netIncomeTF.setText(String.valueOf(setTotalSales() - setTotalExpense()));
        totalSalesTF.setEditable(false);
        totalExpenseTF.setEditable(false);
        netIncomeTF.setEditable(false);
    }

    private int setTotalSales() {
        int toatlSales = 0;
        try {
            int monthNumber = getMonthNumber();
            int yearName = Integer.parseInt(yearComboBox.getSelectedItem().toString());
            OracleConnection oc = new OracleConnection();
            String sql = "select sum(sd.p_quantity*so.mrp) from sales_details sd,supply_order so,sales,product  " +
                    "where sales.sale_id=sd.sale_id AND SD.P_ID =PRODUCT.P_ID AND PRODUCT.S_ID=SO.S_ID " +
                    "and extract (month from to_date(sales.SALE_DATE,'yyyy-month-dd'))='" + monthNumber + "'" +
                    "AND extract (year from to_date(SALE_DATE,'dd-mon-yy'))='" + yearName + "'" +
                    " having sum(sd.p_quantity*so.mrp)=(select sum(sum(sd.p_quantity*so.mrp))  " +
                    "from sales_details sd,supply_order so,sales,product " +
                    " where sales.sale_id=sd.sale_id and SD.P_ID =PRODUCT.P_ID " +
                    "AND PRODUCT.S_ID=SO.S_ID and extract (month from to_date(sales.SALE_DATE,'yyyy-month-dd'))='" + monthNumber + "'" +
                    "AND extract (year from to_date(SALE_DATE,'dd-mon-yy'))='" + yearName + "'" +
                    " group by (sd.p_quantity*so.mrp)) ";

            PreparedStatement ps = oc.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                toatlSales = rs.getInt(1);

            }

        } catch (Exception e) {
            System.out.println(e + "  summary details");
        }
        return toatlSales;

    }

    private int setTotalExpense() {
        int toatlExpense = 0;

        totalBill = getTotalPayBill();
        totalBuy = getTotalBuy();
        toatlExpense = totalBill + totalBuy;
        return toatlExpense;

    }

    private int getTotalBuy() {
        try {
            int monthNumber = getMonthNumber();
            int yearName = Integer.parseInt(yearComboBox.getSelectedItem().toString());

            OracleConnection oc2 = new OracleConnection();
            String sql2 = "select sum(s_price*initial_qty) from supply_order where " +
                    "extract (month from to_date(supply_order.Sup_DATE,'yyyy-month-dd'))='" + monthNumber + "'" +
                    "AND extract (year from to_date(supply_order.Sup_DATE,'dd-mon-yy'))='" + yearName + "'";

            PreparedStatement ps2 = oc2.conn.prepareStatement(sql2);
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                totalBuy = rs2.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e + "  getTotalBuy");
        }
        return totalBuy;
    }

    private int getTotalPayBill() {
        try {
            int monthNumber = getMonthNumber();
            int yearName = Integer.parseInt(yearComboBox.getSelectedItem().toString());
            OracleConnection oc1 = new OracleConnection();
            String sql1 = "select sum(amount) from expenses where  " +
                    " extract (month from to_date(expenses.Sup_DATE,'yyyy-month-dd'))='" + monthNumber + "'" +
                    "AND extract (year from to_date(expenses.Sup_DATE,'dd-mon-yy'))='" + yearName + "'";


            PreparedStatement ps1 = oc1.conn.prepareStatement(sql1);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                totalBill = rs1.getInt(1);

            }
        } catch (Exception e) {
            System.out.println(e + "  getTotalPayBill");
        }
        return totalBill;
    }


    public void setExpenseReport() {
        int n;

        try {
            int monthNumber = getMonthNumber();
            int yearName = Integer.parseInt(yearComboBox.getSelectedItem().toString());

            OracleConnection oc = new OracleConnection();
            String sql = "select sup_date,e_id,purpose,amount,description from expenses where  extract (year from to_date(SUP_DATE,'dd-mon-yy'))='" + yearName + "' and extract (month from to_date(SUP_DATE,'yyyy-month-dd'))='" + monthNumber + "' ORDER BY e_id";

            PreparedStatement ps = oc.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsd = rs.getMetaData();
            n = rsd.getColumnCount();

            DefaultTableModel d = (DefaultTableModel) expensesTable.getModel();
            d.setRowCount(0);

            while (rs.next()) {
                Vector v = new Vector();

                for (int i = 1; i <= n; i++) {

                    v.add(rs.getDate(1));
                    v.add(rs.getInt(2));
                    v.add(rs.getString(3));
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

        Date date = new SimpleDateFormat("MMMM").parse(monthName);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
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

    public void summaryYearComboFillUp() {
        String sql = "(SELECT SUPPLY_ORDER.sup_date FROM SUPPLY_ORDER ) " +
                "union (SELECT EXPENSES.sup_date FROM EXPENSES )" +
                " union (SELECT SALES.sale_date FROM sales ) ";
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