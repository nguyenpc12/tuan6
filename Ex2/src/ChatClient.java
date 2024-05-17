import java.io.*;
import java.net.*;

public class ChatClient {
    private static final String HOST = "localhost";
    private static final int PORT = 12345;

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket(HOST, PORT);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        // Receive and print server's welcome message
        System.out.println(in.readLine());

        // Send username to server
        String username = userInput.readLine();
        out.println(username);

        // Start a thread to listen for messages from the server
        Thread serverListener = new Thread(() -> {
            String messageFromServer;
            try {
                while ((messageFromServer = in.readLine()) != null) {
                    System.out.println(messageFromServer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        serverListener.start();

        // Read user input and send messages to the server
        String userInputMessage;
        while ((userInputMessage = userInput.readLine()) != null) {
            out.println(userInputMessage);
        }
    }
}
