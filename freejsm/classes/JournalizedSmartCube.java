/*
 * JournalizedSmartCube.java
 *
 * Created on 25 janvier 2005, 12:41
 */

package classes;

/**
 *
 * @author  nahuel
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.sql.PreparedStatement;

import java.util.Hashtable;
import java.util.Enumeration;

import com.develog.utils.SmartConnector;
import com.develog.utils.SmartCube;


public class JournalizedSmartCube extends SmartCube {

    private String requetteANC;
    private String requetteNEW;
    private Connection connex;
    private Hashtable clefruche;
    private String requette;
    
    public JournalizedSmartCube(Connection connex) throws SQLException {
        super(connex);
        this.connex = connex;
    }
    
    public void setCubeData(Hashtable clefruche) throws Exception{
	super.setCubeData(clefruche);
        this.clefruche = clefruche;
    }

    public void commitDBTransaction(boolean update, Integer util_id, String table, Integer line_id, String id_tab, String requetteANC){
        try{
            if(!update)
                requetteANC = "";// getRequette(update, table, id_tab, line_id);
                
            super.commitDBTransaction();
        if(!update){    
            ResultSet rset2 = connex.prepareStatement("select @@IDENTITY as 'ident'").executeQuery();
            rset2.next();
            line_id = new Integer(rset2.getInt(1));
        }
        }catch(Exception e){
            System.out.println("[JournalizedSmartCube][commitDBTransaction] Exception : " + e);
            e.printStackTrace();
        }
        /*Enumeration cles = clefruche.keys();
        Enumeration valeurs = clefruche.elements();
        String valeursChamp = "";
                
	while(cles.hasMoreElements()){
            String cle = (String)cles.nextElement();
            
            valeursChamp = valeursChamp + "###" + cle + "=>" + clefruche.get(cle);
            System.out.print(cle+" : ");
            System.out.println(clefruche.get(cle));
        }*/
        try{
            requetteNEW = getRequette(update, table, id_tab, line_id);
            
            PreparedStatement statement1 = connex.prepareStatement("INSERT INTO JOURNAL(UTIL_ID,JO_TABNOM,JO_PRECVAL,JO_NOUVAL,JO_OPERATION) VALUES(" + util_id + ",'" + table + "',?,?,?)");
            statement1.setString(2,requetteNEW);
            if(update != true){
                statement1.setString(3,"I");
                statement1.setString(1,"");
            }else{
                statement1.setString(1,requetteANC);
                statement1.setString(3,"U");
            }
            statement1.executeUpdate();
        }catch(Exception e){
            System.out.println("[JournalizedSmartCube][commitDBTransaction] Exception : " + e);
            e.printStackTrace();
        }
    }
    
    public String getRequette(boolean update, String table, String id_tab, Integer line_id){
        String requetteGEN;
        //if(update){
            try{
                    ResultSet rset = connex.prepareStatement("SELECT * FROM " + table + " WHERE " + id_tab + "  = " + line_id).executeQuery();
                    rset.next();
                    ResultSetMetaData rsetmd = rset.getMetaData();
                    requetteGEN = "";
                    for( int i = 1 ; i <= rsetmd.getColumnCount() ; i++ ){

                        String value;

                        switch(rsetmd.getColumnType(i)){
                            case Types.INTEGER:value = "" + rset.getInt(i);break;
                            case Types.FLOAT:  value = "" + rset.getFloat(i);break;
                            case Types.REAL:   value = "" + rset.getFloat(i);break;
                            case Types.DOUBLE: value = "" + rset.getDouble(i);break;
                            case Types.VARCHAR:value = "" + rset.getString(i);break;
                            case Types.CHAR:   value = "" + rset.getString(i);break;
                            case Types.DATE:   value = "" + rset.getDate(i);break;
                            case Types.TINYINT:value = "" + rset.getBoolean(i);break;
                            case Types.BOOLEAN:value = "" + rset.getBoolean(i);break;
                            default: { value = "";System.out.println("Rejet : " + rsetmd.getColumnName(i));}break;
                        }
                        requetteGEN = requetteGEN + "###" + rsetmd.getColumnName(i) + "=>" + value;
                    }
                    return requetteGEN;
                }catch(Exception e){
                System.out.println("[JournalizedSmartCube][getRequette] Exception " + e);
                e.printStackTrace();
                }
        //}
        return (new String(""));
    }
     
}