package client;
import java.net.*;
import java.util.Scanner;


public class Main {
    private static DatagramSocket clientSocket;
    private static DatagramSocket serverSocket;
    private static byte[] emissionBuffer;

    private static void chooseServer(){
        Scanner scanner = new Scanner(System.in);
        boolean isValid;
        do {
            try {
                System.out.println("Pick the server address : ");
                InetAddress serverAddress = InetAddress.getByName(scanner.next());
                System.out.println("Pick the server port : ");
                int serverPort = scanner.nextInt();
                serverSocket = new DatagramSocket(new InetSocketAddress(serverAddress, serverPort));
                isValid = true;
            } catch (Exception e) {
                isValid = false;
                System.out.println("We are not able to contact this server, please try again.");
            }
        } while (!isValid);

    }

    public static void main(String[] args) {
        System.out.println("I am a client !");
        try {
            clientSocket = new DatagramSocket(null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        chooseServer();
        Scanner scanner = new Scanner(System.in);
        // Channel creation :
        byte[] receptionBuffer = new byte[1024];
        // Emission :
        System.out.println("Pick your username for this session (exit to quit):");
        String emissionMessage = scanner.nextLine();

        while (!emissionMessage.equals("exit")) {
            try {
                emissionBuffer = emissionMessage.getBytes();
                DatagramPacket emissionPacket = new DatagramPacket(emissionBuffer, emissionBuffer.length, serverSocket.getInetAddress(), serverSocket.getPort());
                clientSocket.send(emissionPacket);
                // Reception :
                DatagramPacket receptionPacket = new DatagramPacket(receptionBuffer, receptionBuffer.length);
                clientSocket.receive(receptionPacket);
                String receptionMessage = new String(receptionPacket.getData(), 0, receptionPacket.getLength());
                System.out.println("From the server: " + receptionMessage);
                emissionMessage = scanner.nextLine();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Bye-bye!");
        clientSocket.close();
    }
}
