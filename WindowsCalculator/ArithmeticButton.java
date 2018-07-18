import java.awt.Color;
import java.awt.event.*;

public class ArithmeticButton extends Button implements ActionListener {
    private Color c;
    
    public ArithmeticButton(String d, Text text) {
        super(d,text);
        c = Color.GREEN;
        ArithmeticButton.this.setBackground(c);
        ArithmeticButton.this.setOpaque(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!text.isEqualIsPressed()) {
            if (super.description.equals(".")) {
                if (text.getCurrentString().equals("")) {
                    text.setCurrentString("0.");
                    text.setText(text.getText()+"0.");
                } else if (!text.getCurrentString().contains(".")) {
                    text.setCurrentString(text.getCurrentString()+super.description);
                    super.actionPerformed(e);
                } else if (text.getText().equals("0.0")) {
                    text.setText("");
                    super.actionPerformed(e);
                }
            } else {
                text.setCurrentString(text.getCurrentString()+super.description);
                super.actionPerformed(e);
            }
            System.out.println("Balance: "+text.getBalance());
        System.out.println("String: "+text.getCurrentString());
        } else {
            if (super.description.equals(".")) {
                text.setBalance(0);
                text.setCurrentString("0.");
                text.setText("0.");
            } else {
                if (text.getCurrentString().endsWith(".")) {
                    text.setCurrentString(text.getCurrentString()+super.description);
                    text.setEqualIsPressed(false);
                    super.actionPerformed(e);
                } else {
                    text.setBalance(0);
                    text.setCurrentButton(null);
                    text.setText("");
                    text.setCurrentString(text.getCurrentString()+super.description);
                    text.setEqualIsPressed(false);
                    super.actionPerformed(e);
                }
            }
            System.out.println("Balance: "+text.getBalance());
        System.out.println("String: "+text.getCurrentString());
        }
    }
}