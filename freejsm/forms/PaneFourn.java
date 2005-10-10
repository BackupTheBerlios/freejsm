/*
 * PaneFourn.java
 *
 * Created on 17 d�cembre 2004, 10:06
 */

package forms;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.util.Hashtable;
import java.util.HashMap;
import java.util.Vector;

//  import com.develog.utils.SmartCube;
import classes.JournalizedSmartCube;
import com.develog.utils.SmartConnector;
import com.develog.swing.SmartJTable;
/**
 *
 * @author  nahuel
 */
public class PaneFourn extends javax.swing.JPanel {
    
    private forms.PrincipaleGUI papa;
    private Vector fournGUI = new Vector();
    private SmartConnector intsconnector;
    public SmartJTable intSJT = null;
    private JournalizedSmartCube intsc;
    private String originalQuery = "SELECT FO_ID,FO_NOM,FO_ADRESSE,FO_CP,FO_VILLE,FO_TEL,FO_FAX,FO_MAIL, FO_ACTIV FROM FOURNISSEUR WHERE FO_ACTIV = 1";
    private String query;
    private Connection connex;
    private boolean enModification = false;

    
    /** Creates new form PaneFourn */
    public PaneFourn(Connection connectEXT, forms.PrincipaleGUI papa) {
        query = originalQuery;
        connex = connectEXT;
        this.papa = papa;
        initComponents();
        initCube();
        initTable();
        
        initDoubleClick();
        this.etatDeBase();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jPanel1 = new javax.swing.JPanel();
        listingPane = new javax.swing.JPanel();
        searchPane = new javax.swing.JPanel();
        rechercheRSLabel = new javax.swing.JLabel();
        rechercheRSField = new javax.swing.JTextField();
        rechercheCPLabel = new javax.swing.JLabel();
        rechercheCPField = new javax.swing.JTextField();
        actionPane = new javax.swing.JPanel();
        addFourn = new javax.swing.JButton();
        delFourn = new javax.swing.JButton();
        editFourn = new javax.swing.JButton();
        fournTabPane = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        editPane = new javax.swing.JPanel();
        buttonPane = new javax.swing.JPanel();
        addButton = new javax.swing.JButton();
        applyButton = new javax.swing.JButton();
        delButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        coord1 = new javax.swing.JPanel();
        rsPane = new javax.swing.JPanel();
        rsLabel = new javax.swing.JLabel();
        rsField = new javax.swing.JTextField();
        addrPane = new javax.swing.JPanel();
        addrLabel = new javax.swing.JLabel();
        addrField = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        emailLabel = new javax.swing.JLabel();
        emailField = new javax.swing.JTextField();
        coord2 = new javax.swing.JPanel();
        cpLabel = new javax.swing.JLabel();
        cpField = new javax.swing.JTextField();
        villeLabel = new javax.swing.JLabel();
        villeField = new javax.swing.JTextField();
        telLabel = new javax.swing.JLabel();
        telField = new javax.swing.JTextField();
        faxLabel = new javax.swing.JLabel();
        faxField = new javax.swing.JTextField();

        setLayout(new java.awt.BorderLayout());

        listingPane.setLayout(new java.awt.BorderLayout());

        listingPane.setPreferredSize(new java.awt.Dimension(504, 100));
        searchPane.setBorder(new javax.swing.border.EtchedBorder());
        searchPane.setPreferredSize(new java.awt.Dimension(504, 52));
        rechercheRSLabel.setText("Raison Sociale :");
        searchPane.add(rechercheRSLabel);

        rechercheRSField.setColumns(15);
        rechercheRSField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                rechercheRSFieldKeyReleased(evt);
            }
        });

        searchPane.add(rechercheRSField);

        rechercheCPLabel.setText("Code Postal :");
        searchPane.add(rechercheCPLabel);

        rechercheCPField.setColumns(5);
        rechercheCPField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                rechercheCPFieldKeyReleased(evt);
            }
        });

        searchPane.add(rechercheCPField);

        addFourn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/develog/icones/stock_apply.png")));
        addFourn.setToolTipText("Ajouter");
        addFourn.setMaximumSize(new java.awt.Dimension(50, 26));
        addFourn.setMinimumSize(new java.awt.Dimension(50, 26));
        addFourn.setPreferredSize(new java.awt.Dimension(50, 26));
        actionPane.add(addFourn);

        delFourn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/develog/icones/stock_cancel-16.png")));
        delFourn.setToolTipText("Supprimer");
        actionPane.add(delFourn);

        editFourn.setFont(new java.awt.Font("Dialog", 0, 12));
        editFourn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/develog/icones/edit.png")));
        editFourn.setToolTipText("Editer");
        actionPane.add(editFourn);

        searchPane.add(actionPane);

        listingPane.add(searchPane, java.awt.BorderLayout.NORTH);

        fournTabPane.setMinimumSize(new java.awt.Dimension(468, 500));
        fournTabPane.setPreferredSize(new java.awt.Dimension(468, 500));
        listingPane.add(fournTabPane, java.awt.BorderLayout.CENTER);

        add(listingPane, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.BorderLayout());

        editPane.setLayout(new java.awt.BorderLayout(5, 5));

        editPane.setBorder(new javax.swing.border.TitledBorder("Coordonn\u00e9es"));
        buttonPane.setLayout(new javax.swing.BoxLayout(buttonPane, javax.swing.BoxLayout.Y_AXIS));

        addButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/develog/icones/stock_add_24.png")));
        addButton.setToolTipText("Ajouter");
        addButton.setMaximumSize(new java.awt.Dimension(50, 26));
        addButton.setMinimumSize(new java.awt.Dimension(50, 26));
        addButton.setPreferredSize(new java.awt.Dimension(50, 26));
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        buttonPane.add(addButton);

        applyButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/develog/icones/stock_ok_20.png")));
        applyButton.setToolTipText("Appliquer");
        applyButton.setMaximumSize(new java.awt.Dimension(50, 26));
        applyButton.setMinimumSize(new java.awt.Dimension(50, 26));
        applyButton.setPreferredSize(new java.awt.Dimension(50, 26));
        applyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyButtonActionPerformed(evt);
            }
        });

        buttonPane.add(applyButton);

        delButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/develog/icones/stock_cancel-16.png")));
        delButton.setToolTipText("Supprimer");
        delButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delButtonActionPerformed(evt);
            }
        });

        buttonPane.add(delButton);

        editPane.add(buttonPane, java.awt.BorderLayout.EAST);

        jPanel4.setLayout(new java.awt.GridLayout(1, 2));

        coord1.setLayout(new java.awt.BorderLayout());

        rsPane.setLayout(new javax.swing.BoxLayout(rsPane, javax.swing.BoxLayout.X_AXIS));

        rsLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        rsLabel.setText("Raison Sociale :");
        rsPane.add(rsLabel);

        rsField.setName("FO_NOM");
        rsPane.add(rsField);

        coord1.add(rsPane, java.awt.BorderLayout.NORTH);

        addrPane.setLayout(new java.awt.GridLayout(1, 2));

        addrLabel.setText("Addresse :");
        addrLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        addrPane.add(addrLabel);

        addrField.setColumns(15);
        addrField.setRows(3);
        addrField.setBorder(new javax.swing.border.EtchedBorder());
        addrField.setName("FO_ADRESSE");
        addrPane.add(addrField);

        coord1.add(addrPane, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.X_AXIS));

        emailLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        emailLabel.setText("E-mail :");
        jPanel3.add(emailLabel);

        emailField.setName("FO_MAIL");
        jPanel3.add(emailField);

        coord1.add(jPanel3, java.awt.BorderLayout.SOUTH);

        jPanel4.add(coord1);

        coord2.setLayout(new java.awt.GridLayout(4, 2));

        cpLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        cpLabel.setText("Code Postal :");
        coord2.add(cpLabel);

        cpField.setName("FO_CP");
        coord2.add(cpField);

        villeLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        villeLabel.setText("Ville :");
        coord2.add(villeLabel);

        villeField.setName("FO_VILLE");
        coord2.add(villeField);

        telLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        telLabel.setText("N\u00b0 de T\u00e9l\u00e9phone :");
        coord2.add(telLabel);

        telField.setName("FO_TEL");
        coord2.add(telField);

        faxLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        faxLabel.setText("N\u00b0 de Fax :");
        coord2.add(faxLabel);

        faxField.setName("FO_FAX");
        coord2.add(faxField);

        jPanel4.add(coord2);

        editPane.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel2.add(editPane, java.awt.BorderLayout.NORTH);

        add(jPanel2, java.awt.BorderLayout.SOUTH);

    }//GEN-END:initComponents

    private void delButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delButtonActionPerformed
        if ( JOptionPane.showConfirmDialog(this,"Supprimer le fournisseur ?","Suppression",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null) == 0)
            try{
                //intsconnector.deleteRow();
                intsconnector.getValuesFromUI();
                String requetteANC = intsc.getRequette(enModification, "FOURNISSEUR", "FO_ID", intsc.getCubeInt("FO_ID"));
                
                // on set TVA ACTIV a 0
                Hashtable fo_activ = new Hashtable();
                fo_activ.put(new String("FO_ACTIV"),new Boolean(false));
                intsc.setCubeData(fo_activ);
                
                intsconnector.addNewRowWithID();
                
                intsc.getSpecific(intSJT.getSelectedRow()+1);
                
                //intsc.commitDBTransaction();
                
                intsc.commitDBTransaction(true, papa.userLogged.getID(), "FOURNISSEUR", intsc.getCubeInt("FO_ID"), "FO_ID", requetteANC);
                
                intSJT.loadData();
                miseABlanc();

                intSJT.change();
                etatDeBase();
            }catch(Exception e){
                System.out.println("[PaneFourn][delButtonActionPerformed] Exception : " + e);
                e.printStackTrace();
                JOptionPane.showMessageDialog(this,"Impossible de supprimer le fournisseur !","Erreur de suppression",JOptionPane.ERROR_MESSAGE,null);
                miseABlanc();
                etatDeBase(); 
            }
    }//GEN-LAST:event_delButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        miseABlanc();
        etatDeModification();
    }//GEN-LAST:event_addButtonActionPerformed

    private void applyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyButtonActionPerformed
        //Ajoute une ligne
        Hashtable ID = new Hashtable();
        try{ 
            if ( enModification == false )
                intsconnector.genNewRowWithID();
            
            String requetteANC = intsc.getRequette(enModification, "FOURNISSEUR", "FO_ID", intsc.getCubeInt("FO_ID"));
            
            intsconnector.getValuesFromUI();
            Hashtable fo_activ = new Hashtable();
            fo_activ.put(new String("FO_ACTIV"),new Boolean(true));
            intsc.setCubeData(fo_activ);
            intsconnector.addNewRowWithID();
                        
            //intsc.commitDBTransaction();
            intsc.commitDBTransaction(enModification, papa.userLogged.getID(), "FOURNISSEUR", intsc.getCubeInt("FO_ID"), "FO_ID", requetteANC);
            
            miseABlanc();

            intSJT.loadData();
            intSJT.change();
            
            enModification = false;
            intSJT.loadData();
            intSJT.change();
            etatDeBase();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"Le fournisseur n'a pu �tre ins�r�","Erreur d'ajout",JOptionPane.ERROR_MESSAGE,null);
            System.out.println("[PaneFourn][applyButtonActionPerformed] Exception : " + e);
            e.printStackTrace();
            miseABlanc();
            etatDeBase(); 
        }
    }//GEN-LAST:event_applyButtonActionPerformed

    private void rechercheCPFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rechercheCPFieldKeyReleased
        refresh();
    }//GEN-LAST:event_rechercheCPFieldKeyReleased

    private void rechercheRSFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rechercheRSFieldKeyReleased
        refresh();
    }//GEN-LAST:event_rechercheRSFieldKeyReleased
    
    public void refresh(){
     try{
         query = originalQuery + " WHERE FO_NOM like '" + rechercheRSField.getText() + "%' AND FO_CP like '" + rechercheCPField.getText() + "%'";
         System.out.println("Query=> "+query);
         intSJT.setQuery(query);
         intSJT.loadData();
         intSJT.change();
     }catch(Exception e){System.out.println(e);};    
    }
        
    private void initCube(){
        try{

            intsc = new JournalizedSmartCube(connex);
            intsc.setQuery("SELECT FO_ID,FO_NOM,FO_ADRESSE,FO_CP,FO_VILLE,FO_TEL,FO_FAX,FO_MAIL, FO_ACTIV FROM FOURNISSEUR WHERE FO_ACTIV = 1");
            intsc.loadCube();
            fournGUI.add(editPane);
            intsconnector = new SmartConnector(intsc,fournGUI);
            intsconnector.setEditable(false);
            }catch(Exception e){ 
                System.out.println(e);
            }
    }
    
    private void initTable(){ 
    try{
          Hashtable mappingColName = new Hashtable();
          HashMap invisibleColName  = new HashMap();
          
          /** On renomme les colonnes qui vont servir */
          
          mappingColName.put("FO_NOM","Raison Sociale");
          mappingColName.put("FO_ADRESSE","Adresse");
          mappingColName.put("FO_CP","Code postal");
          mappingColName.put("FO_VILLE","Ville");
          mappingColName.put("FO_TEL","T�l�phone");
          mappingColName.put("FO_FAX","Fax");
          mappingColName.put("FO_MAIL","E-Mail");
          
          /** On masque celle qui servent � rien */
          invisibleColName.put("FO_ID"  , "FO_ID");
          invisibleColName.put("FO_ACTIV"  , "FO_ACTIV");
          
          intSJT = new SmartJTable(intsc, mappingColName, invisibleColName);
          fournTabPane.setViewportView(intSJT);
    }catch(Exception e){
        System.out.println(e);};  
    }
    
    private boolean checkAnnulation() {
        return (!enModification || ( JOptionPane.showConfirmDialog(this,"Annuler la saisie en cours ?","Annulation",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null) == 0));
    }
    private void initDoubleClick() {
       intSJT.addMouseListener(new MouseAdapter(){
          public void mouseClicked(MouseEvent e){
            if ((e.getClickCount() == 1) && ( checkAnnulation()))
            {
              try{
                    enModification = false;
                    etatDeConsultation();
                    System.out.println("Un clic");
                    intsc.getSpecific(intSJT.getSelectedRow()+1);
                    intsconnector.setValuesFromCube();
              }catch(Exception e2){
                  etatDeBase();
                  System.out.println(e2);};
            }
                        
            if (e.getClickCount() == 2)
            {
              try{
                    enModification = true;
                    etatDeModification();
                    System.out.println("Deux clic");
                    intsc.getSpecific(intSJT.getSelectedRow()+1);
                    intsconnector.setValuesFromCube();
              }catch(Exception e2){
                  etatDeBase();
                  System.out.println(e2);
              };
            }
         }
       });
   }
       
    
    private void miseABlanc(){
        //Remise � blanc
        rsField.setText("");
        addrField.setText("");
        cpField.setText("");
        villeField.setText("");
        telField.setText("");
        faxField.setText("");
        emailField.setText("");
    }
    
    private void etatDeBase(){
       rechercheRSField.setEnabled(true);
       rechercheCPField.setEnabled(true);
       intsconnector.setEditable(false);
       
       //Gestion du bouton de droite
       addButton.setVisible(true);
       addButton.setEnabled(true);
       applyButton.setVisible(false);
       delButton.setVisible(false);
   }
    private void etatDeConsultation(){
       rechercheRSField.setEnabled(true);
       rechercheCPField.setEnabled(true);
       intsconnector.setEditable(false);
       
       //Gestion du bouton de droite
       addButton.setVisible(true);
       addButton.setEnabled(true);
       applyButton.setVisible(false);
       delButton.setVisible(true);
       delButton.setEnabled(true);
   }
 
    
    private void etatDeModification(){
       rechercheRSField.setEnabled(false);
       rechercheCPField.setEnabled(false);
       intsconnector.setEditable(true);
       
       //Gestion du bouton de droite
       addButton.setVisible(false);
       applyButton.setVisible(true);
       applyButton.setEnabled(true);
       delButton.setVisible(false);
   }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionPane;
    private javax.swing.JButton addButton;
    private javax.swing.JButton addFourn;
    private javax.swing.JTextArea addrField;
    private javax.swing.JLabel addrLabel;
    private javax.swing.JPanel addrPane;
    private javax.swing.JButton applyButton;
    private javax.swing.JPanel buttonPane;
    private javax.swing.JPanel coord1;
    private javax.swing.JPanel coord2;
    private javax.swing.JTextField cpField;
    private javax.swing.JLabel cpLabel;
    private javax.swing.JButton delButton;
    private javax.swing.JButton delFourn;
    private javax.swing.JButton editFourn;
    private javax.swing.JPanel editPane;
    private javax.swing.JTextField emailField;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JTextField faxField;
    private javax.swing.JLabel faxLabel;
    private javax.swing.JScrollPane fournTabPane;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel listingPane;
    private javax.swing.JTextField rechercheCPField;
    private javax.swing.JLabel rechercheCPLabel;
    private javax.swing.JTextField rechercheRSField;
    private javax.swing.JLabel rechercheRSLabel;
    private javax.swing.JTextField rsField;
    private javax.swing.JLabel rsLabel;
    private javax.swing.JPanel rsPane;
    private javax.swing.JPanel searchPane;
    private javax.swing.JTextField telField;
    private javax.swing.JLabel telLabel;
    private javax.swing.JTextField villeField;
    private javax.swing.JLabel villeLabel;
    // End of variables declaration//GEN-END:variables
    
    
}