package client;
import java.net.*;
import java.util.Scanner;


public class Main {
    private static DatagramSocket clientSocket;
    private static InetAddress serverAddress;
    private static int serverPort;

    private static void chooseServer(){
        Scanner scanner = new Scanner(System.in);
        boolean isValid;
        do {
            try {
                System.out.println("Pick the server address : ");
                Main.serverAddress = InetAddress.getByName(scanner.next());
                System.out.println("Pick the server port : ");
                Main.serverPort = scanner.nextInt();
                isValid = true;
            } catch (Exception e) {
                isValid = false;
                System.out.println(e.getMessage());
                System.out.println("We are not able to contact this server, please try again.");
            }
        } while (!isValid);

    }

    private static void ask(){}
    private static void answer(){}

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("I am a client !");
        // Set up :
        try {
                Main.clientSocket = new DatagramSocket(null);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                chooseServer();
        byte[] receptionBuffer = new byte[1024];
        byte[] emissionBuffer = null;
        System.out.println("Pick your username for this session (exit to quit):");
        String emissionMessage = scanner.nextLine();
        // Communication :
        while (!emissionMessage.equals("exit")) {
            try {
                // Emission :
                emissionBuffer = emissionMessage.getBytes();
                DatagramPacket emissionPacket = new DatagramPacket(emissionBuffer, emissionBuffer.length, Main.serverAddress, Main.serverPort);
                Main.clientSocket.send(emissionPacket);
                // Reception :
                DatagramPacket receptionPacket = new DatagramPacket(receptionBuffer, receptionBuffer.length);
                Main.clientSocket.receive(receptionPacket);
                String receptionMessage = new String(receptionPacket.getData(), 0, receptionPacket.getLength());
                System.out.println("From the server: " + receptionMessage);
                // Reaction :
                emissionMessage = scanner.nextLine();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Exit.");
                Main.clientSocket.close();
                return;
            }
        }
        // End of communication :
        System.out.println("Bye-bye!");
        Main.clientSocket.close();
    }
}
