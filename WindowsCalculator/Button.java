import java.awt.event.ActionEvent;
import javax.swing.JButton;

public abstract class Button extends JButton {
    protected Text text;
    protected String description;
    protected boolean isAvailable;
    
    public Button() {}

    public Button(String d, Text text) {
        description = d;
        this.text = text;
        setText(description);
    }
    
    protected String getDescription() {
        return description;
    }

    protected void setDescription(String descpription) {
        this.description = descpription;
    }
    
    protected boolean getIsAvailable() {
        return isAvailable;
    }

    protected void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    
    protected void setText(Text text) {
        this.text = text;
    }
    
    protected void actionPerformed(ActionEvent e) {
        text.setText(text.getText() + description);
    }
}