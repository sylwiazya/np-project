package server;

import Stats.Statistics;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class Server {

    private ServerSocket socket;
    private int port;
    private ConnectionHandler connectionHandler;

    protected Server() {

    }

    public Server(int port) throws IOException {
        socket = new ServerSocket(port);
        this.port = port;
        socket.setSoTimeout(10000);
    }

    public void startListening() throws IOException {
        if (this.connectionHandler == null)
            throw new IOException("Connection Handler hasn't been initialized, " +
                    "please set one using onNewConnection method.");

        System.out.println("Listening to requests on port: " + port);

        while (true) {
            try {
                Socket connection = this.socket.accept();
                System.out.println("Server: Received new connection on " +
                        connection.getInetAddress() + ":" + connection.getPort());
                this.connectionHandler.setSocket(connection);
                this.connectionHandler.run();
            } catch (SocketTimeoutException s) {
                if (connectionHandler.getSocket() == null || connectionHandler.getSocket().isClosed()) {
                    System.out.println("There was no requests to connect with the server for 10 minutes ");
                    System.out.println("What would you like to do ? ");
                    System.out.println("1.. Continue waiting  ");
                    System.out.println("2.. Print statistics and terminate server ");
                    var scanner = new Scanner(System.in);
                    int response = Integer.parseInt(scanner.nextLine());
                    if (response == 1) {
                        System.out.println("Continuing to wait for connections...");
                    } else if (response == 2) {
                        Statistics.printStats();
                        socket.close();
                        System.out.println("Terminating...");
                        System.exit(1);
                    } else
                        System.out.println("Invalid choice, continuing to wait for connections...");
                }
            }
        }

    }

    public void onNewConnection(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }
}
