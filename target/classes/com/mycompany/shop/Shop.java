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
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.table.JTableHeader;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.HashMap;
import java.util.Map;
import javax.swing.SwingUtilities;





/**
 *
 * @author odipojames12
 */



public class Shop extends JFrame  implements ActionListener  {

Statement statement;
JButton addProdButton;
      
JTextField productName;
JTextField productId;
JTextField productUnit;
JTextField productQuantity;
JTextField productPrice;
JButton displayButton1;
JTable table1;
JButton clearButton1;
JTextField searchT1;
JButton searchB1;
JComboBox c1,c2,roleC,c3,c4,c5,c6;
JTable catTable;
JTextField sumTexFld;
JButton addB1;
JTextField balanceTexFld;
JTextField paidTexFld;
JTextField q1;
JButton sellB1;
JTable allProductsTable;
JButton loadAllB;
JTextField  srchFld;
JButton searchAllB;
JButton editProductB;
JTextField editName;
JTextField editUnit;
JTextField editQuantity;
JTextField editPrice;
JButton saveB;
JButton deleteB;
JTable restockTable,usersTable,salesTable,restockReportTable ;
JButton restockB1;
JButton addB2,createUserB,editUserB,processB,processB1;
JTextField sumTexFld1,q2,p1,userName,editUserName,editUserRole,editUserPassword;
JPasswordField passwordP;
JCheckBox dateCheckBox,dateCheckBox1;

private Map<String, Integer> inventory = new HashMap<>();


JFrame  jm = new JFrame();
JDatePickerImpl todatePicker,fromdatePicker,todatePicker1,fromdatePicker1;
public static boolean isAuthenticated = false;
public static String logUser = "";
public static String role = "";
public static String userId = "";


String username = System.getProperty("user.name");  
String downloadsPath = "C:\\Users\\" + username + "\\Downloads";

String fileName = "sales_report.xlsx"; // Replace with your desired file name
String filePathInDownloads = downloadsPath + "\\" + fileName;
    
         
    Shop(){
       JDialog.setDefaultLookAndFeelDecorated(true);
       
        JPanel allProductViewPanel = new JPanel();
        JPanel pn2 = new JPanel();
        JPanel restockPanel = new JPanel();
        JPanel addProductsPanel = new JPanel(new BorderLayout());
        JPanel salesReportPanel =  new JPanel();
        JTabbedPane tabs = new JTabbedPane();
        JLabel footer = new JLabel("© Copyright; 2023 OdipoJames");
        
        allProductViewPanel.setSize(1000, 500);
        //pn2.setSize(1000, 500);
        restockPanel.setSize(1000, 500);
       
        
       
        //layouts
        GridLayout grid = new GridLayout(3,3,2,5);
        //GridLayout grid1 = new GridLayout(2,2,2,5);
        
                
        //add products panel
        //display products table panel
        Font buttonFont = new Font("Tahoma",Font.BOLD,12);
        
        table1 = new JTable();
        JScrollPane table1Scroller =new JScrollPane(table1);
        table1Scroller.setBounds(150, 20, 700, 200);
        searchT1 = new JTextField("search by name or id",30);
        searchT1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                searchT1.setText("");
            }
        });
        
        //auto detect changes in search text fields
        searchT1.getDocument().addDocumentListener(new DocumentListener(){
          
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
        allProductsPanel.setBackground(Color.lightGray);
        addProductsPanel.add(pn2,BorderLayout.NORTH);
        addProductsPanel.add(allProductsPanel,BorderLayout.CENTER);
        addProductsPanel.add(allProductsPane2,BorderLayout.SOUTH);
        
        pn2.setSize(200, 200);
        pn2.setLayout(grid);
        pn2.setBorder(new EmptyBorder(30, 50, 50, 50));
        JLabel nameLabel = new JLabel("Name product");
        productName = new JTextField();
        //JLabel idLabel = new JLabel("Product Id");
        productId = new JTextField(10);
        productId.addKeyListener(new JTextFieldKeyListener(productId));
        JLabel unitLabel = new JLabel("Unit");
        productUnit = new JTextField("kgs");
        JLabel quantityLabel = new JLabel("Number of units");
        productQuantity = new JTextField(10);
        productQuantity.addKeyListener(new JTextFieldKeyListener(productQuantity));
        JLabel priceLabel = new JLabel("Price");
        productPrice = new JTextField(10);
        productPrice.addKeyListener(new JFloatListener(productPrice));
        JLabel dammy = new JLabel("");
        addProdButton = new JButton("add product");
        addProdButton.addActionListener((this));
        pn2.add(nameLabel);pn2.add(productName);pn2.add(unitLabel);pn2.add(productUnit); pn2.add(quantityLabel);pn2.add(productQuantity);
        pn2.add(priceLabel); pn2.add(productPrice);pn2.add(dammy); pn2.add(addProdButton);
        
        
        
        
        //sell products window
        GridLayout grid2 = new GridLayout(1,7,5,2);
        JPanel sellProductPanel = new JPanel();
        JPanel sellProductPanel1 = new JPanel();
        sellProductPanel.setSize(1000, 500);
        sellProductPanel1.setBorder(new EmptyBorder(20, 50, 50, 50));
        tabs.add("Sell Product",sellProductPanel);
        tabs.add("Add New  Products",addProductsPanel);
        sellProductPanel.setLayout(new BorderLayout());
        sellProductPanel.add(sellProductPanel1,BorderLayout.NORTH);
        c1 = new JComboBox();
        c1.addItem("Select Product");
        c1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                filComb();
            }
        });
        JLabel lbQ = new JLabel("Quantity");
        JLabel dumn = new JLabel("");
        addB1 = new JButton("add");
        addB1.addActionListener((this));
        q1 = new JTextField(5);
        q1.addKeyListener(new JTextFieldKeyListener(q1));
        sellProductPanel1.add(c1);sellProductPanel1.add(lbQ);sellProductPanel1.add(q1);
        sellProductPanel1.add(addB1);sellProductPanel1.add(dumn);sellProductPanel1.add(dumn);
        sellProductPanel1.add(dumn);
       
        
        JPanel sellProductPanel2 = new JPanel();
        sellProductPanel2.setLayout(new BorderLayout());
        sellProductPanel.add(sellProductPanel2,BorderLayout.CENTER);
        DefaultTableModel catTablemodel = new DefaultTableModel();
        catTablemodel.addColumn("ID");
        catTablemodel.addColumn("Name");
        catTablemodel.addColumn("Unit");
        catTablemodel.addColumn("Quantity");
        catTablemodel.addColumn("Total Ksh");
        catTable = new JTable(catTablemodel);
        catTable.setBounds(40, 50, 300, 200);
        //dissable cell from edditing
        for (int c = 0; c < catTable.getColumnCount(); c++)
        {
            Class<?> col_class = catTable.getColumnClass(c);
            catTable.setDefaultEditor(col_class, null);        // remove editor
        }
	// Adding Jtable to JScrollPane
	JScrollPane sp = new JScrollPane(catTable);
        JPanel buttP= new JPanel();
        buttP.setLayout(new GridLayout(12,1,5,2));
        sellB1  = new JButton("sell");
        sellB1.addActionListener((this));
        JButton rmB  = new JButton("remove");
        rmB.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
         if(catTable.getSelectedRow() != -1) {
               // remove selected row from the model
               catTablemodel.removeRow(catTable.getSelectedRow());
               catSum();
               sumTexFld.setText(Integer.toString(catSum()));
               System.out.println(isAuthenticated);
                System.out.println(role);
            }
        }
        
        });
        JButton rmB1  = new JButton("remove all");
        rmB1.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
        DefaultTableModel dtm = (DefaultTableModel) catTable.getModel();
        dtm.setRowCount(0);
        catSum();
        sumTexFld.setText(Integer.toString(catSum()));
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
        balanceTexFld.addMouseListener(new MouseAdapter(){
          @Override
            public void mouseEntered(MouseEvent e) {
                if(!paidTexFld.getText().equals("") && catSum()< Integer.parseInt(paidTexFld.getText()) ){
                    balanceTexFld.setText(Integer.toString(Integer.parseInt(paidTexFld.getText())-catSum()));
                }
                else{
                  balanceTexFld.setText("0");
                }
            }
        });
     
        buttP.add(rmB);buttP.add(rmB1);buttP.add(sellB1);
        buttP.add(sumLabel);buttP.add(sumTexFld);
        buttP.add(paidLabel);buttP.add(paidTexFld);
        buttP.add(balanceLabel);buttP.add(balanceTexFld);
        sellProductPanel2.add(sp,BorderLayout.CENTER);
        sellProductPanel2.add(buttP,BorderLayout.EAST);
        
      
        //view all products window
         
         tabs.add("View All Products",allProductViewPanel);   
    
         allProductViewPanel.setLayout(null);
         JPanel allProductViewPanel1  = new JPanel();
        
   
        DefaultTableModel allProductsTableModel = new DefaultTableModel();
        allProductsTableModel.addColumn("ID");
        allProductsTableModel.addColumn("Name");
        allProductsTableModel.addColumn("Unit");
        allProductsTableModel.addColumn("Quantity");
        allProductsTableModel.addColumn("Price");
        allProductsTableModel.addColumn("Added By");
        allProductsTableModel.addColumn("Date Added");
        allProductsTable = new JTable(allProductsTableModel);
        allProductsTable.setBounds(10, 50, 400, 200);
        //dissable cell from edditing
        for (int c = 0; c < allProductsTable.getColumnCount(); c++)
        {
            Class<?> col_class = allProductsTable.getColumnClass(c);
            allProductsTable.setDefaultEditor(col_class, null);        // remove editor
        }
	// Adding Jtable to JScrollPane
	JScrollPane sp1 = new JScrollPane(allProductsTable);
        sp1.setBounds(10, 40, 700, 300);
        allProductViewPanel.add(sp1);
        loadAllB = new JButton("show all");
        loadAllB.setBounds(150, 350, 100, 20);
        allProductViewPanel.add(loadAllB);
        loadAllB.addActionListener((this));
        
         srchFld = new JTextField(10);
         srchFld.setBounds(300, 350, 100, 20);
         allProductViewPanel.add(srchFld);
         srchFld.setText("name or id");
         srchFld.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                srchFld.setText("");
            }
        });
         
        //auto sersch when typing
        srchFld.getDocument().addDocumentListener(new DocumentListener(){
          
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
        searchAllB.setBounds(450, 350, 100, 20);
         allProductViewPanel.add(searchAllB);
        searchAllB.addActionListener((this));
        editProductB = new JButton("Edit");
        editProductB.setBounds(200, 10, 100, 20);
        if(isAuthenticated && role.equalsIgnoreCase("admin")){
            allProductViewPanel.add(editProductB);
    }
      
        editProductB.addActionListener((this));
        saveB = new JButton("Save");
        saveB.addActionListener((this));
        deleteB = new JButton("Delete");
        deleteB.setBounds(400, 10, 100, 20);
         if(isAuthenticated && role.equalsIgnoreCase("admin")){
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
        editQuantity.addKeyListener(new JTextFieldKeyListener(editQuantity));
        
        JLabel editPricel = new JLabel("price per unit");
        editPricel.setBounds(60, 200, 100, 20);
        allProductViewPanel1.add(editPricel);
        editPrice = new JTextField(10);
        editPrice.setBounds(10, 220, 220, 20);
        allProductViewPanel1.add( editPrice);
        editPrice.addKeyListener(new JTextFieldKeyListener(editPrice));
        saveB.setBounds(10, 280, 220, 20);
        if(isAuthenticated && role.equalsIgnoreCase("admin")){
           allProductViewPanel1.add(saveB);
    }
        
        
        allProductViewPanel1.setLayout(null);
        allProductViewPanel1.setBounds(720, 30, 260, 380);
        Border blackline4 = BorderFactory.createTitledBorder("Edit Product");
        allProductViewPanel1.setBorder(blackline4);
        allProductViewPanel.add(allProductViewPanel1);
        
        
        
      
        
        
        //restock winow
        GridLayout grid3 = new GridLayout(1,7,5,2);
        JPanel restockPanel1 = new JPanel();
        restockPanel.setSize(1000, 500);
        restockPanel1.setBorder(new EmptyBorder(20, 50, 50, 50));
        //tabs.add("restock Product",sellProductPanel);
        if(isAuthenticated && role.equalsIgnoreCase("admin")){
            tabs.add("Restock Products ",restockPanel);
        }
        restockPanel.setLayout(new BorderLayout());
        restockPanel.add(restockPanel1,BorderLayout.NORTH);
        c2 = new JComboBox();
        c2.addItem("Select Product");
        c2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                filComb2();
            }
        });
        addB2 = new JButton("add");
        addB2.addActionListener((this));
        JLabel q2label = new JLabel("quantity");
        q2 = new JTextField(10);
        q2.addKeyListener(new JTextFieldKeyListener(q2));
        JLabel pricel1 = new JLabel("price per unit");
        p1 = new JTextField(10);
        p1.addKeyListener(new JTextFieldKeyListener(p1));
        restockPanel1.add(c2);restockPanel1.add(q2label);restockPanel1.add(q2);
        restockPanel1.add(pricel1);restockPanel1.add(p1);
        restockPanel1.add(addB2);restockPanel1.add(dumn);restockPanel1.add(dumn);
        restockPanel1.add(dumn);
       
        
        JPanel restockPanel2 = new JPanel();
        restockPanel2.setLayout(new BorderLayout());
        restockPanel.add(restockPanel2,BorderLayout.CENTER);
        DefaultTableModel restockTablemodel = new DefaultTableModel();
        restockTablemodel.addColumn("ID");
        restockTablemodel.addColumn("Name");
        restockTablemodel.addColumn("Unit");
        restockTablemodel.addColumn("Quantity");
        restockTablemodel.addColumn("Price per uinit Ksh");
        restockTable = new JTable(restockTablemodel);
        restockTable.setBounds(40, 50, 300, 200);
        //dissable cell from edditing
        for (int c = 0; c < restockTable .getColumnCount(); c++)
        {
            Class<?> col_class = restockTable.getColumnClass(c);
            restockTable.setDefaultEditor(col_class, null);        // remove editor
        }
	// Adding Jtable to JScrollPane
	JScrollPane sp3 = new JScrollPane(restockTable );
        JPanel restockButtons= new JPanel();
        restockButtons.setLayout(new GridLayout(12,1,5,2));
        restockB1  = new JButton("restock");
        restockB1.addActionListener((this));
        JButton rmB3  = new JButton("remove");
        rmB3.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
         if(restockTable.getSelectedRow() != -1) {
               // remove selected row from the model
               restockTablemodel.removeRow(restockTable.getSelectedRow());
               restockCatSum();
               sumTexFld1.setText(Integer.toString(restockCatSum()));
            }
        }
        
        });
        JButton rmB4  = new JButton("remove  all");
        rmB4.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
        DefaultTableModel dtm = (DefaultTableModel) restockTable.getModel();
        dtm.setRowCount(0);
        restockCatSum();
        sumTexFld1.setText(Integer.toString( restockCatSum()));
        }
        
        });
        JLabel sumLabel1 = new JLabel("Total Sum:");
        sumTexFld1 = new JTextField(10);
        sumTexFld1.setEditable(false);
        sumTexFld1.addKeyListener(new JTextFieldKeyListener(sumTexFld1));
        restockButtons.add(rmB3);restockButtons.add(rmB4);restockButtons.add(restockB1);
        restockButtons.add(sumLabel1);restockButtons.add(sumTexFld1);
        restockPanel2.add(sp3,BorderLayout.CENTER);
        restockPanel2.add(restockButtons,BorderLayout.EAST);
        
        
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
        
        String[] rl = {"normal","admin"};
        JLabel roleLabel =new JLabel("user role");
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
        for (int c = 0; c < usersTable .getColumnCount(); c++)
        {
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
        deleteUser.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
         if(usersTable.getSelectedRow()<0){
        JOptionPane.showMessageDialog(null, "select user","Error!",JOptionPane.ERROR_MESSAGE);
        }
        else{
        try{
            Connection conn =  getConnection();
            Statement st = conn.createStatement();
            String sql = "DELETE FROM users WHERE id = "+usersTable.getValueAt(usersTable.getSelectedRow(), 0);
            String name = usersTable.getValueAt(usersTable.getSelectedRow(), 1).toString();
            //confirm
            JDialog.setDefaultLookAndFeelDecorated(true);
            int response = JOptionPane.showConfirmDialog(null, "Do you want to delete "+name+" ?", "Confirm",
        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION){
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, " "+name+" deleted ");
            usersTableShow();
        }
                
            
        } 
          
        catch(SQLException ex){
          System.out.println(ex.getMessage());
          }
            
            
        }
        
        }
        
        
        });
        
        
        deleteUser.setBounds(350, 180, 100, 20);
        userPanel.add(deleteUser);
        
        JButton viewUsers = new JButton("View Users");
        viewUsers.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
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
        editUserRoleLabel .setBounds(60, 75, 100, 20);
        editF.add(editUserRoleLabel );
        editUserRole = new JTextField();
        editUserRole.setBounds(180, 80, 100, 20);
        editF.add(editUserRole);
        
        JLabel editUserPasswordLabel = new JLabel("Password");
        editUserPasswordLabel.setBounds(60, 105, 100, 20);
        editF.add(editUserPasswordLabel);
        editUserPassword = new JTextField();
        editUserPassword.setBounds(180, 110, 100, 20);
        editF.add(editUserPassword);
        
        editUserB=new JButton("save");
        editUserB.setBounds(180, 150, 100, 20);
        editUserB.addActionListener((this));
        
        editUser.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
        
         //check product not picked
         if(usersTable.getSelectedRow()<0){
         JOptionPane.showMessageDialog(null, "you must select user from table","Error!", JOptionPane.ERROR_MESSAGE);
         }
         else{
             try {
                 //read selected data
                 editUserName.setText(usersTable.getValueAt(usersTable.getSelectedRow(), 1).toString());
                 editUserRole.setText(usersTable.getValueAt(usersTable.getSelectedRow(), 2).toString());
                 
                 
                 
                 Connection conn =  getConnection();
                 Statement st = conn.createStatement();
                 String sql = "SELECT * FROM users WHERE id = '"+usersTable.getValueAt(usersTable.getSelectedRow(), 0).toString()+ "'";
                 ResultSet rs =  st.executeQuery(sql);
                 if(rs.next()){
                 editUserPassword.setText(rs.getString(3));
                 }
             } catch (SQLException ex) {
                 Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
             }
        
         
         
         }
    
        }     });
        editF.add(editUserB);
        userPanel.add(editF);
   
        
        if(isAuthenticated && role.equalsIgnoreCase("admin")){
            tabs.add("User Manager ",userPanel);
        }
        
        
        //sales report window
       
         tabs.add("View Sales Report",salesReportPanel);   
         salesReportPanel.setLayout(null);
         
    
        JPanel salesTablePanel =  new JPanel();
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
                    exportTableToExcel(salesTable, filePathInDownloads);
                    JOptionPane.showMessageDialog(jm, "Table exported to Excel successfully.\n chek in downloads");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(jm, "Error exporting table to Excel: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
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
       fromdatemodel.setDate(Ldate.getYear(), Ldate.getMonthValue()-1, Ldate.getDayOfMonth());
       fromdatemodel.setSelected(true);
       JDatePanelImpl datePanel1 = new JDatePanelImpl(fromdatemodel,p);
       fromdatePicker = new JDatePickerImpl(datePanel1, new DateLabelFormatter());
       fromdatePicker.setBounds(30, 70, 150, 20);
       salesTablePanel.add(fromdatePicker);
        
        
        
        JLabel toLabel = new JLabel("To:");
        toLabel.setBounds(210, 50, 100, 20);
        salesTablePanel.add(toLabel);
        toLabel.setForeground(Color.green);
        toLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        
        UtilDateModel todatemodel = new UtilDateModel();
        todatemodel.setDate(Ldate.getYear(), Ldate.getMonthValue()-1, Ldate.getDayOfMonth());
        todatemodel.setSelected(true);
        JDatePanelImpl datePanel = new JDatePanelImpl(todatemodel,p);
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
        
        
        
        //sales table
        DefaultTableModel salesTablemodel = new DefaultTableModel();
        salesTablemodel.addColumn("ID");
        salesTablemodel.addColumn("NAME");
        salesTablemodel.addColumn("UNIT");
        salesTablemodel.addColumn("QUANTITY");
        salesTablemodel.addColumn("TOTAL KSH.");
        salesTablemodel.addColumn("SOLD BY");
        salesTablemodel.addColumn("DATE");
        salesTable = new JTable(salesTablemodel);
        //dissable cell from edditing
        for (int c = 0; c < salesTable .getColumnCount(); c++)
        {
            Class<?> col_class = salesTable.getColumnClass(c);
            salesTable.setDefaultEditor(col_class, null);        // remove editor
        }
        JScrollPane sp5 = new JScrollPane(salesTable);
        sp5.setBounds(30, 100, 800, 400);
        salesTablePanel.add(sp5);
        
        
        
        //restock report window
         JPanel restockReportPanel = new JPanel();
         tabs.add("View Restock History",restockReportPanel);   
         restockReportPanel.setLayout(null);
         
    
        JPanel restockReportTablePanel =  new JPanel();
        restockReportTablePanel.setFont(new Font("Serif", Font.PLAIN, 15));
        restockReportTablePanel.setLayout(null);
        restockReportTablePanel.setBackground(Color.white);
        restockReportTablePanel.setBounds(30, 30, 900, 600);
        restockReportPanel.add(restockReportTablePanel);
        JLabel  restockL1 = new JLabel("Restocks");
        restockL1.setBounds(30, 20, 100, 20);
        restockReportTablePanel.add( restockL1);
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
                    exportTableToExcel(restockReportTable, filePathInDownloads);
                    JOptionPane.showMessageDialog(jm, "Table exported to Excel successfully.\n chek in downloads");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(jm, "Error exporting table to Excel: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        restockReportTablePanel.add(clearB1);
        clearB1.setBackground(Color.BLUE);
        clearB1.setForeground(Color.white);
        
        JLabel fromLabel1 = new JLabel("From:");
        fromLabel1.setBounds(30, 50, 100, 20);
        restockReportTablePanel.add(fromLabel1);
        fromLabel1.setForeground(Color.green);
        fromLabel1.setFont(new Font("Serif", Font.PLAIN, 16));
        
        //date picker properties
       
        
       UtilDateModel fromdatemodel1 = new UtilDateModel();
       fromdatemodel1.setDate(Ldate.getYear(), Ldate.getMonthValue()-1, Ldate.getDayOfMonth());
       fromdatemodel1.setSelected(true);
       JDatePanelImpl datePanel2 = new JDatePanelImpl(fromdatemodel1,p);
       fromdatePicker1 = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
       fromdatePicker1.setBounds(30, 70, 150, 20);
       restockReportTablePanel.add(fromdatePicker1);
        
        
        
        JLabel toLabel1 = new JLabel("To:");
        toLabel1.setBounds(210, 50, 100, 20);
        restockReportTablePanel.add(toLabel1);
        toLabel1.setForeground(Color.green);
        toLabel1.setFont(new Font("Serif", Font.PLAIN, 16));
        
        UtilDateModel todatemodel1 = new UtilDateModel();
        todatemodel1.setDate(Ldate.getYear(), Ldate.getMonthValue()-1, Ldate.getDayOfMonth());
        todatemodel1.setSelected(true);
        JDatePanelImpl datePanel3 = new JDatePanelImpl(todatemodel1,p);
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
        
        JLabel  restockerLabel = new JLabel("Restocker");
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
        
        
        
        // restock table
        DefaultTableModel  restockReportTableModel = new DefaultTableModel();
         restockReportTableModel.addColumn("ID");
         restockReportTableModel.addColumn("NAME");
         restockReportTableModel.addColumn("UNIT");
         restockReportTableModel.addColumn("QUANTITY");
         restockReportTableModel.addColumn("Price");
         restockReportTableModel.addColumn("RESTOCKED BY");
         restockReportTableModel.addColumn("DATE");
         restockReportTable = new JTable( restockReportTableModel);
        //dissable cell from edditing
        for (int c = 0; c <  restockReportTable .getColumnCount(); c++)
        {
            Class<?> col_class =  restockReportTable.getColumnClass(c);
             restockReportTable.setDefaultEditor(col_class, null);        // remove editor
        }
        JScrollPane sp6 = new JScrollPane( restockReportTable);
        sp6.setBounds(30, 100, 800, 400);
        restockReportTablePanel.add(sp6);
        
        //reminders on low stok
        Timer timer = new Timer();
        int firstAlertDelay = 10 * 1000; // 10 seconds
        int repeatAlertInterval = 5 * 60 * 1000; // 5 minutes

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkAndAlertLowStock();
            }
        }, firstAlertDelay, repeatAlertInterval);
        
        
        jm.add(tabs);
       
        jm.setTitle("SHOP MANAGER");
        jm.setSize(1000, 600);
        jm.setLocationRelativeTo(null);
        //add icon;
        jm.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("store-icon.jpg")));
        jm.setVisible(true);
        jm.add(footer,BorderLayout.SOUTH);
        jm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        jm.addWindowListener(new WindowAdapter() {
    @Override
    public void windowClosing(WindowEvent we)
    { 
        String ObjButtons[] = {"LogOut","Exit"};
        int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to logout or exit?","Shop Manager",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
        if(PromptResult==JOptionPane.YES_OPTION)
        {
         jm.dispose();
         new LoginPage();
          
        }
         if(PromptResult==JOptionPane.NO_OPTION){
         System.exit(0);
         }
    }
});
    
    }
   
   //connection tofdatabase
    public static Connection getConnection(){
    String dbURL = "jdbc:derby:C:/db/shop;create=true";
    
     Connection conn = null;
     
     try{
     conn =  DriverManager.getConnection(dbURL);
     System.out.println("Connected!");
     
     }
     catch(SQLException ex){
        Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
        ex.printStackTrace();
        System.out.println("No connection");
        
     }
    
    return conn;
    }

    
    
    //method to get sum on catTable
      public int catSum(){
        int sum = 0;
        int rowCount =  catTable.getRowCount();
        for(int i=0;i<rowCount;i++){
         sum = sum+ Integer.parseInt(catTable.getValueAt(i, 4).toString());
        }
        return sum;
      }
      
      //method to get sum on catTable
      public int restockCatSum(){
        int sum = 0;
        int rowCount =  restockTable.getRowCount();
        for(int i=0;i<rowCount;i++){
         sum = sum+ Integer.parseInt(restockTable.getValueAt(i, 4).toString());
        }
        return sum;
      }
      
    //method to dispaly all data 
    
    public void displayAllProductstable(){
    
     table1.setModel(new DefaultTableModel());
          try {
              Connection conn=getConnection();
              Statement st = conn.createStatement();
              Statement st2 = conn.createStatement();
              String query = "SELECT * from products ORDER BY date DESC ";
              ResultSet rs =  st.executeQuery(query);
              ResultSetMetaData rsmd = rs.getMetaData();
              //table model
              DefaultTableModel model = (DefaultTableModel) table1.getModel();
              
              
              //get column count
              int cols = 5;//number of table cols
              
              String[] colName =new String[cols];
              //get col names from db
              for(int i=0;i<cols;i++)
                  colName[i]= rsmd.getColumnName(i+1);
              model.setColumnIdentifiers(colName);
              //reading rows from db
              String name,id,unit,quantity,price;
              while(rs.next()){
              id=rs.getString(1);
              name=rs.getString(2);
              unit = rs.getString(3);
              quantity =rs.getString(4);
              price = rs.getString(5);
              String[] row = {id,name,unit,quantity,price};
              model.addRow(row);
              
              }
              st.close();
              st2.close();
              conn.close();
              
             
           System.out.println("DB connected!");
          } 
          catch (SQLException ex) {
               System.out.println(ex.getMessage());
               System.out.println("mySQL error");
              Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
          }
     
    }
    
    
    //search by id or name table
    
    public void displaySearchedProductstable() {
    table1.setModel(new DefaultTableModel());
    if (searchT1.getText().equals("") || searchT1.getText().equalsIgnoreCase("search by name or id")) {
        JOptionPane.showMessageDialog(this, "you must enter the name or id of the product!", "Error!", JOptionPane.ERROR_MESSAGE);
        return; // Exit the method if search input is empty or has a placeholder
    }

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
        } else {
            String query = "SELECT * FROM products WHERE name LIKE ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, "%" + searchItem + "%");
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
    public boolean searchByName(String sname){
      boolean check = false;
      
      try{
        Connection conn = getConnection(); 
        Statement st = conn.createStatement();
        String sql = "SELECT name FROM products WHERE name = '"+sname + "'";
        ResultSet result =  st.executeQuery(sql);
        if(result.next()){
        check = true;
        }
        st.close();
        conn.close();
      }
      catch(Exception e){
          System.out.print(e.getMessage());
      }
     
      return check;
      
         }
    
    //select products 
        public void  filComb(){
         //clear selectbox
         c1.removeAllItems();
         
      
      try{
        Connection conn = getConnection();
        Statement st = conn.createStatement();
        String sql = "SELECT name FROM products";
        ResultSet rs =  st.executeQuery(sql);
        
        while(rs.next()){
            c1.addItem(rs.getString("name"));
        }
        
        st.close();
        conn.close();
       
      }
      catch(Exception e){
          System.out.print(e.getMessage());
      }
     
    }
        
        
   //select products 
        public void  filComb2(){
        c2.removeAllItems();
      
      try{
        Connection conn = getConnection();
        Statement st = conn.createStatement();
        String sql = "SELECT name FROM products";
        ResultSet rs =  st.executeQuery(sql);
        
        while(rs.next()){
            c2.addItem(rs.getString("name"));
        }
        
        st.close();
        conn.close();
       
      }
      catch(Exception e){
          System.out.print(e.getMessage());
      }
     
    }    
        
         
   //select sold products 
        public void  filComb3(){
        c3.removeAllItems();
        
      
      try{
        Connection conn = getConnection();
        Statement st = conn.createStatement();
        String sql = "SELECT DISTINCT  name FROM sold_products";
        ResultSet rs =  st.executeQuery(sql);
        
        while(rs.next()){
            c3.addItem(rs.getString("name"));
        }
        
        st.close();
        conn.close();
        c3.addItem("ALL");
       
      }
      catch(Exception e){
          System.out.print(e.getMessage());
      }
     
    }         
     
        //select  product's  seller
        public void  filComb4(){
        c4.removeAllItems();
        
      try{
        Connection conn = getConnection();
        Statement st = conn.createStatement();
        String sql = "SELECT DISTINCT  sold_by FROM sold_products";
        ResultSet rs =  st.executeQuery(sql);
        
        while(rs.next()){
           
            Statement st2 = conn.createStatement();
            ResultSet rs2 = st2.executeQuery("SELECT * FROM users WHERE id="+rs.getString("sold_by"));
            if(rs2.next()){
             c4.addItem(rs2.getString(2));
            } 
        }
        
        st.close();
        conn.close();
        c4.addItem("ALL");
      
       
      }
      catch(Exception e){
          System.out.print(e.getMessage());
      }
     
    }         
    
    //select restoced products 
        public void  filComb5(){
        c5.removeAllItems();
        
      
      try{
        Connection conn = getConnection();
        Statement st = conn.createStatement();
        String sql = "SELECT DISTINCT  name FROM restocked_products";
        ResultSet rs =  st.executeQuery(sql);
        
        while(rs.next()){
            c5.addItem(rs.getString("name"));
        }
        
        st.close();
        conn.close();
        c5.addItem("ALL");
       
      }
      catch(Exception e){
          System.out.print(e.getMessage());
      }
     
    }         
        
        
     //select products restocker
        public void  filComb6(){
        c6.removeAllItems();
        
      try{
        Connection conn = getConnection();
        Statement st = conn.createStatement();
        String sql = "SELECT DISTINCT  restocked_by FROM restocked_products";
        ResultSet rs =  st.executeQuery(sql);
        
        while(rs.next()){
           
            Statement st2 = conn.createStatement();
            ResultSet rs2 = st2.executeQuery("SELECT * FROM users WHERE id="+rs.getString("restocked_by"));
            if(rs2.next()){
             c6.addItem(rs2.getString(2));
            } 
        }
        
        st.close();
        conn.close();
        c6.addItem("ALL");
      
       
      }
      catch(Exception e){
          System.out.print(e.getMessage());
      }
     
    }       
        
        
        //adding product to cat table
    public void addToCat(){
       DefaultTableModel model = (DefaultTableModel) catTable.getModel();
       String name,id,unit,quantity,total;
       
       if(c1.getSelectedItem().toString().equalsIgnoreCase("Select Product")){
          JOptionPane.showMessageDialog(null, "select product","Error!", JOptionPane.ERROR_MESSAGE);
          c1.requestFocus();
          
       }
       else if(q1.getText().equals("")){
          JOptionPane.showMessageDialog(null, "enter quantity","Error!", JOptionPane.ERROR_MESSAGE);
          q1.requestFocusInWindow();
         
       }
       else if(Integer.parseInt(q1.getText())<=0){
          JOptionPane.showMessageDialog(null, "enter correct value of quantity","Error!", JOptionPane.ERROR_MESSAGE);
          q1.requestFocusInWindow();
         
       }
          
       else{
        try{
        Connection conn = getConnection();
        Statement st = conn.createStatement();
        String select = c1.getSelectedItem().toString();
        String sql = "SELECT * FROM products WHERE name = '" + select + "'";

        ResultSet rs =  st.executeQuery(sql);
       
        int q2 =0 ;    
       
        
        
         
        
        while(rs.next()){
             if(catTable.getRowCount()>0){
        String id_k = rs.getString(1);//get id of product selected
        
        for(int i= 0; i<catTable.getRowCount();i++){ //check quantity of prodects of same id in catTable and sum quantity
           if(catTable.getValueAt(i, 0).equals(id_k)){
             q2 = q2 + Integer.parseInt(catTable.getValueAt(i,3).toString());
           }
        }
           
         }
            if(Integer.parseInt(rs.getString(4))< q2+Integer.parseInt(q1.getText())){
               
       
             JOptionPane.showMessageDialog(null,"few of this product remaining in stok \n please check quantity remaining" );
        }
             
            else if(Integer.parseInt(rs.getString(4))>= q2){
               id=rs.getString(1);
               name=rs.getString(2);
               unit = rs.getString(3);
               quantity =  q1.getText();
               total = Integer.toString((int) (rs.getFloat(5)*Integer.parseInt(q1.getText())));
               
              String[] row = {id,name,unit,quantity,total};
              model.addRow(row);
         
              
             
              
             }
             
       
           
             
            
        
            
        }
         
         
         st.close();
         conn.close();
        
         sumTexFld.setText(Integer.toString(catSum()));
       
      }
      catch(Exception e){
          System.out.print(e.getMessage());
      }
       
       }
        
    } 
    
    
    
    //adding product to cat table
    public void addToRestockTable(){
        
       if(c2.getSelectedItem().toString().equalsIgnoreCase("Select Product")){
          JOptionPane.showMessageDialog(null, "select product","Error!", JOptionPane.ERROR_MESSAGE);
          c2.requestFocus();
          
       }
       else if(q2.getText().equals("")){
          JOptionPane.showMessageDialog(null, "enter quantity","Error!", JOptionPane.ERROR_MESSAGE);
          q2.requestFocusInWindow();
         
       }
        else if(Integer.parseInt(q2.getText())<=0){
          JOptionPane.showMessageDialog(null, "enter correct quantity value","Error!", JOptionPane.ERROR_MESSAGE);
          q2.requestFocusInWindow();
         
       }
         
       
        else if(p1.getText().equals("")){
          JOptionPane.showMessageDialog(null, "enter price ","Error!", JOptionPane.ERROR_MESSAGE);
          p1.requestFocusInWindow();
         
       }
        
       else if(Float.parseFloat(p1.getText())<=0){
          JOptionPane.showMessageDialog(null, "enter correct price value ","Error!", JOptionPane.ERROR_MESSAGE);
          p1.requestFocusInWindow();
         
       }
          
       else{
        try{
        Connection conn = getConnection();
        Statement st = conn.createStatement();
        String select = c2.getSelectedItem().toString();
        String sql = "SELECT * FROM products WHERE name = '"+select+ "'";
        ResultSet rs =  st.executeQuery(sql);
        DefaultTableModel model = (DefaultTableModel) restockTable.getModel();
        String name,id,unit,quantity,price;
        
         
        
        while(rs.next()){
               id=rs.getString(1);
               name=rs.getString(2);
               unit = rs.getString(3);
               quantity =  q2.getText();
               price = p1.getText();
          
              String[] row = {id,name,unit,quantity,price};
              model.addRow(row);      
        }
         st.close();
         conn.close();
        //to be done next
         sumTexFld1.setText(Integer.toString(restockCatSum()));
       
      }
      catch(SQLException e){
          System.out.print(e.getMessage());
      }
       
       }
        
    } 
    
   
    
    

//All Products Table Show/Crud products
 public  void allProductsTableShow(){
         //clears table if any
        DefaultTableModel dm = (DefaultTableModel) allProductsTable.getModel();
        dm.setRowCount(0);
        try{
        Connection conn = getConnection();
        Statement st = conn.createStatement();
        Statement st2 = conn.createStatement();
        String sql = "SELECT * FROM products";
        ResultSet rs =  st.executeQuery(sql);
        DefaultTableModel model = (DefaultTableModel) allProductsTable.getModel();
        String id,name,unit,quantity,price,added_by,date;
        while(rs.next()){
               id=rs.getString(1);
               name=rs.getString(2);
               unit = rs.getString(3);
               quantity =  rs.getString(4);
               price = rs.getString(5);
               //find username of user foriegn key
               ResultSet rs2 = st2.executeQuery("SELECT * FROM users WHERE id="+Integer.parseInt(rs.getString(6))+" ");
              if(rs2.next()){
              added_by = rs2.getString(2);
              }
              else{
              added_by = rs.getString(6);
              }
               date = rs.getString(7);
               String[] row = {id,name,unit,quantity,price,added_by,date};
               model.addRow(row);     
        }
         st.close();
         conn.close();
        
        
       
      }
      catch(Exception e){
          System.out.print(e.getMessage());
      }
       
       
        
    } 
 
 
 
// Search on CRUD products table
public void searchCrudProductsTable() {
    if (srchFld.getText().equals("") || srchFld.getText().equalsIgnoreCase("name or id")) {
        JOptionPane.showMessageDialog(this, "You must enter the name or id of the product!", "Error!", JOptionPane.ERROR_MESSAGE);
        return; // Exit the method if search input is empty or has a placeholder
    }

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
            String query = "SELECT * FROM products WHERE name LIKE ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, "%" + searchItem + "%");
            rs = preparedStatement.executeQuery();
        }

        if (rs.next()) {
            //clear table first if search is found
            dm.setRowCount(0);
            String id, name, unit, quantity, price, added_by, date;
            do {
                id = rs.getString(1);
                name = rs.getString(2);
                unit = rs.getString(3);
                quantity = rs.getString(4);
                price = rs.getString(5);

                ResultSet rs2 = conn.createStatement().executeQuery("SELECT * FROM users WHERE id=" + Integer.valueOf(rs.getString(6)));
                if (rs2.next()) {
                    added_by = rs2.getString(2);
                } else {
                    added_by = rs.getString(6);
                }

                date = rs.getString(7);
                String[] row = { id, name, unit, quantity, price, added_by, date };
                model.addRow(row);
            } while (rs.next());
        } else {
            JOptionPane.showMessageDialog(this, "Search item not found!");
        }

        conn.close();
        System.out.println("DB connected!");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
        System.out.println("mySQL error");
        Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
    }
}

        
    
    
    
    //sell products
public void sellProduct(){
    
    DefaultTableModel model = (DefaultTableModel) catTable.getModel();
    int row_count =  model.getRowCount();
    if(row_count<1){
    JOptionPane.showMessageDialog(null, "There's no product added to cat!",
      "Error!", JOptionPane.ERROR_MESSAGE);

    }
    else{
    try{
        //confirm
            JDialog.setDefaultLookAndFeelDecorated(true);
            int response = JOptionPane.showConfirmDialog(null, "Do you want to sell products?", "Confirm",
        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
     if(response==JOptionPane.YES_OPTION){
        Connection conn = getConnection();
      
      PreparedStatement insert = conn.prepareStatement("insert into sold_products (name,unit,quantity,total_ksh,sold_by)values(?,?,?,?,?)");
      for(int i =0;i<catTable.getRowCount();i++){
          insert.setString(1,model.getValueAt(i, 1).toString());
          insert.setString(2,model.getValueAt(i, 2).toString());
          insert.setInt(3,Integer.parseInt(model.getValueAt(i, 3).toString()));
          insert.setFloat(4,Float.parseFloat(model.getValueAt(i,4).toString()));
          insert.setInt(5, Integer.parseInt(userId));
          insert.executeUpdate();
          //seardch for product being sold
          Statement st = conn.createStatement();
          String sql = "SELECT * FROM products WHERE id = "+ model.getValueAt(i, 0);
          ResultSet rs =  st.executeQuery(sql);
          //update quantity of the product
          if(rs.next()){
           int q = Integer.parseInt(rs.getString(4))-Integer.parseInt(model.getValueAt(i, 3).toString());
          String q1 =  String.valueOf(q); 
          String sql1 = "UPDATE products set quantity="+q1+" WHERE id ="+model.getValueAt(i, 0).toString();
           st.executeUpdate(sql1);
          }
           st.close();
      
        
      }
      JOptionPane.showMessageDialog(this, "transaction succesful!");
      //confirm reciept
      JDialog.setDefaultLookAndFeelDecorated(true);
      int response1 = JOptionPane.showConfirmDialog(null, "Do you want to print a Reciept?", "Confirm",
        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
      if(response1==JOptionPane.YES_OPTION){
      model.addRow(new Object[]{"", "", "","Sum Total", colSum(catTable,4), "",""});
      printTable(catTable);
      }
        conn.close();
        model.setRowCount(0);
        displayAllProductstable();
        allProductsTableShow();
      
     }
    }
    
    
            
      catch(SQLException e){
       System.out.println(e.getMessage());
      }
        
    
    }
      
      
      
}




//restock products
public void restockProducts(){
    
    DefaultTableModel model = (DefaultTableModel) restockTable.getModel();
    int row_count =  model.getRowCount();
    if(row_count<1){
    JOptionPane.showMessageDialog(null, "There's no product added yet",
      "Error!", JOptionPane.ERROR_MESSAGE);

    }
    else{
    try{
      Connection conn = getConnection();
       PreparedStatement insert = conn.prepareStatement("insert into restocked_products (name,unit,quantity,price,restocked_by)values(?,?,?,?,?)");
      for(int i =0;i<model.getRowCount();i++){
          //insert.setInt(1,0);//auto gen in db
          insert.setString(1,model.getValueAt(i, 1).toString());
          insert.setString(2,model.getValueAt(i, 2).toString());
          insert.setInt(3,Integer.parseInt(model.getValueAt(i, 3).toString()));
          insert.setFloat(4,Float.parseFloat(model.getValueAt(i,4).toString()));
          insert.setInt(5,Integer.parseInt(userId));
          insert.executeUpdate();
          //seardch for product being sold
          Statement st = conn.createStatement();
          String sql = "SELECT * FROM products WHERE id = " +model.getValueAt(i, 0);
         
          ResultSet rs =  st.executeQuery(sql);
          //update quantity of the product
          if(rs.next()){
          int q = Integer.parseInt(rs.getString(4))+Integer.parseInt(model.getValueAt(i, 3).toString());
          float pr = Float.parseFloat(model.getValueAt(i,4).toString());
          String sql1 = "UPDATE products set quantity="+q+", price="+pr+" WHERE id ="+model.getValueAt(i, 0).toString();
           st.executeUpdate(sql1);
          }
          
        st.close();
      }
      JOptionPane.showMessageDialog(this, "transaction succesful!");
        model.setRowCount(0);
        displayAllProductstable();
        allProductsTableShow();
      conn.close();
    }
  
      catch(SQLException e){ 
       System.out.println(e.getMessage());
      }
        
    
    }
      
      
      
}









//method to search user already exists
    public boolean searchByUserName(String sname){
      boolean check = false;
      
      try{
        Connection conn = getConnection(); 
        Statement st = conn.createStatement();
        String sql = "SELECT username FROM users WHERE username = '"+sname + "'";
        ResultSet result =  st.executeQuery(sql);
        if(result.next()){
        check = true;
        }
        st.close();
        conn.close();
      }
      catch(Exception e){
          System.out.print(e.getMessage());
      }
     
      return check;
      
         }
    
    public void createUser(){
         String user  =  userName.getText();
         String p =   String.valueOf(passwordP.getPassword());
         String c = roleC.getSelectedItem().toString();
          if(userName.getText().equalsIgnoreCase("")|| String.valueOf(passwordP.getPassword()).equalsIgnoreCase("")){
          JOptionPane.showMessageDialog(this, "username and password required!","Error!", JOptionPane.ERROR_MESSAGE);
          }
          
          else if(user.indexOf(' ') != -1){
              JOptionPane.showMessageDialog(this, "username must be one word!","Error!", JOptionPane.ERROR_MESSAGE);
              userName.requestFocus();
          }
          else if(p.length()<4){
              JOptionPane.showMessageDialog(this, "password must be at least 4 characters!","Error!", JOptionPane.ERROR_MESSAGE);
              passwordP.requestFocus();
          }
          
          else if(searchByUserName(user)){//check if product exists
            JOptionPane.showMessageDialog(this, "user with username "+" "+user+"   already exists!","Error!", JOptionPane.ERROR_MESSAGE);
          }
          else{
           try {  
           Connection conn =  getConnection();
            statement = conn.createStatement();
            PreparedStatement insert = conn.prepareStatement("insert into users (username,password,role)values(?,?,?)");
            //insert.setInt(1,0);//auto gen in db
            insert.setString(1,user);
            insert.setString(2,p);
            insert.setString(3,c);
            insert.executeUpdate();
            JOptionPane.showMessageDialog(this, "User Successfuly Created!");

          //clear fields
          userName.setText("");
          passwordP.setText("");
          
          //displayAllProductstable();
          
          System.out.print("Database Connected");
          statement.close();
          conn.close();
     }
      catch(SQLException ex){
      
          System.out.print(ex.getMessage());
     }  
          
          
          
          
          }
         
        
    
    
    }
    
    
    
    
//users manage table
 public void usersTableShow(){
         //clears table if any
        DefaultTableModel dm = (DefaultTableModel) usersTable.getModel();
        dm.setRowCount(0);
        try{
        Connection conn = getConnection();
        Statement st = conn.createStatement();
        String sql = "SELECT * FROM users";
        ResultSet rs =  st.executeQuery(sql);
        DefaultTableModel model = (DefaultTableModel) usersTable.getModel();
        String id,username,role,password;
        while(rs.next()){
               id=rs.getString(1);
               username=rs.getString(2);
               role = rs.getString(4);
               password = rs.getString(3);
               String[] row = {id,username,role,password};
               model.addRow(row);     
        }
         st.close();
         conn.close();
        
        
       
      }
      catch(Exception e){
          System.out.print(e.getMessage());
      }
       
       
        
    } 
//update user details
 
 public boolean roleValidator(String role){
    boolean check =  false;
    String [] roles = {"admin","normal"};
    for(int i =0;i<roles.length;i++){
    if(role.equals(roles[i])){
    check = true;
    break;
    }
    }
    return check;
 }
 
  // Method to print the JTable
    private static void printTable(JTable table) {
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

    
 // Export the JTable to an Excel file
    private static void exportTableToExcel(JTable table, String filePath) throws IOException {
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

        // Write the workbook to the specified file
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
        }

        
    }


 
 
 public void upDateUser(){
       JDialog.setDefaultLookAndFeelDecorated(true);
       if(editUserName.getText().equalsIgnoreCase("")||editUserRole.getText().equalsIgnoreCase("")){
       JOptionPane.showMessageDialog(this, "select user and click edit first!","Error!", JOptionPane.ERROR_MESSAGE);
       
       }
      else if(!roleValidator(editUserRole.getText())){
         JOptionPane.showMessageDialog(this, "role must either be normal or admin","Error!", JOptionPane.ERROR_MESSAGE);
       }
       else
      {
          
           try{
          Connection conn =  getConnection();
          Statement st = conn.createStatement();
          String sql = "UPDATE users set username='"+editUserName.getText()+"', password='"+editUserPassword.getText()+"', role='"+editUserRole.getText()+"'  WHERE id ="+Integer.valueOf(usersTable.getValueAt(usersTable.getSelectedRow(), 0).toString());
          st.executeUpdate(sql);
          JOptionPane.showMessageDialog(this, "User Successfuly Updated!");
          editUserName.setText("");
          editUserPassword.setText("");
          editUserRole.setText("");
          usersTableShow();
          
      }
      catch(Exception e){
          
          JOptionPane.showMessageDialog(this, "user not updated!","Error!", JOptionPane.ERROR_MESSAGE);
          System.out.println(e.getMessage());
      }
      
      
      
      }
     
      
 
 }
 
 //calculate sum of a given column of Jtable
 
private int colSum(JTable table, int n) {
    int total = 0;
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
 public void salesTableReport(){
         //clears table if any
        DefaultTableModel dm = (DefaultTableModel) salesTable.getModel();
        dm.setRowCount(0);
        //date variables
        String from = fromdatePicker.getJFormattedTextField().getText();
        String to  = todatePicker.getJFormattedTextField().getText();
        String product = c3.getSelectedItem().toString();
        String seller = c4.getSelectedItem().toString();
        try{
        Connection conn = getConnection();
        Statement st = conn.createStatement();
        Statement st2 = conn.createStatement();
        Statement st3 = conn.createStatement();
       
        
        String sql = "SELECT * FROM sold_products WHERE date >= '"+from+"' AND date <='"+to+"'";
        if(!dateCheckBox.isSelected() && product.equalsIgnoreCase("ALL")){
        sql = "SELECT * FROM sold_products WHERE date BETWEEN '"+from+"' AND '"+to+"' ";
        }
        
        //all products sold by specific user between dates
        if(!dateCheckBox.isSelected() && product.equalsIgnoreCase("ALL") && !seller.equalsIgnoreCase("ALL"))
        {
         
         ResultSet rs3 = st3.executeQuery("SELECT * FROM users WHERE username='"+seller+"' ");
         if(rs3.next()){
         sql = "SELECT * FROM sold_products WHERE sold_by ="+Integer.valueOf(rs3.getString(1))+" AND date BETWEEN '"+from+"' AND '"+to+"' ";
         }
        }
        
        //specific products sold by specific user between dates
         if(!dateCheckBox.isSelected() && !product.equalsIgnoreCase("ALL") && !seller.equalsIgnoreCase("ALL"))
        {
         
         ResultSet rs3 = st3.executeQuery("SELECT * FROM users WHERE username='"+seller+"' ");
         if(rs3.next()){
         sql = "SELECT * FROM sold_products WHERE name='"+product+"' AND sold_by ="+Integer.valueOf(rs3.getString(1))+" AND date BETWEEN '"+from+"' AND '"+to+"' ";
         }
        }
         
       
        
        if(!dateCheckBox.isSelected() && !product.equalsIgnoreCase("ALL") && seller.equalsIgnoreCase("ALL")){
        sql = "SELECT * FROM sold_products WHERE name ='"+product+"' AND date BETWEEN '"+from+"' AND '"+to+"' ";
        }
        
         //all
         //all products sold by specific user:no time
        if((dateCheckBox.isSelected()) && (product.equalsIgnoreCase("ALL")) && (!seller.equalsIgnoreCase("ALL")))
        {
         
         ResultSet rs3 = st3.executeQuery("SELECT * FROM users WHERE username='"+seller+"' ");
         if(rs3.next()){
         sql = "SELECT * FROM sold_products WHERE sold_by="+Integer.valueOf(rs3.getString(1));
         }
        }
        
        //specific products sold by specific user:no time
         if((dateCheckBox.isSelected()) && (!product.equalsIgnoreCase("ALL")) && (!seller.equalsIgnoreCase("ALL")))
        {
         
         ResultSet rs3 = st3.executeQuery("SELECT * FROM users WHERE username='"+seller+"' ");
         if(rs3.next()){
         sql = "SELECT * FROM sold_products WHERE name='"+product+"' AND sold_by="+Integer.valueOf(rs3.getString(1));
         }
        }
         
        
        if(dateCheckBox.isSelected() && product.equalsIgnoreCase("ALL") && seller.equalsIgnoreCase("ALL")){
         sql = "SELECT * FROM sold_products ";//show all sales for all products
        }
        
        if(dateCheckBox.isSelected() && !product.equalsIgnoreCase("ALL") && seller.equalsIgnoreCase("ALL")){
         sql = "SELECT * FROM sold_products WHERE name = '"+product+"' ";//show all sales for a specific product
        }
        
        ResultSet rs =  st.executeQuery(sql);
        DefaultTableModel model = (DefaultTableModel) salesTable.getModel();
        String id,name,unit,quantity,total_ksh,sold_by,date;
        while(rs.next()){
               id=rs.getString(1);
               name=rs.getString(2);
               unit = rs.getString(3);
               quantity = rs.getString(4);
               total_ksh = rs.getString(5);
               //find username of user foriegn key
               ResultSet rs2 = st2.executeQuery("SELECT * FROM users WHERE id="+ Integer.valueOf(rs.getString(6)));
              if(rs2.next()){
              sold_by = rs2.getString(2);
              }
              else{
              sold_by = rs.getString(6);
              }
               date = rs.getString(7);
               String[] row = {id,name,unit,quantity,total_ksh,sold_by,date};
               model.addRow(row);  
              
        }
         model.addRow(new Object[]{"", "", "","Sum Total", colSum(salesTable,4), "",""});
         st.close();
         conn.close();
        
        
       
      }
      catch(SQLException e){
          System.out.print(e.getMessage());
      }
       
       
        
    } 
    
 
 
 
 //restocking history/ report
 public void restockTableReport(){
         //clears table if any
        DefaultTableModel dm = (DefaultTableModel) restockReportTable.getModel();
        dm.setRowCount(0);
        //date variables
        String from = fromdatePicker1.getJFormattedTextField().getText();
        String to  = todatePicker1.getJFormattedTextField().getText();
        String product = c5.getSelectedItem().toString();
        String seller = c6.getSelectedItem().toString();
        try{
        Connection conn = getConnection();
        Statement st = conn.createStatement();
        Statement st2 = conn.createStatement();
        Statement st3 = conn.createStatement();
       
        
        String sql = "SELECT * FROM restocked_products WHERE date >= '"+from+"' AND date <='"+to+"'";
        if(!dateCheckBox1.isSelected() && product.equalsIgnoreCase("ALL")){
        sql = "SELECT * FROM restocked_products WHERE date BETWEEN '"+from+"' AND '"+to+"' ";
        }
        
        //all products sold by specific user between dates
        if(!dateCheckBox1.isSelected() && product.equalsIgnoreCase("ALL") && !seller.equalsIgnoreCase("ALL"))
        {
         
         ResultSet rs3 = st3.executeQuery("SELECT * FROM users WHERE username='"+seller+"' ");
         if(rs3.next()){
         sql = "SELECT * FROM restocked_products WHERE restocked_by ="+Integer.valueOf(rs3.getString(1))+" AND date BETWEEN '"+from+"' AND '"+to+"' ";
         }
        }
        
        //specific products sold by specific user between dates
         if(!dateCheckBox1.isSelected() && !product.equalsIgnoreCase("ALL") && !seller.equalsIgnoreCase("ALL"))
        {
         
         ResultSet rs3 = st3.executeQuery("SELECT * FROM users WHERE username='"+seller+"' ");
         if(rs3.next()){
         sql = "SELECT * FROM restocked_products WHERE name='"+product+"' AND restocked_by ="+Integer.valueOf(rs3.getString(1))+" AND date BETWEEN '"+from+"' AND '"+to+"' ";
         }
        }
         
       
        
        if(!dateCheckBox1.isSelected() && !product.equalsIgnoreCase("ALL") && seller.equalsIgnoreCase("ALL")){
        sql = "SELECT * FROM restocked_products WHERE name ='"+product+"' AND date BETWEEN '"+from+"' AND '"+to+"' ";
        }
        
         //all
         //all products restocked by specific user:no time
        if((dateCheckBox1.isSelected()) && (product.equalsIgnoreCase("ALL")) && (!seller.equalsIgnoreCase("ALL")))
        {
         
         ResultSet rs3 = st3.executeQuery("SELECT * FROM users WHERE username='"+seller+"' ");
         if(rs3.next()){
         sql = "SELECT * FROM restocked_products WHERE restocked_by="+Integer.valueOf(rs3.getString(1));
         }
        }
        
        //specific products sold by specific user:no time
         if((dateCheckBox1.isSelected()) && (!product.equalsIgnoreCase("ALL")) && (!seller.equalsIgnoreCase("ALL")))
        {
         
         ResultSet rs3 = st3.executeQuery("SELECT * FROM users WHERE username='"+seller+"' ");
         if(rs3.next()){
         sql = "SELECT * FROM restocked_products WHERE name='"+product+"' AND restocked_by="+Integer.valueOf(rs3.getString(1));
         }
        }
         
        
        if(dateCheckBox1.isSelected() && product.equalsIgnoreCase("ALL") && seller.equalsIgnoreCase("ALL")){
         sql = "SELECT * FROM restocked_products ";//show all sales for all products
        }
        
        if(dateCheckBox1.isSelected() && !product.equalsIgnoreCase("ALL") && seller.equalsIgnoreCase("ALL")){
         sql = "SELECT * FROM restocked_products WHERE name = '"+product+"' ";//show all sales for a specific product
        }
        
        ResultSet rs =  st.executeQuery(sql);
        DefaultTableModel model = (DefaultTableModel) restockReportTable.getModel();
        String id,name,unit,quantity,price,restocked_by,date;
        while(rs.next()){
               id=rs.getString(1);
               name=rs.getString(2);
               unit = rs.getString(3);
               quantity = rs.getString(4);
               price = rs.getString(5);
               //find username of user foriegn key
               ResultSet rs2 = st2.executeQuery("SELECT * FROM users WHERE id="+ Integer.valueOf(rs.getString(6)));
              if(rs2.next()){
              restocked_by = rs2.getString(2);
              }
              else{
              restocked_by = rs.getString(6);
              }
               date = rs.getString(7);
               String[] row = {id,name,unit,quantity,price,restocked_by,date};
               model.addRow(row);  
              
        }
        // model.addRow(new Object[]{"", "", "","Sum Total", colSum(salesTable,4), "",""});
         st.close();
         conn.close();
        
        
       
      }
      catch(SQLException e){
          System.out.print(e.getMessage());
      }
       
       
        
    } 
 
 
 //LOW Stock Reminders
 
 //get all products with their quantity
  public void loadInventoryFromDatabase() {
        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            String query = "SELECT name, quantity FROM products";
            ResultSet resultSet = st.executeQuery(query);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int quantity = resultSet.getInt("quantity");
                inventory.put(name, quantity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  
  //check and alert low stock
public void checkAndAlertLowStock() {
    loadInventoryFromDatabase();
    
    StringBuilder lowStockMessage = new StringBuilder("Low stock alert:\n");

    for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
        String name = entry.getKey();
        int quantity = entry.getValue();
        if (quantity < 10) {
            lowStockMessage.append(name).append(" (Quantity: ").append(quantity).append(")\n");
        }
    }

    // Display a single dialog with information about all products with low stock
    if (lowStockMessage.length() > 24) { // Check if the message contains more than "Low stock alert:\n"
        alertLowStock(lowStockMessage.toString());
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
    public void actionPerformed(ActionEvent e){
        JDialog.setDefaultLookAndFeelDecorated(true);
         
        //add new products
     if(e.getSource()==addProdButton){
         
          if (productName.getText().equals("")
                  ||productQuantity.getText().equals("")||productUnit.getText().equals("")
                  ||productPrice.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Please enter all fields!","Error!", JOptionPane.ERROR_MESSAGE);
        }
          else if (Integer.parseInt(productQuantity.getText())<= 0
                  ) {
            JOptionPane.showMessageDialog(this, "quantity must be more than zero!","Error!", JOptionPane.ERROR_MESSAGE);
        }  
          
         else if (Float.parseFloat(productPrice.getText())<= 0
                  ) {
            JOptionPane.showMessageDialog(this, "price must be more than zero!","Error!", JOptionPane.ERROR_MESSAGE);
        }    
          else if(searchByName(productName.getText())){//check if product exists
            JOptionPane.showMessageDialog(this, "Product with name "+" "+productName.getText()+"   already exists!","Error!", JOptionPane.ERROR_MESSAGE);
          }
          else{
                       //vriables
      String name =productName.getText();
      //int  id =Integer.parseInt(productId.getText());
      String unit = productUnit.getText();
      int  quantity =Integer.parseInt(productQuantity.getText());
      float price = Float.parseFloat(productPrice.getText());
        
       
          try {  
         Connection conn =  getConnection();
         statement = conn.createStatement();
         PreparedStatement insert = conn.prepareStatement("insert into products (name,unit,quantity,price,added_by)values(?,?,?,?,?)");
//          insert.setInt(1,0);//auto gen in db
          insert.setString(1,name);
          insert.setString(2,unit);
          insert.setInt(3,quantity);
          insert.setFloat(4,price);
          insert.setInt(5,Integer.parseInt(userId));
          insert.executeUpdate();
          JOptionPane.showMessageDialog(this, "Product Successfuly Added");
          
          //clear fields
          productName.setText("");
          //productId.setText("");
          productUnit.setText("kgs");
          productQuantity.setText("");
          productPrice.setText("");
          
          displayAllProductstable();
          allProductsTableShow();
          
          System.out.print("Database Connected");
          statement.close();
          conn.close();
     }
      catch(SQLException ex){
          System.out.println("Db not connected!");
          System.out.print(ex.getMessage());
     }  
          }

 

    }
     
     //display allproducts
     if(e.getSource()==displayButton1){
        displayAllProductstable();
     }
     
     
     //clear products 
     if(e.getSource()==clearButton1){
      table1.setModel(new DefaultTableModel());
     }
     
     
     //dispaya only seached products
     
     if(e.getSource()==searchB1){
     displaySearchedProductstable();
     }
     
     //add product to cat
     
     if(e.getSource()==addB1){
           
           addToCat();
     }
     
     if(e.getSource()==sellB1){
     sellProduct();
     }
     if(e.getSource()==loadAllB){ 
       allProductsTableShow();
     }
     
     if(e.getSource()==searchAllB){
     searchCrudProductsTable();
     }
     
     if(e.getSource()==editProductB){ 
         //check product not picked
         if(allProductsTable.getSelectedRow()<0){
         JOptionPane.showMessageDialog(null, "you must select product","Error!", JOptionPane.ERROR_MESSAGE);
         }
         else{
          //read selected data
         editName.setText(allProductsTable.getValueAt(allProductsTable.getSelectedRow(), 1).toString());
         editUnit.setText(allProductsTable.getValueAt(allProductsTable.getSelectedRow(), 2).toString());
         editQuantity.setText(allProductsTable.getValueAt(allProductsTable.getSelectedRow(), 3).toString());
         editPrice.setText(allProductsTable.getValueAt(allProductsTable.getSelectedRow(), 4).toString());
         
         }
    
     }
     
     if(e.getSource()==saveB){
         
      
      if(editName.getText().equals("")||editUnit.getText().equals("")||editQuantity.getText().equals("")
              ||editPrice.getText().equals("")||allProductsTable.getSelectedRow()<0){
          JOptionPane.showMessageDialog(null, "press edit button","Error!", JOptionPane.ERROR_MESSAGE);
      }
      else{
          try{
           int  quantity =Integer.parseInt(editQuantity.getText());
           float price = Float.parseFloat(editPrice.getText());
           int id = Integer.parseInt(allProductsTable.getValueAt(allProductsTable.getSelectedRow(), 0).toString());   
           Connection conn =  getConnection();
           String sql = "UPDATE products set name='"+editName.getText()+"', unit='"+editUnit.getText()+"', quantity="+quantity+",  price="+price+"  WHERE id ="+id;
           Statement st = conn.createStatement();
           st.executeUpdate(sql);
           editName.setText("");
           editUnit.setText("");
           editQuantity.setText("");
           editPrice.setText("");
           allProductsTableShow();//reload the table
           JOptionPane.showMessageDialog(this, "Product Updated Succesfuly");
          }
          catch(SQLException ex){
          System.out.println(ex.getMessage());
          }
      
      }
      
      
     
     }
     
     
     if(e.getSource()==deleteB){
        if(allProductsTable.getSelectedRow()<0){
        JOptionPane.showMessageDialog(this, "select product","Error!",JOptionPane.ERROR_MESSAGE);
        }
        else{
        try{
            Connection conn =  getConnection();
            Statement st = conn.createStatement();
            int id = Integer.parseInt(allProductsTable.getValueAt(allProductsTable.getSelectedRow(), 0).toString());
            String sql = "DELETE FROM products WHERE id = "+id;
            String name = allProductsTable.getValueAt(allProductsTable.getSelectedRow(), 1).toString();
            //confirm
            JDialog.setDefaultLookAndFeelDecorated(true);
            int response = JOptionPane.showConfirmDialog(null, "Do you want to delete "+name+" ?", "Confirm",
        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION){
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(this, " "+name+" deleted ");
            allProductsTableShow();
            displayAllProductstable();
          
        }
                
            
        } 
          
        catch(SQLException ex){
          System.out.println(ex.getMessage());
          }
            
            
        }
     
     }
     
     if(e.getSource()==addB2){
      addToRestockTable();
     }
     
     if(e.getSource()==restockB1){
     restockProducts();
     }
     
     if(e.getSource()==createUserB){
     
     createUser();
     usersTableShow();
     }
     
     if(e.getSource()==editUserB){
     upDateUser();
     
     }
     
     if(e.getSource()==processB){
        salesTableReport();
     }
     
     if(e.getSource()==processB1){
         restockTableReport();
    }
     
     
    }
    
  
    
    
    
    public static void main(String[] args) {
      
       if(isAuthenticated){
     
        new Shop();
       
    }
       
       
       else{
           new LoginPage();
       }
       
}

}
