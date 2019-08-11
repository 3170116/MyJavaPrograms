/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MailApp;

import java.net.URL;
import java.sql.Connection;
import java.util.Date;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
/**
 *
 * @author Jimis
 */
public class FXMLDocumentController implements Initializable {
    
    private static User user = new User();
    private static ObservableList<String> mailsList = FXCollections.observableArrayList();
    private static ArrayList<Mail> mails = new ArrayList<Mail>();
    public static final Thread th_searchForNewMails = new Thread() {
        @Override
        public void run() {
            if (user.getUsername() == null) return;
            diplayReceivedMails(null);
        }
    };
    
    @FXML private Button signIn;
    @FXML private Label usernameLb;
    @FXML private Label passwordLb;
    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private Label error;

    @FXML private Label receivedMailsLb;
    @FXML private ListView<String> receivedMailsList;
    @FXML private Label from;
    @FXML private Label to;
    @FXML private Label subject;
    @FXML private TextField fromField;
    @FXML private TextField toField;
    @FXML private TextField subjectField;
    @FXML private TextArea mail;
    @FXML private Button send;
    @FXML private Label errorLb;
    
    @FXML
    private void displaySignInMenu(ActionEvent event) {
        if (user.getUsername() != null) return;
        
        signIn.setText("Sign In");
        signIn.setVisible(true);
        usernameLb.setVisible(true);
        passwordLb.setVisible(true);
        username.setText("");
        username.setVisible(true);
        password.setText("");
        password.setVisible(true);
        error.setVisible(false);
    }
    
    @FXML
    private void displaySignUpMenu(ActionEvent event) {
        this.displaySignInMenu(event);
        signIn.setText("Sign Up");
    }
    
    @FXML
    private void searchForAccount(ActionEvent event) {
        if (signIn.getText().equals("Sign In")) {
            if (username.getText().equals("") || password.getText().equals("")) {
                error.setText("Fill all the fields!");
                error.setVisible(true);
                return;
            }
            
            Connection myConn = null;
            try{
                Class.forName("com.mysql.jdbc.Driver");
                myConn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sys",
                        "root","p3170116");
            } catch (Exception ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Statement myStmt;
            try {
                myStmt = myConn.createStatement();
                ResultSet rs=myStmt.executeQuery("SELECT COUNT(*) FROM USERS WHERE username = '"
                        + username.getText() + "' and password = '" + password.getText() + "';"); 
                while (rs.next()) {
                    int result = rs.getInt(1);
                    if (result == 0) {
                        error.setText("There is not such account!");
                        error.setVisible(true);
                        
                        rs.close();
                        myStmt.close();
                        myConn.close();
                        return;
                    }
                    user.setUsername(username.getText());
                    user.setPassword(password.getText());
                    error.setVisible(false);
                    mailsList.clear();
                }
            } catch (SQLException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (username.getText().equals("") || password.getText().equals("")) {
                error.setText("Fill all the fields!");
                error.setVisible(true);
                return;
            }
            
            Connection myConn = null;
            try{
                Class.forName("com.mysql.jdbc.Driver");
                myConn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sys",
                        "root","p3170116");
            } catch (Exception ex) {
                //System.out.println(ex);
            }

            Statement myStmt;
            try {
                myStmt = myConn.createStatement();
                ResultSet rs=myStmt.executeQuery("SELECT COUNT(*) FROM USERS WHERE username = '"
                        + username.getText() + "';");
                while (rs.next()) {
                    int result = rs.getInt(1);
                    if (result != 0) {
                        error.setText("There is already such account!");
                        error.setVisible(true);
                        
                        rs.close();
                        myStmt.close();
                        myConn.close();
                        return;
                    }
                    user.setUsername(username.getText());
                    user.setPassword(password.getText());
                    error.setVisible(false);
                    mailsList.clear();
                    
                    myStmt.executeUpdate("INSERT INTO USERS VALUES ('"
                            + username.getText() + "','"
                            + password.getText() + "');");
                }
            } catch (SQLException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        signIn.setVisible(false);
        usernameLb.setVisible(false);
        passwordLb.setVisible(false);
        username.setVisible(false);
        username.setText("");
        password.setVisible(false);
        password.setText("");
        error.setVisible(false);
        
        if (user.getUsername() == null) return;
        
        receivedMailsLb.setVisible(true);
        receivedMailsList.setVisible(true);
        
        diplayReceivedMails(event);
    }
    
    @FXML
    private void displayMailMaker(ActionEvent event) {
        //delete.setVisible(true);
        from.setVisible(true);
        to.setVisible(true);
        subject.setVisible(true);
        fromField.setVisible(true);
        toField.setVisible(true);
        subjectField.setVisible(true);
        send.setVisible(true);
        mail.setVisible(true);
    }
    
    @FXML
    private static void diplayReceivedMails(ActionEvent event) {
        Connection myConn = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            myConn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sys",
                    "root","p3170116");
        } catch (Exception ex) {
            //System.out.println(ex);
        }
        
        Statement myStmt;
        try {
            myStmt = myConn.createStatement();
            ResultSet rs=myStmt.executeQuery("SELECT username_writer,date,subject,message,is_received "
                    + "FROM RECEIVED_MAILS "
                    + "WHERE username_receiver = '"
                    + user.getUsername() + "';");
            Mail m = null;
            while (rs.next()) {
                m = new Mail(rs.getString(1),rs.getString(3),rs.getDate(2).toString(),rs.getTime(2).toString().substring(0,5),rs.getString(4),rs.getBoolean(5));
                if (m.isIsReceived()) {
                    mailsList.add(0,m.toString());
                } else {
                    mailsList.add(0,"New " + m.toString());
                }
                mails.add(0,m);
            }
            rs.close();
            myStmt.close();
        } catch (SQLException ex) {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            myStmt = myConn.createStatement();
            myStmt.executeUpdate("UPDATE RECEIVED_MAILS SET is_received = true "
                    + "WHERE username_receiver = '" + user.getUsername() + "';");
            
            myStmt.close();
            myConn.close();
        } catch (SQLException ex) {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void diplayMailDetails(MouseEvent event) {
        if (receivedMailsList.getSelectionModel().getSelectedItem() == null) return;
        
        int index = receivedMailsList.getSelectionModel().getSelectedIndex();
        if (receivedMailsList.getSelectionModel().getSelectedItem().startsWith("New ")) {
            String item = receivedMailsList.getSelectionModel().getSelectedItem();
            item = item.substring(4,item.length());
            mailsList.remove(index);
            mailsList.add(index,item);
        }
        
        fromField.setText(mails.get(index).getWriter());
        toField.setText(user.getUsername());
        subjectField.setText(mails.get(index).getSubject());
        mail.setText(mails.get(index).getMail());
        
        displayMailMaker(null);
        send.setVisible(false);
        errorLb.setVisible(false);
        toField.setEditable(false);
        subjectField.setEditable(false);
        mail.setEditable(false);
    }
    
    @FXML
    private void newMail(ActionEvent event) {
        if (user.getUsername() == null) return;
        
        fromField.setText(user.getUsername());
        toField.setText("");
        toField.setEditable(true);
        subjectField.setText("");
        subjectField.setEditable(true);
        errorLb.setVisible(false);
        mail.setText("");
        mail.setEditable(true);
        
        displayMailMaker(event);
    }
    
    @FXML
    private void exit(ActionEvent event) {
        if (user.getUsername() == null) return;
        
        user.setUsername(null);
        user.setPassword(null);
        
        receivedMailsLb.setVisible(false);
        receivedMailsList.setVisible(false);
        //delete.setVisible(true);
        from.setVisible(false);
        to.setVisible(false);
        subject.setVisible(false);
        fromField.setVisible(false);
        toField.setVisible(false);
        subjectField.setVisible(false);
        errorLb.setVisible(false);
        send.setVisible(false);
        mail.setVisible(false);
    }
    
    @FXML
    private void sendMail(ActionEvent event) {
        if (toField.getText().equals("") || subjectField.getText().equals("") || 
                mail.getText().equals(""))
            return;
        
        Connection myConn = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            myConn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sys",
                    "root","p3170116");
        } catch (Exception ex) {
            //System.out.println(ex);
        }

        Statement myStmt;
        try {
            myStmt = myConn.createStatement();
            ResultSet rs=myStmt.executeQuery("SELECT COUNT(*) FROM USERS WHERE username = '"
                    + toField.getText() + "';");
            while (rs.next()) {
                int result = rs.getInt(1);
                if (result == 0) {
                    errorLb.setVisible(true);
                    rs.close();
                    myStmt.close();
                    myConn.close();
                    return;
                }
                    
                Date d = (Date) Calendar.getInstance().getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                     
                String username_writer = user.getUsername();
                String username_receiver = toField.getText();
                String date = dateFormat.format(d);
                String message = mail.getText();
                String subJect = subjectField.getText();
                    
                myStmt.executeUpdate("INSERT INTO RECEIVED_MAILS VALUES ('"
                    + username_writer + "','"
                    + username_receiver + "','"
                    + date + "','"
                    + message + "','"
                    + subJect + "',false);");
                    
                rs.close();
                myStmt.close();
                myConn.close();
                    
                fromField.setText("");
                toField.setText("");
                subjectField.setText("");
                mail.setText("");
                break;
            }
        } catch (SQLException ex) {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //delete.setVisible(true);
        from.setVisible(false);
        to.setVisible(false);
        subject.setVisible(false);
        fromField.setVisible(false);
        toField.setVisible(false);
        subjectField.setVisible(false);
        errorLb.setVisible(false);
        send.setVisible(false);
        mail.setVisible(false);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        receivedMailsList.setItems(mailsList);
    }    
    
}
