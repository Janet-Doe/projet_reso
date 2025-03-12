package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class CommunicationThread extends Thread {
    private final InetAddress clientAdr;
    private final int clientPort;
    private final int clientId;
    private final DatagramSocket threadSocket;
    private final Timer timer;
    private byte[] receptionBuffer = new byte[1024];
    private byte[] emissionBuffer;

    public CommunicationThread(DatagramSocket threadSocket, InetAddress clientAdr, int clientPort, int clientId) {
        this.clientAdr = clientAdr;
        this.clientPort = clientPort;
        this.clientId = clientId;
        this.threadSocket = threadSocket;
        this.timer = new Timer(this, 60);
    }

    public void run() {
        timer.start();
        System.out.println("Connection thread running");
        while (!this.isInterrupted()) {
            try {
                String question = "Ask your question ?";
                this.emissionBuffer = question.getBytes();
                DatagramPacket packetToSend = new DatagramPacket(emissionBuffer, emissionBuffer.length, this.clientAdr, this.clientPort);
                threadSocket.send(packetToSend);
                DatagramPacket response = waitingResponse();

                question = InternalCommunication.getQuestion();
                InternalCommunication.putQuestionInWaitingList((new String(response.getData())).trim());

                sendPacket(question);

                response = waitingResponse();
                String answer = InternalCommunication.getAnswer();
                InternalCommunication.putAnswerInWaitingList((new String(response.getData())).trim());

                sendPacket(answer);

            } catch (IOException e) {
                System.err.println("Error thread");
                throw new RuntimeException(e);
            }
        }

        System.out.println("Connection thread stopped");
        this.threadSocket.close();
        InternalCommunication.get(this.clientId).setState(ConnectionState.WAITING);
    }

    private void sendPacket(String message) {
        try {
            System.out.println("Message to send : "+ message);
            this.emissionBuffer = message.getBytes();
            DatagramPacket packetToSend = new DatagramPacket(emissionBuffer, emissionBuffer.length, this.clientAdr, this.clientPort);
            threadSocket.send(packetToSend);
            this.emissionBuffer = null;
        } catch (IOException e) {
            System.out.println("send packet failed");
            throw new RuntimeException(e);
        }
    }

    private DatagramPacket waitingResponse() {
        try {
            this.receptionBuffer = new byte[1024];
            DatagramPacket incomingPacket = new DatagramPacket(this.receptionBuffer, receptionBuffer.length);
            timer.reset();
            System.out.println("waiting...");
            this.threadSocket.receive(incomingPacket);
            System.out.println("Message received : "+ Arrays.toString(incomingPacket.getData()));
            timer.reset();

            return incomingPacket;
        } catch (Exception e) {
            System.out.println("waiting response failed");
            throw new RuntimeException(e);
        }
    }
}
