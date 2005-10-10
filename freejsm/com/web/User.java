/*
 * User.java
 *
 * Created on 22 avril 2004, 14:56
 */

package com.develog.web;

/**
 *
 * @author  yann
 */

import java.util.Hashtable;
import java.util.Vector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class User {
    public int us_id            = 0;
    public String name       = new String();
    public String login      = new String();
    public String password   = new String();
    public int gr_id            = 0;   
    private boolean toCreate    = false;
    private String searchString = new String(
            "select * from USER where US_ID = ?");
    private String updateString = new String(
            "update USER set US_NAME=?,US_LOGIN=?,US_PASSWORD=?,GR_ID=?,"+
            "where US_ID = ?");
    private String insertString = new String(
            "insert into USER set US_NAME=?,US_LOGIN=?,US_PASSWORD=?,GR_ID=?,US_ID");
    private String deleteString = new String(
            "delete from USER where US_ID=?");
    private String countUsersQuery = new String(
            "select count(*) from USER");
    static private String listString = new String(
            "select distinct US_ID from USER");
    
    private Connection connexion = null;
    
    /** cree une representation de l'utilisateur */
    public User() {
    }
    
    public User(int id, Connection connect, boolean create) throws Exception {
        this.initContent(id,connect,create);
    }
    
    public User(String id, Connection connect, boolean create) throws Exception {
        int newId = 0;

        //On converti la chaine du code
        try {
            newId = (new Integer(id)).intValue();
        }catch(NumberFormatException NFe) {
            throw new Exception("[User][CONSTRUCTEUR]NFe lors de la convertion de l'id en entier: "+NFe);
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
                us_id       = rset.getInt("US_ID");
                name        = rset.getString("US_NAME");
                login       = rset.getString("US_LOGIN");
                password    = rset.getString("US_PASSWORD");
                gr_id       = rset.getInt("GR_ID");
            }
            //si create alors on crée un objet
            if((us_id == 0 ) && (create)) {
                us_id = id;
                toCreate = true;
            }
        }catch(Exception e){
            throw new Exception("[User][CONSTRUCTEUR]Exception lors de la création de l'objet: "+e);
        }
    }
    
    public void updateDB() throws Exception {
        try {
            PreparedStatement pstmt = null;
            if(toCreate)
                pstmt = connexion.prepareStatement(insertString);
            else
                pstmt = connexion.prepareStatement(updateString);

            pstmt.setString(1,name);
            pstmt.setString(2,login);
            pstmt.setString(3,password);
            pstmt.setInt(4,gr_id);
            pstmt.setInt(5,us_id);
            
            pstmt.executeQuery();
        }catch(SQLException SQLe){
            throw new Exception("[User][update]SQLe lors de update: "+SQLe);
        }
    }
    
    public void delete() throws Exception {
        try {
        PreparedStatement pstmt = connexion.prepareStatement(deleteString);
        pstmt.setInt(1, us_id);
        ResultSet rset = pstmt.executeQuery();
        }catch(SQLException SQLe)
        {
            throw new Exception("[User][delete]SQLe lors du delete: "+SQLe);
        }
    }
    
    public String toString() {
        return name;
    }
    
    public String display() {
        return new String("US_ID="+us_id+" ,US_NAME="+name+
            " ,US_LOGIN="+login+
            " ,US_PASSWORD="+password+
            " ,GR_ID="+gr_id);
    }
    
    public static Hashtable getList(Connection connect) throws Exception {
        Hashtable list = new Hashtable();
        try {
            ResultSet resultSet = connect.prepareStatement(listString).executeQuery();
            while(resultSet.next()) {
                list.put(new String(""+resultSet.getInt(1)), new User(resultSet.getInt(1),connect,false));
            }
        }catch(SQLException SQLe){
            throw new Exception("[User][getList]SQLe lors de la remontée de la liste: "+SQLe);
        }catch(Exception e){
            throw new Exception("[User][getList]Exception lors de la remontée de la liste: "+e);
        }
        return list;
    }
    
    public int getNbUsers() throws Exception {
        try{
            PreparedStatement pstmtNbUsers = connexion.prepareStatement(countUsersQuery);
            ResultSet rsetNbUsers = pstmtNbUsers.executeQuery();
            rsetNbUsers.next();
            return rsetNbUsers.getInt(1);
        }catch(SQLException SQLe){
            System.out.println("Erreur");
            throw new Exception("[User][getNbUsers]SQLe lors de la remontée du nombre d'utilisateurs: "+SQLe);
        }
    }

}
