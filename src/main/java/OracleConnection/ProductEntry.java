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

    ProductEntry(JFrame frame) {
        this.frame = frame;
        initComponents();
    }

    private void initComponents() {

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.lightGray);

        f1 = new Font("Arial", Font.BOLD, 15);
        f2 = new Font("Arial", Font.BOLD, 11);

        productId = new JLabel("Product Id : ");
        productId.setBounds(450, 200, 150, 50);
        productId.setFont(f1);
        panel.add(productId);

        productName = new JLabel("Product Name : ");
        productName.setBounds(450, 250, 150, 50);
        productName.setFont(f1);
        panel.add(productName);



        tfProductId = new JTextField();
        tfProductId.setBounds(600, 210, 250, 30);
        tfProductId.setFont(f1);
        panel.add(tfProductId);

        tfProductName = new JTextField();
        tfProductName.setBounds(600, 260, 250, 30);
        tfProductName.setFont(f1);
        panel.add(tfProductName);



        backButton = new JButton("Back");
        backButton.setBounds(720, 400, 70, 25);
        backButton.setBackground(Color.cyan);
        backButton.setFont(f2);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Dashboard(frame);
                panel.setVisible(false);
            }
        });
        panel.add(backButton);

        addButton = new JButton("Save");
        addButton.setBounds(620, 400, 70, 25);
        addButton.setBackground(Color.cyan);
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
                    ps1.setString(3,tfProductName.getText());
                    int x = ps1.executeUpdate();

                    tfProductId.setText("");
                    tfProductName.setText("");

                    if (x < 0) {

                        JOptionPane.showMessageDialog(frame, "input valid info");
                    }
                } catch (Exception d) {
                    System.out.println(d);
                }
            }
        });
        panel.add(addButton);

        frame.add(panel);
        frame.setAlwaysOnTop(true);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Inventory Management");

        Toolkit toolkit = Toolkit.getDefaultToolkit();

        int xsize = (int) toolkit.getScreenSize().getWidth();
        int ysize = (int) toolkit.getScreenSize().getHeight();
        frame.setSize(xsize, ysize);

    }

}
