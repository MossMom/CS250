package HW3;

import java.net.*;
import java.io.*;

/*
 * Exit Codes:
 * ~1~ Incorrect number of args provided to main()
 */

public class TCPClient {
    private static Socket socket;
    private static DataInputStream din = null;
    public static void main(String[] args){
        String serverHost = null;
        int serverPort = Integer.MAX_VALUE;
        if (args.length == 2){
            serverHost = args[0];
            serverPort = Integer.parseInt(args[1]);
        } else {
            System.err.println("Incorrect Number of Arguments. 2 Arguments Required. java Client <serverHost> <serverPort>");
            System.exit(1);
        }
        try {
            socket = new Socket(serverHost, serverPort);
            System.out.println("Connected to Server!");
            din = new DataInputStream(socket.getInputStream());
            int message = din.readInt();
            System.out.println("Message from server: " + message);
            System.out.println("Terminating.");
        } catch (IOException e) {
            System.err.println("Fatal Connection Error!");
            e.printStackTrace();
        }
    }
}