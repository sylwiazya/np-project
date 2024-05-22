package equation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Equation {

    private int difficulty;
    private long solution;
    private String equation;
    private final static ArrayList<String> OPERATIONS =
            new ArrayList<>(Arrays.asList("+", "-", "*", "/"));

    protected Equation() {

    }

    public Equation(int difficulty) throws EquationException {
        if (difficulty < 1)
            throw new EquationTooEasyException("C'mon, this is too easy!");
        if (difficulty > 4)
            throw new EquationTooHardException("You think you're smart, huh?");

        this.difficulty = difficulty;
        this.equation = buildEquation(difficulty);
        this.solution = (long) solveEquation(this.equation);
    }

    private String buildEquation(int difficulty) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < difficulty; i++) {
            // This will generate a number with number of digits equal to difficulty
            int firstOperand = (int) (Math.random() * Math.pow(10, difficulty));
            String operation = OPERATIONS.get((int) (Math.random() * OPERATIONS.size()));
            int secondOperand = (int) (Math.random() * Math.pow(10, difficulty));

            // This line assures that no division with zero happens
            while (operation.equals("/") && secondOperand == 0)
                secondOperand = (int) (Math.random() * Math.pow(10, difficulty));

            stringBuilder.append(firstOperand);
            stringBuilder.append(" ");
            stringBuilder.append(operation);
            stringBuilder.append(" ");
            stringBuilder.append(secondOperand);

            if (i != difficulty - 1) {
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
        return difficulty;
    }

    public long getSolution() {
        return solution;
    }

    @Override
    public String toString() {
        return this.equation;
    }
}

