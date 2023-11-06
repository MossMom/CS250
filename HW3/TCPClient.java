package HW3;

import java.net.*;
import java.io.*;
import java.util.Random;

/*
 * Exit Codes:
 * ~1~ Incorrect number of args provided to main()
 * ~2~ Error while connected to server
 */

public class TCPClient {
    private static Socket socket;
    private static DataInputStream din = null;
    private static String serverHost = null;
    private static int serverPort = Integer.MAX_VALUE;
    private static int numMessages;
    private static int seed;
    private static Random rng = new Random();

// TASK 1
    public static void serverSetup(String serverHost, int serverPort) {
        try {
            socket = new Socket(serverHost, serverPort);
            //System.out.println("Connected to Server!");
            din = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.err.println("Fatal Connection Error!");
            e.printStackTrace();
        }
    }

// TASK 2
    public static void recieveConfig(String serverHost, int serverPort) {
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
    
    public static void main(String[] args){
        //String[] args2 = {"bee.cs.colostate.edu", "8008"};

        if (args.length == 2){
            serverHost = args[0];
            serverPort = Integer.parseInt(args[1]);
        } else {
            System.err.println("Incorrect Number of Arguments. 2 Arguments Required.");
            System.exit(1);
        }

        serverSetup(serverHost, serverPort);

        recieveConfig(serverHost, serverPort);
    }
}