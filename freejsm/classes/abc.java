/*
 * abc.java
 *
 * Created on 25 avril 2005, 11:16
 */

package classes;

import java.sql.*;
import java.math.BigDecimal;
/**
 *
 * @author  nahuel
 */
public class abc {
    
    /** Creates a new instance of abc */
    public abc(Connection connexion) {
        try{
            System.out.println("debut");
            ResultSet rset = connexion.prepareStatement("SELECT article.AR_ID, TVA_ID, CS_PRIXUNITAIRE FROM contenu_stock, article WHERE article.AR_ID = contenu_stock.AR_ID;").executeQuery();
            while(rset.next()){
                if(rset.getInt("TVA_ID") == 1){
                    BigDecimal prix = rset.getBigDecimal("CS_PRIXUNITAIRE");
                    System.out.println("Prix original : " + prix);
                    prix = prix.divide(new BigDecimal("1.055"), 2, BigDecimal.ROUND_HALF_EVEN);
                    System.out.println("Prix modifié avec 5.5 : " + prix);
                    connexion.prepareStatement("UPDATE cs_contenu SET CS_PRIXUNITAIRE = '" + prix + "' WHERE AR_ID = " + rset.getInt("AR_ID")).executeUpdate();

                }else if(rset.getInt("TVA_ID") == 2){        
                    BigDecimal prix = rset.getBigDecimal("CS_PRIXUNITAIRE");
                    System.out.println("Prix original : " + prix);
                    prix = prix.divide(new BigDecimal("1.196"), 2, BigDecimal.ROUND_HALF_EVEN);
                    System.out.println("Prix modifié avec 19.6 : " + prix);
                    connexion.prepareStatement("UPDATE cs_contenu SET CS_PRIXUNITAIRE = '" + prix + "' WHERE AR_ID = " + rset.getInt("AR_ID")).executeUpdate();                

                }
            }
        }catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
    }
    
}
