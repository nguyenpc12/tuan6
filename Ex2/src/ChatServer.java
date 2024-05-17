import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 12345;
    private static Set<PrintWriter> clientWriters = new HashSet<>();

    public static void main(String[] args) throws Exception {
        System.out.println("The chat server is running...");
        ServerSocket serverSocket = new ServerSocket(PORT);
        try {
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } finally {
            serverSocket.close();
        }
    }

    private static class ClientHandler extends Thread {
        private String username;
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Ask for username
                out.println("Please enter your username: ");
                username = in.readLine();
                broadcast(username + " has joined the chat.");

                // Add PrintWriter to set of client writers
                synchronized (clientWriters) {
                    clientWriters.add(out);
                }

                // Process messages from client
                String message;
                while ((message = in.readLine()) != null) {
                    if (message.equalsIgnoreCase("!quit")) {
                        return;
                    }
                    broadcast(username + ": " + message);
                }
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                if (username != null) {
                    broadcast(username + " has left the chat.");
                }
                synchronized (clientWriters) {
                    clientWriters.remove(out);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }

        private void broadcast(String message) {
            synchronized (clientWriters) {
                for (PrintWriter writer : clientWriters) {
                    writer.println(message);
                }
            }
        }
    }
}
