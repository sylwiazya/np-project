package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.Timer;
import java.util.TimerTask;

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

//        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//        scheduler.schedule(() -> {
//            System.out.println("10 minutes passed. Performing scheduled task...");
//            // Add your task here
//
//        }, 10, TimeUnit.MINUTES);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("10 minutes passed. Performing scheduled task...");
                // Add your task here
            }
        }, 10 * 60 * 1000); // 10 minutes in milliseconds

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
