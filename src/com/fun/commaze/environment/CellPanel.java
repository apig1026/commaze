/*
 * purpose: An object of this class is responsible for the graphical 
 * representation of a cell. 
 */

package com.fun.commaze.environment;

import java.awt.Color;
import javax.swing.JPanel;

/**
 *
 * @author GreedyBFS Team
 */
public class CellPanel extends JPanel
{
    public CellPanel(int width, int height)
    {
        this.setSize(width, height);
        this.setBackground(Color.GREEN);
    } 
    
    public CellPanel() 
    {
        super();
        this.setBackground(Color.GREEN);
    }
}
