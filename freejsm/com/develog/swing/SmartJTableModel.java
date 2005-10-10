/*
 * SmartJTableModel.java
 *
 * Created on 21 juin 2004, 13:30
 */

package com.develog.swing;
import javax.swing.table.AbstractTableModel;
import java.util.Vector;
import com.develog.utils.SmartCube;
import javax.swing.event.*;
import javax.swing.table.TableModel;
import java.util.Hashtable;
import java.util.HashMap;
import javax.swing.JTable;
import java.util.Enumeration;
/**
 *
 * @author  alexandre
 * Cette classe permet de définir le comportement de la table en fonction
 * des donneés.Il s'agit d'une sorte de Masque qui fixe la représentation de la 
 * table.
 */
public class SmartJTableModel extends AbstractTableModel implements TableModelListener {
    
    private SmartCube intSc;
    private Hashtable Mapping      = new Hashtable();
    private HashMap   invisibleCol = new HashMap();
    private HashMap   translatCol  = new HashMap();

    
    /** Creates a new instance of SmartJTableModel */
    public SmartJTableModel(SmartCube sc) {
        intSc = sc; 
    }
    
    /** Permet de créer une table avec des noms de colonnes différents de ceux
     *  de la base et de masquer également des colonnes*/
    public SmartJTableModel(SmartCube sc,Hashtable mapExt,HashMap invisibleColExt) {
        intSc      = sc; 
        Mapping    = mapExt;
        invisibleCol = invisibleColExt;
        translatCol=translateColNum();
    }
    
    public int getColumnCount() {
        try{
            return(intSc.getColumnCount()-invisibleCol.size()); 
        }catch(Exception e){System.out.println("Anomalie en comptage de colonne");}
        return 0;
    }
    
    public String getColumnName(int col) {
        try{
            if (Mapping.size()==0) return intSc.getColumnName(getTranslateValue(col)+1);
            if (Mapping.containsKey(intSc.getColumnName(getTranslateValue(col)+1))) return ""+Mapping.get(intSc.getColumnName(getTranslateValue(col)+1));
            return intSc.getColumnName(getTranslateValue(col)+1);
        }catch(Exception e){return new String();}
    }
    
    public int getRowCount() {
       return(intSc.getRowCount());
    }
    
    public Object getValueAt(int numRow, int numCol ) {
        try{
             intSc.getSpecific(numRow+1);
             return intSc.getCubeDataObject(getTranslateValue(numCol)+1);
        }catch(Exception e){System.out.println("Ne peut remonter l'object demandé : " + e);}
        return null;
    } 
   
   public Class getColumnClass(int c) { 
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col) {
        if (col < 100) {
            return false;
        } else {
            return true;
        }
    }
    
  public void setValueAt(Object value, int row, int col) {
       Hashtable tempo = new Hashtable();
       try{
            tempo.put(intSc.getColumnName(col+1),value);
            intSc.getSpecific(row+1);
            intSc.setCubeData(tempo);
            intSc.commitCubeTransaction();
            intSc.commitDBTransaction();
       }catch(Exception e){System.out.println("Ne peut pas "+e);};
    
            fireTableCellUpdated(row, col);
    }
  
    
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        TableModel model = (TableModel)e.getSource();
        String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);
        fireTableDataChanged();
    }
    
    /** Cette méthode génére une table de correspondance entre les références
     *  du cube et les références visibles et nom */
    private HashMap translateColNum(){
     
     try{   
      HashMap tablStc = new HashMap();
      
      int nbCubeCol = intSc.getColumnCount();
      int nbTablCol = invisibleCol.size();
      int idxCube   = 0;
      int idxTable  = 0;
         
      for (idxCube = 0; idxCube <= nbCubeCol-1 ; idxCube++){
        if( !invisibleCol.containsKey(intSc.getColumnName(idxCube+1))) {
            tablStc.put(new Integer(idxTable),new Integer(idxCube));
            //System.out.println(intSc.getColumnName(idxCube+1) + ":"+ idxCube + "--Mappé-->" + idxTable);
            idxTable ++;
        }
      }
      
      return tablStc;
     }catch(Exception e){System.out.println(e);
     return new HashMap();}
    }
    
   
    private int getTranslateValue(int value){
        return (new Integer(""+translatCol.get(new Integer(""+value))).intValue());
    }
    
    /** Cette méthode génére une table de correspondance entre le nom de la 
     *  colonne présente dans le cube et celle fournie par l'utilisateur   */
    public void translateColName(){
        
    }
    
    
    
    
}
