package OracleConnection;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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

                String invoice="_invoice_.pdf";
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                System.out.println(dateFormat.format(date));
                Document document = new Document();


                try
                {
                    JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView());
                    j.setDialogTitle("Save as pdf");
                    j.addChoosableFileFilter(new FileNameExtensionFilter( "PDF Documents","pdf"));
                    j.showSaveDialog(null);


                    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Invoice.pdf"));
                    document.open();

                    Paragraph p1= new Paragraph("Company Name");

                    Paragraph p2= new Paragraph("Address");
                    Paragraph p3= new Paragraph("042-35712296\n\n\n");

                    Paragraph p4= new Paragraph("\n\nGrand Total: ");
                    Paragraph p5= new Paragraph("\nThank you for visiting us…!!\nReturn/Exchange not possible with-out bill.");

                    p1.setAlignment(Element.ALIGN_CENTER);
                    p3.setAlignment(Element.ALIGN_CENTER);
                    p2.setAlignment(Element.ALIGN_CENTER);
                    document.add(p1);
                    document.add(p2);
                    document.add(p3);

                    Phrase phrase = new Phrase("Time/Date: "+dateFormat.format(date));
                    PdfContentByte canvas = writer.getDirectContent();
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase, 40, 740, 0);
                    Phrase invNo = new Phrase("Invoice No: " );
                    PdfContentByte canv = writer.getDirectContent();
                    ColumnText.showTextAligned(canv, Element.ALIGN_LEFT, invNo, 510, 785, 0);



                    PdfPTable tab = new PdfPTable(5);
                    float[] columnWidths = new float[] {15f, 30f, 10f, 10f,15f};
                    tab.setWidths(columnWidths);
                    tab.addCell("Serial");

                    tab.addCell("Product name");
                    tab.addCell("Mrp");
                    tab.addCell("Quantity");
                    tab.addCell("Total Price");



                    for (int i=0;i<(table.getRowCount());i++){

                        String serial = table.getValueAt(i,0).toString();
                        String p_name = table.getValueAt(i,1).toString();
                        String mrp = table.getValueAt(i,2).toString();
                        String qty = table.getValueAt(i,3).toString();
                        //String price = table1.getValueAt(i,4).toString();
                        tab.addCell(serial);
                        tab.addCell(p_name);
                        tab.addCell(mrp);
                        tab.addCell(qty);
                        //tab.addCell(price);




                    }






                    document.add(tab);
                    document.add(p4);
                    document.add(p5);
                    // writer.close();
                    document.close();
                } catch (DocumentException e1)
                {
                    Logger.getLogger(CreateInvoice.class.getName()).log(Level.SEVERE,null,e1);
                    //e1.printStackTrace();
                } catch (FileNotFoundException e1)
                {
                    Logger.getLogger(CreateInvoice.class.getName()).log(Level.SEVERE,null,e1);
                    //e1.printStackTrace();
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
                        pf.setOrientation(PageFormat.LANDSCAPE);
                        if(pageNum>0){
                            return Printable.NO_SUCH_PAGE;
                        }

                        Graphics2D g2 = (Graphics2D)pg;
                        g2.translate(pf.getImageableX(), pf.getImageableY());
                        g2.scale(0.24,0.24);

                        mainpanel.paint(g2);


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