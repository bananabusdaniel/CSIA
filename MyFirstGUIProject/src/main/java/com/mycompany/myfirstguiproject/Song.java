/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myfirstguiproject;

import java.util.ArrayList;

/**
 *
 * @author 16939
 */
public class Song {
    
    private String songName = "not set yet";
    private String movieName = "not set yet";
    private int songBPM = -999;
    private String dateOfPerformance = "not set yet";
    private ArrayList<Student> completedStudents = new ArrayList<Student>();
    public static Song song = new Song();
    
    public Song(){
    
    }
    
    public Song(String songName, String movieName, int songBPM, String dateOfPerformance, ArrayList<Student> completedStudents){
        this.songName = songName;
        this.movieName = movieName;
        this.songBPM = songBPM;
        this.dateOfPerformance = dateOfPerformance;
        this.completedStudents = completedStudents;
    }
    
    public String getSongName(){
        return songName;
    }
    public String getMovieName(){
        return movieName;
    }
    public int getSongBPM(){
        return songBPM;
    }
    public ArrayList<Student> getCompletedStudents(){
        return completedStudents;
    }
    public String getDateOfPerformance(){
        return dateOfPerformance;
    }
    public void setSongName(String songName){
        this.songName = songName;
    }
    public void setMovieName(String movieName){
        this.movieName = movieName;
    }
    public void addCompletedStudents(Student student, int s){
        MainGUI.songs.get(s).completedStudents.add(student);
    }
    public void setSongBPM(int songBPM){
        this.songBPM = songBPM;
    }
    public void setDateOfPerformance(String dateOfPerformance){
        this.dateOfPerformance = dateOfPerformance;
    }
}
