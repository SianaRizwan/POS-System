package OracleConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;
import java.util.Vector;

public class Dashboard {

    private JFrame frame;
    private JPanel mainPanel, panelInventory, panelBuy, panelSell, panelPayBills, panelReport;
    private Font f1, f2;
    private JTabbedPane tabbedPane;

    private JComboBox buyComboBox;//buy
    private JLabel buyNameLabel, buyIdLabel, buySupplierLabel, buy_priceLabel, buyMRPLabel, buyQuantityLabel, buyDateLabel;
    private JTextField buyIdTextField, buySupplierTextField, buy_priceTextField, buyMRPTextField, buyQuantityTextField,
            buyDateTextField;//Buy
    private JButton buyAddNewButton, buySaveButton;//buy


    private JComboBox sellComboBox;//sell
    private JLabel sellNameLabel;
    private JLabel sellIdLabel;
    private JTextField sellIdTextField;
    private JLabel sellMRPLabel;
    private JTextField sellMRPTextField;
    private JLabel sellQuantityLabel;
    private JLabel sellDateLabel;
    private JTextField sellQuantityTextField, sellDateTextField;//sell
    private JButton invoiceButton, sellSaveButton;//sell

    private JComboBox expenseComboBox;
    private JLabel expId, purpose, amount, date, description;//expense Paybills
    private JTextField tfExpId, tfAmount, tfDate, tfDescription;//expense Paybills
    private JButton expSaveButton,expDelButton;
    private static String[] purposes = {"Select an option", "Employee Salary", "Rent", "Utility Bills", "Others"};//expense list.

    private JButton inventoryDeleteButton;//inventory

    private JTable inventoryTable,sellTable,expTable;
    private DefaultTableModel inventoryModel,sellModel,expModel;
    private JScrollPane inventoryScrollPane,sellScrollPane,expScrollPane;


    private String[] inventoryColumns = {"Name", "Id", "MRP","Quantity"};
    private String[] inventoryRows = new String[4];
    private String[] sellColumns = {"Name", "Id", "MRP"};
    private String[] sellRows = new String[3];
    private String[] expenseColumns = {"Purpose", "Id", "Amount","Date","Description"};
    private String[] expenseRows = new String[5];


    OracleConnection oc = new OracleConnection();
    PreparedStatement ps;
    ResultSet rs;

    Dashboard(JFrame frame) {
        this.frame = frame;
        initComponents();
        prodName();
        table_update_inventory();
    }

    public class productName {
        int id;
        String name;

        public productName(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public String toString() {
            return name;
        }
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

        panelInventory = new JPanel();
        panelInventory.setLayout(null);
        panelInventory.setBackground(new Color(0xD9B9F2));

        panelBuy = new JPanel();
        panelBuy.setLayout(null);
        panelBuy.setBackground(new Color(0xD9B9F2));

        panelSell = new JPanel();
        panelSell.setLayout(null);
        panelSell.setBackground(new Color(0xD9B9F2));

        panelPayBills = new JPanel();
        panelPayBills.setLayout(null);
        panelPayBills.setBackground(new Color(0xD9B9F2));

        panelReport = new JPanel();
        panelReport.setLayout(null);
        panelReport.setBackground(new Color(0xD9B9F2));

        tabbedPane.addTab("Inventory", panelInventory);
        tabbedPane.addTab("Buy", panelBuy);
        tabbedPane.addTab("Sell", panelSell);
        tabbedPane.addTab("PayBills", panelPayBills);
        tabbedPane.addTab("Report", panelReport);

        {
            inventoryDeleteButton = new JButton("Delete");
            inventoryDeleteButton.setBounds(600, 240, 90, 25);
            inventoryDeleteButton.setBackground(Color.cyan);
            inventoryDeleteButton.setFont(f2);
            panelInventory.add(inventoryDeleteButton);

            inventoryTable = new JTable();
            inventoryModel = new DefaultTableModel();
            inventoryScrollPane = new JScrollPane(inventoryTable);
            inventoryModel.setColumnIdentifiers(inventoryColumns);
            inventoryTable.setModel(inventoryModel);
            inventoryTable.setFont(f1);
            inventoryTable.setBackground(Color.WHITE);
            inventoryTable.setSelectionBackground(Color.GRAY);
            inventoryTable.setRowHeight(30);

            inventoryScrollPane.setBounds(150,350,1000,300);
            panelInventory.add(inventoryScrollPane);




        }
        {
            ///Buy Tab

            buyComboBox = new JComboBox();
            buyComboBox.setBounds(600, 110, 200, 30);
            panelBuy.add(buyComboBox);
            buyComboBox.setEditable(false);

            buyNameLabel = new JLabel("Product Name : ");
            buyNameLabel.setBounds(450, 100, 150, 50);
            buyNameLabel.setFont(f1);
            panelBuy.add(buyNameLabel);

            buyIdLabel = new JLabel("Product Id : ");
            buyIdLabel.setBounds(450, 150, 150, 50);
            buyIdLabel.setFont(f1);
            panelBuy.add(buyIdLabel);


            buySupplierLabel = new JLabel("Supplier : ");
            buySupplierLabel.setBounds(450, 200, 150, 50);
            buySupplierLabel.setFont(f1);
            panelBuy.add(buySupplierLabel);

            buy_priceLabel = new JLabel("Buying Price : ");
            buy_priceLabel.setBounds(450, 250, 150, 50);
            buy_priceLabel.setFont(f1);
            panelBuy.add(buy_priceLabel);

            buyMRPLabel = new JLabel("MRP : ");
            buyMRPLabel.setBounds(450, 300, 150, 50);
            buyMRPLabel.setFont(f1);
            panelBuy.add(buyMRPLabel);

            buyQuantityLabel = new JLabel("Quantity : ");
            buyQuantityLabel.setBounds(450, 350, 150, 50);
            buyQuantityLabel.setFont(f1);
            panelBuy.add(buyQuantityLabel);

            buyDateLabel = new JLabel("Date : ");
            buyDateLabel.setBounds(450, 400, 150, 50);
            buyDateLabel.setFont(f1);
            panelBuy.add(buyDateLabel);

            buyIdTextField = new JTextField();
            buyIdTextField.setBounds(600, 160, 200, 30);
            buyIdTextField.setFont(f1);
            panelBuy.add(buyIdTextField);
            buyIdTextField.setEditable(false);


            buyComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    try {
                        // OracleConnect oc = new OracleConnect();
                        Statement st = oc.conn.createStatement();
                        String sql = "select P_ID,NAME from PRODUCT where NAME='" + buyComboBox.getSelectedItem() + "'";
                        ResultSet rs = st.executeQuery(sql);

                        while (rs.next()) {
                            buyIdTextField.setText(String.valueOf(rs.getInt("P_ID")));
                        }


                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            });


            buySupplierTextField = new JTextField();
            buySupplierTextField.setBounds(600, 210, 200, 30);
            buySupplierTextField.setFont(f1);
            panelBuy.add(buySupplierTextField);

            buy_priceTextField = new JTextField();
            buy_priceTextField.setBounds(600, 260, 200, 30);
            buy_priceTextField.setFont(f1);
            panelBuy.add(buy_priceTextField);

            buyMRPTextField = new JTextField();
            buyMRPTextField.setBounds(600, 310, 200, 30);
            buyMRPTextField.setFont(f1);
            panelBuy.add(buyMRPTextField);

            buyQuantityTextField = new JTextField();
            buyQuantityTextField.setBounds(600, 360, 200, 30);
            buyQuantityTextField.setFont(f1);
            panelBuy.add(buyQuantityTextField);

            buyDateTextField = new JTextField();
            buyDateTextField.setBounds(600, 410, 200, 30);
            buyDateTextField.setFont(f1);
            panelBuy.add(buyDateTextField);

            buyAddNewButton = new JButton("Add New");
            buyAddNewButton.setBounds(850, 110, 100, 30);
            buyAddNewButton.setFont(f2);
            buyAddNewButton.setForeground(new Color(0xFEFEFE));

            buyAddNewButton.setBackground(new Color(0x7E0AB5));
            buyAddNewButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new ProductEntry(frame);
                    mainPanel.setVisible(false);
                }
            });
            panelBuy.add(buyAddNewButton);


            buySaveButton = new JButton("Save");
            buySaveButton.setFont(f2);
            buySaveButton.setBounds(580, 550, 80, 30);
            buySaveButton.setBackground(new Color(0x7E0AB5));
            buySaveButton.setForeground(new Color(0xFEFEFE));

            panelBuy.add(buySaveButton);
            buySaveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {


                    try {
                        String buyDate1 = buyDateTextField.getText();
                        Date sqlBuyDate1 = Date.valueOf(buyDate1);


                        String sql_SUPPLY_ORDER = "insert into SUPPLY_ORDER (S_NAME, S_PRICE, S_QUANTITY, MRP, SUPPLIER, SUP_DATE) values(?, ?, ?, ?, ?, ?)";
                        ps = oc.conn.prepareStatement(sql_SUPPLY_ORDER);
                        ps.setString(1, buyComboBox.getSelectedItem().toString());
                        ps.setInt(2, Integer.parseInt(buy_priceTextField.getText().trim()));
                        ps.setInt(3, Integer.parseInt(buyQuantityTextField.getText().trim()));
                        ps.setInt(4, Integer.parseInt(buyMRPTextField.getText().trim()));
                        ps.setString(5, buySupplierTextField.getText().trim());
                        ps.setDate(6, sqlBuyDate1);
                        ps.executeUpdate();
                        table_update_inventory();

                        buy_priceTextField.setText("");
                        buyQuantityTextField.setText("");
                        buyMRPTextField.setText("");
                        buySupplierTextField.setText("");
                        buyDateTextField.setText("");

                    } catch (Exception e2) {
                        System.out.println(e2);
                    } finally {
                        try {
                            ps.close();
                        } catch (SQLException ex) {
                            System.out.println(ex);
                        }
                    }
                }
            });


        }

        {
            ///Sell Tab

            sellComboBox = new JComboBox();
            sellComboBox.setBounds(600, 160, 200, 30);
            panelSell.add(sellComboBox);
            sellComboBox.setEditable(false);

            sellNameLabel = new JLabel("Product Name : ");
            sellNameLabel.setBounds(450, 150, 150, 50);
            sellNameLabel.setFont(f1);
            panelSell.add(sellNameLabel);

            sellIdLabel = new JLabel("Product Id : ");
            sellIdLabel.setBounds(450, 200, 150, 50);
            sellIdLabel.setFont(f1);
            panelSell.add(sellIdLabel);


            sellMRPLabel = new JLabel("MRP : ");
            sellMRPLabel.setBounds(450, 250, 150, 50);
            sellMRPLabel.setFont(f1);
            panelSell.add(sellMRPLabel);

            sellQuantityLabel = new JLabel("Quantity : ");
            sellQuantityLabel.setBounds(450, 300, 150, 50);
            sellQuantityLabel.setFont(f1);
            panelSell.add(sellQuantityLabel);

            sellDateLabel = new JLabel("Date : ");
            sellDateLabel.setBounds(450, 350, 150, 50);
            sellDateLabel.setFont(f1);
            panelSell.add(sellDateLabel);

            sellIdTextField = new JTextField();
            sellIdTextField.setBounds(600, 210, 200, 30);
            sellIdTextField.setFont(f1);
            panelSell.add(sellIdTextField);
            sellIdTextField.setEditable(false);

            sellComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    try {
                        OracleConnection oc = new OracleConnection();
                        Statement st = oc.conn.createStatement();
                        String sql = "select P_ID,NAME,MRP from PRODUCT , SUPPLY_ORDER where PRODUCT.S_NAME=SUPPLY_ORDER.S_NAME and NAME='" + sellComboBox.getSelectedItem() + "'";
                        ResultSet rs = st.executeQuery(sql);

                        while (rs.next()) {
                            sellIdTextField.setText(String.valueOf(rs.getInt("P_ID")));
                            //  sellManufacturerTextField.setText(rs.getString("MANUFACTURER"));
                            sellMRPTextField.setText(String.valueOf(rs.getInt("MRP")));
                        }


                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            });


            sellMRPTextField = new JTextField();
            sellMRPTextField.setBounds(600, 260, 200, 30);
            sellMRPTextField.setFont(f1);
            panelSell.add(sellMRPTextField);
            sellMRPTextField.setEditable(false);

            sellQuantityTextField = new JTextField();
            sellQuantityTextField.setBounds(600, 310, 200, 30);
            sellQuantityTextField.setFont(f1);
            panelSell.add(sellQuantityTextField);

            sellDateTextField = new JTextField();
            sellDateTextField.setBounds(600, 360, 200, 30);
            sellDateTextField.setFont(f1);
            panelSell.add(sellDateTextField);


            sellSaveButton = new JButton("Save");
            sellSaveButton.setBounds(650, 450, 100, 30);
            sellSaveButton.setBackground(new Color(0x7E0AB5));
            sellSaveButton.setForeground(new Color(0xFEFEFE));

            sellSaveButton.setFont(f2);
            panelSell.add(sellSaveButton);

            invoiceButton = new JButton("Invoice");
            invoiceButton.setBounds(800, 450, 100, 30);
            invoiceButton.setBackground(new Color(0x7E0AB5));
            invoiceButton.setForeground(new Color(0xFEFEFE));

            invoiceButton.setFont(f2);
            panelSell.add(invoiceButton);

            invoiceButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new CreateInvoice(frame);
                    mainPanel.setVisible(false);
                }
            });

            sellTable = new JTable();
            sellModel = new DefaultTableModel();
            sellScrollPane = new JScrollPane(sellTable);
            sellModel.setColumnIdentifiers(sellColumns);
            sellTable.setModel(sellModel);
            sellTable.setFont(f1);
            sellTable.setBackground(Color.WHITE);
            sellTable.setSelectionBackground(Color.GRAY);
            sellTable.setRowHeight(30);

            sellScrollPane.setBounds(150,610,1000,300);
            panelSell.add(sellScrollPane);
        }

        {
            ///Paybills Tab

            expenseComboBox = new JComboBox(purposes);
            expenseComboBox.setBounds(550, 210, 200, 30);
            expenseComboBox.setEditable(false);
            panelPayBills.add(expenseComboBox);

            purpose = new JLabel("Purpose : ");
            purpose.setBounds(450, 200, 150, 50);
            purpose.setFont(f1);
            panelPayBills.add(purpose);

            amount = new JLabel("Amount : ");
            amount.setBounds(450, 250, 150, 50);
            amount.setFont(f1);
            panelPayBills.add(amount);

            date = new JLabel("Date : ");
            date.setBounds(450, 300, 150, 50);
            date.setFont(f1);
            panelPayBills.add(date);

            description = new JLabel("Description : ");
            description.setBounds(450, 350, 150, 50);
            description.setFont(f1);
            panelPayBills.add(description);

            expId = new JLabel("Expense Id : ");
            expId.setBounds(450, 400, 150, 50);
            expId.setFont(f1);
            panelPayBills.add(expId);

            tfAmount = new JTextField();
            tfAmount.setBounds(550, 260, 200, 30);
            tfAmount.setFont(f1);
            panelPayBills.add(tfAmount);

            tfDate = new JTextField();
            tfDate.setBounds(550, 310, 200, 30);
            tfDate.setFont(f1);
            panelPayBills.add(tfDate);

            tfDescription = new JTextField();
            tfDescription.setBounds(550, 360, 200, 30);
            tfDescription.setFont(f1);
            panelPayBills.add(tfDescription);

            tfExpId = new JTextField();
            tfExpId.setBounds(550, 410, 200, 30);
            tfExpId.setFont(f1);
            panelPayBills.add(tfExpId);

            expSaveButton = new JButton("Save");
            expSaveButton.setFont(f2);
            expSaveButton.setForeground(new Color(0xFEFEFE));

            expSaveButton.setBackground(new Color(0x7E0AB5));
            expSaveButton.setBounds(600, 500, 100, 30);
            panelPayBills.add(expSaveButton);
            expSaveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        //date
                        String expDate = tfDate.getText();
                        Date sqlExpDate = Date.valueOf(expDate);

                        //purpose
                        String purpose = expenseComboBox.getSelectedItem().toString();

                        // OracleConnect oc = new OracleConnect();
                        String sqlExpense = "insert into EXPENSES (E_ID, PURPOSE, SUP_DATE, DESCRIPTION, AMOUNT) values(?, ?, ?, ?, ?)";
                        ps = oc.conn.prepareStatement(sqlExpense);

                        ps.setInt(1, Integer.parseInt(tfExpId.getText()));
                        ps.setString(2, purpose);
                        ps.setDate(3, sqlExpDate);
                        ps.setString(4, tfDescription.getText());
                        ps.setInt(5, Integer.parseInt(tfAmount.getText()));

                        ps.executeUpdate();
                        tfExpId.setText("");
                        tfDate.setText("");
                        tfDescription.setText("");
                        tfAmount.setText("");

                    } catch (Exception eq) {
                        System.out.println(eq);
                    } finally {
                        try {
                            ps.close();
                        } catch (SQLException ex) {
                            System.out.println(ex);
                        }
                    }
                }
            });

            expDelButton = new JButton("Delete");
            expDelButton.setBounds(750, 500, 100, 30);
            expDelButton.setBackground(new Color(0x7E0AB5));
            expDelButton.setForeground(new Color(0xFEFEFE));

            expDelButton.setFont(f2);
            panelPayBills.add(expDelButton);

            expTable = new JTable();
            expModel = new DefaultTableModel();
            expScrollPane = new JScrollPane(expTable);
            expModel.setColumnIdentifiers(expenseColumns);
            expTable.setModel(expModel);
            expTable.setFont(f1);
            expTable.setBackground(Color.WHITE);
            expTable.setSelectionBackground(Color.GRAY);
            expTable.setRowHeight(30);

            expScrollPane.setBounds(150,610,1000,300);
            panelPayBills.add(expScrollPane);
        }

        frame.add(mainPanel);
        frame.setAlwaysOnTop(true);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Inventory Management");


     /*  Toolkit toolkit = Toolkit.getDefaultToolkit();

        int xsize = (int) toolkit.getScreenSize().getWidth();
        int ysize = (int) toolkit.getScreenSize().getHeight();
        frame.setSize(xsize, ysize);*/
    }

    private void prodName() {
        try {
            String sql = "select * from PRODUCT";
            ps = oc.conn.prepareStatement(sql);
            rs = ps.executeQuery();
            buyComboBox.removeAllItems();
            sellComboBox.removeAllItems();
            while (rs.next()) {
                buyComboBox.addItem(new productName(rs.getInt(1), rs.getString(2)));
                sellComboBox.addItem(new productName(rs.getInt(1), rs.getString(2)));
            }


        } catch (Exception c) {
            System.out.println(c);
        } finally {
            try {
                rs.close();
                ps.close();

            } catch (SQLException e) {
                System.out.println("prodName");
            }
        }
    }
    private void table_update_inventory() {
        int n;
        try {
            String sql = "select P_ID,NAME,MRP,S_QUANTITY from PRODUCT , SUPPLY_ORDER where PRODUCT.S_NAME=SUPPLY_ORDER.S_NAME ";
            ps = oc.conn.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsd = rs.getMetaData();
            n = rsd.getColumnCount();
            DefaultTableModel d = (DefaultTableModel) inventoryTable.getModel();
            d.setRowCount(0);
            while (rs.next()) {
                Vector v = new Vector();
                for (int i = 1; i <= n; i++) {
                    v.add(rs.getInt("P_ID"));
                    v.add(rs.getString("NAME"));
                    v.add(rs.getInt("MRP"));
                    v.add(rs.getInt("S_QUANTITY"));
                }
                d.addRow(v);
            }
        } catch (Exception e) {
            System.out.println("table_update_inventory");
        }
        finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException e) {
                System.out.println("table_update_inventory");
            }
        }
    }


}