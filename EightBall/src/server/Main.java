package server;

import java.net.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final Map<String, CommunicationThread> DNS = new HashMap<>();
    public static void main(String[] args) {
        DatagramSocket serverSocket = null;
        try {
            InetSocketAddress serverAddress = new InetSocketAddress("localhost", 6666);
            serverSocket = new DatagramSocket(serverAddress);
            byte[] receptionBuffer = new byte[1024];

            while (true) {
                DatagramPacket firstPacket = new DatagramPacket(receptionBuffer, receptionBuffer.length);
                serverSocket.receive(firstPacket);

                String incomingUsername = Arrays.toString(firstPacket.getData());

                if (DNS.containsKey(incomingUsername)) {
                    System.out.println("TODO Username already used");
                    continue;
                }

                InetAddress clientAdr = firstPacket.getAddress();
                int clientPort = firstPacket.getPort();
                DatagramSocket threadSocket = new DatagramSocket(serverAddress);
                CommunicationThread newThread = new CommunicationThread(threadSocket, clientAdr, clientPort);

                DNS.put(incomingUsername, newThread);

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
