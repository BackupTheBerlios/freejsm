/*
 * Synchronization.java
 *
 * Created on 26 janvier 2005, 16:24
 */

package classes;

/**
 *
 * @author  nahuel
 */

import java.lang.Object;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.Enumeration;
import java.util.Vector;

public class Synchronization {
    
    Connection connex;
    
    /** Creates a new instance of Synchronization */
    public Synchronization(Connection connexSource, Vector liaisonTables) {
        this.connex = connexSource;
        
        //listAllTables();
        readJournal(liaisonTables);
    }
    
    public void listAllTables(){
        try{
            ResultSet rset = connex.prepareStatement("SHOW TABLES").executeQuery();
            // on prend toutes les valeurs des noms des tables contenues dans la base dont il y a la connection
            //for(int e = 0 ; e < rset.getRow(); e++){
            while(rset.next()){
                String tableName = rset.getString(1);
                ResultSet tablerset = connex.prepareStatement("SELECT * FROM " + tableName).executeQuery();
                System.out.println("\n\n\n--- Table : " + tableName + "---");
                
                while(tablerset.next()){
                    System.out.print("\n--- Ligne : ");
                    for(int i = 1 ; i < tablerset.getMetaData().getColumnCount(); i++){
                        System.out.print(tablerset.getString(i) + " --- ");
                    }
                }
            }
        }catch(Exception e){
            System.out.println("[Synchronization][Synchronization] Exception : " + e );
            e.printStackTrace();
        }
        
        
        /* for(int e = 0 ; e < rsetMD.getColumnCount(); e++){
            obj = rset.getObject(e);
            switch(rsetMD.getColumnType(e)){
                case Types.FLOAT:break;
                case Types.REAL:break;
                case Types.DOUBLE:break;
                case Types.VARCHAR:break;
                case Types.CHAR:break;
                case Types.INTEGER:break;
                case Types.TIMESTAMP:break;
                case Types.DATE:break;
                case Types.TINYINT:break;
                default:break;
            }
        }*/
    }
    
    public void readJournal(Vector liaisonTables){
        try{
            ResultSet rsetJournal = connex.prepareStatement("SELECT * FROM JOURNAL").executeQuery();
            ResultSetMetaData rsetJournalMD = rsetJournal.getMetaData();
            
                    while(rsetJournal.next()){
                        /* for( int i = 1 ; i < rsetJournalMD.getColumnCount() ; i++ ) {
                            System.out.print(rsetJournal.getString(rsetJournalMD.getColumnName(i)));
                        }
                        System.out.println();*/
                        if(rsetJournal.getString("JO_OPERATION").compareTo("I") == 0){
                            String requetteChamps = "";
                            String requetteValues = "";
                            String nouVal = rsetJournal.getString("JO_NOUVAL");
                            String nouValTab = rsetJournal.getString("JO_TABNOM");
                            int k = nouVal.length();
                            for( int i = 0 ; i < k; ){
                                nouVal = nouVal + "#";
                                i = nouVal.indexOf("###",i);
                                int j = nouVal.indexOf("###",i + 3);

                                
                                String valActuelle = nouVal.substring(i+3,j);
                                valActuelle = valActuelle + "#";
                                String valChamp = valActuelle.substring(0,valActuelle.indexOf("=>"));
                                String valContent = valActuelle.substring(valActuelle.indexOf("=>") + 2,valActuelle.indexOf("#",valActuelle.indexOf("=>") + 2));
                                
                                
                                /**************gestion des modifications ds une table de jointure*************/
                                
                                String relationTable = isRelationnedTo(liaisonTables,valChamp,connex);
                                System.out.println("isRelationnedTo : " + relationTable + " valChamp : " + valChamp);
                                                                
                                ResultSet rsetRelation = connex.prepareStatement("SELECT JO_NOUVAL from JOURNAL where JO_TABNOM = " + relationTable).executeQuery();
                                while(rsetRelation.next()){
                                    String journalNouval = rsetRelation.getString("JO_NOUVAL");
                                    int m = journalNouval.length();
                                    for ( int l = 0 ; l < m ; ){
                                        journalNouval = journalNouval + "#";
                                        l = journalNouval.indexOf("###",l);
                                        int n = journalNouval.indexOf("###",l + 3);
                                        String valAct = journalNouval.substring(i + 3 , j);
                                        valAct = valAct + "#";
                                        String valCh = valAct.substring(0,valAct.indexOf("=>"));
                                        String valCont = valAct.substring(valAct.indexOf("=>") + 2, valAct.indexOf("#",valAct.indexOf("=>") + 2));
                                        if( valChamp == valCh && valContent == valCh ){
                                            
                                            connex.prepareStatement("").executeQuery();
                                            
                                            
                                        }
                                    }
                                }
                                /****************************************************************************/
                                
                                if(!isPrimaryKey(valChamp,nouValTab,connex)){
                                    requetteChamps = requetteChamps + valChamp;
                                    requetteValues = requetteValues + valContent;
                                    requetteChamps = requetteChamps + ",";
                                    requetteValues = requetteValues + ",";
                                }
                                i = j;
                            }
                            requetteChamps = "(" + requetteChamps.substring(0,requetteChamps.length()-1) + ")";
                            requetteValues = "(" + requetteValues.substring(0,requetteValues.length()-1) + ")";
                            System.out.println("requetteChamps => " + requetteChamps);
                            System.out.println("requetteValues => " + requetteValues);
                        }
                    }
        }catch(Exception e){
            System.out.println("[Synchronization][readJournal] Exception : " + e);
            e.printStackTrace();
        }
    }
    
    public String isRelationnedTo(Vector liaisonTables, String champ, Connection connection){
        try{
            for(int i = 0 ; i < liaisonTables.size() ; i++ ){
                String table = (String)liaisonTables.get(i);
                ResultSet rsetTable = connection.prepareStatement("SELECT * from " + table).executeQuery();
                if(rsetTable.findColumn(champ) >= 0)
                    return table;
            }
        }catch(Exception e){
//            System.out.println("[Synchronization][isRelationned] Exception : " + e);
//            e.printStackTrace();
            return "";
        }

        return "";
        
    }
    
    
    
    public boolean isPrimaryKey(String field, String table, Connection connection){
        try{
            ResultSet rsetDesc = connection.prepareStatement("DESC " + table).executeQuery();
            while(rsetDesc.next()){
                if(rsetDesc.getString("Field").compareTo(field) == 0)
                    if(rsetDesc.getString("KEY").compareTo("PRI") == 0)
                        return true;
                    else
                        return false;
            }
        }catch(Exception e){
            System.out.println("[Synchronization][isPrimaryKey] Exception : " + e);
            e.printStackTrace();
        }

        return false;

    }
    
}
