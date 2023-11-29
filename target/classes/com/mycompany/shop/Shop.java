package com.mycompany.shop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Font;
import java.awt.Color;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import org.jdatepicker.impl.*;
import java.time.LocalDate;
import java.awt.print.*;
import java.io.File;
import java.io.FileNotFoundException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.table.JTableHeader;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.HashMap;
import java.util.Map;
import java.text.SimpleDateFormat;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.text.DateFormat;
import java.util.Date;





/**
 *
 * @author odipojames12
 */
public class Shop extends JFrame implements ActionListener {

    Statement statement;
    JButton addProdButton;

    JTextField productName;
    JTextField productId;
    JTextField productUnit;
    JTextField productQuantity;
    JTextField minQuantity;
    JTextField productPrice,buyingPrice;
    JButton displayButton1;
    JTable table1;
    JButton clearButton1;
    JTextField searchT1;
    JButton searchB1;
    JComboBox c1, c2, roleC, c3, c4, c5, c6,c7,c8,c9;
    JTable catTable;
    JTextField sumTexFld;
    JButton addB1,uploadButton,sampleUploadButton;
    JTextField balanceTexFld;
    JTextField paidTexFld;
    JTextField q1;
    JButton sellB1;
    JTable allProductsTable;
    JButton loadAllB;
    JTextField srchFld;
    JButton searchAllB;
    JButton editProductB;
    JTextField editName;
    JTextField editUnit;
    JTextField editQuantity;
    JTextField editPrice;
    JTextField editMinQ,editBuyingPrice;
    JButton saveB;
    JButton deleteB;
    JTextField expense,expenditure,reciept_no,editExpense,editExpenditure,editRecieptN,editExpenseDate;
    JTable restockTable, usersTable, salesTable, restockReportTable,expensesTable,disparitiesTable;
    JButton restockB1;
    JButton addB2, createUserB, editUserB, processB, processB1;
    JTextField sumTexFld1, q2, p1,invoiceTextFld, Bp1,userName, editUserName, editUserRole;
    JPasswordField passwordP, editUserPassword;
    JCheckBox dateCheckBox, dateCheckBox1,dateCheckBox2,dateCheckBox3,invoiceCheckBox1;
    JButton currentStockExpt, currentStock;
    private Map<String, Integer> inventory = new HashMap<>();
    JButton addExpenseButton,expEditButton,editExpenseButton,showExpensesButton;
    JButton expDeleteButton,expExcelButton,expPdfButton;
    JTextField disparity,editDisparityStatus,editDisparity,editDisparityDate;
    JButton addDisparityButton,dispEditButton,dispDeleteButton,showDisparitiesButton;
    JButton editDisparityButton,dispPdfButton,dispExcelButton,editSettingsB;
    JTextField receipt_title,business_name;
    JFrame jm = new JFrame();
    JDatePickerImpl todatePicker, fromdatePicker, todatePicker1, fromdatePicker1,todatePicker2, fromdatePicker2,todatePicker3, fromdatePicker3,disparityDatePicker;
    public static boolean isAuthenticated = false;
    public static String logUser = "";
    public static String role = "";
    public static String userId = "";
    String businessName = "";
    String recieptTitle = "";
    String invoice_no ;
    

    Shop() {
        JDialog.setDefaultLookAndFeelDecorated(true);

        JPanel allProductViewPanel = new JPanel();
        JPanel pn2 = new JPanel();
        JPanel restockPanel = new JPanel();
        JPanel addProductsPanel = new JPanel(new BorderLayout());
        JPanel salesReportPanel = new JPanel();
        JTabbedPane tabs = new JTabbedPane();
        JLabel footer = new JLabel("© Copyright; 2023 OdipoJames");

        allProductViewPanel.setSize(1000, 500);
        //pn2.setSize(1000, 500);
        restockPanel.setSize(1000, 500);

        //layouts
        GridLayout grid = new GridLayout(4, 3, 5, 2);
        //GridLayout grid1 = new GridLayout(2,2,2,5);

        //add products panel
        //display products table panel
        Font buttonFont = new Font("Tahoma", Font.BOLD, 12);

        table1 = new JTable();
        JScrollPane table1Scroller = new JScrollPane(table1);
        table1Scroller.setBounds(150, 20, 700, 200);
        searchT1 = new JTextField("search by name or id", 30);
        searchT1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                searchT1.setText("");
            }
        });

        //auto detect changes in search text fields
        searchT1.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                displaySearchedProductstable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                displaySearchedProductstable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                displaySearchedProductstable();
            }

        }
        );

        searchB1 = new JButton("search");
        searchB1.addActionListener((this));
        searchT1.setColumns(15);
        JPanel allProductsPanel = new JPanel();
        JPanel allProductsPane2 = new JPanel();
        allProductsPanel.setLayout(null);
        allProductsPanel.add(table1Scroller);

        displayButton1 = new JButton("Show Products");
        displayButton1.setFont(buttonFont);
        displayButton1.addActionListener((this));
        clearButton1 = new JButton("Clear");
        clearButton1.addActionListener((this));
        clearButton1.setFont(buttonFont);
        allProductsPane2.add(displayButton1);
        allProductsPane2.add(clearButton1);
        allProductsPane2.add(searchT1);
        allProductsPane2.add(searchB1);
        uploadButton = new JButton("uplaod products");
        uploadButton.addActionListener((this));
        allProductsPane2.add(uploadButton);
        sampleUploadButton = new JButton("download upload sample");
        sampleUploadButton.addActionListener((this));
        allProductsPane2.add(sampleUploadButton);
        allProductsPanel.setBackground(Color.lightGray);
        addProductsPanel.add(pn2, BorderLayout.NORTH);
        addProductsPanel.add(allProductsPanel, BorderLayout.CENTER);
        addProductsPanel.add(allProductsPane2, BorderLayout.SOUTH);

        pn2.setSize(200, 200);
        pn2.setLayout(grid);
        pn2.setBorder(new EmptyBorder(30, 50, 50, 50));
        JLabel nameLabel = new JLabel("Name product");
        productName = new JTextField();
        //JLabel idLabel = new JLabel("Product Id");
        productId = new JTextField(10);
        productId.addKeyListener(new JTextFieldKeyListener(productId));
        JLabel unitLabel = new JLabel("Unit");
        productUnit = new JTextField("ML");
        JLabel quantityLabel = new JLabel("Quantity");
        productQuantity = new JTextField(10);
        productQuantity.addKeyListener(new JFloatListener(productQuantity));
        JLabel priceLabel = new JLabel("Price");
        productPrice = new JTextField(10);
        productPrice.addKeyListener(new JFloatListener(productPrice));
        JLabel minLabel = new JLabel("minimum quantity");
        minQuantity = new JTextField(10);
        minQuantity.addKeyListener(new JFloatListener(minQuantity));

        JLabel dammy = new JLabel("");
        addProdButton = new JButton("add product");
        addProdButton.addActionListener((this));
        
        JLabel buyingPriceLabel = new JLabel("Buying Price");
        buyingPrice = new JTextField(10);
        buyingPrice.addKeyListener(new JFloatListener(buyingPrice));
        
        pn2.add(nameLabel);
        pn2.add(productName);
        pn2.add(unitLabel);
        pn2.add(productUnit);
        pn2.add(quantityLabel);
        pn2.add(productQuantity);
        pn2.add(priceLabel);
        pn2.add(productPrice);
        pn2.add(minLabel);
        pn2.add(minQuantity);
        
        pn2.add(buyingPriceLabel);
        pn2.add(buyingPrice);

        pn2.add(dammy);
        pn2.add(addProdButton);
       
        //sell products window
        GridLayout grid2 = new GridLayout(1, 7, 5, 2);
        JPanel sellProductPanel = new JPanel();
        JPanel sellProductPanel1 = new JPanel();
        sellProductPanel.setSize(1000, 500);
        sellProductPanel1.setBorder(new EmptyBorder(20, 50, 50, 50));
        tabs.add("Sell Product", sellProductPanel);
        if (isAuthenticated && role.equalsIgnoreCase("admin")) {
             tabs.add("Add New  Products", addProductsPanel);
        }
        sellProductPanel.setLayout(new BorderLayout());
        sellProductPanel.add(sellProductPanel1, BorderLayout.NORTH);
        c1 = new JComboBox();
        c1.addItem("Select Product");
        c1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                filComb();
            }
        });
        c1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                autoSearch(c1, String.valueOf(e.getKeyChar()));
            }
        });
        JLabel lbQ = new JLabel("Quantity");
        JLabel dumn = new JLabel("");
        addB1 = new JButton("add");
        addB1.addActionListener((this));
        q1 = new JTextField(5);
        q1.addKeyListener(new JFloatListener(q1));
        sellProductPanel1.add(c1);
        sellProductPanel1.add(lbQ);
        sellProductPanel1.add(q1);
        sellProductPanel1.add(addB1);
        sellProductPanel1.add(dumn);
        sellProductPanel1.add(dumn);
        sellProductPanel1.add(dumn);

        JPanel sellProductPanel2 = new JPanel();
        sellProductPanel2.setLayout(new BorderLayout());
        sellProductPanel.add(sellProductPanel2, BorderLayout.CENTER);
        DefaultTableModel catTablemodel = new DefaultTableModel();
        catTablemodel.addColumn("ID");
        catTablemodel.addColumn("Name");
        catTablemodel.addColumn("Unit");
        catTablemodel.addColumn("Quantity");
        catTablemodel.addColumn("Total Ksh");
        catTable = new JTable(catTablemodel);
        catTable.setBounds(40, 50, 300, 200);
         // Set the size of the second column (index 1)
        int columnIndex4 = 1;
        TableColumn column4 = catTable.getColumnModel().getColumn(columnIndex4);
        column4.setPreferredWidth(180); // Set the preferred width in pixels

        //dissable cell from edditing
        for (int c = 0; c < catTable.getColumnCount(); c++) {
            Class<?> col_class = catTable.getColumnClass(c);
            catTable.setDefaultEditor(col_class, null);        // remove editor
        }
        // Adding Jtable to JScrollPane
        JScrollPane sp = new JScrollPane(catTable);
        JPanel buttP = new JPanel();
        buttP.setLayout(new GridLayout(12, 1, 5, 2));
        sellB1 = new JButton("sell");
        sellB1.addActionListener((this));
        JButton rmB = new JButton("remove");
        rmB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (catTable.getSelectedRow() != -1) {
                    // remove selected row from the model
                    catTablemodel.removeRow(catTable.getSelectedRow());
                    catSum();
                    sumTexFld.setText(Float.toString(catSum()));
                    if (!paidTexFld.getText().equals("") && catSum() > 0 && catSum() < Float.parseFloat(paidTexFld.getText())) {
                        balanceTexFld.setText(Float.toString(Float.parseFloat(paidTexFld.getText()) - catSum()));
                    }

                }
            }

        });
        JButton rmB1 = new JButton("remove all");
        rmB1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel dtm = (DefaultTableModel) catTable.getModel();
                dtm.setRowCount(0);
                catSum();
                sumTexFld.setText(Float.toString(catSum()));
                //clear payment fields
                sumTexFld.setText("");
                balanceTexFld.setText("");
                paidTexFld.setText("");
            }

        });
        JLabel sumLabel = new JLabel("Total Sum:");
        sumTexFld = new JTextField(10);
        sumTexFld.setEditable(false);
        sumTexFld.addKeyListener(new JTextFieldKeyListener(sumTexFld));
        JLabel balanceLabel = new JLabel("Balance ");
        balanceTexFld = new JTextField(10);
        balanceTexFld.setEditable(false);
        JLabel paidLabel = new JLabel("Amount Paid ");
        paidTexFld = new JTextField(10);
        paidTexFld.addKeyListener(new JTextFieldKeyListener(paidTexFld));
        balanceTexFld.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!paidTexFld.getText().equals("") && catSum() < Integer.parseInt(paidTexFld.getText())) {
                    balanceTexFld.setText(Float.toString(Float.parseFloat(paidTexFld.getText()) - catSum()));
                } else {
                    balanceTexFld.setText("0");
                }
            }
        });

        buttP.add(rmB);
        buttP.add(rmB1);
        buttP.add(sellB1);
        buttP.add(sumLabel);
        buttP.add(sumTexFld);
        buttP.add(paidLabel);
        buttP.add(paidTexFld);
        buttP.add(balanceLabel);
        buttP.add(balanceTexFld);
        sellProductPanel2.add(sp, BorderLayout.CENTER);
        sellProductPanel2.add(buttP, BorderLayout.EAST);

        //view all products window
        tabs.add("View All Products", allProductViewPanel);
        

        allProductViewPanel.setLayout(null);
        JPanel allProductViewPanel1 = new JPanel();

        DefaultTableModel allProductsTableModel = new DefaultTableModel();
        allProductsTableModel.addColumn("ID");
        allProductsTableModel.addColumn("Name");
        allProductsTableModel.addColumn("Unit");
        allProductsTableModel.addColumn("Quantity");
        allProductsTableModel.addColumn("Price");
        allProductsTableModel.addColumn("Buying Price");
        allProductsTableModel.addColumn("Added By");
        allProductsTableModel.addColumn("Date Added");
        allProductsTable = new JTable(allProductsTableModel);
        // Set the size of the second column (index 1)
        int columnIndex = 1;
        TableColumn column = allProductsTable.getColumnModel().getColumn(columnIndex);
        column.setPreferredWidth(180); // Set the preferred width in pixels


        //dissable cell from edditing
        for (int c = 0; c < allProductsTable.getColumnCount(); c++) {
            Class<?> col_class = allProductsTable.getColumnClass(c);
            allProductsTable.setDefaultEditor(col_class, null);        // remove editor
        }
        // Adding Jtable to JScrollPane
        JScrollPane sp1 = new JScrollPane(allProductsTable);
        if (isAuthenticated && role.equalsIgnoreCase("admin")) {
            sp1.setBounds(10, 40, 700, 300);
        }
        if (isAuthenticated && role.equalsIgnoreCase("normal")) {
            sp1.setBounds(30, 40, 900, 300);
        }

        allProductViewPanel.add(sp1);
        loadAllB = new JButton("show all");
        loadAllB.setBounds(50, 350, 150, 20);
        allProductViewPanel.add(loadAllB);
        loadAllB.addActionListener((this));

        srchFld = new JTextField(10);
        srchFld.setBounds(260, 350, 200, 20);
        allProductViewPanel.add(srchFld);
        srchFld.setText("name or id");
        srchFld.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                srchFld.setText("");
            }
        });

        //auto sersch when typing
        srchFld.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                searchCrudProductsTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchCrudProductsTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchCrudProductsTable();
            }

        }
        );

        searchAllB = new JButton("search");
        searchAllB.setBounds(500, 350, 150, 20);
        allProductViewPanel.add(searchAllB);

        currentStock = new JButton("current stock");
        currentStock.setBounds(50, 450, 150, 20);
        allProductViewPanel.add(currentStock);
        currentStock.addActionListener((this));
        if (isAuthenticated && role.equalsIgnoreCase("admin")) {
            currentStockExpt = new JButton("export excel");
            currentStockExpt.addActionListener((this));
            currentStockExpt.setBounds(500, 450, 150, 20);
            allProductViewPanel.add(currentStockExpt);
            

        }

        searchAllB.addActionListener((this));
        editProductB = new JButton("Edit");
        editProductB.setBounds(200, 10, 100, 20);
        if (isAuthenticated && role.equalsIgnoreCase("admin")) {
            allProductViewPanel.add(editProductB);
        }

        editProductB.addActionListener((this));
        saveB = new JButton("Save");
        saveB.addActionListener((this));
        deleteB = new JButton("Delete");
        deleteB.setBounds(400, 10, 100, 20);
        if (isAuthenticated && role.equalsIgnoreCase("admin")) {
            allProductViewPanel.add(deleteB);
        }

        deleteB.addActionListener((this));

        JLabel editNamel = new JLabel("name");
        editNamel.setBounds(60, 20, 100, 20);
        allProductViewPanel1.add(editNamel);
        editName = new JTextField();
        editName.setBounds(10, 40, 220, 20);
        allProductViewPanel1.add(editName);

        JLabel editUnitl = new JLabel("unit");
        editUnitl.setBounds(60, 80, 100, 20);
        allProductViewPanel1.add(editUnitl);
        editUnit = new JTextField(10);
        editUnit.setBounds(10, 100, 220, 20);
        allProductViewPanel1.add(editUnit);

        JLabel editQuantityl = new JLabel("quantity");
        editQuantityl.setBounds(60, 140, 100, 20);
        allProductViewPanel1.add(editQuantityl);
        editQuantity = new JTextField(10);
        editQuantity.setBounds(10, 160, 220, 20);
        allProductViewPanel1.add(editQuantity);
        editQuantity.addKeyListener(new JFloatListener(editQuantity));

        JLabel editPricel = new JLabel("price per unit");
        editPricel.setBounds(60, 200, 100, 20);
        allProductViewPanel1.add(editPricel);
        editPrice = new JTextField(10);
        editPrice.setBounds(10, 220, 220, 20);
        allProductViewPanel1.add(editPrice);
        editPrice.addKeyListener(new JFloatListener(editPrice));
        
        
        JLabel editBPricel = new JLabel("buying price");
        editBPricel.setBounds(60, 260, 100, 20);
        allProductViewPanel1.add(editBPricel);
        editBuyingPrice = new JTextField(10);
        editBuyingPrice.setBounds(10, 280, 220, 20);
        allProductViewPanel1.add(editBuyingPrice);
        editBuyingPrice.addKeyListener(new JFloatListener(editBuyingPrice));

        JLabel editMinQL = new JLabel("minimum quantity");
        editMinQL.setBounds(60, 320, 220, 20);
        allProductViewPanel1.add(editMinQL);
        editMinQ = new JTextField(10);
        editMinQ.setBounds(10, 340, 220, 20);
        allProductViewPanel1.add(editMinQ);
        editMinQ.addKeyListener(new JFloatListener(editMinQ));

        saveB.setBounds(10, 380, 220, 20);
        if (isAuthenticated && role.equalsIgnoreCase("admin")) {
            allProductViewPanel1.add(saveB);
        }

        allProductViewPanel1.setLayout(null);
        allProductViewPanel1.setBounds(720, 30, 260,420);
        Border blackline4 = BorderFactory.createTitledBorder("Edit Product");
        allProductViewPanel1.setBorder(blackline4);
        if (isAuthenticated && role.equalsIgnoreCase("admin")) {
            allProductViewPanel.add(allProductViewPanel1);
        }

        //restock winow
        GridLayout grid3 = new GridLayout(1, 7, 5, 2);
        JPanel restockPanel1 = new JPanel();
        restockPanel.setSize(1000, 500);
        restockPanel1.setBorder(new EmptyBorder(20, 50, 50, 50));
        //tabs.add("restock Product",sellProductPanel);
        if (isAuthenticated && role.equalsIgnoreCase("admin")) {
            tabs.add("Restock Products ", restockPanel);
        }
        restockPanel.setLayout(new BorderLayout());
        restockPanel.add(restockPanel1, BorderLayout.NORTH);
        c2 = new JComboBox();
        c2.addItem("Select Product");
        c2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                filComb2();
            }
        });
        c2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                autoSearch(c2, String.valueOf(e.getKeyChar()));
            }
        });
        addB2 = new JButton("add");
        addB2.addActionListener((this));
        JLabel q2label = new JLabel("quantity");
        q2 = new JTextField(10);
        q2.addKeyListener(new JFloatListener(q2));
        JLabel pricel1 = new JLabel("price per unit");
        p1 = new JTextField(10);
        p1.addKeyListener(new JFloatListener(p1));
        JLabel Bpricel1 = new JLabel(" Buying price");
        Bp1 = new JTextField(10);
        Bp1.addKeyListener(new JFloatListener(Bp1));
        restockPanel1.add(c2);
        restockPanel1.add(q2label);
        restockPanel1.add(q2);
        restockPanel1.add(pricel1);
        restockPanel1.add(p1);
        restockPanel1.add(Bpricel1);
        restockPanel1.add(Bp1);
        restockPanel1.add(addB2);
        restockPanel1.add(dumn);
        restockPanel1.add(dumn);
        restockPanel1.add(dumn);

        JPanel restockPanel2 = new JPanel();
        restockPanel2.setLayout(new BorderLayout());
        restockPanel.add(restockPanel2, BorderLayout.CENTER);
        DefaultTableModel restockTablemodel = new DefaultTableModel();
        restockTablemodel.addColumn("ID");
        restockTablemodel.addColumn("Name");
        restockTablemodel.addColumn("Unit");
        restockTablemodel.addColumn("Quantity");
        restockTablemodel.addColumn("Price per uinit Ksh");
        restockTablemodel.addColumn("Buying Price Ksh");
        restockTablemodel.addColumn("Total Buying Price");
        restockTable = new JTable(restockTablemodel);
        restockTable.setBounds(40, 50, 300, 200);
         // Set the size of the second column (index 1)
        int columnIndex3 = 1;
        TableColumn column3 = restockTable.getColumnModel().getColumn(columnIndex3);
        column3.setPreferredWidth(180); // Set the preferred width in pixels

        //dissable cell from edditing
        for (int c = 0; c < restockTable.getColumnCount(); c++) {
            Class<?> col_class = restockTable.getColumnClass(c);
            restockTable.setDefaultEditor(col_class, null);        // remove editor
        }
        // Adding Jtable to JScrollPane
        JScrollPane sp3 = new JScrollPane(restockTable);
        JPanel restockButtons = new JPanel();
        restockButtons.setLayout(new GridLayout(12, 1, 5, 2));
        restockB1 = new JButton("restock");
        restockB1.addActionListener((this));
        JButton rmB3 = new JButton("remove");
        rmB3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (restockTable.getSelectedRow() != -1) {
                    // remove selected row from the model
                    restockTablemodel.removeRow(restockTable.getSelectedRow());
                    restockCatSum();
                    sumTexFld1.setText(Float.toString(restockCatSum()));
                }
            }

        });
        JButton rmB4 = new JButton("remove  all");
        rmB4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel dtm = (DefaultTableModel) restockTable.getModel();
                dtm.setRowCount(0);
                restockCatSum();
                sumTexFld1.setText(Float.toString(restockCatSum()));
            }

        });
        JLabel sumLabel1 = new JLabel("Total Sum:");
        sumTexFld1 = new JTextField(10);
        sumTexFld1.setEditable(false);
        sumTexFld1.addKeyListener(new JTextFieldKeyListener(sumTexFld1));
        restockButtons.add(rmB3);
        restockButtons.add(rmB4);
        restockButtons.add(restockB1);
        restockButtons.add(sumLabel1);
        restockButtons.add(sumTexFld1);
        restockPanel2.add(sp3, BorderLayout.CENTER);
        restockPanel2.add(restockButtons, BorderLayout.EAST);

        //users
        JPanel userPanel = new JPanel();
        userPanel.setLayout(null);
        Border blackline1 = BorderFactory.createTitledBorder("user manger");
        userPanel.setBorder(blackline1);
        //add user
        JLabel userLabel = new JLabel("username");
        userLabel.setBounds(200, 15, 200, 20);
        userPanel.add(userLabel);
        userName = new JTextField();
        userName.setBounds(200, 40, 200, 20);
        userPanel.add(userName);

        JLabel passwordLabel = new JLabel("password");
        passwordLabel.setBounds(500, 15, 200, 20);
        userPanel.add(passwordLabel);
        passwordP = new JPasswordField();
        passwordP.setBounds(500, 40, 200, 20);
        userPanel.add(passwordP);

        String[] rl = {"normal", "admin"};
        JLabel roleLabel = new JLabel("user role");
        roleLabel.setBounds(200, 60, 200, 20);
        userPanel.add(roleLabel);
        roleC = new JComboBox(rl);
        roleC.setBounds(200, 80, 200, 20);
        userPanel.add(roleC);
        createUserB = new JButton("create new user");
        createUserB.setBounds(500, 80, 200, 20);
        createUserB.addActionListener((this));
        userPanel.add(createUserB);

        //users table
        DefaultTableModel usersTablemodel = new DefaultTableModel();
        usersTablemodel.addColumn("ID");
        usersTablemodel.addColumn("USERNAME");
        usersTablemodel.addColumn("ROLE");
        usersTable = new JTable(usersTablemodel);
        //dissable cell from edditing
        for (int c = 0; c < usersTable.getColumnCount(); c++) {
            Class<?> col_class = usersTable.getColumnClass(c);
            usersTable.setDefaultEditor(col_class, null);        // remove editor
        }
        JScrollPane sp4 = new JScrollPane(usersTable);
        sp4.setBounds(30, 150, 300, 300);
        userPanel.add(sp4);
        JButton editUser = new JButton("Edit");

        editUser.setBounds(350, 150, 100, 20);
        userPanel.add(editUser);
        JButton deleteUser = new JButton("Delete");
        deleteUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (usersTable.getSelectedRow() < 0) {
                    JOptionPane.showMessageDialog(null, "select user", "Error!", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        Connection conn = getConnection();
                        Statement st = conn.createStatement();
                        String sql = "DELETE FROM users WHERE id = " + usersTable.getValueAt(usersTable.getSelectedRow(), 0);
                        String name = usersTable.getValueAt(usersTable.getSelectedRow(), 1).toString();
                        //confirm
                        JDialog.setDefaultLookAndFeelDecorated(true);
                        int response = JOptionPane.showConfirmDialog(null, "Do you want to delete " + name + " ?", "Confirm",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (response == JOptionPane.YES_OPTION) {
                            st.executeUpdate(sql);
                            JOptionPane.showMessageDialog(null, " " + name + " deleted ");
                            usersTableShow();
                        }

                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }

                }

            }

        });

        deleteUser.setBounds(350, 180, 100, 20);
        userPanel.add(deleteUser);

        JButton viewUsers = new JButton("View Users");
        viewUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usersTableShow();
            }

        });
        viewUsers.setBounds(350, 220, 100, 20);
        userPanel.add(viewUsers);

        JPanel editF = new JPanel();
        Border blackline2 = BorderFactory.createTitledBorder("edit user");
        editF.setBorder(blackline2);
        editF.setBounds(500, 150, 400, 300);
        editF.setLayout(null);

        JLabel editUserLabel = new JLabel("Username");
        editUserLabel.setBounds(60, 45, 100, 20);
        editF.add(editUserLabel);
        editUserName = new JTextField();
        editUserName.setBounds(180, 50, 100, 20);
        editF.add(editUserName);

        JLabel editUserRoleLabel = new JLabel("Role");
        editUserRoleLabel.setBounds(60, 75, 100, 20);
        editF.add(editUserRoleLabel);
        editUserRole = new JTextField();
        editUserRole.setBounds(180, 80, 100, 20);
        editF.add(editUserRole);

        JLabel editUserPasswordLabel = new JLabel("Password");
        editUserPasswordLabel.setBounds(60, 105, 100, 20);
        editF.add(editUserPasswordLabel);
        editUserPassword = new JPasswordField();
        editUserPassword.setBounds(180, 110, 100, 20);
        editF.add(editUserPassword);

        editUserB = new JButton("save");
        editUserB.setBounds(180, 150, 100, 20);
        editUserB.addActionListener((this));

        editUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //check product not picked
                if (usersTable.getSelectedRow() < 0) {
                    JOptionPane.showMessageDialog(null, "you must select user from table", "Error!", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        //read selected data
                        editUserName.setText(usersTable.getValueAt(usersTable.getSelectedRow(), 1).toString());
                        editUserRole.setText(usersTable.getValueAt(usersTable.getSelectedRow(), 2).toString());

                        Connection conn = getConnection();
                        Statement st = conn.createStatement();
                        String sql = "SELECT * FROM users WHERE id = " + Integer.parseInt(usersTable.getValueAt(usersTable.getSelectedRow(), 0).toString());
                        ResultSet rs = st.executeQuery(sql);
                        if (rs.next()) {
                            editUserPassword.setText(rs.getString(3));
                        }
                        conn.close();
                        st.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    
                    

                }

            }
        });
        editF.add(editUserB);
        userPanel.add(editF);

        if (isAuthenticated && role.equalsIgnoreCase("admin")) {
            tabs.add("User Manager ", userPanel);
        }

        //sales report window
         if (isAuthenticated && role.equalsIgnoreCase("admin")) {
             tabs.add("View Sales Report", salesReportPanel);
        }
       
        salesReportPanel.setLayout(null);

        JPanel salesTablePanel = new JPanel();
        salesTablePanel.setFont(new Font("Serif", Font.PLAIN, 15));
        salesTablePanel.setLayout(null);
        salesTablePanel.setBackground(Color.white);
        salesTablePanel.setBounds(30, 30, 900, 600);
        salesReportPanel.add(salesTablePanel);
        JLabel salesL = new JLabel("SALES");
        salesL.setBounds(30, 20, 100, 20);
        salesTablePanel.add(salesL);
        salesL.setForeground(Color.green);
        salesL.setFont(new Font("Serif", Font.PLAIN, 20));

        processB = new JButton("Process");
        processB.setBounds(400, 20, 100, 20);
        salesTablePanel.add(processB);
        processB.setBackground(Color.BLUE);
        processB.setForeground(Color.white);
        processB.addActionListener((this));

        JButton printB = new JButton("Print");
        printB.setBounds(550, 20, 100, 20);
        printB.addActionListener(e -> printTable(salesTable));
        salesTablePanel.add(printB);
        printB.setBackground(Color.BLUE);
        printB.setForeground(Color.white);

        JButton clearB = new JButton("Export Exl");
        clearB.setBounds(700, 20, 100, 20);
        clearB.addActionListener(e -> {
            try {
                exportTableToExcel(salesTable);
            } catch (IOException ex) {
                Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
            }
                //JOptionPane.showMessageDialog(jm, "Table exported to Excel successfully");
            
        });
        salesTablePanel.add(clearB);
        clearB.setBackground(Color.BLUE);
        clearB.setForeground(Color.white);

        JLabel fromLabel = new JLabel("From:");
        fromLabel.setBounds(30, 50, 100, 20);
        salesTablePanel.add(fromLabel);
        fromLabel.setForeground(Color.green);
        fromLabel.setFont(new Font("Serif", Font.PLAIN, 16));

        //date picker properties
        Properties p = new Properties();
        p.put("text.year", "Year");
        p.put("text.month", "Month");
        p.put("text.today", "Today");
        //getting current date
        LocalDate Ldate = LocalDate.now();

        UtilDateModel fromdatemodel = new UtilDateModel();
        fromdatemodel.setDate(Ldate.getYear(), Ldate.getMonthValue() - 1, Ldate.getDayOfMonth());
        fromdatemodel.setSelected(true);
        JDatePanelImpl datePanel1 = new JDatePanelImpl(fromdatemodel, p);
        fromdatePicker = new JDatePickerImpl(datePanel1, new DateLabelFormatter());
        fromdatePicker.setBounds(30, 70, 150, 20);
        salesTablePanel.add(fromdatePicker);

        JLabel toLabel = new JLabel("To:");
        toLabel.setBounds(210, 50, 100, 20);
        salesTablePanel.add(toLabel);
        toLabel.setForeground(Color.green);
        toLabel.setFont(new Font("Serif", Font.PLAIN, 16));

        UtilDateModel todatemodel = new UtilDateModel();
        todatemodel.setDate(Ldate.getYear(), Ldate.getMonthValue() - 1, Ldate.getDayOfMonth());
        todatemodel.setSelected(true);
        JDatePanelImpl datePanel = new JDatePanelImpl(todatemodel, p);
        todatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        todatePicker.setBounds(200, 70, 150, 20);
        salesTablePanel.add(todatePicker);

        dateCheckBox = new JCheckBox("All Dates", false);
        dateCheckBox.setBounds(380, 70, 100, 20);
        dateCheckBox.setForeground(Color.green);
        dateCheckBox.setFont(new Font("Serif", Font.PLAIN, 16));
        salesTablePanel.add(dateCheckBox);

        JLabel productsLabel = new JLabel("product");
        productsLabel.setBounds(500, 50, 100, 20);
        salesTablePanel.add(productsLabel);
        productsLabel.setForeground(Color.green);
        productsLabel.setFont(new Font("Serif", Font.PLAIN, 16));

        c3 = new JComboBox();
        c3.addItem("ALL");
        c3.setBounds(500, 70, 200, 20);
        salesTablePanel.add(c3);
        c3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                filComb3();
            }
        });
        c3.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                autoSearch(c3, String.valueOf(e.getKeyChar()));
            }
        });

        JLabel sellerLabel = new JLabel("Seller");
        sellerLabel.setBounds(722, 50, 100, 20);
        salesTablePanel.add(sellerLabel);
        sellerLabel.setForeground(Color.green);
        sellerLabel.setFont(new Font("Serif", Font.PLAIN, 16));

        c4 = new JComboBox();
        c4.addItem("ALL");
        c4.setBounds(722, 70, 100, 20);
        salesTablePanel.add(c4);
        c4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                filComb4();
            }
        });
        c4.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                autoSearch(c4, String.valueOf(e.getKeyChar()));
            }
        });

        //sales table
        DefaultTableModel salesTablemodel = new DefaultTableModel();
        salesTablemodel.addColumn("ID");
        salesTablemodel.addColumn("NAME");
        salesTablemodel.addColumn("UNIT");
        salesTablemodel.addColumn("QUANTITY");
        salesTablemodel.addColumn("TOTAL KSH.");
        salesTablemodel.addColumn("PROFIT KSH.");
        salesTablemodel.addColumn("SOLD BY");
        salesTablemodel.addColumn("DATE");
        salesTable = new JTable(salesTablemodel);
        // Set the size of the second column (index 1)
        int columnIndex1 = 1;
        TableColumn column1 = salesTable.getColumnModel().getColumn(columnIndex1);
        column1.setPreferredWidth(180); // Set the preferred width in pixels

        //dissable cell from edditing
        for (int c = 0; c < salesTable.getColumnCount(); c++) {
            Class<?> col_class = salesTable.getColumnClass(c);
            salesTable.setDefaultEditor(col_class, null);        // remove editor
        }
        JScrollPane sp5 = new JScrollPane(salesTable);
        sp5.setBounds(30, 100, 800, 400);
        salesTablePanel.add(sp5);

        //restock report window
        JPanel restockReportPanel = new JPanel();
          if (isAuthenticated && role.equalsIgnoreCase("admin")) {
              tabs.add("View Restock History", restockReportPanel);
        }
       
        restockReportPanel.setLayout(null);

        JPanel restockReportTablePanel = new JPanel();
        restockReportTablePanel.setFont(new Font("Serif", Font.PLAIN, 15));
        restockReportTablePanel.setLayout(null);
        restockReportTablePanel.setBackground(Color.white);
        restockReportTablePanel.setBounds(30, 30, 1000, 600);
        restockReportPanel.add(restockReportTablePanel);
        JLabel restockL1 = new JLabel("Restocks");
        restockL1.setBounds(30, 20, 100, 20);
        restockReportTablePanel.add(restockL1);
        restockL1.setForeground(Color.green);
        restockL1.setFont(new Font("Serif", Font.PLAIN, 20));

        processB1 = new JButton("Process");
        processB1.setBounds(400, 20, 100, 20);
        restockReportTablePanel.add(processB1);
        processB1.setBackground(Color.BLUE);
        processB1.setForeground(Color.white);
        processB1.addActionListener((this));

        JButton printB1 = new JButton("Print");
        printB1.setBounds(550, 20, 100, 20);
        printB1.addActionListener(e -> printTable(restockReportTable));
        restockReportTablePanel.add(printB1);
        printB1.setBackground(Color.BLUE);
        printB1.setForeground(Color.white);

        JButton clearB1 = new JButton("Export Exl");
        clearB1.setBounds(700, 20, 100, 20);
        clearB1.addActionListener(e -> {
            try {
                exportTableToExcel(restockReportTable);
            } catch (IOException ex) {
                Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
            }
                //JOptionPane.showMessageDialog(jm, "Table exported to Excel successfully.");
           
        });
        restockReportTablePanel.add(clearB1);
        clearB1.setBackground(Color.BLUE);
        clearB1.setForeground(Color.white);
        
        
        invoiceCheckBox1 = new JCheckBox("By invoice no", false);
        invoiceCheckBox1.setBounds(850, 20, 120, 20);
        invoiceCheckBox1.setBackground(Color.BLUE);
        invoiceCheckBox1.setForeground(Color.white);
        restockReportTablePanel.add(invoiceCheckBox1);

        JLabel fromLabel1 = new JLabel("From:");
        fromLabel1.setBounds(30, 50, 100, 20);
        restockReportTablePanel.add(fromLabel1);
        fromLabel1.setForeground(Color.green);
        fromLabel1.setFont(new Font("Serif", Font.PLAIN, 16));

        //date picker properties
        UtilDateModel fromdatemodel1 = new UtilDateModel();
        fromdatemodel1.setDate(Ldate.getYear(), Ldate.getMonthValue() - 1, Ldate.getDayOfMonth());
        fromdatemodel1.setSelected(true);
        JDatePanelImpl datePanel2 = new JDatePanelImpl(fromdatemodel1, p);
        fromdatePicker1 = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
        fromdatePicker1.setBounds(30, 70, 150, 20);
        restockReportTablePanel.add(fromdatePicker1);

        JLabel toLabel1 = new JLabel("To:");
        toLabel1.setBounds(210, 50, 100, 20);
        restockReportTablePanel.add(toLabel1);
        toLabel1.setForeground(Color.green);
        toLabel1.setFont(new Font("Serif", Font.PLAIN, 16));

        UtilDateModel todatemodel1 = new UtilDateModel();
        todatemodel1.setDate(Ldate.getYear(), Ldate.getMonthValue() - 1, Ldate.getDayOfMonth());
        todatemodel1.setSelected(true);
        JDatePanelImpl datePanel3 = new JDatePanelImpl(todatemodel1, p);
        todatePicker1 = new JDatePickerImpl(datePanel3, new DateLabelFormatter());
        todatePicker1.setBounds(200, 70, 150, 20);
        restockReportTablePanel.add(todatePicker1);

        dateCheckBox1 = new JCheckBox("All Dates", false);
        dateCheckBox1.setBounds(380, 70, 100, 20);
        dateCheckBox1.setForeground(Color.green);
        dateCheckBox1.setFont(new Font("Serif", Font.PLAIN, 16));
        restockReportTablePanel.add(dateCheckBox1);

        JLabel productsLabel1 = new JLabel("product");
        productsLabel1.setBounds(500, 50, 100, 20);
        restockReportTablePanel.add(productsLabel1);
        productsLabel1.setForeground(Color.green);
        productsLabel1.setFont(new Font("Serif", Font.PLAIN, 16));

        c5 = new JComboBox();
        c5.addItem("ALL");
        c5.setBounds(500, 70, 200, 20);
        restockReportTablePanel.add(c5);
        c5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                filComb5();
            }
        });
        c5.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                autoSearch(c5, String.valueOf(e.getKeyChar()));
            }
        });

        JLabel restockerLabel = new JLabel("Restocker");
        restockerLabel.setBounds(722, 50, 100, 20);
        restockReportTablePanel.add(restockerLabel);
        restockerLabel.setForeground(Color.green);
        restockerLabel.setFont(new Font("Serif", Font.PLAIN, 16));

        c6 = new JComboBox();
        c6.addItem("ALL");
        c6.setBounds(722, 70, 100, 20);
        restockReportTablePanel.add(c6);
        c6.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                filComb6();
            }
        });
        c6.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                autoSearch(c6, String.valueOf(e.getKeyChar()));
            }
        });
        invoiceTextFld = new JTextField();
        invoiceTextFld.setBounds(850, 70, 120, 20);
        restockReportTablePanel.add(invoiceTextFld);

        // restock table
        DefaultTableModel restockReportTableModel = new DefaultTableModel();
        restockReportTableModel.addColumn("ID");
        restockReportTableModel.addColumn("NAME");
        restockReportTableModel.addColumn("UNIT");
        restockReportTableModel.addColumn("QUANTITY");
        restockReportTableModel.addColumn("Price");
        restockReportTableModel.addColumn("Buying Price");
        restockReportTableModel.addColumn("Total Buying Price");
        restockReportTableModel.addColumn("RESTOCKER");
        restockReportTableModel.addColumn("INVOICE NUMBER");
        restockReportTableModel.addColumn("DATE");
        restockReportTable = new JTable(restockReportTableModel);
        int columnIndex2 = 1;
        TableColumn column2 = restockReportTable.getColumnModel().getColumn(columnIndex2);
        column2.setPreferredWidth(180); // Set the preferred width in pixels
        restockReportTable.getColumnModel().getColumn(5).setPreferredWidth(140);
        restockReportTable.getColumnModel().getColumn(6).setPreferredWidth(140);
        restockReportTable.getColumnModel().getColumn(7).setPreferredWidth(140);
        restockReportTable.getColumnModel().getColumn(8).setPreferredWidth(140);
        restockReportTable.getColumnModel().getColumn(9).setPreferredWidth(120);
        //dissable cell from edditing
        for (int c = 0; c < restockReportTable.getColumnCount(); c++) {
            Class<?> col_class = restockReportTable.getColumnClass(c);
            restockReportTable.setDefaultEditor(col_class, null);        // remove editor
        }
        JScrollPane sp6 = new JScrollPane(restockReportTable);
        sp6.setBounds(30, 100, 950, 400);
        restockReportTablePanel.add(sp6);

        //reminders on low stok
        Timer timer = new Timer();
        int firstAlertDelay = 10 * 1000; // 10 seconds
        int repeatAlertInterval = 5 * 60 * 1000; // 5 minutes

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //checkAndAlertLowStock();
                loadInventoryFromDatabase();
            }
        }, firstAlertDelay, repeatAlertInterval);
        
        //exspenses window
        JPanel expensesPanel = new JPanel();
        tabs.add("Expenses", expensesPanel);
        expensesPanel.setLayout(null);
        JLabel expenseL = new JLabel("expense name");
        expenseL.setBounds(200, 15, 200, 20);
        expense = new JTextField();
        expense.setBounds(200, 40, 200, 20);
        expensesPanel.add(expenseL);
        expensesPanel.add(expense);
        
        JLabel expenditureL = new JLabel("Expenditure Ksh");
        expenditureL.setBounds(500, 15, 200, 20);
        expenditure = new JTextField();
        expenditure.addKeyListener(new JFloatListener(expenditure));
        expenditure.setBounds(500, 40, 200, 20);
        expensesPanel.add(expenditureL);
        expensesPanel.add(expenditure);
        
        JLabel reciept_noL= new JLabel("Reciept No");
        reciept_noL.setBounds(200, 70, 200, 20);
        reciept_no = new JTextField();
        reciept_no.setBounds(200, 95, 200, 20);
        expensesPanel.add(reciept_noL);
        expensesPanel.add(reciept_no);
        
        addExpenseButton = new JButton("Add Expense");
        addExpenseButton.addActionListener((this));
        addExpenseButton.setBounds(500, 95, 200, 20);
        expensesPanel.add(addExpenseButton);
        
        
        JLabel fromLabel4 = new JLabel("From:");
        fromLabel4.setBounds(30, 120, 100, 20);
        expensesPanel.add(fromLabel4);
        fromLabel4.setForeground(Color.green);
        fromLabel4.setFont(new Font("Serif", Font.PLAIN, 16));

        //date picker properties
        UtilDateModel fromdatemodel4 = new UtilDateModel();
        fromdatemodel4.setDate(Ldate.getYear(), Ldate.getMonthValue() - 1, Ldate.getDayOfMonth());
        fromdatemodel4.setSelected(true);
        JDatePanelImpl datePanel4 = new JDatePanelImpl(fromdatemodel4, p);
        fromdatePicker2 = new JDatePickerImpl(datePanel4, new DateLabelFormatter());
        fromdatePicker2.setBounds(30, 140, 150, 20);
        expensesPanel.add(fromdatePicker2);

        JLabel toLabel4 = new JLabel("To:");
        toLabel4.setBounds(210, 120, 100, 20);
        expensesPanel.add(toLabel4);
        toLabel4.setForeground(Color.green);
        toLabel1.setFont(new Font("Serif", Font.PLAIN, 16));

        UtilDateModel todatemodel4 = new UtilDateModel();
        todatemodel4.setDate(Ldate.getYear(), Ldate.getMonthValue() - 1, Ldate.getDayOfMonth());
        todatemodel4.setSelected(true);
        JDatePanelImpl datePanel5 = new JDatePanelImpl(todatemodel4, p);
        todatePicker2 = new JDatePickerImpl(datePanel5, new DateLabelFormatter());
        todatePicker2.setBounds(200, 140, 150, 20);
        expensesPanel.add(todatePicker2);

        dateCheckBox2 = new JCheckBox("All Dates", false);
        dateCheckBox2.setBounds(380, 140, 100, 20);
        dateCheckBox2.setForeground(Color.green);
        dateCheckBox2.setFont(new Font("Serif", Font.PLAIN, 16));
        expensesPanel.add(dateCheckBox2);
        JLabel sellerExLabel = new JLabel("Seller");
        sellerExLabel.setBounds(500, 120, 100, 20);
        expensesPanel.add(sellerExLabel);
        sellerExLabel.setForeground(Color.green);
        sellerExLabel.setFont(new Font("Serif", Font.PLAIN, 16));

        c7 = new JComboBox();
        c7.addItem("ALL");
        c7.setBounds(500, 140, 100, 20);
        expensesPanel.add(c7);
        c7.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                filComb7();
            }
        });
        c7.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                autoSearch(c7, String.valueOf(e.getKeyChar()));
            }
        });
        showExpensesButton = new JButton("Show report");
        showExpensesButton.addActionListener((this));
        showExpensesButton.setBounds(620, 140, 120, 20);
        expensesPanel.add(showExpensesButton);
        
         // expenses  table
        DefaultTableModel expensesTableModel = new DefaultTableModel();
        expensesTableModel.addColumn("ID");
        expensesTableModel.addColumn("Expense");
        expensesTableModel.addColumn("Expenditure Ksh.");
        expensesTableModel.addColumn("Reciept No");
        expensesTableModel.addColumn("Added BY");
        expensesTableModel.addColumn("Date");
        expensesTable = new JTable(expensesTableModel);
        
        expensesTable.getColumnModel().getColumn(1).setPreferredWidth(140);
        
        //dissable cell from edditing
        for (int c = 0; c < expensesTable.getColumnCount(); c++) {
            Class<?> col_class = expensesTable.getColumnClass(c);
            expensesTable.setDefaultEditor(col_class, null);        // remove editor
        }
        JScrollPane sp7 = new JScrollPane(expensesTable);
        sp7.setBounds(5, 165, 800, 300);
        expensesPanel.add(sp7);
        
        expPdfButton = new JButton("print");
        expPdfButton.setBounds(150, 475, 100, 20);
        expPdfButton.addActionListener((this));
        expensesPanel.add(expPdfButton);
        expEditButton = new JButton("edit");
        expEditButton.addActionListener((this));
        expEditButton.setBounds(260, 475, 100, 20);
        expDeleteButton = new JButton("delete");
        expDeleteButton.addActionListener((this));
        expDeleteButton.setBounds(370, 475, 100, 20);
        expExcelButton = new JButton("export excl");
        expExcelButton.setBounds(480, 475, 100, 20);
        expExcelButton.addActionListener((this));
        if (isAuthenticated && role.equalsIgnoreCase("admin")) {
        expensesPanel.add(expEditButton);
        expensesPanel.add(expDeleteButton);
        expensesPanel.add(expExcelButton);
        }
       
        

       
        
        JPanel editExpensePanel = new JPanel();
        Border blackline5 = BorderFactory.createTitledBorder("Edit ");
        editExpensePanel.setBorder(blackline5);
        editExpensePanel.setLayout(null);
        editExpensePanel.setBounds(820, 120, 270, 300);
         if (isAuthenticated && role.equalsIgnoreCase("admin")) {
         expensesPanel.add(editExpensePanel);
        }
        JLabel editExpenseL = new JLabel("expense name");
        editExpenseL.setBounds(30, 20, 200, 20);
         editExpense = new JTextField();
        editExpense.setBounds(10, 45, 200, 20);
        editExpensePanel.add(editExpenseL);
        editExpensePanel.add(editExpense);
        
        JLabel editExpenditureL = new JLabel("Expenditure Ksh");
        editExpenditureL.setBounds(30, 75, 200, 20);
        editExpenditure = new JTextField();
        editExpenditure.addKeyListener(new JFloatListener(editExpenditure));
        editExpenditure.setBounds(10, 100, 200, 20);
        editExpensePanel.add(editExpenditureL);
        editExpensePanel.add(editExpenditure);
         
        JLabel editRecieptL= new JLabel("Reciept No");
        editRecieptL.setBounds(30, 130, 200, 20);
        editRecieptN = new JTextField();
        editRecieptN.setBounds(10, 155, 200, 20);
        editExpensePanel.add(editRecieptL);
        editExpensePanel.add(editRecieptN );
        
        
        JLabel editExpenseDateL= new JLabel("Date Registered");
        editExpenseDateL.setBounds(30, 185, 200, 20);
        editExpenseDate = new JTextField();
        editExpenseDate.setBounds(10, 205, 200, 20);
        editExpensePanel.add(editExpenseDateL);
        editExpensePanel.add(editExpenseDate );
        
        
        editExpenseButton = new JButton("Save");
        editExpenseButton.setBounds(10, 245, 200, 20);
        editExpenseButton.addActionListener((this));
        editExpensePanel.add(editExpenseButton);
        
        
        
        
      //disparities/shots window
        JPanel disparitiesPanel = new JPanel();
          if (isAuthenticated && role.equalsIgnoreCase("admin")) {
          tabs.add("Disparities", disparitiesPanel);
        }
        disparitiesPanel.setLayout(null);
        JLabel sellerL = new JLabel("Seller");
        sellerL.setBounds(200, 15, 200, 20);
        disparitiesPanel.add(sellerL);
        c8 = new JComboBox();
        c8.addItem("ALL");
        c8.setBounds(200, 40, 200, 20);
        disparitiesPanel.add(c8);
        c8.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                filComb8();
            }
        });
        c8.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                autoSearch(c8, String.valueOf(e.getKeyChar()));
            }
        });
      
        JLabel disparityL = new JLabel("Dipsparity Ksh");
        disparityL.setBounds(500, 15, 200, 20);
        disparity = new JTextField();
        disparity.addKeyListener(new JFloatListener(disparity));
        disparity.setBounds(500, 40, 200, 20);
        disparitiesPanel.add(disparityL);
        disparitiesPanel.add(disparity);
        
        JLabel dispDateL= new JLabel("Date");
        dispDateL.setBounds(200, 70, 200, 20);
        disparitiesPanel.add( dispDateL);
        UtilDateModel disparityDateModel = new UtilDateModel();
        disparityDateModel.setDate(Ldate.getYear(), Ldate.getMonthValue() - 1, Ldate.getDayOfMonth());
        disparityDateModel.setSelected(true);
        JDatePanelImpl disparityDatePanel = new JDatePanelImpl(disparityDateModel, p);
        disparityDatePicker = new JDatePickerImpl(disparityDatePanel, new DateLabelFormatter());
        disparityDatePicker.setBounds(200, 95, 200, 20);
        disparitiesPanel.add( disparityDatePicker);
        
        
        addDisparityButton = new JButton("Add Disparity");
        addDisparityButton.setBounds(500, 95, 200, 20);
        disparitiesPanel.add(addDisparityButton);
         addDisparityButton.addActionListener((this));
        
        
        JLabel fromLabel5 = new JLabel("From:");
        fromLabel5.setBounds(30, 120, 100, 20);
        disparitiesPanel.add(fromLabel5);
        fromLabel5.setForeground(Color.green);
        fromLabel5.setFont(new Font("Serif", Font.PLAIN, 16));

        //date picker properties
        UtilDateModel fromdatemodel6 = new UtilDateModel();
        fromdatemodel6.setDate(Ldate.getYear(), Ldate.getMonthValue() - 1, Ldate.getDayOfMonth());
        fromdatemodel6.setSelected(true);
        JDatePanelImpl datePanel6 = new JDatePanelImpl(fromdatemodel6, p);
        fromdatePicker3 = new JDatePickerImpl(datePanel6, new DateLabelFormatter());
        fromdatePicker3.setBounds(30, 140, 150, 20);
        disparitiesPanel.add(fromdatePicker3);

        JLabel toLabel5 = new JLabel("To:");
        toLabel5.setBounds(210, 120, 100, 20);
        disparitiesPanel.add(toLabel5);
        toLabel5.setForeground(Color.green);
        toLabel5.setFont(new Font("Serif", Font.PLAIN, 16));

        UtilDateModel todatemodel7 = new UtilDateModel();
        todatemodel7.setDate(Ldate.getYear(), Ldate.getMonthValue() - 1, Ldate.getDayOfMonth());
        todatemodel7.setSelected(true);
        JDatePanelImpl datePanel7 = new JDatePanelImpl(todatemodel7, p);
        todatePicker3 = new JDatePickerImpl(datePanel7, new DateLabelFormatter());
        todatePicker3.setBounds(200, 140, 150, 20);
        disparitiesPanel.add(todatePicker3);

        dateCheckBox3 = new JCheckBox("All Dates", false);
        dateCheckBox3.setBounds(380, 140, 100, 20);
        dateCheckBox3.setForeground(Color.green);
        dateCheckBox3.setFont(new Font("Serif", Font.PLAIN, 16));
        disparitiesPanel.add(dateCheckBox3);
        JLabel sellerDispLabel = new JLabel("Seller");
        sellerExLabel.setBounds(500, 120, 100, 20);
        disparitiesPanel.add(sellerDispLabel);
        sellerDispLabel.setForeground(Color.green);
        sellerDispLabel.setFont(new Font("Serif", Font.PLAIN, 16));

        c9 = new JComboBox();
        c9.addItem("ALL");
        c9.setBounds(500, 140, 100, 20);
        disparitiesPanel.add(c9);
        c9.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                filComb9();
            }
        });
        c9.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                autoSearch(c9, String.valueOf(e.getKeyChar()));
            }
        });
        showDisparitiesButton = new JButton("Show report");
        showDisparitiesButton.setBounds(620, 140, 120, 20);
        showDisparitiesButton.addActionListener((this));
        disparitiesPanel.add(showDisparitiesButton);
        
         // disparities table
        DefaultTableModel disparitiesTableModel = new DefaultTableModel();
        disparitiesTableModel.addColumn("ID");
        disparitiesTableModel.addColumn("Seller");
        disparitiesTableModel.addColumn("Disparity Ksh.");
        disparitiesTableModel.addColumn("Status");
        disparitiesTableModel.addColumn("Added BY");
        disparitiesTableModel.addColumn("Date");
        disparitiesTable = new JTable(disparitiesTableModel);
        
        disparitiesTable.getColumnModel().getColumn(1).setPreferredWidth(140);
        
        //dissable cell from edditing
        for (int c = 0; c < disparitiesTable.getColumnCount(); c++) {
            Class<?> col_class = disparitiesTable.getColumnClass(c);
            disparitiesTable.setDefaultEditor(col_class, null);        // remove editor
        }
        JScrollPane sp8 = new JScrollPane(disparitiesTable);
        sp8.setBounds(5, 165, 800, 300);
        disparitiesPanel.add(sp8);
        
        dispPdfButton = new JButton("print");
        dispPdfButton.setBounds(150, 475, 100, 20);
        dispPdfButton.addActionListener((this));
        disparitiesPanel.add(dispPdfButton);
        dispEditButton = new JButton("edit");
        dispEditButton.addActionListener((this));
        dispEditButton.setBounds(260, 475, 100, 20);
        dispDeleteButton = new JButton("delete");
        dispDeleteButton.setBounds(370, 475, 100, 20);
        dispDeleteButton.addActionListener((this));
        dispExcelButton = new JButton("export excl");
        dispExcelButton.addActionListener((this));
        dispExcelButton.setBounds(480, 475, 100, 20);
        disparitiesPanel.add(dispEditButton);
        disparitiesPanel.add(dispDeleteButton);
        disparitiesPanel.add(dispExcelButton);

       
        
        JPanel editDisparityPanel = new JPanel();
        Border blackline6 = BorderFactory.createTitledBorder("Edit ");
        editDisparityPanel.setBorder(blackline6);
        editDisparityPanel.setLayout(null);
        editDisparityPanel.setBounds(820, 120, 270, 300);
        disparitiesPanel.add(editDisparityPanel);
        JLabel editDisparityL = new JLabel("Disparity Ksh.");
        editDisparityL.setBounds(30, 20, 200, 20);
        editDisparity = new JTextField();
        editDisparity.setBounds(10, 45, 200, 20);
        editDisparity.addKeyListener(new JFloatListener(editDisparity));
        editDisparityPanel.add(editDisparityL);
        editDisparityPanel.add(editDisparity);
        
        JLabel editDisparityStatusL = new JLabel("Status");
        editDisparityStatusL.setBounds(30, 75, 200, 20);
        editDisparityStatus = new JTextField();
        editDisparityStatus.setBounds(10, 100, 200, 20);
        editDisparityPanel.add(editDisparityStatusL);
        editDisparityPanel.add(editDisparityStatus);
         
        JLabel editDisparityDateL= new JLabel("Date");
        editDisparityDateL.setBounds(30, 130, 200, 20);
        editDisparityDate = new JTextField();
        editDisparityDate.setBounds(10, 155, 200, 20);
        editDisparityPanel.add(editDisparityDateL);
        editDisparityPanel.add(editDisparityDate );
        
 
        
        editDisparityButton = new JButton("Save");
        editDisparityButton.setBounds(10, 245, 200, 20);
        editDisparityButton.addActionListener((this));
        editDisparityPanel.add(editDisparityButton);
        //settings window
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(null);
        Border settingsTitle = BorderFactory.createTitledBorder("system settings");
        settingsPanel.setBorder(settingsTitle);
        
        JLabel setNameLabel = new JLabel("Business Name");
        setNameLabel.setBounds(200, 15, 200, 20);
        settingsPanel.add(setNameLabel);
        business_name = new JTextField();
        business_name.setBounds(200, 40, 200, 20);
        settingsPanel.add(business_name);

        JLabel setReceiptLabel = new JLabel("Receipt Title");
        setReceiptLabel .setBounds(500, 15, 200, 20);
        settingsPanel.add(setReceiptLabel );
        receipt_title = new JTextField();
        receipt_title.setBounds(500, 40, 200, 20);
        settingsPanel.add( receipt_title);
        editSettingsB = new JButton("Edit");
        editSettingsB.setBounds(350, 80, 200, 20);
        editSettingsB.addActionListener((this));
        settingsPanel.add(editSettingsB);
        if (isAuthenticated && role.equalsIgnoreCase("admin")) {
            tabs.add("Settings ", settingsPanel);
        }
        
        
        
        jm.add(tabs);

        jm.setTitle(businessName);
        if (isAuthenticated && !role.equalsIgnoreCase("admin")) {
          jm.setSize(800, 650);
        }
        jm.setSize(1100, 650);
        jm.setLocationRelativeTo(null);
        //add icon;
        jm.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("store-icon.jpg")));
        jm.setVisible(true);
        jm.add(footer, BorderLayout.SOUTH);
        jm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        jm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                String ObjButtons[] = {"LogOut", "Exit"};
                int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to logout or exit?", "Shop Manager", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
                if (PromptResult == JOptionPane.YES_OPTION) {
                    jm.dispose();
                    new LoginPage();

                }
                if (PromptResult == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
            }
        });

    }

    //connection tofdatabase
    public static Connection getConnection() {
        String dbURL = "jdbc:derby:C:/db/shop;create=true";

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(dbURL);
            System.out.println("Connected!");

        } catch (SQLException ex) {
            Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            System.out.println("No connection");

        }

        return conn;
    }
    
    
    
//get settings
 
public void getSettings() {
        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            String sql = "SELECT name,title FROM settings";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                recieptTitle = rs.getString("title");
                businessName = rs.getString("name");
                receipt_title.setText(recieptTitle);
                business_name.setText(businessName);
                jm.setTitle(businessName);
            }

            st.close();
            conn.close();

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

    }    
    
//edit settings
 private void editSettings(){
   if(business_name.getText().trim().isEmpty() ||receipt_title.getText().trim().isEmpty()){
        JOptionPane.showMessageDialog(this, "ALl fields required", "Error!", JOptionPane.ERROR_MESSAGE);
         return;
        } 
            try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            //delete existing first
            String deleteQuery = "DELETE FROM settings ";
            int rowsAffected = st.executeUpdate(deleteQuery);
            //create new 
            if(rowsAffected>0){
            PreparedStatement insert = conn.prepareStatement("insert into settings (name,title)values(?,?)");
                    insert.setString(1, business_name.getText());
                    insert.setString(2, receipt_title.getText());
                    insert.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Settings Successfuly Updated");
                    insert.close(); 
            }
            st.close();
            conn.close();
            getSettings();
            

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
   
   
   
 }
    
 //add disparity
    //add expense
    private void addDisparity(){
        if(disparity.getText().trim().isEmpty() || c8.getItemCount()==0  ){
        JOptionPane.showMessageDialog(this, "ALl fields required", "Error!", JOptionPane.ERROR_MESSAGE);
         return;
        }
        String seller = c8.getSelectedItem().toString();
        Float  disp = Float.valueOf(disparity.getText());
        String date = disparityDatePicker.getJFormattedTextField().getText();
        
        
        try{
        Connection conn =getConnection();
        String sql = "INSERT INTO disparities (seller,disparity,added_by,date) VALUES (?, ?, ?, ?)";
        PreparedStatement state = conn.prepareStatement(sql);
        state.setString(1, seller);
        state.setFloat(2, disp);
       // state.setString(3, reciept_num);
        state.setInt(3,Integer.parseInt(userId));
        state.setString(4, date);
        state.executeUpdate();
        JOptionPane.showMessageDialog(this, "Disparity recorded.", "Success", JOptionPane.INFORMATION_MESSAGE);
        state.close();
        conn.close();
        disparitiesTableReport();
        disparity.setText(" ");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }

 public void disparitiesTableReport() {
        //clears table if any
        DefaultTableModel dm = (DefaultTableModel) disparitiesTable.getModel();
        dm.setRowCount(0);
        //get filter variables
        String from = fromdatePicker3.getJFormattedTextField().getText();
        String to = todatePicker3.getJFormattedTextField().getText();
        String seller = c9.getSelectedItem().toString();
        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            Statement st2 = conn.createStatement();
            Statement st3 = conn.createStatement();
            String sql = "SELECT * FROM disparities ORDER BY date DESC ";
            
              //expenses by specific seller between dates
            if (!dateCheckBox3.isSelected() && !seller.equalsIgnoreCase("ALL")) {

                ResultSet rs3 = st3.executeQuery("SELECT * FROM users WHERE username='" + seller + "' ");
                if (rs3.next()) {
                    sql = "SELECT * FROM disparities WHERE added_by =" + Integer.valueOf(rs3.getString(1)) + " AND date BETWEEN '" + from + "' AND '" + to + "' ";
                }
            }
            //specific dates but  all sellers
            if (!dateCheckBox3.isSelected() && seller.equalsIgnoreCase("ALL")) {

             sql = "SELECT * FROM disparities WHERE date >= '" + from + "' AND date <='" + to + "'";
                       
            }
             //all dates and  all sellers
            if (dateCheckBox3.isSelected() && seller.equalsIgnoreCase("ALL")) {

             sql = "SELECT * FROM disparities ";
                       
            }
            
            //all dates but specific seller
             if (dateCheckBox3.isSelected() && !seller.equalsIgnoreCase("ALL")) {

                ResultSet rs3 = st3.executeQuery("SELECT * FROM users WHERE username='" + seller + "' ");
                if (rs3.next()) {
                    sql = "SELECT * FROM disparities WHERE added_by =" + Integer.valueOf(rs3.getString(1));
                }
            }
            ResultSet rs = st.executeQuery(sql);
            DefaultTableModel model = (DefaultTableModel) disparitiesTable.getModel();
            String id,seller1,disparity1,status,added_by, date;
            while (rs.next()) {
                id = rs.getString(1);
                seller1 = rs.getString(2);
                disparity1 = rs.getString(3);
                status = rs.getString(4);
                //find username of user foriegn key
                ResultSet rs2 = st2.executeQuery("SELECT * FROM users WHERE id=" + Integer.parseInt(rs.getString(5)) + " ");
                if (rs2.next()) {
                    added_by = rs2.getString(2);
                } else {
                    added_by = rs.getString(5);
                }
                date = rs.getString(6);
                String[] row = {id,seller1,disparity1,status,added_by,date};
                model.addRow(row);
            }
             model.addRow(new Object[]{"", "Sum Total", colSum(disparitiesTable, 2),"","",""});
            st.close();
            st2.close();
            conn.close();

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

    } 
        
    
    
    
 //add expense
    private void addExspense(){
        if(expense.getText().trim().isEmpty() ||expenditure.getText().trim().isEmpty() || reciept_no.getText().trim().isEmpty() ){
        JOptionPane.showMessageDialog(this, "ALl fields required", "Error!", JOptionPane.ERROR_MESSAGE);
         return;
        }
        String expe = expense.getText();
        Float  expnd = Float.valueOf(expenditure.getText());
        String reciept_num =  reciept_no.getText();
        
        try{
        Connection conn =getConnection();
        String sql = "INSERT INTO expenses (expense, expenditure,reciept_no,  added_by) VALUES (?, ?, ?, ?)";
        PreparedStatement state = conn.prepareStatement(sql);
        state.setString(1, expe);
        state.setFloat(2, expnd);
        state.setString(3, reciept_num);
        state.setInt(4,Integer.parseInt(userId));
        state.executeUpdate();
        JOptionPane.showMessageDialog(this, "Expense recorded.", "Success", JOptionPane.INFORMATION_MESSAGE);
        state.close();
        conn.close();
        exspensesTableReport();
        expense.setText(" ");
        expenditure.setText(" ");
        reciept_no.setText(" ");
        
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    //expenses table report
    public void exspensesTableReport() {
        //clears table if any
        DefaultTableModel dm = (DefaultTableModel) expensesTable.getModel();
        dm.setRowCount(0);
        //get filter variables
        String from = fromdatePicker2.getJFormattedTextField().getText();
        String to = todatePicker2.getJFormattedTextField().getText();
        String seller = c7.getSelectedItem().toString();
        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            Statement st2 = conn.createStatement();
            Statement st3 = conn.createStatement();
            String sql = "SELECT * FROM expenses ORDER BY date DESC ";
            
              //expenses by specific seller between dates
            if (!dateCheckBox2.isSelected() && !seller.equalsIgnoreCase("ALL")) {

                ResultSet rs3 = st3.executeQuery("SELECT * FROM users WHERE username='" + seller + "' ");
                if (rs3.next()) {
                    sql = "SELECT * FROM expenses WHERE added_by =" + Integer.valueOf(rs3.getString(1)) + " AND date BETWEEN '" + from + "' AND '" + to + "' ";
                }
            }
            //specific dates but  all sellers
            if (!dateCheckBox2.isSelected() && seller.equalsIgnoreCase("ALL")) {

             sql = "SELECT * FROM expenses WHERE date >= '" + from + "' AND date <='" + to + "'";
                       
            }
             //all dates and  all sellers
            if (dateCheckBox2.isSelected() && seller.equalsIgnoreCase("ALL")) {

             sql = "SELECT * FROM expenses ";
                       
            }
            
            //all dates but specific seller
             if (dateCheckBox2.isSelected() && !seller.equalsIgnoreCase("ALL")) {

                ResultSet rs3 = st3.executeQuery("SELECT * FROM users WHERE username='" + seller + "' ");
                if (rs3.next()) {
                    sql = "SELECT * FROM expenses WHERE added_by =" + Integer.valueOf(rs3.getString(1));
                }
            }
            ResultSet rs = st.executeQuery(sql);
            DefaultTableModel model = (DefaultTableModel) expensesTable.getModel();
            String id,expense,expenditure,reciept,added_by, date;
            while (rs.next()) {
                id = rs.getString(1);
                expense = rs.getString(2);
                expenditure = rs.getString(3);
                reciept = rs.getString(4);
                //find username of user foriegn key
                ResultSet rs2 = st2.executeQuery("SELECT * FROM users WHERE id=" + Integer.parseInt(rs.getString(5)) + " ");
                if (rs2.next()) {
                    added_by = rs2.getString(2);
                } else {
                    added_by = rs.getString(5);
                }
                date = rs.getString(6);
                String[] row = {id,expense,expenditure,reciept,added_by,date};
                model.addRow(row);
            }
             model.addRow(new Object[]{"", "Sum Total", colSum(expensesTable, 2),"" , "",""});
            st.close();
            st2.close();
            conn.close();

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

    } 
        
        
        
        
    
    
    
    
    
    
   //csv upload/excel upload
    private void save() {
        FileDialog fileDialog = new FileDialog(this, "Choose CSV File", FileDialog.LOAD);
        fileDialog.setFile("*.csv");
        fileDialog.setVisible(true);

        String directory = fileDialog.getDirectory();
        String fileName = fileDialog.getFile();

        if (fileName != null) {
            uploadCsvData(directory + fileName);
        }
    }

    private void uploadCsvData(String filePath) {
        try {
            FileReader fileReader = new FileReader(filePath);
            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withHeader());

            for (CSVRecord record : csvParser) {
                String col1 = record.get("name");
                String col2 = record.get("unit");
                float col3 = Float.parseFloat(record.get("quantity"));
                float col4 = Float.parseFloat(record.get("price"));
                float col5 = Float.parseFloat(record.get("buying_price"));

                // Create SQL insert statement and execute it
                Connection connection =getConnection();
                String sql = "INSERT INTO products (name, unit, quantity, price, buying_price, min_quantity, added_by) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement state = connection.prepareStatement(sql);
                state.setString(1, col1);
                state.setString(2, col2);
                state.setFloat(3, col3);
                state.setFloat(4, col4);
                state.setFloat(5, col5);
                state.setFloat(6, 10);
                state.setInt(7, Integer.parseInt(userId));
                //check if product name exists in db
                if (searchByName(col1)) {//check if product exists
                JOptionPane.showMessageDialog(this, "Product with name " + " " + col1 + "   already exists!", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }
                state.executeUpdate();
            }

            fileReader.close();
            csvParser.close();
            JOptionPane.showMessageDialog(this, "CSV data uploaded to Derby database.", "Success", JOptionPane.INFORMATION_MESSAGE);
            displayAllProductstable();
            usersTableShow();
            allProductsTableShow();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
//download csv sample 
private void saveSampleCsv() {
        try {
            // Create a file chooser
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Sample CSV File");
            
            //Show the dialog and get the user's choice
            int userChoice = fileChooser.showSaveDialog(this);

            //Check if the user selected a file
            if (userChoice == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                // Ensure the file has a .csv extension
                String filePath = selectedFile.getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".csv")) {
                    filePath += ".csv";
                    selectedFile = new File(filePath);
                }

                // Write sample CSV content to the selected file
                try (FileWriter fileWriter = new FileWriter(selectedFile)) {
                    fileWriter.write("name,unit,quantity,price,buying_price\n");
                    fileWriter.write("chrome gin 750,ML,10,20.5,15.5\n");
                    fileWriter.write("Product2,ML,15,30.0,25.0\n");
                    fileWriter.write("Product3,ML,20,25.5,20.0\n");

                    JOptionPane.showMessageDialog(this, "Sample CSV file saved as '" + selectedFile.getName() + "'.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error saving sample CSV file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //method to get sum on catTable
    public float catSum() {
        float sum = 0;
        int rowCount = catTable.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            sum = sum + Float.parseFloat(catTable.getValueAt(i, 4).toString());
        }
        return sum;
    }

    //method to get sum on catTable
    public float restockCatSum() {
        float sum = 0;
        int rowCount = restockTable.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            sum = sum + Float.parseFloat(restockTable.getValueAt(i, 6).toString());
        }
        return sum;
    }

    //method to dispaly all data 
    public void displayAllProductstable() {

        table1.setModel(new DefaultTableModel());
        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            Statement st2 = conn.createStatement();
            String query = "SELECT * from products ORDER BY date DESC ";
            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            //table model
            DefaultTableModel model = (DefaultTableModel) table1.getModel();

            //get column count
            int cols = 5;//number of table cols

            String[] colName = new String[cols];
            //get col names from db
            for (int i = 0; i < cols; i++) {
                colName[i] = rsmd.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(colName);
            //reading rows from db
            String name, id, unit, quantity, price;
            while (rs.next()) {
                id = rs.getString(1);
                name = rs.getString(2);
                unit = rs.getString(3);
                quantity = rs.getString(4);
                price = rs.getString(5);
                String[] row = {id, name, unit, quantity, price};
                model.addRow(row);

            }
            st.close();
            st2.close();
            conn.close();

            System.out.println("DB connected!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("mySQL error");
            Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //search by id or name table
    public void displaySearchedProductstable() {
        table1.setModel(new DefaultTableModel());
//        if (searchT1.getText().equals("") || searchT1.getText().equalsIgnoreCase("search by name or id")) {
//            JOptionPane.showMessageDialog(this, "you must enter the name or id of the product!", "Error!", JOptionPane.ERROR_MESSAGE);
//            return; // Exit the method if search input is empty or has a placeholder
//        }

        try {
            Connection conn = getConnection();
            String searchItem = searchT1.getText();
            DefaultTableModel model = (DefaultTableModel) table1.getModel();
            ResultSet rs;

            if (searchItem.matches("\\d+")) { // Check if it's an id input
                String query = "SELECT * FROM products WHERE id = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, Integer.parseInt(searchItem));
                rs = preparedStatement.executeQuery();
            } else {//case sensitive
//                String query = "SELECT * FROM products WHERE name LIKE ?";
//                PreparedStatement preparedStatement = conn.prepareStatement(query);
//                preparedStatement.setString(1, "%" + searchItem + "%");
//                rs = preparedStatement.executeQuery();
                 //case insesitive
                String query = "SELECT * FROM products WHERE UPPER(name) LIKE UPPER(?)";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, "%" + searchItem.toUpperCase() + "%");
                rs = preparedStatement.executeQuery();
            }

            // Get column names from result set metadata
            ResultSetMetaData rsmd = rs.getMetaData();
            int cols = 5;
            String[] colName = new String[cols];
            for (int i = 0; i < cols; i++) {
                colName[i] = rsmd.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(colName);

            while (rs.next()) {
                String id = rs.getString(1);
                String name = rs.getString(2);
                String unit = rs.getString(3);
                String quantity = rs.getString(4);
                String price = rs.getString(5);
                String[] row = {id, name, unit, quantity, price};
                model.addRow(row);
            }

            rs.close();
            conn.close();
            System.out.println("DB connected!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("mySQL error");
            Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

  //method to search product already exists
public boolean searchByName(String sname) {
    boolean check = false;

    try {
        Connection conn = getConnection();
        Statement st = conn.createStatement();

        // Convert the input to lowercase and remove all whitespace characters
        String cleanedSname = sname.toLowerCase().replaceAll("\\s", "");

        String sql = "SELECT name FROM products";
        ResultSet result = st.executeQuery(sql);

        while (result.next()) {
            // Fetch each name from the result set
            String dbName = result.getString("name");

            // Convert the fetched name to lowercase and remove all whitespace characters
            String cleanedDbName = dbName.toLowerCase().replaceAll("\\s", "");

            // Compare the cleaned names
            if (cleanedDbName.equals(cleanedSname)) {
                check = true;
                break;
            }
        }

        st.close();
        conn.close();
    } catch (Exception e) {
        System.out.print(e.getMessage());
    }

    return check;
}

    


private void autoSearch(JComboBox<String> comboBox, String searchTerm) {
        // Select the item that starts with the entered key (case-insensitive)
        searchTerm = searchTerm.toLowerCase();
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            String item = comboBox.getItemAt(i).toLowerCase();
            if (item.startsWith(searchTerm)) {
                comboBox.setSelectedIndex(i);
                return;
            }
        }
    }


    //select products 
    public void filComb() {
        //clear selectbox
        c1.removeAllItems();

        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            String sql = "SELECT name FROM products";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                c1.addItem(rs.getString("name"));
            }

            st.close();
            conn.close();

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

    }

    //select products 
    public void filComb2() {
        c2.removeAllItems();

        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            String sql = "SELECT name FROM products";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                c2.addItem(rs.getString("name"));
            }

            st.close();
            conn.close();

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

    }

    //select sold products 
    public void filComb3() {
        c3.removeAllItems();

        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            String sql = "SELECT DISTINCT  name FROM sold_products";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                c3.addItem(rs.getString("name"));
            }

            st.close();
            conn.close();
            c3.addItem("ALL");

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

    }

    //select  product's  seller
    public void filComb4() {
        c4.removeAllItems();

        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            String sql = "SELECT DISTINCT  sold_by FROM sold_products";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                Statement st2 = conn.createStatement();
                ResultSet rs2 = st2.executeQuery("SELECT * FROM users WHERE id=" + rs.getString("sold_by"));
                if (rs2.next()) {
                    c4.addItem(rs2.getString(2));
                }
            }

            st.close();
            conn.close();
            c4.addItem("ALL");

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

    }

    //select restoced products 
    public void filComb5() {
        c5.removeAllItems();

        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            String sql = "SELECT DISTINCT  name FROM restocked_products";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                c5.addItem(rs.getString("name"));
            }

            st.close();
            conn.close();
            c5.addItem("ALL");

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

    }

    //select products restocker
    public void filComb6() {
        c6.removeAllItems();

        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            String sql = "SELECT DISTINCT  restocked_by FROM restocked_products";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                Statement st2 = conn.createStatement();
                ResultSet rs2 = st2.executeQuery("SELECT * FROM users WHERE id=" + rs.getString("restocked_by"));
                if (rs2.next()) {
                    c6.addItem(rs2.getString(2));
                }
            }

            st.close();
            conn.close();
            c6.addItem("ALL");

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

    }

  public void filComb7() {
        c7.removeAllItems();

        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            String sql = "SELECT DISTINCT  added_by FROM expenses";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                Statement st2 = conn.createStatement();
                ResultSet rs2 = st2.executeQuery("SELECT * FROM users WHERE id=" + rs.getString("added_by"));
                if (rs2.next()) {
                    c7.addItem(rs2.getString(2));
                }
            }

            st.close();
            conn.close();
            c7.addItem("ALL");

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
  }
      

  
        public void filComb8(){
        c8.removeAllItems();

        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            String sql = "SELECT DISTINCT  * FROM users";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
             c8.addItem(rs.getString(2));  
            }

            st.close();
            conn.close();
            

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

    }  
        
        
    public void filComb9() {
        c9.removeAllItems();

        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            String sql = "SELECT DISTINCT  added_by FROM disparities";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                Statement st2 = conn.createStatement();
                ResultSet rs2 = st2.executeQuery("SELECT * FROM users WHERE id=" + rs.getString("added_by"));
                if (rs2.next()) {
                    c9.addItem(rs2.getString(2));
                }
            }

            st.close();
            conn.close();
            c9.addItem("ALL");

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
  }     
    
    
    //adding product to cat table
    public void addToCat() {
        DefaultTableModel model = (DefaultTableModel) catTable.getModel();
        String name, id, unit, quantity, total;

        if (c1.getSelectedItem().toString().equalsIgnoreCase("Select Product")) {
            JOptionPane.showMessageDialog(null, "select product", "Error!", JOptionPane.ERROR_MESSAGE);
            c1.requestFocus();

        } else if (q1.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "enter quantity", "Error!", JOptionPane.ERROR_MESSAGE);
            q1.requestFocusInWindow();

        } else if (Float.parseFloat(q1.getText()) <= 0) {
            JOptionPane.showMessageDialog(null, "enter correct value of quantity", "Error!", JOptionPane.ERROR_MESSAGE);
            q1.requestFocusInWindow();

        } else {
            try {
                Connection conn = getConnection();
                Statement st = conn.createStatement();
                String select = c1.getSelectedItem().toString();
                String sql = "SELECT * FROM products WHERE name = '" + select + "'";

                ResultSet rs = st.executeQuery(sql);

                float q2 = 0;

                while (rs.next()) {
                    if (catTable.getRowCount() > 0) {
                        String id_k = rs.getString(1);//get id of product selected

                        for (int i = 0; i < catTable.getRowCount(); i++) { //check quantity of prodects of same id in catTable and sum quantity
                            if (catTable.getValueAt(i, 0).equals(id_k)) {
                                q2 = q2 + Float.parseFloat(catTable.getValueAt(i, 3).toString());
                            }
                        }

                    }
                    if (Float.parseFloat(rs.getString(4)) < q2 + Float.parseFloat(q1.getText())) {

                        JOptionPane.showMessageDialog(null, "few of this product remaining in stok \n please check quantity remaining");
                    } else if (Float.parseFloat(rs.getString(4)) >= q2) {
                        id = rs.getString(1);
                        name = rs.getString(2);
                        unit = rs.getString(3);
                        quantity = q1.getText();
                        total = Float.toString((float) (rs.getFloat(5) * Float.parseFloat(q1.getText())));

                        String[] row = {id, name, unit, quantity, total};
                        model.addRow(row);

                    }

                }

                st.close();
                conn.close();

                sumTexFld.setText(String.valueOf(catSum()));

            } catch (Exception e) {
                System.out.print(e.getMessage());
            }

        }

    }

    //adding product to cat table
    public void addToRestockTable() {

        if (c2.getSelectedItem().toString().equalsIgnoreCase("Select Product")) {
            JOptionPane.showMessageDialog(null, "select product", "Error!", JOptionPane.ERROR_MESSAGE);
            c2.requestFocus();

        } else if (q2.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "enter quantity", "Error!", JOptionPane.ERROR_MESSAGE);
            q2.requestFocusInWindow();

        } else if (Integer.parseInt(q2.getText()) <= 0) {
            JOptionPane.showMessageDialog(null, "enter correct quantity value", "Error!", JOptionPane.ERROR_MESSAGE);
            q2.requestFocusInWindow();

        } else if (p1.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "enter price ", "Error!", JOptionPane.ERROR_MESSAGE);
            p1.requestFocusInWindow();

        } 
         else if (Bp1.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "enter buying price ", "Error!", JOptionPane.ERROR_MESSAGE);
            Bp1.requestFocusInWindow();

        }
        
        else if (Float.parseFloat(p1.getText()) <= 0) {
            JOptionPane.showMessageDialog(null, "enter correct price value ", "Error!", JOptionPane.ERROR_MESSAGE);
            p1.requestFocusInWindow();
            
        }
        else if (Float.parseFloat(p1.getText()) <= Float.parseFloat(Bp1.getText())) {
            JOptionPane.showMessageDialog(null, "enter correct price value \n must be greater than buying price ", "Error!", JOptionPane.ERROR_MESSAGE);
            p1.requestFocusInWindow();
            
        }
        
        
        else {
            try {
                Connection conn = getConnection();
                Statement st = conn.createStatement();
                String select = c2.getSelectedItem().toString();
                String sql = "SELECT * FROM products WHERE name = '" + select + "'";
                ResultSet rs = st.executeQuery(sql);
                DefaultTableModel model = (DefaultTableModel) restockTable.getModel();
                String name, id, unit, quantity, price,buying_price,total_bp;

                while (rs.next()) {
                    id = rs.getString(1);
                    name = rs.getString(2);
                    unit = rs.getString(3);
                    quantity = q2.getText();
                    price = p1.getText();
                    buying_price = Bp1.getText();
                    total_bp = String.valueOf(Float.parseFloat(quantity)*Float.parseFloat(buying_price));

                    String[] row = {id, name, unit, quantity, price,buying_price,total_bp};
                    model.addRow(row);
                }
                st.close();
                conn.close();
                //to be done next
                sumTexFld1.setText(Float.toString(restockCatSum()));

            } catch (SQLException e) {
                System.out.print(e.getMessage());
            }

        }

    }

//All Products Table Show/Crud products
    public void allProductsTableShow() {
        //clears table if any
        DefaultTableModel dm = (DefaultTableModel) allProductsTable.getModel();
        dm.setRowCount(0);
        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            Statement st2 = conn.createStatement();
            String sql = "SELECT * FROM products";
            ResultSet rs = st.executeQuery(sql);
            DefaultTableModel model = (DefaultTableModel) allProductsTable.getModel();
            String id, name, unit, quantity, price,buying_price, added_by, date;
            while (rs.next()) {
                id = rs.getString(1);
                name = rs.getString(2);
                unit = rs.getString(3);
                quantity = rs.getString(4);
                price = rs.getString(5);
                //find username of user foriegn key
                ResultSet rs2 = st2.executeQuery("SELECT * FROM users WHERE id=" + Integer.parseInt(rs.getString(8)) + " ");
                if (rs2.next()) {
                    added_by = rs2.getString(2);
                } else {
                    added_by = rs.getString(8);
                }
                buying_price = rs.getString(6);
                date = rs.getString(9);
                String[] row = {id, name, unit, quantity, price,buying_price, added_by, date};
                model.addRow(row);
            }
            st.close();
            conn.close();

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

    }

// Search on CRUD products table
    public void searchCrudProductsTable() {
        
//        if (srchFld.getText().equals("") || srchFld.getText().equalsIgnoreCase("name or id")) {
//            JOptionPane.showMessageDialog(this, "You must enter the name or id of the product!", "Error!", JOptionPane.ERROR_MESSAGE);
//            return; // Exit the method if search input is empty or has a placeholder
//        }

        try {
            // get table modeel
            DefaultTableModel dm = (DefaultTableModel) allProductsTable.getModel();

            Connection conn = getConnection();
            String searchItem = srchFld.getText();
            DefaultTableModel model = (DefaultTableModel) allProductsTable.getModel();
            ResultSet rs;

            if (searchItem.matches("\\d+")) { // Check if it's an ID input
                String query = "SELECT * FROM products WHERE id = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, Integer.parseInt(searchItem));
                rs = preparedStatement.executeQuery();
            } else {
                //case insensitive
                String query = "SELECT * FROM products WHERE UPPER(name) LIKE UPPER(?)";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, "%" + searchItem.toUpperCase() + "%");
                rs = preparedStatement.executeQuery();
            }

            if (rs.next()) {
                //clear table first if search is found
                dm.setRowCount(0);
                String id, name, unit, quantity, price,buying_price, added_by, date;
                do {
                    id = rs.getString(1);
                    name = rs.getString(2);
                    unit = rs.getString(3);
                    quantity = rs.getString(4);
                    price = rs.getString(5);
                    buying_price =rs.getString(6);
                    ResultSet rs2 = conn.createStatement().executeQuery("SELECT * FROM users WHERE id=" + Integer.valueOf(rs.getString(8)));
                    if (rs2.next()) {
                        added_by = rs2.getString(2);
                    } else {
                        added_by = rs.getString(8);
                    }

                    date = rs.getString(9);
                    String[] row = {id, name, unit, quantity, price,buying_price, added_by, date};
                    model.addRow(row);
                } while (rs.next());
            } 
//            else {
//                JOptionPane.showMessageDialog(this, "Search item not found!");
//            }

            conn.close();
            System.out.println("DB connected!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("mySQL error");
            Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //sell products
    public void sellProduct() {

        DefaultTableModel model = (DefaultTableModel) catTable.getModel();
        int row_count = model.getRowCount();
        if (row_count < 1) {
            JOptionPane.showMessageDialog(null, "There's no product added to cat!",
                    "Error!", JOptionPane.ERROR_MESSAGE);

        } else {
            try {
                //confirm
                JDialog.setDefaultLookAndFeelDecorated(true);
                int response = JOptionPane.showConfirmDialog(null, "Do you want to sell products?", "Confirm",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    Connection conn = getConnection();

                    PreparedStatement insert = conn.prepareStatement("insert into sold_products (name,unit,quantity,total_ksh,profit,sold_by)values(?,?,?,?,?,?)");
                    for (int i = 0; i < catTable.getRowCount(); i++) {
                        insert.setString(1, model.getValueAt(i, 1).toString());
                        insert.setString(2, model.getValueAt(i, 2).toString());
                        insert.setFloat(3, Float.parseFloat(model.getValueAt(i, 3).toString()));
                        insert.setFloat(4, Float.parseFloat(model.getValueAt(i, 4).toString()));
                        insert.setInt(6, Integer.parseInt(userId));
                        //calculate profit
                        Statement st1 = conn.createStatement();
                        String sql1 = "SELECT buying_price,price FROM products WHERE id = " + model.getValueAt(i, 0);
                        ResultSet rs1 = st1.executeQuery(sql1);
                        if(rs1.next()){
                         float buyP = rs1.getFloat("buying_price");
                         float Price = rs1.getFloat("price");
                         float profit = (Price-buyP)*Float.parseFloat(model.getValueAt(i, 3).toString());
                         insert.setFloat(5, profit);
                        }
                        st1.close();
                        
                        insert.executeUpdate();
                        //search for product being sold
                        Statement st = conn.createStatement();
                        String sql = "SELECT * FROM products WHERE id = " + model.getValueAt(i, 0);
                        ResultSet rs = st.executeQuery(sql);
                        //update quantity of the product
                        if (rs.next()) {
                            float q = Float.parseFloat(rs.getString(4)) - Float.parseFloat(model.getValueAt(i, 3).toString());
                            //String q1 = String.valueOf(q);
                            String sql2 = "UPDATE products set quantity=" + q + " WHERE id =" + model.getValueAt(i, 0).toString();
                            st.executeUpdate(sql2);
                        }
                        st.close();

                    }
                    JOptionPane.showMessageDialog(this, "transaction succesful!");
                    //confirm recipt
                    JDialog.setDefaultLookAndFeelDecorated(true);
                    int response1 = JOptionPane.showConfirmDialog(null, "Do you want to print a Reciept?", "Confirm",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (response1 == JOptionPane.YES_OPTION) {
                        model.addRow(new Object[]{"", "", "", "Sum Total", colSum(catTable, 4), "", ""});
                        printReceipt(catTable);
                    }
                    conn.close();
                    model.setRowCount(0);
                    displayAllProductstable();
                    allProductsTableShow();
                    //clear payment fields
                    sumTexFld.setText("");
                    balanceTexFld.setText("");
                    paidTexFld.setText("");

                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }

    }

//restock products
    public void restockProducts() {
       
        DefaultTableModel model = (DefaultTableModel) restockTable.getModel();
        int row_count = model.getRowCount();
        if (row_count < 1) {
            JOptionPane.showMessageDialog(null, "There's no product added yet",
                    "Error!", JOptionPane.ERROR_MESSAGE);

        } else {
           
            try {
                Connection conn = getConnection();
                PreparedStatement insert = conn.prepareStatement("insert into restocked_products (name,unit,quantity,price,buying_price,restocked_by,invoice_no)values(?,?,?,?,?,?,?)");
                invoice_no = JOptionPane.showInputDialog("Enter Invoice Number:");
                for (int i = 0; i < model.getRowCount(); i++) {
                    //insert.setInt(1,0);//auto gen in db
                    insert.setString(1, model.getValueAt(i, 1).toString());
                    insert.setString(2, model.getValueAt(i, 2).toString());
                    insert.setFloat(3, Float.parseFloat(model.getValueAt(i, 3).toString()));
                    insert.setFloat(4, Float.parseFloat(model.getValueAt(i, 4).toString()));
                    insert.setFloat(5, Float.parseFloat(model.getValueAt(i, 5).toString()));
                    insert.setInt(6, Integer.parseInt(userId));
                     //to add invoice number here !
                    if(invoice_no==null){
                        JOptionPane.showMessageDialog(null, "Provide invoice number!",
                    "Error!", JOptionPane.ERROR_MESSAGE);
                    return ;
                    }
                    insert.setString(7, invoice_no);
                    insert.executeUpdate();
                    //search for product being restocked
                    Statement st = conn.createStatement();
                    String sql = "SELECT * FROM products WHERE id = " + model.getValueAt(i, 0);

                    ResultSet rs = st.executeQuery(sql);
                    //update quantity of the product
                    if (rs.next()) {
                        float q = Float.parseFloat(rs.getString(4)) + Integer.parseInt(model.getValueAt(i, 3).toString());
                        float pr = Float.parseFloat(model.getValueAt(i, 4).toString());
                        float Bpr = Float.parseFloat(model.getValueAt(i, 5).toString());
                        String sql1 = "UPDATE products set quantity=" + q + ", price=" + pr + ", buying_price=" + Bpr + " WHERE id =" + model.getValueAt(i, 0).toString();
                        st.executeUpdate(sql1);
                    }

                    st.close();
                }
                JOptionPane.showMessageDialog(this, "transaction succesful!");
                model.setRowCount(0);
                displayAllProductstable();
                allProductsTableShow();
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }

    }

//method to search user already exists
    public boolean searchByUserName(String sname) {
        boolean check = false;

        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            String sql = "SELECT username FROM users WHERE username = '" + sname + "'";
            ResultSet result = st.executeQuery(sql);
            if (result.next()) {
                check = true;
            }
            st.close();
            conn.close();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        return check;

    }

    public void createUser() {
        String user = userName.getText();
        String p = String.valueOf(passwordP.getPassword());
        String c = roleC.getSelectedItem().toString();
        if (userName.getText().equalsIgnoreCase("") || String.valueOf(passwordP.getPassword()).equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "username and password required!", "Error!", JOptionPane.ERROR_MESSAGE);
        } else if (user.indexOf(' ') != -1) {
            JOptionPane.showMessageDialog(this, "username must be one word!", "Error!", JOptionPane.ERROR_MESSAGE);
            userName.requestFocus();
        } else if (p.length() < 4) {
            JOptionPane.showMessageDialog(this, "password must be at least 4 characters!", "Error!", JOptionPane.ERROR_MESSAGE);
            passwordP.requestFocus();
        } else if (searchByUserName(user)) {//check if product exists
            JOptionPane.showMessageDialog(this, "user with username " + " " + user + "   already exists!", "Error!", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                Connection conn = getConnection();
                statement = conn.createStatement();
                PreparedStatement insert = conn.prepareStatement("insert into users (username,password,role)values(?,?,?)");
                //insert.setInt(1,0);//auto gen in db
                insert.setString(1, user);
                insert.setString(2, p);
                insert.setString(3, c);
                insert.executeUpdate();
                JOptionPane.showMessageDialog(this, "User Successfuly Created!");

                //clear fields
                userName.setText("");
                passwordP.setText("");

                //displayAllProductstable();
                System.out.print("Database Connected");
                statement.close();
                conn.close();
            } catch (SQLException ex) {

                System.out.print(ex.getMessage());
            }

        }

    }

//users manage table
    public void usersTableShow() {
        //clears table if any
        DefaultTableModel dm = (DefaultTableModel) usersTable.getModel();
        dm.setRowCount(0);
        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            String sql = "SELECT * FROM users";
            ResultSet rs = st.executeQuery(sql);
            DefaultTableModel model = (DefaultTableModel) usersTable.getModel();
            String id, username, role, password;
            while (rs.next()) {
                id = rs.getString(1);
                username = rs.getString(2);
                role = rs.getString(4);
                password = rs.getString(3);
                String[] row = {id, username, role, password};
                model.addRow(row);
            }
            st.close();
            conn.close();

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

    }
//update user details
    public boolean roleValidator(String role) {
        boolean check = false;
        String[] roles = {"admin", "normal"};
        for (int i = 0; i < roles.length; i++) {
            if (role.equals(roles[i])) {
                check = true;
                break;
            }
        }
        return check;
    }

    // Method to print the JTable
    private static void printTable(JTable table) {
        JDialog.setDefaultLookAndFeelDecorated(true);
        TableModel model = table.getModel();
        if(model.getRowCount()==0){
            JOptionPane.showMessageDialog(null, "empty table,load data!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        else{
            try {
        // Create a PrinterJob
        PrinterJob job = PrinterJob.getPrinterJob();

        // Set a printable object to handle the printing
        job.setPrintable(new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                if (pageIndex > 0) {
                    return Printable.NO_SUCH_PAGE;
                }

                Graphics2D g2d = (Graphics2D) graphics;
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

                // Calculate the total width of the table
                int tableWidth = table.getColumnModel().getTotalColumnWidth();
                int tableHeight = table.getHeight();

                // Calculate the scaling factor to fit the table within the page bounds
                double scaleX = pageFormat.getImageableWidth() / tableWidth;
                double scaleY = pageFormat.getImageableHeight() / tableHeight;
                double scale = Math.min(scaleX, scaleY);

                // Apply scaling to the graphics context
                g2d.scale(scale, scale);

                // Print table headers
                JTableHeader header = table.getTableHeader();
                header.paint(g2d);

                // Translate the origin to below the headers
                g2d.translate(0, header.getHeight());

                // Print the JTable
                table.paint(g2d);

                // Add footer text
                g2d.translate(0, tableHeight);
                g2d.drawString("Printed by " + logUser + " on " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date()), 20, 10);
                return Printable.PAGE_EXISTS;
            }
        });

        // Show the print dialog to choose printer settings
        if (job.printDialog()) {
            job.print();
        }
    } catch (PrinterException e) {
        e.printStackTrace();
    }
        
        }
}


 private  void printReceipt(JTable table) {
    try {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setPrintable((graphics, pageFormat, pageIndex) -> {
            if (pageIndex > 0) {
                return Printable.NO_SUCH_PAGE;
            }

            Graphics2D g2d = (Graphics2D) graphics;
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

            // Set the business name as the title
            String businessName = "["+ recieptTitle +"]";
            Font titleFont = new Font("Arial", Font.BOLD, 14);
            g2d.setFont(titleFont);
            g2d.drawString(businessName, 20, 20);

            // Set headers
            String headers = String.format("%-20s %-10s %-10s", "Product", "Quantity", "Total Ksh");
            Font headerFont = new Font("Arial", Font.PLAIN, 10);
            g2d.setFont(headerFont);
            g2d.drawString(headers, 20, 40);

            // Assuming column indices for Name, Quantity, and Total Ksh are 1, 3, and 4
            int nameColumn = 1;
            int quantityColumn = 3;
            int totalKshColumn = 4;

            int rowHeight = table.getRowHeight();
            int yPosition = 60;

            for (int row = 0; row < table.getRowCount(); row++) {
                String name = table.getValueAt(row, nameColumn).toString();
                String quantity = table.getValueAt(row, quantityColumn).toString();
                String totalKsh = table.getValueAt(row, totalKshColumn).toString();

                String line = String.format("%-20s %-10s %-10s", name, quantity, totalKsh);
                g2d.drawString(line, 20, yPosition);
                yPosition += rowHeight;
            }

            // Set the footer with seller name and date
            String sellerName = "["+logUser+"]";
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = dateFormat.format(new Date());
            String footer = String.format("Sold by %s on %s", sellerName, currentDate);
            g2d.drawString(footer, 20, yPosition);

            return Printable.PAGE_EXISTS;
        });

        if (printerJob.printDialog()) {
            printerJob.print();
        }
    } catch (PrinterException e) {
        e.printStackTrace();
    }
}


    // Export the JTable to an Excel file
    public void exportTableToExcel(JTable table) throws FileNotFoundException, IOException {
        
      //check if table is empty
      if(table.getModel().getRowCount() == 0){
       JOptionPane.showMessageDialog(null, "Error table is empty!.", "Error", JOptionPane.ERROR_MESSAGE);
       return;
      }
        
      JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Excel File");

        // Create a file filter for Excel files
        FileNameExtensionFilter excelFilter = new FileNameExtensionFilter("Excel Files", "xlsx");
        fileChooser.setFileFilter(excelFilter);

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();

            // Ensure the full file path includes the .xlsx extension
            if (!filePath.toLowerCase().endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            // Check for the existence of an existing file and delete it
            File existingFile = new File(filePath);
            if (existingFile.exists() && !existingFile.delete()) {
                JOptionPane.showMessageDialog(null, "Error deleting the existing file.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Table Data");

                // Create a header row
                Row headerRow = sheet.createRow(0);
                for (int col = 0; col < table.getColumnCount(); col++) {
                    Cell cell = headerRow.createCell(col);
                    cell.setCellValue(table.getColumnName(col));
                }

                // Create data rows
                for (int row = 0; row < table.getRowCount(); row++) {
                    Row excelRow = sheet.createRow(row + 1); // Add 1 to account for the header row

                    for (int col = 0; col < table.getColumnCount(); col++) {
                        Object value = table.getValueAt(row, col);
                        Cell cell = excelRow.createCell(col);

                        if (value instanceof String) {
                            cell.setCellValue((String) value);
                        } else if (value instanceof Number) {
                            cell.setCellValue(((Number) value).doubleValue());
                        }
                        // You can add more data type handling as needed
                    }
                }

                workbook.write(fos);
                JOptionPane.showMessageDialog(null, "Table data exported to Excel successfully.");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error exporting data to Excel: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }



    public void upDateUser() {
        JDialog.setDefaultLookAndFeelDecorated(true);
        if (editUserName.getText().equalsIgnoreCase("") || editUserRole.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "select user and click edit first!", "Error!", JOptionPane.ERROR_MESSAGE);

        } else if (!roleValidator(editUserRole.getText())) {
            JOptionPane.showMessageDialog(this, "role must either be normal or admin", "Error!", JOptionPane.ERROR_MESSAGE);
        } else {

            try {
                Connection conn = getConnection();
                Statement st = conn.createStatement();
                String sql = "UPDATE users set username='" + editUserName.getText() + "', password='" + String.valueOf(editUserPassword.getPassword()) + "', role='" + editUserRole.getText() + "'  WHERE id =" + Integer.valueOf(usersTable.getValueAt(usersTable.getSelectedRow(), 0).toString());
                st.executeUpdate(sql);
                JOptionPane.showMessageDialog(this, "User Successfuly Updated!");
                editUserName.setText("");
                editUserPassword.setText("");
                editUserRole.setText("");
                usersTableShow();

            } catch (Exception e) {

                JOptionPane.showMessageDialog(this, "user not updated!", "Error!", JOptionPane.ERROR_MESSAGE);
                System.out.println(e.getMessage());
            }

        }

    }

    //calculate sum of a given column of Jtable
    private float colSum(JTable table, int n) {
        float total = 0;
        for (int i = 0; i < table.getRowCount(); i++) {
            try {
                double value = Double.parseDouble(table.getValueAt(i, n).toString());
                total += (int) value; // Convert the double to an integer if needed
            } catch (NumberFormatException e) {
                // Handle invalid number format gracefully
                e.printStackTrace();
            }
        }
        return total;
    }

    //sales table report
    public void salesTableReport() {
        //clears table if any
        DefaultTableModel dm = (DefaultTableModel) salesTable.getModel();
        dm.setRowCount(0);
        //date variables
        String from = fromdatePicker.getJFormattedTextField().getText();
        String to = todatePicker.getJFormattedTextField().getText();
        String product = c3.getSelectedItem().toString();
        String seller = c4.getSelectedItem().toString();
        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            Statement st2 = conn.createStatement();
            Statement st3 = conn.createStatement();

            String sql = "SELECT * FROM sold_products WHERE date >= '" + from + "' AND date <='" + to + "'";
            if (!dateCheckBox.isSelected() && product.equalsIgnoreCase("ALL")) {
                sql = "SELECT * FROM sold_products WHERE date BETWEEN '" + from + "' AND '" + to + "' ";
            }

            //all products sold by specific user between dates
            if (!dateCheckBox.isSelected() && product.equalsIgnoreCase("ALL") && !seller.equalsIgnoreCase("ALL")) {

                ResultSet rs3 = st3.executeQuery("SELECT * FROM users WHERE username='" + seller + "' ");
                if (rs3.next()) {
                    sql = "SELECT * FROM sold_products WHERE sold_by =" + Integer.valueOf(rs3.getString(1)) + " AND date BETWEEN '" + from + "' AND '" + to + "' ";
                }
            }

            //specific products sold by specific user between dates
            if (!dateCheckBox.isSelected() && !product.equalsIgnoreCase("ALL") && !seller.equalsIgnoreCase("ALL")) {

                ResultSet rs3 = st3.executeQuery("SELECT * FROM users WHERE username='" + seller + "' ");
                if (rs3.next()) {
                    sql = "SELECT * FROM sold_products WHERE name='" + product + "' AND sold_by =" + Integer.valueOf(rs3.getString(1)) + " AND date BETWEEN '" + from + "' AND '" + to + "' ";
                }
            }

            if (!dateCheckBox.isSelected() && !product.equalsIgnoreCase("ALL") && seller.equalsIgnoreCase("ALL")) {
                sql = "SELECT * FROM sold_products WHERE name ='" + product + "' AND date BETWEEN '" + from + "' AND '" + to + "' ";
            }

            //all
            //all products sold by specific user:no time
            if ((dateCheckBox.isSelected()) && (product.equalsIgnoreCase("ALL")) && (!seller.equalsIgnoreCase("ALL"))) {

                ResultSet rs3 = st3.executeQuery("SELECT * FROM users WHERE username='" + seller + "' ");
                if (rs3.next()) {
                    sql = "SELECT * FROM sold_products WHERE sold_by=" + Integer.valueOf(rs3.getString(1));
                }
            }

            //specific products sold by specific user:no time
            if ((dateCheckBox.isSelected()) && (!product.equalsIgnoreCase("ALL")) && (!seller.equalsIgnoreCase("ALL"))) {

                ResultSet rs3 = st3.executeQuery("SELECT * FROM users WHERE username='" + seller + "' ");
                if (rs3.next()) {
                    sql = "SELECT * FROM sold_products WHERE name='" + product + "' AND sold_by=" + Integer.valueOf(rs3.getString(1));
                }
            }

            if (dateCheckBox.isSelected() && product.equalsIgnoreCase("ALL") && seller.equalsIgnoreCase("ALL")) {
                sql = "SELECT * FROM sold_products ";//show all sales for all products
            }

            if (dateCheckBox.isSelected() && !product.equalsIgnoreCase("ALL") && seller.equalsIgnoreCase("ALL")) {
                sql = "SELECT * FROM sold_products WHERE name = '" + product + "' ";//show all sales for a specific product
            }

            ResultSet rs = st.executeQuery(sql);
            DefaultTableModel model = (DefaultTableModel) salesTable.getModel();
            String id, name, unit, quantity, total_ksh,profit, sold_by, date;
            while (rs.next()) {
                id = rs.getString(1);
                name = rs.getString(2);
                unit = rs.getString(3);
                quantity = rs.getString(4);
                total_ksh = rs.getString(5);
                profit = rs.getString(6);
                //find username of user foriegn key
                ResultSet rs2 = st2.executeQuery("SELECT * FROM users WHERE id=" + Integer.valueOf(rs.getString(7)));
                if (rs2.next()) {
                    sold_by = rs2.getString(2);
                } else {
                    sold_by = rs.getString(7);
                }
                date = rs.getString(8);
                String[] row = {id, name, unit, quantity, total_ksh,profit, sold_by, date};
                model.addRow(row);

            }
            model.addRow(new Object[]{"", "", "", "Sum Total", colSum(salesTable, 4), colSum(salesTable, 5), ""});
            st.close();
            conn.close();

        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }

    }

    //restocking history/ report
    public void restockTableReport() {
        //clears table if any
        DefaultTableModel dm = (DefaultTableModel) restockReportTable.getModel();
        dm.setRowCount(0);
        //date variables
        String from = fromdatePicker1.getJFormattedTextField().getText();
        String to = todatePicker1.getJFormattedTextField().getText();
        String product = c5.getSelectedItem().toString();
        String seller = c6.getSelectedItem().toString();
        //checke if invoiceTextFld is empty but checked;
        if(invoiceCheckBox1.isSelected() && invoiceTextFld.getText().isEmpty()){
             JOptionPane.showMessageDialog(null, "Provide invoice number!",
                    "Error!", JOptionPane.ERROR_MESSAGE);
                invoiceTextFld.requestFocus();
                    return ;
                    
        }
        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            Statement st2 = conn.createStatement();
            Statement st3 = conn.createStatement();

            String sql = "SELECT * FROM restocked_products WHERE date >= '" + from + "' AND date <='" + to + "'";
            if (!dateCheckBox1.isSelected() && !invoiceCheckBox1.isSelected() && product.equalsIgnoreCase("ALL")) {
                sql = "SELECT * FROM restocked_products WHERE date BETWEEN '" + from + "' AND '" + to + "' ";
            }

            //all products sold by specific user between dates
            if (!dateCheckBox1.isSelected()&& !invoiceCheckBox1.isSelected() && product.equalsIgnoreCase("ALL") && !seller.equalsIgnoreCase("ALL")) {

                ResultSet rs3 = st3.executeQuery("SELECT * FROM users WHERE username='" + seller + "' ");
                if (rs3.next()) {
                    sql = "SELECT * FROM restocked_products WHERE restocked_by =" + Integer.valueOf(rs3.getString(1)) + " AND date BETWEEN '" + from + "' AND '" + to + "' ";
                }
            }

            //specific products sold by specific user between dates
            if (!dateCheckBox1.isSelected() && !invoiceCheckBox1.isSelected() && !product.equalsIgnoreCase("ALL") && !seller.equalsIgnoreCase("ALL")) {

                ResultSet rs3 = st3.executeQuery("SELECT * FROM users WHERE username='" + seller + "' ");
                if (rs3.next()) {
                    sql = "SELECT * FROM restocked_products WHERE name='" + product + "' AND restocked_by =" + Integer.valueOf(rs3.getString(1)) + " AND date BETWEEN '" + from + "' AND '" + to + "' ";
                }
            }

            if (!dateCheckBox1.isSelected() && !invoiceCheckBox1.isSelected() && !product.equalsIgnoreCase("ALL") && seller.equalsIgnoreCase("ALL")) {
                sql = "SELECT * FROM restocked_products WHERE name ='" + product + "' AND date BETWEEN '" + from + "' AND '" + to + "' ";
            }

            //all
            //all products restocked by specific user:no time
            if ((dateCheckBox1.isSelected()) && !invoiceCheckBox1.isSelected() && (product.equalsIgnoreCase("ALL")) && (!seller.equalsIgnoreCase("ALL"))) {

                ResultSet rs3 = st3.executeQuery("SELECT * FROM users WHERE username='" + seller + "' ");
                if (rs3.next()) {
                    sql = "SELECT * FROM restocked_products WHERE restocked_by=" + Integer.valueOf(rs3.getString(1));
                }
            }

            //specific products sold by specific user:no time
            if ((dateCheckBox1.isSelected()) && !invoiceCheckBox1.isSelected() && (!product.equalsIgnoreCase("ALL")) && (!seller.equalsIgnoreCase("ALL"))) {

                ResultSet rs3 = st3.executeQuery("SELECT * FROM users WHERE username='" + seller + "' ");
                if (rs3.next()) {
                    sql = "SELECT * FROM restocked_products WHERE name='" + product + "' AND restocked_by=" + Integer.valueOf(rs3.getString(1));
                }
            }

            if (dateCheckBox1.isSelected() && !invoiceCheckBox1.isSelected() && product.equalsIgnoreCase("ALL") && seller.equalsIgnoreCase("ALL")) {
                sql = "SELECT * FROM restocked_products ";//show all sales for all products
            }

            if (dateCheckBox1.isSelected() && !invoiceCheckBox1.isSelected() && !product.equalsIgnoreCase("ALL") && seller.equalsIgnoreCase("ALL")) {
                sql = "SELECT * FROM restocked_products WHERE name = '" + product + "' ";//show all sales for a specific product
            }
            //search by invoice number
             if (invoiceCheckBox1.isSelected() && product.equalsIgnoreCase("ALL") ) {
                sql = "SELECT * FROM restocked_products WHERE invoice_no = '" + invoiceTextFld.getText() + "' ";
            }
             
           //search by invoice number and specific product
             if (invoiceCheckBox1.isSelected() && !product.equalsIgnoreCase("ALL") ) {
                 sql = "SELECT * FROM restocked_products WHERE name='" + product + "' AND invoice_no='" + invoiceTextFld.getText() + "'";
                
            }  
             
            ResultSet rs = st.executeQuery(sql);
            DefaultTableModel model = (DefaultTableModel) restockReportTable.getModel();
            String id, name, unit, quantity, price,buying_price,total_bp, restocked_by,invoice, date;
            while (rs.next()) {
                id = rs.getString(1);
                name = rs.getString(2);
                unit = rs.getString(3);
                quantity = rs.getString(4);
                price = rs.getString(5);
                buying_price = rs.getString(6);
                total_bp = String.valueOf(Float.parseFloat(quantity)*Float.parseFloat(buying_price));
                //find username of user foriegn key
                ResultSet rs2 = st2.executeQuery("SELECT * FROM users WHERE id=" + Integer.valueOf(rs.getString(7)));
                if (rs2.next()) {
                    restocked_by = rs2.getString(2);
                } else {
                    restocked_by = rs.getString(7);
                }
                invoice = rs.getString(8);
                date = rs.getString(9);
                
                String[] row = {id, name, unit, quantity, price,buying_price,total_bp, restocked_by,invoice, date};
                model.addRow(row);
                
            }
            model.addRow(new Object[]{"", "", "","","","Sum Total", colSum(restockReportTable, 6), "","", ""});
            st.close();
            conn.close();

        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }

    }

    //LOW Stock Reminders
    //get all products with their quantity
    public void loadInventoryFromDatabase() {
        try {
            Connection connection = getConnection();
            Statement st = connection.createStatement();
            String query = "SELECT name, quantity, min_quantity FROM products";
            ResultSet resultSet = st.executeQuery(query);
            StringBuilder lowStockMessage = new StringBuilder("Low stock alert:\n");

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int quantity = resultSet.getInt("quantity");
                int min_quantity = resultSet.getInt("min_quantity");
                inventory.put(name, quantity);
                checkAndAlertLowStock(name, quantity, min_quantity, lowStockMessage);
                // Display a single dialog with information about all products with low stock
                if (lowStockMessage.length() > 17) { // Check if the message contains more than "Low stock alert:\n"
                    alertLowStock(lowStockMessage.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//check and alert low stock
    public void checkAndAlertLowStock(String productName, int quantity, int minQuantity, StringBuilder messageBuilder) {
        if (quantity < minQuantity) {
            messageBuilder.append(productName).append(" (Quantity: ").append(quantity).append(", Min Quantity: ").append(minQuantity).append("\n");
        }
    }

//alert on low stosk
    public void alertLowStock(String message) {
        // Create a custom dialog with a timeout to automatically close it after 4 seconds
        JDialog dialog = new JDialog();
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea(message);

        // Center-align the text within the JTextArea
        textArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        textArea.setAlignmentY(Component.CENTER_ALIGNMENT);

        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setOpaque(false);
        textArea.setEditable(false);
        panel.add(textArea, BorderLayout.CENTER);
        dialog.getContentPane().add(panel);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setPreferredSize(new Dimension(400, 200)); // Adjust dimensions as needed
        dialog.pack();
        dialog.setLocationRelativeTo(null);  // Center the dialog
        dialog.setTitle("Low Stock Alert");
        dialog.setVisible(true);

        Timer dismissTimer = new Timer();
        dismissTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                dialog.dispose();
            }
        }, 6000); // 6 seconds in milliseconds
    }

    //action listeners
    @Override
    public void actionPerformed(ActionEvent e) {
        JDialog.setDefaultLookAndFeelDecorated(true);

        //add new products
        if (e.getSource() == addProdButton) {

            if (productName.getText().equals("")
                    || productQuantity.getText().equals("") || productUnit.getText().equals("")
                    || productPrice.getText().equals("") || buyingPrice.getText().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this, "Please enter all fields!", "Error!", JOptionPane.ERROR_MESSAGE);
            } else if (Integer.parseInt(productQuantity.getText()) <= 0) {
                JOptionPane.showMessageDialog(this, "quantity must be more than zero!", "Error!", JOptionPane.ERROR_MESSAGE);
            } else if (Float.parseFloat(productPrice.getText()) <= 0) {
                JOptionPane.showMessageDialog(this, "price must be more than zero!", "Error!", JOptionPane.ERROR_MESSAGE);
            } else if (searchByName(productName.getText())) {//check if product exists
                JOptionPane.showMessageDialog(this, "Product with name " + " " + productName.getText() + "   already exists!", "Error!", JOptionPane.ERROR_MESSAGE);
            }
            else if (Float.parseFloat(buyingPrice.getText())>Float.parseFloat(productPrice.getText())) {//validate buying and price wrong entry
                JOptionPane.showMessageDialog(this, "Price must be greater than buying price, please check ", "Error!", JOptionPane.ERROR_MESSAGE);
            } 
            else {
                //vriables
                String name = productName.getText();
                //int  id =Integer.parseInt(productId.getText());
                String unit = productUnit.getText();
                float quantity = Float.parseFloat(productQuantity.getText());
                float price = Float.parseFloat(productPrice.getText());
                float minQ = Float.parseFloat(minQuantity.getText());
                float bPrice = Float.parseFloat(buyingPrice.getText());

                try {
                    Connection conn = getConnection();
                    statement = conn.createStatement();
                    PreparedStatement insert = conn.prepareStatement("insert into products (name,unit,quantity,price,buying_price,min_quantity,added_by)values(?,?,?,?,?,?,?)");
//          insert.setInt(1,0);//auto gen in db
                    insert.setString(1, name);
                    insert.setString(2, unit);
                    insert.setFloat(3, quantity);
                    insert.setFloat(4, price);
                    insert.setFloat(5, bPrice);
                    insert.setFloat(6, minQ);
                    insert.setInt(7, Integer.parseInt(userId));
                    insert.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Product Successfuly Added");

                    //clear fields
                    productName.setText("");
                    //productId.setText("");
                    productUnit.setText("ML");
                    productQuantity.setText("");
                    productPrice.setText("");
                    minQuantity.setText("");
                    buyingPrice.setText("");
                    
                    displayAllProductstable();
                    allProductsTableShow();

                    System.out.print("Database Connected");
                    statement.close();
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println("Db not connected!");
                    System.out.print(ex.getMessage());
                }
            }

        }

        //display allproducts
        if (e.getSource() == displayButton1) {
            displayAllProductstable();
        }

        //clear products 
        if (e.getSource() == clearButton1) {
            table1.setModel(new DefaultTableModel());
        }

        //dispaya only seached products
        if (e.getSource() == searchB1) {
            displaySearchedProductstable();
        }

        //add product to cat
        if (e.getSource() == addB1) {

            addToCat();
        }

        if (e.getSource() == sellB1) {
            sellProduct();
        }
        if (e.getSource() == loadAllB) {
            allProductsTableShow();
        }

        if (e.getSource() == searchAllB) {
            searchCrudProductsTable();
        }

        if (e.getSource() == editProductB) {
            //check product not picked
            if (allProductsTable.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(null, "you must select product", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                //read selected data
                editName.setText(allProductsTable.getValueAt(allProductsTable.getSelectedRow(), 1).toString());
                editUnit.setText(allProductsTable.getValueAt(allProductsTable.getSelectedRow(), 2).toString());
                editQuantity.setText(allProductsTable.getValueAt(allProductsTable.getSelectedRow(), 3).toString());
                editPrice.setText(allProductsTable.getValueAt(allProductsTable.getSelectedRow(), 4).toString());

                //get min 
                try {
                    Connection connection = getConnection();
                    Statement st = connection.createStatement();
                    String query = "SELECT  min_quantity,buying_price FROM products WHERE id=" + allProductsTable.getValueAt(allProductsTable.getSelectedRow(), 0);
                    ResultSet resultSet = st.executeQuery(query);

                    while (resultSet.next()) {
                        String minQ = resultSet.getString("min_quantity");
                        String bPrice = resultSet.getString("buying_price");
                        editMinQ.setText(minQ);
                        editBuyingPrice.setText(bPrice);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

        }

        if (e.getSource() == saveB) {

            if (editName.getText().equals("") || editUnit.getText().equals("") || editQuantity.getText().equals("")
                    || editPrice.getText().equals("") || allProductsTable.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(null, "press edit button", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    float quantity = Float.parseFloat(editQuantity.getText());
                    float min_quantity = Float.parseFloat(editMinQ.getText());
                    float price = Float.parseFloat(editPrice.getText());
                    float bPrice = Float.parseFloat(editBuyingPrice.getText());
                    int id = Integer.parseInt(allProductsTable.getValueAt(allProductsTable.getSelectedRow(), 0).toString());
                    Connection conn = getConnection();
                    String sql = "UPDATE products set name='" + editName.getText() + "', unit='" + editUnit.getText() + "', quantity=" + quantity + ",  price=" + price + ",min_quantity=" + min_quantity + ",buying_price="+bPrice+"   WHERE id =" + id;
                    Statement st = conn.createStatement();
                    st.executeUpdate(sql);
                    editName.setText("");
                    editUnit.setText("");
                    editQuantity.setText("");
                    editPrice.setText("");
                    editMinQ.setText("");
                    editBuyingPrice.setText("");
                    allProductsTableShow();//reload the table
                    displayAllProductstable();

                    JOptionPane.showMessageDialog(this, "Product Updated Succesfuly");
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }

            }

        }
        
        
        

        if (e.getSource() == deleteB) {
            if (allProductsTable.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(this, "select product", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    Connection conn = getConnection();
                    Statement st = conn.createStatement();
                    int id = Integer.parseInt(allProductsTable.getValueAt(allProductsTable.getSelectedRow(), 0).toString());
                    String sql = "DELETE FROM products WHERE id = " + id;
                    String name = allProductsTable.getValueAt(allProductsTable.getSelectedRow(), 1).toString();
                    //confirm
                    JDialog.setDefaultLookAndFeelDecorated(true);
                    int response = JOptionPane.showConfirmDialog(null, "Do you want to delete " + name + " ?", "Confirm",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (response == JOptionPane.YES_OPTION) {
                        st.executeUpdate(sql);
                        JOptionPane.showMessageDialog(this, " " + name + " deleted ");
                        allProductsTableShow();
                        displayAllProductstable();

                    }

                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }

            }

        }
        
        // edit exp
         if (e.getSource() == expEditButton) {
            //check item not picked
            if (expensesTable.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(null, "you must select a row", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                //read selected data
                editExpense.setText(expensesTable.getValueAt(expensesTable.getSelectedRow(), 1).toString());
                editExpenditure.setText(expensesTable.getValueAt(expensesTable.getSelectedRow(), 2).toString());
                editRecieptN.setText(expensesTable.getValueAt(expensesTable.getSelectedRow(), 3).toString());
                editExpenseDate.setText(expensesTable.getValueAt(expensesTable.getSelectedRow(), 5).toString());
            }

        }
         
         
         if (e.getSource() ==  editExpenseButton) {

            if (editExpense.getText().trim().isEmpty() || editExpenditure.getText().trim().isEmpty() || editRecieptN.getText().trim().isEmpty()
                    || editExpenseDate.getText().trim().isEmpty() || expensesTable.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(null, "press edit button", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    int id = Integer.parseInt(expensesTable.getValueAt(expensesTable.getSelectedRow(), 0).toString());
                    Connection conn = getConnection();
                    //String sql = "UPDATE expenses set expense='" + editExpense.getText() + "', expenditure='" + editExpenditure.getText() + "', reciept_no=" + editRecieptN.getText()+ ",  date=" + editExpenseDate.getText() + ",  WHERE id =" + id;
                    //String sql = "UPDATE expenses SET expense='" + editExpense.getText() + "', expenditure='" + Float.valueOf(editExpenditure.getText()) + "', reciept_no=" + editRecieptN.getText()+ ", date='" + editExpenseDate.getText() + "' WHERE id=" + id;
                    String sql = "UPDATE expenses SET expense='" + editExpense.getText() + "', expenditure=" + Float.parseFloat(editExpenditure.getText()) + ", reciept_no='" + editRecieptN.getText() + "', date='" + editExpenseDate.getText() + "' WHERE id=" + id;
                    Statement st = conn.createStatement();
                    st.executeUpdate(sql);
                    JOptionPane.showMessageDialog(this, "Expense Updated Succesfuly");
                    exspensesTableReport();
                    editExpense.setText(" ");
                    editExpenditure.setText(" ");
                    editRecieptN.setText(" ");
                    editExpenseDate.setText("");
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }

            }

        }
         
         
         if (e.getSource() == expDeleteButton) {
            if (expensesTable.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(this, "select item", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    Connection conn = getConnection();
                    Statement st = conn.createStatement();
                    int id = Integer.parseInt(expensesTable.getValueAt(expensesTable.getSelectedRow(), 0).toString());
                    String sql = "DELETE FROM expenses WHERE id = " + id;
                    String name = expensesTable.getValueAt(expensesTable.getSelectedRow(), 1).toString();
                    //confirm
                    JDialog.setDefaultLookAndFeelDecorated(true);
                    int response = JOptionPane.showConfirmDialog(null, "Do you want to delete " + name + " ?", "Confirm",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (response == JOptionPane.YES_OPTION) {
                        st.executeUpdate(sql);
                        JOptionPane.showMessageDialog(this, " " + name + " deleted ");
                        exspensesTableReport();

                    }

                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }

            }

        }
         
        if(e.getSource()==expExcelButton){
        try {
                exportTableToExcel(expensesTable);
            } catch (IOException ex) {
                Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if(e.getSource()==expPdfButton){
        printTable(expensesTable);
        }
        
        //disparities
        if(e.getSource()==addDisparityButton){
        addDisparity();
        }
        
        if(e.getSource()==showDisparitiesButton){
        disparitiesTableReport();
        }
        
        if (e.getSource() == dispEditButton) {
            //check item not picked
            if (disparitiesTable.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(null, "you must select a row", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                //read selected data
                editDisparity.setText(disparitiesTable.getValueAt(disparitiesTable.getSelectedRow(), 2).toString());
                editDisparityStatus.setText(disparitiesTable.getValueAt(disparitiesTable.getSelectedRow(), 3).toString());
                editDisparityDate.setText(disparitiesTable.getValueAt(disparitiesTable.getSelectedRow(), 5).toString());
                
            }

        }
        
         if (e.getSource() ==  editDisparityButton) {

            if (editDisparity.getText().trim().isEmpty() || editDisparityStatus.getText().trim().isEmpty() || editDisparityDate.getText().trim().isEmpty()
                     || disparitiesTable.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(null, "press edit button", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    int id = Integer.parseInt(disparitiesTable.getValueAt(disparitiesTable.getSelectedRow(), 0).toString());
                    Connection conn = getConnection();
                    //String sql = "UPDATE expenses set expense='" + editExpense.getText() + "', expenditure='" + editExpenditure.getText() + "', reciept_no=" + editRecieptN.getText()+ ",  date=" + editExpenseDate.getText() + ",  WHERE id =" + id;
                    //String sql = "UPDATE expenses SET expense='" + editExpense.getText() + "', expenditure='" + Float.valueOf(editExpenditure.getText()) + "', reciept_no=" + editRecieptN.getText()+ ", date='" + editExpenseDate.getText() + "' WHERE id=" + id;
                    String sql = "UPDATE disparities SET status='" + editDisparityStatus.getText() + "', disparity=" + Float.parseFloat(editDisparity.getText()) + ",date='" + editDisparityDate.getText() + "' WHERE id=" + id;
                    Statement st = conn.createStatement();
                    st.executeUpdate(sql);
                    JOptionPane.showMessageDialog(this, "Disparity Updated Succesfuly");
                    disparitiesTableReport();
                    editDisparity.setText(" ");
                    editDisparityStatus.setText(" ");
                    editDisparityDate.setText(" ");
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }

            }

        }
        
         if (e.getSource() == dispDeleteButton) {
            if (disparitiesTable.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(this, "select item", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    Connection conn = getConnection();
                    Statement st = conn.createStatement();
                    int id = Integer.parseInt(disparitiesTable.getValueAt(disparitiesTable.getSelectedRow(), 0).toString());
                    String sql = "DELETE FROM disparities WHERE id = " + id;
                    String name = disparitiesTable.getValueAt(disparitiesTable.getSelectedRow(), 1).toString();
                    //confirm
                    JDialog.setDefaultLookAndFeelDecorated(true);
                    int response = JOptionPane.showConfirmDialog(null, "Do you want to delete this particular disparity of  " + name + " ?", "Confirm",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (response == JOptionPane.YES_OPTION) {
                        st.executeUpdate(sql);
                        JOptionPane.showMessageDialog(this, " " + name + " deleted ");
                        disparitiesTableReport();

                    }

                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }

            }

        }
         
         if(e.getSource()==dispExcelButton){
        try {
                exportTableToExcel(disparitiesTable);
            } catch (IOException ex) {
                Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if(e.getSource()==dispPdfButton){
        printTable(disparitiesTable);
        }
         
         
        if (e.getSource() == addB2) {
            addToRestockTable();
        }

        if (e.getSource() == restockB1) {
            restockProducts();
        }

        if (e.getSource() == createUserB) {

            createUser();
            usersTableShow();
        }

        if (e.getSource() == editUserB) {
            upDateUser();

        }

        if (e.getSource() == processB) {
            salesTableReport();
        }

        if (e.getSource() == processB1) {
            restockTableReport();
        }
        
        //current stock report
        if(e.getSource()==currentStock){
         allProductsTableShow();
         printTable(allProductsTable);
        }
        
        if(e.getSource()==currentStockExpt){
           allProductsTableShow();
            try {
                exportTableToExcel(allProductsTable);
            } catch (IOException ex) {
                Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
            }
           }
        
        if(e.getSource()==uploadButton){
        save();
        }
        
        if(e.getSource()==sampleUploadButton){
        saveSampleCsv();
        }
        if(e.getSource()==addExpenseButton){
        addExspense();
        }
        
        if(e.getSource()==showExpensesButton){
        exspensesTableReport();
        }
        
        if(e.getSource()==editSettingsB){
          editSettings();
        }
        
    }

    public static void main(String[] args) {

        if (isAuthenticated) {

            new Shop();

        } else {
            new LoginPage();
        }

    }

}
