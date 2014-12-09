/*
 * AirPlane.java
 *
 * Created on May 4, 2006, 4:28 PM
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

package flight;
    
import java.util.*;

/**
 *
 * @author David
 */
public class AirPlane {

    /* plane specs */
    private String callsign;    // tail number
    private int climbrate;      // feet per second
    private int turnrate;       // degree per second
    
    /* clearance */
    private int cl_heading;     // degrees
    private int cl_alt;         // feet
    private String cl_direct;   //
    private int cl_speed;       // knots
    
    /* current */
    private int cu_heading;     // degrees
    private int cu_alt;         // feet
    private int cu_speed;       // knots
    private double cu_speed_pix;    // speed in pixels
    private int posx;                 
    private int posy;
    private int accel;          // knots per second
    private int deccel;         // knots per second
    
    private long IterTimeStamp; // time stamp of last iteration in seconds
    
    public LabelComponent apLabel;
    
    public TrailComponent[] trailarray = new TrailComponent[4];
    
    private boolean crash;
    
    /** Creates a new instance of AirPlane */
    public AirPlane(String call_sign, int climb_rate, int turn_rate, int heading,
            int alt, String direct, int speed, int pos_x, int pos_y , int accel_rate) {
        cu_heading  = cl_heading    = heading;
        cu_alt      = cl_alt        = alt;
        cu_speed    = cl_speed      = speed;
        cu_speed_pix= cu_speed*0.1;
        cl_direct   = direct;
        
        callsign    = call_sign;
        climbrate   = climb_rate;
        turnrate    = turn_rate;
        accel       = accel_rate;
        
        posx        = pos_x;
        posy        = pos_y; 
        IterTimeStamp = System.currentTimeMillis() / 1000;
        
        apLabel = new LabelComponent(this);
        
        trailarray[0] = new TrailComponent(posx,posy);
        trailarray[1] = new TrailComponent(posx,posy);
        trailarray[2] = new TrailComponent(posx,posy);
        trailarray[3] = new TrailComponent(posx,posy);
        
        crash = true;

    }  
    
    public void SetClearHeading(int heading)
    {
        cl_heading = heading;
    }
    
    public void SetClearAlt(int alt)
    {
        cl_alt = alt;
    }
    
    public void SetClearDirect(String fix)
    {
        cl_direct = fix;
    }
    
    public void SetClearSpeed(int speed)
    {
        cl_speed = speed; 
    }
    
    public void PlaneProcess()
    {
        // get time 
        // compare time with previous process iteration
        long TimeDiff = System.currentTimeMillis() / 1000 - IterTimeStamp;
        
        // boundary check. >360 and <0
        if(cl_heading>360)
        {
            cl_heading -=360;
        }
        if(cl_heading<0)
        {
            cl_heading +=360;
        }
        // calculate heading
        if (cl_heading != cu_heading)
        {
            if(cu_heading > cl_heading)
            {
                if((cu_heading - cl_heading) <= 180) // turn left
                {
                    int delta = (int)TimeDiff * turnrate;
                    if (delta > (cu_heading - cl_heading))
                    {
                        delta = cu_heading - cl_heading;
                    }
                    cu_heading = cu_heading - delta;
                }
                else // turn right
                {
                    int delta = (int)TimeDiff * turnrate;
                    if (delta > (cu_heading - cl_heading))
                    {
                        delta = cu_heading - cl_heading;
                    }
                    cu_heading = cu_heading + delta;
                }                
            }
            else 
            {
                if((cl_heading - cu_heading) <= 180) // turn right
                {
                    int delta = (int)TimeDiff * turnrate;
                    if (delta > (cl_heading - cu_heading))
                    {
                        delta = cl_heading - cu_heading;
                    }
                    cu_heading = cu_heading + delta;
                }
                else // turn left
                {
                    int delta = (int)TimeDiff * turnrate;
                    if (delta > (cl_heading - cu_heading))
                    {
                        delta = cl_heading - cu_heading;
                    }
                    cu_heading = cu_heading - delta;
                }
            }
            if(cu_heading>360)
            {
                cu_heading -=360;
            }
            if(cu_heading<0)
            {
                cu_heading +=360;
            }
        }
        
        // calculate alt
        if (cl_alt != cu_alt)
        {
            //System.out.println("adjusting alt by " + (int)TimeDiff * climbrate);
            if (cl_alt > cu_alt)
            {
                cu_alt = cu_alt + (int)TimeDiff * climbrate;
                //check for overshoot
                if (cl_alt < cu_alt)
                    cu_alt = cl_alt;
                
            }
            else
            {
                cu_alt = cu_alt - (int)TimeDiff * climbrate;
                // check for overshoot
                if (cl_alt > cu_alt)
                    cu_alt = cl_alt;
                
            }
            
        }
        // calculate speed
        if (cl_speed != cu_speed)
        {
            //System.out.println("adjusting accel by " + (int)TimeDiff * accel);
            if (cl_speed > cu_speed)
            {
                cu_speed = cu_speed + (int)TimeDiff * accel;
                //check for overshoot
                if (cl_speed < cu_speed)
                    cu_speed = cl_speed;
                
            }
            else
            {
                cu_speed = cu_speed - (int)TimeDiff * accel;
                // check for overshoot
                if (cl_speed > cu_speed)
                    cu_speed = cl_speed;
                
            }
            
            cu_speed_pix = cu_speed * 0.1;
        }

        for(int i=2; i>=0; i--)
        {
            trailarray[i+1].setPosX(trailarray[i].getPosX());
            trailarray[i+1].setPosY(trailarray[i].getPosY());
        }
        trailarray[0].setPosX(posx);
        trailarray[0].setPosY(posy);

        
        // calculate pos x and y
        //get angle with x axis
        
        int tempheading;
        if (cu_heading - 270 >= 0)
        {
            tempheading = cu_heading - 270;
            // 2nd quadrant
            double delta_y =0; 
            double delta_x =0;
            delta_y = cu_speed_pix * java.lang.Math.sin(java.lang.Math.toRadians(tempheading));
            delta_x = cu_speed_pix * java.lang.Math.cos(java.lang.Math.toRadians(tempheading));
            
            posx-=delta_x;
            posy-=delta_y;

        }
        else if (cu_heading - 180 >=0)
        {
            tempheading = 270 - cu_heading;
            // 3rd quadrant
            double delta_y =0; 
            double delta_x =0;
            delta_y = cu_speed_pix * java.lang.Math.sin(java.lang.Math.toRadians(tempheading));
            delta_x = cu_speed_pix * java.lang.Math.cos(java.lang.Math.toRadians(tempheading));

            posx-=delta_x;
            posy+=delta_y;

        }
        else if (cu_heading - 90 >= 0)
        {
            tempheading = cu_heading - 90;
            // 4th quadrant
            double delta_y =0; 
            double delta_x =0;
            
            delta_y = java.lang.Math.sin(java.lang.Math.toRadians(tempheading));
            delta_x = java.lang.Math.cos(java.lang.Math.toRadians(tempheading));
            
            delta_y *= cu_speed_pix;
            delta_x *= cu_speed_pix;
            
            posx+=delta_x;
            posy+=delta_y;

        }
        else
        {
            // 1st quadrant
            tempheading = 90 - cu_heading;
            double delta_y =0; 
            double delta_x =0;
            delta_y = cu_speed_pix * java.lang.Math.sin(java.lang.Math.toRadians(tempheading));
            delta_x = cu_speed_pix * java.lang.Math.cos(java.lang.Math.toRadians(tempheading));
            
            posx+=delta_x;
            posy-=delta_y;

            
        }
        
        IterTimeStamp = System.currentTimeMillis() / 1000;
    }
    
    public void printPlane()
    {
        System.out.println("current speed = "+cu_speed + " cleared to "+cl_speed);
        System.out.println("current alt = "+cu_alt + " cleared to "+cl_alt);
        System.out.println("current heading = "+cu_heading + " cleared to "+cl_heading);
        System.out.println("**************************************" + System.currentTimeMillis() / 1000);
    }
    
    public int getPosX()
    {
        return posx;
    }
    public int getPosY()
    {
        return posy;
    }
    public String getCallSign()
    {
        return callsign;
    }
    public int getCuHeading()
    {
        return cu_heading;
    }
    
    public int getClHeading()
    {
        return cl_heading;
    }
    
    public int getCuAlt()
    {
        return cu_alt;
    }
    public int getClAlt()
    {
        return cl_alt;
    }
   
    
    public int getCuSpeed()
    {
        return cu_speed;
    }
    

    public int getClSpeed()
    {
        return cl_speed;
    }
    
    public boolean getCrash() {
        return crash;
    }
    
    public boolean checkCrash(AirPlane incoming) {
        if(incoming.posx == this.posx && incoming.posy == this.posy) 
            this.crash = false;
        return this.crash;
        
    }
    
    
/*
 
    private String callsign;    // tail number
    private int climbrate;      // feet per second
    private int turnrate;       // degree per second
    
 
    private int cl_heading;     // degrees
    private int cl_alt;         // feet
    private String cl_direct;   //
    private int cl_speed;       // knots
    
 
    private int cu_heading;     // degrees
    private int cu_alt;         // feet
    private int cu_speed;       // knots
    private int posx;                 
    private int posy;
    private int accel;          // knots per second
    private int deccel;         // knots per second
*/

}
