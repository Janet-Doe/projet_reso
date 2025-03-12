package client;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;


public class Main {
    private static DatagramSocket clientSocket;
    private static InetAddress serverAddress;
    private static int serverPort;
    private static byte[] emissionBuffer;
    private static byte[] receptionBuffer;
    private static final Scanner scanner = new Scanner(System.in);

    private static void chooseServer(){
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
                System.out.println(e);
                System.out.println("We are not able to contact this server, please try again.");
            }
        } while (!isValid);
    }

    private static void ask(){}
    private static void answer(){}

    private static void emission(String emissionMessage) throws IOException {
        Main.emissionBuffer = null;
        System.out.println("message : " + emissionMessage);
        Main.emissionBuffer = emissionMessage.getBytes();
        DatagramPacket emissionPacket = new DatagramPacket(Main.emissionBuffer, Main.emissionBuffer.length, Main.serverAddress, Main.serverPort);
        Main.clientSocket.send(emissionPacket);
    }

    private static String reception() throws IOException {
        Main.receptionBuffer = new byte[1024];
        Scanner scanner = new Scanner(System.in);
        DatagramPacket receptionPacket = new DatagramPacket(Main.receptionBuffer, Main.receptionBuffer.length);
        Main.clientSocket.receive(receptionPacket);
        serverAddress = receptionPacket.getAddress();
        System.out.println("Message coming from " + serverAddress.getHostAddress() + ":" + serverPort);
        serverPort = receptionPacket.getPort();
        return new String(receptionPacket.getData(), 0, receptionPacket.getLength());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("I am a client !");
        // Set up :
        try {
                Main.clientSocket = new DatagramSocket(null);
                } catch (Exception e) {
                    System.out.println(e);
                }
                chooseServer();
        Main.receptionBuffer = new byte[1024];
        Main.emissionBuffer = null;
        boolean validUsername = false;

        // Username choice :
        do {
            try {
                System.out.println("Pick your username for this session (\"abandon\" to quit):");
                String username = scanner.nextLine();
                if (username.equals("abandon")) {
                    throw new Exception("User wants to exit the program.");
                }
                emission("Connexion:" + username);
                String reception = reception();
                System.out.println("From the server: " + reception);
                if (!reception.split(" ")[0].equals("Error")) {
                    validUsername = true;
                }
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("Exiting program.");
                Main.clientSocket.close();
                return;
            }
        } while (!validUsername);

        // Communication :
        System.out.println("Send a message:");
        String emissionMessage = scanner.nextLine();
        while (!emissionMessage.equals("exit")) {
            try {
                emission(emissionMessage);
                System.out.println("From the server: " + reception());
                emissionMessage = scanner.nextLine();
            } catch (Exception e) {
                System.out.println(e);
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
