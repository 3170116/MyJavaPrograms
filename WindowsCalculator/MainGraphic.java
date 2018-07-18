import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
/**
 *
 * @author Jimis
 */
public class MainGraphic extends JFrame {
    private JMenuBar bar = new JMenuBar();
    private JMenu menu;
    private JMenuItem classic;
    private JMenuItem scientific;
    private JPanel main;
    private boolean isClassic = true;
    private JPanel math;
    private Text text = new Text();
    private ArrayList<ArithmeticButton> numbers = new ArrayList<ArithmeticButton>();
    private ArrayList<MathType> maths = new ArrayList<MathType>();
    private ArrayList<OperationButton> acts = new ArrayList<OperationButton>();
    
    public MainGraphic() {
        menu = new JMenu("Options");
        classic = new JMenuItem("Classic");
        math = new JPanel();
        math.setLayout(new GridLayout(4,1,0,0));
        classic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isClassic) {
                    math.setVisible(false);
                    isClassic = true;
                }
            }
        }
        );
        scientific = new JMenuItem("Scientific");
        scientific.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isClassic) {
                    math.setVisible(true);
                    isClassic = false;
                }
            }
        }
        );
        MathType sin = new MathType("sin",text);
        MathType cos = new MathType("cos",text);
        MathType sqrt = new MathType("sqrt",text);
        MathType exp = new MathType("exp",text);
        maths.add(sin); maths.add(cos); maths.add(sqrt); maths.add(exp);
        for (MathType m: maths) {
            m.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    m.actionPerformed(e);
                }
            }
            );
            math.add(m);
        }
        math.setVisible(false);
        add(math,BorderLayout.EAST);
        menu.add(classic); menu.add(scientific); bar.add(menu);
        main = new JPanel();
        main.setLayout(new GridLayout(2,1,0,0));
        main.add(bar); main.add(text.getField());
        add(main,BorderLayout.NORTH);
        JPanel numbersPanel = new JPanel();
        numbersPanel.setLayout(new GridLayout(4,4,0,0));
        ArithmeticButton b7 = new ArithmeticButton("7",text);
        ArithmeticButton b8 = new ArithmeticButton("8",text);
        ArithmeticButton b9 = new ArithmeticButton("9",text);
        AddButton add = new AddButton("+",text);
        ArithmeticButton b4 = new ArithmeticButton("4",text);
        ArithmeticButton b5 = new ArithmeticButton("5",text);
        ArithmeticButton b6 = new ArithmeticButton("6",text);
        SubButton sub = new SubButton("-",text);
        ArithmeticButton b1 = new ArithmeticButton("1",text);
        ArithmeticButton b2 = new ArithmeticButton("2",text);
        ArithmeticButton b3 = new ArithmeticButton("3",text);
        MultiButton multi = new MultiButton("x",text);
        ArithmeticButton b0 = new ArithmeticButton("0",text);
        ArithmeticButton mark = new ArithmeticButton(".",text);
        JButton c = new JButton("C");
        c.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                text.setText("");
                text.setBalance(0);
                text.setCurrentButton(null);
                text.setCurrentString("");
                System.out.println("Balance: "+text.getBalance());
        System.out.println("String: "+text.getCurrentString());
            }
        }
        );
        numbersPanel.add(c);
        DivButton div = new DivButton("/",text);
        numbersPanel.add(b7); numbersPanel.add(b8); numbersPanel.add(b9); numbersPanel.add(add);
        numbersPanel.add(b4); numbersPanel.add(b5); numbersPanel.add(b6); numbersPanel.add(sub);
        numbersPanel.add(b1); numbersPanel.add(b2); numbersPanel.add(b3); numbersPanel.add(multi);
        numbersPanel.add(b0); numbersPanel.add(mark); numbersPanel.add(c); numbersPanel.add(div);
        add(numbersPanel);
        numbers.add(b7); numbers.add(b8); numbers.add(b9);
        numbers.add(b4); numbers.add(b5); numbers.add(b6);
        numbers.add(b1); numbers.add(b2); numbers.add(b3);
        numbers.add(b0); numbers.add(mark);
        acts.add(add); acts.add(sub); acts.add(multi); acts.add(div);
        for (ArithmeticButton b: numbers) {
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    b.actionPerformed(e);
                }
            }
            );
        }
        EqualsButton equal = new EqualsButton("=",text);
        equal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                equal.actionPerformed(e);
                System.out.println("Balance: "+text.getBalance());
        System.out.println("String: "+text.getCurrentString());
            }
        }
        );
        add(equal,BorderLayout.SOUTH);
        for (OperationButton b: acts) {
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    b.actionPerformed(e);
                    System.out.println("Balance: "+text.getBalance());
        System.out.println("String: "+text.getCurrentString());
                }
            }
            );
        }
        setTitle("Calculator");
	setBounds(400,200,450,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new MainGraphic();
    }
}