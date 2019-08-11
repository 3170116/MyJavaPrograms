/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MailApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Jimis
 */
public class MailApp extends Application {
    private Parent root;
    private Scene mainScene;
    private Stage stage;
    
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        
        root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        mainScene = new Scene(root);
        
        stage.setResizable(false);
        stage.setScene(mainScene);
        stage.setTitle("MailApp");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        while (true) {
            try {
                FXMLDocumentController.th_searchForNewMails.run();
                FXMLDocumentController.th_searchForNewMails.wait(100000);
            } catch (Exception ex) {
                
            }
        }
    }
    
}
