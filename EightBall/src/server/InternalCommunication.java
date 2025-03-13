package server;

import java.util.*;

/**
 * Manage communication between CommunicationThread, keep client directory and manage automatic question/answer
 */
public class InternalCommunication {
    private static final Map<Integer, ClientInformation> DNS = new HashMap<>();
    private static final Queue<String> questionQueue = new LinkedList<>();
    private static final Queue<String> answerQueue   = new LinkedList<>();

    public static boolean containsKey(int incomingId) {
        return DNS.containsKey(incomingId);
    }

    public static void put(Integer id, ClientInformation incomingInformation) {
        DNS.put(id, incomingInformation);
    }

    public static ClientInformation get(int id) {
        return DNS.get(id);
    }

    public static void remove(int clientId) {
        InternalCommunication.DNS.remove(clientId);
    }

    public static void putQuestionInWaitingList(String data) {
        questionQueue.add(data);
    }

    public static void putAnswerInWaitingList(String data) {
        answerQueue.add(data);
    }

    public static String getQuestion() {
        if (questionQueue.isEmpty()) {
            return Questions.getRandom().toString();
        } else {
            return questionQueue.poll();
        }
    }

    public static String getAnswer() {
        if (answerQueue.isEmpty()) {
            return Answers.getRandom().toString();
        } else {
            return answerQueue.poll();
        }
    }
}
