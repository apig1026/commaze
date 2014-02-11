/*
 * Land class (GUI)
 * 
 */

package com.fun.commaze.environment;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JFrame;

import com.fun.commaze.solver.GreedyAgent;

/**
 *
 * @author GreedyBFS Team
 */
public class Land extends JFrame implements ActionListener, ItemListener{
   // private Grid grid;
    private Panel p;
    private Button b;
    private Checkbox cbx, cbx2;
    private Label msg;
    private GreedyAgent greed = new GreedyAgent();
    public Land() {
        super("WELCOME TO CORNMAZE!");
        p = new Panel();
        b = new Button("Solve it");
        cbx = new Checkbox("Hide exits");
        cbx2 = new Checkbox("Highlight visited");
        msg = new Label("[=========================Message Board"
                        +"=========================]");
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(600,200));
        this.setBackground(Color.YELLOW);
        p.setLayout(new FlowLayout(FlowLayout.LEFT));
        p.add(new Label("Develope Team: GreedyBFS"));
        p.add(b);
        p.add(cbx);
        p.add(cbx2);
        p.add(msg);
        this.add("North", Grid.getInstance());
        this.add("South", p);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        greed.setLand(this);
        b.addActionListener(this);
        cbx.addItemListener(this);
        cbx2.addItemListener(this);
    }
    public void setMsg(String message)
    {
        msg.setText(message);
    }
    public static void main(String[] args) 
    {
        new Land();
    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == b)
        {
             greed.start();
             b.disable();
             cbx.disable();
             cbx2.disable();
        }
    }
    public void itemStateChanged(ItemEvent evt)
    {   
        if (evt.getSource() == cbx)
        {
             greed.switchHideExit();
        }
        else if(evt.getSource() == cbx2)
        {
             greed.switchVisitedColor();
        }
    }
    
}
