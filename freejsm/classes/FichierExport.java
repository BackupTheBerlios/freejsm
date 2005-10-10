/*
 * FichierExport.java
 *
 * Created on 12 février 2004, 19:14
 */

package classes;

import com.develog.database.DBLogger;
import java.io.IOException;
import java.io.File;
import java.io.RandomAccessFile;
import java.lang.NumberFormatException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
/**
 *
 * @author  yann
 */
public class FichierExport {
    
    public static File genererFichierGaz(String cheminFichier,Connection connect) throws Exception{
        DBLogger log = new DBLogger("[FichierExport][genererFichierGaz]",connect);
        String reqContenu = new String("select * from RESPONSABLES_GAZ where ET_CODE is not null and ET_CODE <> '' order by RG_ID");
        String reqAdher   = new String("select AD_CODEFEDE from ADHERENT where AD_ID = ?");
        //On ouvre le fichier
        File fichier = new File(cheminFichier);
        try{
            RandomAccessFile raf = new RandomAccessFile(fichier,"rws");
            try {
                ResultSet rsetContenu = connect.prepareStatement(reqContenu).executeQuery();
                while(rsetContenu.next()) {
                    //Pour chaque individu, on cree la ligne correspondante
                    //On recherche son entreprise (adherent) de rattachement
           System.out.println("On traite: "+rsetContenu.getString("AD_ID"));
                    PreparedStatement pstmtAdher = connect.prepareStatement(reqAdher);
                    pstmtAdher.setString(1, rsetContenu.getString("AD_ID"));
                    ResultSet rsetAdher = pstmtAdher.executeQuery();
                    if(rsetAdher.next()) {
                        String dateHabilitation = rsetContenu.getString("RG_DATE_HABILITATION");
                        if((dateHabilitation != null) && (dateHabilitation.compareTo("0000-00-00") != 0)){ 
                            dateHabilitation = dateHabilitation.replaceAll("[^0-9]","");
                        }else
                            dateHabilitation = new String("");
                        //On ajuste la longueur du code fede
                        String codeFede = ""+rsetAdher.getInt("AD_CODEFEDE");
                        while(codeFede.length() < 6)
                            codeFede = "0"+codeFede;
                        String ligne = new String("CAP81A"
                                            +codeFede
                                            +"BDEP81A"
                                            +rsetContenu.getString("RG_ID")
                                            +rsetContenu.getString("RG_NOM")+" "
                                            +rsetContenu.getString("ET_CODE")
                                            +dateHabilitation+"\n");
                        //On ajoute la ligne dans le fichier
                        raf.writeBytes(ligne);
                        System.out.println("On écrit la ligne: =>"+ligne+"<=");
                    }else{
                        log.warn("[FichierExport][genererFichierGaz] Responsable gaz rattaché à une entreprise inexistante : "+rsetContenu.getString("AD_ID"));
                    }
                }
            }catch(SQLException SQLe) {
                throw new Exception("[FichierExport][genererFichierGaz]SQLe lors de la remontée des données: "+SQLe);
            }
            //On referme le fichier.
            raf.close();
        }catch(IOException IOe) {
            throw new Exception("[FichierExport][genererFichierGaz]IOe lors de l'ouverture ou de l'écriture du fichier: "+IOe);
        }
        return fichier;
    }
    
    public static File genererFichierCapeb(String cheminFichier,Connection connect) throws Exception{
        DBLogger log = new DBLogger("[FichierExport][genererFichierCapeb]",connect);
        Calendar c = Calendar.getInstance();
        String reqContenu = new String("select distinct(AD_ID) as Code ,AD_NOM, AD_RUE, AD_CPOS, AD_VILLE,SY_LIBELLE " +
            "from ADHERENT,ACTE,SYNDICAT "+
            "where ACTE.AC_CODE=ADHERENT.AD_ID and ACTE.AC_TYPE='C' and ACTE.AC_ANNEE = '" + c.get(Calendar.YEAR) 
            + "' and AD_MAINPROF >= 1 and AD_MAINPROF <=8  and ADHERENT.AD_MAINPROF=SYNDICAT.SY_ID order by ADHERENT.AD_ID");
        //On ouvre le fichier
        File fichier = new File(cheminFichier);
        try{
            RandomAccessFile raf = new RandomAccessFile(fichier,"rws");
            try {
                ResultSet rsetContenu = connect.prepareStatement(reqContenu).executeQuery();
                while(rsetContenu.next()) {
                    //Pour chaque individu, on cree la ligne correspondante
           System.out.println("On traite: "+rsetContenu.getString("Code"));
                    String ligne = new String("81"
                                        +rsetContenu.getString("AD_NOM")+"\t"
                                        +rsetContenu.getString("AD_RUE")+"\t"
                                        +rsetContenu.getString("AD_CPOS")+" "
                                        +rsetContenu.getString("AD_VILLE")+"\t"
                                        +rsetContenu.getString("SY_LIBELLE")+"\n");
                    //On ajoute la ligne dans le fichier
                    raf.writeBytes(ligne);
                    System.out.println("On écrit la ligne: =>"+ligne+"<=");
                }
            }catch(SQLException SQLe) {
                throw new Exception("[FichierExport][genererFichierCapeb]SQLe lors de la remontée des données: "+SQLe);
            }
            //On referme le fichier.
            raf.close();
        }catch(IOException IOe) {
            throw new Exception("[FichierExport][genererFichierCapeb]IOe lors de l'ouverture ou de l'écriture du fichier: "+IOe);
        }
        return fichier;
    }
    
    public static File genererListeAdher(String cheminFichier,Connection connect) throws Exception{
        DBLogger log = new DBLogger("[FichierExport][genererListeAdher]",connect);
        String reqContenu = new String("select * from ADHERENT order by AD_ID");
        String reqSyndicat = new String("select SY_LIBELLE from SYNDICAT where SY_ID=?");
        //On ouvre le fichier
        File fichier = new File(cheminFichier);
        try{
            RandomAccessFile raf = new RandomAccessFile(fichier,"rws");
            //On insere les titres de colone
            raf.writeBytes("ADHERENT\tPROF_PRINCIPALE\tPROF_SEC\tNOM\tRAISON_SOCIALE\tADRESSE\tLIEU_DIT\tCODE_POSTAL\tVILLE\tCIP\tPGN\tPGP\tAB_5\n");
            //On rempli le tableau
            
            try {
                ResultSet rsetContenu = connect.prepareStatement(reqContenu).executeQuery();
                while(rsetContenu.next()) {
                    //On extrait le code de la profession secondaire
                    String codeProfSec = null;
                    String allProf = rsetContenu.getString("AD_ALLPROF").replaceAll("[^0-9]","");
                    if (allProf.length() > 3)
                        codeProfSec = allProf.substring(2,4);
                    String libelleProfSec = null;
                    //On remonte de la table SYNDICAT le nom du syndicat de rattachement ainsi que du secondaire
                    if((codeProfSec != null) && (codeProfSec.compareTo("  ") != 0)) {
                        try {
                            PreparedStatement pstmtSyndic = connect.prepareStatement(reqSyndicat);
                            pstmtSyndic.setInt(1,(new Integer(codeProfSec)).intValue());
                            ResultSet rsetSyndicat = pstmtSyndic.executeQuery();
                            while(rsetSyndicat.next())
                                libelleProfSec = rsetSyndicat.getString(1);
                        }catch(NumberFormatException NFe) {
                            throw new Exception("[FichierExport][genererListeAdher]NFe de la convertion du code de profession secondaire: "+NFe);
                        }catch(SQLException SQLe) {
                            throw new Exception("[FichierExport][genererListeAdher]SQLe lors de la recherche du libellé de la profession secondaire: "+SQLe);
                        }
                    }else
                        libelleProfSec = new String("");
                    //On recherche le libelle de la prof principale
                    String libelleProf = null;
                    try {
                            PreparedStatement pstmtSyndic = connect.prepareStatement(reqSyndicat);
                            pstmtSyndic.setInt(1,(new Integer(rsetContenu.getString("AD_MAINPROF"))).intValue());
                            ResultSet rsetSyndicat = pstmtSyndic.executeQuery();
                            while(rsetSyndicat.next())
                                libelleProf = rsetSyndicat.getString(1);
                        }catch(NumberFormatException NFe) {
                            throw new Exception("[FichierExport][genererListeAdher]NFe de la convertion du code de profession principale: "+NFe);
                        }catch(SQLException SQLe) {
                            throw new Exception("[FichierExport][genererListeAdher]SQLe lors de la recherche du libellé de la profession principale: "+SQLe);
                        }
                    //Pour chaque individu, on cree la ligne correspondante
                    String ligne = new String("N"+"\t"
                                            +libelleProf+"\t"  
                                            +libelleProfSec+"\t"
                                            +rsetContenu.getString("AD_NOM")+"\t"
                                            +rsetContenu.getString("AD_RAISOC")+"\t"
                                            +rsetContenu.getString("AD_RUE")+"\t"
                                            +rsetContenu.getString("AD_LIEUDIT")+"\t"
                                            +rsetContenu.getString("AD_CPOS")+"\t"
                                            +rsetContenu.getString("AD_VILLE")+"\t"
                                            +"N\tN\tN\tN"+"\n");
                    //On ajoute la ligne dans le fichier
                    raf.writeBytes(ligne);
                    //System.out.println("On écrit la ligne: =>"+ligne+"<=");
                }
            }catch(SQLException SQLe) {
                throw new Exception("[FichierExport][genererListeAdher]SQLe lors de la remontée des données: "+SQLe);
            }
            //On referme le fichier.
            raf.close();
        }catch(IOException IOe) {
            throw new Exception("[FichierExport][genererListeAdher]IOe lors de l'ouverture ou de l'écriture du fichier: "+IOe);
        }
        return fichier;
    }
}
