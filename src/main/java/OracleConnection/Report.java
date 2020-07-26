package OracleConnection;

import javax.swing.*;
import java.awt.*;

public class Report {
    private JFrame frame;
    private JPanel panelReport, panel;
    private Font f1, f2;
    private JButton rsales, rbuy, rexpenses;
    private JTextField tyear, tmonth;
    private JLabel lyear,lmonth;


    public Report(JFrame frame) {
        this.frame = frame;
    }

    public JPanel initComponents(JPanel mainPanel) {
        this.panel = mainPanel;
        panelReport = new JPanel();
        panelReport.setLayout(null);
        panelReport.setBackground(new Color(0xD9B9F2));
        JLabel head = new JLabel("Report");
        head.setHorizontalAlignment(SwingConstants.CENTER);
        head.setFont(new Font("Lato Medium", Font.PLAIN, 40));
        head.setBounds(450, 0, 600, 70);
        panelReport.add(head);

        f1 = new Font("Arial", Font.BOLD, 15);
        f2 = new Font("Arial", Font.BOLD, 11);

        rsales = new JButton("Sales");
        rsales.setBounds(250, 300, 120, 50);
        rsales.setBackground(Color.cyan);
        rsales.setFont(f2);
        panelReport.add(rsales);

        rbuy = new JButton("Buy");
        rbuy.setBounds(650, 300, 120, 50);
        rbuy.setBackground(Color.cyan);
        rbuy.setFont(f2);
        panelReport.add(rbuy);

        rexpenses = new JButton("Expenses");
        rexpenses.setBounds(1050, 300, 120, 50);
        rexpenses.setBackground(Color.cyan);
        rexpenses.setFont(f2);
        panelReport.add(rexpenses);

        lmonth = new JLabel("Month : ");
        lmonth.setBounds(200, 150, 150, 50);
        lmonth.setFont(f1);
        panelReport.add(lmonth);
        lyear = new JLabel("Year : ");
        lyear.setBounds(200, 200, 150, 50);
        lyear.setFont(f1);
        panelReport.add(lyear);
        tmonth = new JTextField();
        tmonth.setBounds(330, 160, 200, 30);
        tmonth.setFont(f1);
        panelReport.add(tmonth);
        tyear = new JTextField();
        tyear.setBounds(330, 210, 200, 30);
        tyear.setFont(f1);
        panelReport.add(tyear);

        return panelReport;
    }


}
