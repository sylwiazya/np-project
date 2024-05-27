package equation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Equation implements Serializable {

    private int maxNumOfDigits;
    private Double solution;
    private String equation;
    private final static ArrayList<String> OPERATIONS =
            new ArrayList<>(Arrays.asList("+", "-", "*", "/"));

    protected Equation() {

    }

    public Equation(int maxNumOfDigits, int maxNumOfOperations) throws EquationException {
        if (maxNumOfDigits < 1)
            throw new EquationTooEasyException("Is this even possible? O_o");
        if (maxNumOfDigits > 4)
            throw new EquationTooHardException("You think you're smart, huh?");

        this.maxNumOfDigits = maxNumOfDigits;
        this.equation = buildEquation(maxNumOfDigits, maxNumOfOperations);
        this.solution = Math.floor((solveEquation(this.equation) * 10.0)) / 10.0;
    }

    private String buildEquation(int maxNumOfDigits, int maxNumOfOperations) {
        StringBuilder equation = new StringBuilder();
        final int numberOfOperations = (int) (maxNumOfOperations * Math.random()) + 1;

        for (int i = 0; i < numberOfOperations; i++) {
            // This will generate a number with number of digits equal to maxNumOfDigits
            int operand = (int) (Math.random() * Math.pow(10, maxNumOfDigits));
            String operation = OPERATIONS.get((int) (Math.random() * OPERATIONS.size()));

            // This line assures that no division with zero happens
            while (!equation.isEmpty() && equation.charAt(equation.length() - 1) == '/' && operand == 0)
                operand = (int) (Math.random() * Math.pow(10, maxNumOfDigits));

            equation.append(" ");
            equation.append(operand);
            equation.append(" ");
            equation.append(operation);

            if (i == numberOfOperations - 1) {
                int lastOperand = (int) (Math.random() * Math.pow(10, maxNumOfDigits));
                while (!equation.isEmpty() && equation.charAt(equation.length() - 1) == '/' && lastOperand == 0)
                    lastOperand = (int) (Math.random() * Math.pow(10, maxNumOfDigits));
                equation.append(" ");
                equation.append(lastOperand);
            }

        }
        return equation.toString().trim();
    }

    private double solveEquation(String equation) {
        String[] tokens = equation.split(" ");
        List<String> postfix = ShuntingYard.shuntingYard(tokens);
        return ShuntingYard.evaluatePostfix(postfix);
    }

    public int getDifficulty() {
        return maxNumOfDigits;
    }

    public Double getSolution() {
        return solution;
    }

    @Override
    public String toString() {
        return this.equation;
    }
}

