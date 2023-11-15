/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shop;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

/**
 *
 * @author odipojames12
 */
public class JFloatListener extends KeyAdapter  {
    
    
     private final JTextField textField;
    
    public JFloatListener(JTextField textField) {
        this.textField = textField;
    }
    
    
    @Override
    public void keyTyped(KeyEvent e) {
      super.keyTyped(e);
      char c = e.getKeyChar();
      //float
      // Allow digits, dot, and backspace
                if (!(Character.isDigit(c) || c == '.' || c == KeyEvent.VK_BACK_SPACE)) {
                    e.consume(); // Ignore the event
                }

                // Only allow one dot
                if (c == '.' && textField.getText().contains(".")) {
                    e.consume();
                }
        
    
      
    }
    
}
