/*
 * UnitedRegistry.java
 *
 * Created on 1 d�cembre 2004, 13:58
 */

package classes;

import com.ice.jni.registry.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * @author  alexandre
 * Cette API peut �tre utilis�e pour �crire/modifier/supprimer dans un fichier 
 * de type clef-ruche.Elle permet d'avoir en fonction  de l'OS ou du choix du
 * concepteur un acc�s au registre Windows.
 * TODO: Penser � faire un test pour v�rifier la pr�sence de la DLL 
 * TODO: Avoir une impl�mentation XML afin de reproduire une gestion fid�le
 * du registre windows (cas:Clef,clef,clef,ruche...);
 */
public class UnitedRegistry {

    private String osName = System.getProperty("os.name");
    private int    osType = new Integer(0).intValue();
    private String registryType = "file";
    private boolean useWinRegistry = false; 
    private String pathToFile = System.getProperty("user.dir");
    private String separator = System.getProperty("file.separator");
    private String defaultConfFileName = "defaut.conf";
    private File ConfFile;
    private Properties PropertiesFile=new Properties();
    private String topKey = "HKEY_LOCAL_MACHINE";
    private String defaultConfKey = "SOFTWARE\\Develog";
    private RegistryKey keyTop;
    private RegistryKey keySubKey; 
    
    /** Creates a new instance of GestParam */
    public UnitedRegistry() {
        setOSEnv();
        logOSEnv();
    }
    
    private void logOSEnv(){
        System.out.println("OS:" + osName);
        System.out.println("Type:" + registryType);
       if (useWinRegistry) {
           System.out.println("PATH:" + topKey );
           System.out.println("KEY:" + defaultConfKey);
       }
       else {
           System.out.println("PATH:" + pathToFile);
           System.out.println("FILE:" + defaultConfFileName);
       }
    }
    /** On fixe le contexte d'ex�cution */
    private void setOSEnv(){
       if(osName.startsWith("Windows")){
            osType = 1;
            useWinRegistry = true;
            registryType = "Registre Windows";
        }
        else{
            osType = 2;
            useWinRegistry = false;
            registryType = "Registre fichier";
        }  
    }
     
    /* Le concepteur choisi si il veut utiliser le registre windows */
    public void enableWinRegistry(boolean userChoice){
        
        if ((osType==2) && (userChoice)){
            System.out.println("Warning:L'utilisation du registre n'est disponible que sous Windows"); 
            System.out.println("Warning:La valeur fix�e sera ignor�e.");
            registryType = "Registre fichier";
            System.out.println("Type utilisateur rectifi�:" + registryType);
            useWinRegistry = false;
        }else{
            registryType = "Registre Windows";
            useWinRegistry = userChoice;
            System.out.println("Type utilisateur:" + registryType);
        }
    }
    
    /* Permet de choisir le r�pertoire de sauvegarde du fichier de conf */
    public void setPath(String path){
        if (useWinRegistry) topKey = path;
        else pathToFile = path;
    }
    
    /* Permet de choisir le nom du fichier de sauvegarde */
    public void setConfName(String name){
        if (useWinRegistry) defaultConfKey = name; 
        else defaultConfFileName = name;
    }
    
    /* Acc�s unifi� � l'API Registre 
     * Ouvre l'acc�s au registre 
     * dans le cas d'un syst�me non windows alors on ouvre 
     * tout simplement le fichier de conf
     */
   
    /* Permet de se connecter au registre */
    public void connectToRegistry(){
        /* Dans le cas du fichier Unix */
        try {
             if (useWinRegistry) openRegistryWindows();
             else openRegistryFile();
        }catch(Exception e){System.out.println(e);};
    }
    
    /* Permet de se d�connecter du registre */
    public void disconnectFromRegistry(){
       try {
           closeRegistryFile();
       }catch(Exception e){};
    }
    
    /* Fixe un couple clef/ruche dans le registre*/
    public void setToRegistry(Object key,Object value){
        try {
            if (useWinRegistry) setKeyToRegistryWindows(key,value);
            else setKeyToFile(key,value);
        }catch(Exception e){System.out.println(e);}
    }
    
    /* Recup�re le contenu d'une clef depuis le registre*/
    public String getStringFromRegistry(Object key) throws Exception{ 
          if (useWinRegistry) return getStringValueFromRegistryWindows(key);
          else return getStringValueFromFile(key);
    }
    
        /* Recup�re le contenu d'une clef depuis le registre*/
    public int getIntFromRegistry(Object key){
     try {  
          if (useWinRegistry) return getIntValueFromRegistryWindows(key);
          else return getIntValueFromFile(key);
        }catch(Exception e){System.out.println(e);}
     return 0;
    }
    
    /* D�truit un couple clef/ruche dans le registre */
    public void delInRegistry(Object key){
      try {    
        if (useWinRegistry) delValueInRegistryWindows(key);
        else PropertiesFile.remove(key); 
        }catch(Exception e){System.out.println(e);}
    }
  
    /* Ouvre le registre windows */
    private void openRegistryWindows() throws Exception {
        /* On tente de se connecter au registre windows
         * dans tout les cas on cr�� l'entr�e dans le registre
         * si il existe on s'en fout car windows ignore!
         */
        keyTop = Registry.getTopLevelKey(topKey);
        keyTop.createSubKey(defaultConfKey,topKey,RegistryKey.ACCESS_ALL);
        keySubKey = keyTop.openSubKey("SOFTWARE\\Develog",RegistryKey.ACCESS_ALL);
    }
    
    /* G�n�re et ouvre le fichier de registre (si besoin) */
    private void openRegistryFile() throws Exception {
        
        ConfFile = new File(pathToFile + separator + defaultConfFileName);
        /* On teste la pr�sence du fichier ,si c'est pas le cas on cr��*/
        if(!ConfFile.exists()) ConfFile.createNewFile();
        PropertiesFile.load(new FileInputStream(ConfFile));
    }
    
    /* Enregistre les modifications sur le fichier */
    private void closeRegistryFile() throws Exception{
        PropertiesFile.store(new FileOutputStream(ConfFile),"Registre fichier");
    }
    
    /* Fixe un couple dans le registre Windows */
    private void setKeyToRegistryWindows(Object key,Object value) throws Exception {
        RegStringValue val;
        RegDWordValue valInt;
        if (value.getClass().isAssignableFrom(java.lang.Integer.class)) {
            valInt = new RegDWordValue(keySubKey,(String)key);
            valInt.setData(Integer.parseInt(""+value));
            keySubKey.setValue(valInt);
        }
        else{
            val = new RegStringValue(keySubKey,(String)key, (String)value);
            keySubKey.setValue(val);
        }
    }
    
    /* Fixe un couple dans le fichier */
    private void setKeyToFile(Object key,Object value) {
        
      if(PropertiesFile.containsKey(key)) PropertiesFile.remove(key);  
      PropertiesFile.put(key,value.toString());   
    }
    
    /* Recup�re une chaine  depuis le fichier */
    private String getStringValueFromFile(Object key){
         return PropertiesFile.get(key).toString();
    }
    
    /* R�cup�re un entier depuis le fichier */
    private int getIntValueFromFile(Object key){
        return Integer.parseInt(PropertiesFile.get(key).toString());
    }
   
    /* Recup�re une chaine de caract�re depuis le registre */
    private String getStringValueFromRegistryWindows(Object key) throws Exception{
        return(keySubKey.getStringValue((String)key));
    }
    
    /* Recup�re un entier depuis le registre */
    private int getIntValueFromRegistryWindows(Object key) throws Exception{
        return keySubKey.decrDoubleWord((String)key)+1;
    }
    
    /* Efface une clef du registre windows */
    private void delValueInRegistryWindows(Object key) throws Exception{
        keySubKey.deleteValue((String)key); 
    }
    
    public static void main(String[] args) {
     
          UnitedRegistry toto = new UnitedRegistry();
          toto.enableWinRegistry(false);
          toto.connectToRegistry();
          System.out.println(toto.getIntFromRegistry("port"));
          toto.setToRegistry("serveur","192.168.1.232");
          toto.setToRegistry("port",new Integer(3306));
          toto.disconnectFromRegistry();
     
    }
    
    
    
}
