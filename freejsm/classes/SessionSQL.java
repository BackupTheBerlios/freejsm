package classes;

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

public final class SessionSQL {
    
    private Connection session ; /* Designe la connection */
    private int TypeErreur ; /* Renvoie le numero d'erreur */

    
    /** Constructeur de la classe sessionSQL , on tente une connexion a la base */
    public void sessionSQL(){
        session = null; /* Deigne la connection */
        TypeErreur = 0; /* Renvoie le numero d'erreur */}
    
    public Connection getSession(String dbDriver, String dbServer, String dbName, String user, String password) {
        try{
          Class.forName(dbDriver); /* Nom du driver MySQL */
          session = DriverManager.getConnection(dbServer+dbName, user, password);  
           }
        /* Echec lors du chargement du driver JDBC */
            catch(ClassNotFoundException ErrFound){ 
            System.out.println("Driver de base de donnees abscent!");
            TypeErreur = 1;
            return null;
            }
         /* Echec d'autentification */
            catch(SQLException ErrSQL){ 
            System.out.println(ErrSQL);
            TypeErreur = 2;
            return null;
            }
        TypeErreur = 0;
        System.out.println("Authentification reussie pour l'utilisateur " + user);
        return session;
    }
   
    public int GetError(){
        return TypeErreur;
    }
}
