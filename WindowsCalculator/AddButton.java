public class AddButton extends OperationButton {
    
    public AddButton(String d, Text text) {
        super(d,text);
    }
    
    public double act(double n1, double n2) {
        return n1+n2;
    }
}