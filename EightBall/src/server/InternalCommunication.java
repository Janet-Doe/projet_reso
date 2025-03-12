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

    public static void putQuestionInWaitingList(byte[] data) {
        questionQueue.add(Arrays.toString(data));
    }

    public static void putAnswerInWaitingList(byte[] data) {
        answerQueue.add(Arrays.toString(data));
    }

    public static String getQuestion() {
        String question = questionQueue.poll();
        if (question == null) {
            return Questions.getRandom().toString();
        } else {
            return question;
        }
    }

    public static String getAnswer() {
        String answer = answerQueue.poll();
        if (answer == null) {
            return Answers.getRandom().toString();
        } else {
            return answer;
        }
    }
}
