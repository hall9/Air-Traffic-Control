/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flight.simulator.classes;

/**
 *
 * @author blakejoynes
 */
public abstract class Lesson {
    int lessonID;
    String lessonName;
    
    public abstract String getLesson(int lessonID);
    
    public abstract void setLesson();
    
}
