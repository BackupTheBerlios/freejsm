

import javax.swing.*; 
import forms.PrincipaleGUI;
import de.muntjak.tinylookandfeel.*;

class FreeJSM extends Thread implements Runnable{
    
   public static void main(String[] a) {
           new FreeJSM().start();
    }
    
   public void run() {
        // On tente de charger le theme, en cas d'echec, on poursuit
        try {
            Theme.style = 2;
            UIManager.setLookAndFeel("de.muntjak.tinylookandfeel.TinyLookAndFeel");
        } 
        catch (Exception e) {
            System.out.println("Erreur au chargement du thême WindowsXP:"+e);
        }
        // Création de la fenetre principale */
        PrincipaleGUI GUI = new PrincipaleGUI();
        GUI.show();
    }
}
