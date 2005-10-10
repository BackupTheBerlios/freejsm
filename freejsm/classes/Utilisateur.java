/*
 * Utilisateur.java
 *
 * Created on 20 janvier 2005, 15:03
 */

package classes;

import java.sql.Connection;
import java.sql.ResultSet;

import java.util.Hashtable;

import java.lang.String;
import java.lang.Integer;

/**
 *
 * @author  nahuel
 */
public class Utilisateur {
    
        private Connection connex;
        private boolean admin;
        private int UTIL_ID;
        private String aaa;
        private Hashtable userRights = new Hashtable();
        
    /** Creates a new instance of Utilisateur */
    public Utilisateur(String login, String password, Connection connex) {
        this.connex = connex;
        try{
            ResultSet rset = connex.prepareStatement("SELECT UTIL_ID from UTILISATEUR where UTIL_LOGIN = '" + login + "' and UTIL_PASSWORD = '" + password + "'").executeQuery();
            rset.next();
            UTIL_ID = Integer.parseInt(rset.getString("UTIL_ID"));
        }catch(Exception e){
            System.out.println("[Utilisateur][Utilisateur] Exception : " + e );
            e.printStackTrace();
        }
        refreshRights();

        
    }
    
    // Sert a raffraichir la liste des droits
    public void refreshRights(){
        ResultSet rset;
        
        userRights.clear();
        try{
            rset = connex.prepareStatement("SELECT FO_LEVEL, ST_ID from FONCTION where UTIL_ID = " + UTIL_ID).executeQuery();
        while ( rset.next() ){
            userRights.put(new Integer(rset.getInt("ST_ID")),new Integer(rset.getInt("FO_LEVEL")));
        }
        }catch(Exception e){
            System.out.println("[Utilisateur][refreshRights] Exception : " + e );
            e.printStackTrace();
        }
    }
    
    // Sert a récuperer les droits d'un utilisateur pour un Stock
    public Integer getRight(Integer ST_ID){
        Integer isAdmin = (Integer)userRights.get(ST_ID);

        if (userRights.containsKey(ST_ID)){
            if (isAdmin.compareTo(new Integer(1)) == 0){
                System.out.println("Est admin");
                return (new Integer(1));
            }else if(isAdmin.compareTo(new Integer(0)) == 0){
                System.out.println("N'est pas admin");
                return (new Integer(0));
            }
        }else{
            System.out.println("N'est rien");
            return (new Integer(-1));
        }
        
        return (new Integer(-1));
        
    }
    
    // Permet de configurer le nom et le prenom de l'utilisateur
    public void setNomPrenom(String nom, String prenom){
        try{
            connex.prepareStatement("UPDATE UTILISATEUR SET UTIL_NOM = '" + nom + "' , UTIL_PRENOM = '" + prenom + "' WHERE UTIL_ID = " + UTIL_ID);
        }catch(Exception e){
            System.out.println("[Utilisateur][setNomPrenom] Exception : " + e);
            e.printStackTrace();
        }
    }
    
    // Permet de récuperer le Nom de l'utilisateur
    public String getNom(){
        String result = new String();
        try{
            ResultSet rset = connex.prepareStatement("SELECT UTIL_NOM from UTILISATEUR where UTIL_ID = " + UTIL_ID ).executeQuery();
	    rset.next();
            rset.getString("UTIL_NOM");
        }catch(Exception e){
            System.out.println("[Utilisateur][getNom] Exception :" + e);
            e.printStackTrace();
        }
        return result;
    }
    
    // Permet de récuperer le Nom de l'utilisateur
    public String getPrenom(){
        String result = new String();
        try{
            ResultSet rset = connex.prepareStatement("SELECT UTIL_PRENOM from UTILISATEUR where UTIL_ID = " + UTIL_ID ).executeQuery();
	    rset.next();
            rset.getString("UTIL_PRENOM");
        }catch(Exception e){
            System.out.println("[Utilisateur][getNom] Exception :" + e);
            e.printStackTrace();
        }
        return result;
    }
    
    public void setTelephone(Integer telephone){
        try{
            connex.prepareStatement("UPDATE UTILISATEUR SET UTIL_TELEPHONE = '" + telephone + "' WHERE UTIL_ID = " + UTIL_ID);
        }catch(Exception e){
            System.out.println("[Utilisateur][setTelephone] Exception : " + e);
            e.printStackTrace();
        }
    }
    
    public Integer getTelephone(){
        Integer result = null;
        try{
            ResultSet rset = connex.prepareStatement("SELECT UTIL_TELEPHONE from UTILISATEUR where UTIL_ID = " + UTIL_ID ).executeQuery();
	    rset.next();
            rset.getString("UTIL_TELEPHONE");
        }catch(Exception e){
            System.out.println("[Utilisateur][getTelephone] Exception :" + e);
            e.printStackTrace();
        }
        return result;
    }
    
    public void setMail(String mail){
        try{
            connex.prepareStatement("UPDATE UTILISATEUR SET UTIL_MAIL = '" + mail + "' WHERE UTIL_ID = " + UTIL_ID);
        }catch(Exception e){
            System.out.println("[Utilisateur][setMail] Exception : " + e);
            e.printStackTrace();
        }
    }
    
    public String getMail(){
        String result = new String();
        try{
            ResultSet rset = connex.prepareStatement("SELECT UTIL_MAIL from UTILISATEUR where UTIL_ID = " + UTIL_ID ).executeQuery();
	    rset.next();
            rset.getString("UTIL_MAIL");
        }catch(Exception e){
            System.out.println("[Utilisateur][getMail] Exception :" + e);
            e.printStackTrace();
        }
        return result;
    }    
    
    public Integer getID(){
        return (new Integer(UTIL_ID));
    }
    
    public boolean estAdminQQuePart(){
        return userRights.contains(new Integer(1));
    }
}

   