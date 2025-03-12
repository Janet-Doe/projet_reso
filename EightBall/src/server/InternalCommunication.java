package server;

import java.util.*;

public class InternalCommunication {
    private static final Map<Integer, ClientInformation> DNS = new HashMap<>();
    private static final Queue<String> questionQueue = new LinkedList<>();
    private static final Queue<String> answerQueue   = new LinkedList<>();

    public static Map<Integer, ClientInformation> getDNS() {
        return DNS;
    }

    public static boolean containsKey(int incomingId) {
        return DNS.containsKey(incomingId);
    }

    public static void put(Integer id, ClientInformation incomingInformation) {
        DNS.put(id, incomingInformation);
    }

    public static void putQuestionInWaitingList(String data) {
        questionQueue.add(data);
    }

    public static void putAnswerInWaitingList(String data) {
        answerQueue.add(data);
    }

    public static String getQuestion() {
        if (questionQueue.size() <= 1) {
            return Questions.getRandom().toString();
        } else {
            return questionQueue.poll();
        }
    }

    public static String getAnswer() {
        if (answerQueue.size() <= 1) {
            return Answers.getRandom().toString();
        } else {
            return answerQueue.poll();
        }
    }
}
