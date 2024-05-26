import equation.Equation;
import equation.EquationException;

public class Main {
    public static void main(String[] args) {
        try {
            for (int i = 0; i < 1000; i++) {
                new Equation(0, 5);
            }
        } catch (EquationException ex) {
            System.err.println(ex);
        }
    }

}