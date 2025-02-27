package server;

import org.w3c.dom.css.Counter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Time;

public class CommunicationThread extends Thread {
    private final Socket clientSocket;
    private final DatagramSocket threadSocket;

    public CommunicationThread(DatagramSocket threadSocket, InetAddress clientAdr, int clientPort) throws IOException {
        this.clientSocket = new Socket(clientAdr, clientPort);
        this.threadSocket = threadSocket;
    }

    public void run() {
    }
}
