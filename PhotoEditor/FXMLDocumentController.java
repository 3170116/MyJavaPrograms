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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
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
    @FXML private Button chooseImage;
    @FXML private ComboBox box = new ComboBox();
    ObservableList<String> options = 
    FXCollections.observableArrayList("Negative","Brighter","Darker","Grey");
    @FXML private Button convert;
    @FXML private Button save;
    private String imagePath;
    private Image img = null,negImg = null;
    
    @FXML private Button collectPhotos,join;
    @FXML private Label type;
    @FXML private RadioButton jpeg = new RadioButton();
    @FXML private RadioButton png = new RadioButton();
    private ToggleGroup group = new ToggleGroup();
    @FXML private ImageView photo1,photo2;
    private String path1,path2;
    
    @FXML
    private void displayEdit(ActionEvent event) {
        collectPhotos.setVisible(false);
        join.setVisible(false);
        type.setVisible(false);
        jpeg.setVisible(false);
        png.setVisible(false);
        chooseImage.setVisible(true);
        box.setVisible(true);
        convert.setVisible(true);
        save.setVisible(true);
        photo1.setImage(null);
        photo2.setImage(null);
    }
    
    @FXML
    private void displayJoin(ActionEvent event) {
        collectPhotos.setVisible(true);
        join.setVisible(true);
        firstImage.setImage(null);
        newImage.setImage(null);
        chooseImage.setVisible(false);
        box.setVisible(false);
        convert.setVisible(false);
        save.setVisible(false);
        type.setVisible(true);
        jpeg.setVisible(true);
        png.setVisible(true);
    }
    
    @FXML
    private void openImages(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG", "*.png*"),
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
    private void collectPhoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        if (png.isSelected())
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));
        else
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
        
        File file = fileChooser.showOpenDialog(PhotoEditor.stage);
        if (file != null) {
            if (photo1.getImage() == null) {
                try {
                    photo1.setImage(new Image(new FileInputStream(file.getPath())));
                    path1 = file.getPath();
                } catch (FileNotFoundException ex) {
                    //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                 try {
                    photo2.setImage(new Image(new FileInputStream(file.getPath())));
                    path2 = file.getPath();
                } catch (FileNotFoundException ex) {
                    //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    @FXML
    private void join(ActionEvent event) {
        if (photo1.getImage() == null || photo2.getImage() == null) return;
        
        PixelReader pixelReader1 = photo1.getImage().getPixelReader();
        PixelReader pixelReader2 = photo2.getImage().getPixelReader();
        int w = (int) photo1.getImage().getWidth() + (int) photo2.getImage().getWidth();
        int h = (int) photo1.getImage().getHeight();
        WritableImage wImage = new WritableImage(w,h);
        PixelWriter pixelWriter = wImage.getPixelWriter();
        
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < (int) photo1.getImage().getWidth(); j++) {
                Color c1 = pixelReader1.getColor(j,i);
                Color c2 = pixelReader2.getColor(j,i);
                pixelWriter.setColor(j,i,c1);
                pixelWriter.setColor(j + (int) photo1.getImage().getWidth(),i,c2);
            }
        }
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        if (png.isSelected())
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));
        else
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
        
        File file = fileChooser.showSaveDialog(PhotoEditor.stage);
        if (file != null) {
            try {
                if (png.isSelected())
                    ImageIO.write(SwingFXUtils.fromFXImage(wImage,null), "jpg", file);
                else
                    ImageIO.write(SwingFXUtils.fromFXImage(wImage,null), "png", file);
            } catch (IOException ex) {
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
                        break;
                    case "Grey":
                        pixelWriter.setColor(w,h,color.grayscale());
                        break;
                }
            }
        }
        newImage.setImage(wImage);
    }
    
    @FXML private void save(ActionEvent event) {
        if (newImage.getImage() == null) return;
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        if (imagePath.endsWith("png"))
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));
        else
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
        
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
    
    @FXML
    private void deletePhotos(ActionEvent event) {
        photo1.setImage(null);
        photo2.setImage(null);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        box.setItems(options);
        jpeg.setToggleGroup(group);
        png.setToggleGroup(group);
        jpeg.setSelected(true);
    }    
    
}
