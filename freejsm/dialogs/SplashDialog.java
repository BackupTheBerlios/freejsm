/*
 * SplashDialog.java
 *
 * Created on 9 ottobre 2004, 11.19
 */

package dialogs;

/**
 *
 * @author  Administrator
 */
public class SplashDialog extends javax.swing.JDialog {
    
    /** Creates new form SplashDialog */
    public SplashDialog(javax.swing.JFrame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        this.setSize(350,310);
        classes.Misc.centerFrame(this);
    }
    

    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setUndecorated(true);
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0)));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/splashscreen.png")));
        jLabel1.setIconTextGap(0);
        jPanel1.add(jLabel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }//GEN-END:initComponents
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
    
}
