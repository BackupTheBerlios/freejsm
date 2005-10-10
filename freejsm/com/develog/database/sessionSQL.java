package com.develog.database;

/*
 * sessionSQL.java
 *
 * Created on 15 ao�t 2002, 17:17
 */

/**
 *
 * @author  Labayle Alexandre
 * Cette classe permet d'optenir une session sur la base SQL MySQL
 */
import java.sql.*;
import org.gjt.mm.mysql.Driver;

public final class sessionSQL {
    
    private Connection session ; /* Designe la connection */
    private int TypeErreur ; /* Renvoie le numero d'erreur */

    
    /** Constructeur de la classe sessionSQL , on tente une connexion a la base */
    public void sessionSQL(){
        session = null; /* Deigne la connection */
    }
    
    public Connection getSession(String dbDriver, String dbServer, String dbName, String user, String password) throws Exception {
          
           Class.forName(dbDriver); /* Nom du driver MySQL */
           String urlBase = "jdbc:mysql://"+dbServer+"/"+dbName;
           return(Connection)DriverManager.getConnection(urlBase, user, password);  
       
    } 
}

