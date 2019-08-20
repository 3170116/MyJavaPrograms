/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photoeditor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/**
 *
 * @author Jimis
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML private ImageView firstImage;
    @FXML private ImageView newImage;
    @FXML private ComboBox box = new ComboBox();
    ObservableList<String> options = 
    FXCollections.observableArrayList("Negative","Brighter","Darker");
    @FXML private Button convert;
    @FXML private Button save;
    
    private String imagePath;
    private Image img = null,negImg = null;
    
    @FXML
    private void openImages(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg")
        );
        
        File file = fileChooser.showOpenDialog(PhotoEditor.stage);
        if (file != null) {
            try {
                img = new Image(new FileInputStream(file.getPath()));
                negImg = new Image(new FileInputStream(file.getPath()));
                imagePath = file.getPath();
                firstImage.setImage(img);
                newImage.setImage(null);
                convert.setDisable(false);
                save.setDisable(false);
            } catch (FileNotFoundException ex) {
                //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @FXML
    private void convert(ActionEvent event) {
        PixelReader pixelReader = negImg.getPixelReader();
        WritableImage wImage = new WritableImage((int)img.getWidth(),(int)img.getHeight());
        PixelWriter pixelWriter = wImage.getPixelWriter();
        
        try {
            negImg = new Image(new FileInputStream(imagePath));
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int h = 0; h < negImg.getHeight(); h++) {
            for (int w = 0; w < negImg.getWidth(); w++) {
                Color color = pixelReader.getColor(w,h);
                if (box.getSelectionModel().getSelectedItem() == null)
                    break;
                switch (box.getSelectionModel().getSelectedItem().toString()) {
                    case "Negative":
                        double r = color.getRed();
                        double g = color.getGreen();
                        double b = color.getBlue();

                        r = 1.0 - r;
                        g = 1.0 - g;
                        b = 1.0 - b;

                        r = Math.max(0.0,Math.min(r,1.0));
                        g = Math.max(0.0,Math.min(g,1.0));
                        b = Math.max(0.0,Math.min(b,1.0));

                        pixelWriter.setColor(w,h,new Color(r,g,b,1.0));
                        break;
                    case "Brighter":
                        pixelWriter.setColor(w,h,color.brighter());
                        break;
                    case "Darker":
                        pixelWriter.setColor(w,h,color.darker());
                }
            }
        }
        newImage.setImage(wImage);
    }
    
    @FXML private void save(ActionEvent event) {
        if (newImage.getImage() == null) return;
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        
        File file = fileChooser.showSaveDialog(PhotoEditor.stage);
        if (file != null) {
            try {
                if (imagePath.endsWith("png"))
                    ImageIO.write(SwingFXUtils.fromFXImage(newImage.getImage(),null), "jpg", file);
                else
                    ImageIO.write(SwingFXUtils.fromFXImage(newImage.getImage(),null), "png", file);
            } catch (IOException ex) {
                //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        box.setItems(options);
    }    
    
}
