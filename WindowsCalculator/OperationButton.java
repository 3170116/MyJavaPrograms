import java.awt.event.*;

public abstract class OperationButton extends Button {
    
    public OperationButton() {}
    
    public OperationButton(String d, Text text) {
        super(d,text);
    }
    
    @Override
    protected void actionPerformed(ActionEvent e) {
        if (text.getCurrentButton() == null) {
            if (!text.getText().endsWith(".") && !text.getText().equals("")) {
                if (text.isEqualIsPressed()) {
                    text.setCurrentButton(OperationButton.this);
                    text.setEqualIsPressed(false);
                    super.actionPerformed(e);
                } else {
                    if (!text.getCurrentString().equals("")) {
                        text.setBalance(Double.parseDouble(text.getCurrentString()));
                        text.setCurrentButton(OperationButton.this);
                        text.setCurrentString("");
                    }
                    super.actionPerformed(e);
                }
            }
        } else {
            if (!text.getCurrentString().equals("")) {
                text.setCurrentNumber(Double.parseDouble(text.getCurrentString()));
                text.setBalance(text.getCurrentButton().act(text.getBalance(),text.getCurrentNumber()));
                text.setCurrentButton(OperationButton.this);
                text.setCurrentString("");
                text.setText(""+text.getBalance());
                super.actionPerformed(e);
            }
        }
    }
    
    protected abstract double act(double n1, double n2);
}