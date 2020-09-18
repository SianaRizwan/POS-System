package OracleConnection;

/// adding new category

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;

public class ProductEntry {

    private JFrame frame;
    private JPanel panel;
    private JLabel productId, productName;
    private JTextField tfProductId, tfProductName;
    private Font f1, f2;
    private JButton addButton, backButton;
    BackgroundColor backgroundColor;

    ProductEntry(JFrame frame) {
        this.frame = frame;
        backgroundColor = new BackgroundColor(frame);
        initComponents();
    }

    private void initComponents() {

        panel = backgroundColor.setGradientPanel();
        panel.setLayout(null);

        f1 = new Font("Arial", Font.BOLD, 15);
        f2 = new Font("Arial", Font.BOLD, 11);

        productId = new JLabel("Product Id : ");
        labelPanelAdd(productId, 200);

        productName = new JLabel("Product Name : ");
        labelPanelAdd(productName, 250);


        tfProductId = new JTextField();
        textFieldPanelAdd(tfProductId, 210);

        tfProductName = new JTextField();
        textFieldPanelAdd(tfProductName, 260);


        backButton = new JButton("Back");
        backButton.setBounds(720, 400, 70, 25);
        backgroundColor.setButtonColor(backButton);
        backButton.setFont(f2);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dashboard dashboard = new Dashboard(frame);
                dashboard.tabbedPane.setSelectedIndex(1);
                panel.setVisible(false);
            }
        });
        panel.add(backButton);

        addButton = new JButton("Save");
        addButton.setBounds(620, 400, 70, 25);
        backgroundColor.setButtonColor(addButton);
        addButton.setFont(f2);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    OracleConnection oc = new OracleConnection();

                    String sql1 = "insert into PRODUCT (P_ID, NAME, S_NAME) values(?, ?, ?)";
                    PreparedStatement ps1 = oc.conn.prepareStatement(sql1);

                    ps1.setInt(1, Integer.parseInt(tfProductId.getText()));
                    ps1.setString(2, tfProductName.getText());
                    ps1.setString(3, tfProductName.getText());
                    ps1.executeUpdate();

                    resetTextFields();

                } catch (Exception d) {
                    JOptionPane.showMessageDialog(frame, "Product ID Already Exists");
                    System.out.println(d);
                }
            }
        });
        panel.add(addButton);

        frame.add(panel);
        backgroundColor.setScreenSize(frame);

    }

    private void resetTextFields() {
        tfProductId.setText("");
        tfProductName.setText("");
        tfProductId.requestFocus();
    }

    private void textFieldPanelAdd(JTextField textField, int yCoordinate) {
        textField.setBounds(600, yCoordinate, 250, 30);
        textField.setFont(f1);
        panel.add(textField);
    }

    private void labelPanelAdd(JLabel label, int yCoordinate) {
        label.setBounds(450, yCoordinate, 150, 50);
        label.setFont(f1);
        panel.add(label);
    }

}