package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class Client {
    public final static int SERVER_PORT = 4800;

    public static void main(String[] args) {
        try {
            Socket s = new Socket(InetAddress.getLoopbackAddress(), SERVER_PORT);
            var pw = new PrintWriter(s.getOutputStream());
            var br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            var scanner = new Scanner(System.in);
            // Arbitrary
            s.setSoTimeout(2500);
            while (true) {
                while (true)
                    try {
                        String msgFromServer = br.readLine();
                        if (msgFromServer == null)
                            return;
                        System.out.println(msgFromServer);
                    } catch (SocketTimeoutException ste) {
                        break;
                    }
                String msgFromUser = scanner.nextLine();
                pw.println(msgFromUser);
                pw.flush();
            }

        } catch (IOException e) {
            System.err.println(e);
        }

    }
}
