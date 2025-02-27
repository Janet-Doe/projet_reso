import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the first port to test: ");
        int indiceDebut = scanner.nextInt();
        System.out.println("Enter the last port to test: ");
        int indiceFin = scanner.nextInt();
        testPlagePort(indiceDebut, indiceFin);
    }

    public static void testPlagePort(int indiceDebut, int indiceFin) {
        for (int portNumber = indiceDebut; portNumber <= indiceFin; portNumber++) {
            StringBuilder sb = new StringBuilder("Test of port "+ portNumber +" : ");
            try {
                DatagramSocket socketServeur = new DatagramSocket(new InetSocketAddress("localhost", portNumber));
                sb.append("available");
                socketServeur.close();
            } catch (SocketException se) {
                sb.append("unavailable");
            }
            System.out.println(sb);
        }
    }
}