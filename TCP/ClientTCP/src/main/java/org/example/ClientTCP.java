package org.example;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.ClosedChannelException;
import java.util.Scanner;

/**
 * ClientTCP class that connects to a server using TCP, sends data, and handles responses.
 */
public class ClientTCP {
    private Socket socket;

    /**
     * Constructor that initializes the client and connects to the server.
     *
     * @param hostname The hostname or IP address of the server.
     * @param port The port number on which the server is listening.
     */
    public ClientTCP(String hostname, int port) {
        try {
            // Attempt to connect to the server using the provided hostname and port.
            this.socket = new Socket(hostname, port);
        } catch (UnknownHostException e) {
            System.err.println("Could not connect to server. Terminating!");
            System.exit(1);
        } catch (ClosedChannelException e) {
            System.err.println("Could not connect to server. Terminating!");
            System.exit(1);
        } catch (IIOException e) {
            System.err.println("Could not connect to server on this port number. Terminating!");
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a string and keyword to the server and prints the server's response.
     *
     * @param str The string to send to the server.
     * @param keyword A keyword that may be relevant to the server's processing.
     */
    public void sendData(String str, String keyword) {
        try {
            // Create output and input streams for communication.
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Send the string and keyword to the server.
            out.println(str);
            out.println(keyword);

            String response;
            boolean hasReceived = false;

            // Read and print the response from the server.
            while ((response = in.readLine()) != null) {
                System.out.println(response);
                hasReceived = true;
            }

            // Check if no response was received from the server.
            if (!hasReceived) {
                System.err.println("Could not connect to server on this port number. Terminating!");
                System.exit(1);
            }
        } catch (IOException e) {
            System.err.println("Failed to send expression. Terminating!");
            System.exit(1);
        }
    }

    /**
     * Closes the socket connection.
     */
    public void closeSocket() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e){
            System.err.println("Error closing socket: " + e.getMessage());
        }
    }

    /**
     * Main method for running the client.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get hostname and port from the user.
        System.out.println("Enter server name or IP address: ");
        String hostname = scanner.nextLine();

        System.out.println("Enter port: ");
        int port = Integer.parseInt(scanner.nextLine());

        // Get the string and keyword to send to the server.
        System.out.print("Enter string: ");
        String serverString = scanner.nextLine();

        System.out.print("Enter keyword: ");
        String keyword = scanner.nextLine();

        // Validate the port number.
        if (port < 1024 || port > 49151) {
            System.err.println("Invalid port number. Terminating!");
            System.exit(1);
        }

        // Create the client and send the data.
        ClientTCP tcpClient = new ClientTCP(hostname, port);
        tcpClient.sendData(serverString, keyword);

        // Close the socket after communication.
        tcpClient.closeSocket();
    }
}
