/*
 * trailComponent.java
 *
 * Created on May 23, 2006, 12:22 PM
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
public class TrailComponent extends JLabel {
    
    private int posx;                 
    private int posy;
    
    /** Creates a new instance of ComponentTest */
    public TrailComponent(int x, int y) 
    {
        posx = x; posy = y;
    }
    
    
    public int getPosX()
    {
        return posx;
    }
    public int getPosY()
    {
        return posy;
    }
    public void setPosX(int x)
    {
        posx=x;
    } 
    public void setPosY(int y)
    {
        posy=y;
    }

    public void paint(Graphics g) {
        
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawRect(0,0, 5, 5);
        g2d.dispose();
    }
        
}
