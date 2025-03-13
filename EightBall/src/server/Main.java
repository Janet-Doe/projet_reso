package server;

import java.net.*;

public class Main {

    public static void main(String[] args) {
        DatagramSocket serverSocket = null;
        try {
            // Creating serverSocket waiting for client interaction
            InetSocketAddress serverAddress = new InetSocketAddress("localhost", 6666);
            serverSocket = new DatagramSocket(serverAddress);
            System.out.println("Server address: " + serverAddress.getAddress().getHostAddress());
            System.out.println("Server port: " + serverAddress.getPort());
            byte[] receptionBuffer = new byte[1024];

            while (true) {
                DatagramPacket firstPacket = new DatagramPacket(receptionBuffer, receptionBuffer.length);
                serverSocket.receive(firstPacket);

                InetAddress clientAdr = firstPacket.getAddress();
                int clientPort = firstPacket.getPort();

                // Client asks to connect
                String incomingUsername = new String(firstPacket.getData());
                receptionBuffer = new byte[1024];
                incomingUsername = incomingUsername.split(":")[1].trim();
                System.out.println("Incoming username: " + incomingUsername);

                int id = incomingUsername.hashCode() + clientAdr.hashCode();

                // Check username and address
                if (InternalCommunication.containsKey(id)) {
                    String message = "Error : username and address are already used";
                    System.out.println(message);
                    byte[] emissionBuffer = message.getBytes();
                    DatagramPacket packetToSend = new DatagramPacket(emissionBuffer, emissionBuffer.length, clientAdr, clientPort);
                    serverSocket.send(packetToSend);
                    continue;
                }

                // Create a CommunicationThread for this client
                DatagramSocket threadSocket = new DatagramSocket(null);
                System.out.flush();
                CommunicationThread newThread = new CommunicationThread(threadSocket, clientAdr, clientPort, id, 300);

                ClientInformation newClient = new ClientInformation(incomingUsername, newThread);

                InternalCommunication.put(id, newClient);

                newThread.start();
            }
        } catch(Exception e){
            if (serverSocket != null) {
                serverSocket.close();
            }
            System.err.println(e.getMessage());
        }
    }
}
