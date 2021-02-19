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
public class Song {
    
    private String songName = "not set yet";
    private String movieName = "not set yet";
    private int songBPM = -999;
    private String dateOfPerformance = "not set yet";
    
    public Song(){
    
    }
    
    public Song(String songName, String movieName, int songBPM, String dateOfPerformance){
        this.songName = songName;
        this.movieName = movieName;
        this.songBPM = songBPM;
        this.dateOfPerformance = dateOfPerformance;
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
    public String getDateOfPerformance(){
        return dateOfPerformance;
    }
    public void setSongName(String songName){
        this.songName = songName;
    }
    public void setMovieName(String movieName){
        this.movieName = movieName;
    }
    public void setSongBPM(int songBPM){
        this.songBPM = songBPM;
    }
    public void setDateOfPerformance(String dateOfPerformance){
        this.dateOfPerformance = dateOfPerformance;
    }
}
