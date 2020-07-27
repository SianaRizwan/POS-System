package OracleConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;

public class AdminInventory {
    private JFrame frame;
    private JPanel panelInventory,Panel;
    private Font f1, f2;
    private JButton  inventoryDeleteButton;


    private JTable inventoryTable;
    private DefaultTableModel inventoryModel;
    private JScrollPane inventoryScrollPane;

    private String[] inventoryColumns = {"P_Id", "P_Name", "Unit_Price","MRP", "Quantity"};
    private String[] inventoryRows = new String[5];

    //OracleConnection oc = new OracleConnection();
    //PreparedStatement ps;
    //ResultSet rs;
    public AdminInventory(JFrame frame) {
        this.frame=frame;
        initComponents(Panel);
        //table_update_inventory();
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

                        //table_update_inventory();

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
        inventoryTable.getPreferredScrollableViewportSize();
        inventoryTable.setBackground(Color.WHITE);
        inventoryTable.setSelectionBackground(Color.GRAY);
        inventoryTable.setRowHeight(30);

        //Shows the cell when clicked in the table
        //Ak korte pari product e click korle product details dekhabe including radio button
        //But onek product thakle kotogula radiobutton hobe vaab
        //Tai vabtesilam table ei show koruk (shob product sell hoy gele quantity column e sold out leha dehabe)
        // product gular details table e sorted by date thakbe
        //Delete button o thakbe jodi admin kono prod delete korte chay
        //eta just akta template . modify by yourself then share :D
        inventoryTable.addMouseListener(new java.awt.event.MouseAdapter()

                                        {

                                            public void mouseClicked(java.awt.event.MouseEvent e)

                                            {

                                                int row=inventoryTable.rowAtPoint(e.getPoint());

                                                int col= inventoryTable.columnAtPoint(e.getPoint());

                                                JOptionPane.showMessageDialog(null,inventoryTable.getValueAt(row,col).toString());

                                                System.out.println(inventoryTable.getValueAt(row,col).toString());

                                            }

                                        }

        );

        inventoryScrollPane.setBounds(150,350,1000,300);
        panelInventory.add(inventoryScrollPane);


        //table_update_inventory();

        return panelInventory;
    }


}