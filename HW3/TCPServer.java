package HW3;

import java.net.*;
import java.io.*;
import java.util.Random;
import java.util.HashMap;

/*
 * Exit Codes:
 * ~1~ Incorrect number of args provided to main()
 * ~2~ Exception thrown in serverSetup()
 * ~3~ Exception thrown in sendReqToClient()
 */

public class TCPServer {
    private static Socket clientSocket;
    private static ServerSocket serverSocket;
    private static DataOutputStream doutClient1 = null;
    private static DataOutputStream doutClient2 = null;
    private static HashMap<String, DataOutputStream> outputStreams = new HashMap<String, DataOutputStream>();
    private static Random rng = new Random();
    private static String clientOneName;
    private static String clientTwoName;

// TASK 1
    public static void serverSetup(int portNumber, int seed, Random rng) {
        try {
            System.out.println("IP Address: " + InetAddress.getLocalHost());
            System.out.println("Port Number " + portNumber);

            serverSocket = new ServerSocket(portNumber);

            System.out.println("waiting for client...");
            
            //System.out.println("Waiting on Client 1");
            clientSocket = serverSocket.accept();
            //System.out.println("Connected to Client 1!");
            doutClient1 = new DataOutputStream(clientSocket.getOutputStream());
            outputStreams.put("C1", doutClient1);
            clientOneName = clientSocket.getInetAddress().getHostName();
            //System.out.println("Client 1 added to outputStreams HashMap!");

            //System.out.println("Waiting on Client 2");
            clientSocket = serverSocket.accept();
            //System.out.println("Connected to Client 2!");
            doutClient2 = new DataOutputStream(clientSocket.getOutputStream());
            outputStreams.put("C2", doutClient2);
            clientTwoName = clientSocket.getInetAddress().getHostName();
            //System.out.println("Client 2 added to outputStreams HashMap!");

            System.out.println("Clients Connected!");

            rng.setSeed(seed);
            //System.out.println("Random seed set to " + seed + "!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(2);
        }
    }
// TASK 2
    public static void sendConfig(int numMessages, Socket clientSocket, int portNumber, Random rng) {
        try {
            System.out.println("Sending config to clients...");

            outputStreams.get("C1").writeInt(numMessages);
            outputStreams.get("C1").flush();
            int clientOneSeed = rng.nextInt();
            outputStreams.get("C1").writeInt(clientOneSeed);
            outputStreams.get("C1").flush();
            //System.out.println(numMessages + " & random int sent to Client 1!");

            System.out.println(clientOneName + " " + clientOneSeed);
        } catch (IOException e) {
            System.err.println("Client 1 could not listen on port: " + portNumber);
            System.exit(3);
        }
        try {
            outputStreams.get("C2").writeInt(numMessages);
            outputStreams.get("C2").flush();
            int clientTwoSeed = rng.nextInt();
            outputStreams.get("C2").writeInt(clientTwoSeed);
            outputStreams.get("C2").flush();
            //System.out.println(numMessages + " & random int sent to Client 2!");

            System.out.println(clientTwoName + " " + clientTwoSeed);
        } catch (IOException e) {
            System.err.println("Client 2 could not listen on port: " + portNumber);
            System.exit(3);
        }
        
        System.out.println("Finished sending config to clients.");

    }

    public static void main(String[] args){
        //String[] args2 = {"8008", "69", "3"};

        int portNumber = Integer.MAX_VALUE; // Garbage value so we don't start server on random port.
        int seed = 0;
        int numMessages = 0;
        if (args.length == 3){
            portNumber = Integer.parseInt(args[0]);
            seed = Integer.parseInt(args[1]);
            numMessages = Integer.parseInt(args[2]);
        } else {
            System.err.println("Incorrect Number of Arguments. 3 Arguments Required.");
            System.exit(1);
        }

        // TASK 1
        serverSetup(portNumber, seed, rng);

        // TASK 2
        sendConfig(numMessages, clientSocket, portNumber, rng);

    }
}