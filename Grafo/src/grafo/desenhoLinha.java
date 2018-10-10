/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafo.lib;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Jefferson
 */
public class desenhoLinha extends JPanel{

    public desenhoLinha() {
    }
    
    
    protected void paintComponent(Graphics g){
        super.paintComponents(g);
      
        g.drawLine(30, 40,100, 100);
        g.setColor(Color.BLUE);
        g.fillRect(25,25,100,30);
        
        
    }
    
}
