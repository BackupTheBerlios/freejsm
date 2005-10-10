/*
 * JFormattedRenderer.java
 *
 * Created on 12 novembre 2002, 16:31
 */

package classes;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.util.*;

/**
 *
 * @author  ala
 */
public class JFormattedRenderer extends JFormattedTextField implements TableCellRenderer {
    
    /** Creates a new instance of JFormattedRenderer */
    public JFormattedRenderer() {
    super();
    setOpaque(true);
    }
    public Component getTableCellRendererComponent(JTable table, 
    Object value, boolean isSelected, boolean hasFocus, 
    int row, int column) 
    { 
        setBackground(isSelected && !hasFocus ? 
        table.getSelectionBackground() : table.getBackground()); 
        setForeground(isSelected && !hasFocus ? 
        table.getSelectionForeground() : table.getForeground()); 
        setFont(table.getFont()); 
        this.setBorder(null);
        try{
            this.setFormatter(new MaskFormatter("##/##/####"));
            this.setText((String)value);
            return this;
        }
        catch(java.text.ParseException e){System.out.println(e.toString());}
        return this;
    }
}
