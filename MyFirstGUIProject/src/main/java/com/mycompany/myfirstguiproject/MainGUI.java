/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myfirstguiproject;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.ColorModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author 16939
 */
public class MainGUI extends javax.swing.JFrame {
    
   
    private static int numOfTotalRespondents = 0;
    private static int newProgress = 0;
    private int respondentIterations = 0;
    //private static final String SURVEYLINK = "https://www.surveymonkey.com/analyze/browse/Q3QX6VcTZ8y_2F2w6PdfKMEGvjwPbqWiR4VVbxqErVN7g_3D";
    
    //private static final String CLASSDATE = "Feb 16";
    public static int respondentsTotal = 0;
    
    private int triesToEnter = 0;

    private ArrayList<Student> students = new ArrayList<Student>();
    public static ArrayList<Song> songs = new ArrayList<Song>();
    private ArrayList<BollywoodClass> classes = new ArrayList<BollywoodClass>();
    public static MainGUI mainGUI = new MainGUI();
    
    /**
     * Creates new form MainGUI
     */
    public MainGUI() {
        initComponents();
        myInit();
        RemoveButton.setVisible(false);
        ConfirmRemoveCB.setVisible(false);
        
    }
    
    public String getMonthAndDay(Date date){
        String month = "";
        switch(date.getMonth()){
            case 0: 
                month = "Jan";
                break;
            case 1:
                month = "Feb";
                break;
            case 2: 
                month = "Mar";
                break;
            case 3:
                month = "Apr";
                break;
            case 4: 
                month = "May";
                break;
            case 5:
                month = "Jun";
                break;
            case 6: 
                month = "Jul";
                break;
            case 7:
                month = "Aug";
                break;
            case 8: 
                month = "Sep";
                break;
            case 9:
                month = "Oct";
                break;
            case 10: 
                month = "Nov";
                break;
            case 11:
                month = "Dec";
                break;    
        }
        return month + " " + date.getDate();
    }

    public void myInit(){
        /*ExtractionProgressBar.setMinimum(0);
        ClassNameTF.setVisible(false);
        ClassLocationTF.setVisible(false);
        ClassSongCB.setVisible(false);
        InPersonCB.setVisible(false);
        ZoomCB.setVisible(false);*/
        ListOfStudentsT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Name", "Email", "Location", "Phone Number", "Time Zone (GMT)", "Notes"
            }
        ));
    }
    public Thread threadMethod() {
        Thread t1 = new Thread(() -> {
            ExtractionProgressBar.setMaximum(respondentsTotal);
            while(ExtractionProgressBar.getValue() != ExtractionProgressBar.getMaximum()){
                    //int progress = numOfTotalRespondents - newProgress;
                    ExtractionProgressBar.setValue(EmailAutomation.progressCounter);
                    System.out.println("Max: " + respondentsTotal);
                    System.out.println("Current: " + EmailAutomation.emails.getSize());
                   try {
                       TimeUnit.SECONDS.sleep(1);
                   } catch (InterruptedException ex) {
                       ex.printStackTrace();
                   }
               }
        });
        return t1;
    }
    
    public void refreshStudentTable(){
        if(students.size() <= ListOfStudentsT.getRowCount()){
            for(int row = 0; row < students.size(); row++){
                ListOfStudentsT.setValueAt(students.get(row).getName(), row, 0);
                ListOfStudentsT.setValueAt(students.get(row).getEmail(), row, 1);
                ListOfStudentsT.setValueAt(students.get(row).getLocation(), row, 2);
                ListOfStudentsT.setValueAt(students.get(row).getPhoneNumber(), row, 3);
                ListOfStudentsT.setValueAt(students.get(row).getPhoneNumber(), row, 4);
                ListOfStudentsT.setValueAt(students.get(row).getNotes(), row, 5);
            }
        }
    }
    public void refreshSongTable(){
        if(songs.size() <= ListOfSongsT.getRowCount()){
            for(int row = 0; row < songs.size(); row++){
                ListOfSongsT.setValueAt(songs.get(row).getSongName(), row, 0);
                ListOfSongsT.setValueAt(songs.get(row).getMovieName(), row, 1);
                ListOfSongsT.setValueAt(songs.get(row).getSongBPM(), row, 2);
                ListOfSongsT.setValueAt(songs.get(row).getDateOfPerformance(), row, 3);
            }
        }
    }
    public void refreshClassTable(){
        if(classes.size() <= ListOfClassesT.getRowCount()){
            for(int row = 0; row < classes.size(); row++){
                ListOfClassesT.setValueAt(classes.get(row).getClassName(), row, 0);
                ListOfClassesT.setValueAt(classes.get(row).getClassLocation(), row, 1);
                ListOfClassesT.setValueAt(classes.get(row).getClassDate(), row, 2);
                ListOfClassesT.setValueAt(classes.get(row).getClassSong(), row, 3);
                ListOfClassesT.setValueAt(classes.get(row).getInPersonOrZoom(), row, 4);
            }
        }
    }
    
    public boolean correctStudentInfo(){
        if(StudentNameTF.getText().length() < 2){
            JOptionPane.showMessageDialog(this, "The Student Name must be filled and at least 2 characters long. Please re-enter the following information", "Error Message", HEIGHT);
            return false;
        }
        else if(StudentNameTF.getText().length() > 30){
            triesToEnter++;
            JOptionPane.showMessageDialog(this, "The student name seems a bit long... Consider editing it by only writing a first name", "Warning Message", HEIGHT);
            return false;
        }
        if(StudentEmailTF.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Please make sure the student email is not left blank", "Error Message", HEIGHT);
            return false;
        }
        if(!StudentEmailTF.getText().substring(StudentEmailTF.getText().length()-4).equals(".com") && triesToEnter == 0){
            triesToEnter++;
            JOptionPane.showMessageDialog(this, "Please check to make sure the email is valid before re-entering", "Warning Message", HEIGHT);
            return false;
        }
        if(StudentPhoneNumberTF.getText().substring(0, 1).equals("0") && triesToEnter == 0){
            triesToEnter++;
            JOptionPane.showMessageDialog(this, "We reccomend that you add the country code to the phone number!", "Warning Message", HEIGHT);
            return false;
        }
        if(StudentPhoneNumberTF.getText().length() < 7 || StudentPhoneNumberTF.getText().length() > 15){
            JOptionPane.showMessageDialog(this, "Please make sure the phone number is in between 7 and 15 characters, inclusive", "Error Message", HEIGHT);
            return false;
        }  
        if(!StudentTimeZoneTF.getText().substring(0, 3).equalsIgnoreCase("GMT") && triesToEnter==0){
            triesToEnter++;
            JOptionPane.showMessageDialog(this, "Please make sure you are using the correct timezone format: GMT", "Error Message", HEIGHT);
            return false;
        }
        return (StudentNameTF.getText().length() > 2 && StudentNameTF.getText().length() < 30 && !StudentEmailTF.getText().equals("") && StudentEmailTF.getText().substring(StudentEmailTF.getText().length()-4).equals(".com") && !StudentPhoneNumberTF.getText().substring(0, 1).equals("0") && StudentPhoneNumberTF.getText().length() > 7 && StudentPhoneNumberTF.getText().length() < 15) || triesToEnter > 0;
    }
    
    public boolean correctSongInfo(){
        if(triesToEnter > 0){
            return true;
        }
        else if(SongNameTF.getText().equals("")){
            JOptionPane.showMessageDialog(this, "You must input a song name in the corresponding text-field", "Error Message", HEIGHT);
            return false;
        }
        else if(!isNumeric()){
            JOptionPane.showMessageDialog(this, "Please input an integer for the Song BPM", "Error Message", HEIGHT);
            return false;
        }
        else if(MovieNameTF.getText().length() < 30 && SongNameTF.getText().length() < 30){
            return true;
        }
        else{
            JOptionPane.showMessageDialog(this, "Make sure your inputs arent too long... Re-enter the song information" ,"Warning Message" ,HEIGHT);  
            return false;
        }
    }
    
    public boolean isNumeric(){
        
        if (SongBPMTF.getText() == null) {
        return false;
        }   
        try{
            int d = Integer.parseInt(SongBPMTF.getText());
        }
        catch (NumberFormatException nfe){
            return false;
        }
        return true;
    }
    
    public void makeStudentListEmpty(){
        for(int row = 0; row < ListOfStudentsT.getRowCount(); row++){
            for(int col = 0; col < ListOfStudentsT.getColumnCount(); col++){
                ListOfStudentsT.setValueAt("", row, col);
            }
        }
    }
    
    public void makeSongListEmpty(){
        for(int row = 0; row < ListOfSongsT.getRowCount(); row++){
            for(int col = 0; col < ListOfSongsT.getColumnCount(); col++){
                ListOfSongsT.setValueAt("", row, col);
            }
        }
    }
    
    public String inPersonOrZoom(){
        if(InPersonCB.isSelected()){
            if(ZoomCB.isSelected()){
                return "In Person and On Zoom";
            }else{
                return "In Person";
            }
        }
        else if(ZoomCB.isSelected()){
            return "On Zoom";
        }
        return "N/A";
    }
    
    public boolean correctClassInfo(){
        if(!InPersonCB.isSelected() && !ZoomCB.isSelected()){
            JOptionPane.showMessageDialog(this, "Please select either In-person or Zoom class in order to input a new class", "Error Message", HEIGHT);
            return false;
        }
        if(ClassLocationTF.getText().equals("")){
            JOptionPane.showMessageDialog(this, "The class location textfield cannot be left blank for input", "Error Message", HEIGHT);
            return false;
        }
        if(ClassSongCB.getSelectedIndex() == -1){
            JOptionPane.showMessageDialog(this, "Please input a song as part of the information about the class", "Error Message", HEIGHT);
            return false;
        }
        else{
        return true;
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ErrorPopup = new javax.swing.JPopupMenu();
        Input = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        InputStudentOKButton = new javax.swing.JButton();
        StudentNameTF = new javax.swing.JTextField();
        StudentEmailTF = new javax.swing.JTextField();
        StudentLocationTF = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        SongNameTF = new javax.swing.JTextField();
        StudentAdditionalNotesTF = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        ListOfInformationTP = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ListOfStudentsT = new javax.swing.JTable();
        SearchStudentsTF = new javax.swing.JTextField();
        SortingStudentsCB = new javax.swing.JComboBox<>();
        SongsForStudentsCB = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ListOfSongsT = new javax.swing.JTable();
        jTextField13 = new javax.swing.JTextField();
        SortingSongsCB = new javax.swing.JComboBox<>();
        InputSongOKButton = new javax.swing.JButton();
        DateOfPerformanceChooser = new com.toedter.calendar.JDateChooser();
        MovieNameTF = new javax.swing.JTextField();
        StudentTimeZoneTF = new javax.swing.JTextField();
        StudentPhoneNumberTF = new javax.swing.JTextField();
        SongBPMTF = new javax.swing.JTextField();
        AddSongToStudentCB = new javax.swing.JComboBox<>();
        AddSongToStudentButton = new javax.swing.JButton();
        RefreshButton = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        ConfirmRemoveCB = new javax.swing.JCheckBox();
        RemoveButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        ListOfClassesT = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        InputClassButton = new javax.swing.JButton();
        ClassNameTF = new javax.swing.JTextField();
        ZoomCB = new javax.swing.JCheckBox();
        InPersonCB = new javax.swing.JCheckBox();
        ClassSongCB = new javax.swing.JComboBox<>();
        ClassLocationTF = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        ClassDateChooser = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        jSpinner1 = new javax.swing.JSpinner();
        jToggleButton1 = new javax.swing.JToggleButton();
        jLabel6 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        EmailExtractionB = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        EmailList = new javax.swing.JList<>();
        SendEmailButton = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        SignUpSheetLinkTF = new javax.swing.JTextField();
        ExtractionProgressBar = new javax.swing.JProgressBar();
        ClassDateCB = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        cutMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        pasteMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        contentsMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        InputStudentOKButton.setBackground(new java.awt.Color(255, 255, 255));
        InputStudentOKButton.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        InputStudentOKButton.setForeground(new java.awt.Color(102, 102, 255));
        InputStudentOKButton.setText("OK");
        InputStudentOKButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                InputStudentOKButtonMouseReleased(evt);
            }
        });
        InputStudentOKButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputStudentOKButtonActionPerformed(evt);
            }
        });

        StudentNameTF.setColumns(5);
        StudentNameTF.setForeground(new java.awt.Color(204, 204, 204));
        StudentNameTF.setText("Insert Student Name");
        StudentNameTF.setSelectedTextColor(new java.awt.Color(204, 204, 204));
        StudentNameTF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                StudentNameTFMouseReleased(evt);
            }
        });
        StudentNameTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StudentNameTFActionPerformed(evt);
            }
        });

        StudentEmailTF.setForeground(new java.awt.Color(204, 204, 204));
        StudentEmailTF.setText("Insert Student Email");
        StudentEmailTF.setSelectedTextColor(new java.awt.Color(204, 204, 204));
        StudentEmailTF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                StudentEmailTFMouseReleased(evt);
            }
        });
        StudentEmailTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StudentEmailTFActionPerformed(evt);
            }
        });

        StudentLocationTF.setForeground(new java.awt.Color(204, 204, 204));
        StudentLocationTF.setText("Insert Student Location");
        StudentLocationTF.setMaximumSize(new java.awt.Dimension(157, 26));
        StudentLocationTF.setMinimumSize(new java.awt.Dimension(157, 26));
        StudentLocationTF.setSelectedTextColor(new java.awt.Color(204, 204, 204));
        StudentLocationTF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                StudentLocationTFMouseReleased(evt);
            }
        });
        StudentLocationTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StudentLocationTFActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 2, 18)); // NOI18N
        jLabel1.setText("Input New Student Information");

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 2, 18)); // NOI18N
        jLabel2.setText("Input Song Information");

        SongNameTF.setForeground(new java.awt.Color(204, 204, 204));
        SongNameTF.setText("Insert Song Name");
        SongNameTF.setSelectedTextColor(new java.awt.Color(204, 204, 204));
        SongNameTF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                SongNameTFMouseReleased(evt);
            }
        });
        SongNameTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SongNameTFActionPerformed(evt);
            }
        });

        StudentAdditionalNotesTF.setForeground(new java.awt.Color(204, 204, 204));
        StudentAdditionalNotesTF.setText("Insert Additional Notes");
        StudentAdditionalNotesTF.setMaximumSize(new java.awt.Dimension(157, 90));
        StudentAdditionalNotesTF.setMinimumSize(new java.awt.Dimension(157, 90));
        StudentAdditionalNotesTF.setSelectedTextColor(new java.awt.Color(204, 204, 204));
        StudentAdditionalNotesTF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                StudentAdditionalNotesTFMouseReleased(evt);
            }
        });
        StudentAdditionalNotesTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StudentAdditionalNotesTFActionPerformed(evt);
            }
        });

        ListOfStudentsT.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        ListOfStudentsT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Name", "Email", "Location", "Phone Number", "Time Zone (GMT)", "Notes"
            }
        ));
        ListOfStudentsT.setGridColor(new java.awt.Color(153, 153, 153));
        ListOfStudentsT.setRowHeight(20);
        ListOfStudentsT.setShowGrid(true);
        ListOfStudentsT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ListOfStudentsTMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(ListOfStudentsT);

        SearchStudentsTF.setText("Search in List...");
        SearchStudentsTF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                SearchStudentsTFMouseReleased(evt);
            }
        });
        SearchStudentsTF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                SearchStudentsTFKeyReleased(evt);
            }
        });

        SortingStudentsCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sort By...", "Name A-Z", "Email A-Z", "Location A-Z", "GMT (Descending)" }));
        SortingStudentsCB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SortingStudentsCBItemStateChanged(evt);
            }
        });

        SongsForStudentsCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Add Songs..." }));
        SongsForStudentsCB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SongsForStudentsCBItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(SortingStudentsCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(116, 116, 116)
                        .addComponent(SongsForStudentsCB, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(SearchStudentsTF, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(0, 8, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SongsForStudentsCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SearchStudentsTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SortingStudentsCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        ListOfInformationTP.addTab("List of Students", jPanel5);

        ListOfSongsT.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        ListOfSongsT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Song Name", "Movie Name (year)", "Song BPM", "Date of Performance"
            }
        ));
        ListOfSongsT.setGridColor(new java.awt.Color(153, 153, 153));
        ListOfSongsT.setShowGrid(true);
        jScrollPane2.setViewportView(ListOfSongsT);
        if (ListOfSongsT.getColumnModel().getColumnCount() > 0) {
            ListOfSongsT.getColumnModel().getColumn(2).setPreferredWidth(20);
            ListOfSongsT.getColumnModel().getColumn(3).setResizable(false);
            ListOfSongsT.getColumnModel().getColumn(3).setPreferredWidth(130);
        }

        jTextField13.setText("Search in List...");

        SortingSongsCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sort By...", "Song Name A-Z", "Movie Name A-Z", "BPM (ascending)", "BPM (descending)", "Most Recent Performance" }));
        SortingSongsCB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SortingSongsCBItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(SortingSongsCB, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SortingSongsCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        ListOfInformationTP.addTab("List of Songs", jPanel4);

        InputSongOKButton.setBackground(new java.awt.Color(255, 255, 255));
        InputSongOKButton.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        InputSongOKButton.setForeground(new java.awt.Color(102, 102, 255));
        InputSongOKButton.setText("OK");
        InputSongOKButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                InputSongOKButtonMouseReleased(evt);
            }
        });
        InputSongOKButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputSongOKButtonActionPerformed(evt);
            }
        });

        DateOfPerformanceChooser.setToolTipText("Choose Date");

        MovieNameTF.setForeground(new java.awt.Color(204, 204, 204));
        MovieNameTF.setText("Insert Movie Name (year)");
        MovieNameTF.setSelectedTextColor(new java.awt.Color(204, 204, 204));
        MovieNameTF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                MovieNameTFMouseReleased(evt);
            }
        });
        MovieNameTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MovieNameTFActionPerformed(evt);
            }
        });

        StudentTimeZoneTF.setForeground(new java.awt.Color(204, 204, 204));
        StudentTimeZoneTF.setText("Insert Time Zone (GMT)");
        StudentTimeZoneTF.setMaximumSize(new java.awt.Dimension(157, 26));
        StudentTimeZoneTF.setMinimumSize(new java.awt.Dimension(157, 26));
        StudentTimeZoneTF.setSelectedTextColor(new java.awt.Color(204, 204, 204));
        StudentTimeZoneTF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                StudentTimeZoneTFMouseReleased(evt);
            }
        });
        StudentTimeZoneTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StudentTimeZoneTFActionPerformed(evt);
            }
        });

        StudentPhoneNumberTF.setForeground(new java.awt.Color(204, 204, 204));
        StudentPhoneNumberTF.setText("Insert Phone Number");
        StudentPhoneNumberTF.setMaximumSize(new java.awt.Dimension(143, 26));
        StudentPhoneNumberTF.setMinimumSize(new java.awt.Dimension(143, 26));
        StudentPhoneNumberTF.setSelectedTextColor(new java.awt.Color(204, 204, 204));
        StudentPhoneNumberTF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                StudentPhoneNumberTFMouseReleased(evt);
            }
        });
        StudentPhoneNumberTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StudentPhoneNumberTFActionPerformed(evt);
            }
        });

        SongBPMTF.setForeground(new java.awt.Color(204, 204, 204));
        SongBPMTF.setText("Insert Song BPM");
        SongBPMTF.setSelectedTextColor(new java.awt.Color(204, 204, 204));
        SongBPMTF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                SongBPMTFMouseReleased(evt);
            }
        });
        SongBPMTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SongBPMTFActionPerformed(evt);
            }
        });

        AddSongToStudentCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose Student to Add Song", "Student 1", "Student 2" }));

        AddSongToStudentButton.setBackground(new java.awt.Color(255, 255, 255));
        AddSongToStudentButton.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        AddSongToStudentButton.setForeground(new java.awt.Color(102, 102, 255));
        AddSongToStudentButton.setText("Add Song");
        AddSongToStudentButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                AddSongToStudentButtonMouseReleased(evt);
            }
        });

        RefreshButton.setIcon(new javax.swing.ImageIcon("/Users/16939/Desktop/RefreshIcon.png")); // NOI18N
        RefreshButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                RefreshButtonMouseReleased(evt);
            }
        });

        jLabel8.setText("Select One Song on List and then:");

        jLabel10.setText("Adding Student to Song:");

        ConfirmRemoveCB.setText("Confirm");

        RemoveButton.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        RemoveButton.setText("Remove");
        RemoveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                RemoveButtonMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(RemoveButton)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(ConfirmRemoveCB)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(AddSongToStudentCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AddSongToStudentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(14, 14, 14)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(12, 12, 12)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(StudentNameTF)
                                        .addComponent(StudentLocationTF, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                                        .addComponent(StudentEmailTF)
                                        .addComponent(StudentPhoneNumberTF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(StudentTimeZoneTF, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                                        .addComponent(StudentAdditionalNotesTF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(RefreshButton)
                                    .addGap(75, 75, 75)
                                    .addComponent(jLabel1)))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(62, 62, 62)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(SongNameTF, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(MovieNameTF, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(1, 1, 1))
                                        .addComponent(SongBPMTF, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(DateOfPerformanceChooser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(80, 80, 80)
                                    .addComponent(jLabel2))))
                        .addComponent(ListOfInformationTP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(210, 210, 210)
                .addComponent(InputStudentOKButton)
                .addGap(306, 306, 306)
                .addComponent(InputSongOKButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jLabel2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(RefreshButton))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel1)))
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(SongNameTF, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(MovieNameTF, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SongBPMTF, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DateOfPerformanceChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(InputSongOKButton))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(134, 134, 134)
                                .addComponent(StudentLocationTF, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(StudentNameTF, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(StudentTimeZoneTF, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(9, 9, 9)
                                .addComponent(StudentEmailTF, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addComponent(StudentPhoneNumberTF, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(87, 87, 87)
                                .addComponent(StudentAdditionalNotesTF, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(InputStudentOKButton)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(283, 283, 283)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(309, 309, 309))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(ListOfInformationTP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(AddSongToStudentCB, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(RemoveButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(AddSongToStudentButton)
                            .addComponent(jLabel10)
                            .addComponent(ConfirmRemoveCB))
                        .addGap(208, 208, 208))))
        );

        DateOfPerformanceChooser.getAccessibleContext().setAccessibleName("");

        Input.addTab("Inputting Information", jPanel1);

        ListOfClassesT.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        ListOfClassesT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Class Name", "Class Location", "Class Date", "Class Song", "In-Person/Zoom"
            }
        ));
        ListOfClassesT.setGridColor(new java.awt.Color(153, 153, 153));
        ListOfClassesT.setShowGrid(true);
        jScrollPane3.setViewportView(ListOfClassesT);

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel3.setText("Up-Coming Classes");

        InputClassButton.setBackground(new java.awt.Color(255, 255, 255));
        InputClassButton.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        InputClassButton.setForeground(new java.awt.Color(102, 102, 255));
        InputClassButton.setText("OK");
        InputClassButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                InputClassButtonMouseReleased(evt);
            }
        });

        ClassNameTF.setForeground(new java.awt.Color(204, 204, 204));
        ClassNameTF.setText("  Insert Class Name");
        ClassNameTF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ClassNameTFMouseReleased(evt);
            }
        });

        ZoomCB.setText("Zoom");

        InPersonCB.setText("In-Person");

        ClassLocationTF.setForeground(new java.awt.Color(204, 204, 204));
        ClassLocationTF.setText("  Insert Class Location");
        ClassLocationTF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ClassLocationTFMouseReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 2, 18)); // NOI18N
        jLabel4.setText("Pick a Date in Below to Start Inputting a New Class");

        ClassDateChooser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ClassDateChooserMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(164, 164, 164)
                        .addComponent(jLabel4)
                        .addGap(48, 48, 48)
                        .addComponent(jLabel5))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 778, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(331, 331, 331)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(InputClassButton)
                            .addComponent(InPersonCB)
                            .addComponent(ZoomCB)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(253, 253, 253)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ClassDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ClassNameTF)
                            .addComponent(ClassLocationTF, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(303, 303, 303)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(ClassSongCB, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ClassDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(ClassNameTF, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ClassLocationTF, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ClassSongCB, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(InPersonCB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ZoomCB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(InputClassButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );

        Input.addTab("Class Calendar", jPanel2);

        jToggleButton1.setText("Choose an Existing Dance Formation");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jLabel6.setText("Number of Students:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jToggleButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 307, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(642, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButton1)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(17, 17, 17))
        );

        Input.addTab("Dance Formations", jPanel3);

        EmailExtractionB.setText("Extract Emails from Sign-Up Sheet");
        EmailExtractionB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                EmailExtractionBMouseReleased(evt);
            }
        });

        EmailList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Email 1", "Email 2", "Email 3", "Email 4", "Email 5", "Email 6", "Email 7" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(EmailList);

        SendEmailButton.setText("Send Email");
        SendEmailButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                SendEmailButtonMouseReleased(evt);
            }
        });

        jLabel9.setText("Choose Exsisting Student Email(s) to Add to List");

        jTable4.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Name", "Email", "Notes"
            }
        ));
        jTable4.setGridColor(new java.awt.Color(153, 153, 153));
        jTable4.setRowHeight(20);
        jTable4.setShowGrid(true);
        jScrollPane5.setViewportView(jTable4);

        jButton5.setText("Add Highlighted Emails to the List");

        SignUpSheetLinkTF.setText("https://www.surveymonkey.com/analyze/browse/Q3QX6VcTZ8y_2F2w6PdfKMEGvjwPbqWiR4VVbxqErVN7g_3D");
        SignUpSheetLinkTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SignUpSheetLinkTFActionPerformed(evt);
            }
        });

        ExtractionProgressBar.setForeground(new java.awt.Color(51, 51, 255));
        ExtractionProgressBar.setToolTipText("");
        ExtractionProgressBar.setMaximumSize(new java.awt.Dimension(32767, 50));
        ExtractionProgressBar.setMinimumSize(new java.awt.Dimension(10, 50));
        ExtractionProgressBar.setPreferredSize(new java.awt.Dimension(146, 80));

        ClassDateCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setText("Choose Date and Input Survey Link");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jLabel7))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(ClassDateCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                            .addComponent(EmailExtractionB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(SignUpSheetLinkTF, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                            .addComponent(ExtractionProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(16, 16, 16)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(jLabel9)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addComponent(SendEmailButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addGap(113, 113, 113))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ClassDateCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SignUpSheetLinkTF)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(EmailExtractionB, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ExtractionProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5)
                    .addComponent(SendEmailButton))
                .addGap(199, 199, 199))
        );

        Input.addTab("Email Extraction", jPanel6);

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");

        openMenuItem.setMnemonic('o');
        openMenuItem.setText("Open");
        fileMenu.add(openMenuItem);

        saveMenuItem.setMnemonic('s');
        saveMenuItem.setText("Save");
        fileMenu.add(saveMenuItem);

        saveAsMenuItem.setMnemonic('a');
        saveAsMenuItem.setText("Save As ...");
        saveAsMenuItem.setDisplayedMnemonicIndex(5);
        fileMenu.add(saveAsMenuItem);

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setMnemonic('e');
        editMenu.setText("Edit");

        cutMenuItem.setMnemonic('t');
        cutMenuItem.setText("Cut");
        editMenu.add(cutMenuItem);

        copyMenuItem.setMnemonic('y');
        copyMenuItem.setText("Copy");
        editMenu.add(copyMenuItem);

        pasteMenuItem.setMnemonic('p');
        pasteMenuItem.setText("Paste");
        editMenu.add(pasteMenuItem);

        deleteMenuItem.setMnemonic('d');
        deleteMenuItem.setText("Delete");
        editMenu.add(deleteMenuItem);

        menuBar.add(editMenu);

        helpMenu.setMnemonic('h');
        helpMenu.setText("Help");

        contentsMenuItem.setMnemonic('c');
        contentsMenuItem.setText("Contents");
        helpMenu.add(contentsMenuItem);

        aboutMenuItem.setMnemonic('a');
        aboutMenuItem.setText("About");
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Input, javax.swing.GroupLayout.PREFERRED_SIZE, 830, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Input, javax.swing.GroupLayout.PREFERRED_SIZE, 734, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // TODO add your handling code here:
        if(jToggleButton1.isSelected()){
            jToggleButton1.setText("Create a New Dance Formation");
        }
        else{
            jToggleButton1.setText("Choose an Existing Dance Formation");
        }
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void SongBPMTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SongBPMTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SongBPMTFActionPerformed

    private void StudentPhoneNumberTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StudentPhoneNumberTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_StudentPhoneNumberTFActionPerformed

    private void StudentTimeZoneTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StudentTimeZoneTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_StudentTimeZoneTFActionPerformed

    private void MovieNameTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MovieNameTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MovieNameTFActionPerformed

    private void InputSongOKButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_InputSongOKButtonMouseReleased
        // TODO add your handling code here:
        // 
        if(correctSongInfo()){
            triesToEnter = 0;
            songs.add(new Song(SongNameTF.getText(), MovieNameTF.getText(), Integer.parseInt(SongBPMTF.getText()), getMonthAndDay(DateOfPerformanceChooser.getDate()), new ArrayList<Student>()));
            String [] songsArray = new String[songs.size()+1];
            songsArray[0] = "Choose Song";
            for(int i = 1; i < songsArray.length; i++){
               songsArray[i] = songs.get(i-1).getSongName();
            }
            ClassSongCB.setModel(new javax.swing.DefaultComboBoxModel(songsArray));
            SongsForStudentsCB.setModel(new javax.swing.DefaultComboBoxModel(songsArray));
            
        }
        refreshSongTable();
        
    }//GEN-LAST:event_InputSongOKButtonMouseReleased

    private void StudentAdditionalNotesTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StudentAdditionalNotesTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_StudentAdditionalNotesTFActionPerformed

    private void SongNameTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SongNameTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SongNameTFActionPerformed

    private void StudentLocationTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StudentLocationTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_StudentLocationTFActionPerformed

    private void StudentEmailTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StudentEmailTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_StudentEmailTFActionPerformed

    private void StudentNameTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StudentNameTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_StudentNameTFActionPerformed

    private void StudentNameTFMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StudentNameTFMouseReleased
        // TODO add your handling code here:
        StudentNameTF.setText("");
        StudentNameTF.setForeground(Color.BLACK);
    }//GEN-LAST:event_StudentNameTFMouseReleased

    private void InputStudentOKButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_InputStudentOKButtonMouseReleased
        // TODO add your handling code here: 
       
        if(correctStudentInfo()){
            triesToEnter = 0;
            students.add(new Student(StudentNameTF.getText(), StudentEmailTF.getText(), StudentLocationTF.getText(), StudentTimeZoneTF.getText(), Integer.parseInt(StudentPhoneNumberTF.getText()), StudentAdditionalNotesTF.getText()));
        }
        String [] studentsArray = new String[students.size()+1];
        studentsArray[0] = "Choose Student to Add Song";
        for(int i = 1; i < studentsArray.length; i++){
            studentsArray[i] = students.get(i-1).getName();
        }
        AddSongToStudentCB.setModel(new javax.swing.DefaultComboBoxModel(studentsArray));
        refreshStudentTable();
        
    }//GEN-LAST:event_InputStudentOKButtonMouseReleased

    private void SortingSongsCBItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SortingSongsCBItemStateChanged
        // TODO add your handling code here:
        
    }//GEN-LAST:event_SortingSongsCBItemStateChanged

    private void SortingStudentsCBItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SortingStudentsCBItemStateChanged
        // TODO add your handling code here:
        SortingAndSearching sortForStudent = new SortingAndSearching();
        
        if(SortingStudentsCB.getSelectedItem().toString().equals("Name A-Z")){
            sortForStudent.sortByStudentNameAZ(students);
            refreshStudentTable();
        }
        else if (SortingStudentsCB.getSelectedItem().toString().equals("Email A-Z")){
            sortForStudent.sortByStudentEmailAZ(students);
        }
        else if(SortingStudentsCB.getSelectedItem().toString().equals("Location A-Z")){
            sortForStudent.sortByStudentLocationAZ(students);
        }
        else if(SortingStudentsCB.getSelectedItem().toString().equals("GMT (Descending)")){
            sortForStudent.sortByStudentTimeZoneAZ(students);
        }
        
    }//GEN-LAST:event_SortingStudentsCBItemStateChanged

    private void StudentEmailTFMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StudentEmailTFMouseReleased
        // TODO add your handling code here:
        StudentEmailTF.setText("");
        StudentEmailTF.setForeground(Color.BLACK);
    }//GEN-LAST:event_StudentEmailTFMouseReleased

    private void StudentLocationTFMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StudentLocationTFMouseReleased
        // TODO add your handling code here:
        StudentLocationTF.setText("");
        StudentLocationTF.setForeground(Color.BLACK);
    }//GEN-LAST:event_StudentLocationTFMouseReleased

    private void StudentPhoneNumberTFMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StudentPhoneNumberTFMouseReleased
        // TODO add your handling code here:
        StudentPhoneNumberTF.setText("");
        StudentPhoneNumberTF.setForeground(Color.BLACK);
    }//GEN-LAST:event_StudentPhoneNumberTFMouseReleased

    private void StudentTimeZoneTFMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StudentTimeZoneTFMouseReleased
        // TODO add your handling code here:
        StudentTimeZoneTF.setText("");
        StudentTimeZoneTF.setForeground(Color.BLACK);
    }//GEN-LAST:event_StudentTimeZoneTFMouseReleased

    private void StudentAdditionalNotesTFMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StudentAdditionalNotesTFMouseReleased
        // TODO add your handling code here:
        StudentAdditionalNotesTF.setText("");
        StudentAdditionalNotesTF.setForeground(Color.BLACK);
    }//GEN-LAST:event_StudentAdditionalNotesTFMouseReleased

    private void SearchStudentsTFMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SearchStudentsTFMouseReleased
        // TODO add your handling code here:
        SearchStudentsTF.setText("");
    }//GEN-LAST:event_SearchStudentsTFMouseReleased

    private void SearchStudentsTFKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SearchStudentsTFKeyReleased
        // TODO add your handling code here:
        if(evt.getKeyChar() == '\n'){
            SortingAndSearching searchForStudent = new SortingAndSearching();
            String searchBar = SearchStudentsTF.getText();
            int found = searchForStudent.binarySearchStudentName(students, searchBar);
            if(found == -1){
                SearchStudentsTF.setText("Element Not Found");
            }
            else{
                makeStudentListEmpty();
                ListOfStudentsT.setValueAt(students.get(found).getName(), 0, 0);
                ListOfStudentsT.setValueAt(students.get(found).getEmail(), 0, 1);
                ListOfStudentsT.setValueAt(students.get(found).getLocation(), 0, 2);
                ListOfStudentsT.setValueAt(students.get(found).getPhoneNumber(), 0, 3);
                ListOfStudentsT.setValueAt(students.get(found).getPhoneNumber(), 0, 4);
                ListOfStudentsT.setValueAt(students.get(found).getNotes(), 0, 5);
            }
       }
    }//GEN-LAST:event_SearchStudentsTFKeyReleased

    private void SongNameTFMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SongNameTFMouseReleased
        // TODO add your handling code here:
        SongNameTF.setText("");
        SongNameTF.setForeground(Color.BLACK);
    }//GEN-LAST:event_SongNameTFMouseReleased

    private void MovieNameTFMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MovieNameTFMouseReleased
        // TODO add your handling code here:
        MovieNameTF.setText("");
        MovieNameTF.setForeground(Color.BLACK);
    }//GEN-LAST:event_MovieNameTFMouseReleased

    private void SongBPMTFMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SongBPMTFMouseReleased
        // TODO add your handling code here:
        SongBPMTF.setText("");
        SongBPMTF.setForeground(Color.BLACK);
    }//GEN-LAST:event_SongBPMTFMouseReleased

    private void EmailExtractionBMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EmailExtractionBMouseReleased
        // TODO add your handling code here:
        Thread seleniumMethod = new Thread(() -> {
            EmailAutomation.setProperty();
            EmailList.clearSelection();
            try {
                EmailAutomation.ExtractEmails(SignUpSheetLinkTF.getText(), ClassDateCB.getSelectedItem().toString());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            EmailList.setModel(EmailAutomation.emails);
        });
        seleniumMethod.start();
        
    }//GEN-LAST:event_EmailExtractionBMouseReleased

    private void SignUpSheetLinkTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SignUpSheetLinkTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SignUpSheetLinkTFActionPerformed

    private void SendEmailButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SendEmailButtonMouseReleased
        // TODO add your handling code here:
        EmailAutomation.setProperty();
        try {
            EmailAutomation.sendEmail();
        } catch (InterruptedException | AWTException ex) {
            ex.printStackTrace();
        } 
    }//GEN-LAST:event_SendEmailButtonMouseReleased

    private void InputSongOKButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputSongOKButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InputSongOKButtonActionPerformed

    private void InputClassButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_InputClassButtonMouseReleased
        // TODO add your handling code here:
        if(correctClassInfo()){
            classes.add(new BollywoodClass(ClassNameTF.getText(), ClassLocationTF.getText(), getMonthAndDay(ClassDateChooser.getDate()), ClassSongCB.getSelectedItem().toString(), inPersonOrZoom()));
            refreshClassTable();
        }
            String [] classDateArray = new String[classes.size()];
            for(int i = 0; i < classDateArray.length; i++){
               classDateArray[i] = classes.get(i).getClassDate();
            }
            ClassDateCB.setModel(new javax.swing.DefaultComboBoxModel(classDateArray));
    }//GEN-LAST:event_InputClassButtonMouseReleased

    private void ClassNameTFMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ClassNameTFMouseReleased
        // TODO add your handling code here:
        ClassNameTF.setText("");
        ClassNameTF.setForeground(Color.BLACK);
    }//GEN-LAST:event_ClassNameTFMouseReleased

    private void ClassLocationTFMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ClassLocationTFMouseReleased
        // TODO add your handling code here:
        ClassLocationTF.setText("");
        ClassLocationTF.setForeground(Color.BLACK);
    }//GEN-LAST:event_ClassLocationTFMouseReleased

    private void ClassDateChooserMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ClassDateChooserMouseReleased
        // TODO add your handling code here:
        ClassNameTF.setVisible(true);
        ClassLocationTF.setVisible(true);
        ClassSongCB.setVisible(true);
        InPersonCB.setVisible(true);
        ZoomCB.setVisible(true);
    }//GEN-LAST:event_ClassDateChooserMouseReleased

    private void RefreshButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RefreshButtonMouseReleased
        // TODO add your handling code here:
        StudentNameTF.setText("Insert Student Name");
        StudentNameTF.setForeground(Color.gray);
        StudentEmailTF.setText("Insert Student Email");
        StudentEmailTF.setForeground(Color.gray);
        StudentLocationTF.setText("Insert Student Location");
        StudentLocationTF.setForeground(Color.gray);
        StudentPhoneNumberTF.setText("Insert Phone Number");
        StudentPhoneNumberTF.setForeground(Color.gray);
        StudentTimeZoneTF.setText("Insert Time Zone (GMT)");
        StudentTimeZoneTF.setForeground(Color.gray);
        StudentAdditionalNotesTF.setText("Insert Additional Notes");
        StudentAdditionalNotesTF.setForeground(Color.gray);
        SongNameTF.setText("Insert Song Name");
        SongNameTF.setForeground(Color.gray);
        MovieNameTF.setText("Insert Movie Name (year)");
        MovieNameTF.setForeground(Color.gray);
        SongBPMTF.setText("Insert Song BPM");
        SongBPMTF.setForeground(Color.gray);
    }//GEN-LAST:event_RefreshButtonMouseReleased

    private void AddSongToStudentButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddSongToStudentButtonMouseReleased
        // TODO add your handling code here:
        
        //NEED TO FIX so that when the button is clicked...
        //1. The song that is selected gets a student added to it.
        //Make sure it checks for no song selection
        for(int i = 0; i  < students.size();i++){
            if(students.get(i).getName().equals(AddSongToStudentCB.getSelectedItem().toString())){
                if(ListOfSongsT.getSelectedRow() == -1){
                    JOptionPane.showMessageDialog(this, "You must select a song to add to the students repertoire, up in the list of songs", "Error Message", HEIGHT);
                }
                else{
                    int s = ListOfSongsT.getSelectedRow();
                    System.out.println(s);
                    songs.get(s).addCompletedStudents(students.get(i), s);
                    for(int j = 0; j < songs.get(s).getCompletedStudents().size();j++)
                        System.out.println(songs.get(s).getCompletedStudents().get(j).getName());
                    //Song.song.addCompletedStudents(students.get(i));
                    //Student [] songForStudentArray = new Student[Song.song.getCompletedStudents().size()];
                    //for(int j = 0; j < songForStudentArray.length; j++){
                        //songForStudentArray[j] = students.get(j);
                    //}
                    //SongsForStudentsCB.setModel(new javax.swing.DefaultComboBoxModel(songForStudentArray));
                }
            }
        }
    }//GEN-LAST:event_AddSongToStudentButtonMouseReleased

    private void SongsForStudentsCBItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SongsForStudentsCBItemStateChanged
        // TODO add your handling code here:
        makeStudentListEmpty();
        for(int i = 0; i  < songs.size(); i++){
            if(SongsForStudentsCB.getSelectedItem().toString().equals(songs.get(i).getSongName())){
                for(int j = 0; j < songs.get(i).getCompletedStudents().size(); j++){
                    ListOfStudentsT.setValueAt(songs.get(i).getCompletedStudents().get(j).getName(), j, 0);
                    ListOfStudentsT.setValueAt(songs.get(i).getCompletedStudents().get(j).getEmail(), j, 1);
                    ListOfStudentsT.setValueAt(songs.get(i).getCompletedStudents().get(j).getLocation(), j, 2);
                    ListOfStudentsT.setValueAt(songs.get(i).getCompletedStudents().get(j).getPhoneNumber(), j, 3);
                    ListOfStudentsT.setValueAt(songs.get(i).getCompletedStudents().get(j).getPhoneNumber(), j, 4);
                    ListOfStudentsT.setValueAt(songs.get(i).getCompletedStudents().get(j).getNotes(), j, 5);
                }
            }
        }
    }//GEN-LAST:event_SongsForStudentsCBItemStateChanged

    private void InputStudentOKButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputStudentOKButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InputStudentOKButtonActionPerformed

    private void ListOfStudentsTMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ListOfStudentsTMouseReleased
        // TODO add your handling code here:
        int index = ListOfStudentsT.getSelectedRow();
        if (index != -1) {
            RemoveButton.setVisible(true);
            ConfirmRemoveCB.setVisible(true);
        }
    }//GEN-LAST:event_ListOfStudentsTMouseReleased

    private void RemoveButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RemoveButtonMouseReleased
        // TODO add your handling code here:
        if(ConfirmRemoveCB.isSelected()){
            students.remove(ListOfStudentsT.getSelectedRow());
            makeStudentListEmpty();
            refreshStudentTable();
        }
        else{
            JOptionPane.showMessageDialog(this, "Please click the confirm button before removing an element from the table", "Error Message", HEIGHT);
        }
    }//GEN-LAST:event_RemoveButtonMouseReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws ClassNotFoundException, InstantiationException {
        try {
            /* Set the Nimbus look and feel */
            //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
            /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
            * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
            */
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //</editor-fold>

            /* Create and display the form */
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                mainGUI.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddSongToStudentButton;
    private javax.swing.JComboBox<String> AddSongToStudentCB;
    private javax.swing.JComboBox<String> ClassDateCB;
    private com.toedter.calendar.JDateChooser ClassDateChooser;
    private javax.swing.JTextField ClassLocationTF;
    private javax.swing.JTextField ClassNameTF;
    private javax.swing.JComboBox<String> ClassSongCB;
    private javax.swing.JCheckBox ConfirmRemoveCB;
    private com.toedter.calendar.JDateChooser DateOfPerformanceChooser;
    private javax.swing.JButton EmailExtractionB;
    private javax.swing.JList<String> EmailList;
    private javax.swing.JPopupMenu ErrorPopup;
    public javax.swing.JProgressBar ExtractionProgressBar;
    private javax.swing.JCheckBox InPersonCB;
    private javax.swing.JTabbedPane Input;
    private javax.swing.JButton InputClassButton;
    private javax.swing.JButton InputSongOKButton;
    private javax.swing.JButton InputStudentOKButton;
    private javax.swing.JTable ListOfClassesT;
    private javax.swing.JTabbedPane ListOfInformationTP;
    private javax.swing.JTable ListOfSongsT;
    private javax.swing.JTable ListOfStudentsT;
    private javax.swing.JTextField MovieNameTF;
    private javax.swing.JButton RefreshButton;
    private javax.swing.JButton RemoveButton;
    private javax.swing.JTextField SearchStudentsTF;
    private javax.swing.JButton SendEmailButton;
    private javax.swing.JTextField SignUpSheetLinkTF;
    private javax.swing.JTextField SongBPMTF;
    private javax.swing.JTextField SongNameTF;
    private javax.swing.JComboBox<String> SongsForStudentsCB;
    private javax.swing.JComboBox<String> SortingSongsCB;
    private javax.swing.JComboBox<String> SortingStudentsCB;
    private javax.swing.JTextField StudentAdditionalNotesTF;
    private javax.swing.JTextField StudentEmailTF;
    private javax.swing.JTextField StudentLocationTF;
    private javax.swing.JTextField StudentNameTF;
    private javax.swing.JTextField StudentPhoneNumberTF;
    private javax.swing.JTextField StudentTimeZoneTF;
    private javax.swing.JCheckBox ZoomCB;
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem contentsMenuItem;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    // End of variables declaration//GEN-END:variables

}
