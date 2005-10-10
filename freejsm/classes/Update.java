/*
 * update.java
 *
 * Created on 28 mai 2004, 15:17
 */

package classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

/**
 *
 * @author  alexandre
 * Ceci n'est qu'un patch pour la base de données.
 */

public class Update {
    
    private String QueryUpdate0 = new String(
            "update ACTE set AC_CREDIT0 = '0'  where AC_CREDIT0 is null");
    private String QueryUpdate1 = new String(
            "update ACTE set AC_CREDIT1 = '0'  where AC_CREDIT1 is null");
    private String QueryUpdate2 = new String(
            "update ACTE set AC_CREDIT2 = '0'  where AC_CREDIT2 is null");
    private String QueryUpdate3 = new String(
            "update ACTE set AC_CREDIT3 = '0'  where AC_CREDIT3 is null");
    private String QueryUpdate4 = new String(
            "update ACTE set AC_CREDIT4 = '0'  where AC_CREDIT4 is null");
    private String QueryUpdate5 = new String(
            "update ACTE set AC_CREDIT5 = '0'  where AC_CREDIT5 is null");
    private String QueryUpdate6 = new String(
            "update ACTE set AC_CREDIT6 = '0'  where AC_CREDIT6 is null");
    private String QueryUpdate7 = new String(
            "update ACTE set AC_CREDIT7 = '0'  where AC_CREDIT7 is null");
    private String QueryUpdate8 = new String(
            "update ACTE set AC_CREDIT8 = '0'  where AC_CREDIT8 is null");
    private String QueryUpdate9 = new String(
            "update ACTE set AC_CREDIT9 = '0'  where AC_CREDIT9 is null");
   private String QueryUpdate10 = new String(
            "update ACTE set AC_CREDIT10 = '0'  where AC_CREDIT10 is null");
    private String QueryUpdate11 = new String(
            "update ACTE set AC_CREDIT11 = '0'  where AC_CREDIT11 is null");
    private String QueryUpdate12 = new String(
            "update ACTE set AC_CREDIT12 = '0'  where AC_CREDIT12 is null");
    private String QueryUpdate13 = new String(
            "update ACTE set AC_CREDIT13 = '0'  where AC_CREDIT13 is null");
    
    private String QueryUpdate14 = new String(
            "update ADHERENT SET AD_ALLPROF = substring(AD_ID,1,2) where AD_ALLPROF=00");
    
    private String QueryUpdate15 = new String(
            "update ADHERENT SET AD_MAINPROF = substring(AD_ALLPROF,1,2) where AD_MAINPROF = 00 " );
    
    private String QueryUpdate16 = new String(
            "update ADHERENT SET AD_ALLPROF = REPLACE(AD_ALLPROF , '/00','')");

    private String QueryUpdate17 = new String(
            "update ADHERENT SET AD_ALLPROF = REPLACE(AD_ALLPROF , '/','')");
    
    
    /** Creates a new instance of update */
    public Update(Connection connectEXT) {
    
       ResultSet UsageUnique     = null;
       PreparedStatement pstmt   = null;
       
       try {
            pstmt        = connectEXT.prepareStatement(QueryUpdate0);
            UsageUnique  = pstmt.executeQuery();
            pstmt        = connectEXT.prepareStatement(QueryUpdate1);
            UsageUnique  = pstmt.executeQuery();
            pstmt        = connectEXT.prepareStatement(QueryUpdate2);
            UsageUnique  = pstmt.executeQuery();
            pstmt        = connectEXT.prepareStatement(QueryUpdate3);
            UsageUnique  = pstmt.executeQuery();
            pstmt        = connectEXT.prepareStatement(QueryUpdate4);
            UsageUnique  = pstmt.executeQuery();
            pstmt        = connectEXT.prepareStatement(QueryUpdate5);
            UsageUnique  = pstmt.executeQuery();
            pstmt        = connectEXT.prepareStatement(QueryUpdate6);
            UsageUnique  = pstmt.executeQuery();
            pstmt        = connectEXT.prepareStatement(QueryUpdate7);
            UsageUnique  = pstmt.executeQuery();
            pstmt        = connectEXT.prepareStatement(QueryUpdate8);
            UsageUnique  = pstmt.executeQuery();
            pstmt        = connectEXT.prepareStatement(QueryUpdate9);
            UsageUnique  = pstmt.executeQuery();
            pstmt        = connectEXT.prepareStatement(QueryUpdate10);
            UsageUnique  = pstmt.executeQuery();
            pstmt        = connectEXT.prepareStatement(QueryUpdate11);
            UsageUnique  = pstmt.executeQuery();
            pstmt        = connectEXT.prepareStatement(QueryUpdate12);
            UsageUnique  = pstmt.executeQuery();
            pstmt        = connectEXT.prepareStatement(QueryUpdate13);
            UsageUnique  = pstmt.executeQuery();
            pstmt        = connectEXT.prepareStatement(QueryUpdate14);
            UsageUnique  = pstmt.executeQuery();
            pstmt        = connectEXT.prepareStatement(QueryUpdate15);
            UsageUnique  = pstmt.executeQuery();
            pstmt        = connectEXT.prepareStatement(QueryUpdate16);
            UsageUnique  = pstmt.executeQuery();
            pstmt        = connectEXT.prepareStatement(QueryUpdate17);
            UsageUnique  = pstmt.executeQuery();
            
       }catch(SQLException SQLe)
            {
                 System.out.println(SQLe);
            }  
   }
}
