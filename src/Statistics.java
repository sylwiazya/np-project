import java.util.ArrayList;

public final class Statistics {
    private static int numOfQuizzes;
    private static int oneOperationQuizzes;
    private static int twoOperationQuizzes;
    private static int threeOperationQuizzes;
    private static int fourOperationQuizzes;
    private static ArrayList<Quiz> quizzes;

    public static void printStats(){
        System.out.println(
        "Number of quizzes: "+numOfQuizzes
        +"\nNumber of quizzes which include one operation: "+oneOperationQuizzes
        +"\nNumber of quizzes which include two operation as a max: "+twoOperationQuizzes
        +"\nNumber of quizzes which include three operation as a max: "+threeOperationQuizzes
        +"\nNumber of quizzes which include four operation as a max: "+fourOperationQuizzes);
        int[] totalGrades = new int[5];
        for (int i =0;i<quizzes.size();i++){
            float ratio = quizzes.get(i).getGrade()/quizzes.get(i).getNumOfQuestions();
            if (ratio < 0.25)
                totalGrades[0]++;
            else if (ratio < 0.5)    
                totalGrades[1]++;
            else if (ratio < 0.75)    
                totalGrades[2]++;
            else if (ratio < 1)    
                totalGrades[3]++;         
            else 
                totalGrades[4]++;    
        }
        System.out.println("The number of quizzes that acheived a grade below 25% is : "+ totalGrades[0]);
        System.out.println("The number of quizzes that acheived a grade between 25% & 50% is : "+ totalGrades[1]);
        System.out.println("The number of quizzes that acheived a grade between 50% & 75% is : "+ totalGrades[2]);
        System.out.println("The number of quizzes that acheived a grade between 75% & %100(not included) is : "+ totalGrades[3]);
        System.out.println("The number of quizzes that acheived a grade equal to 100% is : "+ totalGrades[5]);

    }

    public synchronized static void incrementNumOfQuizzes() {
        numOfQuizzes++;
    }

    public synchronized static void incrementOneOpQuizzes() {
        oneOperationQuizzes++;
    }

    public synchronized static void incrementTwoOpQuizzes() {
        twoOperationQuizzes++;
    }

    public synchronized static void incrementThreeOpQuizzes() {
        threeOperationQuizzes++;
    }

    public synchronized static void incrementFourOpQuizzes() {
        fourOperationQuizzes++;
    }


    public synchronized static void addQuiz(Quiz q) {
        quizzes.add(q);
    }

    public synchronized static int getNumOfQuizzes() {
        return numOfQuizzes;
    }

    public synchronized static int getOneOperationQuizzes() {
        return oneOperationQuizzes;
    }

    public synchronized static int getTwoOperationQuizzes() {
        return twoOperationQuizzes;
    }

    public synchronized static int getThreeOperationQuizzes() {
        return threeOperationQuizzes;
    }

    public synchronized static int getFourOperationQuizzes() {
        return fourOperationQuizzes;
    }
}

