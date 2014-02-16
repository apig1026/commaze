/*
 * GreedyBFS searching engine (algorithm) 
 */
package com.fun.commaze.solver;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import com.fun.commaze.environment.*;
/**
 *
 * @author GreedyBFS Team
 */
public class GreedyAgent extends Thread
{
    private Grid grid;
    private Land land;
    private Point start;
    private Point[] exits;
    private ArrayList<Point> trace;
    private Cell[] adjCells; //adjacent cells
    private int posX, posY;
    private boolean found = true;
    private int hideExit = 0;
    private Color visitedColor = Color.DARK_GRAY;
    private final int SHOWTIME = 75; // milliseconds 

    /**
     * Same principle as seekExit(). Used for the thread
     */
    @Override
    public void run()
    {
        initialize();
        double distance = this.next();
        while(distance != 0)
        {
            distance = this.next();
            try{
                Thread.sleep(SHOWTIME);
            }catch(InterruptedException e){}
        }
        if(!found)
            land.setMsg("This maze has no path to exit");
        else
            land.setMsg("Exit reached");
    }
    
    /**
     * Sets Land
     * @param land
     */
    public void setLand(Land land)
    {
        this.land = land;
    }
    
    /**
     * Switches hide exit status 
     */
    public void switchHideExit()
    {
        if(hideExit == 0)
            hideExit = 1;
        else
            hideExit = 0;
    }
    
    /**
     * Switches visited color
     */
    public void switchVisitedColor()
    {
        if(visitedColor == Color.DARK_GRAY)
            visitedColor = Color.pink;
        else
            visitedColor = Color.DARK_GRAY;
    }
    
    /**
     * Seek the path to any exit cells
     */
    public void seekExit()
    {
        while(this.next() != 0)
        {
            // infinite loop until find the exit
        }
        System.out.println("Exit reached");
    }
    
    /**
     * Initialize all variables
     */
    private void initialize()
    {
        grid = Grid.getInstance();
        trace = new ArrayList<Point>();
        adjCells = new Cell[4]; // 0 left, 1 right, 2 up, 3 down
        start = grid.getStartLoc();
        start.setLocation(start.getX()+1, start.getY());
        exits = grid.getExitLocs();
        posX = (int)start.getX();
        posY = (int)start.getY();
    }
    
    /**
     * Check and set values to (left, right, down) adjacent cells
     */
    private void inspect()
    {
        posX = (int)start.getX();
        posY = (int)start.getY();
        this.adjCells[0] = grid.getCellAt(posX, posY-1);
        this.adjCells[1] = grid.getCellAt(posX, posY+1);
        this.adjCells[2] = grid.getCellAt(posX-1, posY);
        this.adjCells[3] = grid.getCellAt(posX+1, posY);
        
        for(int i=0; i < adjCells.length; i++)
        {
            if(adjCells[i] != null)
            {
                if(adjCells[i].isBlocked() || adjCells[i].isEdge() || adjCells[i].isVisited())
                    adjCells[i] = null;
            }
        }
    }
    
    /**
     * Returns closest distance from current cell to all possible exits
     * @param posX of a cell
     * @param posY of a cell
     * @return int: the distance of the closest exit
     */
    private double getCloseDistance(int posX, int posY)
    {
        int exitX, exitY; // indexes for the exit cell
        double x, y, d;
        double min = -1; // minimum distance to the exit cell
        for(int i = 0; i < exits.length; i++)
        {
            exitX = (int)exits[i].getX();
            exitY = (int)exits[i].getY();
            x = Math.pow((posX - exitX), 2.0) + hideExit;
            y = Math.pow((posY - exitY), 2.0);
            d = Math.sqrt(x + y);
            if(min == -1)
                min = d;
            else
                min = Math.min(d, min);
        }
        return min;
    }
    
    /**
     * Move to the next adjacent cell that has closest distance to any of exits
     * @return int: the distance of any closest exit
     */
    private double next()
    {
        double min = -1; // minimum distance to the exit cell
        double d = -1; // temporary closest distance holder
        Point cPoint = new Point(); // temporary closest point holder
        int cIndex = 0; // index of adjCells that has closest distance
        
        this.walk();
        
         // find next cell and its location that has closest distance to any exit
        for(int i = 0; i < adjCells.length; i++)
        {
            if(adjCells[i] != null)
            {
                switch(i)
                {
                    case 0: // left
                        d = this.getCloseDistance(posX, posY-1);
                        cIndex = 0;
                        break;
                    case 1: // right
                        d = this.getCloseDistance(posX, posY+1);
                        cIndex = 1;
                        break;
                    case 2: // up 
                        d = this.getCloseDistance(posX-1, posY);
                        cIndex = 2;
                        break;
                    case 3: // down
                        d = this.getCloseDistance(posX+1, posY);
                        cIndex = 3;
                        break;
                }
                if(min == -1 || d <= min)
                {
                    min = d;
                    cPoint = this.setLocation(cIndex);
                }
            }
        }
        // output message 
        land.setMsg("We are at (" + (int)start.getX() + "," + (int)start.getY() 
                + ")" + " to the cloest exit distance:" + (float)min);
        
        // move to next cell
        if(min == -1)
        {
            // deadend, go back by using recursive call
            try
            {
                grid.getCellAt(posX, posY).setVisited(true);
                grid.getCellAt(posX, posY).setVisitedColor(visitedColor);
                start = trace.remove(trace.size() - 1);
            }catch(ArrayIndexOutOfBoundsException e)
            {
                this.found = false;
                return 0;
            }
            return next();
        }
        else if(min == 0)
        {
            // reach to exit
            start = cPoint;
            this.walk(); // last step to the exit
            return min;
        }
        else // move to the next cloest exit cell
        {
            trace.add(start);
            start = cPoint; // move to next cloest cell
            return min;
        }
    }
    
    /**
     * Sets the current cell to red and visited when walking to the cell 
     */
    private void walk()
    {
        this.inspect(); // must inspect around cells when it is walking
        grid.getCellAt(posX, posY).setVisited(true);
        grid.getCellAt(posX, posY).setVisitedColor(Color.RED);
    }
    
    /**
     * Sets specific location for a point
     * @param index
     * @return point
     */
    private Point setLocation(int index)
    {
        Point point = new Point();
        switch(index)
        {
            case 0: //left
                point.setLocation(posX, posY-1);
                break;
            case 1: //right
                point.setLocation(posX, posY+1);
                break;
            case 2: //up
                point.setLocation(posX-1, posY);
                break;
            case 3: //down
                point.setLocation(posX+1, posY);
                break;
            default: // current
                point.setLocation(posX, posY);   
        }
        return point;
    }
}
