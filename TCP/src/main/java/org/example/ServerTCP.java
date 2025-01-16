package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ServerTCP class that listens for incoming client connections, handles communication,
 * anonymizes text based on keyword, and sends responses.
 */
public class ServerTCP {
    private ServerSocket serverSocket;

    /**
     * Constructor that initializes the server and listens on the specified port.
     *
     * @param port The port number to listen for incoming connections.
     */
    public ServerTCP(int port) {
        try {
            // Create a ServerSocket to listen on the given port.
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handles the connection with a client. Reads input, processes it, and sends a response.
     *
     * @param socket The socket connection with the client.
     */
    public void handleConnection(Socket socket) {
        try {
            // Set up input and output streams for communication.
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Read the string and keyword sent by the client.
            String str = in.readLine();
            String keyword = in.readLine();

            // If the string or keyword is invalid, terminate the connection.
            if (str == null || keyword == null || str.isEmpty() || keyword.isEmpty()) {
                System.err.println("Did not receive valid string from client. Terminating!");
                closeSocket(socket);  // Close the socket to terminate the connection
                return;
            }

            // Anonymize the string and count occurrences of the keyword.
            String anonymizedStr = textAnonymizer(str, keyword);
            int occurrences = countKeywordOccurrences(str, keyword);

            // Send the anonymized string to the client.
            out.println(anonymizedStr);

            // Send the message "Socket Programming" for each occurrence of the keyword.
            for (int i = 0; i < occurrences; i++) {
                out.println("Socket Programming");
            }
        } catch (Exception e) {
            System.err.println("Result transmission failed. Terminating!");
        } finally {
            // Ensure the socket is closed after the communication ends.
            closeSocket(socket);
        }
    }

    /**
     * Anonymizes the input string by replacing the keyword with 'X' characters.
     *
     * @param str The input string.
     * @param keyword The keyword to be anonymized.
     * @return The anonymized string with the keyword replaced by 'X' characters.
     */
    public String textAnonymizer(String str, String keyword) {
        return str.replaceAll(keyword, "X".repeat(keyword.length()));
    }

    /**
     * Counts the occurrences of the keyword in the input string.
     *
     * @param str The input string.
     * @param keyword The keyword to count occurrences of.
     * @return The number of times the keyword appears in the string.
     */
    public int countKeywordOccurrences(String str, String keyword) {
        return (str.length() - str.replace(keyword, "").length()) / keyword.length();
    }

    /**
     * Closes the socket connection.
     *
     * @param socket The socket to be closed.
     */
    private void closeSocket(Socket socket) {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing socket: " + e.getMessage());
        }
    }

    /**
     * Waits for incoming client connections and processes each connection.
     */
    public void waitConnection() {
        while (true) {
            try {
                // Wait for an incoming connection and handle it.
                Socket socket = serverSocket.accept();
                handleConnection(socket);
            } catch (IOException e) {
                System.err.println("Server exception: " + e.getMessage());
            }
        }
    }

    /**
     * Main method to start the server and begin listening for connections.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        // Ensure the user provides a port number as an argument.
        if (args.length != 1) {
            System.err.println("Usage: java ServerTCP <port number>");
            System.exit(1);
        }

        // Parse the port number and start the server.
        int port = Integer.parseInt(args[0]);
        ServerTCP tcpServer = new ServerTCP(port);
        tcpServer.waitConnection();
    }
}
