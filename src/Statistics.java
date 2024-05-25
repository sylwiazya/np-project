import java.util.ArrayList;

public final class Statistics {
    private static int numOfQuizzes;
    private static int oneOperationQuizzes;
    private static int twoOperationQuizzes;
    private static int threeOperationQuizzes;
    private static int fourOperationQuizzes;
    private static ArrayList<Quiz> quizzes;

    public static void incrementNumOfQuizzes() {
        numOfQuizzes++;
    }

    public static void incrementOneOpQuizzes() {
        oneOperationQuizzes++;
    }

    public static void incrementTwoOpQuizzes() {
        twoOperationQuizzes++;
    }

    public static void incrementThreeOpQuizzes() {
        threeOperationQuizzes++;
    }

    public static void incrementFourOpQuizzes() {
        fourOperationQuizzes++;
    }


    public static void addQuiz(Quiz q) {
        quizzes.add(q);
    }

    public static int getNumOfQuizzes() {
        return numOfQuizzes;
    }

    public static int getOneOperationQuizzes() {
        return oneOperationQuizzes;
    }

    public static int getTwoOperationQuizzes() {
        return twoOperationQuizzes;
    }

    public static int getThreeOperationQuizzes() {
        return threeOperationQuizzes;
    }

    public static int getFourOperationQuizzes() {
        return fourOperationQuizzes;
    }
}

