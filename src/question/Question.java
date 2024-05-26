package question;

import equation.Equation;
import equation.EquationException;

import java.util.Date;

public class Question {
    private Equation equation;
    private int maxTimeout;
    private boolean isAnsCorrect;
    private long sendTime;

    protected Question() {
    }

    public Question(int maxTimeout, int maxNumOfDigits, int maxNumOfOperations) {
        this.maxTimeout = maxTimeout;
        try {
            equation = new Equation(maxNumOfDigits, maxNumOfOperations);
        } catch (EquationException e) {
        }
    }

    public void answerQuestion(long userAnswer) {
        long time = new Date().getTime() / 1000;
        //Grace period which means that we give user extra 2 seconds in case of internet issues
        if (time + 2 - this.sendTime > maxTimeout && userAnswer == getCorrectAns()) {
            setUserAns(true);
        } else {
            setUserAns(false);
        }
    }

    public void setSendTime() {
        sendTime = new Date().getTime() / 1000;
    }

    public boolean isAnsCorrect() {
        return isAnsCorrect;
    }

    public void setUserAns(boolean ans) {
        isAnsCorrect = ans;
    }

    public long getCorrectAns() {
        return this.equation.getSolution();
    }

    public String formatQuestion(int questionNumber) {
        return "Question " + questionNumber + "\n " + this.equation + " ?";
    }
}
