package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        int port = 4800;
        try {
            Socket s = new Socket(InetAddress.getLoopbackAddress(), port);
            var pw = new PrintWriter(s.getOutputStream());
            var br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println(br.readLine());
                pw.println(scanner.nextLine());
                pw.flush();
            }

        } catch (IOException e) {
            System.err.println(e);
        }

    }
}
