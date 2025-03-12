package server;

import java.net.*;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        DatagramSocket serverSocket = null;
        try {
            InetSocketAddress serverAddress = new InetSocketAddress("localhost", 6666);
            serverSocket = new DatagramSocket(serverAddress);
            System.out.println("Server adress: " + serverAddress.getAddress().getHostAddress());
            System.out.println("Server port: " + serverAddress.getPort());
            byte[] receptionBuffer = new byte[1024];

            while (true) {
                DatagramPacket firstPacket = new DatagramPacket(receptionBuffer, receptionBuffer.length);
                serverSocket.receive(firstPacket);

                String incomingUsername = new String(firstPacket.getData());
                incomingUsername = incomingUsername.trim();
                System.out.println("Incoming message: " + incomingUsername);

                if (InternalCommunication.containsKey(incomingUsername)) {
                    System.out.println("TODO Username already used : add like UserName_2 to DNS & send the new name to the client");
                    continue;
                }

                InetAddress clientAdr = firstPacket.getAddress();
                int clientPort = firstPacket.getPort();
                DatagramSocket threadSocket = new DatagramSocket(serverAddress);
                CommunicationThread newThread = new CommunicationThread(threadSocket, clientAdr, clientPort);

                InternalCommunication.put(incomingUsername, newThread);

                newThread.start();
            }
        } catch (Exception e) {
            if (serverSocket != null) {
                serverSocket.close();
            }
            System.err.println(e);
        }
    }
}
