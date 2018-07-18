public class SubButton extends OperationButton {
    
    public SubButton(String d, Text text) {
        super(d,text);
    }
    
    @Override
    public double act(double n1, double n2) {
        return n1-n2;
    }
}
