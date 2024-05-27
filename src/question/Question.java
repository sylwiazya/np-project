package question;

import equation.Equation;
import equation.EquationException;

import java.io.Serializable;
import java.util.Date;

public class Question implements Serializable {
    private Equation equation;
    // In seconds
    private int maxTimeout;
    private boolean isUserAnswerCorrect;
    // In seconds
    private long sendTime;
    // To take internet delay into account, users will get an additional 2 seconds for each question
    private static final int GRACE_PERIOD = 2;

    protected Question() {
    }

    public Question(int maxTimeout, int maxNumOfDigits, int maxNumOfOperations) {
        this.maxTimeout = maxTimeout;
        try {
            equation = new Equation(maxNumOfDigits, maxNumOfOperations);
        } catch (EquationException e) {
            System.out.println("Maximum number of digits must be between 1 & 4 inclusive.");
        }
    }

    public void answerQuestion(double userAnswer) {
        long time = new Date().getTime() / 1000;
        long elapsedTime = time - GRACE_PERIOD - this.sendTime;
        //Grace period which means that we give user extra 2 seconds in case of internet issues
        if ((elapsedTime <= this.maxTimeout || this.maxTimeout == 0) && userAnswer == this.getCorrectAns())
            setUserAnsCorrect(true);
        else
            setUserAnsCorrect(false);
        System.out.println("time: " + time + " sendTime: " + this.sendTime + " maxTimeout "
                + this.maxTimeout + " elapsedTime " + elapsedTime);
    }

    public void setSendTime() {
        this.sendTime = new Date().getTime() / 1000;
    }

    public boolean isUserAnswerCorrect() {
        return this.isUserAnswerCorrect;
    }

    private void setUserAnsCorrect(boolean ans) {
        this.isUserAnswerCorrect = ans;
    }

    private Double getCorrectAns() {
        return this.equation.getSolution();
    }

    public String formatQuestion(int questionNumber) {
        return "Question " + questionNumber + ": What does the following expression evaluate to?\n" + this.equation;
    }

    /**
     * Sets the Send Time for the question and calls format Question with the passed argument
     */
    public String showQuestion(int questionNumber) {
        this.setSendTime();
        return this.formatQuestion(questionNumber);
    }

}
