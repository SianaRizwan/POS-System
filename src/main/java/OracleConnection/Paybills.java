package OracleConnection;


import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;

public class Paybills {

    private JFrame frame;
    private Font f1, f2;
    private JPanel panelPayBills, panel;

    private JComboBox expenseComboBox;
    private JLabel expId, purpose, amount, date, description;//expense Paybills
    private JTextField tfExpId, tfAmount, tfDescription;//expense Paybills
    private JButton expSaveButton, expDelButton, expAddButton;
    private static String[] purposes = {"Select an option", "Employee Salary", "Rent", "Utility Bills", "Others"};//expense list.

    private JDateChooser dateChooser;

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
        panelPayBills.setBackground(new Color(195, 197, 97));

        expenseComboBox = new JComboBox(purposes);
        expenseComboBox.setBounds(550, 210, 200, 30);
        expenseComboBox.setEditable(false);
        panelPayBills.add(expenseComboBox);

        expenseComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                try {
                    OracleConnection oc = new OracleConnection();
                    Statement st = oc.conn.createStatement();
                    String purposeType = expenseComboBox.getSelectedItem().toString();

                    if (purposeType == "Employee Salary") {
                        String sql = "SELECT SUM(AMOUNT) AS TOTAL FROM SALARY, USERS WHERE SALARY.SAL_ID = USERS.SAL_ID";
                        ResultSet rs = st.executeQuery(sql);
                        while (rs.next()) {
                            tfAmount.setText(String.valueOf(rs.getInt("TOTAL")));
                        }
                    }
                    else{
                        tfAmount.setText("");
                    }

                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });

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


        dateChooser = new JDateChooser();
        dateChooser.setBounds(550, 310, 200, 30); // Modify depending on your preference
        panelPayBills.add(dateChooser);

        tfDescription = new JTextField();
        tfDescription.setBounds(550, 360, 200, 30);
        tfDescription.setFont(f1);
        panelPayBills.add(tfDescription);

        tfExpId = new JTextField();
        tfExpId.setBounds(550, 410, 200, 30);
        tfExpId.setFont(f1);
        panelPayBills.add(tfExpId);

        expTable = new JTable();
        expModel = new DefaultTableModel();
        expScrollPane = new JScrollPane(expTable);
        expModel.setColumnIdentifiers(expenseColumns);
        expTable.setModel(expModel);
        expTable.setFont(f1);
        expTable.setBackground(Color.WHITE);
        expTable.setSelectionBackground(Color.GRAY);
        expTable.setAutoCreateRowSorter(true);

        expTable.setRowHeight(30);

        expScrollPane.setBounds(150, 610, 1000, 300);
        panelPayBills.add(expScrollPane);

        expAddButton = new JButton("Add");
        expAddButton.setFont(f2);
        expAddButton.setForeground(Color.BLACK);
        expAddButton.setBackground(Color.cyan);
        expAddButton.setBounds(450, 500, 100, 30);
        panelPayBills.add(expAddButton);
        expAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Date sqlExpDate = convertJavaDateToSqlDate(dateChooser.getDate());
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


                    expenseTableAdd();
                    tfExpId.setText("");
                    tfDescription.setText("");
                    tfAmount.setText("");

                    expenseComboBox.requestFocus();

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
        expSaveButton.setForeground(Color.BLACK);

        expSaveButton.setBackground(Color.cyan);
        expSaveButton.setBounds(600, 500, 100, 30);
        panelPayBills.add(expSaveButton);
        expSaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Expenses Saved Successfully");
                expModel.setRowCount(0);
            }
        });

        expDelButton = new JButton("Delete");
        expDelButton.setBounds(750, 500, 100, 30);
        expDelButton.setBackground(Color.cyan);
        expDelButton.setForeground(Color.BLACK);
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


        return panelPayBills;

    }

    private void expenseTableAdd() {
        //date
        Date sqlExpDate = convertJavaDateToSqlDate(dateChooser.getDate());

        DefaultTableModel d = (DefaultTableModel) expTable.getModel();
        d.addRow(new Object[]{expenseComboBox.getSelectedItem().toString(), Integer.parseInt(tfExpId.getText()),
                Integer.parseInt(tfAmount.getText()), sqlExpDate, tfDescription.getText()});
    }


    private java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

}