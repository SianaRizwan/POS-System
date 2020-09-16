package OracleConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class LoginPage {

    private JFrame frame;
    private JPanel panel;
    private JLabel userLabel, passLabel;
    private Font f1, f2;
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private static String uID;


    LoginPage(JFrame frame) {
        this.frame = frame;
        initComponents();
    }

    private void initComponents() {
        //frame = new JFrame();

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.lightGray);

        f1 = new Font("Arial", Font.BOLD, 15);
        f2 = new Font("Arial", Font.BOLD, 11);
        panel.setBackground(new Color(0xD9B9F2));
        userLabel = new JLabel();
        userLabel.setText("Username : ");
        userLabel.setBounds(500, 250, 150, 50);
        userLabel.setFont(f1);
        userLabel.setToolTipText("Enter your User Name");
        panel.add(userLabel);

        passLabel = new JLabel("Password : ");
        passLabel.setBounds(500, 290, 150, 50);
        passLabel.setToolTipText("Enter password");
        passLabel.setFont(f1);
        panel.add(passLabel);

        userNameField = new JTextField();
        userNameField.setBounds(600, 260, 200, 30);
        userNameField.setFont(f1);
        panel.add(userNameField);

        passwordField = new JPasswordField();
        passwordField.setBounds(600, 300, 200, 30);
        passwordField.setFont(f2);
        panel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(630, 360, 80, 30);
        loginButton.setBackground(new Color(0x7E0AB5));
        loginButton.setFont(f2);
        loginButton.setForeground(new Color(0xFEFEFE));

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    OracleConnection oc = new OracleConnection();

                    String sql = "select U_ID,NAME,PASSWORD,users.sal_id,designation FROM USERS,salary where users.sal_id=salary.sal_id and NAME='" + userNameField.getText().trim() + "'and PASSWORD ='" + passwordField.getText() + "'";
                    PreparedStatement ps = oc.conn.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        uID = String.valueOf(rs.getInt("U_ID"));
                        String designation=rs.getString("designation");
                        // System.out.println(uID);

                        if (designation.equals("admin")||designation.equals("ADMIN")||designation.equals("Admin")) {

                            new AdminDashboard(frame);
                        } else {
                            new Dashboard(frame);
                        }
                        panel.setVisible(false);

                    } else {

                        JOptionPane.showMessageDialog(frame, "invalid user id or password");
                        userNameField.setText("");
                        passwordField.setText("");
                        userNameField.requestFocus();
                    }
                } catch (Exception e1) {
                    System.out.println(e1);
                }

            }
        });
        panel.add(loginButton);


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



    public static String getUID() {
        System.out.println(uID);
        return uID;

    }

    public static void main(String[] args) {
        int count;
        JFrame frame = new JFrame();
        try{
            String sql="select count(u_id) from users";
            OracleConnection oc=new OracleConnection();
            PreparedStatement ps=oc.conn.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                count=rs.getInt(1);
                System.out.println(count);
                if(count==0) {

                    Register register=   new Register(frame);
                    register.initComponents();

                }
                else new LoginPage(frame);
            }


        } catch (Exception e) {
            System.out.println(e +"  login drama");
        }
    }
}
