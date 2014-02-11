/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * <b>
 *               [4]
 *            [1][2][3]
 *                ^
 * </b><i>Basic representation of agents view. In this case agent 
 * is looking up.</i>
 */

package com.fun.commaze.mapgen;

import java.awt.Point;
import java.util.Random;

import com.fun.commaze.environment.Cell;
import com.fun.commaze.environment.Grid;

/**
 *
 * @author GreedyBFS Team
 */
public class LookInFrontAgent extends Thread{
    private Point location;
    private Grid grid;
    boolean roomToExpand = true;

    private enum direction { north, east, south, west };
    
    
    @Override
    public void run() 
    {
        initialize();
        
        while(roomToExpand) {
            if(!moveForward()) {
                System.out.println("Gotta go back");
                if(!backTrack())
                {
                    //There is nowhere left to go
                    roomToExpand = false;
                    System.out.println("IM DONE! at: " + location.x  
                            +"X" + location.y);
                    break;
                }
            }
        }
        
        //To generate the location of the exits
        grid.generateExitLocs();
        
        //Resets all "visited" nodes (nodes used in map generation) to unvisited status
        grid.resetVisited();
    }
    
    private void initialize()
    {
        grid = Grid.getInstance();
        grid.generateStartLoc();
        location = grid.getStartLoc();
    }
    
    /** 
     * Tries to move North , 
     *               [4]
     *            [1][2][3]
     *                ^
     * if unsuccessful it returns false otherwise it moves to cell #2 
     */
    private boolean moveNorth() 
    {
        if(//if Cell#1 exist it must be blocked
           (grid.getCellAt(location.x - 1, location.y - 1) == null ||
           grid.getCellAt(location.x - 1, location.y - 1).isBlocked()) &&
           
           //Cell #2 has to exist and must be currently blocked
           (grid.getCellAt(location.x , location.y - 1) != null &&
           grid.getCellAt(location.x , location.y - 1).isBlocked()) &&
           
           //If cell #3 exist it must be blocked
           (grid.getCellAt(location.x + 1, location.y - 1) == null ||
           grid.getCellAt(location.x + 1, location.y - 1).isBlocked()) &&
           
           //if cell #4 exist it must be blocked
           (grid.getCellAt(location.x , location.y - 2) == null ||
           grid.getCellAt(location.x , location.y - 2).isBlocked()) 
           ) 
        {
            //checks whether the space to be moved toward is an edge
            if(grid.getCellAt(location.x, location.y - 1).isEdge())
            {
                return false;
            }
            else
            {
                moveTo(location.x, location.y - 1);
                return true;
            }
        } else {
            return false;
        }
    }
    
    /** Tries to move East , 
     *      [1]
     *     >[2][4]
     *      [3] 
     * if unsuccessful it returns false otherwise it moves to cell #2
     */
    private boolean moveEast()
    {
        if(//if Cell#1 exist it must be blocked
           (grid.getCellAt(location.x + 1, location.y - 1) == null ||
           grid.getCellAt(location.x + 1, location.y - 1).isBlocked()) &&
           
           //Cell #2 has to exist and must be currently blocked
           (grid.getCellAt(location.x + 1 , location.y) != null &&
           grid.getCellAt(location.x + 1 , location.y).isBlocked()) &&
           
           //If cell #3 exist it must be blocked
           (grid.getCellAt(location.x + 1, location.y + 1) == null ||
           grid.getCellAt(location.x + 1, location.y + 1).isBlocked()) &&
           
           //if cell #4 exist it must be blocked
           (grid.getCellAt(location.x + 2, location.y) == null ||
           grid.getCellAt(location.x + 2, location.y).isBlocked()) 
           ) 
        {
            //checks whether the space to be moved toward is an edge
            if(grid.getCellAt(location.x + 1, location.y).isEdge())
            {
                return false;
            }
            else
            {            
                moveTo(location.x + 1, location.y);
                return true;
            }
        } else {
            return false;
        }
    }
    
    /** Tries to move South, 
     *          v
     *      [1][2][3]
     *         [4] 
     * 
     * if unsuccessful it returns false otherwise it moves to cell #2
     */
    private boolean moveSouth()
    {
        if(//if Cell#1 exist it must be blocked
           (grid.getCellAt(location.x - 1, location.y + 1) == null ||
           grid.getCellAt(location.x - 1, location.y + 1).isBlocked()) &&
           
           //Cell #2 has to exist and must be currently blocked
           (grid.getCellAt(location.x, location.y + 1) != null &&
           grid.getCellAt(location.x, location.y + 1).isBlocked()) &&
           
           //If cell #3 exist it must be blocked
           (grid.getCellAt(location.x + 1, location.y + 1) == null ||
           grid.getCellAt(location.x + 1, location.y + 1).isBlocked()) &&
           
           //if cell #4 exist it must be blocked
           (grid.getCellAt(location.x, location.y + 2) == null ||
           grid.getCellAt(location.x, location.y + 2).isBlocked()) 
           ) 
        {
            //checks whether the space to be moved toward is an edge
            if(grid.getCellAt(location.x, location.y + 1).isEdge())
            {
                return false;
            }
            else
            {            
                moveTo(location.x, location.y + 1);
                return true;
            }
        } else {
            return false;
        }
    }

    /** Tries to move West,
     *           [1]
     *        [4][2]<
     *           [3]       
     * if unsuccessful it returns false otherwise it moves to cell #2 
     */
    private boolean moveWest()
    {
        if(//if Cell#1 exist it must be blocked
           (grid.getCellAt(location.x - 1, location.y - 1) == null ||
           grid.getCellAt(location.x - 1, location.y - 1).isBlocked()) &&
           
           //Cell #2 has to exist and must be currently blocked
           (grid.getCellAt(location.x - 1, location.y) != null &&
           grid.getCellAt(location.x - 1, location.y).isBlocked()) &&
           
           //If cell #3 exist it must be blocked
           (grid.getCellAt(location.x - 1, location.y + 1) == null ||
           grid.getCellAt(location.x - 1, location.y + 1).isBlocked()) &&
           
           //if cell #4 exist it must be blocked
           (grid.getCellAt(location.x - 2, location.y) == null ||
           grid.getCellAt(location.x - 2, location.y).isBlocked()) 
           ) 
        {
            //checks whether the space to be moved toward is an edge
            if(grid.getCellAt(location.x - 1, location.y).isEdge())
            {
                return false;
            }
            else
            {
                moveTo(location.x - 1, location.y);
                return true;
            }
        } else {
            return false;
        }
    }
    
    /**Takes care of moving this agent to the new location and 
     * setting the parameters of the Cell which she is going to.
     */
    private void moveTo(int x, int y)
    {
        location = new Point(x,y);
        grid.getCellAt(x, y).setBlocked(false);
    }
    
    /**
     * This method tries to expand the path to one of the maximum 
     * of four directions around the current location.
     * If expansion is not possible it returns false.
     */
    private boolean moveForward() 
    {
        Random r = new Random();
        int direct = r.nextInt(4);
            boolean movedForward = false;
            switch(direct) {
                case 0:
                    if (moveNorth()) {
                        movedForward = true;
                    } else {
                        movedForward = (moveSouth()||moveWest()||moveEast());
                    }
                    break;
                case 1:
                    if (moveWest()) {
                        movedForward = true;
                    } else {
                        movedForward = (moveNorth()||moveSouth()||moveEast());
                    }
                    break;
                case 2:
                    if (moveEast()) {
                        movedForward = true;
                    } else {
                        movedForward = (moveSouth()||moveNorth()||moveWest());
                    }
                    break;
                case 3:
                    if (moveSouth()) {
                        movedForward = true;
                    } else {
                        movedForward = (moveEast()||moveNorth()||moveWest());
                    }
                    break;  
            }
            return movedForward;
    }
    
    /**
     * This method tries to find an unvisited adjacent cell and moves there 
     * if one exist and marks it as visited.
     * Otherwise it return false which indicated end of job for this agent.
     */
    private boolean backTrack()
    {
        grid.getCellAt(location.x, location.y).setVisited(true);
        
        Cell north = grid.getCellAt(location.x, location.y - 1);
        Cell east  = grid.getCellAt(location.x + 1, location.y);
        Cell south = grid.getCellAt(location.x, location.y + 1);
        Cell west  = grid.getCellAt(location.x - 1, location.y);
        if(north != null && !north.isBlocked() && !north.isVisited()) {
            moveTo(location.x, location.y - 1);
        } else if(east != null && !east.isBlocked() && !east.isVisited()) {
            moveTo(location.x + 1, location.y);
        } else if(south != null && !south.isBlocked() && !south.isVisited()) {
            moveTo(location.x, location.y + 1);
        } else if(west != null && !west.isBlocked() && !west.isVisited()) {
            moveTo(location.x - 1, location.y);
        } else {
            return false;
        }
        return true;
    }
}
