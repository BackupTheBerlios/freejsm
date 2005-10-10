/*
 * CustomJDialog.java
 *
 * Created on 17 février 2004, 18:03
 */

package com.develog.swing;

import java.awt.LayoutManager;
import java.util.Vector;
/**
 *
 * @author  yann
 */
public class CustomJPanel extends javax.swing.JPanel {
    public void handleJTableClick(String val) {}
    public void handleJTableClick(int row) {}
    public void handleJTableClick(String val, String param) {}
    public void handleJTableClick(int row,Vector obj) {}
    
    protected boolean rootPaneCheckingEnabled = false;

        protected boolean isRootPaneCheckingEnabled() {
        return rootPaneCheckingEnabled;
    }
/*    public void setLayout(LayoutManager manager) {
        this.getContentPane().setLayout(manager);
    }
    private Error createRootPaneException(String op) {
        String type = getClass().getName();
        return new Error(
            "Do not use " + type + "." + op + "() use " 
                          + type + ".getContentPane()." + op + "() instead");
    }

 */   
}
