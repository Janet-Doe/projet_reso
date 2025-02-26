import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrer l'indice du début de la plage de port à tester");
        int indiceDebut = scanner.nextInt();
        System.out.println("Entrer l'indice de fin de la plage");
        int indiceFin = scanner.nextInt();

        testPlagePort(indiceDebut, indiceFin);
    }

    public static void testPlagePort(int indiceDebut, int indiceFin) {
        for (int portNumber = indiceDebut; portNumber <= indiceFin; portNumber++) {
            StringBuilder sb = new StringBuilder("Test du port "+ portNumber +" : ");
            try {
                DatagramSocket socketServeur = new DatagramSocket(new InetSocketAddress("localhost", portNumber));
                sb.append("disponible");
                socketServeur.close();
            } catch (SocketException se) {
                sb.append("indisponible");
            }
            System.out.println(sb);
        }
    }
}