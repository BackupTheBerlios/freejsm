/*
 * SmartJTable.java
 *
 * Labayle Alexandre
 * Cette classe permet de créer une table qui s'appuie sur un cube.
 */

 //Designation du package

/** Importation des classes essentielles */
package com.develog.swing;

import javax.swing.JTable;
import com.develog.utils.SmartConnector;
import com.develog.utils.SmartCube;
import com.develog.swing.SmartJTableModel;
import javax.swing.event.*;
import javax.swing.table.TableModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import java.util.Enumeration;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JCheckBox;
import java.sql.Connection;
import java.sql.SQLException;
import java.awt.*;
import java.awt.event.*; 
import java.util.Hashtable;
import java.util.HashMap;

//import com.develog.swing.SmartJTableRenderer;

/**
 *
 * Cette classe permet d'avoir une classe JTable optimisee pour les interactions
 * avec les bases de donnees.Celle-ci repose sur un cube de données et permet d'
 * en afficher et de modifier ses données.Cette table devients sensible aux changements
 * de données grace à son TableModelListener.
 * implements TableModelListener
 */
public class SmartJTable extends JTable  {
    
    private SmartCube intsc;
    private Vector internalColName = new Vector();
    private Vector internalRow     = new Vector();
    private SmartJTableModel TableModelt ;
    private Hashtable intHt = new Hashtable();
    private HashMap   intHm = new HashMap();
    
    /** Permet à la table de construire son propre SmartCube bof bof bof */
    public SmartJTable(Connection connex,String Query) throws Exception {
 
        //TODO lever un peux mieux les excpetions !!!
     try{
        intsc = new SmartCube(connex);
        intsc.setQuery(Query);
        intsc.loadCube();
        
        
        if (intsc.getRowCount() != 0) intsc.getFirst();
        TableModelt = new SmartJTableModel(intsc,intHt,intHm);
        this.setModel(TableModelt);
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        

            ListSelectionModel rowSM = this.getSelectionModel();
            new SmartJTableRenderer(this);
        }catch(Exception e){System.out.println(e.toString());
        }     
    }
    
    /** Ce modéle permet d'interagir avec un smartCube déja existant */
    public SmartJTable(SmartCube sc,Hashtable ht,HashMap hm) throws Exception {
        intsc = sc;
        intHt = ht;
        intHm = hm;
        
        if (intsc.getRowCount() != 0) intsc.getFirst();
            TableModelt = new SmartJTableModel(intsc,intHt,intHm);
            this.setModel(TableModelt);
            this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        try{
            ListSelectionModel rowSM = this.getSelectionModel();
            new SmartJTableRenderer(this);
        }catch(Exception e){System.out.println(e.toString());
        } 
    }
    
    
    public void setQuery(String Query){
       intsc.setQuery(Query);
    }
     
    public SmartJTable(Connection connex) throws SQLException {
        intsc = new SmartCube(connex);
        System.out.println("Warning:Aucune Query fixée pour l'instant");
    }
    
    public void loadData() throws Exception{
        
        intsc.loadCube();
        if (intsc.getRowCount() != 0) intsc.getFirst();
        TableModelt = new SmartJTableModel(intsc,intHt,intHm);
        this.setModel(TableModelt);
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        try{
            ListSelectionModel rowSM = this.getSelectionModel();
            new SmartJTableRenderer(this);
        }catch(Exception e){System.out.println(e.toString());
        }     
    }
    
    public void change(){
        /** Synchronisation entre le cube et la base de données
         * celle-ci entraine un évenement qui force la table à ce recharger */
        TableModelt.fireTableDataChanged();
    }
    
}
