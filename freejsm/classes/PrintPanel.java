/*
 * PrintPanel.java
 *
 * Created on 27 janvier 2004, 22:58
 */

package classes;
import java.awt.print.*;
import java.awt.*;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.Dimension;
import java.beans.*;
import java.io.Serializable;
/**
 *
 * @author  alexandre
 *
 */
public class PrintPanel extends javax.swing.JPanel implements Printable {
    
    PrinterJob mPrinterJob;
    PageFormat mPageFormat;
    double mdPreviewScale = 0.5d;
    final static int miBorderSize = 50;

    
    /** Creates a new instance of PrintPanel */
    public PrintPanel() {
        super();
        
        // Initialisation des paramétres de l'imprimante et de mise en page
        mPrinterJob = mPrinterJob.getPrinterJob();
        mPageFormat = mPrinterJob.defaultPage();
    
        // Couleur de fond du panneau
        setBackground(Color.darkGray);
        
        // Calcul de la taille optimale d'affichage du panneau
        setPreferredSize(new Dimension((int)((mPageFormat.getWidth() + 2 * miBorderSize ) *
        mdPreviewScale), (int)((mPageFormat.getHeight() + 2 * miBorderSize ) * mdPreviewScale)));
    }
       
    /** Ceci indique au systeme d'impression qu'il reste encore des pages du livre à imprimer */
    public void print(){
  
        if(mPrinterJob.printDialog()){
            mPageFormat = mPrinterJob.validatePage(mPageFormat);
        repaint();
        }
        //Création du livre.
        Book book = new Book();
        //Ajout de l'image de l'aperçu
        book.append(this,mPageFormat);
        //Transmition du livre au job d'impression
        mPrinterJob.setPageable(book);
        try{
            mPrinterJob.print();
            }catch(Exception PrintException){System.out.println(""+PrintException);}
        }
    
    public void paintComponent(Graphics g){
    
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
        
        //Reglage de l'échelle souhaitée
        g2.scale(mdPreviewScale,mdPreviewScale);
        
        //Dessin du papier
        g2.setPaint(Color.white);
        g2.fillRect(miBorderSize,miBorderSize,(int)mPageFormat.getWidth(),(int)mPageFormat.getHeight());
       
        //Fixer l'origine du papier
        g2.translate(miBorderSize,miBorderSize);
       
        //Dessiner les marges de la zone d'impression
        g2.setPaint(Color.lightGray);
        
        g2.drawLine( 0 , (int)mPageFormat.getImageableY() - 1, 
                         (int)mPageFormat.getWidth() - 1, 
                         (int)mPageFormat.getImageableY() -1);
        g2.drawLine( 0 , (int)(mPageFormat.getImageableY() + mPageFormat.getImageableHeight()),
                         (int)mPageFormat.getWidth() -1,
                         (int)(mPageFormat.getImageableY() + mPageFormat.getImageableHeight()));
        g2.drawLine(     (int)mPageFormat.getImageableX() - 1,0,
                         (int)mPageFormat.getImageableX() - 1,
                         (int)mPageFormat.getHeight() - 1);
        g2.drawLine(     (int)(mPageFormat.getImageableX() + mPageFormat.getImageableWidth()), 0, 
                         (int)(mPageFormat.getImageableX() + mPageFormat.getImageableWidth()), 
                         (int)mPageFormat.getHeight() - 1 );
        //Deplacement de l'origine pour la seconde fois 
        g2.translate(mPageFormat.getImageableX(), mPageFormat.getImageableY());
        
        //Limitation de la zone de sortie de l'écran d'impression
        g2.setClip( 0 , 0 , 
                   (int)mPageFormat.getImageableWidth(),
                   (int)mPageFormat.getImageableHeight());
        
        // Sortie de l'image qui doit s'affichier dans l'aperçu et s'imprimer 
        drawMyGraphics(g2);
    }
     
    public int print(java.awt.Graphics g, PageFormat pageFormat, int iPageIndex) throws PrinterException {
    
       int iPrintState = Printable.NO_SUCH_PAGE;
       if (iPageIndex == 1){
           Graphics2D g2 = (Graphics2D) g;
           
           //Déplacer l'origine et limiter la zone de sortie
           g2.translate((int)mPageFormat.getImageableX(),(int)mPageFormat.getImageableY());
           g2.setClip( 0 , 0, (int)mPageFormat.getImageableWidth(),(int)mPageFormat.getHeight());
           
           //Sortie de l'image
           drawMyGraphics(g2);
           
           iPrintState = Printable.PAGE_EXISTS;
       }
       return iPrintState;
    }
    
    void drawMyGraphics(Graphics2D g2){
    }
    
    //Demande à l'image de zoomer X2.
    public void ZoomIn(){
        if (mdPreviewScale < 2){
            mdPreviewScale *= 2;
            repaint();
        }
    }
    
    //Demande à l'image de déZoomer X0.25
    public void ZoomOut(){
        if (mdPreviewScale > 0.25){
            mdPreviewScale /= 2;
            repaint();
        }
    }
    
}
    
