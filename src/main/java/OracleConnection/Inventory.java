package OracleConnection;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

public class Inventory {
    private JFrame frame;
    private JPanel panelInventory, Panel;
    private Font f1, f2;
    private JButton inventoryDeleteButton;

    BackgroundColor backgroundColor;
    private JTable inventoryTable;
    private DefaultTableModel inventoryModel;
    private JScrollPane inventoryScrollPane;

    private String[] inventoryColumns = {"Id", "Name", "MRP", "Quantity"};

    OracleConnection oc = new OracleConnection();
    PreparedStatement ps;
    ResultSet rs;


    public Inventory(JFrame frame) {
        this.frame = frame;
        backgroundColor = new BackgroundColor(frame);

        initComponents(Panel);
    }

    public JPanel initComponents(final JPanel mainPanel) {

        this.Panel = mainPanel;

        panelInventory = backgroundColor.setGradientPanel();
        panelInventory.setLayout(null);


        f1 = new Font("Arial", Font.BOLD, 15);
        f2 = new Font("Arial", Font.BOLD, 11);

        inventoryDeleteButton = new JButton("Delete");
        inventoryDeleteButton.setBounds(600, 240, 90, 25);
        backgroundColor.setButtonColor(inventoryDeleteButton);
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

                        updateInventoryTable();


                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Selected Product Is Sold At Least Once \nIt Cannot Be Deleted ");
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
        backgroundColor.setTableDesign(inventoryTable, f1);
        inventoryScrollPane.setBounds(150, 350, 1000, 300);
        panelInventory.add(inventoryScrollPane);


        updateInventoryTable();

        return panelInventory;
    }

    public void updateInventoryTable() {
        int n;
        try {
            String sql = "select P_ID,NAME,max(MRP),sum(S_QUANTITY) from PRODUCT , SUPPLY_ORDER where PRODUCT.S_NAME=SUPPLY_ORDER.S_NAME having sum(S_QUANTITY)>0 group by  P_ID,NAME order by p_id";
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
                    v.add(rs.getInt(3));
                    v.add(rs.getInt(4));


                }
                d.addRow(v);
            }


        } catch (Exception e) {
            System.out.println(e + " table_update_inventory");
        }
    }


}