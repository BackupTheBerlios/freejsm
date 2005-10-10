/*
 * Synchronize.java
 *
 * Created on 28 janvier 2005, 12:11
 */

package classes;

/**
 *
 * @author  nahuel
 */

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;

public class Synchronize {
    
    private Connection connex;
    private Integer lastID;
    private Hashtable changedID = new Hashtable();

    /** Creates a new instance of Synchronize */
    public Synchronize(Connection connex, Vector tableLevels) {
        this.connex = connex;
        parcourListeNiveaux(tableLevels);
    }
/***********************************************************************************************/
    public void parcourListeNiveaux(Vector tableNiveaux){
        for(int i = 0 ; i < tableNiveaux.size() ; i++ ){
            parcourNiveau((Vector)tableNiveaux.get(i));
        }
    }
/***********************************************************************************************/    
    public void parcourNiveau(Vector tableNiveau){
        for(int j = 0 ; j < tableNiveau.size() ; j++ ){
                traiterTable((String)tableNiveau.get(j));
        }
    }
/***********************************************************************************************/    
    public void traiterTable(String tableName){
        lireJournal(tableName);
    }
/***********************************************************************************************/    
    private void lireJournal(String tableName){
        try{
            System.out.println(tableName);
        ResultSet journalRSET = connex.prepareStatement("SELECT * FROM EXT_JO WHERE JO_TABNOM = '" + tableName + "' ORDER BY JO_ID" ).executeQuery();
        while(journalRSET.next()){
            if(journalRSET.getString("JO_OPERATION").compareTo("I") == 0 ){
                System.out.println("INSERT !!!");
                Hashtable fieldsAndValues = parseJO(journalRSET.getString("JO_NOUVAL"),journalRSET.getString("JO_TABNOM"));
                insertAction(fieldsAndValues, journalRSET.getString("JO_TABNOM"));
            }else if(journalRSET.getString("JO_OPERATION").compareTo("U") == 0 ){
                Hashtable fieldsAndValues = parseJO(journalRSET.getString("JO_NOUVAL"),journalRSET.getString("JO_TABNOM"));
                System.out.println("UPDATe !!!");
                compareNewLine(fieldsAndValues,journalRSET.getString("JO_TABNOM"));
                modifyAction(fieldsAndValues, journalRSET.getString("JO_TABNOM"));
            }
        }
        }catch(Exception e){
            System.out.println("[Synchronize][lireJournal] Exception : " + e);
            e.printStackTrace();
        }
    }
/***********************************************************************************************/
    private void compareNewLine(Hashtable fieldsAndValues1, String table){
	try{
            ResultSet journalRSET2 = connex.prepareStatement("SELECT * FROM JOURNAL").executeQuery();
            Hashtable fieldsAndValues2 = new Hashtable();
            boolean breakinfo = false;
            while(journalRSET2.next()){
                if(journalRSET2.getString("JO_TABNOM").compareTo(table) == 0){
                    fieldsAndValues2 = parseJO(journalRSET2.getString("JO_NOUVAL"),journalRSET2.getString("JO_TABNOM"));
                    Enumeration journalLineKeys = fieldsAndValues2.keys();
                    while(journalLineKeys.hasMoreElements()){
                        String cle = (String)journalLineKeys.nextElement();
                        ResultSet primKeysRSET = connex.prepareStatement("DESC " + table).executeQuery();
                        while(primKeysRSET.next()){
                            if(primKeysRSET.getString("Key").compareTo("PRI") == 0){
                                System.out.println("On teste : " + primKeysRSET.getString("Field"));
                                if(((String)fieldsAndValues2.get(primKeysRSET.getString("Field"))).compareTo((String)fieldsAndValues1.get(primKeysRSET.getString("Field"))) != 0){
                                    breakinfo = true;
                                    System.out.println("il faut casser");
                                    break;
                                }else
                                    System.out.println("il faut choisir");// fieldsAndValues1 = new chooseUpdateLineDialog(fieldsAndValues1, fieldsAndValues2);

                                if(breakinfo)
                                    break;
                            }
                            if(breakinfo)
                                break;
                        }
                        if(breakinfo)
                            break;
                    }
                    if(breakinfo)
                        break;
                }
            }
        }catch(Exception e){
            System.out.println("[Synchronize][compareNewLine] Exception : " + e);
            e.printStackTrace();
        }
        /*while(primKeysRSET.next()){
            if(primKeysRSET.getString("Key").compareTo("PRI") == 0){

            }
        }*/
        Enumeration keys = fieldsAndValues1.keys();
        
        while(keys.hasMoreElements()){
            String field = (String)keys.nextElement();
            
        }
        //ResultSet compareRSET = destConnex.prepareStatement("SELECT * from " + table + " where ").executeQuery();
    }
/***********************************************************************************************/
    private Hashtable parseJO(String parseString, String table){
        System.out.println("On parse une ligne pour la table : " + table);
        Hashtable fieldsAndValues = new Hashtable();
        int k = parseString.length();
        for( int i = 0 ; i < k; ){
            parseString = parseString + "#";
            i = parseString.indexOf("###",i);
            int j = parseString.indexOf("###",i + 3);
            String valActuelle = parseString.substring(i+3,j);
            valActuelle = valActuelle + "#";
            // chope la valeur du champ
            String valChamp = new String();

            valChamp = valActuelle.substring( 0 ,valActuelle.indexOf("=>"));

            // chope la valeur du contenu du champ
            String valContent = valActuelle.substring(valActuelle.indexOf("=>") + 2,valActuelle.indexOf("#",valActuelle.indexOf("=>") + 2));
            
            // on traite les boolean
            if(valContent.compareTo("true") == 0)
                valContent = "1";
            else if(valContent.compareTo("false") == 0 )
                valContent = "0";
            
            
            // verifie si c'est un champ de clef primaires ou pas de cette table
            if(!isPrimaryKey(valChamp, table)){
                // si ca n'en est pas un, il l'insere dans une Hashtable
                fieldsAndValues.put(valChamp, valContent);
            }else{
                // on verifie si il y a deja un niveau superieur qui a une clef primaire de meme nom et de meme ID
                String valContent2 = verificationParent(valChamp,valContent);
                valContent = valContent.substring(0,valContent.length()-2);
                // valContent2 = valContent2.substring(0,valContent2.length()-2);
                System.out.println("valContent : " + valContent + " valContent2 : " + valContent2);
                if(valContent.compareTo(valContent2) != 0 ){
                    fieldsAndValues.put(valChamp,  valContent2);
                    System.out.println("la valeur de cette clé primaire a change : " +valChamp +" => " + valContent2);
                }
                    System.out.println("valContentttttttttt : " + valContent);
                    lastID = new Integer(valContent);
            }

            i = j;
        }
        return fieldsAndValues;
    }
/***********************************************************************************************/
    private String verificationParent(String valChamp, String valContent){
        valContent = valContent.substring(0,valContent.length()-2);
        // ca prend les clef de la hashtable qui contient les hashtable qui contient les modifications
        System.out.println("On teste les parents");
        Enumeration key = changedID.keys();
        while(key.hasMoreElements()){
            String table = (String)key.nextElement();
            System.out.println("On teste la table : " + table);
            // Ca regarde quel est le nom du champ de la clef primaire
            String keyNom = new String();
            try{
                // ca selectionne la description de la table
                ResultSet verifRSET = connex.prepareStatement("DESC " + table).executeQuery();
                // Ca prend ligne par ligne du resultset
                while(verifRSET.next()){
                    // Ca prend la valeur du champ Key pour la ligne actuelle
                    String keyTable = verifRSET.getString("Key");
                    // Ca verifie si elle est primaire
                    if(keyTable.compareTo("PRI") == 0){
                        // Si elle l'est on prend la valeur du champ qui est clé primaire
                        keyNom = verifRSET.getString("Field");
                        // On compare a la valeur du champ qu'on veut tester
                        if(keyNom.compareTo(valChamp) == 0){
                            // Si on a trouvé le meme champ on le fait savoir et on 
                            Hashtable tableHash = (Hashtable)changedID.get(table);
                            System.out.println("Trouvééééééééééé meme champ : " + valChamp + " " + valContent);
                            Integer valContentInt = new Integer(valContent);
                            if(tableHash.containsKey(valContentInt)){
                                System.out.println("contient la clé : " + valContentInt);
                                // Hashtable priID = (Hashtable)tableHash.get(valContentInt);
                                if(tableHash.get(valContentInt) != valContentInt){
                                    valContentInt = (Integer)tableHash.get(valContentInt);
                                    valContent = valContentInt.toString();
                                    System.out.println("nouveau valContent : " + valContent);
                                    return valContent;
                                }else
                                    return valContent;
                            }
                        }
                    }
                }
            }catch(Exception e){
                System.out.println("[Synchronize][verificationParent] Exception : " + e );
                e.printStackTrace();
            }
        }
        return valContent;
        
    }
/***********************************************************************************************/    
    private boolean isPrimaryKey(String valChamp, String table){
        try{
            ResultSet rsetDesc = connex.prepareStatement("DESC " + table).executeQuery();
            while(rsetDesc.next()){
                if(rsetDesc.getString("Field").compareTo(valChamp) == 0)
                    if(rsetDesc.getString("KEY").compareTo("PRI") == 0)
                        return true;
                    else
                        return false;
            }
        }catch(Exception e){
            System.out.println("[Synchronize][isPrimaryKey] Exception : " + e);
            e.printStackTrace();
            return false;
        }
        return false;
    }
/***********************************************************************************************/    
    private void modifyAction(Hashtable fieldsAndValues, String table){
        Enumeration keys = fieldsAndValues.keys();
        String field = (String)keys.nextElement();
        String requette = field + "='" + (String)fieldsAndValues.get(field) + "',";
        
        while(keys.hasMoreElements()){
            field = (String)keys.nextElement();
            requette = requette + field + "='" + (String)fieldsAndValues.get(field) + "',";
        }
        
        requette = requette.substring(0, requette.length()-1);
        System.out.println("requette : " + requette );

        
        try{
            connex.prepareStatement("UPDATE " + table + " SET " + requette).executeUpdate();
        }catch(Exception e){
            System.out.println("[Synchronize][insertAction] Exception : " + e);
            e.printStackTrace();
        }
        
    }
    
    
    private void insertAction(Hashtable fieldsAndValues, String table){
        System.out.println("INSERT ACTION");
        Enumeration keys = fieldsAndValues.keys();
        String field = (String)keys.nextElement();
        String fields = field + ",";
        String values = "'" + (String)fieldsAndValues.get(field) + "'" + ",";
        
        while(keys.hasMoreElements()){
            field = (String)keys.nextElement();
            fields = fields + field + ",";
            values = values + "'" + (String)fieldsAndValues.get(field) + "'" + ",";
        }
        
        values = "(" + values.substring(0, values.length()-1) + ")";
        fields = "(" + fields.substring(0, fields.length()-1) + ")";
        System.out.println("Fields : " + fields ) ;
        System.out.println("Values : " + values ) ;
        
        try{
            connex.prepareStatement("INSERT INTO " + table + " " + fields + " values" + values).executeUpdate();
            ResultSet insertRSET = connex.prepareStatement("select @@IDENTITY as 'ident'").executeQuery();
            if(insertRSET.next()){
                Integer lineID = new Integer(insertRSET.getInt("ident"));
                Hashtable a = new Hashtable();
                
                if(changedID.containsKey(table))
                    a = (Hashtable)changedID.get(table);
                
                System.out.println("last ID : " + lastID + " new ID : " + lineID);
                a.put(lastID, lineID);
                
                if(!changedID.containsKey(table))
                    changedID.put(table, a);
            }
        }catch(Exception e){
            System.out.println("[Synchronize][insertAction] Exception : " + e);
            e.printStackTrace();
        }
        
    }
/***********************************************************************************************/
    public String primaryKey(String nomTable){
        try{
            DatabaseMetaData metadata = connex.getMetaData();
            ResultSet clefs = metadata.getPrimaryKeys(connex.getCatalog(),null,nomTable);
            while(clefs.next()){
               String nomColonne = clefs.getString("COLUMN_NAME");
               System.out.println("La colonne "+nomColonne+"est une clef primaire de "+nomTable);
               return nomColonne;
            }
        }catch(Exception e){
            System.out.println("[Synchronize][primaryKey] Exception : " + e );
            e.printStackTrace();
        }
        return "";
    }
}
