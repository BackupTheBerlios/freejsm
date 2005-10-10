/*
 * ComboObjet.java
 *
 * Created on 22 février 2005, 18:24
 */

package classes;

/**
 *
 * @author  nahuel
 */
public class ComboObjet {
    String nom = new String();
    int id;
    /** Creates a new instance of ComboObjet */
    public ComboObjet(String nom, int id) {
        this.id = id;
        this.nom = nom;
    }
    
    public String toString(){
        return nom;
    }
    
    public int getID(){
        return id;
    }
    
}
