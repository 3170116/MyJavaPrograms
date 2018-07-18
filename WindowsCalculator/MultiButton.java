public class MultiButton extends OperationButton {
    
    public MultiButton(String d, Text text) {
        super(d,text);
    }
    
    @Override
    public double act(double n1, double n2) {
        return n1*n2;
    }
}