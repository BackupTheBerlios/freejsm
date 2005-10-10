/*
 * DClient.java
 *
 * Created on 22 septembre 2002, 17:13
 */

package dialogs;

import java.awt.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.awt.print.*;

import javax.swing.*;
import javax.swing.text.*;


import com.develog.database.Parametre;


/**
 *
 * @author  Labayle Alexandre
 */
public class DiversParams extends javax.swing.JDialog {

    /* Liste des variables */
    private Parametre serveurMailParam  = null;
    private Parametre loginMailParam    = null;
    private Parametre pwdMailParam      = null;
    private Parametre adresseCapebParam = null;
    private Parametre sujetCapebParam   = null;
    private Parametre corpsCapebParam   = null;
    private Parametre adresseGazParam   = null;
    private Parametre sujetGazParam     = null;
    private Parametre corpsGazParam     = null;
    private Connection connexion        = null;
    private boolean EnModif             = false;
    
    /** Constructeur de la fen�tre en mode consultation  */
    public DiversParams(java.awt.Frame parent, Connection connex, boolean modal) {
        super(parent, modal);
        connexion = connex;
        
        //On cree les object correspondant aux params presents sur la fenetre
        try {
            serveurMailParam    = new Parametre("SERVEUR_MAIL",connexion,true);
            loginMailParam      = new Parametre("LOGIN_MAIL",connexion,true);
            pwdMailParam        = new Parametre("PWD_MAIL",connexion,true);
            adresseCapebParam   = new Parametre("ADRESSE_CAPEB", connexion,true);
            sujetCapebParam     = new Parametre("SUJET_CAPEB", connexion,true);
            corpsCapebParam     = new Parametre("CORPS_CAPEB", connexion,true);
            adresseGazParam     = new Parametre("ADRESSE_GAZ", connexion,true);
            sujetGazParam       = new Parametre("SUJET_GAZ", connexion,true);
            corpsGazParam       = new Parametre("CORPS_GAZ", connexion,true);
        }catch(Exception e)
        {
            System.out.println("Erreur � l'init des params: "+e);
        }
        initComponents();    
        
        this.setTitle("Param�tres...");
        
        // On affiche les donnees dans l'interface
        this.pack();
        SetFields();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jPanel2 = new javax.swing.JPanel();
        AdresseMail = new javax.swing.JPanel();
        panelserver = new javax.swing.JPanel();
        Text = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        labelServeur = new javax.swing.JLabel();
        labelLogin = new javax.swing.JLabel();
        labelPass = new javax.swing.JLabel();
        champs = new javax.swing.JPanel();
        serveurMail = new javax.swing.JTextField();
        loginMail = new javax.swing.JTextField();
        pwdMail = new javax.swing.JPasswordField();
        jCheckBox1 = new javax.swing.JCheckBox();
        panelCapeb = new javax.swing.JPanel();
        textCapeb = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        labelCapeb = new javax.swing.JLabel();
        sujetCapebLabel = new javax.swing.JLabel();
        corpsCapebLabel = new javax.swing.JLabel();
        ChampsCapeb = new javax.swing.JPanel();
        adresseCapeb = new javax.swing.JTextField();
        sujetCapeb = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        corpsCapeb = new javax.swing.JTextArea();
        panelGaz = new javax.swing.JPanel();
        textGaz = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        labelCapeb1 = new javax.swing.JLabel();
        sujetCapebLabel1 = new javax.swing.JLabel();
        corpsCapebLabel1 = new javax.swing.JLabel();
        ChampsGaz = new javax.swing.JPanel();
        adresseGaz = new javax.swing.JTextField();
        sujetGaz = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        corpsGaz = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        BouttonPanel = new javax.swing.JPanel();
        OK = new javax.swing.JButton();
        Annuler = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setFont(new java.awt.Font("Arial", 0, 10));
        setModal(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel2.setMinimumSize(new java.awt.Dimension(500, 500));
        jPanel2.setPreferredSize(new java.awt.Dimension(500, 500));
        AdresseMail.setLayout(new java.awt.GridLayout(3, 1));

        AdresseMail.setBorder(new javax.swing.border.TitledBorder(new javax.swing.border.EtchedBorder(), "Param\u00e8tres Mail", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 10)));
        panelserver.setLayout(new java.awt.GridLayout(1, 2));

        panelserver.setBorder(new javax.swing.border.TitledBorder(null, "Serveur de mail", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 11)));
        Text.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 8));

        jPanel7.setLayout(new java.awt.GridLayout(3, 0, 0, 8));

        labelServeur.setFont(new java.awt.Font("Dialog", 0, 12));
        labelServeur.setText("Serveur d'envoi de mail :");
        jPanel7.add(labelServeur);

        labelLogin.setFont(new java.awt.Font("Dialog", 0, 12));
        labelLogin.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelLogin.setText("Identifiant du serveur de mail :");
        jPanel7.add(labelLogin);

        labelPass.setFont(new java.awt.Font("Dialog", 0, 12));
        labelPass.setText("Mot de passe du serveur de mail:");
        jPanel7.add(labelPass);

        Text.add(jPanel7);

        panelserver.add(Text);

        champs.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        serveurMail.setColumns(20);
        champs.add(serveurMail);

        loginMail.setColumns(20);
        champs.add(loginMail);

        pwdMail.setColumns(20);
        champs.add(pwdMail);

        jCheckBox1.setText("S'authentifier lors de l'envoi");
        champs.add(jCheckBox1);

        panelserver.add(champs);

        AdresseMail.add(panelserver);

        panelCapeb.setLayout(new java.awt.GridLayout(1, 2));

        panelCapeb.setBorder(new javax.swing.border.TitledBorder(null, "Mail \"CAPEB\"", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 11)));
        panelCapeb.setToolTipText("Mail CAPEB");
        panelCapeb.setName("Mail \"CAPEB\"");
        textCapeb.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jPanel6.setLayout(new java.awt.GridLayout(3, 0, 0, 11));

        labelCapeb.setFont(new java.awt.Font("Dialog", 0, 12));
        labelCapeb.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelCapeb.setText("Adresse d'envoi :");
        jPanel6.add(labelCapeb);

        sujetCapebLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        sujetCapebLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        sujetCapebLabel.setText("Sujet du mail :");
        sujetCapebLabel.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel6.add(sujetCapebLabel);

        corpsCapebLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        corpsCapebLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        corpsCapebLabel.setText("Corps du message :");
        jPanel6.add(corpsCapebLabel);

        textCapeb.add(jPanel6);

        panelCapeb.add(textCapeb);

        ChampsCapeb.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        adresseCapeb.setColumns(20);
        adresseCapeb.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        adresseCapeb.setPreferredSize(new java.awt.Dimension(220, 21));
        ChampsCapeb.add(adresseCapeb);

        sujetCapeb.setColumns(20);
        sujetCapeb.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        sujetCapeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sujetCapebActionPerformed(evt);
            }
        });

        ChampsCapeb.add(sujetCapeb);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(220, 78));
        corpsCapeb.setColumns(1);
        corpsCapeb.setRows(5);
        corpsCapeb.setTabSize(20);
        jScrollPane1.setViewportView(corpsCapeb);

        ChampsCapeb.add(jScrollPane1);

        panelCapeb.add(ChampsCapeb);

        AdresseMail.add(panelCapeb);

        panelGaz.setLayout(new java.awt.GridLayout(1, 2));

        panelGaz.setBorder(new javax.swing.border.TitledBorder(null, "Mail \"Responsables Gaz\"", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 11)));
        panelGaz.setToolTipText("Mail \"Responsables Gaz\"");
        panelGaz.setName("Mail \"Responsables Gaz\"");
        textGaz.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jPanel8.setLayout(new java.awt.GridLayout(3, 2, 0, 11));

        labelCapeb1.setFont(new java.awt.Font("Dialog", 0, 12));
        labelCapeb1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelCapeb1.setText("Adresse d'envoi :");
        jPanel8.add(labelCapeb1);

        sujetCapebLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
        sujetCapebLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        sujetCapebLabel1.setText("Sujet du mail :");
        sujetCapebLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel8.add(sujetCapebLabel1);

        corpsCapebLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
        corpsCapebLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        corpsCapebLabel1.setText("Corps du message :");
        jPanel8.add(corpsCapebLabel1);

        textGaz.add(jPanel8);

        panelGaz.add(textGaz);

        ChampsGaz.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        adresseGaz.setColumns(20);
        adresseGaz.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        ChampsGaz.add(adresseGaz);

        sujetGaz.setColumns(20);
        sujetGaz.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        ChampsGaz.add(sujetGaz);

        jScrollPane2.setPreferredSize(new java.awt.Dimension(220, 78));
        corpsGaz.setColumns(1);
        corpsGaz.setRows(5);
        corpsGaz.setTabSize(20);
        jScrollPane2.setViewportView(corpsGaz);

        ChampsGaz.add(jScrollPane2);

        panelGaz.add(ChampsGaz);

        AdresseMail.add(panelGaz);

        jPanel2.add(AdresseMail, java.awt.BorderLayout.CENTER);
        AdresseMail.getAccessibleContext().setAccessibleName("Mail :");

        getContentPane().add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel3.setBorder(new javax.swing.border.TitledBorder(new javax.swing.border.EtchedBorder(), "Th\u00e9me"));
        jRadioButton1.setFont(new java.awt.Font("Dialog", 0, 12));
        jRadioButton1.setText("D\u00e9velog");
        jRadioButton1.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/com/develog/icones/IceWM.png")));
        jRadioButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/develog/icones/DeveLog.png")));
        jRadioButton1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/com/develog/icones/DeveLog.png")));
        jPanel3.add(jRadioButton1);

        jRadioButton2.setFont(new java.awt.Font("Dialog", 0, 12));
        jRadioButton2.setText("WindowsXP");
        jRadioButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/develog/icones/Windows.png")));
        jPanel3.add(jRadioButton2);

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.BorderLayout());

        OK.setFont(new java.awt.Font("Dialog", 0, 12));
        OK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/gnu/icones/22x22/actions/button_ok.png")));
        OK.setText("ok");
        OK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bouttonOK(evt);
            }
        });

        BouttonPanel.add(OK);

        Annuler.setFont(new java.awt.Font("Dialog", 0, 12));
        Annuler.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/gnu/icones/22x22/actions/button_cancel.png")));
        Annuler.setText("annuler");
        Annuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnnulerActionPerformed(evt);
            }
        });

        BouttonPanel.add(Annuler);

        jPanel1.add(BouttonPanel, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

    }//GEN-END:initComponents

    private void sujetCapebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sujetCapebActionPerformed
        // Add your handling code here:
    }//GEN-LAST:event_sujetCapebActionPerformed
    //Rempli les champs
    public void SetFields (){
        try{
            serveurMail.setText(serveurMailParam.getValeur());
            loginMail.setText(loginMailParam.getValeur());
            pwdMail.setText(pwdMailParam.getValeur()); 
            adresseCapeb.setText(adresseCapebParam.getValeur());
            sujetCapeb.setText(sujetCapebParam.getValeur());
            corpsCapeb.setText(corpsCapebParam.getValeur());
            adresseGaz.setText(adresseGazParam.getValeur());
            sujetGaz.setText(sujetGazParam.getValeur());
            corpsGaz.setText(corpsGazParam.getValeur());
                
        }catch(Exception e) {
            System.out.println("[DiversParams][SetFields] Exception lors du remplissage des champs: "+e);
        }
    }
    
    private void AnnulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnnulerActionPerformed
        // Add your handling code here:
        dispose();
    }//GEN-LAST:event_AnnulerActionPerformed

    private void bouttonOK(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bouttonOK
        try {
            serveurMailParam.setValeur(serveurMail.getText());
            loginMailParam.setValeur(loginMail.getText());
            pwdMailParam.setValeur(pwdMail.getText());
            adresseCapebParam.setValeur(adresseCapeb.getText());
            sujetCapebParam.setValeur(sujetCapeb.getText());
            corpsCapebParam.setValeur(corpsCapeb.getText());
            adresseGazParam.setValeur(adresseGaz.getText());
            sujetGazParam.setValeur(sujetGaz.getText());
            corpsGazParam.setValeur(corpsGaz.getText());
        }catch(Exception e) {
            System.out.println(e);
        }
        dispose();
    }//GEN-LAST:event_bouttonOK
    
    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel AdresseMail;
    private javax.swing.JButton Annuler;
    private javax.swing.JPanel BouttonPanel;
    private javax.swing.JPanel ChampsCapeb;
    private javax.swing.JPanel ChampsGaz;
    private javax.swing.JButton OK;
    private javax.swing.JPanel Text;
    public javax.swing.JTextField adresseCapeb;
    private javax.swing.JTextField adresseGaz;
    private javax.swing.JPanel champs;
    private javax.swing.JTextArea corpsCapeb;
    private javax.swing.JLabel corpsCapebLabel;
    private javax.swing.JLabel corpsCapebLabel1;
    private javax.swing.JTextArea corpsGaz;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelCapeb;
    private javax.swing.JLabel labelCapeb1;
    private javax.swing.JLabel labelLogin;
    private javax.swing.JLabel labelPass;
    private javax.swing.JLabel labelServeur;
    private javax.swing.JTextField loginMail;
    private javax.swing.JPanel panelCapeb;
    private javax.swing.JPanel panelGaz;
    private javax.swing.JPanel panelserver;
    private javax.swing.JPasswordField pwdMail;
    private javax.swing.JTextField serveurMail;
    private javax.swing.JTextField sujetCapeb;
    private javax.swing.JLabel sujetCapebLabel;
    private javax.swing.JLabel sujetCapebLabel1;
    private javax.swing.JTextField sujetGaz;
    private javax.swing.JPanel textCapeb;
    private javax.swing.JPanel textGaz;
    // End of variables declaration//GEN-END:variables
}
