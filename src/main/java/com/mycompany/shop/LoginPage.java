/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.border.Border;
import java.sql.*;
import static com.mycompany.shop.Shop.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;




public class LoginPage  implements ActionListener {
       JLabel password1, label;
       JTextField username;
       JButton button;
       JPasswordField Password;
       JFrame frame;

    LoginPage(){
        JDialog.setDefaultLookAndFeelDecorated(true);
      // creating a JPanel class
        JPanel panel = new JPanel();
        Border blackline = BorderFactory.createTitledBorder("Login");
        panel.setLayout(null);
        panel.setBorder(blackline);
        frame = new JFrame();
        frame.setTitle("LOGIN TO MY SHOP");
        
        
        
 
       
        frame.add(panel);
        frame.setSize(new Dimension(1000, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        
        //pic
        JLabel imageLabel = new JLabel("image");
        imageLabel.setIcon(new ImageIcon(this.getClass().getResource("store-icon.jpg"))); 
        imageLabel.setBounds(30, 60, 480, 400);
        panel.add(imageLabel);
        
        // Username label constructor
        label = new JLabel("Username");
        label.setBounds(520, 100, 70, 20);
        panel.add(label);
        
        // Username TextField constructor
        username = new JTextField();
        username.setBounds(520, 130, 193, 28);
        panel.add(username);
        // Password Label constructor
        password1 = new JLabel("Password");
        password1.setBounds(520, 160, 70, 20);
        panel.add(password1);
        // Password TextField
        Password = new JPasswordField();
        Password.setBounds(520, 190, 193, 28);
        panel.add(Password);
        
        // Button constructor
        button = new JButton("Login");
        button.setBounds(550, 240, 90, 25);
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
        button.addActionListener((this));
        panel.add(button);
        frame.setVisible(true);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("store-icon.jpg")));
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we)
            { 
                String ObjButtons[] = {"Yes","No"};
                int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to exit?","Online Examination System",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
                if(PromptResult==JOptionPane.YES_OPTION)
                {
                    System.exit(0);
                }
            }
        });
        
    

    }
    
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
    String Username = username.getText();
    String Password1 = String.valueOf(Password.getPassword());
     if(Username.equalsIgnoreCase("")|| Password1.equalsIgnoreCase("")){
          JOptionPane.showMessageDialog(null, "username and password required!","Error!", JOptionPane.ERROR_MESSAGE);
          }
   else{
        //log in     
      try{
        java.sql.Connection conn = getConnection(); 
        Statement st = conn.createStatement();
        String sql = "SELECT * FROM users WHERE username = '"+Username + "' AND  password = '"+Password1+"' ";
        ResultSet rs =  st.executeQuery(sql);
        if(rs.next()){
         JOptionPane.showMessageDialog(null, "You'ved loged in successfuly!");
         frame.dispose();
         Shop.logUser = Username;
         Shop.isAuthenticated = true;
         Shop.role = rs.getString(4);
         Shop.userId = rs.getString(1);
        Shop b1 = new Shop();
        //loading data to window
        b1.displayAllProductstable();
        b1.usersTableShow();
        b1.allProductsTableShow();
       
         
         //new addB1(); 
        }
        else{
        JOptionPane.showMessageDialog(null, "password and username do not match!","Error!", JOptionPane.ERROR_MESSAGE);
        }
        st.close();
        conn.close();
      }
      catch(SQLException ex){
          System.out.print(ex.getMessage());
      }
         
               
    }
     
 }
    
}
   


