import javax.swing.JTextField;

public class Text {
    private JTextField field;
    private double balance;
    private double currentNumber;
    private String currentString;
    private OperationButton currentButton;
    private boolean equalIsPressed;
    
    public Text() {
        field = new JTextField("",10);
        field.setEditable(false);
        balance = 0;
        currentNumber = 0;
        currentString = "";
        currentButton = null;
        equalIsPressed = false;
    }
    
    public JTextField getField() {
        return field;
    }
    
    public String getText() {
        return field.getText();
    }

    public void setText(String text) {
        field.setText(text);
    }
    
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    public double getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(double currentNumber) {
        this.currentNumber = currentNumber;
    }
    
    public String getCurrentString() {
        return currentString;
    }

    public void setCurrentString(String currentString) {
        this.currentString = currentString;
    }
    
    public OperationButton getCurrentButton() {
        return currentButton;
    }

    public void setCurrentButton(OperationButton currentButton) {
        this.currentButton = currentButton;
    }
    
    public boolean isEqualIsPressed() {
        return equalIsPressed;
    }

    public void setEqualIsPressed(boolean equalIsPressed) {
        this.equalIsPressed = equalIsPressed;
    }
}