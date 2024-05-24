import java.util.ArrayList;

public class Quiz {

    private int numOfQuestions;
    private int maxNumOfOperations;
    private int maxNumOfDigits;
    private ArrayList<Question> questions;
    private int maxTimeout;


    protected Quiz() {

    }

    public Quiz(int numOfQuestions, int maxNumOfDigits, int maxNumOfOperations, int maxTimeout) {
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
}