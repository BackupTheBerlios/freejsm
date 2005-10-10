package forms;

/*
 * identification.java
 *
 * Created on 11/01/2004
 */

/**
 * Ce code source est la propriete de Develog.
 * @author  Alexandre Labayle
 *          Marigo Yann
 *
 * Cette interface assure la saisie des mots de passe pour l'acc�s � la base 
 * de donnee.
 *
 */
import java.awt.*;
import java.net.URL;
public final class Identification extends javax.swing.JFrame {
      
    /** Creates new form identification */
    public Identification() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jp = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        Validation = new javax.swing.JButton();
        Annulation = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        UtilisateurPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        utilisateur = new javax.swing.JTextField();
        PasswordPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        password = new javax.swing.JPasswordField();

        setTitle("Identification");
        setName("FrIdentification");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jp.setLayout(new java.awt.BorderLayout());

        Validation.setFont(new java.awt.Font("Dialog", 0, 12));
        Validation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/develog/icones/stock_ok_20.png")));
        Validation.setText("ok");
        Validation.setAlignmentY(2.0F);
        Validation.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Validation.setPreferredSize(new java.awt.Dimension(76, 30));
        Validation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Valider(evt);
            }
        });

        jPanel2.add(Validation);

        Annulation.setFont(new java.awt.Font("Dialog", 0, 12));
        Annulation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/develog/icones/stock_cancel_20.png")));
        Annulation.setText("annuler");
        Annulation.setAlignmentY(2.0F);
        Annulation.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Annulation.setPreferredSize(new java.awt.Dimension(103, 30));
        Annulation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Annuler(evt);
            }
        });

        jPanel2.add(Annulation);

        jp.add(jPanel2, java.awt.BorderLayout.EAST);

        jp.add(jSeparator1, java.awt.BorderLayout.NORTH);

        getContentPane().add(jp, java.awt.BorderLayout.SOUTH);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/develog/icones/Computer.png")));
        jButton3.setBorder(null);
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.setFocusPainted(false);
        jPanel4.add(jButton3);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel1.setText("Utilisateur:");
        UtilisateurPanel.add(jLabel1);

        utilisateur.setColumns(10);
        UtilisateurPanel.add(utilisateur);

        jPanel5.add(UtilisateurPanel, java.awt.BorderLayout.NORTH);

        PasswordPanel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                PasswordKT(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel2.setText("Password:");
        PasswordPanel.add(jLabel2);

        password.setColumns(10);
        PasswordPanel.add(password);

        jPanel5.add(PasswordPanel, java.awt.BorderLayout.SOUTH);

        jPanel4.add(jPanel5);

        getContentPane().add(jPanel4, java.awt.BorderLayout.CENTER);

        pack();
    }//GEN-END:initComponents

    private void PasswordKT(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PasswordKT

    }//GEN-LAST:event_PasswordKT

    private void Valider(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Valider
        // On positionne juste le flag a Vrai pour indiquer
        // qu'on est pret a valider la saisie.
        
        flag = true;
    }//GEN-LAST:event_Valider

    private void Annuler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Annuler
        /* On ferme l'application dans le cas ou on annule
         */
        System.exit(0);
    }//GEN-LAST:event_Annuler
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm
    /* Cette methode renvoie l'etat de l'interface utilisateur 
     cela permet de savoir si ENTREE est press�ee sur le PASSWORD
     ou que le boutton VALIDATION est enclenche*/
    /**
     * @return
     */    
    public boolean StateUI(){
        if (flag) { return false;} else { return true;} 
    }
    
    /* Cette methode retourne le contenu du champs UTILISATEUR
     */
    public String user(){
        return utilisateur.getText();
    }
    
    /* Cette methode retourne le contenu du champs PASSWORD 
     */
    public String passwd(){
        char[] tableau = password.getPassword();
        return new String(tableau);
    }
    
    /* Cette methode permet de reinitialiser l'objet */
    public void reset(){
        utilisateur.setText(null);
        password.setText(null);
        flag = false;
    
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Annulation;
    private javax.swing.JPanel PasswordPanel;
    private javax.swing.JPanel UtilisateurPanel;
    private javax.swing.JButton Validation;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel jp;
    private javax.swing.JPasswordField password;
    private javax.swing.JTextField utilisateur;
    // End of variables declaration//GEN-END:variables

    private boolean flag = false;

}