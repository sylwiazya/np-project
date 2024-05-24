package equation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Equation {

    private int maxNumOfDigits;
    private long solution;
    private String equation;
    private final static ArrayList<String> OPERATIONS =
            new ArrayList<>(Arrays.asList("+", "-", "*", "/"));

    protected Equation() {

    }

    public Equation(int maxNumOfDigits, int maxNumOfOperations) throws EquationException {
        if (maxNumOfDigits < 1)
            throw new EquationTooEasyException("C'mon, this is too easy!");
        if (maxNumOfDigits > 4)
            throw new EquationTooHardException("You think you're smart, huh?");

        this.maxNumOfDigits = maxNumOfDigits;
        this.equation = buildEquation(maxNumOfDigits, maxNumOfOperations);
        this.solution = (long) solveEquation(this.equation);
    }

    private String buildEquation(int maxNumOfDigits, int maxNumOfOperations) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < maxNumOfOperations; i++) {
            // This will generate a number with number of digits equal to maxNumOfDigits
            int firstOperand = (int) (Math.random() * Math.pow(10, maxNumOfDigits));
            String operation = OPERATIONS.get((int) (Math.random() * OPERATIONS.size()));
            int secondOperand = (int) (Math.random() * Math.pow(10, maxNumOfDigits));

            // This line assures that no division with zero happens
            while (operation.equals("/") && secondOperand == 0)
                secondOperand = (int) (Math.random() * Math.pow(10, maxNumOfDigits));

            stringBuilder.append(firstOperand);
            stringBuilder.append(" ");
            stringBuilder.append(operation);
            stringBuilder.append(" ");
            stringBuilder.append(secondOperand);

            if (i != maxNumOfOperations - 1) {
                String nextOperation = OPERATIONS.get((int) (Math.random() * OPERATIONS.size()));
                stringBuilder.append(" ");
                stringBuilder.append(nextOperation);
                stringBuilder.append(" ");
            }

        }
        return stringBuilder.toString();
    }

    private double solveEquation(String equation) {
        String[] tokens = equation.split(" ");
        List<String> postfix = ShuntingYard.shuntingYard(tokens);
        return ShuntingYard.evaluatePostfix(postfix);
    }

    public int getDifficulty() {
        return maxNumOfDigits;
    }

    public long getSolution() {
        return solution;
    }

    @Override
    public String toString() {
        return this.equation;
    }
}

