package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket socket;
    private int port;
    private ConnectionHandler connectionHandler;

    protected Server() {

    }

    public Server(int port) throws IOException {
        socket = new ServerSocket(port);
        this.port = port;
    }

    public void startListening() throws IOException {
        if (this.connectionHandler == null)
            throw new IOException("Connection Handler hasn't been initialized, " +
                    "please set one using onNewConnection method.");

        System.out.println("Listening to requests on port: " + port);
        while (true) {
            Socket connection = this.socket.accept();
            this.connectionHandler.setSocket(connection);
            this.connectionHandler.run();
        }

    }

    public void onNewConnection(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }
}
