package client;
import server.Answers;

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
    private static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";

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
                System.out.println(e.getMessage());
                System.out.println("We are not able to contact this server, please try again.");
            }
        } while (!isValid);
    }

    private static void emission(String emissionMessage) throws IOException {
        Main.emissionBuffer = null;
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
        serverPort = receptionPacket.getPort();
        return new String(receptionPacket.getData(), 0, receptionPacket.getLength());
    }

    private static void answer() throws IOException {
        System.out.println("Please pick the number of your answer:");
        for (int i = 0; i< Answers.getSize(); i++){
            System.out.println("[" + (i+1) + "] " + Answers.getAnswer(i));
        }
        int answer = scanner.nextInt()-1;
        while (answer < 0 || answer >= Answers.getSize()) {
            System.out.println("This is not a valid answer. Please try again:");
            answer = scanner.nextInt()-1;
        }
        emission(Answers.getAnswer(answer).toString());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the 8-Ball Project! All your questions will find their answers.");
        // Set up :
        try {
                Main.clientSocket = new DatagramSocket(null);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
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
                System.out.println("Answer from the server: " + reception);
                if (!reception.split(" ")[0].equals("Error")) {
                    validUsername = true;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Exiting program.");
                Main.clientSocket.close();
                return;
            }
        } while (!validUsername);

        // Communication loop :
        System.out.println("Ask a question, or exit with \"exit\":");
        String emissionMessage = scanner.nextLine();
        while (!emissionMessage.equals("exit")) {
            try {
                emission(emissionMessage); //sending question n°1
                System.out.println("Please answer this question: " + Main.ANSI_CYAN + reception() + Main.ANSI_RESET); //receiving question n°2
                //emission(scanner.nextLine());
                answer(); //answering q2
                System.out.println("Answer to your question: " + Main.ANSI_CYAN + reception() + Main.ANSI_RESET); //receiving answer q1
                reception(); //authorization to keep going
                System.out.println("You can ask another question, or exit with \"exit\":");
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
