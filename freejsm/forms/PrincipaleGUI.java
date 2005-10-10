package forms;

/*
 * PrincipaleGUI.java
 *
 * Created on 3 aoet 2002, 20:38
 */

/**
 *
 * @author  Labayle Alexandre
 *Interface principale de l'application diademe
 */
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.net.URL;
import java.io.*;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.HashMap;


import java.*;
import java.sql.*;
import javax.swing.tree.*;
import java.lang.Thread;
import java.util.HashMap;
import java.util.Vector;

import classes.GenFichierSvg;
import classes.Misc;
import classes.Utilisateur;
import classes.UnitedRegistry;
import classes.Update;
import dialogs.DateDialog;
import dialogs.DBWizard;
import dialogs.DiversParams;
import dialogs.Login;
import dialogs.SplashDialog;
import dialogs.OpenCommande;



import com.develog.database.sessionSQL;

public class PrincipaleGUI extends javax.swing.JFrame {
    
    /* Il s'agit d'un numéro de série arbitraire */
    public static String productBuildSerial = "1.1build011204";
    private sessionSQL _session = new sessionSQL();
    public String serverName;
    public String port;
    public String db;
    public Utilisateur userLogged; 
    private String user;
    private String passwd;
    private UnitedRegistry uReg ;
    private Connection connex = null;
    private Apropos aprop = null;
    private PaneAide help = null;
    private HashMap dateHash;
   
    static java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    /** Creation de la fenetre principale
     * Désormé c'est principaleGUI qui appelle la fenetre d'authentification
     * en effet l'inverse est pour le moins déguellasse !*/
        
    public PrincipaleGUI(){

        /* En premier lieu on balance le splashscreen 
           pour le préchargement des classes
        */
        SplashDialog splash = new SplashDialog(this,false);
        splash.show();
        
        
        /* On tente un chargement des paramétres de l'application 
         * si cela échou(premiére install) on lance le DBWizard
         */
          initRegistry();
          try { initValues(); } 
          catch (Exception e){
              System.out.println("[PrincipaleGUI][initValues] Exception :" + e);
              e.printStackTrace();
              DBWizard _wiz = new DBWizard(this,true);
              Misc.centerFrame(_wiz);
              _wiz.show();
          }
          
          
          /* Si c'est tjs pas initialisé alors on claque la fenétre */
          /*try { initValues(); } 
           catch (Exception e){
                this.GestErreur("L'application n'a pas pu s'initialiser");
                this.dispose();
          }*/
          
        /* On initialise la fenetre de login pour obtenir une connection */
        Login _login = new Login(this,true);
        Misc.centerFrame(_login);
        _login.show();
        
        /* On initialise au final l'interface graphique si tout est ok */
        initComponents();
        splash.dispose();
                
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                DialogFermer();
            }
        });
        initStockMenu();
        
        // Les menu se cachent
        
        // d'abord pour les non admin
        if(!userLogged.estAdminQQuePart()){
            stocksItem.setVisible(false);
            paramMenu.setVisible(false);
        }else{
            stocksItem.setVisible(true);
        }
        
        // ensuite pour l'admin
        if(userLogged.getID().compareTo(new Integer(1)) != 0){
            usersItem.setVisible(false);
            fournItem.setVisible(false);
            tvaItem.setVisible(false);
            mainItem.setVisible(false);
            DBWizard.setVisible(false);
            initStockMenu.setVisible(false);
            backupItem.setVisible(false);
            jSeparator9.setVisible(false);
        }else{
            stocksItem.setVisible(true);
            paramMenu.setVisible(true);
        }
                
        saveItem.setVisible(false);
        Banniere.setVisible(false);
        accesRapideItem.setVisible(false);
        Divers.setVisible(false);
        jSeparator7.setVisible(false);
    }
    
    private void initRegistry(){
        uReg = new UnitedRegistry();
        uReg.enableWinRegistry(true);
        uReg.connectToRegistry();
    }
    
     private void initValues() throws Exception{
    
        serverName = uReg.getStringFromRegistry("serverName");
        port = uReg.getStringFromRegistry("port");
        db = uReg.getStringFromRegistry("db");
        user = uReg.getStringFromRegistry("dbuser");
        passwd = uReg.getStringFromRegistry("dbpasswd");
        System.out.println("s:"+serverName+" / p:"+port+" /db:"+db);
    }
    
    public void setUserName(String name){
        user = name;
    }
    
    public void setPassword(String passwd){
        this.passwd=passwd;
    }
    
     public void initConnectionSQL() throws Exception {
         //connex = _session.getSession("com.mysql.jdbc.Driver", serverName, db, user, passwd);   
         
         // on se connecte a la BD, en recuperant les infos du registre
     connex = _session.getSession("org.gjt.mm.mysql.Driver", serverName, db, user, passwd);   
     }
    
    private void initComponents() {//GEN-BEGIN:initComponents
        PopUPTree = new javax.swing.JPopupMenu();
        PCNouveau = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        PCModifier = new javax.swing.JMenuItem();
        PCConsulter = new javax.swing.JMenuItem();
        PCSupprimer = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        PCImprimer = new javax.swing.JMenu();
        jSeparator3 = new javax.swing.JSeparator();
        ConteneurPrincipal = new javax.swing.JPanel();
        principalTab = new com.develog.utils.CloseableTabbedPane(this);
        Banniere = new javax.swing.JToolBar();
        CreerClientRapide = new javax.swing.JButton();
        CreerClientRapide4 = new javax.swing.JButton();
        CreerClientRapide5 = new javax.swing.JButton();
        CreerClientRapide6 = new javax.swing.JButton();
        CreerClientRapide1 = new javax.swing.JButton();
        CreerClientRapide2 = new javax.swing.JButton();
        CreerClientRapide3 = new javax.swing.JButton();
        MenuPrincipal = new javax.swing.JMenuBar();
        fichierMenu = new javax.swing.JMenu();
        backupItem = new javax.swing.JMenu();
        saveItem = new javax.swing.JMenuItem();
        journalItem = new javax.swing.JMenuItem();
        syncItem = new javax.swing.JMenuItem();
        restorItem = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JSeparator();
        PCQuitter = new javax.swing.JMenuItem();
        esMenu = new javax.swing.JMenu();
        commMenu = new javax.swing.JMenu();
        jSeparator5 = new javax.swing.JSeparator();
        entreeMenu = new javax.swing.JMenu();
        sortieMenu = new javax.swing.JMenu();
        jSeparator8 = new javax.swing.JSeparator();
        jesMenu = new javax.swing.JMenu();
        jEntree = new javax.swing.JMenu();
        jSortie = new javax.swing.JMenu();
        suiviMenu = new javax.swing.JMenu();
        etatStocksMenu = new javax.swing.JMenu();
        inventaireMenu = new javax.swing.JMenu();
        jSeparator6 = new javax.swing.JSeparator();
        jepMenu = new javax.swing.JMenu();
        jenMenu = new javax.swing.JMenu();
        stockSaisieMenu = new javax.swing.JMenu();
        commandesMenu = new javax.swing.JMenu();
        articlesMenu = new javax.swing.JMenu();
        paramMenu = new javax.swing.JMenu();
        usersItem = new javax.swing.JMenuItem();
        fournItem = new javax.swing.JMenuItem();
        stocksItem = new javax.swing.JMenuItem();
        tvaItem = new javax.swing.JMenuItem();
        mainItem = new javax.swing.JMenuItem();
        Divers = new javax.swing.JMenuItem();
        DBWizard = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JSeparator();
        initStockMenu = new javax.swing.JMenu();
        jSeparator7 = new javax.swing.JSeparator();
        accesRapideItem = new javax.swing.JCheckBoxMenuItem();
        aideMenu = new javax.swing.JMenu();
        PAAide = new javax.swing.JMenuItem();
        PApropos = new javax.swing.JMenuItem();

        PopUPTree.setLabel("toto");
        PCNouveau.setText("Nouveau");
        PCNouveau.setToolTipText("Permet de cr\u00e9er un nouveau client");
        PCNouveau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PCNouveau(evt);
            }
        });

        PopUPTree.add(PCNouveau);

        PopUPTree.add(jSeparator1);

        PCModifier.setText("Modifier");
        PopUPTree.add(PCModifier);

        PCConsulter.setText("Consulter");
        PCConsulter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PCConsulterActionPerformed(evt);
            }
        });

        PopUPTree.add(PCConsulter);

        PCSupprimer.setText("Supprimer");
        PopUPTree.add(PCSupprimer);

        PopUPTree.add(jSeparator2);

        PCImprimer.setText("Imprimer");
        PopUPTree.add(PCImprimer);

        PopUPTree.add(jSeparator3);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("FreeJSM - Alpha 0.0.1");
        setName("FPrincipaleGUI");
        ConteneurPrincipal.setLayout(new java.awt.BorderLayout());

        principalTab.setBackground(new java.awt.Color(219, 218, 216));
        principalTab.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        principalTab.setOpaque(true);
        principalTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                principalTabMouseClicked(evt);
            }
        });

        ConteneurPrincipal.add(principalTab, java.awt.BorderLayout.CENTER);

        Banniere.setBorder(null);
        Banniere.setBorderPainted(false);
        Banniere.setOpaque(false);
        CreerClientRapide.setBackground(new java.awt.Color(204, 204, 255));
        CreerClientRapide.setFont(new java.awt.Font("Dialog", 0, 12));
        CreerClientRapide.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/gnu/icones/22x22/actions/22x22_User.png")));
        CreerClientRapide.setToolTipText("Cr\u00e9er un nouvel adh\u00e9rent");
        CreerClientRapide.setBorder(new javax.swing.border.CompoundBorder());
        CreerClientRapide.setBorderPainted(false);
        CreerClientRapide.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        CreerClientRapide.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        CreerClientRapide.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        CreerClientRapide.setAutoscrolls(true);
        CreerClientRapide.setOpaque(false);
        CreerClientRapide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreerClientRapideActionPerformed(evt);
            }
        });

        Banniere.add(CreerClientRapide);

        CreerClientRapide4.setBackground(new java.awt.Color(204, 204, 255));
        CreerClientRapide4.setFont(new java.awt.Font("Dialog", 0, 12));
        CreerClientRapide4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/gnu/icones/22x22/apps/yast_partitioner.png")));
        CreerClientRapide4.setToolTipText("Statistiques");
        CreerClientRapide4.setBorder(new javax.swing.border.CompoundBorder());
        CreerClientRapide4.setBorderPainted(false);
        CreerClientRapide4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        CreerClientRapide4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        CreerClientRapide4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        CreerClientRapide4.setAutoscrolls(true);
        CreerClientRapide4.setOpaque(false);
        CreerClientRapide4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreerClientRapide4ActionPerformed(evt);
            }
        });

        Banniere.add(CreerClientRapide4);

        CreerClientRapide5.setBackground(new java.awt.Color(204, 204, 255));
        CreerClientRapide5.setFont(new java.awt.Font("Dialog", 0, 12));
        CreerClientRapide5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/gnu/icones/22x22/apps/yast_ldap_client.png")));
        CreerClientRapide5.setToolTipText("Liste des adh\u00e9rents");
        CreerClientRapide5.setBorder(new javax.swing.border.CompoundBorder());
        CreerClientRapide5.setBorderPainted(false);
        CreerClientRapide5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        CreerClientRapide5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        CreerClientRapide5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        CreerClientRapide5.setAutoscrolls(true);
        CreerClientRapide5.setOpaque(false);
        CreerClientRapide5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreerClientRapide5ActionPerformed(evt);
            }
        });

        Banniere.add(CreerClientRapide5);

        CreerClientRapide6.setBackground(new java.awt.Color(204, 204, 255));
        CreerClientRapide6.setFont(new java.awt.Font("Dialog", 0, 12));
        CreerClientRapide6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/gnu/icones/22x22/apps/kdvi.png")));
        CreerClientRapide6.setToolTipText("Rechercher un adh\u00e9rent");
        CreerClientRapide6.setBorder(new javax.swing.border.CompoundBorder());
        CreerClientRapide6.setBorderPainted(false);
        CreerClientRapide6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        CreerClientRapide6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        CreerClientRapide6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        CreerClientRapide6.setAutoscrolls(true);
        CreerClientRapide6.setOpaque(false);
        Banniere.add(CreerClientRapide6);

        CreerClientRapide1.setFont(new java.awt.Font("Dialog", 0, 12));
        CreerClientRapide1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/gnu/icones/22x22/mimetypes/source_c.png")));
        CreerClientRapide1.setBackground(new java.awt.Color(204, 204, 255));
        CreerClientRapide1.setToolTipText("Saisir nouvelle cotisation");
        CreerClientRapide1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        CreerClientRapide1.setBorderPainted(false);
        CreerClientRapide1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        CreerClientRapide1.setBorder(new javax.swing.border.CompoundBorder());
        CreerClientRapide1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        CreerClientRapide1.setOpaque(false);
        CreerClientRapide1.setAutoscrolls(true);
        CreerClientRapide1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreerClientRapide1ActionPerformed(evt);
            }
        });

        Banniere.add(CreerClientRapide1);

        CreerClientRapide2.setFont(new java.awt.Font("Dialog", 0, 12));
        CreerClientRapide2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/gnu/icones/22x22/mimetypes/source_f.png")));
        CreerClientRapide2.setBackground(new java.awt.Color(204, 204, 255));
        CreerClientRapide2.setToolTipText("Saisie nouveaux frais divers");
        CreerClientRapide2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        CreerClientRapide2.setBorderPainted(false);
        CreerClientRapide2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        CreerClientRapide2.setBorder(new javax.swing.border.CompoundBorder());
        CreerClientRapide2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        CreerClientRapide2.setOpaque(false);
        CreerClientRapide2.setAutoscrolls(true);
        CreerClientRapide2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreerClientRapide2ActionPerformed(evt);
            }
        });

        Banniere.add(CreerClientRapide2);

        CreerClientRapide3.setFont(new java.awt.Font("Dialog", 0, 12));
        CreerClientRapide3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/gnu/icones/22x22/mimetypes/source_p.png")));
        CreerClientRapide3.setBackground(new java.awt.Color(204, 204, 255));
        CreerClientRapide3.setToolTipText("Saisie d'une nouvelle prestation");
        CreerClientRapide3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        CreerClientRapide3.setBorderPainted(false);
        CreerClientRapide3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        CreerClientRapide3.setBorder(new javax.swing.border.CompoundBorder());
        CreerClientRapide3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        CreerClientRapide3.setOpaque(false);
        CreerClientRapide3.setAutoscrolls(true);
        CreerClientRapide3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreerClientRapide3ActionPerformed(evt);
            }
        });

        Banniere.add(CreerClientRapide3);

        ConteneurPrincipal.add(Banniere, java.awt.BorderLayout.NORTH);

        getContentPane().add(ConteneurPrincipal, java.awt.BorderLayout.CENTER);

        MenuPrincipal.setBorder(new javax.swing.border.EtchedBorder());
        fichierMenu.setText("Fichier");
        fichierMenu.setFont(new java.awt.Font("Dialog", 0, 12));
        fichierMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fichierMenuActionPerformed(evt);
            }
        });

        backupItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/gnu/icones/22x22/apps/yast_profile-manager.png")));
        backupItem.setText("Sauvegarde");
        backupItem.setFont(new java.awt.Font("Dialog", 0, 12));
        saveItem.setFont(new java.awt.Font("Dialog", 0, 12));
        saveItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/gnu/icones/22x22/apps/yast_backup.png")));
        saveItem.setText("Sauvegarder");
        saveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clickSurSauvegarde(evt);
            }
        });

        backupItem.add(saveItem);

        journalItem.setText("Generer Journal");
        journalItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                journalItemActionPerformed(evt);
            }
        });

        backupItem.add(journalItem);

        syncItem.setText("Synchroniser les bases");
        syncItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                syncItemActionPerformed(evt);
            }
        });

        backupItem.add(syncItem);

        restorItem.setFont(new java.awt.Font("Dialog", 0, 12));
        restorItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/gnu/icones/22x22/apps/yast_restore.png")));
        restorItem.setText("Synchroniser avec une nouvelle base");
        restorItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restorItemActionPerformed(evt);
            }
        });

        backupItem.add(restorItem);

        fichierMenu.add(backupItem);

        fichierMenu.add(jSeparator4);

        PCQuitter.setFont(new java.awt.Font("Arial", 0, 12));
        PCQuitter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/gnu/icones/22x22/actions/exit.png")));
        PCQuitter.setText("Quitter");
        PCQuitter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PCQuitterActionPerformed(evt);
            }
        });

        fichierMenu.add(PCQuitter);

        MenuPrincipal.add(fichierMenu);

        esMenu.setText("Entr\u00e9es / Sorties");
        esMenu.setFont(new java.awt.Font("Dialog", 0, 12));
        commMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/develog/icones/kate.png")));
        commMenu.setText("Commande");
        commMenu.setFont(new java.awt.Font("Dialog", 0, 12));
        esMenu.add(commMenu);

        esMenu.add(jSeparator5);

        entreeMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/develog/icones/kr_arc_pack.png")));
        entreeMenu.setText("Entr\u00e9e");
        entreeMenu.setFont(new java.awt.Font("Dialog", 0, 12));
        entreeMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entreeMenuActionPerformed(evt);
            }
        });

        esMenu.add(entreeMenu);

        sortieMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/develog/icones/kr_arc_unpack.png")));
        sortieMenu.setText("Sortie");
        sortieMenu.setFont(new java.awt.Font("Dialog", 0, 12));
        esMenu.add(sortieMenu);

        esMenu.add(jSeparator8);

        jesMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/develog/icones/gnome-news.png")));
        jesMenu.setText("Journaux des E/S");
        jesMenu.setFont(new java.awt.Font("Dialog", 0, 12));
        jEntree.setText("Entr\u00e9es");
        jEntree.setFont(new java.awt.Font("Dialog", 0, 12));
        jesMenu.add(jEntree);

        jSortie.setText("Sorties");
        jSortie.setFont(new java.awt.Font("Dialog", 0, 12));
        jesMenu.add(jSortie);

        esMenu.add(jesMenu);

        MenuPrincipal.add(esMenu);

        suiviMenu.setText("Suivi");
        suiviMenu.setFont(new java.awt.Font("Dialog", 0, 12));
        etatStocksMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/develog/icones/ark_addfile.png")));
        etatStocksMenu.setText("Etat des stocks");
        etatStocksMenu.setFont(new java.awt.Font("Dialog", 0, 12));
        suiviMenu.add(etatStocksMenu);

        inventaireMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/develog/icones/stardict.png")));
        inventaireMenu.setText("Inventaires");
        inventaireMenu.setFont(new java.awt.Font("Dialog", 0, 12));
        suiviMenu.add(inventaireMenu);

        suiviMenu.add(jSeparator6);

        jepMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/develog/icones/package_settings.png")));
        jepMenu.setText("Journal des \u00e9carts positifs");
        jepMenu.setFont(new java.awt.Font("Dialog", 0, 12));
        jepMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jepMenuActionPerformed(evt);
            }
        });

        suiviMenu.add(jepMenu);

        jenMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/develog/icones/package_settings.png")));
        jenMenu.setText("Journal des \u00e9carts n\u00e9gatifs");
        jenMenu.setFont(new java.awt.Font("Dialog", 0, 12));
        suiviMenu.add(jenMenu);

        stockSaisieMenu.setText("Saisie des Stocks");
        suiviMenu.add(stockSaisieMenu);

        commandesMenu.setText("Commandes");
        commandesMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commandesMenuActionPerformed(evt);
            }
        });

        suiviMenu.add(commandesMenu);

        articlesMenu.setText("Edition des Produits");
        suiviMenu.add(articlesMenu);

        MenuPrincipal.add(suiviMenu);

        paramMenu.setText("Param\u00e8tres");
        paramMenu.setFont(new java.awt.Font("Arial", 0, 12));
        paramMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paramMenuActionPerformed(evt);
            }
        });

        usersItem.setFont(new java.awt.Font("Dialog", 0, 12));
        usersItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/develog/icones/gaim.png")));
        usersItem.setText("Utilisateurs");
        usersItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usersItemActionPerformed(evt);
            }
        });

        paramMenu.add(usersItem);

        fournItem.setFont(new java.awt.Font("Dialog", 0, 12));
        fournItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/develog/icones/fileshare.png")));
        fournItem.setText("Fournisseurs");
        fournItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fournItemActionPerformed(evt);
            }
        });

        paramMenu.add(fournItem);

        stocksItem.setFont(new java.awt.Font("Dialog", 0, 12));
        stocksItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/develog/icones/kfm.png")));
        stocksItem.setText("Stocks");
        stocksItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stocksItemActionPerformed(evt);
            }
        });

        paramMenu.add(stocksItem);

        tvaItem.setFont(new java.awt.Font("Dialog", 0, 12));
        tvaItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/develog/icones/gnome-finance.png")));
        tvaItem.setText("TVA");
        tvaItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tvaItemActionPerformed(evt);
            }
        });

        paramMenu.add(tvaItem);

        mainItem.setFont(new java.awt.Font("Dialog", 0, 12));
        mainItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/develog/icones/gnome-settings.png")));
        mainItem.setText("G\u00e9n\u00e9raux");
        mainItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainItemActionPerformed(evt);
            }
        });

        paramMenu.add(mainItem);

        Divers.setFont(new java.awt.Font("Dialog", 0, 12));
        Divers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/gnu/icones/22x22/apps/kservices.png")));
        Divers.setText("Divers...");
        Divers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DiversActionPerformed(evt);
            }
        });

        paramMenu.add(Divers);

        DBWizard.setFont(new java.awt.Font("Dialog", 0, 12));
        DBWizard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/gnu/icones/22x22/actions/server.png")));
        DBWizard.setText("Base de donn\u00e9e");
        DBWizard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DBWizardActionPerformed(evt);
            }
        });

        paramMenu.add(DBWizard);

        paramMenu.add(jSeparator9);

        initStockMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/gnu/icones/22x22/actions/server.png")));
        initStockMenu.setText("Initialisation du stock");
        initStockMenu.setFont(new java.awt.Font("Dialog", 0, 12));
        initStockMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                initStockMenuActionPerformed(evt);
            }
        });

        paramMenu.add(initStockMenu);

        paramMenu.add(jSeparator7);

        accesRapideItem.setFont(new java.awt.Font("Dialog", 0, 12));
        accesRapideItem.setSelected(true);
        accesRapideItem.setText("Acc\u00e9s rapide");
        accesRapideItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/gnu/icones/22x22/apps/looknfeel.png")));
        accesRapideItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClickMenuBanniere(evt);
            }
        });

        paramMenu.add(accesRapideItem);

        MenuPrincipal.add(paramMenu);

        aideMenu.setText("Aide");
        aideMenu.setFont(new java.awt.Font("Arial", 0, 12));
        aideMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aideMenuActionPerformed(evt);
            }
        });

        PAAide.setFont(new java.awt.Font("Dialog", 0, 12));
        PAAide.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/gnu/icones/22x22/apps/khelpcenter.png")));
        PAAide.setText("Aide");
        PAAide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PAAideActionPerformed(evt);
            }
        });

        aideMenu.add(PAAide);

        PApropos.setFont(new java.awt.Font("Dialog", 0, 12));
        PApropos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/gnu/icones/22x22/apps/babelfish.png")));
        PApropos.setText("A propos");
        PApropos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PApropos(evt);
            }
        });

        aideMenu.add(PApropos);

        MenuPrincipal.add(aideMenu);

        setJMenuBar(MenuPrincipal);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-714)/2, (screenSize.height-561)/2, 714, 561);
    }//GEN-END:initComponents

    private void commandesMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commandesMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_commandesMenuActionPerformed

    private void initStockMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_initStockMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_initStockMenuActionPerformed

    private void principalTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_principalTabMouseClicked

    }//GEN-LAST:event_principalTabMouseClicked

    private void entreeMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entreeMenuActionPerformed
    }//GEN-LAST:event_entreeMenuActionPerformed

    private void jepMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jepMenuActionPerformed
    }//GEN-LAST:event_jepMenuActionPerformed

    private void restorItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restorItemActionPerformed
            try{
                if(JOptionPane.showConfirmDialog(this,"Etes vous sur de vouloir le faire ?","Mise à jour de la base",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION){
                    ResultSet rset = connex.prepareStatement("SHOW TABLES").executeQuery();
                    while(rset.next()){
                        connex.prepareStatement("DROP TABLE " + rset.getString(1)).executeUpdate();
                    }
                    (new classes.CommitSVGFile(connex, this, true)).show();
                }
            }catch(Exception e){
                System.out.println("[PrincipaleGUI][restorItemActionPerformed] Exception : " + e);
                e.printStackTrace();
            }
    }//GEN-LAST:event_restorItemActionPerformed

    private void syncItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_syncItemActionPerformed
        try{
            // new classes.GenFichierCSV(connex, this, true);
            new classes.CSVFileToDB(connex, this,true);
            
            java.util.Vector niveaux = new java.util.Vector();
            
            java.util.Vector niveau1 = new java.util.Vector();
            java.util.Vector niveau2 = new java.util.Vector();
            java.util.Vector niveau3 = new java.util.Vector();
            java.util.Hashtable champs = new java.util.Hashtable();
            
            niveau1.add(new String("UTILISATEUR"));
            niveau1.add(new String("TVA"));
            niveau1.add(new String("CATEGORIE"));
            niveau1.add(new String("FOURNISSEUR"));
            niveau1.add(new String("STOCK"));
            niveau1.add(new String("PARAMETRE"));
            
            niveau2.add(new String("ARTICLE"));
            niveau2.add(new String("COMMANDE"));
            niveau2.add(new String("FONCTION"));
            niveau2.add(new String("ENTREE"));
            niveau2.add(new String("SORTIE"));
            
            niveau3.add(new String("CONTENU_COMMANDE"));
            niveau3.add(new String("CONTENU_STOCK"));
            niveau3.add(new String("CONTENU_ENTREE"));
            niveau3.add(new String("CONTENU_SORTIE"));
            niveau3.add(new String("CONTENU_COMMANDE"));
            
            niveaux.add(niveau1);
            niveaux.add(niveau2);
            niveaux.add(niveau3);
           
            champs.put(new String("TVA_TX"), new String("Taux de TVA"));
            champs.put(new String("TVA_ID"), new String("ID TVA"));
            champs.put(new String("TVA_ACTIV"), new String("TVA Active"));
            champs.put(new String("TVA_DESIGN"), new String("Désignation"));
            
            champs.put(new String("CA_ID"), new String("ID Catégorie"));
            champs.put(new String("CA_PARENT"), new String("Catégorie parent"));
            champs.put(new String("CA_NOM"), new String("Désignation"));
            champs.put(new String("CA_VISIBLE"), new String("Catégorie Active"));
            
            champs.put(new String("FO_ID"), new String("ID Fournisseur"));
            champs.put(new String("FO_NOM"), new String("Nom"));
            champs.put(new String("FO_VILLE"), new String("Ville"));
            champs.put(new String("FO_CP"), new String("Code Postal"));
            champs.put(new String("FO_TEL"), new String("Téléphone"));
            champs.put(new String("FO_MAIL"), new String("E-Mail"));
            champs.put(new String("FO_FAX"), new String("Fax"));
            champs.put(new String("FO_ADRESSE"), new String("Adresse"));
            champs.put(new String("FO_ACTIV"), new String("Fournisseur Actif"));
            
            
            champs.put(new String("ST_ID"), new String("ID Stock"));
            champs.put(new String("ST_NOM"), new String("Nom"));
            champs.put(new String("ST_VILLE"), new String("Ville"));
            champs.put(new String("ST_CP"), new String("Code Postal"));
            champs.put(new String("ST_TEL"), new String("Téléphone"));
            champs.put(new String("ST_MAIL"), new String("E-Mail"));
            champs.put(new String("ST_FAX"), new String("Fax"));
            champs.put(new String("ST_ADRESSE"), new String("Adresse"));
            champs.put(new String("ST_ACTIV"), new String("Stock Actif"));
                        
            champs.put(new String("PM_CLEF"), new String("Clef"));
            champs.put(new String("PM_VALEUR"), new String("Valeur"));

            champs.put(new String("UTIL_ID"), new String("ID Utilisateur"));
            champs.put(new String("UTIL_NOM"), new String("Nom"));
            champs.put(new String("UTIL_PRENOM"), new String("Prénom"));
            champs.put(new String("UTIL_TELEPHONE"), new String("Téléphone"));
            champs.put(new String("UTIL_MAIL"), new String("E-Mail"));
            champs.put(new String("UTIL_LOGIN"), new String("Login"));
            champs.put(new String("UTIL_ACTIV"), new String("Activé"));
            champs.put(new String("UTIL_PASSWORD"), new String("Mot de Passe"));
            
            champs.put(new String("AR_ID"), new String("ID Article"));
            champs.put(new String("AR_CODE"), new String("Code"));
            champs.put(new String("AR_DESIGN"), new String("Désignation"));
            champs.put(new String("AR_UNIT"), new String("Unité de mesure"));
            champs.put(new String("AR_COND"), new String("Conditionnement"));
            champs.put(new String("AR_ACTIV"), new String("Article Actif"));
            
            champs.put(new String("CO_ID"), new String("ID Commande"));
            champs.put(new String("CO_DATE"), new String("Date de commande"));
                        
            champs.put(new String("FO_LEVEL"), new String("Droits"));
            
            champs.put(new String("ENT_ID"), new String("ID Entrée"));
            champs.put(new String("ENT_DATE"), new String("Date d'entrée"));
            
            champs.put(new String("SORT_ID"), new String("ID Sortie"));
            champs.put(new String("SORT_DATE"), new String("Date de sortie"));
            
            champs.put(new String("CS_ID"), new String("ID Contenu Stock"));
            champs.put(new String("CS_QT"), new String("Quantité"));
            champs.put(new String("CS_SEUIL"), new String("Seuil de réaprovisionement"));
            champs.put(new String("CS_PRIXUNITAIRE"), new String("Prix Unitaire"));
            
            champs.put(new String("CC_ID"), new String("ID Contenu Commande"));
            champs.put(new String("CC_QT"), new String("Quantité"));
            
            champs.put(new String("CE_ID"), new String("ID Contenu Entée"));
            champs.put(new String("CS_ID"), new String("ID Contenu Sortie"));
            Vector parentsID = new Vector();
            parentsID.add("FONCTION");
            (new dialogs.Synchronize(this,true,connex ,niveaux, champs, parentsID)).show();
            (new classes.GenFichierSvg(connex, this, true)).show();
/*            try{
                ResultSet rset = connex.prepareStatement("SHOW TABLES").executeQuery();
                while(rset.next()){
                    connex.prepareStatement("DROP TABLE " + rset.getString(1)).executeUpdate();
                }
            }catch(Exception e){
                System.out.println(e);
            }
            
            (new classes.CommitSVGFile(connex, this, true)).show();*/
            
                        
        }catch(Exception e){
            System.out.println("[PrincipaleGUI][syncItemActionPerformed] Exception : " +e );
            e.printStackTrace();
        }
    }//GEN-LAST:event_syncItemActionPerformed

    private void journalItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_journalItemActionPerformed
        try{
            new classes.GenFichierCSV(connex, this, true);
            
/*            java.util.Vector test = new java.util.Vector();
            java.util.Vector test3 = new java.util.Vector();
            java.util.Vector test2 = new java.util.Vector();
            
            test.add(new String("UTILISATEUR"));
            test.add(new String("TVA"));
            test.add(new String("STOCK"));
            test3.add(new String("FONCTION"));
            
            test2.add(test);
            test2.add(test3);
            
            java.util.Hashtable blabla = new java.util.Hashtable();
            blabla.put(new String("TVA_TX"), new String("TAUX DE TVA"));
            
            (new dialogs.Synchronize(this,true,connex, test2, blabla)).show();*/
            
            
            
        }catch(Exception e){
            System.out.println("[PrincipaleGUI][jMenuItem1ActionPerformed] Exception : " + e); 
            e.printStackTrace();
        }
    }//GEN-LAST:event_journalItemActionPerformed

    private void CreerClientRapideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreerClientRapideActionPerformed
        
    }//GEN-LAST:event_CreerClientRapideActionPerformed

    private void CreerClientRapide4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreerClientRapide4ActionPerformed
        
    }//GEN-LAST:event_CreerClientRapide4ActionPerformed

    private void CreerClientRapide5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreerClientRapide5ActionPerformed
        
    }//GEN-LAST:event_CreerClientRapide5ActionPerformed

    private void CreerClientRapide1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreerClientRapide1ActionPerformed
        
    }//GEN-LAST:event_CreerClientRapide1ActionPerformed

    private void CreerClientRapide2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreerClientRapide2ActionPerformed
        
    }//GEN-LAST:event_CreerClientRapide2ActionPerformed

    private void CreerClientRapide3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreerClientRapide3ActionPerformed
        
    }//GEN-LAST:event_CreerClientRapide3ActionPerformed

    private void paramMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paramMenuActionPerformed
    }//GEN-LAST:event_paramMenuActionPerformed

    private void mainItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainItemActionPerformed
        PaneMain general = new PaneMain(connex,this);
        principalTab.add("Paramètres généraux",general);
        principalTab.setSelectedComponent(general);
    }//GEN-LAST:event_mainItemActionPerformed

    private void stocksItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stocksItemActionPerformed
        PaneStock stock = new PaneStock(connex, this, userLogged);
        principalTab.add("Stocks",stock);
        principalTab.setSelectedComponent(stock);
    }//GEN-LAST:event_stocksItemActionPerformed

    private void tvaItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tvaItemActionPerformed
        PaneTVA tva = new PaneTVA(connex, this);
        principalTab.add("Taux de TVA",tva);
        principalTab.setSelectedComponent(tva);
    }//GEN-LAST:event_tvaItemActionPerformed

    private void fournItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fournItemActionPerformed
        PaneFourn fournisseurs = new PaneFourn(connex, this);
        principalTab.add("Fournisseurs",fournisseurs);
        principalTab.setSelectedComponent(fournisseurs);
    }//GEN-LAST:event_fournItemActionPerformed

    private void usersItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usersItemActionPerformed
        PaneUser users = new PaneUser(connex,this);
        principalTab.add("Utilisateurs",users);
        principalTab.setSelectedComponent(users);
    }//GEN-LAST:event_usersItemActionPerformed

    private void DBWizardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DBWizardActionPerformed
        new DBWizard(this,true).show();
    }//GEN-LAST:event_DBWizardActionPerformed

    private void clickSurSauvegarde(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clickSurSauvegarde
        try{
            new GenFichierSvg(connex, this, true);
        }catch(Exception e){
            GestErreur("Erreur lors de la génération du fichier de sauvegarde : "+e);
            e.printStackTrace();
        }
    }//GEN-LAST:event_clickSurSauvegarde


    private void PAAideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PAAideActionPerformed
        help = new PaneAide(this,true);
        help.setLocation((screenSize.width-(help.getSize().width))/2,(screenSize.height-(help.getSize().height))/2);
        help.show();
    }//GEN-LAST:event_PAAideActionPerformed

    private void fichierMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fichierMenuActionPerformed
    }//GEN-LAST:event_fichierMenuActionPerformed


    private void DiversActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DiversActionPerformed
        // On ouvre une pop-up de parametrage
        DiversParams dp = new DiversParams(this,connex,true);
        dp.show();
    }//GEN-LAST:event_DiversActionPerformed

            
    private void ClickMenuBanniere(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClickMenuBanniere
        // Permet d'activer ou non la banniere ecran de selection
        if(!accesRapideItem.getState()){
            Banniere.setVisible(false);}
        else{Banniere.setVisible(true);}
    }//GEN-LAST:event_ClickMenuBanniere
                    
    private void PApropos(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PApropos
        // Affiche des infos e la con concernant le logiciel
        aprop = new Apropos(this, true,productBuildSerial);
        aprop.show();
    }//GEN-LAST:event_PApropos
    
    private void PCQuitterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PCQuitterActionPerformed
        DialogFermer();
    }//GEN-LAST:event_PCQuitterActionPerformed
    
    private void PCConsulterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PCConsulterActionPerformed
    }//GEN-LAST:event_PCConsulterActionPerformed
    
    private void PCNouveau(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PCNouveau
    }//GEN-LAST:event_PCNouveau
    
    private void aideMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aideMenuActionPerformed
    }//GEN-LAST:event_aideMenuActionPerformed

    
    /** Exit the Application */    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToolBar Banniere;
    private javax.swing.JPanel ConteneurPrincipal;
    private javax.swing.JButton CreerClientRapide;
    private javax.swing.JButton CreerClientRapide1;
    private javax.swing.JButton CreerClientRapide2;
    private javax.swing.JButton CreerClientRapide3;
    private javax.swing.JButton CreerClientRapide4;
    private javax.swing.JButton CreerClientRapide5;
    private javax.swing.JButton CreerClientRapide6;
    private javax.swing.JMenuItem DBWizard;
    private javax.swing.JMenuItem Divers;
    private javax.swing.JMenuBar MenuPrincipal;
    private javax.swing.JMenuItem PAAide;
    private javax.swing.JMenuItem PApropos;
    private javax.swing.JMenuItem PCConsulter;
    private javax.swing.JMenu PCImprimer;
    private javax.swing.JMenuItem PCModifier;
    private javax.swing.JMenuItem PCNouveau;
    private javax.swing.JMenuItem PCQuitter;
    private javax.swing.JMenuItem PCSupprimer;
    private javax.swing.JPopupMenu PopUPTree;
    private javax.swing.JCheckBoxMenuItem accesRapideItem;
    private javax.swing.JMenu aideMenu;
    private javax.swing.JMenu articlesMenu;
    private javax.swing.JMenu backupItem;
    private javax.swing.JMenu commMenu;
    private javax.swing.JMenu commandesMenu;
    private javax.swing.JMenu entreeMenu;
    private javax.swing.JMenu esMenu;
    private javax.swing.JMenu etatStocksMenu;
    private javax.swing.JMenu fichierMenu;
    private javax.swing.JMenuItem fournItem;
    private javax.swing.JMenu initStockMenu;
    private javax.swing.JMenu inventaireMenu;
    private javax.swing.JMenu jEntree;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JMenu jSortie;
    private javax.swing.JMenu jenMenu;
    private javax.swing.JMenu jepMenu;
    private javax.swing.JMenu jesMenu;
    private javax.swing.JMenuItem journalItem;
    private javax.swing.JMenuItem mainItem;
    private javax.swing.JMenu paramMenu;
    public javax.swing.JTabbedPane principalTab;
    private javax.swing.JMenuItem restorItem;
    private javax.swing.JMenuItem saveItem;
    private javax.swing.JMenu sortieMenu;
    private javax.swing.JMenu stockSaisieMenu;
    private javax.swing.JMenuItem stocksItem;
    private javax.swing.JMenu suiviMenu;
    private javax.swing.JMenuItem syncItem;
    private javax.swing.JMenuItem tvaItem;
    private javax.swing.JMenuItem usersItem;
    // End of variables declaration//GEN-END:variables
    private javax.swing.JPanel synthese = new JPanel(); 
    private javax.swing.JButton jButton2 = new javax.swing.JButton();
     
    public void TuerFils(JPanel Obj){
        principalTab.remove(Obj);
    }
    
    
    /* ----------------Affiche une boite de dialogue pour la fermeture de la fenetre ---------------------*/
    public void DialogFermer(){
        if ( JOptionPane.showConfirmDialog(this,"Voulez-vous vraiment \n" + "sortir?","Fermeture",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null) == 0)
        {
            System.exit(0);
        }
    }
      
    public void GestInfo(String e){
        if ( JOptionPane.showConfirmDialog(this,e,"Info",
        JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null) == 0){}
    }
    
    public void GestErreur(String e){
        if ( JOptionPane.showConfirmDialog(this,e,"ERREUR",
        JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null) == 0){}
    }
    
    public boolean TestUser(String login, String password){
        try{
            ResultSet rset = connex.prepareStatement("SELECT * from UTILISATEUR, FONCTION where UTILISATEUR.UTIL_ID = FONCTION.UTIL_ID and UTIL_LOGIN = '" + login + "' and UTIL_PASSWORD = '" + password + "'" ).executeQuery();
            ResultSet rset2 = connex.prepareStatement("SELECT UTIL_ID FROM UTILISATEUR where UTIL_LOGIN = '" + login + "' and UTIL_PASSWORD = '" + password + "'" ).executeQuery();
            if(rset2.next()){
                if(rset.next() || rset2.getInt("UTIL_ID") == 1){
                    return true;
                }
            }else
                return false;
            /*rset = connex.prepareStatement("SELECT UTIL_ID FROM UTILISATEUR where UTIL_LOGIN = '" + login + "' and UTIL_PASSWORD = '" + password + "'" ).executeQuery();
            rset.next();
            if(rset.getInt("UTIL_ID") == 0 )
                return true;*/
            
            return false;

            
        }catch(Exception e){
            System.out.println("[PrincipaleGUI][TestUser] Exception : " +e );
            e.printStackTrace();
        }
        return false;
       
    }
    
    public void Login(String login, String password){
        userLogged = new Utilisateur(login, password, connex);
    }
    
    private void initStockMenu(){
        try{
            ResultSet rset = connex.prepareStatement("SELECT * from FONCTION, STOCK where STOCK.ST_ID = FONCTION.ST_ID and UTIL_ID = " + userLogged.getID()).executeQuery();
            ResultSet rset2 = connex.prepareStatement("SELECT ST_ID, ST_NOM from STOCK").executeQuery();
            
            while(rset2.next()){
                final int st_idInit = rset2.getInt("ST_ID");
                final String st_nomInit = rset2.getString("ST_NOM");
                // Initialisation du Stock
                javax.swing.JMenuItem initST = new javax.swing.JMenuItem();
                initST.setFont(new java.awt.Font("Dialog", 0, 12));
                initST.setText(rset2.getString("ST_NOM"));
                initST.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        // A CHANGER LE "TEST" qui est le nom du fichier model
                        ouvrirPaneProduit(st_idInit, st_nomInit);
                    }
                });
                initStockMenu.add(initST);
                
            }
            while(rset.next()){
                
                // On stocke les 2 variables dans des final pour pouvoir les passer aux action listener
                final int st_id = rset.getInt("ST_ID");
                
                final String st_nom = rset.getString("ST_NOM");
                
                
                // On créé les 2 objets qui vont aller dans les menus
                // Inventaire
                javax.swing.JMenu inventItem = new javax.swing.JMenu();
                // Etat des Stocks
                javax.swing.JMenuItem stockItem = new javax.swing.JMenuItem();
                // Journal des ecarts positifs                
                javax.swing.JMenuItem jepItem = new javax.swing.JMenuItem();
                // Journal des ecarts negatifs                
                javax.swing.JMenuItem jenItem = new javax.swing.JMenuItem();
                // Journal des E/S
                javax.swing.JMenuItem jEntreeItem = new javax.swing.JMenuItem();
                
                javax.swing.JMenuItem jSortieItem = new javax.swing.JMenuItem();
                
                javax.swing.JMenuItem commItem = new javax.swing.JMenuItem();
                                
                javax.swing.JMenuItem entreeItem = new javax.swing.JMenuItem();
                
                javax.swing.JMenuItem sortieItem = new javax.swing.JMenuItem();
                
                javax.swing.JMenuItem saisieStockItem = new javax.swing.JMenuItem();
                
                javax.swing.JMenuItem openCommandeStock = new javax.swing.JMenuItem();
                
                javax.swing.JMenuItem articlesItem = new javax.swing.JMenuItem();
                
                // On leur attribue des proprietés pour le design
                articlesItem.setFont(new java.awt.Font("Dialog", 0, 12));
                stockItem.setFont(new java.awt.Font("Dialog", 0, 12));
                inventItem.setFont(new java.awt.Font("Dialog", 0, 12));
                commItem.setFont(new java.awt.Font("Dialog", 0, 12));
                jepItem.setFont(new java.awt.Font("Dialog", 0, 12));
                jenItem.setFont(new java.awt.Font("Dialog", 0, 12));
                jEntreeItem.setFont(new java.awt.Font("Dialog", 0, 12));
                jSortieItem.setFont(new java.awt.Font("Dialog", 0, 12));
                entreeItem.setFont(new java.awt.Font("Dialog", 0, 12));
                sortieItem.setFont(new java.awt.Font("Dialog", 0, 12));
                saisieStockItem.setFont(new java.awt.Font("Dialog", 0, 12));
                openCommandeStock.setFont(new java.awt.Font("Dialog", 0, 12));
                articlesItem.setText(rset.getString("ST_NOM"));
                stockItem.setText(rset.getString("ST_NOM"));
                commItem.setText(rset.getString("ST_NOM"));
                inventItem.setText(rset.getString("ST_NOM"));
                jepItem.setText(rset.getString("ST_NOM"));
                jenItem.setText(rset.getString("ST_NOM"));
                jEntreeItem.setText(rset.getString("ST_NOM"));
                jSortieItem.setText(rset.getString("ST_NOM"));
                entreeItem.setText(rset.getString("ST_NOM"));
                sortieItem.setText(rset.getString("ST_NOM"));
                saisieStockItem.setText(rset.getString("ST_NOM"));
                openCommandeStock.setText(rset.getString("ST_NOM"));
                
                // On met des actions listener pour lancer new Rapport Pane qui permet d'ouvrir une tab avec le rapport.
                stockItem.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                    // A CHANGER LE "TEST" qui est le nom du fichier model
                        newStockRapport(st_id, st_nom);
                    }
                });

                jepItem.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                    newSelectJEPNDate("JEP", new Integer(st_id));
                    }
                });
                jenItem.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                    newSelectJEPNDate("JEN", new Integer(st_id));
                    }
                });
                jEntreeItem.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                    // A CHANGER LE "TEST" qui est le nom du fichier model
                    newDateDialog("entree", st_id, st_nom);
                    }
                });
                jSortieItem.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                    // A CHANGER LE "TEST" qui est le nom du fichier model
                    newDateDialog("sortie", st_id, st_nom);
                    }
                });
                commItem.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                    // A CHANGER LE "TEST" qui est le nom du fichier model
                    newPaneCommande(connex,st_nom,st_id,userLogged);
                    }
                });
                entreeItem.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                    // A CHANGER LE "TEST" qui est le nom du fichier model
                    newPaneEntree(connex,st_nom,st_id,userLogged);
                    }
                });
                sortieItem.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                    // A CHANGER LE "TEST" qui est le nom du fichier model
                    newPaneSortie(connex,st_nom,st_id,userLogged);
                    }
                });
                saisieStockItem.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                    newStockSaisiePane(st_id,st_nom);
                    }
                });
                openCommandeStock.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                    newStockOpenCommande(connex,st_id, st_nom);
                    }
                });
                articlesItem.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                    newArticleItem(st_id,st_nom);
                    }
                });
                
                inventItem = initInventMenu(st_id, inventItem);
                // On les rajoute au menu
		inventaireMenu.add(inventItem);
                etatStocksMenu.add(stockItem);
                jepMenu.add(jepItem);
                jenMenu.add(jenItem);
                jEntree.add(jEntreeItem);
                jSortie.add(jSortieItem);
                commMenu.add(commItem);
                entreeMenu.add(entreeItem);
                sortieMenu.add(sortieItem);
                stockSaisieMenu.add(saisieStockItem);
                commandesMenu.add(openCommandeStock);
                articlesMenu.add(articlesItem);
                
            }
        }catch(Exception e){
            System.out.println("[PrincipaleGUI][initStockMenu] Exception : " + e);
            e.printStackTrace();
        }
    }
    
    private String listeCategorie(int CA_ID){
        String retour = new String();
        try{
            ResultSet rset = connex.prepareStatement("SELECT * FROM CATEGORIE WHERE CA_VISIBLE=1 AND CA_PARENT="+CA_ID).executeQuery();
            while(rset.next()){
                System.out.println(rset.getInt("CA_ID") );
                retour += "OR CATEGORIE.CA_ID=" + rset.getInt("CA_ID") + " ";
                retour += listeCategorie(rset.getInt("CA_ID"))  ;
            }
        }catch(Exception e){
            System.out.println("[PrincipaleGUI][listeCategorie] Exception : "+e);
            e.printStackTrace();
        }
        return retour;
    }
    
    private JMenu initInventMenu(int st_id, JMenu catMenu){
        try{
            ResultSet rset = connex.prepareStatement("SELECT * FROM CATEGORIE WHERE CA_PARENT=0").executeQuery();
            while(rset.next()){
                final int st_id2 = st_id;
                final String st_nom = rset.getString("CA_NOM");
                String listeCat = listeCategorie(rset.getInt("CA_ID"));
                listeCat = listeCat.substring(3);
                System.out.println("listecat : " + listeCat);
                javax.swing.JMenuItem catItem = new javax.swing.JMenuItem();
                catItem.setFont(new java.awt.Font("Dialog", 0, 12));
                catItem.setText(rset.getString("CA_NOM"));
                final String listeCat2 = listeCat;
                final String CA_NOM = rset.getString("CA_NOM");
                catItem.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            newInventaireRapport(st_id2, st_nom, listeCat2, CA_NOM);
                        }
                });
                catMenu.add(catItem);
            }
        }catch(SQLException e){
            System.out.println("[PrincipaleGUI][initInventMenu] Exception : " + e);
            e.printStackTrace();
        }
        
        return catMenu;
    }
    
    private void newPaneEntree(Connection connex, String st_nom, int st_id, Utilisateur userLogged){
        PaneEntree entree = new PaneEntree(connex, st_id, userLogged, this);
        principalTab.add("Entrée pour " + st_nom,entree);
        principalTab.setSelectedComponent(entree);
    }
    
    private void newStockOpenCommande(Connection connex, int st_id, String st_nom){
        (new OpenCommande(this,true, connex, st_id)).show();
    }
    
    private void newPaneSortie(Connection connex, String st_nom, int st_id, Utilisateur userLogged){
        PaneSortie sortie = new PaneSortie(connex, st_id, userLogged, this);
        principalTab.add("Sortie pour " + st_nom,sortie);
        principalTab.setSelectedComponent(sortie);
    }
    
    private void newPaneCommande( Connection connex, String st_nom, int st_id, Utilisateur userLogged){
        PaneCommande commande = new PaneCommande(connex,st_id,userLogged,this);
        principalTab.add("Commande pour " + st_nom,commande);
        principalTab.setSelectedComponent(commande);
    }
    
    private void ouvrirPaneProduit(int st_id, String st_nom){
        PaneProduits initStock = new PaneProduits(connex,this, st_id, userLogged.getID().intValue());
        principalTab.add("Initialisation du stock " + st_nom,initStock);
        principalTab.setSelectedComponent(initStock);
    }
    
    private void newArticleItem(int st_id, String st_nom){
        PaneProduitsUser panuser = new PaneProduitsUser(connex, this, st_id, userLogged.getID().intValue());
        principalTab.add("Produits du stock " + st_nom ,panuser);
        principalTab.setSelectedComponent(panuser);
    }
    
    private void newRapportPane(int st_id, String st_nom, String listing_name){
         
        try{
            HashMap param = new HashMap();
            param.put("ST_ID", new Integer(st_id));
            param.put("ST_NOM", new String(st_nom));
            PaneRapport rapport = new PaneRapport(listing_name,param,connex);
            principalTab.add("Etat des stocks",rapport);
            principalTab.setSelectedComponent(rapport);
        
        }catch(Exception e){
        
            System.out.println("[PrincipaleGUI][newRapportPane] Exception :"+e);
            e.printStackTrace();
        
        }
    }
    
    private void newSortieRapport(int st_id, String st_nom, HashMap param){
         
        try{
            param.put("ST_ID", new Integer(st_id));
            param.put("ST_NOM", new String(st_nom));
            PaneRapport rapport = new PaneRapport("sortie",param,connex);
            principalTab.add("Sorties pour "+ st_nom,rapport);
            principalTab.setSelectedComponent(rapport);
        
        }catch(Exception e){
        
            System.out.println("[PrincipaleGUI][newSortieRapport] Exception :"+e);
            e.printStackTrace();
        
        }
    }
     
    private void newEntreeRapport(int st_id, String st_nom, HashMap param){
         
        try{
            param.put("ST_ID", new Integer(st_id));
            param.put("ST_NOM", new String(st_nom));
            PaneRapport rapport = new PaneRapport("entree",param,connex);
            principalTab.add("Entrées pour " + st_nom,rapport);
            principalTab.setSelectedComponent(rapport);
        
        }catch(Exception e){
        
            System.out.println("[PrincipaleGUI][newEntreeRapport] Exception :"+e);
            e.printStackTrace();
        
        }
    }

    private void newInventaireRapport(int st_id, String st_nom, String catReq, String CA_NOM){
         
        try{
            HashMap param = new HashMap();
            param.put("ST_ID", new Integer(st_id));
            param.put("ST_NOM", new String(st_nom));
            param.put("CA_REQ", new String("( " + catReq + " )"));
            param.put("CA_NOM", new String(CA_NOM));
            PaneRapport rapport = new PaneRapport("inventaire",param,connex);
            principalTab.add("Inventaire de " + st_nom,rapport);
            principalTab.setSelectedComponent(rapport);
        
        }catch(Exception e){
        
            System.out.println("[PrincipaleGUI][newInventaireRapport] Exception :"+e);
            e.printStackTrace();
        
        }
    }
    
    public void newCommandeRapport(int st_id, int co_id){
         
        try{
            HashMap param = new HashMap();
            param.put("ST_ID", new Integer(st_id));
            param.put("CO_ID", new Integer(co_id));
            PaneRapport commande = new PaneRapport("commande",param,connex);
            principalTab.add("Commande N°" + co_id,commande);
            principalTab.setSelectedComponent(commande);
        
        }catch(Exception e){
        
            System.out.println("[PrincipaleGUI][newCommandeRapport] Exception :"+e);
            e.printStackTrace();
        
        }
    }

    private void newStockRapport(int st_id, String st_nom){
         
        try{
            HashMap param = new HashMap();
            param.put("ST_ID", new Integer(st_id));
            param.put("ST_NOM", new String(st_nom));
            PaneRapport rapport = new PaneRapport("stocks",param,connex);
            principalTab.add("Stock de " + st_nom,rapport);
            principalTab.setSelectedComponent(rapport);
        
        }catch(Exception e){
        
            System.out.println("[PrincipaleGUI][newStockRapport] Exception :"+e);
            e.printStackTrace();
        
        }
    }    
    
    private void newDateDialog(String form, int st_id, String st_nom){
        (new DateDialog(this, true, this, form, st_id, st_nom)).show();
    }
    
    public void setDateHash(String debutDate, String finDate, String form, int st_id, String st_nom){
        dateHash = new HashMap();
        dateHash.put("datePetit",debutDate);
        dateHash.put("dateGrand",finDate);
        if(form.compareTo("entree") == 0){
            this.newEntreeRapport(st_id, st_nom, dateHash);
        }else if(form.compareTo("sortie") == 0){
            this.newSortieRapport(st_id, st_nom, dateHash);
        }
    }
    
        
    public void newjepRapport(String date, Integer ST_ID){
         
        try{
            HashMap param = new HashMap();
            param.put("JEP_DATE", date);
            param.put("ST_ID", ST_ID);
            PaneRapport rapport = new PaneRapport("jepReport",param,connex);
            principalTab.add("Journaux des écarts positifs au " + date ,rapport);
            principalTab.setSelectedComponent(rapport);
        }catch(Exception e){
            System.out.println("[PrincipaleGUI][newjepRapport] Exception :"+e);
            e.printStackTrace();
        }
    }
    
    public void newjenRapport(String date, Integer ST_ID){
         
        try{
            HashMap param = new HashMap();
            param.put("JEP_DATE", date);
            param.put("ST_ID", ST_ID);
            PaneRapport rapport = new PaneRapport("jenReport",param,connex);
            principalTab.add("Journaux des écarts négatifs au " + date,rapport);
            principalTab.setSelectedComponent(rapport);
        }catch(Exception e){
            System.out.println("[PrincipaleGUI][newjenRapport] Exception :"+e);
            e.printStackTrace();
        }
    }
    
    private void newSelectJEPNDate(String journal, Integer ST_ID){
        (new dialogs.SelectJEPNDateDialog(this, true, connex, journal, ST_ID)).show();
    }
    
    private void newStockSaisiePane(int st_id, String st_nom){
        JEPPanel saisieStockPanel = new JEPPanel(connex,st_id,userLogged, this);
        principalTab.add("Saisie des stocks de " + st_nom,saisieStockPanel);
        principalTab.setSelectedComponent(saisieStockPanel);
        
    }
   
}    
    

    

