package cs250.HW3;

import java.net.*;
import java.io.*;

public class Server {
    private static Socket clientSocket;
    private static ServerSocket serverSocket;
    private static DataOutputStream doutClient = null;
    public static void main(String[] args){
        int portNumber = Integer.MAX_VALUE; //Garbage value so we don't start server on random port.
        if (args.length == 1){
            portNumber = Integer.parseInt(args[0]);
        } else {
            System.err.println("Incorrect Number of Arguments. 1 Argument Required. java Server <portNumber>");
            System.exit(1);
        }
        try {
            serverSocket = new ServerSocket(portNumber);
            //wait on client
            clientSocket = serverSocket.accept();
            System.out.println("Connected to Client!");
            int theAnswer = 42;
            doutClient = new DataOutputStream(clientSocket.getOutputStream());
            doutClient.writeInt(theAnswer);
            doutClient.flush();
            System.out.println("Information Sent to Client.");
            System.out.println("Terminating.");
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + portNumber);
            System.exit(1);
        }
    }
}