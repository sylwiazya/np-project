package server;

import Stats.Statistics;
import question.Question;
import question.Quiz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.function.Predicate;

public class ClientHandler implements Runnable {
    private Socket socket;

    private ClientHandler() {
    }

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    private double getDouble(PrintWriter pw, BufferedReader br, String invalidMessage,
                             Predicate<Double> validityLambda) throws SocketException {
        double number;
        while (true)
            try {
                number = Double.parseDouble(br.readLine());
                if (validityLambda != null)
                    if (!validityLambda.test(number))
                        throw new NumberFormatException();
                break;
            } catch (NumberFormatException nfe) {
                pw.println(invalidMessage);
                pw.flush();
            } catch (SocketException soe) {
                throw soe;
            } catch (IOException e) {
                System.out.println(e);
            }
        return number;
    }

    private int getInteger(PrintWriter pw, BufferedReader br, String invalidMessage,
                           Predicate<Double> validityLambda) throws SocketException {
        return (int) getDouble(pw, br, invalidMessage, validityLambda);
    }

    private void startQuiz(Socket clientSocket, PrintWriter pw, BufferedReader br) {
        try {
            pw.println("Please enter the number of questions: ");
            pw.flush();
            int numOfQuestions = getInteger(pw, br, "Please enter a valid integer greater than 0",
                    (integer) -> integer > 0);

            pw.println("Please enter time for each question. If you want an unlimited time, enter 0: ");
            pw.flush();
            int maxTimeout = getInteger(pw, br, "Please enter a valid integer greater than or equal to zero",
                    (integer) -> integer >= 0);

            pw.println("Please enter the maximum number of digits in each operand in a question (between 1 and 4): ");
            pw.flush();
            int maxNumberOfDigits = getInteger(pw, br, "Please enter a valid integer between 1 and 4",
                    (integer) -> integer >= 1 && integer <= 4);

            pw.println("Please enter the maximum number of operations in each question: ");
            pw.flush();
            int maxNumberOfOperations = getInteger(pw, br, "Please enter a valid integer greater than 0",
                    (integer) -> integer > 0);

            pw.println("Starting Quiz...");
            pw.flush();
            Quiz quiz = new Quiz(numOfQuestions, maxTimeout, maxNumberOfDigits, maxNumberOfOperations);

            int currentQuestionNumber = 0;
            for (Question q : quiz.getQuestions()) {
                currentQuestionNumber++;
                pw.println(q.showQuestion(currentQuestionNumber));
                pw.flush();
                double answer = getDouble(pw, br, "Please enter a valid numerical value", null);
                q.answerQuestion(answer);

                if (currentQuestionNumber != quiz.getNumOfQuestions()) {
                    pw.println("Getting next question...");
                    pw.flush();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        // Nothing is going to interrupt this thread
                    }
                }
            }
            pw.println("Your grade is " + quiz.getGrade() + " / " + quiz.getNumOfQuestions());
            pw.flush();

            //Adding the info of the quiz into Statistics class
            Statistics.incrementNumOfQuizzes();
            Statistics.addQuiz(quiz);
            switch (maxNumberOfOperations) {
                case 1 -> Statistics.incrementOneOpQuizzes();
                case 2 -> Statistics.incrementTwoOpQuizzes();
                case 3 -> Statistics.incrementThreeOpQuizzes();
                case 4 -> Statistics.incrementFourOpQuizzes();
            }
            Statistics.updateStats();


        } catch (SocketException soe) {
            System.out.println("User disconnected unexpectedly");
            try {
                socket.close();
            } catch (IOException ioe) {
            }
        }
    }

    @Override
    public void run() {
        try {
            var pw = new PrintWriter(this.socket.getOutputStream());
            var br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

            while (true) {
                pw.println("1. Start a new quiz.");
                pw.println("2. Terminate.");
                pw.flush();

                int userChoice = getInteger(pw, br, "Please enter a number that is either 1 or 2",
                        (integer) -> integer == 1 || integer == 2);

                switch (userChoice) {
                    case 1 -> this.startQuiz(this.socket, pw, br);
                    case 2 -> {
                        pw.println("Terminating connection...");
                        pw.flush();
                        socket.close();
                        return;
                    }
                }
            }
        } catch (SocketException se) {
            System.out.println("User disconnected unexpectedly " + se);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
