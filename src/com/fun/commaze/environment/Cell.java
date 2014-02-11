/*
 * Cell class
 * 
 */

package com.fun.commaze.environment;

import java.awt.Color;

/**
 *
 * @author GreedyBFS Team
 */
public class Cell 
{
        private boolean blocked;
 	private boolean visited;
 	private boolean edge;
        private CellPanel panel;
        
        public Cell()
        {
            this.reset();
            panel = new CellPanel();
            
        }
        
        public Cell(int coordx, int coordy, boolean blocked)
        {
            this();
            this.setBlocked(blocked);
        }
        
        public Cell(int coordx, int coordy, boolean blocked, boolean visited)
        {
            this(coordx, coordy, blocked);
            this.setVisited(visited);
        }
        
        public Cell(int coordx, int coordy, boolean blocked, boolean visited, boolean edge)
        {
            this(coordx, coordy, blocked, visited);
            this.setEdge(edge);
        }
        
        //Initializes the variables so that in some of the constructors
        //the variables do not need to be initiated again        
        public void reset()
        {
            this.setBlocked(false);
            this.setVisited(false);
            this.setEdge(false);
        }
        
        public boolean isBlocked()
        {
            return blocked;
        }
        
        public boolean isVisited()
        {
            return visited;
        }
        
        public boolean isEdge()
        {
            return edge;
        }
        
        public void setBlocked(boolean blocked)
        {
            this.blocked = blocked;
            if(!blocked && panel != null) 
            {
                panel.setBackground(Color.LIGHT_GRAY);
            }
        }
        
        public void setVisited(boolean visited)
        {
            this.visited = visited;
            if(visited && panel != null)
            {
                panel.setBackground(Color.DARK_GRAY);
            }
        }
                
        public void setEdge(boolean edge)
        {
            this.edge = edge;
        }
        
        //This is used so that the starting/ending point is displayed more visibly
        public void setStartEndPos()
        {
            panel.setBackground(Color.BLUE);
        }
        
        public void setVisitedColor(Color c)
        {
            panel.setBackground(c);
        }

        public CellPanel getPanel() {
            return panel;
        }

        public void setPanel(CellPanel panel) {
            this.panel = panel;
        }      
}
