/*
 * Misc.java
 *
 * iReport  --  Visual designer for generating JasperReports Documents
 *  Copyright (C) 2002-2003  Giulio Toffoli gt@businesslogic.it
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 *  Giulio Toffoli
 *  Via T.Aspetti, 233
 *  35100 Padova ITALY
 *  gt@businesslogic.it
 *
 * Created on 14 febbraio 2003, 16.35
 */

package classes;
import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.util.jar.*;
import java.net.*;
import java.io.*;

/**
 *
 * @author  Administrator
 */
public class Misc {
        public static final String[] special_chars= new String[]{"&","&amp;",
                                        "á","&aacute;",
                                        "â","&acirc;",
                                        "æ","&aelig;",
                                        "à","&agrave;",
                                        "å","&aring;",
                                        "ã","&atilde;",
                                        "ä","&auml;",
                                        "ç","&ccedil;",
                                        "é","&eacute;",
                                        "ê","&ecirc;",
                                        "è","&egrave;",
                                        "ð","&eth;",
                                        "ë","&euml;",
                                        ">","&gt;",
                                        "í","&iacute;",
                                        "î","&icirc;",
                                        "ì","&igrave;",
                                        "ï","&iuml;",
                                        "<","&lt;",
                                        "ñ","&ntilde;",
                                        "ó","&oacute;",
                                        "ô","&ocirc;",
                                        "ò","&ograve;",
                                        "ø","&oslash;",
                                        "õ","&otilde;",
                                        "ö","&ouml;",
                                        "\"","&quot;",
                                        "ß","&szlig;",
                                        "þ","&thorn;",
                                        "ú","&uacute;",
                                        "û","&ucirc;",
                                        "ù","&ugrave;",
                                        "ü","&uuml;",
                                        "ý","&yacute;",
                                        "ÿ","&yuml;",
                                        "¡","&#161;",
                                        "ª","&#170;",
                                        "·","&#183;",
                                        "¢","&#162;",
                                        "«","&#171;",
                                        "¸","&#184;",
                                        "£","&#163;",
                                        "®","&#174;",
                                        "¹","&#185;",
                                        "¤","&#164;",
                                        "°","&#176;",
                                        "º","&#186;",
                                        "¥","&#165;",
                                        "±","&#177;",
                                        "»","&#187;",
                                        "¦","&#166;",
                                        "²","&#178;",
                                        "¼","&#188;",
                                        "§","&#167;",
                                        "³","&#179;",
                                        "½","&#189;",
                                        "¨","&#168;",
                                        "µ","&#181;",
                                        "¾","&#190;",
                                        "©","&#169;",
                                        "¶","&#182;",
                                        "¿","&#191;",
                                        "¬","&#172;",
                                        "×","&#215;",
                                        "÷","&#247;",
                                        "±","&#177;",
                                        "·","&#183;",
                                        "½","&#189;",
                                        "«","&#171;",
                                        "²","&#178;",
                                        "¹","&#185;",
                                        "¾","&#190;",
                                        "¬","&#172;",
                                        "³","&#179;",
                                        "»","&#187;",
                                        "×","&#215;",
                                        "°","&#176;",
                                        "µ","&#181;",
                                        "¼","&#188;",
                                        "÷","&#247;"};

        /** Creates a new instance of Misc */
        public Misc() {
        }
        
         public static String xmlEscape(String text)
          {
            if( text == null) return "";
            int i=0;
            String tmp = "";
            for (i=0; i < special_chars.length; i+=2)
            {
              text = string_replace(special_chars[i], special_chars[i+1], text);
            }

            return text;
          }
         
         /*
        public static java.awt.Image loadImageFromResources(String filename) {
                try {
                        ClassLoader cl = ClassLoader.getSystemClassLoader();
                        //java.io.InputStream in = new java.io.FileInputStream( cl.getResource(filename).getPath() );
                        java.io.InputStream in = cl.getResourceAsStream(filename);
                        byte[] data = getBytesFromInputStream(in, in.available());
                        return java.awt.Toolkit.getDefaultToolkit().createImage(data);
                } catch (Exception ex) {
                        System.out.println("Exception loading resource: "+filename);
                        //ex.getMessage();
                        //ex.printStackTrace();
                }
                return null;
        }
        */
        /** New version by Umberto Uderzo */
        public static java.awt.Image loadImageFromResources(String filename) {
                try {
                        return new javax.swing.ImageIcon( Misc.class.getResource( "/" + filename )).getImage();
                } catch (Exception ex) {
                        System.out.println("Exception loading resource: " +filename);
                }
                return null;
        }
        
        /**
         * Returns an array of bytes containing the bytecodes for
         * the class represented by the InputStream
         * @param in the inputstream of the class file
         * @return the bytecodes for the class
         * @exception java.io.IOException if the class cannot be read
         */
        private static byte[] getBytesFromInputStream(java.io.InputStream in, int length)  throws java.io.IOException {
                java.io.DataInputStream din = new java.io.DataInputStream(in);
                byte[] bytecodes = new byte[length];
                try {
                        din.readFully(bytecodes);
                } finally {
                        if (din != null) din.close();
                }
                return bytecodes;
        }
        
        public static java.awt.image.BufferedImage loadBufferedImageFromResources(Component c,String filename) {
                
                try {
                        Misc m = new Misc();
                        java.awt.Image img = loadImageFromResources(filename);
                        MediaTracker mt = new MediaTracker(c);
                        mt.addImage(img,0);
                        mt.waitForID(0);
                        int width= img.getWidth(null);
                        int height= img.getHeight(null);
                        BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
                        Graphics gg = bi.getGraphics();
                        gg.drawImage(img, 0,0, null);
                        gg.dispose();
                        return bi;
                } catch (Exception ex){
                        System.out.println(ex.toString());
                }
                return null;
        }
        
        public static void updateComboBox(javax.swing.JComboBox comboBox, Vector newItems) {
                updateComboBox(comboBox,newItems, false);
        }
        public static void updateComboBox(javax.swing.JComboBox comboBox, Vector newItems, boolean addNullEntry) {
                Object itemSelected = null;
                if (comboBox.getSelectedIndex() >=0 ) {
                        itemSelected = comboBox.getSelectedItem();
                }
                
                comboBox.removeAllItems();
                boolean selected = false;
                boolean foundNullItem = false;
                Enumeration e = newItems.elements();
                while (e.hasMoreElements()) {
                        Object item = e.nextElement();
                        comboBox.addItem(item);
                        if (item == itemSelected) {
                                selected = true;
                                comboBox.setSelectedItem(itemSelected);
                        }
                        if (item.equals("")) {
                                foundNullItem = true;
                        }
                }
                
                if (addNullEntry) {
                        if (!foundNullItem) comboBox.insertItemAt("", 0);
                        if (!selected) comboBox.setSelectedItem("");
                }
                
        }
        
        /**   Mthis method perform equals based on string rapresentation of objects
         *
         */
        public static void updateStringComboBox(javax.swing.JComboBox comboBox, Vector newItems, boolean addNullEntry) {
                Object itemSelected = null;
                if (comboBox.getSelectedIndex() >=0 ) {
                        itemSelected = comboBox.getSelectedItem()+"";
                }
                
                comboBox.removeAllItems();
                boolean selected = false;
                boolean foundNullItem = false;
                Enumeration e = newItems.elements();
                while (e.hasMoreElements()) {
                        String item = ""+e.nextElement();
                        comboBox.addItem(item);
                        if (item.equals(itemSelected)) {
                                selected = true;
                                comboBox.setSelectedItem(itemSelected);
                        }
                        if (item.equals("")) {
                                foundNullItem = true;
                        }
                }
                
                if (addNullEntry) {
                        if (!foundNullItem) comboBox.insertItemAt("", 0);
                        if (!selected) comboBox.setSelectedItem("");
                }
                
        }
        
        public static String nvl(Object obj, String def) {
                return (obj == null) ? def : obj.toString();
        }
        
        public static void centerFrame(java.awt.Component c) {
                java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
                c.setLocation((int)((tk.getScreenSize().getWidth()-c.getWidth())/2),
                (int)((tk.getScreenSize().getHeight()- c.getHeight())/2) );
        }
        
        /**
         *    Replace s2 with s1 in s3
         **/
        public static String string_replace(String s1, String s2, String s3) {
                String string="";
                string = "";
                
                if (s2 == null || s3 == null || s2.length() == 0) return s3;
                
                int pos_i = 0; // posizione corrente.
                int pos_f = 0; // posizione corrente finale
                
                int len = s2.length();
                while ( (pos_f = s3.indexOf(s2, pos_i)) >= 0) {
                        string += s3.substring(pos_i,pos_f)+s1;
                        //+string.substring(pos+ s2.length());
                        pos_f = pos_i = pos_f + len;
                        
                }
                
                string += s3.substring(pos_i);
                
                return string;
        }
        
        public static java.awt.Image loadImageFromFile(String path) {
                java.io.File file = new java.io.File(path);
                if (file.exists()) {
                        java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
                        java.awt.Image img = tk.createImage(path);
                        try {
                                java.awt.MediaTracker mt = new java.awt.MediaTracker( new javax.swing.JPanel() );
                                mt.addImage(img,0);
                                mt.waitForID(0);
                        } catch (Exception ex){
                                return null;
                        }
                        return img;
                }
                return null;
        }
        
        
        
        /**
         * This method inserts a blank character between to consecutive
         * newline characters if encoutered. Also appends a blank character at
         * the beginning of the text, if the first character is a newline character
         * and at the end of the text, if the last character is also a newline.
         * This is useful when trying to layout the paragraphs.
         * Thanks to Teodor Danciu for this this method (c) 2003 Teodor Danciu
         */
        public static String treatNewLineChars(String source) {
                String result = source;
                
                if (source != null && source.length() > 0) {
                        StringBuffer sbuffer = new StringBuffer(source);
                        
                        // insert a blank character between every two consecutives
                        // newline characters
                        int offset = source.length() - 1;
                        int pos = source.lastIndexOf("\n\n", offset);
                        while (pos >= 0 && offset > 0) {
                                sbuffer = sbuffer.insert(pos + 1, " ");
                                offset = pos - 1;
                                pos = source.lastIndexOf("\n\n", offset);
                        }
                        
                        // append a blank character at the and of the text
                        // if the last character is a newline character
                        if (sbuffer.charAt(sbuffer.length() - 1) == '\n') {
                                sbuffer.append(' ');
                        }
                        
                        // append a blank character at the begining of the text
                        // if the first character is a newline character
                        if (sbuffer.charAt(0) == '\n') {
                                sbuffer.insert(0, ' ');
                        }
                        
                        result = sbuffer.toString();
                }
                
                // remove this if you want to treat the tab characters in a special way
                result = replaceTabWithBlank(result);
                
                return result;
        }
        
        
        /**
         *  Thanks to Teodor Danciu for this method (c) 2003 Teodor Danciu
         */
        public static String replaceTabWithBlank(String source) {
                String result = source;
                
                if (source != null && source.length() > 0) {
                        StringBuffer sbuffer = new StringBuffer(source);
                        
                        int offset = 0;
                        int pos = source.indexOf("\t", offset);
                        while (pos >= 0) {
                                sbuffer.setCharAt(pos, ' ');
                                offset = pos + 1;
                                pos = source.indexOf("\t", offset);
                        }
                        
                        result = sbuffer.toString();
                }
                
                return result;
        }
        
        public static String toHTML(String s) {
                s = Misc.string_replace("&gt;",">",s);
                s = Misc.string_replace("&lt;","<",s);
                s = Misc.string_replace("&nbsp;"," ",s);
                s = Misc.string_replace("&nbsp;&nbsp;&nbsp;&nbsp;","\t",s);
                s = Misc.string_replace("<br>", "\n", s);
                return s;
        }
        
        static public String getShortFileName(String filename) {
                if (filename.length() > 50) {
                        java.io.File f = new java.io.File(filename);
                        if (nvl(f.getParentFile(),"").length()>10) {
                                String dir = f.getParentFile().getPath()+java.io.File.separatorChar;
                                
                                String shortDir = dir.substring(0,dir.indexOf( java.io.File.separatorChar)+1);
                                dir = dir.substring(dir.indexOf( java.io.File.separatorChar)+1);
                                if (dir.indexOf( java.io.File.separatorChar) > 0) {
                                        shortDir += dir.substring(0, dir.indexOf( java.io.File.separatorChar)+1);
                                }
                                return shortDir + "..."+ java.io.File.separatorChar +
                                f.getName();
                        }
                }
                
                return filename;
                
        }
        
        
        /**
         * Thanx to Jackie Manning j.m@programmer.net for this method!!
         */
        public static String getJdbcTypeClass( int t ) {
                String cls = "java.lang.String";
                switch( t ) {
                        case java.sql.Types.TINYINT:
                        case java.sql.Types.BIT:
                                cls = "java.lang.Byte";
                                break;
                        case java.sql.Types.SMALLINT:
                                cls = "java.lang.Short";
                                break;
                        case java.sql.Types.INTEGER:
                                cls = "java.lang.Integer";
                                break;
                        case java.sql.Types.REAL:
                        case java.sql.Types.DOUBLE:
                        case java.sql.Types.NUMERIC:
                        case java.sql.Types.DECIMAL:
                                cls = "java.lang.Double";
                                break;
                        case java.sql.Types.CHAR:
                        case java.sql.Types.VARCHAR:
                                cls = "java.lang.String";
                                break;
                                
                        case java.sql.Types.BIGINT:
                                cls = "java.lang.Long";
                                break;
                        case java.sql.Types.DATE:
                        case java.sql.Types.TIME:
                                cls = "java.util.Date";
                                break;
                        case java.sql.Types.TIMESTAMP:
                                cls = "java.sql.Timestamp";
                                break;
                }
                return cls;
        }
        
        public static long getLastWriteTime(String filename) {
                try {
                        java.io.File f = new java.io.File(filename);
                        return f.lastModified();
                } catch (Exception ex) {
                        return -1;
                }
        }
        
        /**
         *Method used to grab the Frame which is above this component in the hierarchy.
         *This allows programmers to make any component the parent of any window or
         *dialog easier.
         *@param comp the component to get the Frame for
         *@return the Frame above this component in the hierarchy
         */
        public static java.awt.Frame frameFromComponent(java.awt.Component parent) {
                java.awt.Frame f = (java.awt.Frame)javax.swing.SwingUtilities.getAncestorOfClass(java.awt.Frame.class, parent);
                return f;
        }//end frameFromComponent
        //ErtanO 12.03.2004
        public static java.util.List getAvailablePLAF(){
                java.util.List l = new java.util.ArrayList();
                l.add("System");
                l.add("TinyLAF");
                l.add("JGoodiesLAF-PlasticXP");
                l.add("JGoodiesLAF-Plastic");
                l.add("JGoodiesLAF-Plastic3D");
                l.add("JGoodiesLAF-ExtWindows");
                
                javax.swing.UIManager.LookAndFeelInfo[] lfinfo = javax.swing.UIManager.getInstalledLookAndFeels();
                
                for (int i=0; i<lfinfo.length; ++i) {
                        l.add( lfinfo[i].getName() );
                }
                
                return l;
        }
        public static void setPLAF(String s){
                try {
                        if(s.equals("TinyLAF")) {
                                javax.swing.UIManager.setLookAndFeel("de.muntjak.tinylookandfeel.TinyLookAndFeel");
                        } else if(s.equals("JGoodiesLAF-PlasticXP")) {
                                javax.swing.UIManager.setLookAndFeel("com.jgoodies.plaf.plastic.PlasticXPLookAndFeel");
                        } else if(s.equals("JGoodiesLAF-Plastic")) {
                                javax.swing.UIManager.setLookAndFeel("com.jgoodies.plaf.plastic.PlasticLookAndFeel");
                        } else if(s.equals("JGoodiesLAF-Plastic3D")) {
                                javax.swing.UIManager.setLookAndFeel("com.jgoodies.plaf.plastic.Plastic3DLookAndFeel");
                        } else if(s.equals("JGoodiesLAF-ExtWindows")) {
                                javax.swing.UIManager.setLookAndFeel("com.jgoodies.plaf.windows.ExtWindowsLookAndFeel");
                        } else if(s.equals("System")) {
                                javax.swing.UIManager.setLookAndFeel( javax.swing.UIManager.getSystemLookAndFeelClassName() );
                        } else {
                                javax.swing.UIManager.LookAndFeelInfo[] lfinfo = javax.swing.UIManager.getInstalledLookAndFeels();
                                for (int i=0; i<lfinfo.length; ++i) {
                                        if (lfinfo[i].getName().equalsIgnoreCase( s )) {
                                                javax.swing.UIManager.setLookAndFeel(  lfinfo[i].getClassName() );
                                                return;
                                        }
                                }
                        }
                } catch (Exception ex) {
                        ex.printStackTrace();
                }
        }
        
        public static String getClassPath() {
                return (String)System.getProperty("java.class.path");
        }
        
        
        /**
         * Enumerates the resouces in a give package name.
         * This works even if the resources are loaded from a jar file!
         *
         * Adapted from code by mikewse
         * on the java.sun.com message boards.
         * http://forum.java.sun.com/thread.jsp?forum=22&thread=30984
         *
         * @param packageName The package to enumerate
         * @return A Set of Strings for each resouce in the package.
         */
        public static Set getResoucesInPackage(String packageName) throws IOException {
                String localPackageName;
                if( packageName.endsWith("/") ) {
                        localPackageName = packageName;
                } else {
                        localPackageName = packageName + '/';
                }
                
                Enumeration dirEnum = ClassLoader.getSystemResources( localPackageName );
                
                Set names = new HashSet();
                
                // Loop CLASSPATH directories
                while( dirEnum.hasMoreElements() ) {
                        URL resUrl = (URL) dirEnum.nextElement();
                        
                        // Pointing to filesystem directory
                        if ( resUrl.getProtocol().equals("file") ) {
                                File dir = new File( resUrl.getFile() );
                                File[] files = dir.listFiles();
                                if ( files != null ) {
                                        for( int i=0; i<files.length; i++ ) {
                                                File file = files[i];
                                                if ( file.isDirectory() )
                                                        continue;
                                                names.add( localPackageName + file.getName() );
                                        }
                                }
                                
                                // Pointing to Jar file
                        } else if ( resUrl.getProtocol().equals("jar") ) {
                                JarURLConnection jconn = (JarURLConnection) resUrl.openConnection();
                                JarFile jfile = jconn.getJarFile();
                                Enumeration entryEnum = jfile.entries();
                                while( entryEnum.hasMoreElements() ) {
                                        JarEntry entry = (JarEntry) entryEnum.nextElement();
                                        String entryName = entry.getName();
                                        // Exclude our own directory
                                        if ( entryName.equals(localPackageName) )
                                                continue;
                                        String parentDirName = entryName.substring( 0, entryName.lastIndexOf('/')+1 );
                                        if ( ! parentDirName.equals(localPackageName) )
                                                continue;
                                        names.add( entryName );
                                }
                        } else {
                                // Invalid classpath entry
                        }
                }
                
                return names;
        }
        
        
        /**
         *  Take a filename, strip out the extension and append the new extension
         *  newExtension =   ".xyz"  or "xyz"
         *  If filename is null, ".xyz" is returned
         */
        public static String changeFileExtension(String filename, String newExtension ) {
                if (!newExtension.startsWith(".")) newExtension = "."+newExtension;
                if (filename == null || filename.length()==0 ) {
                        return newExtension;
                }
                
                int index = filename.lastIndexOf(".");
                if (index >= 0) {
                        filename = filename.substring(0,index);
                }
                return filename += newExtension;
        }
        
}//end class Misc
