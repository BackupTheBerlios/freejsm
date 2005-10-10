/*
 * ExtensionFilter.java
 *
 * Created on 13 février 2004, 03:21
 */

package com.develog.io;

/**
 *
 * @author  yann
 */

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;

public class ExtensionFilter extends javax.swing.filechooser.FileFilter {
    private String description = new String("");
    private HashSet extensions = new HashSet();
    
    public ExtensionFilter(String desc) {
        super();
        description = desc;
    }
    
    public void addExtension(String extension) {
        extensions.add(extension);
    }
    
    public void setDescription(String desc) {
        if(desc != null)
            description = desc;
    }
    
    public boolean accept(File f) {
        //Si aucune extension n'a ete precisee, on affiche tout
        if(extensions.size() == 0)
            return true;
        //On affiche les repertoires
        if (f.isDirectory())
          return true;
        
        //Recuperation de l'extension
        String extension = getExtension(f);
        //On verifie qu'elle correspond au critere
        if (extensions.contains(extension))
           return true;

        //Arrive ici, on sait que le fichier ne doit pas etre affiche
        return false;
    }
    
    public String getDescription() {
        String result = new String(description+" (");
        for (Iterator e = extensions.iterator() ; e.hasNext() ;) {
            if(!result.endsWith("("))
                result += ", ";
            result += "*."+((String)e.next());
        }
        result += ")";
        return result;
    }
    
    private String getExtension(File f)
    {
        String s = f.getName();
        int i = s.lastIndexOf('.');
        if (i > 0 &&  i < s.length() - 1)
          return s.substring(i+1).toLowerCase();
        return "";
    }
}
