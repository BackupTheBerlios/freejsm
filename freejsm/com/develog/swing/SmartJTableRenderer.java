/*
 * SmartJTableRederer.java
 *
 * Created on 22 juin 2004, 18:34
 */

package com.develog.swing;
import com.develog.swing.SmartJTable;
import javax.swing.JCheckBox;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.DefaultCellEditor;



/**
 *
 * @author  alexandre
 */
public class SmartJTableRenderer {
    
    /** Creates a new instance of SmartJTableRederer */
    public SmartJTableRenderer(SmartJTable sjt) throws Exception {
    
        for (int loop=1;loop < sjt.getModel().getColumnCount();loop++){
           if (sjt.getModel().getColumnClass(loop).isAssignableFrom(java.lang.Boolean.class)){
               sjt.getColumnModel().getColumn(loop).setCellRenderer(new CheckCellRenderer());
               sjt.getColumnModel().getColumn(loop).setCellEditor(new DefaultCellEditor(new JCheckBox()));
           }
           if (sjt.getModel().getColumnClass(loop).isAssignableFrom(java.sql.Date.class)){

              // sjt.getColumnModel().getColumn(loop).setCellRenderer(new CheckCellRenderer());
              // sjt.getColumnModel().getColumn(loop).setCellEditor(new DateCellEditor());
           }
           }
       }
    
   /* Permet de convertir une chaine de caract�re dd/MM/yyyy */
   /* Au format attendu par MySQL (yyyy-dd-MM)               */
   public String MySQLDate(String DateFormatee){       
       return((DateFormatee.substring(6,10) + "-"  + DateFormatee.substring(3,5)) + "-" + DateFormatee.substring(0,2)) ;
   }
}

