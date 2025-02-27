package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class CommunicationThread extends Thread {
    private final Socket clientSocket;
    private final DatagramSocket threadSocket;
    private final Timer timer;
    private byte[] receptionBuffer = new byte[1024];
    private byte[] emissionBuffer;

    public CommunicationThread(DatagramSocket threadSocket, InetAddress clientAdr, int clientPort) throws IOException {
        this.clientSocket = new Socket(clientAdr, clientPort);
        this.threadSocket = threadSocket;
        this.timer = new Timer(this, 600);
    }

    public void run() {
        timer.start();
        while (timer.getTime() < 600) {
            try {
                String question = "Ask your question ?";
                this.emissionBuffer = question.getBytes();
                DatagramPacket packetToSend = new DatagramPacket(emissionBuffer, emissionBuffer.length, clientSocket.getInetAddress(), clientSocket.getPort());
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
                throw new RuntimeException(e);
            }
        }
    }

    private void sendPacket(String message) {
        try {
            this.emissionBuffer = message.getBytes();
            DatagramPacket packetToSend = new DatagramPacket(emissionBuffer, emissionBuffer.length, clientSocket.getInetAddress(), clientSocket.getPort());
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
            return incomingPacket;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
