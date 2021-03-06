/*
 * PaneTVA.java
 *
 * Created on 16 d�cembre 2004, 15:13
 */

package forms;


import com.develog.utils.SmartConnector;
// import com.develog.utils.SmartCube;
import forms.PrincipaleGUI;
import classes.JournalizedSmartCube;
// import com.develog.utils.SmartCube;
import com.develog.swing.SmartJTable;
import com.develog.database.Parametre;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author  nahuel
 */
public class PaneTVA extends javax.swing.JPanel {
    
    private Vector taxGUI = new Vector();
    private Connection connex = null;
    private SmartConnector intsconnector;
    private JournalizedSmartCube intsc;
    // private SmartCube intsc;
    public  SmartJTable intSJT = null;
    private boolean enModification = false;
    private PrincipaleGUI papa;
    
    /** Creates new form PaneTVA */
    public PaneTVA(Connection connect, PrincipaleGUI papa) {
        connex = connect;
        this.papa = papa;
        initComponents();
        
        initCube();
        initTable();
        initDoubleClick();
        etatDeBase();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jLabel1 = new javax.swing.JLabel();
        searchPane = new javax.swing.JPanel();
        designLabel = new javax.swing.JLabel();
        designField = new javax.swing.JTextField();
        tauxLabel = new javax.swing.JLabel();
        tauxField = new javax.swing.JTextField();
        actionPane = new javax.swing.JPanel();
        addTaux = new javax.swing.JButton();
        delTaux = new javax.swing.JButton();
        editTaux = new javax.swing.JButton();
        tvaTabPane = new javax.swing.JScrollPane();

        jLabel1.setText("jLabel1");

        setLayout(new java.awt.BorderLayout());

        searchPane.setBorder(new javax.swing.border.EtchedBorder());
        searchPane.setPreferredSize(new java.awt.Dimension(504, 52));
        designLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        designLabel.setText("D\u00e9signation :");
        designLabel.setMaximumSize(new java.awt.Dimension(75, 26));
        designLabel.setMinimumSize(new java.awt.Dimension(75, 26));
        designLabel.setPreferredSize(new java.awt.Dimension(75, 26));
        searchPane.add(designLabel);

        designField.setMaximumSize(new java.awt.Dimension(2147483647, 19));
        designField.setMinimumSize(new java.awt.Dimension(100, 19));
        designField.setName("TVA_DESIGN");
        designField.setPreferredSize(new java.awt.Dimension(100, 19));
        searchPane.add(designField);

        tauxLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        tauxLabel.setText("Taux ( % ) :");
        searchPane.add(tauxLabel);

        tauxField.setColumns(5);
        tauxField.setMaximumSize(new java.awt.Dimension(4, 19));
        tauxField.setName("TVA_TX");
        searchPane.add(tauxField);

        addTaux.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/develog/icones/stock_apply.png")));
        addTaux.setToolTipText("Ajouter");
        addTaux.setMaximumSize(new java.awt.Dimension(50, 26));
        addTaux.setMinimumSize(new java.awt.Dimension(50, 26));
        addTaux.setPreferredSize(new java.awt.Dimension(50, 26));
        addTaux.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTauxActionPerformed(evt);
            }
        });

        actionPane.add(addTaux);

        delTaux.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/develog/icones/stock_cancel_20.png")));
        delTaux.setToolTipText("Supprimer");
        delTaux.setMaximumSize(new java.awt.Dimension(50, 26));
        delTaux.setMinimumSize(new java.awt.Dimension(50, 26));
        delTaux.setPreferredSize(new java.awt.Dimension(50, 26));
        delTaux.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delTauxActionPerformed(evt);
            }
        });

        actionPane.add(delTaux);

        editTaux.setFont(new java.awt.Font("Dialog", 0, 12));
        editTaux.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/develog/icones/stock_ok_20.png")));
        editTaux.setToolTipText("Editer");
        editTaux.setMaximumSize(new java.awt.Dimension(50, 26));
        editTaux.setMinimumSize(new java.awt.Dimension(50, 26));
        editTaux.setPreferredSize(new java.awt.Dimension(50, 26));
        editTaux.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editTauxActionPerformed(evt);
            }
        });

        actionPane.add(editTaux);

        searchPane.add(actionPane);

        add(searchPane, java.awt.BorderLayout.NORTH);

        add(tvaTabPane, java.awt.BorderLayout.CENTER);

    }//GEN-END:initComponents

    private void addTauxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addTauxActionPerformed
        etatDeAjout();
    }//GEN-LAST:event_addTauxActionPerformed

    private void editTauxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editTauxActionPerformed
        //Ajoute une ligne
        Hashtable ID = new Hashtable();
        try{ 
            if ( enModification == false )
                intsconnector.genNewRowWithID();

            String requetteANC = intsc.getRequette(enModification, "TVA", "TVA_ID", intsc.getCubeInt("TVA_ID"));
            
            intsconnector.getValuesFromUI();
            Hashtable tva_activ = new Hashtable();
            tva_activ.put(new String("TVA_ACTIV"),new Boolean(true));
            intsc.setCubeData(tva_activ);
            intsconnector.addNewRowWithID();
            // intsc.getSpecific(intSJT.getSelectedRow()+1);
            intsc.commitDBTransaction(enModification, papa.userLogged.getID(), "TVA", intsc.getCubeInt("TVA_ID"), "TVA_ID", requetteANC);
            
            // intsc.commitDBTransaction();
            
            //Remise � blanc
            designField.setText("");
            tauxField.setText("");     
            intSJT.loadData();
            intSJT.change();
            etatDeBase();
            
            enModification = false;
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"Le taux de TVA n'a pu �tre ins�r�","Erreur d'ajout",JOptionPane.ERROR_MESSAGE,null);
            etatDeBase();
            designField.setText("");
            tauxField.setText("");
            enModification = false;
            try{intSJT.loadData();}catch(Exception e2){System.out.println("[PaneTVA][editTauxActionPerformed] Exception : " + e2);}
            System.out.println("[PaneTVA][editTauxActionPerformed] Exception � l'insertion : "+e);
        }
    }//GEN-LAST:event_editTauxActionPerformed

    private void delTauxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delTauxActionPerformed
       if ( JOptionPane.showConfirmDialog(this,"Supprimer le taux de TVA ?","Suppression",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null) == 0)
            try{
                // intsconnector.deleteRow();
	        intsconnector.getValuesFromUI();       
        	

                String requetteANC = intsc.getRequette(enModification, "TVA", "TVA_ID", intsc.getCubeInt("TVA_ID"));
                
                // on set TVA ACTIV a 0
                Hashtable tva_activ = new Hashtable();
                tva_activ.put(new String("TVA_ACTIV"),new Boolean(false));
                intsc.setCubeData(tva_activ);
                
                intsconnector.addNewRowWithID();
                
                intsc.getSpecific(intSJT.getSelectedRow()+1);
                
                
                intsc.commitDBTransaction(true, papa.userLogged.getID(), "TVA", intsc.getCubeInt("TVA_ID"), "TVA_ID", requetteANC);
                //intsc.commitDBTransaction();
                intSJT.loadData();
                //Remise � blanc
                designField.setText("");
                tauxField.setText("");
                
                intSJT.change();
                etatDeBase();
            }catch(Exception e){
                System.out.println("[PaneTVA][delTauxActionPerformed] Exception :" + e);
                e.printStackTrace();
                JOptionPane.showMessageDialog(this,"Impossible de supprimer le taux de TVA","Erreur de suppression",JOptionPane.ERROR_MESSAGE,null);
                designField.setText("");
                tauxField.setText("");
                try{intSJT.loadData();}catch(Exception e2){System.out.println("[PaneTVA][delTauxActionPerformed] Exception : " + e2);}
                etatDeBase(); 
            }
    }//GEN-LAST:event_delTauxActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionPane;
    private javax.swing.JButton addTaux;
    private javax.swing.JButton delTaux;
    private javax.swing.JTextField designField;
    private javax.swing.JLabel designLabel;
    private javax.swing.JButton editTaux;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel searchPane;
    private javax.swing.JTextField tauxField;
    private javax.swing.JLabel tauxLabel;
    private javax.swing.JScrollPane tvaTabPane;
    // End of variables declaration//GEN-END:variables

    private void initCube(){
        try{

            intsc = new JournalizedSmartCube(connex);
            // intsc = new SmartCube(connex);
            intsc.setQuery("SELECT TVA_DESIGN, TVA_TX, TVA_ACTIV, TVA_ID FROM TVA WHERE TVA_ACTIV = 1 ORDER BY TVA_TX");
            intsc.loadCube();
            taxGUI.add(searchPane);
            intsconnector = new SmartConnector(intsc,taxGUI);
            intsconnector.setEditable(false);
            }catch(Exception e){ 
                System.out.println(e);
            }
    }
    
    private void initTable(){ 
    try{
          Hashtable mappingColName = new Hashtable();
          HashMap   invisibleColName  = new HashMap();
          
          /** On renomme les colonnes qui vont servir */
          
          
          mappingColName.put("TVA_DESIGN","D�signation");
          mappingColName.put("TVA_TX","Taux");
                    
          /** On masque celle qui servent � rien */
          invisibleColName.put("TVA_ACTIV", "ACTIV");
          invisibleColName.put("TVA_ID", "ID");
          
          intSJT = new SmartJTable(intsc, mappingColName, invisibleColName);
          tvaTabPane.setViewportView(intSJT);
    }catch(Exception e){
        System.out.println(e);};  
    }

   private void initDoubleClick() {
       intSJT.addMouseListener(new MouseAdapter(){
          public void mouseClicked(MouseEvent e){
            if (e.getClickCount() == 1)
            {
              try{
                    etatDeSuppression();
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
   
      private void etatDeAjout(){
       delTaux.setEnabled(false);
       addTaux.setEnabled(false);
       editTaux.setEnabled(true);
       intsconnector.setEditable(true);
       designField.setText(""); 
       tauxField.setText(""); 
   }
   
   
   private void etatDeBase(){
       delTaux.setEnabled(false);
       addTaux.setEnabled(true);
       editTaux.setEnabled(false);
       intsconnector.setEditable(false);
   }
   
   public void etatDeModification(){
       editTaux.setEnabled(true);
       delTaux.setEnabled(false);
       addTaux.setEnabled(false);
       intsconnector.setEditable(true);
   }
   
   public void etatDeSuppression(){
       editTaux.setEnabled(false);
       delTaux.setEnabled(true);
       addTaux.setEnabled(true);
       intsconnector.setEditable(false);
   }
}
