package server;

import java.net.Socket;

public class ConnectionHandler implements Runnable {
    private Socket socket;

    @Override
    public void run() {
        ClientHandler clientHandler = new ClientHandler(socket);
        Thread t = new Thread(clientHandler);
        t.start();
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }
}
