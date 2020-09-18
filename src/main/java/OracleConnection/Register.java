package OracleConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Register {

    private JFrame frame;
    private JPanel registerPanel;
    private Font font1, font2, font3;
    private JLabel signupLabel;
    private JLabel userLabel;
    private JTextField userTextField;
    private JLabel userNameLabel;
    private JTextField userNameTextField;
    private JLabel emailLabel;
    private JTextField emailTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JLabel retypePasswordLabel;
    private JPasswordField retypePasswordField;
    private JButton registerButton;
    private JLabel welcomeMessageLabel, designation;
    private JComboBox salaryComboBox;
    int count = 0;

    OracleConnection oc = new OracleConnection();
    private JLabel designationId;
    private JLabel designationName;
    private JTextField tfDesignationId;
    private JTextField tfDesignationName;
    private JLabel companyNameLabel,companyAddressLabel,contactNumberLabel;
    private JTextField companyNameTextField,contactNumberTextField;
    private JTextArea companyAddressTextArea;
    BackgroundColor backgroundColor;

    Register(JFrame frame) {
        this.frame = frame;
        backgroundColor =new BackgroundColor(frame);
    }


    public JPanel initComponents() {

        registerPanel = backgroundColor.setGradientPanel();
        registerPanel.setLayout(null);

        font1 = new Font("Arial", Font.BOLD, 15);
        font2 = new Font("Arial", Font.BOLD, 25);
        font3 = new Font("Arial", Font.PLAIN, 15);

        signupLabel = new JLabel();
        signupLabel.setText("SIGN UP");
        signupLabel.setBounds(630, 150, 150, 50);
        labelPanelAdd(signupLabel, font2);

        welcomeMessageLabel = new JLabel();
        labelPanelAdd(welcomeMessageLabel, font3);

        userLabel = new JLabel();
        userLabel.setText("USER ID : ");
        userLabel.setBounds(500, 250, 150, 50);
        labelPanelAdd(userLabel, font1);

        userTextField = new JTextField();
        userTextField.setBounds(675, 260, 200, 30);
        textFieldPanelAdd(userTextField);

        userNameLabel = new JLabel();
        userNameLabel.setText("USER NAME : ");
        userNameLabel.setBounds(500, 290, 150, 50);
        labelPanelAdd(userNameLabel, font1);

        userNameTextField = new JTextField();
        userNameTextField.setBounds(675, 300, 200, 30);
        textFieldPanelAdd(userNameTextField);

        emailLabel = new JLabel();
        emailLabel.setText("EMAIL : ");
        emailLabel.setBounds(500, 330, 150, 50);
        labelPanelAdd(emailLabel, font1);

        emailTextField = new JTextField();
        emailTextField.setBounds(675, 340, 200, 30);
        textFieldPanelAdd(emailTextField);

        passwordLabel = new JLabel();
        passwordLabel.setText("PASSWORD : ");
        passwordLabel.setBounds(500, 370, 150, 50);
        labelPanelAdd(passwordLabel, font1);

        passwordField = new JPasswordField();
        passwordField.setBounds(675, 380, 200, 30);
        textFieldPanelAdd(passwordField);

        retypePasswordLabel = new JLabel();
        retypePasswordLabel.setText("RETYPE PASSWORD : ");
        retypePasswordLabel.setBounds(500, 410, 200, 50);
        labelPanelAdd(retypePasswordLabel, font1);

        retypePasswordField = new JPasswordField();
        retypePasswordField.setBounds(675, 420, 200, 30);
        textFieldPanelAdd(retypePasswordField);

        designation = new JLabel("DESIGNATION : ");
        designation.setBounds(500, 450, 150, 50);
        labelPanelAdd(designation, font1);

        salaryComboBox = new JComboBox();
        salaryComboBox.setBounds(675, 460, 200, 30);
        registerPanel.add(salaryComboBox);
        salaryComboBox.setEditable(false);

        designationId = new JLabel("DESIGNATION ID: ");
        designationId.setBounds(500, 600, 150, 50);
        labelPanelAdd(designationId, font1);

        designationName = new JLabel("DESIGNATION : ");
        designationName.setBounds(500, 640, 150, 50);
        labelPanelAdd(designationName, font1);

        tfDesignationId = new JTextField();
        tfDesignationId.setBounds(675, 610, 200, 30);
        textFieldPanelAdd(tfDesignationId);

        companyNameLabel = new JLabel("COMPANY NAME : ");
        companyNameLabel.setBounds(500, 450, 150, 50);
        labelPanelAdd(companyNameLabel, font1);

        companyNameTextField = new JTextField();
        companyNameTextField.setBounds(675, 460, 200, 30);
        textFieldPanelAdd(companyNameTextField);


        companyAddressLabel = new JLabel("ADDRESS : ");
        companyAddressLabel.setBounds(500, 490, 150, 50);
        labelPanelAdd(companyAddressLabel, font1);

        companyAddressTextArea = new JTextArea();
        companyAddressTextArea.setBounds(675, 500, 200, 60);
        companyAddressTextArea.setFont(font1);
        registerPanel.add(companyAddressTextArea);

        contactNumberLabel = new JLabel("CONTACT NO. : ");
        contactNumberLabel.setBounds(500, 560, 150, 50);
        labelPanelAdd(contactNumberLabel, font1);

        contactNumberTextField = new JTextField();
        contactNumberTextField.setBounds(675, 570, 200, 30);
        textFieldPanelAdd(contactNumberTextField);


        tfDesignationName = new JTextField();
        tfDesignationName.setBounds(675, 650, 200, 30);
        textFieldPanelAdd(tfDesignationName);

        registerButton = new JButton("Register");
        registerButton.setFont(font1);
        backgroundColor.setButtonColor(registerButton);
        registerPanel.add(registerButton);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Emailvalidator emailValidator = new Emailvalidator();
                    if (emailValidator.validate(emailTextField.getText().trim())) {
                        if ((passwordField.getText()).equals(retypePasswordField.getText())) {

                            String sql = "insert into USERS (U_ID, NAME, PASSWORD, EMAIL, SAL_ID) values(?, ?, ?, ?, ?)";
                            PreparedStatement ps = oc.conn.prepareStatement(sql);
                            ps.setInt(1, Integer.parseInt(userTextField.getText().trim()));
                            ps.setString(2, userNameTextField.getText().trim());
                            ps.setString(3, passwordField.getText());
                            ps.setString(4, emailTextField.getText().trim());
                            if (count == 0) {
                                String sql1 = "insert into salary (sal_id,designation) values(?,?)";
                                OracleConnection oc1 = new OracleConnection();
                                PreparedStatement ps1 = oc1.conn.prepareStatement(sql1);
                                ps1.setInt(1, Integer.parseInt(tfDesignationId.getText().trim()));
                                ps1.setString(2, tfDesignationName.getText().trim());
                                int y = ps1.executeUpdate();
                                if (y > 0) {
                                    new LoginPage(frame);
                                    registerPanel.setVisible(false);
                                }

                                String sql2="insert into company_info(name,address,CONTACT_NUMBER) values(?,?,?)";
                                OracleConnection oc2=new OracleConnection();
                                PreparedStatement ps2=oc2.conn.prepareStatement(sql2);
                                ps2.setString(1,companyNameTextField.getText().trim());
                                ps2.setString(2, companyAddressTextArea.getText().trim());
                                ps2.setString(3,contactNumberTextField.getText().trim());

                                ps2.executeUpdate();
                                //      System.out.println("bbbb");
                            }
                            if (count == 0) {
                                ps.setInt(5, Integer.parseInt(tfDesignationId.getText().trim()));
                            } else {
                                ps.setInt(5, getDesignationId());
                            }
                            int x = ps.executeUpdate();


                            if (x > 0) {

                                JOptionPane.showMessageDialog(frame, "registration successful");

                                resetTextFields();

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

        setRegistrationField();


        frame.add(registerPanel);
      /*  frame.setAlwaysOnTop(true);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Inventory Management");*/



        backgroundColor.setScreenSize(frame);

        return registerPanel;

    }

    private void resetTextFields() {
        userTextField.setText("");
        userNameTextField.setText("");
        emailTextField.setText("");
        passwordField.setText("");
        retypePasswordField.setText("");
        salaryComboBox.requestFocus();
    }

    private void textFieldPanelAdd(JTextField companyNameTextField) {
        companyNameTextField.setFont(font1);
        registerPanel.add(companyNameTextField);
    }

    private void labelPanelAdd(JLabel emailLabel, Font font1) {
        emailLabel.setFont(font1);
        registerPanel.add(emailLabel);
    }


    private void setRegistrationField() {
        try {
            String sql = "select count(u_id) from users";
            OracleConnection oc = new OracleConnection();
            PreparedStatement ps = oc.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                count = rs.getInt(1);
                if (count == 0) {
                    designation.setVisible(false);
                    salaryComboBox.setVisible(false);
                    showMessage( "Create New Account ");
                    tfDesignationName.setText("Admin");
                    tfDesignationName.setEditable(false);
                    welcomeMessageLabel.setBounds(615, 180, 300, 50);
                    registerButton.setBounds(620, 740, 100, 30);

                } else {
                    setVisibilityFalse();
                    chooseDesignation();
                    showMessage( "Create Account For Your Employees");
                    welcomeMessageLabel.setBounds(570, 180, 300, 50);
                    registerButton.setBounds(620, 520, 100, 30);


                }
            }
        } catch (Exception e) {
            System.out.println(e + " reg designation");
        }
    }

    private void setVisibilityFalse() {
        designationName.setVisible(false);
        designationId.setVisible(false);
        tfDesignationName.setVisible(false);
        tfDesignationId.setVisible(false);
        companyNameLabel.setVisible(false);
        companyAddressLabel.setVisible(false);
        contactNumberLabel.setVisible(false);
        contactNumberTextField.setVisible(false);
        companyAddressTextArea.setVisible(false);
        companyNameTextField.setVisible(false);
    }

    private void showMessage( String s) {
        welcomeMessageLabel.setText(s);
    }

    public void chooseDesignation() {
        try {
            String sql = "select * from SALARY";
            OracleConnection oc1 = new OracleConnection();
            PreparedStatement ps = oc1.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            salaryComboBox.removeAllItems();
            while (rs.next()) {
                salaryComboBox.addItem(new Register.designation(rs.getString(2)));
            }


        } catch (Exception c) {
            System.out.println(c);
        }
    }

    public class designation {
        String name;

        public designation(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }

    //changes

    private int getDesignationId() {
        int sal_id = 0;
        try {
            //String DesignationName = salaryComboBox.getSelectedItem().toString();

            Statement st = oc.conn.createStatement();
            String sql = "select SAL_ID,DESIGNATION from SALARY where DESIGNATION ='" + salaryComboBox.getSelectedItem() + "'";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                sal_id = rs.getInt("SAL_ID");
            }
        } catch (Exception e) {
            System.out.println(e + " getDesignationId");
        }

        return sal_id;
    }

}