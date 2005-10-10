/*
 * aide.java
 *
 * Created on 13 janvier 2004, 17:27
 */

package forms;

/**
 *
 * @authors Labayle Alexandre,Marigo Yann
 */
public final class PaneAide extends javax.swing.JDialog {
    
    /** Creates new form aide */
    public PaneAide(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jPanel1 = new javax.swing.JPanel();
        Contenu = new javax.swing.JPanel();
        Aide = new javax.swing.JLabel();
        Banniere = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        DecorationGauche = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        BoutonFermer = new javax.swing.JButton();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setLayout(new java.awt.BorderLayout());

        Aide.setText("Belle aide expliquant comment utiliser l'interface de consultation.");
        Contenu.add(Aide);

        jPanel1.add(Contenu, java.awt.BorderLayout.CENTER);

        Banniere.setLayout(new java.awt.BorderLayout());

        Banniere.setBackground(new java.awt.Color(204, 204, 255));
        Banniere.setBorder(new javax.swing.border.EtchedBorder());
        Banniere.setMaximumSize(new java.awt.Dimension(2147483647, 50));
        jLabel1.setFont(new java.awt.Font("Arial", 0, 24));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/develog/icones/stock_help_24.png")));
        jLabel1.setText("Aide");
        Banniere.add(jLabel1, java.awt.BorderLayout.CENTER);

        DecorationGauche.setLayout(new java.awt.BorderLayout());

        DecorationGauche.setBackground(new java.awt.Color(204, 204, 255));
        jPanel14.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 1));

        jPanel14.setBackground(new java.awt.Color(204, 204, 255));
        jPanel15.setBackground(new java.awt.Color(240, 240, 255));
        jPanel14.add(jPanel15);

        jPanel16.setBackground(new java.awt.Color(240, 240, 255));
        jPanel14.add(jPanel16);

        jPanel17.setBackground(new java.awt.Color(204, 204, 255));
        jPanel14.add(jPanel17);

        DecorationGauche.add(jPanel14, java.awt.BorderLayout.CENTER);

        jPanel8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 1));

        jPanel8.setBackground(new java.awt.Color(204, 204, 255));
        jPanel7.setBackground(new java.awt.Color(225, 225, 255));
        jPanel8.add(jPanel7);

        jPanel6.setBackground(new java.awt.Color(204, 204, 255));
        jPanel8.add(jPanel6);

        jPanel10.setBackground(new java.awt.Color(204, 204, 255));
        jPanel8.add(jPanel10);

        DecorationGauche.add(jPanel8, java.awt.BorderLayout.SOUTH);

        jPanel18.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 1));

        jPanel18.setBackground(new java.awt.Color(204, 204, 255));
        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.add(jPanel19);

        jPanel20.setBackground(new java.awt.Color(240, 240, 255));
        jPanel18.add(jPanel20);

        jPanel21.setBackground(new java.awt.Color(225, 225, 255));
        jPanel18.add(jPanel21);

        DecorationGauche.add(jPanel18, java.awt.BorderLayout.NORTH);

        Banniere.add(DecorationGauche, java.awt.BorderLayout.WEST);

        jToolBar1.setBorder(new javax.swing.border.EtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setFont(new java.awt.Font("Dialog", 0, 8));
        jToolBar1.setMaximumSize(new java.awt.Dimension(50, 50));
        jToolBar1.setMinimumSize(new java.awt.Dimension(50, 50));
        BoutonFermer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/develog/icones/exit.png")));
        BoutonFermer.setBorderPainted(false);
        BoutonFermer.setMargin(new java.awt.Insets(0, 5, 0, 5));
        BoutonFermer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoutonFermerActionPerformed(evt);
            }
        });

        jToolBar1.add(BoutonFermer);

        Banniere.add(jToolBar1, java.awt.BorderLayout.EAST);

        jPanel1.add(Banniere, java.awt.BorderLayout.NORTH);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }//GEN-END:initComponents

    private void BoutonFermerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonFermerActionPerformed
        //On Ferme la fen�tre
        setVisible(false);
        dispose();
    }//GEN-LAST:event_BoutonFermerActionPerformed
    
    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog
 
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Aide;
    private javax.swing.JPanel Banniere;
    private javax.swing.JButton BoutonFermer;
    private javax.swing.JPanel Contenu;
    private javax.swing.JPanel DecorationGauche;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
    
}