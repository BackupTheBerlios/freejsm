/*
 * DataFile.java
 *
 * Created on 17 janvier 2004, 15:50
 */

package classes;

import java.io.File;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Vector;
/**
 *
 * @author  Yann  Marigo pour Dévelog
 */
public class DataFile {
    
    private static boolean isInt(char c) {
        try {
            Integer i = new Integer(new String(""+c));
        } catch (NumberFormatException NFe)
        { return false;}
        return true;
    }
    
    private static boolean isData(String s,char separateur) {
        //Si la ligne ne commence pas par un ! 
        //ou que le second caractere n'est pas un int
        //on retourne faux
        if ((s == null) || (s.length() < 2) || (s.charAt(0) != separateur) || (!isInt(s.charAt(1))))
            return false;

        return true;
    }
    
    private static Date getDate(String s) {
        int l = s.length();
        return Date.valueOf(s.substring(l-10,l-6)+"-"+s.substring(l-13,l-11)+"-01");
    }
    
    private static Vector getChamps(String s) {
        //Boucle de recuperation du mot suivant
        Vector result = new Vector();
        int curseur = 1;
        int nbExclamSuc = 0;
        while (curseur < s.length()-1) {
            //On decoupe selon les !
            int nextSep = s.indexOf('!',curseur);
            String bloc = s.substring(curseur,nextSep);
            //On eleve les blancs en début et en fin de bloc
            while (bloc.startsWith(" "))
                bloc = bloc.substring(1,bloc.length());
            while (bloc.endsWith(" "))
                bloc = bloc.substring(0,bloc.length()-1);
            //On stocke le bloc nettoye
            result.addElement(bloc);
            curseur = nextSep+1;
        }
        return result;
    }
    
    private static void traiterLigne(String s,char separateur,Date dateFichier,Connection connex) {
        try {
            PreparedStatement insert = connex.prepareStatement(
                "insert into CAPEB (CA_ID,CA_DATE_FICHIER,CA_PERIODE,"
                    +"CA_MILLESIME_PERIODE,CA_NOMPRENOM,CA_EFFECTIF,CA_SALAIRE,"
                    +"CA_EMISSION,CA_ENCAISS,CA_ETAT_ENCAISS)"
                    +" values (?,?,?,?,?,?,?,?,?,?)");
            PreparedStatement update = connex.prepareStatement(
                "update CAPEB set CA_ID=?,CA_DATE_FICHIER=?,CA_PERIODE=?,"
                    +"CA_MILLESIME_PERIODE=?,CA_NOMPRENOM=?,CA_EFFECTIF=?,CA_SALAIRE=?,"
                    +"CA_EMISSION=?,CA_ENCAISS=?,CA_ETAT_ENCAISS=?"
                    +"where CA_ID=? and CA_PERIODE=? and CA_MILLESIME_PERIODE=?");
            //On teste la ligne pour savoir si on la traite
            if (isData(s,separateur)) {
                //On récupere la liste des champs sous forme de Vector
                Vector contenu = getChamps(s);
                ResultSet rset = null;
                
                String periode = (String)contenu.elementAt(2);

                //Si le champ 3 du vecteur est une chaine vide, on insere
                //sinon, on update
                if (((String)contenu.elementAt(3)).compareTo("") == 0) {
                    insert.setInt(1, new Integer((String)contenu.elementAt(0)).intValue());
                    insert.setDate(2,dateFichier);
                    insert.setString(3,periode.substring(0,periode.indexOf(' ')));
                    insert.setInt(4, new Integer(periode.substring(periode.indexOf(' ')+1)).intValue());
                    insert.setString(5, (String)contenu.elementAt(1));
                    if (((String)contenu.elementAt(5)).compareTo("") != 0)
                        insert.setInt(6, new Integer((String)contenu.elementAt(5)).intValue());
                    else insert.setNull(6, Types.DOUBLE);
                    if (((String)contenu.elementAt(6)).compareTo("") != 0)
                        insert.setDouble(7, new Double(((String)contenu.elementAt(6)).replaceAll("[^0-9,-]","").replaceAll("[^0-9-]",".")).doubleValue());
                    else insert.setNull(7, Types.DOUBLE);
                    if (((String)contenu.elementAt(8)).compareTo("") != 0)
                        insert.setDouble(8, new Double(((String)contenu.elementAt(8)).replaceAll("[^0-9,-]","").replaceAll("[^0-9-]",".")).doubleValue());
                    else insert.setNull(8, Types.DOUBLE);
                    if (((String)contenu.elementAt(9)).compareTo("") != 0)
                        insert.setDouble(9, new Double(((String)contenu.elementAt(9)).replaceAll("[^0-9,-]","").replaceAll("[^0-9-]",".")).doubleValue());
                    else insert.setNull(9, Types.DOUBLE);
                    if (((String)contenu.elementAt(9)).compareTo("") != 0)
                        insert.setBoolean(10, true);
                    else insert.setBoolean(10, false);
                    
                    rset = insert.executeQuery();
                } else if (((String)contenu.elementAt(3)).compareTo("B") == 0) {
                    update.setInt(1, new Integer((String)contenu.elementAt(0)).intValue());
                    update.setDate(2,dateFichier);
                    update.setString(3,periode.substring(0,periode.indexOf(' ')));
                    update.setInt(4, new Integer(periode.substring(periode.indexOf(' ')+1)).intValue());
                    update.setString(5, (String)contenu.elementAt(1));
                    if (((String)contenu.elementAt(5)).compareTo("") != 0)
                        update.setInt(6, new Integer((String)contenu.elementAt(5)).intValue());
                    else update.setNull(6, Types.DOUBLE);
                    if (((String)contenu.elementAt(6)).compareTo("") != 0)
                        update.setDouble(7, new Double(((String)contenu.elementAt(6)).replaceAll("[^0-9,-]","").replaceAll("[^0-9-]",".")).doubleValue());
                    else update.setNull(7, Types.DOUBLE);
                    if (((String)contenu.elementAt(6)).compareTo("") != 0)
                        update.setDouble(8, new Double(((String)contenu.elementAt(8)).replaceAll("[^0-9,-]","").replaceAll("[^0-9-]",".")).doubleValue());
                    else update.setNull(8, Types.DOUBLE);
                    if (((String)contenu.elementAt(6)).compareTo("") != 0)
                        update.setDouble(9, new Double(((String)contenu.elementAt(9)).replaceAll("[^0-9,-]","").replaceAll("[^0-9-]",".")).doubleValue());
                    else update.setNull(9, Types.DOUBLE);
                    if (((String)contenu.elementAt(9)).compareTo("") != 0)
                        update.setBoolean(10, true);
                    else update.setBoolean(10,false);
                    
                    //On insere les conditions
                    update.setInt(11, new Integer((String)contenu.elementAt(0)).intValue());
                    update.setString(12,(periode).substring(0,periode.indexOf(' ')));
                    update.setInt(13, new Integer(periode.substring(periode.indexOf(' ')+1)).intValue());

                    rset = update.executeQuery();
                }
            }
        }catch(SQLException SQLe)
        { System.out.println("[USAT][traiterLigne] SQLe : "+SQLe);}
         catch (NumberFormatException NFe)
        { System.out.println("[USAT][traiterLigne] NFe : "+NFe);}
    }
    
    
    public static void importCapeb(File fichier,Connection connection) {
        //On recupere le fichier a importer
        RandomAccessFile raf = null;
        
        try {
            raf = new RandomAccessFile(fichier,"r");
            
            //On recupere ligne a ligne le contenu et on le traite
            long fichierPointer = 0;
            long fichierTaille = raf.length();
            
            if (fichierPointer < fichierTaille) {
                String ligne = raf.readLine();
                fichierPointer = raf.getFilePointer();
                Date dateFichier = getDate(ligne);
                while (fichierPointer < fichierTaille) {
                    //Lecture d'une ligne et traitement
                    //Si la ligne est une data on la traite

                    ligne = raf.readLine(); 
                    DataFile.traiterLigne(ligne,'!',dateFichier,connection);
                    fichierPointer = raf.getFilePointer();
                }
            }
        }catch (Exception e)
        { System.out.println("Exception : "+e);}
    }
}
