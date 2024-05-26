import server.ConnectionHandler;
import server.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Server server = new Server(4800);
            server.onNewConnection(new ConnectionHandler());
            server.startListening();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

}