package server;

import question.Question;
import question.Quiz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Predicate;

public class ClientHandler implements Runnable {
    private Socket socket;

    private ClientHandler() {
    }

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    private long getLong(PrintWriter pw, BufferedReader br, String invalidMessage,
                         Predicate<Long> validityLambda) {
        long _long;
        while (true)
            try {
                _long = Long.parseLong(br.readLine());
                if (validityLambda != null)
                    if (!validityLambda.test(_long))
                        throw new NumberFormatException();
                break;
            } catch (NumberFormatException nfe) {
                pw.println(invalidMessage);
                pw.flush();
            } catch (IOException e) {
                System.out.println(e);
            }
        return _long;
    }

    private int getInteger(PrintWriter pw, BufferedReader br, String invalidMessage,
                           Predicate<Long> validityLambda) {
        return (int) getLong(pw, br, invalidMessage, validityLambda);
    }

    private void startQuiz(Socket clientSocket, PrintWriter pw, BufferedReader br) {
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
            long answer = getLong(pw, br, "Please enter a valid numerical value", null);
            q.answerQuestion(answer);

            if (currentQuestionNumber != quiz.getNumOfQuestions()) {
                pw.println("Getting next question...");
                pw.flush();
                try {
                    // Sylwiazya: Best way?
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // Nothing is going to interrupt this thread
                }
            }
        }
        pw.println("Your grade is " + quiz.getGrade() + " / " + quiz.getNumOfQuestions());
        pw.flush();
    }

    @Override
    public void run() {
        try {
            var pw = new PrintWriter(this.socket.getOutputStream());
            var br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

            pw.println("1. Start a new quiz.");
            pw.println("2. Terminate.");
            pw.flush();
            int userChoice = getInteger(pw, br, "Please enter a number that is either 1 or 2",
                    (integer) -> integer == 1 || integer == 2);

            switch (userChoice) {
                case 1 -> this.startQuiz(this.socket, pw, br);
                case 2 -> {

                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
