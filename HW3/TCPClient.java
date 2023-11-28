package HW3;

import java.net.*;
import java.io.*;
import java.util.Random;

/*
 * Exit Codes:
 * ~1~ Incorrect number of args provided to main()
 * ~2~ Error while sending numbers to server
 */

public class TCPClient {
    private static Socket socket;
    private static DataInputStream din = null;
    private static DataOutputStream dout = null;
    private static String serverHost = null;
    private static int serverPort = Integer.MAX_VALUE;
    private static int numMessages;
    private static int seed;
    private static Random rng = new Random();
    private static long senderSum = 0;
    private static int numOfSentMessages = 0;
    private static int numOfReceivedMessages = 0;
    private static long otherSum = 0;

// TASK 1
    public static void serverSetup(String serverHost, int serverPort) {
        try {
            socket = new Socket(serverHost, serverPort);
            //System.out.println("Connected to Server!");
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Fatal Connection Error!");
            e.printStackTrace();
        }
    }

// TASK 2
    public static void recieveConfig() {
        try {
            numMessages = din.readInt();
            //System.out.println("numMessages \"" + numMessages + "\" recieved from server!");

            seed = din.readInt();
            //System.out.println("seed \"" + seed + "\" recieved from server!");
            rng.setSeed(seed);

            System.out.println("Recieved config");
            System.out.println("number of messages = " + numMessages);
            System.out.println("seed = " + seed);
        } catch (IOException e) {
            System.err.println("Fatal Connection Error!");
            e.printStackTrace();
        }
    }

// TASK 3
    public static void sendNumbers() {
        System.out.println("Starting to send messages to server...");
        try {
            for (int i = 0; i < numMessages; i++) {
                int next = rng.nextInt();
                dout.writeInt(next);
                dout.flush();
                senderSum += next;
                numOfSentMessages++;
            }
        } catch (IOException e) {
            System.err.println("Server could not listen on port: " + serverPort);
            System.exit(2);
        }
        System.out.println("Finished sending messages to server.");
        System.out.println("Total messages sent: " + numOfSentMessages);
        System.out.println("Sum of messages sent: " + senderSum);
    }

// TASK 4 & 5
    public static void receiveRelay() {
        System.out.println("Starting to listen for messages from server...");
        try {
            for (int i = 0; i < numOfSentMessages; i++) {
                otherSum += din.readInt();
                numOfReceivedMessages++;
            }
        } catch (IOException e) {
            System.err.println("Fatal Connection Error!");
            e.printStackTrace();
        }
        System.out.println("Finished listening for messages from server.");
        System.out.println("Total messages received: " + numOfReceivedMessages);
        System.out.println("Sum of messages received: " + otherSum);
    }

    public static void main(String[] args){
        String[] args2 = {"bee.cs.colostate.edu", "8008"};

        if (args2.length == 2){
            serverHost = args2[0];
            serverPort = Integer.parseInt(args2[1]);
        } else {
            System.err.println("Incorrect Number of Arguments. 2 Arguments Required.");
            System.exit(1);
        }

        // TASK 1
        serverSetup(serverHost, serverPort);

        // TASK 2
        recieveConfig();

        // TASK 3
        sendNumbers();

        // TASK 4 & 5
        receiveRelay();
    }
}