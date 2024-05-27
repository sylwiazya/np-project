package Stats;

import question.Quiz;
import util.PieChart;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public final class Statistics {
    private static int numOfQuizzes;
    private static int oneOperationQuizzes;
    private static int twoOperationQuizzes;
    private static int threeOperationQuizzes;
    private static int fourOperationQuizzes;
    private static ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
    public static final String FILE_NAME = "Stats.out";


    public static void initStats() {
        try (ObjectInputStream oReader = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            numOfQuizzes = oReader.readInt();
            oneOperationQuizzes = oReader.readInt();
            twoOperationQuizzes = oReader.readInt();
            threeOperationQuizzes = oReader.readInt();
            fourOperationQuizzes = oReader.readInt();
            quizzes = (ArrayList<Quiz>) oReader.readObject();
        } catch (EOFException e) {
            System.out.println("No data is found ,starting with empty statistics");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to initialize statistics");
        }
    }

    public synchronized static void updateStats() {
        try (ObjectOutputStream oWriter = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oWriter.writeInt(numOfQuizzes);
            oWriter.writeInt(oneOperationQuizzes);
            oWriter.writeInt(twoOperationQuizzes);
            oWriter.writeInt(threeOperationQuizzes);
            oWriter.writeInt(fourOperationQuizzes);
            oWriter.writeObject(quizzes);
            oWriter.flush();
        } catch (IOException e) {
            System.err.println("Failed to update statistics" + e);
        }
    }

    public static void printStats() {
        System.out.printf("%-85s %d%n", "Number of quizzes:",
                numOfQuizzes);
        System.out.printf("%-85s %d%n", "Number of quizzes which include one operation:",
                oneOperationQuizzes);
        System.out.printf("%-85s %d%n", "Number of quizzes which include two operations as a max:",
                twoOperationQuizzes);
        System.out.printf("%-85s %d%n", "Number of quizzes which include three operations as a max:",
                threeOperationQuizzes);
        System.out.printf("%-85s %d%n", "Number of quizzes which include four operations as a max:",
                fourOperationQuizzes);
        System.out.println();
        int[] totalGrades = new int[5];
        try {
            for (Quiz quiz : quizzes) {
                float gradeRatio = quiz.getGrade() / (float) quiz.getNumOfQuestions();
                if (gradeRatio < 0.25)
                    totalGrades[0]++;
                else if (gradeRatio < 0.5)
                    totalGrades[1]++;
                else if (gradeRatio < 0.75)
                    totalGrades[2]++;
                else if (gradeRatio < 1)
                    totalGrades[3]++;
                else
                    totalGrades[4]++;
            }
            System.out.printf(
                    "%-85s %d%n", "The number of quizzes that achieved a grade below 25% is:",
                    totalGrades[0]
            );
            System.out.printf(
                    "%-85s %d%n", "The number of quizzes that achieved a grade between 25% & 50% is:",
                    totalGrades[1]
            );
            System.out.printf(
                    "%-85s %d%n", "The number of quizzes that achieved a grade between 50% & 75% is:",
                    totalGrades[2]
            );
            System.out.printf(
                    "%-85s %d%n", "The number of quizzes that achieved a grade between 75% & 100% (not included) is:",
                    totalGrades[3]
            );
            System.out.printf(
                    "%-85s %d%n", "The number of quizzes that achieved a grade equal to 100% is:",
                    totalGrades[4]
            );

            drawStats(totalGrades, new String[]{
                    "The number of quizzes that achieved a grade below 25%",
                    "The number of quizzes that achieved a grade between 25% & 50%",
                    "The number of quizzes that achieved a grade between 50% & 75%",
                    "The number of quizzes that achieved a grade between 75% & 100% (not included)",
                    "The number of quizzes that achieved a grade equal to 100%",
            });

        } catch (NullPointerException e) {
            System.out.println("No Quizzes taken yet!");
        }
    }

    public static void drawStats(int[] values, String[] labels) {
        JFrame frame = new JFrame("Pie Chart of Student Grades");
        float[] percentages = new float[values.length];
        for (int i = 0; i < percentages.length; i++)
            percentages[i] = values[i] / (float) values.length;

        PieChart panel = new PieChart(values, percentages, labels);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(1200, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        try {
            Thread.sleep(60 * 10 * 1000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
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

}

