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
public class Student {
    private String name = "not set yet";
    private String email = "not set yet";
    private String location = "not set yet";
    private String timeZone = "not set yet";
    private int phoneNumber = -999;
    private String notes = "not set yet";
    
    
    public Student(){
    
    }
    
    public Student(String name, String email, String location, String timeZone, int phoneNumber, String notes){
        
        this.name = name;
        this.email = email;
        this.location = location;
        this.timeZone = timeZone;
        this.phoneNumber = phoneNumber;
        this.notes = notes;
        
    }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public String getLocation(){
        return location;
    }
    public String getTimeZone(){
        return timeZone;
    }
    public int getPhoneNumber(){
        return phoneNumber;
    }
    public String getNotes(){
        return notes;
    }
   
    public void setName(String name){
        this.name = name;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setLocation(String location){
        this.location = location;
    }
    public void setTimeZone(String timeZone){
        this.timeZone = timeZone;
    }
    public void setPhoneNumber(int phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public void setNotes(String notes){
        this.notes = notes;
    }
   
}
