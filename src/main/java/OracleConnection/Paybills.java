package OracleConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Paybills {

    private JFrame frame;
    private Font f1, f2;
    private JPanel panelPayBills, panel;

    private JComboBox expenseComboBox;
    private JLabel expId, purpose, amount, date, description;//expense Paybills
    private JTextField tfExpId, tfAmount, tfDate, tfDescription;//expense Paybills
    private JButton expSaveButton, expDelButton, expAddButton;
    private static String[] purposes = {"Select an option", "Employee Salary", "Rent", "Utility Bills", "Others"};//expense list.

    private JTable expTable;
    private DefaultTableModel expModel;
    private JScrollPane expScrollPane;

    private String[] expenseColumns = {"Purpose", "Id", "Amount", "Date", "Description"};
    private String[] expenseRows = new String[5];

    OracleConnection oc = new OracleConnection();
    PreparedStatement ps;
    ResultSet rs;

    Paybills(JFrame frame) {
        this.frame = frame;
    }

    public JPanel initComponents(JPanel mainPanel) {

        this.panel = mainPanel;

        panelPayBills = new JPanel();
        panelPayBills.setLayout(null);
        panelPayBills.setBackground(new Color(0xD9B9F2));

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

        expAddButton = new JButton("Add");
        expAddButton.setFont(f2);
        expAddButton.setForeground(new Color(0xFEFEFE));
        expAddButton.setBackground(new Color(0x7E0AB5));
        expAddButton.setBounds(450, 500, 100, 30);
        panelPayBills.add(expAddButton);
        expAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //date
                    String expDate = tfDate.getText();
                    Date sqlExpDate = Date.valueOf(expDate);
                    // Date selectedDate = (Date) datePicker.getModel().getValue();

                    //purpose
                    String purpose = expenseComboBox.getSelectedItem().toString();

                    String sqlExpense = "insert into EXPENSES (E_ID, PURPOSE, SUP_DATE, DESCRIPTION, AMOUNT) values(?, ?, ?, ?, ?)";
                    ps = oc.conn.prepareStatement(sqlExpense);
                    ps.setInt(1, Integer.parseInt(tfExpId.getText()));
                    ps.setString(2, purpose);
                    ps.setDate(3, sqlExpDate);
                    ps.setString(4, tfDescription.getText());
                    ps.setInt(5, Integer.parseInt(tfAmount.getText()));
                    ps.executeUpdate();
                  /*  {
                        //sal_id(pk) empty,so it wont work
                        OracleConnection oc1 = new OracleConnection();
                        String sql1 = "insert into SALARY (DESIGNATION, AMOUNT) values(?, ?)";
                        PreparedStatement ps1 = oc1.conn.prepareStatement(sql1);
                        ps1.setString(1, tfDescription.getText());
                        ps1.setInt(2, Integer.parseInt(tfAmount.getText()));
                        ps1.executeUpdate();
                    } */

                    expenseTableAdd();
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


        expSaveButton = new JButton("Save");
        expSaveButton.setFont(f2);
        expSaveButton.setForeground(new Color(0xFEFEFE));

        expSaveButton.setBackground(new Color(0x7E0AB5));
        expSaveButton.setBounds(600, 500, 100, 30);
        panelPayBills.add(expSaveButton);
        expSaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Paybills(frame);
                panelPayBills.setVisible(false);
            }
        });

        expDelButton = new JButton("Delete");
        expDelButton.setBounds(750, 500, 100, 30);
        expDelButton.setBackground(new Color(0x7E0AB5));
        expDelButton.setForeground(new Color(0xFEFEFE));
        expDelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel d = (DefaultTableModel) expTable.getModel();
                int selectRow = expTable.getSelectedRow();
                int id = Integer.parseInt(d.getValueAt(selectRow, 1).toString());
                int warningMsg = JOptionPane.showConfirmDialog(frame, "Do you want to delete the selected item?", "DELETE", JOptionPane.YES_NO_OPTION);

                if (warningMsg == JOptionPane.YES_OPTION) {
                    try {
                        String sql1 = "delete from EXPENSES where E_ID=?";
                        OracleConnection oc1 = new OracleConnection();
                        PreparedStatement ps1 = oc.conn.prepareStatement(sql1);

                        ps1.setInt(1, id);
                        ps1.executeUpdate();

                        d.removeRow(selectRow);

                    } catch (Exception ex) {
                        System.out.println(ex + " expense delete");
                    }
                }

            }
        });
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

        expScrollPane.setBounds(150, 610, 1000, 300);
        panelPayBills.add(expScrollPane);

        return panelPayBills;

    }

    private void expenseTableAdd() {
        //date
        String expDate = tfDate.getText();
        Date sqlExpDate = Date.valueOf(expDate);

        DefaultTableModel d = (DefaultTableModel) expTable.getModel();
        d.addRow(new Object[]{expenseComboBox.getSelectedItem().toString(), Integer.parseInt(tfExpId.getText()),
                Integer.parseInt(tfAmount.getText()), sqlExpDate, tfDescription.getText()});
    }
}
