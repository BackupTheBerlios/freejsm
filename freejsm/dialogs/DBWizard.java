/*
 * DBWizard.java
 *
 * Created on 26 novembre 2004, 13:44
 */

package dialogs;

/* Active le support de la base de registre Windows 
 * les param�tres sont alors stock�s dans celle-ci
 * si cela n'est pas possible(unix ou exception)
 * alors un fichier texte est g�n�r� 
 */

import java.sql.*;
import javax.swing.*;

import classes.UnitedRegistry;
import com.develog.database.sessionSQL;

import com.ice.jni.registry.*;
import com.mysql.jdbc.Driver;


/**
 *
 * @author  alexandre
 */
public class DBWizard extends javax.swing.JDialog {
    
    private UnitedRegistry uReg ;
    private sessionSQL ses = new sessionSQL();
    private String driverName = "com.mysql.jdbc.Driver";
    private Connection connect;
    private java.awt.Frame papounet;
    
    /** Creates new form DBWizard */
    public DBWizard(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        papounet = parent;
        initComponents();
        initRegistry();
        initValues();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jPanel2 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        user = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        passwd = new javax.swing.JPasswordField();
        jPanel9 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        serverName = new javax.swing.JTextField();
        jPanel32 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        db = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        port = new javax.swing.JTextField();
        jPanel25 = new javax.swing.JPanel();
        tester = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        valider = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel27 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Configuration d'acc\u00e9s aux donn\u00e9es");
        setResizable(false);
        jPanel26.setLayout(new java.awt.BorderLayout());

        jPanel8.setLayout(new javax.swing.BoxLayout(jPanel8, javax.swing.BoxLayout.X_AXIS));

        jPanel8.setBorder(new javax.swing.border.TitledBorder(null, "Configuration d'acc\u00e9s aux donn\u00e9es", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(0, 0, 255)));
        jPanel12.setLayout(new java.awt.BorderLayout());

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/gnu/icones/serveur/service_status_stopped.png")));
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel12.add(jLabel3, java.awt.BorderLayout.WEST);

        jPanel8.add(jPanel12);

        jPanel10.setLayout(new javax.swing.BoxLayout(jPanel10, javax.swing.BoxLayout.X_AXIS));

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));

        jPanel17.setLayout(new java.awt.BorderLayout());

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Nom utilisateur:");
        jLabel7.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel21.add(jLabel7);

        jPanel17.add(jPanel21, java.awt.BorderLayout.EAST);

        jPanel1.add(jPanel17);

        jPanel18.setLayout(new java.awt.BorderLayout());

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Mot de passe:");
        jLabel8.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel22.add(jLabel8);

        jPanel18.add(jPanel22, java.awt.BorderLayout.EAST);

        jPanel1.add(jPanel18);

        jPanel19.setLayout(new java.awt.BorderLayout());

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Nom du serveur:");
        jLabel9.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel23.add(jLabel9);

        jPanel19.add(jPanel23, java.awt.BorderLayout.EAST);

        jPanel1.add(jPanel19);

        jPanel30.setLayout(new java.awt.BorderLayout());

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Base de donn\u00e9e:");
        jLabel11.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel31.add(jLabel11);

        jPanel30.add(jPanel31, java.awt.BorderLayout.EAST);

        jPanel1.add(jPanel30);

        jPanel20.setLayout(new java.awt.BorderLayout());

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Port:");
        jLabel10.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel24.add(jLabel10);

        jPanel20.add(jPanel24, java.awt.BorderLayout.EAST);

        jPanel1.add(jPanel20);

        jPanel10.add(jPanel1);

        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.Y_AXIS));

        jPanel4.setLayout(new java.awt.BorderLayout());

        user.setColumns(15);
        user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userActionPerformed(evt);
            }
        });

        jPanel16.add(user);

        jPanel4.add(jPanel16, java.awt.BorderLayout.WEST);

        jPanel3.add(jPanel4);

        jPanel5.setLayout(new java.awt.BorderLayout());

        passwd.setColumns(15);
        passwd.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11));
        jPanel15.add(passwd);

        jPanel5.add(jPanel15, java.awt.BorderLayout.WEST);

        jPanel3.add(jPanel5);

        jPanel9.setLayout(new java.awt.BorderLayout());

        serverName.setColumns(15);
        serverName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serverNameActionPerformed(evt);
            }
        });

        jPanel14.add(serverName);

        jPanel9.add(jPanel14, java.awt.BorderLayout.WEST);

        jPanel3.add(jPanel9);

        jPanel32.setLayout(new java.awt.BorderLayout());

        db.setColumns(15);
        jPanel33.add(db);

        jPanel32.add(jPanel33, java.awt.BorderLayout.WEST);

        jPanel3.add(jPanel32);

        jPanel11.setLayout(new java.awt.BorderLayout());

        port.setColumns(4);
        port.setText("3306");
        jPanel13.add(port);

        jPanel11.add(jPanel13, java.awt.BorderLayout.WEST);

        tester.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/gnu/icones/16x16/actions/connect_creating.png")));
        tester.setText("Tester!");
        tester.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testerActionPerformed(evt);
            }
        });

        jPanel25.add(tester);

        jPanel11.add(jPanel25, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel11);

        jPanel10.add(jPanel3);

        jPanel8.add(jPanel10);

        jPanel26.add(jPanel8, java.awt.BorderLayout.CENTER);

        jPanel6.setLayout(new java.awt.BorderLayout());

        valider.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/gnu/icones/22x22/actions/button_ok.png")));
        valider.setText("Valider");
        valider.setEnabled(false);
        valider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                validerActionPerformed(evt);
            }
        });

        jPanel7.add(valider);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/gnu/icones/22x22/actions/button_cancel.png")));
        jButton2.setText("Annuler");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel7.add(jButton2);

        jPanel6.add(jPanel7, java.awt.BorderLayout.EAST);

        jPanel26.add(jPanel6, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel26, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel27, java.awt.BorderLayout.NORTH);

        getContentPane().add(jPanel28, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel29, java.awt.BorderLayout.WEST);

        pack();
    }//GEN-END:initComponents

    private void validerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_validerActionPerformed
        setValues();
        uReg.disconnectFromRegistry();
        this.dispose();
    }//GEN-LAST:event_validerActionPerformed

    private void initRegistry(){
        uReg = new UnitedRegistry();
        uReg.enableWinRegistry(true);
        uReg.connectToRegistry();
    }
    
    private void initValues(){
        try{
        user.setText(uReg.getStringFromRegistry("dbuser"));
        serverName.setText(uReg.getStringFromRegistry("serverName"));
        db.setText(uReg.getStringFromRegistry("db"));
        passwd.setText(uReg.getStringFromRegistry("dbpasswd"));
        port.setText(""+uReg.getStringFromRegistry("port"));
        
        }catch(Exception e){};
    }
    
    private void setValues(){
        uReg.setToRegistry("dbuser",user.getText());
        uReg.setToRegistry("dbpasswd",passwd.getText());
        uReg.setToRegistry("serverName", serverName.getText());
        uReg.setToRegistry("db", db.getText());
        uReg.setToRegistry("port", port.getText());
    }
    
    private void interrogation(){
        user.setEditable(false);
        serverName.setEditable(false);
        db.setEditable(false);
        port.setEditable(false);
        passwd.setEditable(false);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/gnu/icones/serveur/service_status_unknown.png")));
    }
    
    private void running(){
        user.setEditable(false);
        serverName.setEditable(false);
        db.setEditable(false);
        port.setEditable(false);
        passwd.setEditable(false);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/gnu/icones/serveur/service_status_running.png")));
        tester.setEnabled(false);
    }
    
    private void base(){
        user.setEditable(true);
        serverName.setEditable(true);
        db.setEditable(true);
        port.setEditable(true);
        passwd.setEditable(true);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/gnu/icones/serveur/service_status_stopped.png")));
        tester.setEnabled(true);
    }
     
    public void GestInfo(String e){
        if ( JOptionPane.showConfirmDialog(papounet,e,"Info",
        JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null) == 0){}
    }
    
    public void GestErreur(String e){
        if ( JOptionPane.showConfirmDialog(papounet,e,"ERREUR",
        JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null) == 0){}
    }
    private void testerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testerActionPerformed
/* Teste la connection � la base SQL avec les param�tres donn�s*/
   interrogation();
   try{
       connect=ses.getSession("com.mysql.jdbc.Driver", serverName.getText(), db.getText(), user.getText(), passwd.getText());
       running();
       GestInfo("Connection �tablie!");
       valider.setEnabled(true);
       //connect.close();
   }catch(Exception e){
        GestErreur("La connection a �chou�e!\nRaison :"+e);
        base();
   }
    }//GEN-LAST:event_testerActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_userActionPerformed

    private void serverNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serverNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_serverNameActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField db;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPasswordField passwd;
    private javax.swing.JTextField port;
    private javax.swing.JTextField serverName;
    private javax.swing.JButton tester;
    private javax.swing.JTextField user;
    private javax.swing.JButton valider;
    // End of variables declaration//GEN-END:variables
    
}
