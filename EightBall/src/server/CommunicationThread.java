package server;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class CommunicationThread extends Thread {
    private final Socket clientSocket;
    private final DatagramSocket threadSocket;
    private final Timer timer;

    public CommunicationThread(DatagramSocket threadSocket, InetAddress clientAdr, int clientPort) throws IOException {
        this.clientSocket = new Socket(clientAdr, clientPort);
        this.threadSocket = threadSocket;
        this.timer = new Timer(this, 600);
    }

    public void run() {
        timer.start();
        while (timer.getTime() < 600) {

        }
    }
}
