/*
 * DBConverter.java
 *
 * Created on 27 janvier 2004, 23:35
 */

package com.develog.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
/**
 *
 * @author  yann
 */
public class DBConverter {
    
    private String dBOrigine = null;
    private String dBDestination = null;
    private String tableOrigine = null;
    private String tableDestination = null;
    private String clef = null;
    private Vector correspondance = new Vector();
    private boolean suppOrigine = false;
    private DBLogger log = null;
    
    public DBConverter() {
        
    }
    
    public DBConverter(String dbO, String dbD, String to, String k, String td) throws Exception {
        //On verifie qu'aucun des parametre n'est null
        if(dbO == null || dbD == null || to == null || td == null)
            throw new Exception("[DBConverter][CONSTRUCTEUR] Au moins l'un des param du constructeur est null");
        
        dBOrigine       = dbO;
        dBDestination   = dbD;
        tableOrigine    = to;
        tableDestination = td;
        clef            = k;
        
    }
    
    public DBConverter(String dbO, String dbD, String to, String k, String td, Vector corres) throws Exception {
        //On verifie qu'aucun des parametre n'est null
        if(dbO == null || dbD == null || to == null || td == null || corres ==null)
            throw new Exception("[DBConverter][CONSTRUCTEUR] Au moins l'un des param du constructeur est null");
        
        dBOrigine       = dbO;
        dBDestination   = dbD;
        tableOrigine    = to;
        tableDestination = td;
        correspondance  = corres;
        clef            = k;
    }
    
    public DBConverter(String dbO, String dbD, String to, String k, String td, Vector corres, boolean suppOrigin) throws Exception {
        //On verifie qu'aucun des parametre n'est null
        if(dbO == null || dbD == null || to == null || td == null || corres ==null)
            throw new Exception("[DBConverter][CONSTRUCTEUR] Au moins l'un des param du constructeur est null");
        
        dBOrigine       = dbO;
        dBDestination   = dbD;
        tableOrigine    = to;
        tableDestination = td;
        correspondance  = corres;
        clef            = k;
        suppOrigine     = suppOrigin;
    }
       
    public void setCorrespondance(Vector corres) {
        correspondance = corres;
    }
    
    public void convert() throws Exception {
        //On lance la convertion avec les valeurs courante de l'objet
        convert(dBOrigine,dBDestination,tableOrigine,clef,tableDestination,correspondance);
    }
    
    public void convert(String dbO, String dbD, String to,String k,String td, Vector corres) throws Exception {
        //On cree les deux connections
        Connection connexionOrigine = DBTool.sGetDBConnection(dbO, "usat", "sylvie");
        Connection connexionDestination = DBTool.sGetDBConnection(dbD, "usat", "sylvie");
        log = new DBLogger("DBConverter",connexionDestination);
        
        ResultSet toIndex     = null;
        ResultSet tdInsertion   = null;
        if(k == null)
            k = new String("*");
        
        //On charge un ResultSet avec le contenu de la table d'origine
        try {
            toIndex = connexionOrigine.prepareStatement("select "+k+" from "+to).executeQuery();
        }catch(SQLException SQLe)
        {
            throw new Exception("[DBConverter][convert] SQLe lors de la remontee des index de la table: "+SQLe);
        }
        System.out.println("Index remonté");
        //On traite ligne à ligne
        if(toIndex != null) {
            String req = null;
            
            while(toIndex.next()){
                
                //On remonte la ligne corespondant à l'index courant
                try {
                    System.out.println("On traite l'index:"+toIndex.getString(1));
                    PreparedStatement pstmtContenu = connexionOrigine.prepareStatement(
                        "select * from "+to+" where "+k+" = ?"); 
                    pstmtContenu.setObject(1,toIndex.getObject(1));
                    ResultSet toContenu = pstmtContenu.executeQuery();
                    
                    while(toContenu.next()) {
                        //Si la requète n'est pas construite, on le fait
                        if(req == null) {
                            req = new String("insert into "+td+" (");

                            for(int index=0; index < correspondance.size();index++) {
                                //Si il y a deja un nom de champ ds la query, on ajoute une virgule
                                if (req.indexOf('(') < req.length()-1)
                                    req += ",";
                                //on recupere la methode de convertion du champ
                                Vector convertionChamp = (Vector)correspondance.elementAt(index);
                                //on construit la req d'insertion dans la table de destination
                                req += (String)convertionChamp.elementAt(1);
                            }
                            req += ") values (";
                            for(int index=0; index < correspondance.size();index++) {
                                //Si il y a deja des valeur ds la query, on ajoute une virgule
                                if (req.indexOf('?') >=0)
                                    req += ",";

                                //on construit la req d'insertion dans la table de destination
                                req += "?";
                            }
                            req += ")";
                            System.out.println("On a construit la req:"+req);
                        }

                        PreparedStatement pstmt = null;

                        try{
                            pstmt = connexionDestination.prepareStatement(req);
                        }catch(SQLException SQLe){
                            throw new Exception("[DBConverter][convert]SQLe lors de la creation du preparedStatement d'injection: "+SQLe);
                        }

                        //On rempli le pstmt
                        for(int index=0; index < correspondance.size();index++) {
                            //on recupere la methode de convertion du champ
                            Vector convertionChamp = (Vector)correspondance.elementAt(index);
                            //on recupere la valeur du champ dans la table d'origine
                            Object toObj = toContenu.getObject((String)convertionChamp.elementAt(0));
                            //on traite l'objet en fonction du filtre en 3
                            Object tdObj = null;
                            if(((String)convertionChamp.elementAt(3)).length() > 0)
                                tdObj = filtrer(((String)toObj),((String)convertionChamp.elementAt(3)));
                            else
                                tdObj = toObj;
                            //on recupere la methode de convertion du champ
                            Vector champ = (Vector)correspondance.elementAt(index);
                            //on ajoute les objets ds le pstmt
                            try {
                                pstmt.setObject(index+1,tdObj);
                            }catch(SQLException SQLe) {
                                throw new Exception("[DBConverter][convert]SQLe lors du remplissage du preparedStatement d'injection sur le champ "+index+" de type "+((Integer)champ.elementAt(2)).intValue()+", la req est =>"+req+"<= : "+SQLe);
                            }catch(NumberFormatException NFe) {
                                throw new Exception("[DBConverter][convert]NFe lors du remplissage du preparedStatement d'injection sur le champ "+index+" : "+NFe);
                            }catch(ClassCastException CCe) {
                                throw new Exception("[DBConverter][convert]CCe lors du remplissage du prepareStatement d'injection sur le champ "+index+" : "+CCe);
                            }
                        }

                        //On injecte dans la db de destination
                        try {
                            tdInsertion = pstmt.executeQuery();
                            System.out.println("Inséré!");
                        }catch(SQLException SQLe) {
                            log.error("[DBConverter][convert]SQLe lors de l'injection, la req est =>"+req+"<= : "+SQLe);
                        }catch(NumberFormatException NFe) {
                            log.error("[DBConverter][convert]NFe lors de l'injection, la req est =>"+req+"<= : "+NFe);
                        }catch(ClassCastException CCe) {
                            log.error("[DBConverter][convert]CCe lors de l'injection, la req est =>"+req+"<= : "+CCe);
                        }
                    }
                    //Si cela a ete demande, on supprime l'enregistrement d'origine
                    if(suppOrigine)
                        toIndex.deleteRow();
                }catch(SQLException SQLe)
                {
                    log.error("[DBConverter][convert] SQLe lors de la remontee des donnees de la table d'origine: "+SQLe);
                }
            }
        }
    }
    
    public static Vector createFiltre(String chO,String chD,int typ,String f) {
        Vector result = new Vector();
        result.addElement(new String(chO));
        result.addElement(new String(chD));
        result.addElement(new Integer(typ));
        result.addElement(new String(f));

        return result;
    }
    
    public static String filtrer(String aFiltrer, String filtre) {
        if(aFiltrer != null) {
            int index = 0;
            while(index < filtre.length()) {
                String nomFiltre = filtre.substring(index,filtre.indexOf('{',index));
                //System.out.println("Filtre:"+nomFiltre+" / chaine:"+filtre.substring(index));
                if(nomFiltre.compareTo("r") == 0) { //remplacer
                    //On recup les params
                    int paramIndex = index+1+chercheIndexFinDeParam(filtre.substring(filtre.indexOf('{',index)+1));
                    //System.out.println("Chaine : "+filtre.substring(filtre.indexOf('{',index)+1,paramIndex));
                    String corpsFiltre = filtre.substring(filtre.indexOf('{',index)+1,paramIndex);
                    //On recupere le premier couple x,y
                    String aChercher= corpsFiltre.substring(0,corpsFiltre.indexOf(','));
                    String remplacement = corpsFiltre.substring(corpsFiltre.indexOf(',')+1);
                    aFiltrer = aFiltrer.replaceAll(aChercher, remplacement);
                    index = paramIndex+1;
                }
                if(nomFiltre.compareTo("rc") == 0) { //remplacer chaine
                    //On recup les params
                    int paramIndex = index+1+chercheIndexFinDeParam(filtre.substring(filtre.indexOf('{',index)+1));
                    System.out.println("Corps Filtre =>"+filtre.substring(filtre.indexOf('{',index)+1,paramIndex+1)+"<=");
                    String corpsFiltre = filtre.substring(filtre.indexOf('{',index)+1,paramIndex+1);
                    //On recupere le premier couple x,y
                    Vector param = recupParams(corpsFiltre);
                    if(param.size() > 0)
                        aFiltrer = new String((String)param.elementAt(0));
                    else
                        aFiltrer = new String("");
                    //System.out.println("Nouvelle Chaine =>"+aFiltrer+"<=");
                    index = paramIndex+2;
                }
                if(nomFiltre.compareTo("aa") == 0) { //ajouter apres
                    int paramIndex = index+1+chercheIndexFinDeParam(filtre.substring(filtre.indexOf('{',index)+1));
                    String corpsFiltre = filtre.substring(filtre.indexOf('{',index)+1,paramIndex+1);
                    //System.out.println("Corps:"+corpsFiltre);
                    aFiltrer = corpsFiltre + aFiltrer;
                    //System.out.println("Nouvelle chaine: "+aFiltrer);
                    index = paramIndex+2;
                }
                if(nomFiltre.compareTo("se") == 0) { //si egal
                    int paramIndex = index+1+chercheIndexFinDeParam(filtre.substring(filtre.indexOf('{',index)+1));
                    String corpsFiltre = filtre.substring(filtre.indexOf('{',index)+1,paramIndex+1);
                    Vector params = recupParams(corpsFiltre);
                    if(((String)params.elementAt(0)).equals("l")) {
                        int longueur = 0;
                        try {
                            longueur = (new Integer((String)params.elementAt(1))).intValue();
                        }catch(NumberFormatException NFe)
                        {
                            System.out.println("Le param de longueur n'est pas valide");
                        }
                        if(aFiltrer.length() == longueur)
                            aFiltrer = filtrer(aFiltrer,(String)params.elementAt(2));
                    }
                    index = paramIndex+2;

                }
                if(nomFiltre.compareTo("sd") == 0) { //si different
                    int paramIndex = index+1+chercheIndexFinDeParam(filtre.substring(filtre.indexOf('{',index)+1));
                    String corpsFiltre = filtre.substring(filtre.indexOf('{',index)+1,paramIndex+1);
                    Vector params = recupParams(corpsFiltre);
                    if(((String)params.elementAt(0)).equals("l")) {
                        int longueur = 0;
                        try {
                            longueur = (new Integer((String)params.elementAt(1))).intValue();
                        }catch(NumberFormatException NFe)
                        {
                            System.out.println("Le param de longueur n'est pas valide");
                        }
                        if(aFiltrer.length() != longueur)
                            aFiltrer = filtrer(aFiltrer,(String)params.elementAt(2));
                    }
                    index = paramIndex+2;

                }
                if(nomFiltre.compareTo("si") == 0) { //si inferieur
                    int paramIndex = index+1+chercheIndexFinDeParam(filtre.substring(filtre.indexOf('{',index)+1));
                    String corpsFiltre = filtre.substring(filtre.indexOf('{',index)+1,paramIndex+1);
                    Vector params = recupParams(corpsFiltre);
                    if(((String)params.elementAt(0)).equals("l")) {
                        int longueur = 0;
                        try {
                            longueur = (new Integer((String)params.elementAt(1))).intValue();
                        }catch(NumberFormatException NFe)
                        {
                            System.out.println("Le param de longueur n'est pas valide");
                        }
                        System.out.println("Longueur:"+longueur);
                        if(aFiltrer.length() < longueur)
                            aFiltrer = filtrer(aFiltrer,(String)params.elementAt(2));
                    }
                    index = paramIndex+2;
                }
                if(nomFiltre.compareTo("am") == 0) { //Applique masque
                    int paramIndex = index+1+chercheIndexFinDeParam(filtre.substring(filtre.indexOf('{',index)+1));
                    String corpsFiltre = filtre.substring(filtre.indexOf('{',index)+1,paramIndex+1);
                    String masque = ((String)recupParams(corpsFiltre).elementAt(0));
                    String resultat = new String("");
                    int nbDelim = 0;
                    for(int i=0;((i<masque.length())&&((i-nbDelim)<aFiltrer.length()));i++) {
                        if(masque.charAt(i) == '#')
                            resultat += aFiltrer.charAt(i-nbDelim);
                        else {
                            resultat += masque.charAt(i);
                            nbDelim++;
                        }
                    }
                    aFiltrer = resultat;
                    index = paramIndex+2;
                }
                if(nomFiltre.compareTo("sc") == 0) { //sous chaine
                    int paramIndex = index+1+chercheIndexFinDeParam(filtre.substring(filtre.indexOf('{',index)+1));
                    if(aFiltrer.length() > 0) {
                        String corpsFiltre = filtre.substring(filtre.indexOf('{',index)+1,paramIndex+1);
                        Vector params = recupParams(corpsFiltre);
                        int debut = 0;
                        int fin = 0;
                        //si il y a un seul param, c'est l'index de debut de cesure
                        if(params.size() > 1) {
                            try {
                                fin = (new Integer(((String)params.elementAt(1)))).intValue();
                            }catch(NumberFormatException NFe) {
                                System.out.println("Format de l'index de fin de scésure incorrect");
                            }
                        }else{
                            fin = aFiltrer.length();
                        }
                        try {
                            debut = (new Integer(((String)params.elementAt(0)))).intValue();
                        }catch(NumberFormatException NFe) {
                            System.out.println("Format de l'index de debut de scésure incorrect");
                        }
                        //System.out.println("Chaine:"+aFiltrer+" /debut:"+debut+" /fin:"+fin);
                        aFiltrer = aFiltrer.substring(debut,fin);
                    }
                    
                    index = paramIndex+2;
                }
                if(nomFiltre.compareTo("scr") == 0) { //sous chaine relative à deux autres
                    int paramIndex = index+1+chercheIndexFinDeParam(filtre.substring(filtre.indexOf('{',index)+1));
                    if(aFiltrer.length() > 0) {
                        String corpsFiltre = filtre.substring(filtre.indexOf('{',index)+1,paramIndex+1);
                        Vector params = recupParams(corpsFiltre);
                        int debut = 0;
                        int fin = 0;
                        try {
                            debut = aFiltrer.indexOf((String)params.elementAt(0));
                            if(debut > -1) //On pare au cas ou la chaine de depart n'existe pas
                                debut += ((String)params.elementAt(0)).length();
                        }catch(NumberFormatException NFe) {
                            System.out.println("Format de l'index de debut de scésure incorrect");
                        }
                        //si il y a un seul param, c'est l'index de debut de cesure
                        if(params.size() > 1) {
                            try {
                                if(params.size() > 2) { // on recherche le premier delimiteur
                                    int min = -1;
                                    for(int ind=1;ind < params.size();ind++){
                                        int elPosition = aFiltrer.indexOf((String)params.elementAt(ind),debut);
                                        if((elPosition > debut) && ((min == -1) || ((elPosition > -1) && (elPosition < min)))){
                                            min = elPosition;
                                            //System.out.println("Nouveau min:"+min);
                                        }
                                    }
                                    fin = min;
                                }else
                                    fin = aFiltrer.indexOf((String)params.elementAt(1));
                            }catch(NumberFormatException NFe) {
                                System.out.println("Format de l'index de fin de scésure incorrect");
                            }
                        }else{
                            fin = aFiltrer.length();
                        }
                        
                        //System.out.println("Chaine:"+aFiltrer+" /debut:"+debut+" /fin:"+fin);
                        if((debut != -1) && (fin != -1)){
                            aFiltrer = aFiltrer.substring(debut,fin);
                            System.out.println("Filtree :"+aFiltrer);
                        }else{
                            aFiltrer = new String("");
                            //System.out.println("NON FILTREE :"+aFiltrer);
                        }
                    }
                    
                    index = paramIndex+3;
            //System.out.println(index +"<"+ filtre.length());
                }
                if(nomFiltre.compareTo("td") == 0) { //trim debut
                    int paramIndex = index+1+chercheIndexFinDeParam(filtre.substring(filtre.indexOf('{',index)+1));
                    while((aFiltrer.length() > 0) && (aFiltrer.charAt(0) == ' '))
                        aFiltrer = aFiltrer.substring(1);
                    index = paramIndex+2;
                }
                if(nomFiltre.compareTo("tf") == 0) { //trim fin
                    int paramIndex = index+1+chercheIndexFinDeParam(filtre.substring(filtre.indexOf('{',index)+1));
                    while((aFiltrer.length() > 0) && (aFiltrer.charAt(aFiltrer.length() - 1) == ' '))
                        aFiltrer = aFiltrer.substring(0,aFiltrer.length() - 1);
                    index = paramIndex+2;
                }
                if(nomFiltre.compareTo("tt") == 0) { //trim tout
                    int paramIndex = index+1+chercheIndexFinDeParam(filtre.substring(filtre.indexOf('{',index)+1));
                    while((aFiltrer.length() > 0) && (aFiltrer.indexOf("  ") != -1))
                        aFiltrer = aFiltrer.replaceAll("  ", " ");
                    index = paramIndex+2;
                }
                //System.out.println("FIn de boucle");
            }
        }else return null;
        return aFiltrer;
    }
    
    private static int chercheIndexFinDeParam(String f) {
        int ouvertes = 1;
        int fermees = 0;
        int paramIndex = 0;
        while (ouvertes != fermees) {
            if(f.charAt(paramIndex) == '}')
                fermees++;
            else if(f.charAt(paramIndex) == '{')
                ouvertes++;
            paramIndex++;
        }
        //System.out.println("Chaine à trait: "+f+" / fin:"+paramIndex);
        return paramIndex;
    }
    
    private static Vector recupParams(String s) {
        Vector result = new Vector();
        int index = 0;
        while (index < s.length()) {
            int indexSeparateur = s.indexOf(',',index);
            if(indexSeparateur < 0)
                indexSeparateur = s.length();
            //System.out.println("Chaine:"+s+" / sep:"+indexSeparateur);
            result.add(new String(s.substring(index,indexSeparateur)));
            index = indexSeparateur + 1;
        }
        return result;
    }
}


