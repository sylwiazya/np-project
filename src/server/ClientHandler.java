package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;

    private ClientHandler() {
    }

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            var dis = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            var pw = new PrintWriter(socket.getOutputStream());
            socket.setSoTimeout(4 * 1000);

            pw.println("Choose one of the two option: 1- Read from file. 2- Write to file.");
            pw.flush();
            int choice;
            while (true) {
                try {
                    choice = Integer.parseInt(dis.readLine());
                    if (choice != 1 && choice != 2) {
                        pw.println("Invalid option, please try again");
                        pw.flush();
                        continue;
                    }
                    break;
                } catch (NumberFormatException nfe) {
                    pw.println("Invalid option, please try again");
                    pw.flush();
                }
            }
            switch (choice) {
                case 1 -> {
                    pw.println("Enter name of file you want to read");
                    pw.flush();
                }
                case 2 -> {
                    pw.println("Enter name of file you want to write to");
                    pw.flush();
                }
            }
            this.socket.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
