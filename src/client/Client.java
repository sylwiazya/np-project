package client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public final static int SERVER_PORT = 4800;

    public static void main(String[] args) {
        try {
            Socket s = new Socket(InetAddress.getLoopbackAddress(), SERVER_PORT);
            var pw = new PrintWriter(s.getOutputStream());
            var br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            var scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Menu :-");
                System.out.println("1.. Start a new quiz ");
                System.out.println("2.. Terminate ");
                System.out.print("Your Choice : ");
                String choice;
                choice = scanner.nextLine();
                if (Integer.parseInt(choice) == 1) {
                    //Client configuration of the quiz
                    System.out.println("What is the wanted number of questions in the quiz ? ");
                    pw.println(scanner.nextLine());
                    System.out.println("Would you like to have a specific period of time for every question ? " +
                            "If so, please enter the period in seconds. If not, please enter 0");
                    pw.println(scanner.nextLine());
                    System.out.print("The difficulty of the quiz will be specified by two options :" +
                            "\n1.. please enter the MAX number of operations in each question : ");
                    pw.println(scanner.nextLine());
                    System.out.print("2.. please enter the MAX number of digits in each operand " +
                            "in the question : ");
                    pw.println(scanner.nextLine());
                    pw.flush();
                    //Server respond that consist of the quiz
                    //...
                } else if (Integer.parseInt(choice) == 2) {
                    s.close();
                    break;
                } else {
                    System.err.println("Wrong input type!");
                }
            }

        } catch (IOException e) {
            System.err.println(e);
        }

    }
}
