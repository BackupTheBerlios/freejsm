/*
 * Parametre.java
 *
 * Created on 17 janvier 2004, 18:20
 */

package com.develog.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author  Yann Marigo pour Dévelog
 */
public class Parametre {
    
    private String clef     = null;
    private String valeur   = null;
    private Connection connection = null;
    private String selectQuery = new String(
        "select PM_VALEUR from PARAMETRE where PM_CLEF = ?");
    private PreparedStatement psSelect = null;
    private String updateQuery = new String(
        "update PARAMETRE set PM_VALEUR = ? where PM_CLEF = ?");
    private PreparedStatement psUpdate = null;
    private String searchQuery = new String(
        "select PM_VALEUR from PARAMETRE where PM_CLEF = ?");
    private PreparedStatement psSearch = null;
    private String createQuery = new String(
        "insert into PARAMETRE values (?,'')");
    private PreparedStatement psCreate = null;
    
    /** Creates a new instance of Parametre */
    public Parametre(Connection connect) throws Exception {
        connection = connect;
        initParams();
    }
    
    public Parametre(String c, Connection connect) throws Exception {
        connection = connect;
        clef = new String(c);
        initParams();
    }
    
    public Parametre(String c, Connection connect, boolean create) throws Exception {
        if (create) {
            try {
                psSearch = connect.prepareStatement(searchQuery);
                psSearch.setString(1,c);
                ResultSet rset = psSearch.executeQuery();
                if (!rset.next()) {
                    //La clef n'existe pas, on la cree vide
                    try {
                        psCreate = connect.prepareStatement(createQuery);
                        psCreate.setString(1, c);
                        ResultSet rsetCreate = psCreate.executeQuery();
                    }catch(SQLException SQLe)
                    {
                        throw new Exception("[Parametre][Constructeur]SQLe lors de la creation de la clef: "+SQLe);
                    }
                }
            }catch(SQLException SQLe)
            {
                throw new Exception("[Parametre][Constructeur]SQLe lors de la recherche de presence de la  clef: "+SQLe);
            }
        }
        connection = connect;
        clef = new String(c);
        initParams();
    }
    
    private void initParams() throws Exception {
        try{
            psSelect = connection.prepareStatement(selectQuery);
            psUpdate = connection.prepareStatement(updateQuery);
        }catch(SQLException SQLe)
        {
            throw new Exception("[Parametre][initParams]SQLe lors de l'initialisation de l'objet:"+SQLe);
        }
        catch(Exception e)
        {
            throw new Exception("[Parametre][initParams]Exception lors de l'initialisation de l'objet de clef =>"+clef
                +"<= / Exception: "+e);
        }
    }
    
    public String getValeur() throws Exception {
        if (valeur == null)
            try {
                psSelect.setString(1,clef);
                ResultSet rset = psSelect.executeQuery();
                
                while(rset.next()) {
                    valeur = rset.getString(1);
                }
            }catch(SQLException SQLe)
            { 
                throw new Exception("[Parametre][getValeur]SQLe lors de la recuperation de la valeur de la clef =>"+clef+"<= / Exception:"+SQLe);
            }
        return (valeur==null)?new String(""):valeur;
    }
    
    public void setValeur(String val) throws Exception {
        if (val != null) {
            try {
                psUpdate.setString(1,val);
                psUpdate.setString(2,clef);
                psUpdate.executeUpdate();
            }catch(SQLException SQLe)
            { 
                throw new Exception("[Parametre][setValue]SQLe lors de la mise à jour de "+clef+" : "+SQLe); 
            }
        }
    }
}
