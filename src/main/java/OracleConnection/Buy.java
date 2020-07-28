package OracleConnection;

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
    private JTextField buyIdTextField, buySupplierTextField, buy_priceTextField, buyMRPTextField, buyQuantityTextField,
            buyDateTextField;//Buy
    private JButton buyAddNewButton, buySaveButton;//buy

    OracleConnection oc = new OracleConnection();
    PreparedStatement ps;
    ResultSet rs;
    Inventory inv ;
    Sell sell;

    public Buy(JFrame frame,Inventory i,Sell s) {
        this.frame = frame;
        inv=i;
        sell=s;
        inv.table_update_inventory();

    }

    public JPanel initComponents(final JPanel mainPanel) {

        //   final Inventory inventory = new Inventory(frame);

        this.Panel = mainPanel;

        panelBuy = new JPanel();
        panelBuy.setLayout(null);
        panelBuy.setBackground(new Color(0xD9B9F2));

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

                        OracleConnection oc2 = new OracleConnection();

                        String sql = "select S_NAME FROM SUPPLY_ORDER where S_NAME='" + buyComboBox.getSelectedItem().toString() + "'";
                        PreparedStatement ps1 = oc2.conn.prepareStatement(sql);
                        ResultSet rs1 = ps1.executeQuery();
                        if (rs1.next()) {
                            quantityAdd();
                        } else {

                            String buyDate1 = buyDateTextField.getText();
                            Date sqlBuyDate1 = Date.valueOf(buyDate1);


                            String sql_SUPPLY_ORDER = "insert into SUPPLY_ORDER (S_NAME, S_PRICE, S_QUANTITY, MRP, SUPPLIER, SUP_DATE) values(?, ?, ?, ?, ?, ?)";
                            String col[]={"S_ID"};
                            ps = oc.conn.prepareStatement(sql_SUPPLY_ORDER,col);
                            ps.setString(1, buyComboBox.getSelectedItem().toString());
                            ps.setInt(2, Integer.parseInt(buy_priceTextField.getText().trim()));
                            ps.setInt(3, Integer.parseInt(buyQuantityTextField.getText().trim()));
                            ps.setInt(4, Integer.parseInt(buyMRPTextField.getText().trim()));
                            ps.setString(5, buySupplierTextField.getText().trim());
                            ps.setDate(6, sqlBuyDate1);
                            ps.executeUpdate();

                            ResultSet rs=ps.getGeneratedKeys();
                            while (rs.next()){
                                //   System.out.println("id "+rs.getInt(1));
                                int idd=rs.getInt(1);
                                OracleConnection oc3 = new OracleConnection();

                                String sql3="update PRODUCT set S_ID=? where S_NAME=?";
                                PreparedStatement ps3 = oc3.conn.prepareStatement(sql3);
                                ps3.setInt(1, idd);
                                ps3.setString(2, buyComboBox.getSelectedItem().toString());

                                ps3.executeUpdate();
                            }

                            //  oc.conn.commit();
                            inv.table_update_inventory();
                            sell.prodName();

                        }
                        buy_priceTextField.setText("");
                        buyQuantityTextField.setText("");
                        buyMRPTextField.setText("");
                        buySupplierTextField.setText("");
                        buyDateTextField.setText("");
                        buyComboBox.requestFocus();


                    } catch (Exception e2) {
                        System.out.println(e2);
                    }


                }
            });


        }


        prodName();
        inv.table_update_inventory();


        return panelBuy;
    }

    public void prodName() {
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

    private void quantityAdd() {
        try {
            int quantity = Integer.parseInt(buyQuantityTextField.getText());
            String name = buyComboBox.getSelectedItem().toString();
            int mrp = Integer.parseInt(buyMRPTextField.getText());
            String d = buyDateTextField.getText();
            Date date = Date.valueOf(d);

            String sql = "UPDATE SUPPLY_ORDER SET S_QUANTITY = S_QUANTITY +?, MRP=? WHERE S_NAME = ? and S_QUANTITY > 0";
            PreparedStatement ps1 = oc.conn.prepareStatement(sql);
            ps1.setInt(1, quantity);
            ps1.setInt(2, mrp);
            ps1.setString(3, name);

            ps1.executeUpdate();

        } catch (Exception e) {
            System.out.println(e + " quantityAdd");
        }
    }


}
