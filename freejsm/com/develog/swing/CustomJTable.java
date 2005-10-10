/*
 * CustomJTable.java
 *
 * Created on 7 novembre 2002, 19:37
 * Labayle Alexandre
 * Code cédé à la société Dévelog
 */

 //Designation du package

/** Importation des classes essentielles */
package com.develog.swing;

import javax.swing.JTable;
import javax.swing.table.*;
import java.sql.*;
import java.sql.Types;
import java.util.Vector;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.*;
import java.text.SimpleDateFormat;
import java.util.EventObject;
import java.util.Locale;
import java.text.DateFormat;
import java.util.Date;
import java.lang.Integer;
import java.lang.Object;
import com.develog.swing.AlternateCellTableModel;

/**
 *
 * Cette classe permet d'avoir une classe JTable optimisï¿½e pour les interactions
 * avec les bases de donnees.
 */
public class CustomJTable extends JTable {
    
    public  ResultSet         CurrentResultSetData  = null;
    private DefaultTableModel CustomTableModel;
    public  ResultSetMetaData CurrentResultSetMD    = null;
    private String            InternalQuery         = null;
    private Connection        InternalConnection    = null;
    private int               compteurInterne       = 0 ;
    private Vector            SizeVector            = new Vector();
    public CustomJPanel       papounet              = null;
    public String             paramString           = null;
    public boolean            flagclose             = false;
    public Vector             nameColumnsVector     = new Vector();
    public Vector             DataVector            = new Vector();
    public AlternateCellTableModel topModel = new AlternateCellTableModel();
    
    public CustomJTable() {}
    public CustomJTable(Connection ExternalConnection , String ExternalQuery) throws Exception {
           
       InternalQuery      = new String(ExternalQuery);
       InternalConnection = ExternalConnection;
       JDialog papounet = null;
       topModel.setColors(new Color(255,255,230), Color.BLACK);
       topModel.setColorsAlt(new Color(230,255,230),Color.BLACK);
       topModel.setInterval(1);
       InitCompoment();
    }
    
    public CustomJTable(Connection ExternalConnection , String ExternalQuery , CustomJPanel papa) throws Exception {
           
       InternalQuery      = new String(ExternalQuery);
       InternalConnection = ExternalConnection;
       papounet   = papa;
       topModel.setColors(new Color(255,255,230), Color.BLACK);
       topModel.setColorsAlt(new Color(230,255,230),Color.BLACK);
       topModel.setInterval(1);
       InitCompoment();
    }
    
    public CustomJTable(Connection ExternalConnection , String ExternalQuery , CustomJPanel papa, String param) throws Exception {
           
       InternalQuery      = new String(ExternalQuery);
       InternalConnection = ExternalConnection;
       papounet   = papa;
       paramString = param;
       topModel.setColors(new Color(255,255,230), Color.BLACK);
       topModel.setColorsAlt(new Color(230,255,230),Color.BLACK);
       topModel.setInterval(1);
       InitCompoment();
    }
    
    // Remonte les donnees de la requete demande.
    private ResultSet LoadDBData() throws Exception {
       
       PreparedStatement PreparedInternal                = null;
       // On execute la remonte d'info afin d'avoir un ResultSet avec lequel
       // travailler.
       try{
            PreparedInternal  = InternalConnection.prepareStatement(InternalQuery);
       }
       catch(SQLException e) {
            throw new Exception("[CustomJTable][LoadDBData] : \n" + e );}
              
       return(PreparedInternal.executeQuery());
    }
    
    // Remonte le schéma de les champs de table demande sous forme de vecteur//
    private Vector LoadDBMetaData(ResultSet ResultSetInternalDataStream) throws Exception {  
           
        ResultSetMetaData ResultSetInternalMetaData       = null;
        Vector            InternalColumnsName             = new Vector();
           
        try{
           ResultSetInternalMetaData = ResultSetInternalDataStream.getMetaData();
           for (int Comp=1;Comp <= ResultSetInternalMetaData.getColumnCount();Comp++){
               /* On ajoute les colonnes en plus */
               InternalColumnsName.addElement(ResultSetInternalMetaData.getColumnLabel(Comp));
           }
       }
       catch(SQLException e){
           throw new Exception("[CustomJTable][LoadDBMetaData]: \n" + e);}
       
       return(InternalColumnsName);
    }
    
    
    // Remonte le schéma de la table demande sous forme de resultset//
    private ResultSetMetaData GiveRSMetaData(ResultSet ResultSetInternalDataStream) throws Exception {  
           
        ResultSetMetaData ResultSetInternalMetaData       = null;
           
        try{
           ResultSetInternalMetaData = ResultSetInternalDataStream.getMetaData();
       }
       catch(SQLException e){
           throw new Exception("[CustomJTable][GiveRSMetaData]: \n" + e);}
       
       return(ResultSetInternalMetaData);
    }
    
    
    /** Cette methode permet de retourner la prochaine ligne */
     private Vector GetNextRow(ResultSet InternalDataStream) throws Exception {
         
         Vector currentRow = new Vector();
         SimpleDateFormat dateFormat  = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
       
         try{
         for (int Comp=1;Comp <= CurrentResultSetMD.getColumnCount();Comp++)
             switch(CurrentResultSetMD.getColumnType(Comp))
             {			
                 case Types.FLOAT:
                 {  currentRow.addElement(new Float(InternalDataStream.getFloat(Comp)));
                    DataVector.addElement(new Float(InternalDataStream.getFloat(Comp)));
                 }

                 break;
                 case Types.DOUBLE:
                 {
                     currentRow.addElement(new Double(InternalDataStream.getDouble(Comp)));
                     DataVector.addElement(new Double(InternalDataStream.getDouble(Comp)));
                 }
                 break;
                 case Types.VARCHAR:
                 {

                     currentRow.addElement(InternalDataStream.getString(Comp));
                     DataVector.addElement(InternalDataStream.getString(Comp));
       
                    
                 }
                 break;
                 case Types.CHAR:
                 {
                     currentRow.addElement(InternalDataStream.getString(Comp));
                     DataVector.addElement(InternalDataStream.getString(Comp));
                     // MaxiSize(Comp, InternalDataStream.getString(Comp));
           
                     
                 }
                 break;
                 case Types.INTEGER:
                 {
                     currentRow.addElement(new Long(InternalDataStream.getLong(Comp)));
                     DataVector.addElement(new Long(InternalDataStream.getLong(Comp)));
                 }
                 break;
                 case Types.TIMESTAMP:
                 {
                     currentRow.addElement(InternalDataStream.getDate(Comp));
                     DataVector.addElement(InternalDataStream.getDate(Comp));
                 }
                 break;
                 case Types.DATE:
                 {
    
                     if ( InternalDataStream.getDate(Comp) == null ) {
                         currentRow.addElement("");
                         DataVector.addElement("");
                     }
                     else {
                         currentRow.addElement(dateFormat.format(InternalDataStream.getDate(Comp)));
                         DataVector.addElement(dateFormat.format(InternalDataStream.getDate(Comp)));
                     }
              
                 }
                 break;
                 case Types.TINYINT:
                 {
                     currentRow.addElement(new Boolean(InternalDataStream.getBoolean(Comp)));
                     DataVector.addElement(new Boolean(InternalDataStream.getBoolean(Comp)));
                 }
                 break;
                 default: 
                 {
                     currentRow.addElement(new Integer(0));
                     DataVector.addElement(new Integer(0));
                 }
             }
             
             
         }catch(Exception e){
             throw new Exception("[CustomJTable][GetNextRow] Anomalie : \n " + e);}
         
         return currentRow;
     }
     
    /** Cette methode compare juste 2 tailles et stocke la plus grande dans un vecteur */
     private void MaxiSize (int curseur , String valeur){
         
         int longeur = 0;
         /* Previent les crashs à cause des valeurs null dans la base MySQL */
         //if ( Tempo.substring(0,1).compareTo("R") != 0 ) valeur = "0";
         //longeur = valeur.length();
         System.out.println(valeur + ":" + valeur.compareTo(""));
         //if ( new Integer(longeur).compareTo((Integer)SizeVector.elementAt(curseur-1)) >= 0 )
           // SizeVector.setElementAt(new Integer(longeur), curseur-1); 
     }
     
    /** Initialise le premier vecteur de taille des colonnes */
     private Vector FirstVector(ResultSet InternalDataStream) throws Exception {
     
         Vector InitializeVector = new Vector();
     try{
         for (int Comp=1;Comp <= CurrentResultSetMD.getColumnCount();Comp++)
         InitializeVector.addElement(new Integer(0)); 
     }catch(Exception e){
             throw new Exception("[CustomJTable][FirstVector] Anomalie initialisation Vector de taille: \n " + e);}
             
        return(InitializeVector);
     }
     
    /** Cette methode formate l'affichage des types a l'ecran */
     private void SetColumnDisplayType() throws Exception{

        for (int Comp=1;Comp <= CurrentResultSetMD.getColumnCount();Comp++)
             switch(CurrentResultSetMD.getColumnType(Comp))
             {			
                case Types.TINYINT:{
                    this.getColumnModel().getColumn(Comp-1).setCellRenderer(new CheckCellRenderer());
                    this.getColumnModel().getColumn(Comp-1).setCellEditor(new DefaultCellEditor(new JCheckBox()));
                }       
                break;
                case Types.BOOLEAN:{
                    this.getColumnModel().getColumn(Comp-1).setCellRenderer(new CheckCellRenderer());
                    this.getColumnModel().getColumn(Comp-1).setCellEditor(new DefaultCellEditor(new JCheckBox()));
                }
                case Types.BIT:{
                    this.getColumnModel().getColumn(Comp-1).setCellRenderer(new CheckCellRenderer());
                    this.getColumnModel().getColumn(Comp-1).setCellEditor(new DefaultCellEditor(new JCheckBox()));
                }  
                default:{
                this.getColumnModel().getColumn(Comp-1).setCellRenderer(topModel);
                }
             }
    }
     
    private void SetColumnMaxSize(ResultSet InternalDataStream) throws Exception {
        
        try{
         for (int Comp=1;Comp <= CurrentResultSetMD.getColumnCount();Comp++) 
            //System.out.println(SizeVector.get(Comp-1));
            this.getColumnModel().getColumn(Comp-1).setMaxWidth(((Integer)SizeVector.elementAt(Comp-1)).intValue()*10);
            }catch(Exception e){
            throw new Exception("[CustomJTable][SetColumnMaxSize] Anomalie durant le formatage des colonnes \n " + e);}
    }
    
     private void LoadDataIntoTable(ResultSet InternalDataStream) throws Exception {
        
        Vector test = new Vector();
         /** Cette methode initialise le remplissage de la table */
        try{
            while(InternalDataStream.next()){
            /** détermination du type de la colonne */  
        CustomTableModel.addRow(GetNextRow(InternalDataStream));}
        CustomTableModel.fireTableDataChanged();
        }catch(Exception e){
            throw new Exception("[CustomJTable][LoadDataIntoTable] Impossible d'obtenir le prochain élément : \n" + e);} 
        SetColumnDisplayType();
        //SetColumnMaxSize(InternalDataStream);
    }  
        
    /* Cette méthode initialise les composants interne de la JTable */
    private void InitCompoment() throws Exception {
       
       try{
       CurrentResultSetData = LoadDBData();
       CurrentResultSetMD   = GiveRSMetaData(CurrentResultSetData);
       //SizeVector = FirstVector(CurrentResultSetData);
       nameColumnsVector = LoadDBMetaData(CurrentResultSetData);
       UpdateTableModel(nameColumnsVector);
       LoadDataIntoTable(CurrentResultSetData);
       }
       catch(Exception e){
           throw new Exception("[CustomJTable][InitCompoment] Impossible d'initialiser le composant : \n" + e);}
    }
    
    /* Cette méthode permet de changer la Query courante */
    public void NewQuery(String NewQuery){
        InternalQuery = new String(NewQuery);
    }
    
    
    /* Cette méthode permet de recharger la table avec la Query locale*/
    public void ReLoadTable() throws Exception {
        try{ 
            InitCompoment();}
        catch(Exception e){
            throw new Exception("[CustomJTable][ReLoadTable] Rechargement de la table impossible : \n" + e);}
    }
    
    /* Affecte le modele standard de la table */   
    private void UpdateTableModel(Vector InternalColumnsName){ 
       CustomTableModel = new DefaultTableModel(null, InternalColumnsName);
       this.setModel(CustomTableModel);
    }
    
     public boolean editCellAt(int row,int column,EventObject e){ 
         //System.out.println(""+this.getValueAt(row,0)); 
         if(papounet != null)
             if(paramString != null)
                papounet.handleJTableClick(""+this.getValueAt(row,0),paramString);
             else
                papounet.handleJTableClick(""+this.getValueAt(row,0));
         return false;
     }
}