public class DivButton extends OperationButton {
    
    public DivButton(String d, Text text) {
        super(d,text);
    }
    
    @Override
    public double act(double n1, double n2) {
        return (double) n1/n2;
    }
}
