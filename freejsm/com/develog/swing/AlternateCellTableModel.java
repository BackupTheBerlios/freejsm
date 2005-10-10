/*
 * AlternateCellTableModel.java
 *
 * Created on 2 novembre 2004, 15:14
 */

package com.develog.swing;

/**
 *
 * @author  alexandre
 */
import java.awt.Color;
import java.awt.Component;
import java.io.Serializable;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

/**
* class de dafichage pour les cellule d une JTable
* cette class permet un coloriage alternatif du fond
*
* @author Ludo
* @version 1
* @since 21.10.2001
* @see JTable
*/
public class AlternateCellTableModel extends JLabel
    implements TableCellRenderer, Serializable
{

    protected static Border noFocusBorder;
    
    private Color unselectedForeground;
    private Color unselectedBackground;
    private Color unselectedForegroundAlt;
    private Color unselectedBackgroundAlt;
    private Color selectedForeground;
    private Color selectedBackground;
    private int alternateInc;

    public AlternateCellTableModel()
    {
        super();
        noFocusBorder = new EmptyBorder(1, 2, 1, 2);
        setOpaque(true);
        setBorder(noFocusBorder);
        alternateInc = 0;
    }
    
    public void setInterval( int i )
    {
        alternateInc = i;
    }

    public void setColorsSel( Color back, Color fore )
    {
        selectedForeground = fore;
        selectedBackground = back;
    }

    public void setColorsAlt( Color back, Color fore )
    {
        unselectedForegroundAlt = fore;
        unselectedBackgroundAlt = back;
    }
    
    public void setColors( Color back, Color fore )
    {
        unselectedForeground = fore;
        unselectedBackground = back;
    }
    
    public void setForeground(Color c)
    {
        super.setForeground(c);
        unselectedForeground = c;
    }
    
    public void setBackground(Color c)
    {
        super.setBackground(c);
        unselectedBackground = c;
    }

    public void updateUI()
    {
        super.updateUI();
        setForeground(null);
        setBackground(null);
    }
    
    public Component getTableCellRendererComponent(JTable table, Object value,
                            boolean isSelected, boolean hasFocus, int row, int column)
    {
        try
        {
        boolean alternateColor = false; // on demare par la couleur de fon
        // init l interval pour le suivi des ligne en fonction du scroll
        float dec = (float)( row ) / (float)( alternateInc * 2 );
        dec -= (int)dec; // me rest que les parti decimal
        dec *= ( alternateInc * 2 ); // g un resulta entre 0 et ( intervalAlternate * 2 )
        dec += .5; // pour eviter le movai bornage de larondi ;o)
        int interval = (int) dec;
        if ( interval >= alternateInc ) //deja alterner
        {
            if ( alternateInc != 0 ) { alternateColor = !alternateColor;}
            interval -= alternateInc;
        }
        // fin de linit du decalage de l intevale

        if ( isSelected )
        {
            // gestion des couleur de selection
            super.setForeground( ( selectedForeground != null) ? selectedForeground : table.getSelectionForeground() );
            super.setBackground( ( selectedBackground != null) ? selectedBackground : table.getSelectionBackground() );
        }
        else
        {
            // gestion des couleurs alternées en fonction des variable initialisé avan ;o)
            if ( alternateColor )
            {
                super.setForeground( ( unselectedForeground != null) ? unselectedForegroundAlt : table.getForeground() );
                super.setBackground( ( unselectedBackgroundAlt != null) ? unselectedBackgroundAlt : table.getBackground() );
            }
            else
            {
                super.setForeground( ( unselectedForeground != null) ? unselectedForeground : table.getForeground() );
                super.setBackground( ( unselectedBackground != null) ? unselectedBackground : table.getBackground() );
            }
        }

        setFont( table.getFont() );
        if ( hasFocus )
        {
            setBorder( UIManager.getBorder("Table.focusCellHighlightBorder") );
            if ( table.isCellEditable( row, column ) )
            {
                super.setForeground( UIManager.getColor("Table.focusCellForeground") );
                super.setBackground( UIManager.getColor("Table.focusCellBackground") );
            }
        }
        else
        {
            setBorder( noFocusBorder );
        }
       // this.setHorizontalAlignment( JLabel.CENTER );
/*
        // restituer ce morceau de code a la place de la ligne precedente pour alligner les text en fonction du type de la donne
        // a droite pour du numeric et a gauche pour de l alphanum
        //  mais moi je prefere tou centrer  ;o)
        // sa pourrai ossi etre un parametre de la class
        if ( table.getColumnClass( column ).getName().endsWith("String" ) ) // cest une string
        {
            this.setHorizontalAlignment( JLabel.LEFT ); //JLabel.CENTER );
        }
        else
        {
            this.setHorizontalAlignment( JLabel.RIGHT ); //JLabel.CENTER );
        }
*/
        setValue( value );
        }
        catch (Exception e)
        { System.out.println("AlternateCellTableModel getTableCellRendererComponent() : "+e); }
       return this;
    }
    
    protected void setValue( Object value )
    {
        setText( ( value == null ) ? "" : value.toString() );
    }

}