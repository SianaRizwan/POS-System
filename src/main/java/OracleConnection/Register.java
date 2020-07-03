package OracleConnection;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;

public class Register {

    private JFrame frame;
    private JPanel registerPanel;
    private Font font1, font2, font3, f1;
    private JLabel signupLabel;
    private JLabel userLabel;
    private JTextField userTextField;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel emailLabel;
    private JTextField emailTextField;
    private JLabel passwordLabel;
    private JLabel retypePasswordLabel;
    private JPasswordField retypePasswordField;
    private JButton registerButton, backButton;
    private JLabel pokpokLabel;
    private JPasswordField passwordField;

    Register(JFrame frame) {
        this.frame = frame;
        initComponents();
    }

    private void initComponents() {


        registerPanel = new JPanel();
        registerPanel.setLayout(null);
        registerPanel.setBackground(new Color(0xD9B9F2));

        font1 = new Font("Arial", Font.BOLD, 15);
        f1 = new Font("Arial", Font.BOLD, 15);
        font2 = new Font("Arial", Font.BOLD, 22);
        font3 = new Font("Arial", Font.PLAIN, 12);

        signupLabel = new JLabel();
        signupLabel.setText("SIGN UP");
        signupLabel.setBounds(670, 170, 100, 50);
        signupLabel.setFont(font2);
        registerPanel.add(signupLabel);

        pokpokLabel = new JLabel();
        pokpokLabel.setText("Create Your Account. It's Free and Only Take a Minute ");
        pokpokLabel.setBounds(570, 200, 300, 50);
        pokpokLabel.setFont(font3);
        registerPanel.add(pokpokLabel);

        userLabel = new JLabel();
        userLabel.setText("USER ID : ");
        userLabel.setBounds(500, 250, 150, 50);
        userLabel.setFont(font1);
        registerPanel.add(userLabel);

        userTextField = new JTextField();
        userTextField.setBounds(675, 260, 200, 30);
        userTextField.setFont(font1);
        registerPanel.add(userTextField);

        nameLabel = new JLabel();
        nameLabel.setText("NAME : ");
        nameLabel.setBounds(500, 290, 150, 50);
        nameLabel.setFont(font1);
        registerPanel.add(nameLabel);

        nameTextField = new JTextField();
        nameTextField.setBounds(675, 300, 200, 30);
        nameTextField.setFont(font1);
        registerPanel.add(nameTextField);

        emailLabel = new JLabel();
        emailLabel.setText("EMAIL : ");
        emailLabel.setBounds(500, 330, 150, 50);
        emailLabel.setFont(font1);
        registerPanel.add(emailLabel);

        emailTextField = new JTextField();
        emailTextField.setBounds(675, 340, 200, 30);
        emailTextField.setFont(font1);
        registerPanel.add(emailTextField);

        passwordLabel = new JLabel();
        passwordLabel.setText("PASSWORD : ");
        passwordLabel.setBounds(500, 370, 150, 50);
        passwordLabel.setFont(font1);
        registerPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(675, 380, 200, 30);
        passwordField.setFont(font1);
        registerPanel.add(passwordField);

        retypePasswordLabel = new JLabel();
        retypePasswordLabel.setText("RETYPE PASSWORD : ");
        retypePasswordLabel.setBounds(500, 410, 200, 50);
        retypePasswordLabel.setFont(font1);
        registerPanel.add(retypePasswordLabel);

        retypePasswordField = new JPasswordField();
        retypePasswordField.setBounds(675, 420, 200, 30);
        retypePasswordField.setFont(font1);
        registerPanel.add(retypePasswordField);

        backButton = new JButton("Back");
        backButton.setBounds(580, 490, 100, 20);
        backButton.setBackground(new Color(0x7E0AB5));
        backButton.setForeground(new Color(0xFEFEFE));
        backButton.setFont(f1);
        registerPanel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginPage(frame);
                registerPanel.setVisible(false);
            }
        });

        registerButton = new JButton("Register");
        registerButton.setBounds(700, 490, 100, 20);
        registerButton.setFont(f1);
        registerButton.setBackground(new Color(0x7E0AB5));
        registerButton.setForeground(new Color(0xFEFEFE));
        registerPanel.add(registerButton);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Emailvalidator emailValidator = new Emailvalidator();
                    if (emailValidator.validate(emailTextField.getText().trim())) {
                        if ((passwordField.getText()).equals(retypePasswordField.getText())) {

                            OracleConnection oc = new OracleConnection();
                            String sql = "insert into USERS (U_ID, NAME, PASSWORD, EMAIL) values(?, ?, ?, ?)";
                            PreparedStatement ps = oc.conn.prepareStatement(sql);
                            ps.setInt(1, Integer.parseInt(userTextField.getText().trim()));
                            ps.setString(2, nameTextField.getText().trim());
                            ps.setString(3, passwordField.getText());
                            ps.setString(4, emailTextField.getText().trim());
                            int x = ps.executeUpdate();

                            OracleConnection oc1 = new OracleConnection();
                            String sql1 = "insert into SALES (U_ID) values(?)";
                            PreparedStatement ps1 = oc1.conn.prepareStatement(sql1);
                            ps1.setInt(1, Integer.parseInt(userTextField.getText().trim()));
                            ps1.executeUpdate();


                            if (x > 0) {
                                new Dashboard(frame);
                                registerPanel.setVisible(false);
                            } else {
                                JOptionPane.showMessageDialog(frame, "insert failed");
                            }
                        } else {
                            JOptionPane.showMessageDialog(frame, "Both password does not match");

                        }

                    } else {
                        JOptionPane.showMessageDialog(frame, "invalid email id");
                    }

                } catch (Exception e1) {
                    System.out.println(e1);
                }



            }
        });

        frame.add(registerPanel);
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
