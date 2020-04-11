package OracleConnection;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CreateInvoice {

    private JFrame frame;
    private JPanel mainpanel;
    private JLabel cName, mobile, address, email, invoiceGenerator, companyName, invoiceSerial;
    private JTextField tfName, tfmobile, tfaddress, tfemail, tfinvg, tfcomname, tfserial;
    private Font f1, f2;
    public JTable table;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private JButton invsave, invprint, invback;
    private String[] columns = {"Serial no", "Product Name", "Price", "Qty", "Total"};
    private String[] rows = new String[5];

    OracleConnection oc = new OracleConnection();
    PreparedStatement ps;
    ResultSet rs;

    public CreateInvoice(JFrame frame) {
        this.frame = frame;
        initComponents();

    }

    private void initComponents() {
        //frame = new JFrame();


        mainpanel = new JPanel();
        mainpanel.setLayout(null);
        mainpanel.setBackground(new Color(0xD9B9F2));
        //mainpanel.setBackground(Color.lightGray);
        JLabel head = new JLabel("Invoice");
        head.setHorizontalAlignment(SwingConstants.CENTER);
        head.setFont(new Font("Lato Medium", Font.PLAIN, 40));
        head.setBounds(450, 0, 600, 70);
        mainpanel.add(head);

        f1 = new Font("Arial", Font.BOLD, 15);
        f2 = new Font("Arial", Font.BOLD, 11);
        cName = new JLabel("Customer Name : ");
        cName.setBounds(200, 100, 150, 50);
        cName.setFont(f1);
        mainpanel.add(cName);
        mobile = new JLabel("Mobile no : ");
        mobile.setBounds(650, 100, 150, 50);
        mobile.setFont(f1);
        mainpanel.add(mobile);
        address = new JLabel("Address : ");
        address.setBounds(200, 150, 150, 50);
        address.setFont(f1);
        mainpanel.add(address);
        email = new JLabel("Email : ");
        email.setBounds(650, 150, 150, 50);
        email.setFont(f1);
        mainpanel.add(email);
        tfName = new JTextField();
        tfName.setBounds(350, 110, 200, 30);
        tfName.setFont(f1);
        mainpanel.add(tfName);
        tfmobile = new JTextField();
        tfmobile.setBounds(750, 110, 200, 30);
        tfmobile.setFont(f1);
        mainpanel.add(tfmobile);
        tfaddress = new JTextField();
        tfaddress.setBounds(350, 160, 200, 30);
        tfaddress.setFont(f1);
        mainpanel.add(tfaddress);
        tfemail = new JTextField();
        tfemail.setBounds(750, 160, 200, 30);
        tfemail.setFont(f1);
        mainpanel.add(tfemail);

        table = new JTable();
        model = new DefaultTableModel();
        scrollPane = new JScrollPane(table);
        model.setColumnIdentifiers(columns);
        table.setModel(model);
        table.setFont(f1);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(Color.GRAY);
        table.setRowHeight(30);

        scrollPane.setBounds(150, 350, 1000, 300);
        mainpanel.add(scrollPane);

        invsave = new JButton("Save");
        invsave.setBounds(200, 750, 120, 40);
        invsave.setBackground(Color.cyan);
        invsave.setFont(f2);
        mainpanel.add(invsave);
        invsave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               JFrame win = (JFrame)SwingUtilities.getWindowAncestor(mainpanel);
                Dimension size = win.getSize();
                //BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
                BufferedImage image = (BufferedImage)win.createImage(size.width, size.height);
                Graphics g = image.getGraphics();
                win.paint(g);
                g.dispose();
                try
                {
                    ImageIO.write(image, "jpg", new File("D:\\Spl(1)\\MyFrame2.jpg"));
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }


            });
        invprint = new JButton("Print");
        invprint.setBounds(350, 750, 120, 40);
        invprint.setBackground(Color.cyan);
        invprint.setFont(f2);
        mainpanel.add(invprint);
        invprint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                PrinterJob job = PrinterJob.getPrinterJob();
                job.setJobName("Print Data");

                job.setPrintable(new Printable(){
                    public int print(Graphics pg, PageFormat pf, int pageNum){
                        pf.setOrientation(PageFormat.PORTRAIT);
                        if(pageNum>0){
                            return Printable.NO_SUCH_PAGE;
                        }

                        Graphics2D g2 = (Graphics2D)pg;
                        g2.translate(pf.getImageableX(), pf.getImageableY());
                        g2.scale(0.24,0.24);

                        mainpanel.paint(g2);
//

                        return Printable.PAGE_EXISTS;


                    }
                });

                boolean ok = job.printDialog();
                if(ok){
                    try{

                        job.print();
                    }
                     catch (PrinterException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        invback = new JButton("Back");
        invback.setBounds(500, 750, 120, 40);
        invback.setBackground(Color.cyan);
        invback.setFont(f2);
        mainpanel.add(invback);
        invback.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Dashboard(frame);
                mainpanel.setVisible(false);
            }
        });
        invoiceSerial = new JLabel("Invoice serial : ");
        invoiceSerial.setBounds(10, 10, 130, 40);
        invoiceSerial.setFont(f1);
        mainpanel.add(invoiceSerial);
        tfserial = new JTextField();
        tfserial.setBounds(130, 15, 160, 30);
        tfserial.setFont(f1);
        mainpanel.add(tfserial);
        invoiceGenerator = new JLabel("Created by :");
        invoiceGenerator.setBounds(950, 700, 150, 50);
        invoiceGenerator.setFont(f1);
        mainpanel.add(invoiceGenerator);
        companyName = new JLabel("Company Name :");
        companyName.setBounds(950, 750, 150, 50);
        companyName.setFont(f1);
        mainpanel.add(companyName);
        tfinvg = new JTextField();
        tfinvg.setBounds(1150, 710, 200, 30);
        tfinvg.setFont(f1);
        mainpanel.add(tfinvg);
        tfcomname = new JTextField();
        tfcomname.setBounds(1150, 760, 200, 30);
        tfcomname.setFont(f1);
        mainpanel.add(tfcomname);


        frame.add(mainpanel);
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