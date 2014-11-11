/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flight.simulator;

/**
 *
 * @author blakejoynes
 */
public abstract class Grade {
    int lessonID;
    String uName;
    char grade;
    
    
    public abstract char getGrade(int lessonID);
    public abstract void getFeedback(String uName, int lessonID);
    public abstract void simFeedback();
    
    
}
