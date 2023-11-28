package HW3;

import java.net.*;
import java.io.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * Exit Codes:
 * ~1~ Incorrect number of args provided to main()
 * ~2~ Exception thrown in serverSetup()
 * ~3~ Exception thrown in sendReqToClient()
 * ~4~ Exception thrown in relayMessages()
 */

public class TCPServer {
    private static Socket clientSocket;
    private static ServerSocket serverSocket;
    private static DataInputStream dinClient1 = null;
    private static DataInputStream dinClient2 = null;
    private static DataOutputStream doutClient1 = null;
    private static DataOutputStream doutClient2 = null;
    private static HashMap<String, DataOutputStream> outputStreams = new HashMap<String, DataOutputStream>();
    private static HashMap<String, DataInputStream> inputStreams = new HashMap<String, DataInputStream>();
    private static Random rng = new Random();
    private static String clientOneName;
    private static String clientTwoName;
    private static int inputValue;
    private static long clientOneSum;
    private static long clientTwoSum;
    private static List<Integer> clientOneNums = new ArrayList<>();
    private static List<Integer> clientTwoNums = new ArrayList<>();
    private static int receivedOne = 0;
    private static int receivedTwo = 0;

// TASK 1
    public static void serverSetup(int portNumber, int seed) {
        try {
            System.out.println("IP Address: " + InetAddress.getLocalHost());
            System.out.println("Port Number " + portNumber);

            serverSocket = new ServerSocket(portNumber);

            System.out.println("waiting for client...");
            
            //System.out.println("Waiting on Client 1");
            clientSocket = serverSocket.accept();
            //System.out.println("Connected to Client 1!");
            doutClient1 = new DataOutputStream(clientSocket.getOutputStream());
            outputStreams.put("C1o", doutClient1);
            dinClient1 = new DataInputStream(clientSocket.getInputStream());
            inputStreams.put("C1i", dinClient1);
            clientOneName = clientSocket.getInetAddress().getHostName();

            //System.out.println("Waiting on Client 2");
            clientSocket = serverSocket.accept();
            //System.out.println("Connected to Client 2!");
            doutClient2 = new DataOutputStream(clientSocket.getOutputStream());
            outputStreams.put("C2o", doutClient2);
            dinClient2 = new DataInputStream(clientSocket.getInputStream());
            inputStreams.put("C2i", dinClient2);
            clientTwoName = clientSocket.getInetAddress().getHostName();


            System.out.println("Clients Connected!");

            rng.setSeed(seed);
            //System.out.println("Random seed set to " + seed + "!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(2);
        }
    }

// TASK 2
    public static void sendConfig(int numMessages, int portNumber) {
        try {
            System.out.println("Sending config to clients...");

            outputStreams.get("C1o").writeInt(numMessages);
            outputStreams.get("C1o").flush();
            int clientOneSeed = rng.nextInt();
            outputStreams.get("C1o").writeInt(clientOneSeed);
            outputStreams.get("C1o").flush();
            //System.out.println(numMessages + " & random int sent to Client 1!");

            System.out.println(clientOneName + " " + clientOneSeed);
        } catch (IOException e) {
            System.err.println("Client 1 could not listen on port: " + portNumber);
            System.exit(3);
        }
        try {
            outputStreams.get("C2o").writeInt(numMessages);
            outputStreams.get("C2o").flush();
            int clientTwoSeed = rng.nextInt();
            outputStreams.get("C2o").writeInt(clientTwoSeed);
            outputStreams.get("C2o").flush();
            //System.out.println(numMessages + " & random int sent to Client 2!");

            System.out.println(clientTwoName + " " + clientTwoSeed);
        } catch (IOException e) {
            System.err.println("Client 2 could not listen on port: " + portNumber);
            System.exit(3);
        }
        
        System.out.println("Finished sending config to clients.");

    }

// TASK 3
     public static void recieveNumbers(int numMessages) {
        System.out.println("Starting to listen for client messages...");
        try {
            for (int i = 0; i < numMessages; i++) {
                inputValue = inputStreams.get("C1i").readInt();
                clientOneNums.add(inputValue);
                receivedOne++;
            }
            clientOneSum = clientOneNums.stream().mapToInt(Integer::intValue).sum();
        } catch (IOException e) {
            System.err.println("Fatal Connection Error!");
            e.printStackTrace();
        }
        try {
            for (int i = 0; i < numMessages; i++) {
                inputValue = inputStreams.get("C2i").readInt();
                clientTwoNums.add(inputValue);
                receivedTwo++;
            }
            clientTwoSum = clientTwoNums.stream().mapToInt(Integer::intValue).sum();
        } catch (IOException e) {
            System.err.println("Fatal Connection Error!");
            e.printStackTrace();
        }
        System.out.println("Finished listening for client messages.");

        System.out.println(clientOneName);
        System.out.println("        Messages received: " + receivedOne);
        System.out.println("        Sum received: " + clientOneSum);

        System.out.println(clientTwoName);
        System.out.println("        Messages received: " + receivedTwo);
        System.out.println("        Sum received: " + clientTwoSum);
     }

// TASK 4 & 5
     public static void relayMessages(int portNumber) {
        try {
            for (Integer num : clientTwoNums) {
                outputStreams.get("C1o").writeInt(num);
                outputStreams.get("C1o").flush();
            }
        } catch (IOException e) {
            System.err.println("Client 1 could not listen on port: " + portNumber);
            System.exit(4);
        }
        try {
            for (Integer num : clientOneNums) {
                outputStreams.get("C2o").writeInt(num);
                outputStreams.get("C2o").flush();
            }
        } catch (IOException e) {
            System.err.println("Client 2 could not listen on port: " + portNumber);
            System.exit(4);
        }
     }

    public static void main(String[] args){
        String[] args2 = {"8008", "69", "3"};

        int portNumber = Integer.MAX_VALUE; // Garbage value so we don't start server on random port.
        int seed = 0;
        int numMessages = 0;
        if (args2.length == 3){
            portNumber = Integer.parseInt(args2[0]);
            seed = Integer.parseInt(args2[1]);
            numMessages = Integer.parseInt(args2[2]);
        } else {
            System.err.println("Incorrect Number of Arguments. 3 Arguments Required.");
            System.exit(1);
        }

        // TASK 1
        serverSetup(portNumber, seed);

        // TASK 2
        sendConfig(numMessages, portNumber);

        // TASK 3
        recieveNumbers(numMessages);

        // TASK 4 & 5
        relayMessages(portNumber);
    }
}