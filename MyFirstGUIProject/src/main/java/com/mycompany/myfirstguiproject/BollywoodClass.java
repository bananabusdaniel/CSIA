/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myfirstguiproject;

/**
 *
 * @author 16939
 */
public class BollywoodClass {
    private String className = "not set yet";
    private String classLocation = "not set yet";
    private String inPersonOrZoom = "not set yet";
   
    Song song = new Song();
   // We will be using the class, Song, in this class as well
    public BollywoodClass(){
    
    }
    
    public BollywoodClass(String className, String classLocation, Song song, String inPersonOrZoom){
    
    }
    
    public String getInPersonOrZoom(){
        return inPersonOrZoom;
    }
    
    public String getClassName(){
        return className;
    }
    
    public String getClassLocation(){
        return classLocation;
    }
    
    public void setInPersonOrZoom(String inPersonOrZoom){
       this.inPersonOrZoom = inPersonOrZoom;
    }
    
    public void setClassName(String className){
        this.className = className;
    }
    
    public void setClassLocation(String classLocation){
        this.classLocation = classLocation;
    }
    
}
