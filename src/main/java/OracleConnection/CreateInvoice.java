package OracleConnection;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateInvoice {

    private JFrame frame;
    private JPanel mainpanel;
    private JLabel cName, mobile, address, email, invoiceGeneratorCreatedBy, companyName, invoiceSerial;
    private JTextField tfName, tfmobile, tfaddress, tfemail, invoiceGeneratorCreatedByTextField, companyNameTextField, invoiceSerialNumberTextField;
    private Font f1, f2;
    public JTable table;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private JButton invsave, invprint, invback;
    private String[] columns = {"Serial no", "Product Name", "Unit Price", "Qty", "Total"};
    private String[] rows = new String[5];
    LoginPage loginPage;
    private JLabel netTotalLabel;
    private JTextField netTotalTextField;
    BackgroundColor backgroundColor;

    public CreateInvoice(JFrame frame) {
        this.frame = frame;
        backgroundColor = new BackgroundColor(frame);
        initComponents();

    }

    private void initComponents() {
        //frame = new JFrame();


        mainpanel = backgroundColor.setGradientPanel();
        mainpanel.setLayout(null);
        JLabel head = new JLabel("Invoice");
        head.setHorizontalAlignment(SwingConstants.CENTER);
        head.setFont(new Font("Lato Medium", Font.PLAIN, 40));
        head.setBounds(450, 0, 600, 70);
        mainpanel.add(head);

        f1 = new Font("Arial", Font.BOLD, 15);
        f2 = new Font("Arial", Font.BOLD, 11);
        cName = new JLabel("Customer Name : ");
        cName.setBounds(200, 100, 150, 50);
        labelPanelAdd(cName);

        mobile = new JLabel("Mobile no : ");
        mobile.setBounds(650, 100, 150, 50);
        labelPanelAdd(mobile);

        address = new JLabel("Address : ");
        address.setBounds(200, 150, 150, 50);
        labelPanelAdd(address);

        email = new JLabel("Email : ");
        email.setBounds(650, 150, 150, 50);
        labelPanelAdd(email);

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
        backgroundColor.setTableDesign(table, f1);


        scrollPane.setBounds(150, 350, 1000, 300);
        mainpanel.add(scrollPane);

        invsave = new JButton("Save");
        invsave.setBounds(200, 750, 120, 40);
        backgroundColor.setButtonColor(invsave);
        invsave.setFont(f2);
        mainpanel.add(invsave);
        invsave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                System.out.println(dateFormat.format(date));

                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle("Save Pdf");
                chooser.setApproveButtonText("Save");
                chooser.addChoosableFileFilter(new FileNameExtensionFilter("PDF", "pdf"));
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setAcceptAllFileFilterUsed(true);


                if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());


                    try {

                        Document document = new Document();
                        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(new File(chooser.getSelectedFile(), "Invoice.pdf")));
                        document.open();
                        String company_Name = null, companyAddress = null, contactNumber = null;

                        try {
                            String sql = "select * from company_info";
                            OracleConnection oc = new OracleConnection();
                            PreparedStatement ps = oc.conn.prepareStatement(sql);
                            ResultSet rs = ps.executeQuery();
                            while (rs.next()) {
                                company_Name = rs.getString(1);
                                companyAddress = rs.getString(2);
                                contactNumber = rs.getString(3);
                            }

                        } catch (SQLException throwables) {
                            System.out.println(throwables + " create invoice company info");
                        }

                        Paragraph companyName = new Paragraph(company_Name);
                        Paragraph address = new Paragraph(companyAddress);
                        Paragraph contactNum = new Paragraph(contactNumber);
                        Paragraph p5 = new Paragraph("Thank you for visiting us…!!\nReturn/Exchange not possible with-out bill\n\n\n\n\n");


                        companyName.setAlignment(Element.ALIGN_CENTER);
                        contactNum.setAlignment(Element.ALIGN_CENTER);
                        address.setAlignment(Element.ALIGN_CENTER);
                        p5.setAlignment(Element.ALIGN_CENTER);
                        document.add(companyName);
                        document.add(address);
                        document.add(contactNum);
                        document.add(p5);

                        Phrase phrase = new Phrase("Time/Date: " + dateFormat.format(date));
                        PdfContentByte canvas = writer.getDirectContent();
                        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase, 40, 800, 0);

                        Paragraph phrase1 = new Paragraph("Customer Name :" + tfName.getText());
                        phrase1.setIndentationLeft(30f);
                        document.add(phrase1);

                        Paragraph phrase2 = new Paragraph("Contact No :" + tfmobile.getText());
                        phrase2.setIndentationLeft(30f);
                        document.add(phrase2);

                        Paragraph phrase3 = new Paragraph("Email :" + tfemail.getText());
                        phrase3.setIndentationLeft(30f);
                        document.add(phrase3);

                        Paragraph phrase4 = new Paragraph("Address :" + tfaddress.getText());
                        phrase4.setIndentationLeft(30f);
                        phrase4.setSpacingAfter(20f);
                        document.add(phrase4);


                        Phrase invNo = new Phrase("Invoice No: " + invoiceSerialNumberTextField.getText());
                        PdfContentByte canv = writer.getDirectContent();
                        ColumnText.showTextAligned(canv, Element.ALIGN_LEFT, invNo, 500, 785, 0);

                        PdfContentByte canvtable = writer.getDirectContent();
                        PdfPTable tab = new PdfPTable(5);
                        float[] columnWidths = new float[]{15f, 30f, 10f, 10f, 15f};
                        tab.setWidths(columnWidths);
                        tab.setTotalWidth(85f);
                        tab.addCell("Serial");
                        tab.addCell("Product name");
                        tab.addCell("Mrp");
                        tab.addCell("Quantity");
                        tab.addCell("Total Price");


                        for (int i = 0; i < (table.getRowCount()); i++) {

                            String serial = table.getValueAt(i, 0).toString();
                            String p_name = table.getValueAt(i, 1).toString();
                            String mrp = table.getValueAt(i, 2).toString();
                            String qty = table.getValueAt(i, 3).toString();
                            String price = table.getValueAt(i, 4).toString();
                            tab.addCell(serial);
                            tab.addCell(p_name);
                            tab.addCell(mrp);
                            tab.addCell(qty);
                            tab.addCell(price);
                        }

                        document.add(tab);

                        Paragraph netTotal = new Paragraph("Net Total : " + netTotalTextField.getText());
                        netTotal.setIndentationLeft(385f);
                        document.add(netTotal);

                        Paragraph createdBy = new Paragraph("Created By : " + invoiceGeneratorCreatedByTextField.getText());
                        createdBy.setSpacingBefore(15f);
                        createdBy.setIndentationLeft(385f);
                        document.add(createdBy);

                        document.close();
                        writer.close();
                        JOptionPane.showMessageDialog(frame, "Invoice Saved...");
                    } catch (DocumentException e1) {
                        Logger.getLogger(CreateInvoice.class.getName()).log(Level.SEVERE, null, e1);
                        //e1.printStackTrace();
                    } catch (FileNotFoundException e1) {
                        Logger.getLogger(CreateInvoice.class.getName()).log(Level.SEVERE, null, e1);
                        //e1.printStackTrace();
                    }


                }
            }


        });
        invprint = new JButton("Print");
        invprint.setBounds(350, 750, 120, 40);
        backgroundColor.setButtonColor(invprint);
        invprint.setFont(f2);
        mainpanel.add(invprint);
        invprint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);

                PrinterJob job = PrinterJob.getPrinterJob();
                job.setJobName("Print Data");


                job.setPrintable(new Printable() {
                    public int print(Graphics pg, PageFormat pf, int pageNum) {
                        pf.setOrientation(PageFormat.LANDSCAPE);
                        if (pageNum > 0) {
                            return Printable.NO_SUCH_PAGE;
                        }

                        Graphics2D g2 = (Graphics2D) pg;
                        g2.translate(pf.getImageableX(), pf.getImageableY());
                        g2.scale(0.24, 0.24);

                        mainpanel.paint(g2);


                        return Printable.PAGE_EXISTS;


                    }
                });

                boolean ok = job.printDialog();
                if (ok) {
                    try {

                        job.print();
                    } catch (PrinterException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        invback = new JButton("Back");
        invback.setBounds(500, 750, 120, 40);
        backgroundColor.setButtonColor(invback);
        invback.setFont(f2);
        mainpanel.add(invback);
        invback.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dashboard dashboard = new Dashboard(frame);
                dashboard.tabbedPane.setSelectedIndex(2);
                mainpanel.setVisible(false);
            }
        });

        invoiceSerial = new JLabel("Invoice serial : ");
        invoiceSerial.setBounds(10, 10, 130, 40);
        labelPanelAdd(invoiceSerial);

        invoiceSerialNumberTextField = new JTextField();
        invoiceSerialNumberTextField.setBounds(130, 15, 160, 30);
        invoiceSerialNumberTextField.setFont(f1);
        setInvoiceSerialNumber();
        invoiceSerialNumberTextField.setEditable(false);
        mainpanel.add(invoiceSerialNumberTextField);

        invoiceGeneratorCreatedBy = new JLabel("Created by :");
        invoiceGeneratorCreatedBy.setBounds(950, 740, 150, 50);
        labelPanelAdd(invoiceGeneratorCreatedBy);

        companyName = new JLabel("Company Name :");
        companyName.setBounds(950, 790, 150, 50);
        labelPanelAdd(companyName);

        invoiceGeneratorCreatedByTextField = new JTextField();
        invoiceGeneratorCreatedByTextField.setBounds(1150, 750, 200, 30);
        invoiceGeneratorCreatedByTextField.setFont(f1);
        setSellerName();
        invoiceGeneratorCreatedByTextField.setEditable(false);
        mainpanel.add(invoiceGeneratorCreatedByTextField);

        companyNameTextField = new JTextField();
        companyNameTextField.setBounds(1150, 800, 200, 30);
        companyNameTextField.setFont(f1);
        setCompanyName();
        mainpanel.add(companyNameTextField);

        netTotalLabel = new JLabel("Net Total : ");
        netTotalLabel.setBounds(800, 660, 150, 50);
        netTotalLabel.setToolTipText("Enter password");
        labelPanelAdd(netTotalLabel);

        netTotalTextField = new JTextField();
        netTotalTextField.setBounds(950, 670, 200, 30);
        netTotalTextField.setFont(f1);
        setNetTotalValue();
        netTotalTextField.setEditable(false);
        mainpanel.add(netTotalTextField);

        frame.add(mainpanel);


        backgroundColor.setScreenSize(frame);

    }

    private void labelPanelAdd(JLabel label) {
        label.setFont(f1);
        mainpanel.add(label);
    }

    private void setInvoiceSerialNumber() {
        try {
            String sql = "select max(sale_id) from sales";
            OracleConnection oc = new OracleConnection();
            PreparedStatement preparedStatement = oc.conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                invoiceSerialNumberTextField.setText(rs.getString(1));
                System.out.println(rs.getString(1) + " opop");
            }

        } catch (Exception e) {
            System.out.println(e + "  setInvoiceSerialNumber");
        }
    }

    private void setSellerName() {
        try {
            String sql = "select name from users where u_id='" + loginPage.getUID() + "'";
            OracleConnection oc = new OracleConnection();
            PreparedStatement preparedStatement = oc.conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {

                invoiceGeneratorCreatedByTextField.setText(rs.getString(1));
            }

        } catch (Exception e) {
            System.out.println(e + "  setSellerName");
        }
    }

    private void setCompanyName() {
        try {
            String sql = "select name from company_info";
            OracleConnection oc = new OracleConnection();
            PreparedStatement preparedStatement = oc.conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {

                companyNameTextField.setText(rs.getString(1));
            }

        } catch (Exception e) {
            System.out.println(e + "  setCompanyName");
        }
    }

    private void setNetTotalValue() {
        try {
            String sql = "select sum(mrp*p_quantity) from supply_order,sales_details,sales,product " +
                    "where sales.sale_id=sales_details.sale_id and sales_details.p_id=product.p_id " +
                    "and product.s_id=supply_order.s_id and sales.sale_id=(select max(sale_id) from sales)";
            OracleConnection oc = new OracleConnection();
            PreparedStatement preparedStatement = oc.conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {

                netTotalTextField.setText(rs.getString(1));
                //   System.out.println(loginPage.getUID()+" opop");
            }

        } catch (Exception e) {
            System.out.println(e + "  invoice serial");
        }
    }

}