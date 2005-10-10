/*
 * CustomCellRenderer.java
 *
 * Created on 29 octobre 2002, 15:15
 */

package classes;


import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;

public class CustomCellRenderer
		extends		JLabel
		implements	TreeCellRenderer
{
   private ImageIcon		bijouterie;
   private ImageIcon		clients;
   private ImageIcon		cmd;
   private ImageIcon		rep;
   private ImageIcon		factures;
   private ImageIcon		inventaire;
   
   private boolean		bSelected;


	public CustomCellRenderer()
	{
		// Chargement des images
		bijouterie = new ImageIcon(getClass().getResource("/IconesStock/home.png"));
		clients = new ImageIcon(getClass().getResource("/IconesStock/User.png"));
		factures = new ImageIcon(getClass().getResource("/IconesStock/achat.png"));
		cmd = new ImageIcon(getClass().getResource("/IconesStock/cmd.png"));
		rep = new ImageIcon(getClass().getResource("/IconesStock/rep.png"));
                inventaire = new ImageIcon(getClass().getResource("/IconesStock/inventaire.png"));
        }  

	public Component getTreeCellRendererComponent( JTree tree,
					Object value, boolean bSelected, boolean bExpanded,
				        boolean bLeaf, int iRow, boolean bHasFocus )
	{
		// Find out which node we are rendering and get its text
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
		String	labelText = (String)node.getUserObject();

		this.bSelected = bSelected;
		
		// Set the correct foreground color
		if( !bSelected )
			setForeground( Color.black );
		else
			setForeground( Color.white );

		// Determine the correct icon to display
		if( labelText.equals( "Bijouterie" ) )
			setIcon( bijouterie );
		else if( labelText.equals("Clients" ) )
			setIcon( clients );
                else if (labelText.equals("Commandes") )
                        setIcon(cmd );
                else if (labelText.equals("R�parations"))
                        setIcon(rep);
                else if (labelText.equals("Factures"))
                        setIcon(factures);
                else if (labelText.equals("Inventaire"))
                        setIcon(inventaire);

		// Add the text to the cell
		setText( labelText );
                setFont(new java.awt.Font("Arial", 0, 10));

		return this;
	}

	// This is a hack to paint the background.  Normally a JLabel can
	// paint its own background, but due to an apparent bug or
	// limitation in the TreeCellRenderer, the paint method is
	// required to handle this.
	public void paint( Graphics g )
	{
		Color		bColor;
		Icon		currentI = getIcon();

		// Set the correct background color
		bColor = bSelected ? SystemColor.textHighlight : Color.white;
		g.setColor( bColor );

		// Draw a rectangle in the background of the cell
		g.fillRect( 0, 0, getWidth() - 1, getHeight() - 1 );

		super.paint( g );
	}

}

