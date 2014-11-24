/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flight.simulator.classes;

import java.util.Date;

/**
 *
 * @author blakejoynes
 */
public abstract class User {
    String uName;
    String pass;
    String email;
    Date dateofBirth;
    boolean admin = false;
    
   public abstract boolean logIn();
}
