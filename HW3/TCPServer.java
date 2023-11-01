package HW3;

import java.net.*;
import java.io.*;
import java.util.Random;

/*
 * Exit Codes:
 * ~1~ Incorrect number of args provided to main()
 * ~2~ Exception thrown in serverSetup
 */

public class TCPServer {
    private static Socket clientSocket;
    private static ServerSocket serverSocket;
    private static DataOutputStream doutClient1 = null;
    private static DataOutputStream doutClient2 = null;

// TASK 1
    public static void serverSetup(int portNumber, int seed, Random rng) {
        try {
            serverSocket = new ServerSocket(portNumber);
            //wait on client
            clientSocket = serverSocket.accept();
            System.out.println("Connected to Client(s)!"); //TODO Remove
            rng.setSeed(seed);
            System.out.println("Random seed set to <seed>!"); //TODO Remove
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(2);
        }
    }
// TASK 2
    public static void sendReqToClient(int numMessages, Socket clientSocket) {
        try {
            doutClient1 = new DataOutputStream(clientSocket.getOutputStream());
            doutClient1.writeInt(numMessages);
            doutClient1.flush();
            System.out.println("Information Sent to Client 1!");
        } catch (IOException e) {
            // TODO: handle exception
        }
        try {
            doutClient2 = new DataOutputStream(clientSocket.getOutputStream());
            doutClient2.writeInt(numMessages);
            doutClient2.flush();
            System.out.println("Information Sent to Client 2!");
        } catch (IOException e) {
            // TODO: handle exception
        }
    }

    public static void main(String[] args){
        int portNumber = Integer.MAX_VALUE; // Garbage value so we don't start server on random port.
        int seed = 0;
        int numMessages = 0;
        Random rng = new Random(); // Init random class
        if (args.length == 3){
            portNumber = Integer.parseInt(args[0]);
            seed = Integer.parseInt(args[1]);
            numMessages = Integer.parseInt(args[2]);
        } else {
            System.err.println("Incorrect Number of Arguments. 3 Arguments Required. java Server <portNumber>");
            System.exit(1);
        }

        // TASK 1
        serverSetup(portNumber, seed, rng);

        // TASK 2
        //sendReqToClient(clientSocket1, clientSocket2);

        try {
            serverSocket = new ServerSocket(portNumber);
            //wait on client
            clientSocket = serverSocket.accept();
            System.out.println("Connected to Client!");
            int theAnswer = 42;
            doutClient1 = new DataOutputStream(clientSocket.getOutputStream());
            doutClient1.writeInt(theAnswer);
            doutClient1.flush();
            System.out.println("Information Sent to Client.");
            System.out.println("Terminating.");
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + portNumber);
            System.exit(1);
        }
    }
}