/*
 * LabelComponent.java
 *
 * Created on May 23, 2006, 12:22 PM
 *
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

 */

package atc;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author David
 */
public class LabelComponent extends JLabel implements MouseListener, MouseMotionListener {
    
    private AirPlane ap;
    
    /**
     * Creates a new instance of LabelComponent
     */
    public LabelComponent( AirPlane airplane) {
        addMouseListener( this );
        addMouseMotionListener( this );
        this.setText("hello there");
        setToolTipText("callsign: "+airplane.getCallSign());
        ap = airplane; 
    }
    
    public void mouseDragged(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) 
    {
        if (e.getButton() == e.BUTTON1) //left click?
        {
            ap.SetClearHeading(ap.getClHeading()-10);
        }
        else    // right click
        {
            ap.SetClearHeading(ap.getClHeading()+10);
        }
    }
    public void mouseMoved(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public void paint(Graphics g) {
        
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.setColor(Color.BLUE);
        g2d.drawRect(0,0, 5, 5);
        g2d.setColor(Color.BLACK);
        g2d.drawString(ap.getCallSign(),15,10);
        g2d.drawString(ap.getCuAlt()/100 + "  " + ap.getClAlt()/100, 15, 20);
        g2d.drawString("H"+ap.getCuHeading()+ "C"+ap.getClHeading(), 15, 30);
          
        g2d.dispose();
    }
        
}
