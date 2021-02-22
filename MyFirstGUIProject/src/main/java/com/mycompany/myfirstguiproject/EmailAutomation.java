/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myfirstguiproject; 

import com.google.gson.Gson;
import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.swing.DefaultListModel;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 *
 * @author 16939
 */
public class EmailAutomation {
    
    //The survey link should be retrieved from the maingui - email tab - text field
    private static final String SURVEYLINK = "https://www.surveymonkey.com/analyze/browse/Q3QX6VcTZ8y_2F2w6PdfKMEGvjwPbqWiR4VVbxqErVN7g_3D";
    //The class date should be retrieved from the main gui - email tab - TF/CB???
    private static final String CLASSDATE = "Feb 16";
    static DefaultListModel emails = new DefaultListModel();
    public static int  progressCounter = 0;
    
    public static void setProperty(){
        System.setProperty("webdriver.chrome.driver", "/Users/16939/Desktop/MyFirstGUIProject/Nest/chromedriver");
        
    }
    public static ChromeOptions headless(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        return options;
    }
    public static void waitSec(int seconds) throws InterruptedException{
        TimeUnit.SECONDS.sleep(seconds);
    }
    
    public static void main(String[] args) throws InterruptedException {
        //setProperty();
        //ExtractEmails(SURVEYLINK, CLASSDATE);
        //sendEmail(EmailSubjectTF.getText(), BodyTextTF.getText());
    }
    
    public static DefaultListModel ExtractEmails(String surveyLink, String classDate) throws InterruptedException{
        WebDriver driver = new ChromeDriver();
        driver.get(surveyLink);
        logIntoSurveyMonkey(driver, "16939@students.isb.ac.th", "reDblue0");
        waitSec(15);
        getTotalRespondentNum(driver);
        waitSec(2);
        MainGUI.mainGUI.threadMethod().start();
        while(notRespondent1(driver)){
            //A PROBLEM  is that it is not printing out the first email (the latest respondent)
            if(checkClassDate(driver)){
                String emailAnswer1 = driver.findElement(By.xpath("/html/body/div[2]/div[3]/div/table/tbody/tr/td[2]/div[3]/div[3]/div/div/div[3]/div[2]/div/div[2]/div/div[1]/div/div[2]/div/p")).getText();
                if(!emailAnswer1.equals("")){
                    System.out.println(emailAnswer1);
                    emails.addElement(emailAnswer1);
                }
                String emailAnswer2 = driver.findElement(By.xpath("/html/body/div[2]/div[3]/div/table/tbody/tr/td[2]/div[3]/div[3]/div/div/div[2]/div[2]/div/div[2]/div/div[1]/div/div[2]/div/p")).getText();
                if(!emailAnswer2.equals("")){
                    System.out.println(emailAnswer2); 
                    emails.addElement(emailAnswer2);
                } 
            }
            progressCounter++;
            waitSec(5);
            waitSec(5);
            driver.findElement(By.xpath("/html/body/div[2]/div[3]/div/table/tbody/tr/td[2]/div[2]/div/div/a[1]")).click();
      }
        MainGUI.mainGUI.ExtractionProgressBar.setValue(MainGUI.mainGUI.ExtractionProgressBar.getMaximum());
        return emails;
        
    }
    
    public static String getTotalRespondentNum(WebDriver driver){
        String respondentNumber = driver.findElement(By.xpath("/html/body/div[2]/div[3]/div/table/tbody/tr/td[2]/div[2]/div/a")).getText();
        System.out.println(respondentNumber);
        MainGUI.respondentsTotal = Integer.parseInt(respondentNumber.substring(respondentNumber.length()-1));
        return respondentNumber.substring(respondentNumber.length()-1);
    }
    
    public static boolean notRespondent1(WebDriver driver) throws InterruptedException{
        waitSec(15);
        String respondentNumber = driver.findElement(By.xpath("/html/body/div[2]/div[3]/div/table/tbody/tr/td[2]/div[2]/div/a")).getText();
        if(!respondentNumber.equalsIgnoreCase("Respondent #1")){
            return true;
        }
        else{
            return false;
        }
    }
    
    public static boolean checkClassDate(WebDriver driver){
        //For some reason, the date question has several different xpaths- if this is encountered, then we can make a method which checks the valid xpaths 
        
        if(driver.findElement(By.xpath("/html/body/div[2]/div[3]/div/table/tbody/tr/td[2]/div[3]/div[3]/div/div/div[3]/div[2]/div/div[2]/div/div[2]/div/div[2]/div/ul/li/span")).getText().equals(CLASSDATE)){
            return true; 
        }
        else if(driver.findElement(By.xpath("/html/body/div[2]/div[3]/div/table/tbody/tr/td[2]/div[3]/div[3]/div/div/div[2]/div[2]/div/div[2]/div/div[2]/div/div[2]/div/ul/li/span")).getText().equals(CLASSDATE)){
            return true;
        }
        else{
            return false;
        }
    }
    
    public static void logIntoSurveyMonkey(WebDriver driver, String username, String password) throws InterruptedException{
        waitSec(5);
        driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/div/div[2]/div[1]/div[2]/div/div/p[1]/a")).click();//Login via gmail
        waitSec(3);
        driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div/div[2]/div/div/div[2]/div/div[1]/div/form/span/section/div/div/div[1]/div/div[1]/div/div[1]/input")).sendKeys(username + Keys.ENTER); //Putting in email
        waitSec(3);
        driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div/div[2]/div/div/div[2]/div/div[1]/div/form/span/section/div/div/div[1]/div[1]/div/div/div/div/div[1]/div/div[1]/input")).sendKeys(password + Keys.ENTER); //Putting in pw
    }
    
    public static void sendEmail() throws InterruptedException, AWTException{
        
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.gmail.com");
        waitSec(5);
        driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div/div[2]/div/div/div[2]/div/div[1]/div/form/span/section/div/div/div[1]/div/div[1]/div/div[1]/input")).sendKeys("16939@students.isb.ac.th" + Keys.ENTER);
        waitSec(3);
        driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div/div[2]/div/div/div[2]/div/div[1]/div/form/span/section/div/div/div[1]/div[1]/div/div/div/div/div[1]/div/div[1]/input")).sendKeys("reDblue0" + Keys.ENTER);
        waitSec(5);
        driver.findElement(By.xpath("/html/body/div[7]/div[3]/div/div[2]/div[1]/div[1]/div[1]/div/div/div/div[1]/div/div")).click();
        waitSec(1);
        //Emails list needs to be edited so it gets every element(email) within the DefaultListModel
        
        /*driver.findElement(By.xpath("/html/body/div[20]/div/div/div/div[1]/div[3]/div[1]/div[1]/div/div/div/div[3]/div/div/div[4]/table/tbody/tr/td[2]/form/div[2]")).sendKeys(EmailAutomation.emails.toString()
                + Keys.TAB + emailSubject + Keys.TAB + emailBodyText);*/
        driver.findElement(By.xpath("/html/body/div[17]/div/div/div/div[1]/div[3]/div[1]/div[1]/div/div/div/div[3]/div/div/div[4]/table/tbody/tr/td[2]/table/tbody/tr[2]/td/div/div/div[4]/table/tbody/tr/td[1]/div/div[2]/div[1]")).click();
    
    }
}
