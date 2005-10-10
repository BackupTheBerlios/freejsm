/*
 * smartcube.java
 *
 * Created on 7 juin 2004, 10:44
 */

/**
 *
 * @author  alexandre
 * Le smartcube est l'élément de base de la gestion automatique Smart.
 * C'est lui qui contient les données remontées de la base de données et permet 
 * d'effectuer les opérations de base sur les enregistrements de la base.
 * Le smartCube emet des évenements qui indiques si celui-ci a été modifié ou pas
 * si il est synchronisé ou pas...Bien pratique pour les objets qui l'implémente
 * car ceux-ci sont tenus informés de l'état des données en dessous d'uex.
 * TODO:Permettre d'avoir une fonction de raffinement de requettes SQL
 *      Les requettes pourraient etre stockées dans un fichier XML,on ne passerai
 *      alors que les paramétres de celles-ci(code moins pollué).
 *      Gestion propre de la remontée d'exeption par une classe  SmartException
 *      qui pourrait étre handlée avec une base ou un fichier (log).Cette class
 *      pourrait afficher un mode DEBUG.
 */

package com.develog.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.Hashtable;
import java.util.Collections;
import java.util.Enumeration;
import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.awt.*;
import java.util.EventListener;

public class SmartCube {
    
    private String internalQuery         = null;
    private Connection internalConnex    = null;
    private ResultSet internalRS         = null;
    private ResultSetMetaData internalMD = null;
    private int ColumnNumber = 0;
    public  Vector ColumnName = new Vector();
    private static int type = ResultSet.TYPE_SCROLL_SENSITIVE;
    private static int mode = ResultSet.CONCUR_UPDATABLE;
    private Statement internalStatement ;
    private PreparedStatement internalPS;
    private ParameterMetaData internalPMD;
    private SimpleDateFormat dateFormat            = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
    private Vector smartListeners = new Vector();
    
    /** Initialise les structures du smartCube */
    public SmartCube(Connection connex) throws SQLException {
        internalConnex     = connex;
        internalConnex.setAutoCommit(false);
        internalStatement  = connex.createStatement(type, mode);
    }
    
    public SmartCube(String query,Connection connex) throws SQLException {
        internalConnex     = connex;
        internalConnex.setAutoCommit(false);
        internalStatement  = connex.createStatement(type, mode);
        internalQuery      = query;
    }
    
    
    /** Positione la requete que l'on veut dans le cube */
    public void setQuery(String query){   
        internalQuery      = query;
    }
    
    /** Charge le cube avec les données issue de la requete */
    public void loadCube() throws Exception {    
     try{
         internalMD = null;
         internalRS = null;
         ColumnNumber = 0;
         internalRS = internalStatement.executeQuery(internalQuery);
         //internalRS = internalPS.executeQuery(internalQuery);
         internalMD = internalRS.getMetaData();
         ColumnName = getColumnName();
         setRowCount();
         this.getFirst();
         
     }catch(Exception e){
        throw new Exception("[smartCube][loadCube]Impossible de charger le cube:" + e);}
     }
   
    /* Cette méthode permet d'extraire le resultSet du cube */
    public ResultSet getResultSetFromCube(){
    
        return internalRS;
    }
    /* Raffraichit l'enregistrement courant avec les données de la base
     * .On teste si la ligne à bougée sinon on ne fait rien */
    public void refreshCubeRow() throws Exception{
     try{
         internalRS.refreshRow();
        }catch(Exception e){
            throw new Exception("[smartCube][refreshCubeRow]Impossible de rafraichir la ligne dans le cube:" + e);}
    }
    
    /** met à jour la base de données avec les données du cube */
    public void commitDBTransaction() throws Exception {
        try{
            internalConnex.commit();
        }catch(Exception e){throw new Exception("[smartCube][commitDBTransaction]Impossible de commiter les modifications dans la base" + e);}
    }
    
    /** Annule la derniere transaction */
    public void rollBackDBTransaction() throws Exception {
        try{
            internalConnex.rollback();
        }catch(Exception e){throw new Exception("[smartCube][rollBackDBTransaction]Impossible d'annuler les modifications sur la base" + e);}       
    }
    
    /** Annule la derniere modification sur le cube ,cette modifi n'as aucun effet
     *  si un commit a été effectué ou si une opération de nature updateRow ont 
     *  été effecutés */
    public void rollBackCubeTransaction() throws Exception {
        try{
            internalRS.cancelRowUpdates();
        }catch(Exception e){throw new Exception("[smartCube][rollBackCubeTransaction]Impossible d'annuler les modifications sur le cube" + e);}       
    }
    
    /** Valide la derniére transaction dans le cube */
    public void commitCubeTransaction() throws Exception {
        try{
            internalRS.updateRow();
        }catch(Exception e){throw new Exception("[smartCube][commitCubeTransaction]Impossible de valider les modifications sur le cube" + e);}       
    }
    

    
/* ----------Méthodes d'affectations des données du cube ---------------------*/
    
    /** Fixe une valeur du cube pour l'enregistrement courant  */
    public void setCubeData(Hashtable clefruche ) throws Exception{
        Object obj = null;
        int    idx = 0;
        long  datebidon = 0;
        try{
            for (Enumeration e = clefruche.keys() ; e.hasMoreElements() ;){
            obj = e.nextElement(); 
            idx = isPresent(obj.toString());
            switch(internalMD.getColumnType(idx)){
                case Types.FLOAT:{internalRS.updateFloat(idx , ((Float)clefruche.get(obj)).floatValue());
                }break;
                case Types.REAL:{internalRS.updateFloat(idx , ((Float)clefruche.get(obj)).floatValue());
                }break;
                case Types.DOUBLE:{internalRS.updateDouble(idx , ((Double)clefruche.get(obj)).doubleValue());
                }break;
                case Types.VARCHAR:{internalRS.updateString(idx , (String)clefruche.get(obj));
                }break;
                case Types.CHAR:{internalRS.updateString(idx , (String)clefruche.get(obj));
                }break;
                case Types.INTEGER:{internalRS.updateInt(idx , ((Integer)clefruche.get(obj)).intValue());
                }break;
                case Types.TIMESTAMP:{}break;
                case Types.DATE:{internalRS.updateDate(idx , (java.sql.Date)clefruche.get(obj));
                }break;
                case Types.TINYINT:{internalRS.updateBoolean(idx , ((Boolean)clefruche.get(obj)).booleanValue());
                }break;
            }
          } 
        }catch(Exception e){
            throw new Exception("[smartCube][setCubeData]Impossible de fixer la colone " + obj.toString() + " :"+e);
        }       
        
    }

    
    /** Ajoute une nouvelle ligne au cube de données,
     *  cette ligne deviens l'enregistrement courant */
    public void startNewCubeRowInsertion() throws Exception{
        try{
            internalRS.moveToInsertRow();
        }catch(SQLException e){throw new Exception("[smartCube][startNewCubeRowInsertion]Impossible d'initialiser une nouvelle ligne dans le cube" + e);}       
    }
    
    /** Ajoute définitivement au cube de données
     * la nouvelle ligne ,le rollback n'est plus possible */
    public void finaliseNewCubeRowInsertion() throws Exception{
        try{
            internalRS.insertRow();
            internalRS.moveToCurrentRow();
            internalRS.last();
        }catch(SQLException e){throw new Exception("[smartCube][finaliseNewCubeRowInsertion]Impossible d'ajouter la nouvelle ligne dans le cube" + e);}       
    }
    
    /** Supprimer l'enregistrement courant */
    public void deleteCubeRow() throws Exception{
        try{
            internalRS.deleteRow();
        }catch(SQLException e){throw new Exception("[smartCube][deleteCubeRow]Impossible de supprimer la ligne du cube" + e);}       
    }
        
//------------recupération des données ---------------------------------------*/    
    public Boolean getCubeBoolean(String key) throws Exception{
        try{
            if ( isPresent(key) > 0 ) return(getCubeBoolean(isPresent(key)));
        }catch(Exception e){
            throw new Exception("[getCubeBoolean]" + e);  
        }
         return new Boolean(false);
    }
    
    public Boolean getCubeBoolean(int idx) throws Exception{
        try{
            return(new Boolean(internalRS.getBoolean(idx)));
        }catch(Exception e){
            throw new Exception("[getCubeBoolean]" + e);
        }
        //return new Boolean(false);
    }
    
    public String getCubeString(String key) throws Exception{
        try{
            if ( isPresent(key) > 0 ) return(getCubeString(isPresent(key)));
        }catch(Exception e){throw new Exception("[getCubeString]" + e);}
        return "";
    }
    
    public String getCubeString(int idx) throws Exception{
        try{
            if (internalRS.getString(idx)==null) return new String("");
            else return(internalRS.getString(idx));
        }catch(Exception e){throw new Exception("[getCubeString]" + e);
        //return "";
        }
    }
    
    public Double getCubeDouble(String key) throws Exception{
        try{
            if ( isPresent(key) > 0 ) return(getCubeDouble(isPresent(key)));
        }catch(Exception e){throw new Exception("[getCubeDouble]" + e);
        }
        return new Double(0);
        
    }
    
    public Double getCubeDouble(int idx) throws Exception{
        try{
            return(new Double(internalRS.getDouble(idx)));
        }catch(Exception e){throw new Exception("[getCubeDouble]" + e);
        //return new Double(0);
        }
    }
    
    public Float getCubeFloat(String key) throws Exception{
        try{
            if ( isPresent(key) > 0 ) return(getCubeFloat(isPresent(key)));
        }catch(Exception e){throw new Exception("[getCubeFloat]" + e);
        }
        return new Float(0);
    }
    
    public Float getCubeFloat(int idx) throws Exception{
        try{
            return(new Float(internalRS.getFloat(idx)));
        }catch(Exception e){throw new Exception("[getCubeFloat]" + e);
        //return new Float(0);
        }
    }
    
    public java.sql.Date getCubeDate(String key) throws Exception{
        long datebidon = 0;
        try{
            if ( isPresent(key) > 0 ) return(getCubeDate(isPresent(key)));
        }catch(Exception e){
            throw new Exception("[getCubeDate]" + e);
        }
        return new java.sql.Date(datebidon);
    }
    
    public java.sql.Date getCubeDate(int idx) throws Exception{
        long datebidon = 0;
        try{
            return(internalRS.getDate(idx));
        }catch(Exception e){throw new Exception("[getCubeDate]" + e);}
        //return new java.sql.Date(datebidon);
    }
    
    public Integer getCubeInt(String key) throws Exception{
        try{
            if ( isPresent(key) > 0 ) return(getCubeInt(isPresent(key)));
        }catch(Exception e){
            throw new Exception("[getCubeInt]" + e);
        }
        return new Integer(0);
    }
    
    public Integer getCubeInt(int idx) throws Exception{
        try{
            return(new Integer(internalRS.getInt(idx)));
        }catch(Exception e){throw new Exception("[getCubeInt]" + e);}
        //return new Integer(0);
    }
    
//------------fin recupération des données -----------------------------------*/    
    
    /** Renvoie une information piochée dans le cube à l'enregistrement courant 
     * dans ce cas il faut connaitre le nom du champs de recherche */
     public String getCubeData(String key) throws Exception{

      String value = null;
      try{
        if ( isPresent(key) > 0 ) {
            switch(internalMD.getColumnType(isPresent(key))){    
            case Types.FLOAT:  {value = "" + internalRS.getFloat(isPresent(key));
            }break;
            case Types.REAL:  {value = "" + internalRS.getFloat(isPresent(key));
            }break;
            case Types.DOUBLE: {value = "" + internalRS.getDouble(isPresent(key));
            }break;
            case Types.VARCHAR:{value = "" + internalRS.getString(isPresent(key));
            }break;
            case Types.CHAR:   {value = "" + internalRS.getString(isPresent(key));
            }break;
            case Types.INTEGER:{value = "" + internalRS.getInt(isPresent(key));
            }break;
            case Types.TIMESTAMP:{value = "" ;}break;
            case Types.DATE:{value = DateSQLMy(internalRS.getDate(isPresent(key))) ;
            }break;
            case Types.TINYINT:{value = "" + internalRS.getBoolean(isPresent(key));
            }break;
            default: { 
                value = "";
            }break;
            }      
        }     
     }catch(Exception e){throw new Exception("[SmartCube][getCubeDate]Impossible de récupérer la valeur depuis le cube" + e);};
     return value;
    }

     /** Renvoie une information piochée dans le cube à l'enregistrement courant 
     * dans ce cas il faut connaitre l'indice du champs de recherche */
     public String getCubeData(int idx) throws Exception{

      String value = null;
      try{
            switch(internalMD.getColumnType(idx)){    
            case Types.FLOAT:  {value = "" + internalRS.getFloat(idx);
            }break;
            case Types.REAL:  {value = "" + internalRS.getFloat(idx);
            }break;
            case Types.DOUBLE: {value = "" + internalRS.getDouble(idx);
            }break;
            case Types.VARCHAR:{value = "" + internalRS.getString(idx);
            }break;
            case Types.CHAR:   {value = "" + internalRS.getString(idx);
            }break;
            case Types.INTEGER:{value = "" + internalRS.getInt(idx);
            }break;
            case Types.TIMESTAMP:{
            }break;
            case Types.DATE:{value = DateSQLMy(internalRS.getDate(idx)) ;
            }break;
            case Types.TINYINT:{value = "" + internalRS.getBoolean(idx);
            }break;
            default: { 
                value = "";
            }break;
            }           
     }catch(Exception e){throw new Exception("[SmartCube][getCubeDate]Impossible de récupérer la valeur depuis le cube" + e);};
     return value;
    }
    
     /** Renvoie une information piochée dans le cube à l'enregistrement courant 
     * dans ce cas il faut connaitre le nom du champs de recherche */
     public Object getCubeDataObject(String key) throws Exception{

      Object value = null;
      try{
        if ( isPresent(key) > 0 ) {
            switch(internalMD.getColumnType(isPresent(key))) { 
            case Types.FLOAT:  {value = this.getCubeFloat(key);}break;
            case Types.REAL:   {value = this.getCubeFloat(key);}break;
            case Types.DOUBLE: {value = this.getCubeDouble(key);}break;
            case Types.VARCHAR:{value = this.getCubeString(key);}break;
            case Types.CHAR:   {value = this.getCubeString(key);}break;
            case Types.INTEGER:{value = this.getCubeInt(key);}break;
            case Types.DATE:   {value = DateSQLMy(this.getCubeDate(key));}break;
            case Types.TINYINT:{value = this.getCubeBoolean(key);}break;
            default: { value = " ";System.out.println("Rejet[" + key);}break;
          }   
        }        
     }catch(Exception e){throw new Exception("[SmartCube][getCubeDate]Impossible de récupérer l'objet depuis le cube" + e);};
     if (value==null) return new String("");
     else return value;
    }
       
     /** Renvoie une information piochée dans le cube à l'enregistrement courant 
     * dans ce cas il faut connaitre l'indice du champs de recherche */
     public Object getCubeDataObject(int idx) throws Exception{

         Object value = null;
      try{
            switch(internalMD.getColumnType(idx)){    
            case Types.FLOAT:  {value = this.getCubeFloat(idx);}break;
            case Types.REAL:   {value = this.getCubeFloat(idx);}break;
            case Types.DOUBLE: {value = this.getCubeDouble(idx);}break;
            case Types.VARCHAR:{value = this.getCubeString(idx);}break;
            case Types.CHAR:   {value = this.getCubeString(idx);}break;
            case Types.INTEGER:{value = this.getCubeInt(idx);}break;
            case Types.DATE:   {value = this.getCubeDate(idx);}break;
            case Types.TINYINT:{value = this.getCubeBoolean(idx);}break;
            default: { value = "";
            }break;
          }           
     }catch(Exception e){throw new Exception("[SmartCube][getCubeDate]Impossible de récupérer l'objet depuis le cube" + e);};
     return value;
    }
     
     
/* ---------- Methodes de parcours du cube --------------------------------- */
    
   /** Donne l'enregistrement suivant ,si il n'existe pas retourne faux et 
    * reste sur le dernier enregistrement parcouru */
    public boolean getNext() throws Exception{
        try{
            return(internalRS.next());
        }catch(Exception e){
            throw new Exception("[SmartCube][getNext]Impossible de récupérer l'enregistrement suivant dans le cube" + e);}
            //return false;
        }
    
     /** Donne l'enregistrement avant le premier */
    public void getBeforeFirst() throws Exception{
        try{
            internalRS.beforeFirst();
        }catch(Exception e){
            throw new Exception("[SmartCube][getBeforeFirst]Impossible de récupérer la position avant le debut du cube" + e);};
        }
    
    /** Donne l'enregistrement précedent , si il n'existe pas retroune faux et 
     * reste sur le dernier enregistrement parcouru */
    public boolean getPrevious() throws Exception{
       try{ 
            return (internalRS.previous());
       }catch(Exception e){
            throw new Exception("[smartcube][getPrevious]Impossible de récupérer l'enregistrement précédent dans le cube " + e);
            //return false;
       }
    }
    
    /** Donne le premier enregistrement , si il n'existe pas retourne faux */
    public boolean getFirst() throws Exception{
       try{ 
            return (internalRS.first());
       }catch(Exception e){
            throw new Exception("[smartcube][getFirst]Impossible de récupérer le premier enregistrement du cube" + e);
            //return false;
       }
    }
    
    /** Donne le dernier enregistrement , si il n'existe pas retourne faux  */
    public boolean getLast() throws Exception{
       try{ 
            return (internalRS.last());
       }catch(Exception e){
            throw new Exception("[smartcube][getLast]Impossible de récupérer le dernier enregistrement du cube." + e);
       }//return false;}
    }
    
    /** Permet un positionnement sur une ligne du cube à partir de son numéro */
    public void getSpecific(int idx) throws Exception{
        try{
            internalRS.relative(idx-internalRS.getRow());
        }catch(Exception e){
            this.getFirst();          
            throw new Exception("[smartcube][getSpecific]Impossible de se positionner sur l'enregistrement " + idx + " + du cube." + e);
        }
    }
    
/* -------------Méthodes de consultation des informations annexes ------------*/    

    
    /** Interroge le cube pour savoir si un champs de la base fait partie de
     * son metaschema.Si il ne fait pas partit du schéma alors on retourne -1 */
    public int isPresent(String champs) throws Exception {
        try{
            return(internalRS.findColumn(champs));
        }catch(Exception e){
            throw new Exception("[smartCube][isPresent]Erreur à la recherche de la colonne "+e);
        }
    }
    
    /** Donne le nom d'une colonne à partir de son indice */
    public String getColumnName(int idx) throws Exception {
        try{
            return(internalMD.getColumnName(idx));
        }catch(Exception e){throw new Exception("[smartCube][getColumnName1]" +e);}
    }
    
    /** Donne la liste des colonnes sous forme de vecteurs     */
    public Vector getColumnName() throws Exception{
     Vector colName = new Vector();
     try{ 
        for (int loop=1 ; loop<internalMD.getColumnCount(); loop++)
             colName.add(internalMD.getColumnName(loop));
           return(colName);
        }catch(Exception e){throw new Exception("[smartCube][getColumnName2]" +e);}
    }
    
    /** Donne le type de la colonne a partir du nom de celle-ci */
    public String getColumnType(String name) throws Exception{
        try{
            return(getColumnType(isPresent(name)));
        }catch (Exception e){throw new Exception("[smartCube][getColumnType1]"+e);}
    }
    
    /** Donne le type de la colonne */
    public String getColumnType(int idx) throws Exception {
        try{    
            return(internalMD.getColumnClassName(idx));
        }catch(Exception e){throw new Exception("[smartCube][getColumnType2]"+e);}
    }
    
    /** Donne le contenu de la ligne courante sous forme de vecteur */
    public Vector getRowCubeData() throws Exception{
        Vector RowData =new Vector();
        try{
            for (Enumeration e=ColumnName.elements();e.hasMoreElements();)
                RowData.add(this.getCubeDataObject(e.nextElement().toString()));
            return(RowData);
        }catch(Exception e){throw new Exception("[smartCube][getRowCubeData]"+e);}
    }
    
    /** Donne le contenu d'une ligne (idx) sous forme de vecteur */
    public Vector getRowCubeData(int idx) throws Exception{
        Vector RowData = new Vector();
        try{
            this.getSpecific(idx);
            for (Enumeration e=ColumnName.elements();e.hasMoreElements();)
                RowData.add(this.getCubeDataObject(e.nextElement().toString()));
            return(RowData);
        }catch(Exception e){throw new Exception("[smartCube][getRowCubeData2]"+e);}
    }
    
    /** Donne le contenu d'une colonne sous forme de vecteur */
    public Vector getColumnCubeData(String colName) throws Exception{
        Vector ColData = new Vector();
        try{
            this.getBeforeFirst();
            while(this.getNext()) ColData.addElement(this.getCubeDataObject(colName));
            return ColData;
        }catch(Exception e){throw new Exception("[smartCube][getRowCubeData2]"+e);}
    }
    
    /** Donne le nombre de colonnes dans le cube de données */
    public int getColumnCount() throws Exception{
        try{
            if (internalMD==null) return 0;
            else return(internalMD.getColumnCount());
        }catch(Exception e){throw new Exception("[smartCube][getColumnCount]"+e);}
    }
    
    /** Cette méthode retourne le nombres de lignes présentes dans le cube */
    public int getRowCount(){
        return ColumnNumber;
    }
    
    /** Retourne la connexion courante à la base de donnée */
    public Connection getCubeConnect() throws Exception {
        try{
            return(internalConnex);
        }catch(Exception e){throw new Exception("[smartCube][getCubeConnect]"+e);}
    }
    
    private void setRowCount() throws Exception{
      try{
        internalRS.beforeFirst();
        while(internalRS.next()) ColumnNumber++;
      }catch(Exception e){throw new Exception("[smartCube][setRowCount]"+e);}
    }

/* -------- Méthode de conversion des dates --------------------------------- */
   /* Permet de convertir une chaine de caractï¿½re dd/MM/yyyy */
   /* Au format attendu par MySQL (yyyy-dd-MM)               */
   public String DateSQLMy(Date DateFormatee){    
        if (  DateFormatee == null ) return ("  /  /    ");
        String st = new String("" + DateFormatee);
        
        return(st.substring(8,10) + "/" + st.substring(5,7) + "/" + st.substring(0,4)) ;
   }
   
   /* Permet de convertir une chaine de caractï¿½re dd/MM/yyyy */
   /* Au format attendu par MySQL (yyyy-dd-MM)               */
   public String MySQLDate(String DateFormatee){       
       System.out.println(DateFormatee);
       return((DateFormatee.substring(6,10) + "-"  + DateFormatee.substring(3,5)) + "-" + DateFormatee.substring(0,2)) ;
   } 
}
