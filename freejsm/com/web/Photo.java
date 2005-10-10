/*
 * Photo.java
 *
 * Created on 22 avril 2004, 14:56
 */

package com.develog.web;

/**
 *
 * @author  yann
 */

import java.io.InputStream;
import java.util.Hashtable;
import java.util.Vector;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import com.mysql.jdbc.jdbc2.Blob;

public class Photo {
    public int ph_id        = 0;
    public int us_id        = 0;
    public String name      = new String();
    public String content_type = new String();
    public Blob blob        = null;
    
    private boolean toCreate    = false;
    private String searchString = new String(
            "select * from PHOTO where PH_ID = ?");
    private String updateString = new String(
            "update PHOTO set US_ID=?,PH_NAME=? "+
            "where PH_ID = ?");
    private String insertString = new String(
            "insert into PHOTO set US_ID=?,PH_NAME=?,PH_ID=?");
    private String totalUpdateString = new String(
            "update PHOTO set US_ID=?,PH_NAME=?,PH_CONTENTTYPE=?,PH_BLOB=? "+
            "where PH_ID = ?");
    private String totalInsertString = new String(
            "insert into PHOTO set US_ID=?,PH_NAME=?,PH_CONTENTTYPE=?,PH_BLOB=?,PH_ID=?");
    private String deleteString = new String(
            "delete from PHOTO where PH_ID=?");
    private String countPhotosQuery = new String(
            "select count(*) from PHOTO");
    static private String listString = new String(
            "select distinct PH_ID from PHOTO");
    
    private Connection connexion = null;
    
    /** cree une representation de l'utilisateur */
    public Photo() {
    }
    
    public Photo(int id, Connection connect, boolean create) throws Exception {
        this.initContent(id,connect,create);
    }
    
    public Photo(String id, Connection connect, boolean create) throws Exception {
        int newId = 0;

        //On converti la chaine du code
        try {
            newId = (new Integer(id)).intValue();
        }catch(NumberFormatException NFe) {
            throw new Exception("[Photo][CONSTRUCTEUR]NFe lors de la convertion de l'id en entier: "+NFe);
        }
        this.initContent(newId,connect,create);
    }
    
     private void initContent(int id, Connection connect, boolean create) throws Exception {
        try{
            connexion  = connect;
            //On le recherche ds la db
            ResultSet rset = null;
            try{
                PreparedStatement psSearch = connexion.prepareStatement(searchString);
                psSearch.setInt(1, id);
                rset  = psSearch.executeQuery();
            }catch(Exception e){
                throw new Exception("Erreur lors de la remontée de donnée de la DB: "+e);
            }
            //Si il existe on le remonte sinon, 
            if(rset.next()) {
                ph_id   = rset.getInt("PH_ID");
                us_id   = rset.getInt("US_ID");
                name    = rset.getString("PH_NAME");
                content_type = rset.getString("PH_CONTENTTYPE");
                blob    = rset.getBlob("PH_BLOB");
            }
            //si create alors on crée un objet
            if((ph_id == 0 ) && (create)) {
                ph_id = id;
                toCreate = true;
            }
        }catch(Exception e){
            throw new Exception("[Photo][CONSTRUCTEUR]Exception lors de la création de l'objet: "+e);
        }
    }
    
    public void updateDB() throws Exception {
        try {
            PreparedStatement pstmt = null;
            if(toCreate)
                pstmt = connexion.prepareStatement(insertString);
            else
                pstmt = connexion.prepareStatement(updateString);
            pstmt.setInt(1,us_id);
            pstmt.setString(2, name);
            pstmt.setInt(3,ph_id);
        
            pstmt.executeQuery();
        }catch(SQLException SQLe){
            throw new Exception("[Photo][update]SQLe lors de update: "+SQLe);
        }
    }
     
    public void updateDB(InputStream is, int length) throws Exception {
        try {
            PreparedStatement pstmt = null;
            if(toCreate)
                pstmt = connexion.prepareStatement(totalInsertString);
            else
                pstmt = connexion.prepareStatement(totalUpdateString);
            pstmt.setInt(1,us_id);
            pstmt.setString(2, name);
            pstmt.setString(3, content_type);
            pstmt.setBinaryStream(4,is,length);
            pstmt.setInt(5,ph_id);
        
            pstmt.executeQuery();
        }catch(SQLException SQLe){
            throw new Exception("[Photo][update]SQLe lors de update: "+SQLe);
        }
    }
        
    public void delete() throws Exception {
        try {
        PreparedStatement pstmt = connexion.prepareStatement(deleteString);
        pstmt.setInt(1, us_id);
        ResultSet rset = pstmt.executeQuery();
        }catch(SQLException SQLe)
        {
            throw new Exception("[Photo][delete]SQLe lors du delete: "+SQLe);
        }
    }
    
    public String toString() {
        return name;
    }
    
    public String display() {
        return new String("PH_ID="+ph_id+"US_ID="+us_id+" ,PH_NAME="+name);
    }
    
    public static Vector getSimpleList(Connection connect) throws Exception {
        Vector list = new Vector();
        try {
            ResultSet resultSet = connect.prepareStatement(listString).executeQuery();
            while(resultSet.next()) {
                list.addElement(new String(""+resultSet.getInt(1)));
            }
        }catch(SQLException SQLe){
            throw new Exception("[Photo][getSimpleList]SQLe lors de la remontée de la liste: "+SQLe);
        }catch(Exception e){
            throw new Exception("[Photo][getSimpleList]Exception lors de la remontée de la liste: "+e);
        }
        return list;
    }
    
    public static Hashtable getList(Connection connect) throws Exception {
        Hashtable list = new Hashtable();
        try {
            ResultSet resultSet = connect.prepareStatement(listString).executeQuery();
            while(resultSet.next()) {
                list.put(new String(""+resultSet.getInt(1)), new Photo(resultSet.getInt(1),connect,false));
            }
        }catch(SQLException SQLe){
            throw new Exception("[Photo][getList]SQLe lors de la remontée de la liste: "+SQLe);
        }catch(Exception e){
            throw new Exception("[Photo][getList]Exception lors de la remontée de la liste: "+e);
        }
        return list;
    }
    
    public int getNbPhotos() throws Exception {
        try{
            PreparedStatement pstmtNbPhotos = connexion.prepareStatement(countPhotosQuery);
            ResultSet rsetNbPhotos = pstmtNbPhotos.executeQuery();
            rsetNbPhotos.next();
            return rsetNbPhotos.getInt(1);
        }catch(SQLException SQLe){
            System.out.println("Erreur");
            throw new Exception("[Photo][getNbPhotos]SQLe lors de la remontée du nombre d'utilisateurs: "+SQLe);
        }
    }

}
