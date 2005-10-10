/* Cette classe permet de connecter un cube de données avec une interface utili
 * lisateur .On s'adresse tout le temps à ce connecteur pour réaliser les opé.
 * sur la base de données.
 */

package com.develog.utils;

import javax.swing.JPanel;
import java.util.Hashtable;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JFormattedTextField;
import java.util.Enumeration;
import java.util.Vector;
import java.awt.Component;


public class SmartConnector {
    
    private Hashtable H_intObjectField = new Hashtable();
    private SmartCube Sc_intsmartcube;
    private JPanel    jPinterne;
    private Vector    listeCompo       = new Vector();
    public final  int JTextFieldClass = 1;
    public final  int JCheckBoxClass  = 2;
    public final  int JTextAreaClass  = 3;
    public final  int JFormattedTextFieldClass  = 4;
    
    /** Permet de gérer le cas où une même entité est gérée sur N panels
     * ex:Systéme à onglets.Il est aussi possible d'exclure des panneaux de 
     * l'affectation grace à ce systéme,pour traiter le cas où 2 panneaux "maitres"
     * seraient sur l'un dans l'autre.
     */
    public SmartConnector(SmartCube sc, Vector EJPanel){
        Sc_intsmartcube = sc;
        createList(EJPanel);
        cleanList();
    }
    
    /* Cette méthode 'épure' la lsite des composants suceptibles d'interagir 
     * on ne conserve que les types qui nous plaisent et surtout on dégage tout
     * ce qui n'est pas nommé pour interragir avec la base de donnée          */
    private void cleanList(){
        
       for (int loop=0; loop < listeCompo.size(); loop++ )
        /** Filtre de composants arbitraires */
        if (((Component)listeCompo.get(loop)).getName().compareTo("null") != 0 ){
           if ((!listeCompo.get(loop).getClass().isAssignableFrom(javax.swing.JTextField.class)) &&
              (!listeCompo.get(loop).getClass().isAssignableFrom(javax.swing.JCheckBox.class))  && 
              (!listeCompo.get(loop).getClass().isAssignableFrom(javax.swing.JTextArea.class))  &&
              (!listeCompo.get(loop).getClass().isAssignableFrom(javax.swing.JFormattedTextField.class)))
                listeCompo.remove(loop);
        }
    }
    
    /* Cette méthode compose la liste de touts les composants de composants */
    private void createList(Vector EJPanel){
        Vector tempo    = new Vector();   
        for (Enumeration e = EJPanel.elements() ; e.hasMoreElements() ;){
            tempo = AllCompoments((JPanel)(e.nextElement()),new Vector());
            for (Enumeration f = tempo.elements() ; f.hasMoreElements() ;)
              listeCompo.add(f.nextElement());
        }
    }
    
    /* Fixe les valeurs à l'écran avec les données de la base , ses valeurs sont
     * affectées en fonction de la classe de base de l'objet */
    public void setValuesFromCube() throws Exception{
     try{
        for (int loop=0; loop < listeCompo.size(); loop++ ){
          switch(getObjectClass((Component)listeCompo.get(loop))){
            case JTextFieldClass:{ ((JTextField)(Component)listeCompo.get(loop)).setText(Sc_intsmartcube.getCubeDataObject(((Component)listeCompo.get(loop)).getName()).toString());
            }break;
            case JCheckBoxClass :{ ((JCheckBox)(Component)listeCompo.get(loop)).setSelected(crade(Sc_intsmartcube.getCubeData(((Component)listeCompo.get(loop)).getName())));
            }break;
            case JTextAreaClass :{ ((JTextArea)(Component)listeCompo.get(loop)).setText(Sc_intsmartcube.getCubeDataObject(((Component)listeCompo.get(loop)).getName()).toString());
            }break;
            case JFormattedTextFieldClass:{ ((JFormattedTextField)(Component)listeCompo.get(loop)).setText(Sc_intsmartcube.getCubeDataObject(((Component)listeCompo.get(loop)).getName()).toString());
            }break;
          }
        }
     }catch(Exception e){throw new Exception("[smartCoonector][setValuesFromCube]Impossible de fixer les valeurs à l'écran:"+e);}
    }
    
    /* Grise les éléments a l'écran , cette méthode est bien pratique car elle
     * permet de gérer des éléments à l'écran dans différents états */
    public void setEditable(boolean state){
        
        for (int loop=0; loop < listeCompo.size(); loop++ ){
          switch(getObjectClass((Component)listeCompo.get(loop))){ 

            case JTextFieldClass:{ ((JTextField)(Component)listeCompo.get(loop)).setEnabled(state);
            }break;
            case JCheckBoxClass :{ ((JCheckBox)(Component)listeCompo.get(loop)).setEnabled(state);
            }break;
            case JTextAreaClass :{ ((JTextArea)(Component)listeCompo.get(loop)).setEnabled(state);
            }break;
            case JFormattedTextFieldClass:{ ((JFormattedTextField)(Component)listeCompo.get(loop)).setEnabled(state);
            }break;
          }
        }        
    }
    
    /* Artifice qui permet juste d'optenir un boolean à partir d'une chaine */
    private boolean crade(String valeur){
        if ( valeur.compareTo("false") == 0) return false;
        else return true;
    }
        
    /* Prepare la hasmap d'envoie vers le cube de données */
    public void getValuesFromUI() throws Exception {
        String name = "";
        try{
            for (int loop=0 ;loop < listeCompo.size(); loop++ ){
                name   = ((Component)listeCompo.get(loop)).getName(); 
                switch(getObjectClass((Component)listeCompo.get(loop))){    
                    case JTextFieldClass:{ H_intObjectField.put(name, convertToCube(name,((JTextField)(Component)listeCompo.get(loop)).getText()));
                    }break;
                    case JCheckBoxClass :{ H_intObjectField.put(name, convertToCube(name,""+((JCheckBox)(Component)listeCompo.get(loop)).isSelected()));
                    }break;
                    case JTextAreaClass :{ H_intObjectField.put(name, convertToCube(name,((JTextArea)(Component)listeCompo.get(loop)).getText()));
                    }break;
                    case JFormattedTextFieldClass:{ H_intObjectField.put(name , convertToCube(name,((JFormattedTextField)(Component)listeCompo.get(loop)).getText()));
                    }break;
                }
            }
         }catch(Exception e){
             throw new Exception("[smartConnector][getValuesFromUI]Format nom attendu sur le composant " + name);}
    }
    
    /* Recherche le type de l'objet tel qu'il est dans la cube et assure une convertion */
    private Object convertToCube(String Name,String Valeur) throws Exception{
    try{
        long datebidon = 0;
        if (Sc_intsmartcube.getColumnType(Name).compareTo("java.lang.Integer") == 0) return new Integer(Valeur);
        if (Sc_intsmartcube.getColumnType(Name).compareTo("java.lang.String")  == 0) return new String(Valeur);        
        if (Sc_intsmartcube.getColumnType(Name).compareTo("java.lang.Double")  == 0) return new Double(Valeur);        
        if (Sc_intsmartcube.getColumnType(Name).compareTo("java.lang.Byte")    == 0) return new Boolean(Valeur);
        if (Sc_intsmartcube.getColumnType(Name).compareTo("java.lang.Boolean") == 0) return new Boolean(Valeur);
        if (Sc_intsmartcube.getColumnType(Name).compareTo("java.sql.Date")     == 0) return new java.sql.Date(datebidon).valueOf(MySQLDate(Valeur));
        if (Sc_intsmartcube.getColumnType(Name).compareTo("java.lang.Float")   == 0) {
                            System.out.println(Valeur);
            return new Float(Valeur);
        }
        return new String(Valeur);
       }catch(Exception e){
             throw new Exception("[smartConnector][convertToCube]Impossible de convertir depuis le cube " + e);}
    }
    
    
    /* Donne la classe principale du composant interrogé */
    public int getObjectClass(Component comp){
    
        int value = 0;
        if ( comp.getClass().isAssignableFrom(javax.swing.JTextField.class) ) return JTextFieldClass;
        if ( comp.getClass().isAssignableFrom(javax.swing.JCheckBox.class) )  return JCheckBoxClass;
        if ( comp.getClass().isAssignableFrom(javax.swing.JTextArea.class) )  return JTextAreaClass ;
        if ( comp.getClass().isAssignableFrom(javax.swing.JFormattedTextField.class) )  return JFormattedTextFieldClass;
        return value;
    }
    
   /* Cette méthode renvoie tous les composants d'un composant du moment que c'est un panel
    */
   private Vector AllCompoments(JPanel pere, Vector Compose){
        int ComposeNumber = pere.getComponentCount();
        /* On fait une boucle de parcours recursif pour trouver tous les fils dont le
         * nom n'est pas vide */
        for(int loop=0;loop < ComposeNumber;loop++){
          if (pere.getComponent(loop).getClass().isAssignableFrom(javax.swing.JPanel.class)){
             AllCompoments((JPanel)pere.getComponent(loop),Compose);}
          else {
            if ( !pere.getComponent(loop).getClass().isAssignableFrom(javax.swing.JScrollPane.class))
                if ( pere.getComponent(loop).getName() != null ) Compose.addElement(pere.getComponent(loop));
          } 
        }
        return Compose;
   }
   
   /* Permet de convertir une chaine de caractï¿½re dd/MM/yyyy */
   /* Au format attendu par MySQL (dd/MM/yyyy)               */
   public String DateSQLMy(String DateFormatee){    
        //if (  DateFormatee == null ) return ("  /  /    ");
        return(DateFormatee.substring(5,7));
        //+ "/" + DateFormatee.substring(5,7) + "/" + DateFormatee.substring(0,4)) ;
   }
   
   /* Permet de convertir une chaine de caractï¿½re dd/MM/yyyy */
   /* Au format attendu par MySQL (yyyy-dd-MM)               */
   public String MySQLDate(String DateFormatee){
       System.out.println("<"+DateFormatee+">");
       if ( DateFormatee.compareTo("  /  /    ")==0) return "9999-01-01";
       if (  DateFormatee == null ) DateFormatee ="0000-00-00";
       return((DateFormatee.substring(6,10) + "-"  + DateFormatee.substring(3,5)) + "-" + DateFormatee.substring(0,2)) ;
   }   
   
   /** Genére une ligne vide qu'il faudra remplir ,ceci permet de réserver l'emplacement 
    *  dans le cube */
   public void genNewRowWithID() throws Exception { 
       Sc_intsmartcube.startNewCubeRowInsertion();
       Sc_intsmartcube.finaliseNewCubeRowInsertion();
   }
  
   /** Ajoute une nouvelle ligne à au cube , on evite le commit pour permettre 
     * le rollback de données sur le cube lui meme au niveau UI */
    public void addNewRowWithID() throws Exception {
        updateRow();
    }
       
   /** Genere un ligne sans ID afin de pouvoir la remplir à la mano */
    public void genNewRowNoneID() throws Exception{
       Sc_intsmartcube.startNewCubeRowInsertion();
   }
    /** Ajoute une nouvelle ligne à au cube , on evite le commit pour permettre 
     * le rollback de données sur le cube lui meme au niveau UI */
    public void addNewRowNoneID() throws Exception {
        getValuesFromUI();
        Sc_intsmartcube.setCubeData(H_intObjectField);
        Sc_intsmartcube.finaliseNewCubeRowInsertion();
    }
    
    public void updateRow() throws Exception {
        getValuesFromUI();
        Sc_intsmartcube.setCubeData(H_intObjectField);
        Sc_intsmartcube.commitCubeTransaction();
    }
    
    public void deleteRow() throws Exception{
        Sc_intsmartcube.deleteCubeRow();
    }
}