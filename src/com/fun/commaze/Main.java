/*
 * Main class
 * 
 */

package com.fun.commaze;

import com.fun.commaze.environment.Land;
import com.fun.commaze.mapgen.LookInFrontAgent;

/**
 *
 * @author GreedyBFS Team
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        Land ourLand = new Land();
        new LookInFrontAgent().start();
    }
}
