import server.ConnectionHandler;
import server.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        try {
            Server server = new Server(4800);
            ConnectionHandler connectionHandler = new ConnectionHandler();
            server.onNewConnection(connectionHandler);
            server.startListening();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

}