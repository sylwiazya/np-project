import equation.Equation;
import equation.EquationException;

public class Main {
    public static void main(String[] args) {
        try {
            Equation e = new Equation(4);
            System.out.println(e);
            System.out.println(e.getSolution());
        } catch (EquationException ex) {
            System.err.println(ex);
        }
    }
}