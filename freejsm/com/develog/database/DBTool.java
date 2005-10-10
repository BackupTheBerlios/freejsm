package com.develog.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBTool
{
        static private String DBDriver = "org.gjt.mm.mysql.Driver";
        static private String dBName = "jdbc:mysql://localhost/develog_web";
        static private String dBLogin = "devweb";
        static private String dBPassword = "passweb";
                                                                                
                                                                                
        /**
        * Retourne une java.sql.Connection a la base de donnees
        */
        static public Connection sGetDBConnection(String dBName, String dBLogin, String dBPassword)
                throws Exception
        {
                Connection connection = null;
                                                                                
                //Connexion a la base de donnees
                        try
                        {
                                        Class.forName(DBDriver).newInstance();
                                                                                
                        }
                        catch (Exception e)
                        {
                                throw new Exception("[DBTool.getDBConnection] Impossible de charger le driver de la base de donnees: "+DBDriver);
                        }
                                                                                
                try
                {
                        //Recuperation de la connection
                        connection = DriverManager.getConnection(dBName,dBLogin,dBPassword);
                }
                catch(SQLException SQLe)
                {
                        throw new Exception("[DBTool.getDBConnection] Erreur lors de l'etablissement de la connexion a la BD:"+SQLe);
                }
                                                                                
                return connection;
        }
 
 
 
        /**
        * Retourne une java.lang.Connection avec les para de conn locaux
        */
        static public Connection sGetDBConnection()
                throws Exception
        {
                return sGetDBConnection(dBName,dBLogin,dBPassword);
        }
}
