package server;

import java.util.*;

public class InternalCommunication {
    private static final Map<String, CommunicationThread> DNS = new HashMap<>();
    private static final Queue<String> questionQueue = new LinkedList<>();
    private static final Queue<String> answerQueue   = new LinkedList<>();

    public static Map<String, CommunicationThread> getDNS() {
        return DNS;
    }

    public static boolean containsKey(String incomingUsername) {
        return DNS.containsKey(incomingUsername);
    }

    public static void put(String incomingUsername, CommunicationThread newThread) {
        DNS.put(incomingUsername, newThread);
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
