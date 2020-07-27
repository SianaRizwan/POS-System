package OracleConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Report {
    private JFrame frame;
    private JPanel panel;
    private JComboBox monthComboBox,yearComboBox;
    private Font f1, f2;
    private JButton rsales, rbuy, rexpenses;
    private JTextField tyear, tmonth;
    private JLabel lyear,lmonth;


    public Report(JFrame frame) {
        this.frame = frame;

    }

    public JPanel initComponents() {

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(0xD9B9F2));

        f1 = new Font("Arial", Font.BOLD, 15);
        f2 = new Font("Arial", Font.BOLD, 11);



            JLabel head = new JLabel("Report");
            head.setHorizontalAlignment(SwingConstants.CENTER);
            head.setFont(new Font("Lato Medium", Font.PLAIN, 40));
            head.setBounds(450, 0, 600, 70);
            panel.add(head);


            rsales = new JButton("Sales");
            rsales.setBounds(250, 250, 120, 50);
            rsales.setBackground(Color.cyan);
            rsales.setFont(f2);
            rsales.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
            panel.add(rsales);

            rbuy = new JButton("Buy");
            rbuy.setBounds(650, 250, 120, 50);
            rbuy.setBackground(Color.cyan);
            rbuy.setFont(f2);
            rbuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
            panel.add(rbuy);

            rexpenses = new JButton("Expenses");
            rexpenses.setBounds(1050, 250, 120, 50);
            rexpenses.setBackground(Color.cyan);
            rexpenses.setFont(f2);
            rexpenses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
            panel.add(rexpenses);

            lmonth = new JLabel("Month : ");
            lmonth.setBounds(200, 150, 150, 50);
            lmonth.setFont(f1);
            panel.add(lmonth);
            lyear = new JLabel("Year : ");
            lyear.setBounds(700, 150, 150, 50);
            lyear.setFont(f1);
            panel.add(lyear);
            monthComboBox = new JComboBox();
            monthComboBox.setBounds(280, 160, 200, 30);
            monthComboBox.setEditable(false);
            monthComboBox.setFont(f1);
            panel.add(monthComboBox);
            yearComboBox = new JComboBox();
            yearComboBox.setBounds(780, 160, 200, 30);
            yearComboBox.setEditable(false);
            yearComboBox.setFont(f1);
            panel.add( yearComboBox);

        frame.add(panel);
        frame.setAlwaysOnTop(true);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Report");

        Toolkit toolkit = Toolkit.getDefaultToolkit();

        int xsize = (int) toolkit.getScreenSize().getWidth();
        int ysize = (int) toolkit.getScreenSize().getHeight();
        frame.setSize(xsize, ysize);

            return  panel;


        }



}