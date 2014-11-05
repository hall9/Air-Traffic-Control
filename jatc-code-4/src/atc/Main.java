/*
 * Main.java
 *
 * Created on May 4, 2006, 4:26 PM
 *
 * Copyright 2006 David Ammerlaan
 *
 *    This file is part of jATC.
 *
 *  jATC is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  jATC is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with jATC; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 */

package atc;

import java.awt.*;
import javax.swing.*;
/**
 *
 * @author David
 */
public class Main {
    
    private static AtcScreen atcscreen;
    private static JFrame frame;
    /** Creates a new instance of Main */
    public Main() {
    }
        
    private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        frame = new JFrame("ATC simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        atcscreen = new AtcScreen();
        atcscreen.setOpaque(true);
        frame.setContentPane(atcscreen);
        
        //Display the window.
        frame.setSize(600,600);
       //atcscreen.setSize(300,300);
        //frame.pack();
        frame.setVisible(true);
    }

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
  
        
        
        
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });  
                
        AirPlane ap1 = new AirPlane("a1", 50, 5, 100, 50000, "bogus1", 200, 200, 200, 10);
        AirPlane ap2 = new AirPlane("a2", 50, 5, 100, 10000, "bogus2", 200, 300, 250, 10);
        
        
        ap1.SetClearAlt(1000);
        ap1.SetClearHeading(1);
        ap1.SetClearSpeed(75);     

                    try
            {
                Thread.sleep(2000);
            }
            catch(Exception e)
            {
                System.out.println("insomnia!");
            }
        
        atcscreen.addplane(ap1);
        atcscreen.addplane(ap2);
        
        while(true)
        {
            try
            {
                Thread.sleep(4000);
            }
            catch(Exception e)
            {
                System.out.println("insomnia!");
            }  
            atcscreen.update(); // this will update the "radar" screen
            frame.repaint();
        }
    }  
}
