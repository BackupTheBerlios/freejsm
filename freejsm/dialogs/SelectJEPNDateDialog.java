/*
 * selectJEPNDateDialog.java
 *
 * Created on 22 mars 2005, 13:53
 */

package dialogs;

import java.sql.Connection;
import java.sql.ResultSet;
/**
 *
 * @author  nahuel
 */
public class SelectJEPNDateDialog extends javax.swing.JDialog {
    static java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    private Connection connex;
    private String journal;
    private Integer ST_ID;
    private forms.PrincipaleGUI papa;
    
    /** Creates new form selectJEPNDateDialog */
    public SelectJEPNDateDialog(forms.PrincipaleGUI parent, boolean modal, Connection connex, String journal, Integer ST_ID) {
        super(parent, modal);
        papa = parent;
        
        this.connex = connex;
        this.ST_ID = ST_ID;
        this.journal = journal;
        
        initComponents();
        
        initListe();
        this.setLocation((screenSize.width-(this.getSize().width))/2,(screenSize.height-(this.getSize().height))/2);
        this.setSize(200,65);
        this.setTitle("Selection de date");
        this.setResizable(false);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        dateListe = new javax.swing.JComboBox();
        acceptButton = new javax.swing.JButton();

        getContentPane().setLayout(new java.awt.FlowLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().add(dateListe);

        acceptButton.setText("Ok");
        acceptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptButtonActionPerformed(evt);
            }
        });

        getContentPane().add(acceptButton);

        pack();
    }//GEN-END:initComponents

    private void acceptButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acceptButtonActionPerformed
        if(journal.compareTo("JEP") == 0){
            papa.newjepRapport((String)dateListe.getSelectedItem(),ST_ID);
        }else if(journal.compareTo("JEN") == 0){
            papa.newjenRapport((String)dateListe.getSelectedItem(),ST_ID);
        }
        this.dispose();
    }//GEN-LAST:event_acceptButtonActionPerformed
    
private void initListe(){
    try{
        ResultSet rset = connex.prepareStatement("SELECT DISTINCT(" + journal + "_DATE) FROM " + journal + " WHERE ST_ID = " + ST_ID).executeQuery();
        while(rset.next()){
            dateListe.addItem(rset.getString(journal + "_DATE"));
        }
    }catch(Exception e){
        System.out.println("[SelectJEPNDateDialog][initListe] Exception " + e);
        e.printStackTrace();
    }
}
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton acceptButton;
    private javax.swing.JComboBox dateListe;
    // End of variables declaration//GEN-END:variables
    
}