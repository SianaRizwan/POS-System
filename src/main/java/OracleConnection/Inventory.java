package OracleConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class Inventory {
    private JFrame frame;
    private JPanel panelInventory,Panel;
    private Font f1, f2;
    private JButton  inventoryDeleteButton;


    private JTable inventoryTable;
    private DefaultTableModel inventoryModel;
    private JScrollPane inventoryScrollPane;

    private String[] inventoryColumns = {"Id", "Name", "MRP", "Quantity"};
    private String[] inventoryRows = new String[4];

    OracleConnection oc = new OracleConnection();
    PreparedStatement ps;
    ResultSet rs;
    public Inventory(JFrame frame) {
        this.frame=frame;
        initComponents(Panel);
        table_update_inventory();
    }

    public JPanel initComponents(final JPanel mainPanel){

        this.Panel=mainPanel;

        panelInventory = new JPanel();
        panelInventory.setLayout(null);
        panelInventory.setBackground(new Color(0xD9B9F2));



        f1 = new Font("Arial", Font.BOLD, 15);
        f2 = new Font("Arial", Font.BOLD, 11);

        inventoryDeleteButton = new JButton("Delete");
        inventoryDeleteButton.setBounds(600, 240, 90, 25);
        inventoryDeleteButton.setBackground(new Color(0x7E0AB5));
        inventoryDeleteButton.setForeground(new Color(0xFEFEFE));
        inventoryDeleteButton.setFont(f2);
        panelInventory.add(inventoryDeleteButton);
        inventoryDeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel d = (DefaultTableModel) inventoryTable.getModel();
                int selectRow = inventoryTable.getSelectedRow();
                String name = d.getValueAt(selectRow, 1).toString();
                int warningMsg = JOptionPane.showConfirmDialog(frame, "Do you want to delete the product?", "DELETE", JOptionPane.YES_NO_OPTION);

                if (warningMsg == JOptionPane.YES_OPTION) {
                    try {
                        String sql1 = "delete from PRODUCT where NAME=?";

                        OracleConnection oc1 = new OracleConnection();
                        PreparedStatement ps1 = oc1.conn.prepareStatement(sql1);

                        ps1.setString(1, name);
                        ps1.executeUpdate();

                        OracleConnection oc2 = new OracleConnection();
                        String sql2 = "delete from SUPPLY_ORDER where S_NAME=?";
                        PreparedStatement ps2 = oc2.conn.prepareStatement(sql2);

                        ps2.setString(1, name);
                        ps2.executeUpdate();

                        table_update_inventory();

                    } catch (Exception ex) {
                        System.out.println(ex + " inventory delete");
                    }
                }

            }
        });



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


        table_update_inventory();

        return panelInventory;
    }

    public void table_update_inventory() {
        int n,t;
        try {
            String sql = "select P_ID,NAME,MRP,S_QUANTITY from PRODUCT , SUPPLY_ORDER where PRODUCT.S_NAME=SUPPLY_ORDER.S_NAME ";
            //String sql3 = "SELECT SO.S_QUANTITY-SD.P_QUANTITY AS DIFF \n" +
                  //  "   FROM SALES_DETAILS SD, SUPPLY_ORDER SO, PRODUCT PROD\n" +
                   // "    WHERE SD.P_ID = PROD.P_ID AND PROD.S_ID = SO.S_ID;";
            ps = oc.conn.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsd = rs.getMetaData();
            n = rsd.getColumnCount();

            //PreparedStatement ps3 = oc.conn.prepareStatement(sql3);
            //ResultSet rs3 = ps3.executeQuery();
            //ResultSetMetaData rsd3 = rs3.getMetaData();
            //t=rsd3.getColumnCount();

            DefaultTableModel d = (DefaultTableModel) inventoryTable.getModel();
            d.setRowCount(0);

            while (rs.next()) {
                Vector v = new Vector();

                for (int i = 1; i <= n; i++) {

                    v.add(rs.getInt(1));
                    v.add(rs.getString(2));
                    v.add(rs.getInt(3));
                    v.add(rs.getInt(4));


                }
                d.addRow(v);
            }


        } catch (Exception e) {
            System.out.println(e+ " table_update_inventory");
        }
        finally {
            try {
                rs.close();
                ps.close();

            } catch (SQLException e) {
                System.out.println(e+" 1table_update_inventory");
            }
        }
    }
}