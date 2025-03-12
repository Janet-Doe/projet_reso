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
    private static final String ANSI_CYAN  = "\u001B[36m";
    public  static final String ANSI_RESET = "\u001B[0m";

    /**
     * Ask for an address and a port and test them, as long as the given server isn't accessible.
     */
    private static void chooseServer() {
        boolean isValid = false;
        while (!isValid) {
            try {
                System.out.println("Pick the server address : ");
                Main.serverAddress = InetAddress.getByName(scanner.next());
                System.out.println("Pick the server port : ");
                Main.serverPort = scanner.nextInt();
                if (Main.serverAddress.isReachable(3000)) {
                    isValid = true;
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("We are not able to contact this server, please try again.");
            }
        }
    }

    /**
     * Send a message from a given String
     * @param emissionMessage String, message to send
     * @throws IOException, exception from the DatagramSocket.send() method
     */
    private static void emission(String emissionMessage) throws IOException {
        Main.emissionBuffer = null;
        Main.emissionBuffer = emissionMessage.getBytes();
        DatagramPacket emissionPacket = new DatagramPacket(Main.emissionBuffer, Main.emissionBuffer.length, Main.serverAddress, Main.serverPort);
        Main.clientSocket.send(emissionPacket);
    }

    /**
     * Reception message from server.
     * @return String message parsed in String.
     * @throws Exception, exception from the DatagramSocket.receive() method
     */
    private static String reception() throws Exception {
        Main.receptionBuffer = new byte[1024];
        DatagramPacket receptionPacket = new DatagramPacket(Main.receptionBuffer, Main.receptionBuffer.length);
        Main.clientSocket.receive(receptionPacket);
        String message = (new String(receptionPacket.getData())).trim();
        if (message.equals("Connection lost")) {
            throw new Exception("Connection lost");
        }
        serverAddress = receptionPacket.getAddress();
        serverPort    = receptionPacket.getPort();
        return message;
    }

    /**
     * Ask and send a valid answer to the server.
     * @throws IOException, exception from the DatagramSocket.send() method
     */
    private static void answer() throws IOException {
        System.out.println("Please pick the number of your answer:");
        for (int i = 0; i < Answers.getSize(); i++){
            System.out.println("[" + (i+1) + "] " + Answers.getAnswer(i));
        }
        int answer = 1;
        boolean answerIsCorrect = false;
        while (!answerIsCorrect) {
            String answerString = scanner.next();
            if (!isUnsignedInteger(answerString)) {
                System.out.println("This is not a valid answer. Please try again:");
                continue;
            }
            answer = Integer.parseInt(answerString) - 1;
            if (answer < 0 || answer >= Answers.getSize()) {
                System.out.println("This is not a valid answer. Please try again:");
                continue;
            }
            answerIsCorrect = true;
        }
        emission(Answers.getAnswer(answer).toString());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the 8-Ball Project! All your questions will find their answers.");
        // Set up :
        try {
            Main.clientSocket = new DatagramSocket(null);
            Main.clientSocket.setSoTimeout(300000); // set a timed-out at 5min of inactivity
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        chooseServer();
        Main.receptionBuffer = new byte[1024];
        Main.emissionBuffer = null;
        boolean validUsername = false;

        // Username choice :
        while (!validUsername) {
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
        } ;

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

    /**
     * Check if String is an unsigned Integer.
     * @param s String
     * @return boolean, true if it is or false
     */
    public static boolean isUnsignedInteger(String s) {
        int radix = 10;
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                return false;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }
}
