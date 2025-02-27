package server;

import java.net.*;

public class Main {
    public static void main(String[] args) {
        DatagramSocket serverSocket = null;
        try {
            InetSocketAddress serverAddress = new InetSocketAddress("localhost", 6666);
            serverSocket = new DatagramSocket(serverAddress);
            byte[] receptionBuffer = new byte[1024];

            while (true) {
                DatagramPacket firstPacket = new DatagramPacket(receptionBuffer, receptionBuffer.length);
                serverSocket.receive(firstPacket);

                InetAddress clientAdr = firstPacket.getAddress();
                int clientPort = firstPacket.getPort();
                DatagramSocket threadSocket = new DatagramSocket(serverAddress);

                CommunicationThread newThread = new CommunicationThread(threadSocket, clientAdr, clientPort);
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
