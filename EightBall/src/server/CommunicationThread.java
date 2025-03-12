package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class CommunicationThread extends Thread {
    private final InetAddress clientAdr;
    private final int clientPort;
    private final DatagramSocket threadSocket;
    private final Timer timer;
    private byte[] receptionBuffer = new byte[1024];
    private byte[] emissionBuffer;

    public CommunicationThread(DatagramSocket threadSocket, InetAddress clientAdr, int clientPort) throws IOException {
        this.clientAdr = clientAdr;
        this.clientPort = clientPort;
        this.threadSocket = threadSocket;
        this.timer = new Timer(this, 600);
    }

    public void run() {
        timer.start();
        System.out.println("Connection thread running");
        while (true) {
            try {
                String question = "Ask your question ?";
                this.emissionBuffer = question.getBytes();
                DatagramPacket packetToSend = new DatagramPacket(emissionBuffer, emissionBuffer.length, this.clientAdr, this.clientPort);
                threadSocket.send(packetToSend);
                DatagramPacket response = waitingResponse();

                question = InternalCommunication.getQuestion();
                InternalCommunication.putQuestionInWaitingList(response.getData());

                sendPacket(question);

                response = waitingResponse();
                String answer = InternalCommunication.getAnswer();
                InternalCommunication.putAnswerInWaitingList(response.getData());

                sendPacket(answer);

            } catch (IOException e) {
                System.out.println("Connection thread stopped");
                throw new RuntimeException(e);
            }
        }
    }

    private void sendPacket(String message) {
        try {
            System.out.println("Message to send : "+ message);
            this.emissionBuffer = message.getBytes();
            DatagramPacket packetToSend = new DatagramPacket(emissionBuffer, emissionBuffer.length, this.clientAdr, this.clientPort);
            threadSocket.send(packetToSend);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private DatagramPacket waitingResponse() {
        try {
            DatagramPacket incomingPacket = new DatagramPacket(this.receptionBuffer, receptionBuffer.length);
            timer.reset();
            this.threadSocket.receive(incomingPacket);
            timer.reset();
            System.out.println("Message received : "+ incomingPacket.getData());
            return incomingPacket;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
