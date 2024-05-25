import server.ConnectionHandler;
import server.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

//        var q = new Quiz(5, 2, 3, 90);
//        for (int i = 0; i < q.getNumOfQuestions(); i++) {
//            System.out.println(q.getQuestions().get(i).formatQuestion(i + 1));
//        }

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