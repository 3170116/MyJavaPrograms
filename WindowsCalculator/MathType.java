import java.awt.event.ActionEvent;

public class MathType extends OperationButton {

    public MathType(String d, Text t) {
        super(d,t);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (text.isEqualIsPressed()) {
            if (text.getCurrentButton() == null) {
                text.setCurrentButton(this);
                /*text.setBalance(this.culc(text.getBalance()));
                text.setText(""+text.getBalance());*/
                text.setEqualIsPressed(false);
            } else {
                if (!text.getCurrentString().equals("")) {
                    text.setCurrentNumber(Double.parseDouble(text.getCurrentString()));
                    text.setBalance(text.getCurrentButton().act(text.getBalance(),text.getCurrentNumber()));
                    text.setCurrentButton(this);
                    text.setCurrentString("");
                    text.setText(""+text.getBalance());
                }
            }
        } else {
            if (!text.getCurrentString().equals("")) {
                text.setCurrentString(""+this.culc(Double.parseDouble(text.getCurrentString())));
                if (text.getCurrentButton() == null) {
                    text.setText(text.getCurrentString());
                } else {
                    text.setText(text.getText() + text.getCurrentString());
                }
            }
        }
        System.out.println("Balance: "+text.getBalance());
        System.out.println("String: "+text.getCurrentString());
    }
    
    public double culc(double number){
        double result = number;
        switch (super.description) {
            case "sin": result = Math.sin(number);
            case "cos": result = Math.cos(number);
            case "sqrt": result = Math.sqrt(number);
            case "exp": result = Math.exp(number);
        }
        return result;
    }
    
    public double act(double n1, double n2) {
        return 0;
    }
}