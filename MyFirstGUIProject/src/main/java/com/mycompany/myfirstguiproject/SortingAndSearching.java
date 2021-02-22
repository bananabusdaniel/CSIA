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
public class SortingAndSearching {
    
    public void sortByStudentNameAZ(ArrayList<Student> students){
     int n = students.size();
     boolean sorted = false;
     while (!sorted) {
          n--; //It is the n which will result in one less comparison happening each outer pass;
               //whereas, with the first bubble sort we could use the 'pass' variable used for the for loop.
          sorted = true;  
          for (int i=0; i < n; i++) {
               if (students.get(i).getName().compareToIgnoreCase(students.get(i+1).getName()) > 1) {
                    Student temp = students.get(i);  
                    students.set(i, students.get(i+1));
                    students.set(i+1, temp);
                    sorted = false; //as in the second bubble sort, if swapping happens we'll want to continue, and so
                                    //with sorted re-set to false again, the while loop continues
               }
          }
     }
  }
   
    public void sortByStudentEmailAZ(ArrayList<Student> students){
     int n = students.size();
     boolean sorted = false;
     while (!sorted) {
          n--; //It is the n which will result in one less comparison happening each outer pass;
               //whereas, with the first bubble sort we could use the 'pass' variable used for the for loop.
          sorted = true;  
          for (int i=0; i < n; i++) {
               if (students.get(i).getEmail().compareToIgnoreCase(students.get(i+1).getEmail()) < 1) {
                    Student temp = students.get(i);  
                    students.set(i, students.get(i+1));
                    students.set(i+1, temp);
                    sorted = false; //as in the second bubble sort, if swapping happens we'll want to continue, and so
                                    //with sorted re-set to false again, the while loop continues
               }
          }
     }
  }
    
    public void sortByStudentLocationAZ(ArrayList<Student> students){
     int n = students.size();
     boolean sorted = false;
     while (!sorted) {
          n--; //It is the n which will result in one less comparison happening each outer pass;
               //whereas, with the first bubble sort we could use the 'pass' variable used for the for loop.
          sorted = true;  
          for (int i=0; i < n; i++) {
               if (students.get(i).getLocation().compareToIgnoreCase(students.get(i+1).getLocation()) < 1) {
                    Student temp = students.get(i);  
                    students.set(i, students.get(i+1));
                    students.set(i+1, temp);
                    sorted = false; //as in the second bubble sort, if swapping happens we'll want to continue, and so
                                    //with sorted re-set to false again, the while loop continues
               }
          }
     }
  }
    
    public void sortByStudentTimeZoneAZ(ArrayList<Student> students){
     int n = students.size();
     boolean sorted = false;
     while (!sorted) {
          n--; //It is the n which will result in one less comparison happening each outer pass;
               //whereas, with the first bubble sort we could use the 'pass' variable used for the for loop.
          sorted = true;  
          for (int i=0; i < n; i++) {
               if (students.get(i).getTimeZone().compareToIgnoreCase(students.get(i+1).getTimeZone()) < 1) {
                    Student temp = students.get(i);  
                    students.set(i, students.get(i+1));
                    students.set(i+1, temp);
                    sorted = false; //as in the second bubble sort, if swapping happens we'll want to continue, and so
                                    //with sorted re-set to false again, the while loop continues
               }
          }
     }
  }
    
    public int binarySearchStudentName(ArrayList<Student> students, String key){
    int low = 0;
    int high = students.size() -1;
    while(low <= high){           // Keep on looking for the key until the low and the high cross 
        int mid = (low + high) / 2; // each other - if that does happen, it means the key was not found.
        if(students.get(mid).getName().compareToIgnoreCase(key) == 0)
            return mid;           // This is what will happen if/when we find the key in the array.
        else if(students.get(mid).getName().compareToIgnoreCase(key) < 0)
            low = mid + 1;        // Since the arr[mid] value is less than the key, we can eliminate 
        else                        // looking at the left side of the remaining elements
            high = mid -1;        // i.e. the arr[mid] value is greater than what we are looking for, 
    }                               // so we can eliminate looking at the right side of the remaining elements
    return -1;
}
    
}
