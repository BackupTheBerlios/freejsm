
package com.develog.swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;

class CheckCellRenderer extends JCheckBox implements TableCellRenderer 
{ 

public CheckCellRenderer() { 
super(); 
setOpaque(true); 
} 

public Component getTableCellRendererComponent(JTable table, 
Object value, boolean isSelected, boolean hasFocus, 
int row, int column) 
{ 
if (value instanceof Boolean) { 
Boolean b = (Boolean)value; 
setSelected(b.booleanValue()); 
} 

setBackground(isSelected && !hasFocus ? 
table.getSelectionBackground() : table.getBackground()); 
setForeground(isSelected && !hasFocus ? 
table.getSelectionForeground() : table.getForeground()); 

setFont(table.getFont()); 

return this; 
} 
} 
