package question;

import java.io.Serializable;
import java.util.ArrayList;

public class Quiz implements Serializable {

    private int numOfQuestions;
    private int maxNumOfOperations;
    private int maxNumOfDigits;
    private ArrayList<Question> questions;
    private int maxTimeout;
    private int grade;


    protected Quiz() {

    }

    public Quiz(int numOfQuestions, int maxTimeout, int maxNumOfDigits, int maxNumOfOperations) {
        this.numOfQuestions = numOfQuestions;
        this.maxTimeout = maxTimeout;
        this.maxNumOfDigits = maxNumOfDigits;
        this.maxNumOfOperations = maxNumOfOperations;
        this.questions = new ArrayList<>();
        for (int i = 0; i < numOfQuestions; i++) {
            questions.add(new Question(maxTimeout, maxNumOfDigits, maxNumOfOperations));
        }
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public int getNumOfQuestions() {
        return numOfQuestions;
    }

    public int getMaxTimeout() {
        return this.maxTimeout;
    }

    public int getMaxNumOfOperations() {
        return this.maxNumOfOperations;
    }

    public int getMaxNumOfDigits() {
        return this.maxNumOfDigits;
    }

    public int getGrade() {
        this.grade = 0;
        this.questions.forEach(question -> this.grade += question.isUserAnswerCorrect() ? 1 : 0);
        return this.grade;
    }
}
