package OracleConnection;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;

public class Buy {

    private JFrame frame;
    private Font f1, f2;
    private JPanel panelBuy, Panel;
    private JComboBox buyComboBox;//buy
    private JLabel buyNameLabel, buyIdLabel, buySupplierLabel, buy_priceLabel, buyMRPLabel, buyQuantityLabel, buyDateLabel;
    private JTextField buyIdTextField, buySupplierTextField, buy_priceTextField, buyMRPTextField, buyQuantityTextField; //buy
    private JButton buyAddNewButton, buySaveButton;//buy
    private JDateChooser dateChooser;

    OracleConnection oc = new OracleConnection();
    PreparedStatement ps;
    ResultSet rs;
    Inventory inv;
    Sell sell;
    BackgroundColor backgroundColor;

    public Buy(JFrame frame, Inventory i, Sell s) {
        this.frame = frame;
        inv = i;
        sell = s;
        inv.updateInventoryTable();
        backgroundColor = new BackgroundColor(frame);
    }

    public JPanel initComponents(final JPanel mainPanel) {


        this.Panel = mainPanel;

        panelBuy = backgroundColor.setGradientPanel();
        panelBuy.setLayout(null);

        f1 = new Font("Arial", Font.BOLD, 15);
        f2 = new Font("Arial", Font.BOLD, 11);

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

           /*
            buyDateTextField = new JTextField();
            buyDateTextField.setBounds(600, 410, 200, 30);
            buyDateTextField.setFont(f1);
            panelBuy.add(buyDateTextField);
            */

            dateChooser = new JDateChooser();
            dateChooser.setBounds(600, 410, 200, 30);
            panelBuy.add(dateChooser);

            buyAddNewButton = new JButton("Add New");
            buyAddNewButton.setBounds(850, 110, 100, 30);
            buyAddNewButton.setFont(f2);
            backgroundColor.setButtonColor(buyAddNewButton);
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
            backgroundColor.setButtonColor(buySaveButton);

            panelBuy.add(buySaveButton);
            buySaveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {


                    try {

                        Date sqlBuyDate1 = convertJavaDateToSqlDate(dateChooser.getDate());

                        String sql_SUPPLY_ORDER = "insert into SUPPLY_ORDER (S_NAME, S_PRICE, S_QUANTITY, MRP, SUPPLIER, SUP_DATE,initial_qty) values(?, ?, ?, ?, ?, ?,?)";
                        String col[] = {"S_ID"};
                        ps = oc.conn.prepareStatement(sql_SUPPLY_ORDER, col);
                        ps.setString(1, buyComboBox.getSelectedItem().toString());
                        ps.setInt(2, Integer.parseInt(buy_priceTextField.getText().trim()));
                        ps.setInt(3, Integer.parseInt(buyQuantityTextField.getText().trim()));
                        ps.setInt(4, Integer.parseInt(buyMRPTextField.getText().trim()));
                        ps.setString(5, buySupplierTextField.getText().trim());
                        ps.setDate(6, sqlBuyDate1);
                        ps.setInt(7, Integer.parseInt(buyQuantityTextField.getText().trim()));
                        ps.executeQuery();

                        refreshTextFields();

                        ResultSet rs = ps.getGeneratedKeys();

                        while (rs.next()) {
                            System.out.println("id " + rs.getInt(1));
                            int idd = rs.getInt(1);
                            OracleConnection oc3 = new OracleConnection();

                            String sql3 = "update PRODUCT set S_ID=? where S_NAME=?";
                            PreparedStatement ps3 = oc3.conn.prepareStatement(sql3);
                            ps3.setInt(1, idd);
                            ps3.setString(2, buyComboBox.getSelectedItem().toString());

                            ps3.executeUpdate();
                        }


                    } catch (Exception e2) {
                        System.out.println(e2);
                    }


                }
            });


        }


        addProductNameToJComboBox();

        return panelBuy;
    }

    private void refreshTextFields() {
        buy_priceTextField.setText("");
        buyQuantityTextField.setText("");
        buyMRPTextField.setText("");
        buySupplierTextField.setText("");
        buyComboBox.requestFocus();
    }


    public void addProductNameToJComboBox() {
        try {
            String sql = "select * from PRODUCT";
            ps = oc.conn.prepareStatement(sql);
            rs = ps.executeQuery();
            buyComboBox.removeAllItems();
            while (rs.next()) {
                buyComboBox.addItem(new Buy.productName(rs.getInt(1), rs.getString(2)));
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


    private java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

}