package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Thread which manage communication with one client.
 */
public class CommunicationThread extends Thread {
    private static int threadIdCounter = 0;
    private final int threadId;
    private final InetAddress clientAdr;
    private final int clientPort;
    private final int clientId;
    private final DatagramSocket threadSocket;
    private byte[] emissionBuffer;
    /**
     * Timer to close this thread when time limit between client message is passed
     */
    private final Timer timer;

    public CommunicationThread(DatagramSocket threadSocket, InetAddress clientAdr, int clientPort, int clientId, int timeLimit) {
        this.clientAdr = clientAdr;
        this.clientPort = clientPort;
        this.clientId = clientId;
        this.threadSocket = threadSocket;
        this.timer = new Timer(this, timeLimit);
        this.threadId = threadIdCounter++;
    }

    public int getThreadId() {
        return threadId;
    }

    /**
     * If the thread is interrupted, client is disconnected
     * So we close this threadSocket and we remove client from DNS
     */
    @Override
    public void interrupt() {
        super.interrupt();
        System.out.println("Connection thread n°"+this.threadId+" stopped");
        sendPacket("Connection lost");
        this.threadSocket.close();
        InternalCommunication.remove(this.clientId);
    }

    /**
     * Methode run when thread is start, with all software logical server side
     */
    public void run() {
        timer.start();
        System.out.println("Connection thread n°"+this.threadId+" running");

        //While client is connected, "do something"
        while (!this.isInterrupted()) {
            try {
                String question = "Ask your question:";
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
                System.err.println("Error thread n°"+this.threadId);
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Create a DatagramPacket to send message given in parameter.
     * @param message String, message to send to client
     */
    private void sendPacket(String message) {
        try {
            System.out.println("Thread n°"+this.threadId+" : Message to send : "+ message);
            this.emissionBuffer = message.getBytes();
            DatagramPacket packetToSend = new DatagramPacket(emissionBuffer, emissionBuffer.length, this.clientAdr, this.clientPort);
            threadSocket.send(packetToSend);
            this.emissionBuffer = null;
        } catch (IOException e) {
            System.out.println("Thread n°"+this.threadId+" : send packet failed");
            throw new RuntimeException("Thread closed");
        }
    }

    /**
     * Wait for client answer and return it as DatagramPacket.
     * Reset timer before and after waiting
     * @return DatagramPacket, client message
     */
    private DatagramPacket waitingResponse() {
        try {
            byte[] receptionBuffer = new byte[1024];
            DatagramPacket incomingPacket = new DatagramPacket(receptionBuffer, receptionBuffer.length);
            timer.reset();
            System.out.println("Thread n°"+this.threadId+" : waiting...");
            this.threadSocket.receive(incomingPacket);
            System.out.println("Message received : "+ (new String(incomingPacket.getData())).trim());
            timer.reset();

            return incomingPacket;
        } catch (Exception e) {
            System.out.println("Thread n°"+this.threadId+" : waiting response failed");
            throw new RuntimeException("Thread closed");
        }
    }
}
