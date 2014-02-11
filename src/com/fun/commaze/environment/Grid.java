/*
 * Grid class
 * 
 */

package com.fun.commaze.environment;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import javax.swing.JPanel;

/**
 *
 * @authors GreedyBFS Team
 */
public class Grid extends JPanel 
{
    public Grid()
    {
        //Calls the constructor with X columns and Y rows
        this(52, 102);
    }
    
    public Grid(int inputX, int inputY)
    {
        super(new GridLayout(inputX, inputY));
        maze = new Cell[inputX][inputY];
        sizeX = inputX;
        sizeY = inputY;
        //mazePanel = new CellPanel[inputX][inputY];
        //setGridSize(inputX, inputY);
        createGrid();
    }
    
    /**
     * This method initializes every single cell in the grid and sets 
     * the boundry cells property
     */
    private void createGrid()
    {
        for(int xx=0;xx<maze.length;xx++)
        {            
            for(int yy=0;yy<maze[xx].length;yy++)
            {
                //Loops through to initialize individual cells
                //Special case: boundary cells are identified with an extra
                //              flag so a seperate constructor is called
                if(xx==0 || yy==0 || xx==maze.length-1 || yy==maze[xx].length-1)
                {
                    //boundary cells (edge)
                    maze[xx][yy] = new Cell(xx, yy, true, false, true);
                    maze[xx][yy].getPanel().setBackground(Color.BLACK);
                    maze[xx][yy].getPanel().setToolTipText(xx + "X" + yy);
                    this.add(maze[xx][yy].getPanel());
                }
                else
                {
                    //normal non-boundary cells
                    maze[xx][yy] = new Cell(xx, yy, true, false);
                    maze[xx][yy].getPanel().setToolTipText(xx + "X" + yy);
                    this.add(maze[xx][yy].getPanel());
                }
            }
        }
    }
    
    /**
     * Returns a reference to the cell at the specified location
     * @Return Cell 
     * If no such cell exist it returns null
     */
    public Cell getCellAt(int posX, int posY)
    {
        Cell retCell = null;
        try
        {
            retCell = maze[posX][posY];
        } 
        catch (IndexOutOfBoundsException e)
        {
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        };
        return retCell;
    }
    
    /**
     * 
     * This function will reset the visited status for the entire maze
     */    
    public void resetVisited()
    {
        for(int xx=0; xx<sizeX; xx++)
        {
            for(int yy=0; yy<sizeY; yy++)
            {
                maze[xx][yy].setVisited(false);
            }
        }
    }
    
    /**
     * 
     * @return index of the start cell 
     */
    public Point getStartLoc()
    {
        return startLoc;
    }
 
    /**
     * 
     * This function generates the starting location 
     */    
    public void generateStartLoc()
    {
        int posY;
        
        posY = ((int)Math.floor(sizeX*Math.random())+1);
        startLoc = new Point(0,posY);
        maze[0][posY].setStartEndPos();        
    }
    
    /**
     * 
     * @return indexes of the end cells
     */
    public Point[] getExitLocs()
    {
        return exitLocs;
    }
    
     /**
     * 
     * Generates the exit locations
     */
    public void generateExitLocs()
    {
        int bottomLoc;
        int numExit = (int)(Math.random()*10)+1; //Maximum of 10 exits
        int counter = 0;
        int yPos = 1;                           //Position 0 is for edges
        double chance = 0.8;
        double exitBar;
        
        boolean pointExist;
        Point tempPoint;
        
        bottomLoc = sizeX-2;
        exitLocs = new Point[numExit];
        
        //Loop until we have generated an amount of exits
        //equal to the amount we set out to generate
        while(true)
        {
            if(maze[bottomLoc][yPos].isBlocked() == false)
            {
                tempPoint = new Point(bottomLoc, yPos);
                
                if(counter == 0)
                {
                    exitBar = Math.random();    //Randomizes to see whether
                    if(chance < exitBar)       //this point will be made into an exit
                    {
                        exitLocs[counter] = tempPoint;
                        maze[bottomLoc+1][yPos].setStartEndPos();
                        counter = counter + 1;
                    }                 
                }
                else
                {                
                    pointExist = false;
                    
                    for(int xx = 0; xx < counter; xx++)
                    {
                        if(exitLocs[xx].getY() == (double)yPos)
                        {
                            //this point is already in the array thus ignored
                            pointExist = true;
                            break;
                        }
                    }
                    
                    if(!pointExist)
                    {
                        exitBar = Math.random();    //Randomizes to see whether
                        if(chance < exitBar)       //this point will be made into an exit
                        {
                            exitLocs[counter] = tempPoint;
                            maze[bottomLoc+1][yPos].setStartEndPos();
                            counter = counter + 1;
                        }                        
                    }
                }
            }
            
            if(counter == numExit)
            {
                for(int xx = 0; xx < counter; xx++)
                {
                    System.out.println("Exit "+xx+"'s xPos: "+exitLocs[xx].getX()+" yPos: "+exitLocs[xx].getY());
                }
                break;  //Exits the loop as we have the amount of exits we want
            }
            
            yPos = yPos + (int)Math.floor(3*Math.random())+1;
            if(yPos >= sizeY)
            {
                //The +1 is to avoid edge condition
                yPos = yPos - sizeY + 1;
            }
        }
    } 
    /**
     * Rerurns an instance of the grid, if null creates a new one
     */
    public static Grid getInstance()
    {
        if(myInstance == null) 
        {
            myInstance = new Grid();
        }
        return myInstance;
    }
    
    //private CellPanel[][] mazePanel;
    private Cell[][] maze;
    private static Grid myInstance;
    
    //holder for starting/ending positions
    private Point startLoc;
    private Point[] exitLocs;
    
    //values for the width/height of the grid
    private int sizeX;
    private int sizeY;
}
