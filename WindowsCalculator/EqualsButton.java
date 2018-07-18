import java.awt.event.*;

public class EqualsButton extends Button {
    
    public EqualsButton(String d, Text text) {
        super(d,text);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (text.getCurrentButton() == null) {
            if (!text.getCurrentString().equals("") && !text.getCurrentString().endsWith(".")) {
                text.setBalance(Double.parseDouble(text.getCurrentString()));
            }
            text.setCurrentString("");
            if (isInteger(text.getBalance())) {
                text.setText(""+ (int) text.getBalance());
            } else {
                text.setText(""+text.getBalance());
            }
        } else {
            if (!text.getCurrentString().equals("") && !text.getCurrentString().endsWith(".")) {
                text.setCurrentNumber(Double.parseDouble(text.getCurrentString()));
                text.setBalance(text.getCurrentButton().act(text.getBalance(),text.getCurrentNumber()));
                text.setCurrentString("");
                if (isInteger(text.getBalance())) {
                    text.setText(""+ (int) text.getBalance());
                } else {
                    text.setText(""+text.getBalance());
                }
                text.setCurrentButton(null);
            }
        }
        text.setEqualIsPressed(true);
    }
    
    public boolean isInteger(double number) {
        return (int) number == number;
    }
}