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

    }
}
