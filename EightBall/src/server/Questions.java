package server;

import java.util.List;
import java.util.Random;

/**
 * Enumeration of possible default questions, and client questions list.
 */
public enum Questions {
    ROMANCE,
    MONEY,
    FUTURE,
    FAMILY,
    STUDIES,
    BREAKUP,
    FRIEND,
    EX,
    WORK;

    private static final List<Questions> VALUES = List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public String toString(){
        return switch (this){
            case ROMANCE -> "Will my romantic life be fulfilling this year?";
            case MONEY -> "Will I win at the lottery?";
            case FAMILY -> "Will I have a big family during my life?";
            case WORK -> "Will I get the promotion?";
            case STUDIES -> "Will I do alright my exams this semester?";
            case BREAKUP -> "Should I dump my partner?";
            case FRIEND -> "Should I invite my friend to hangout?";
            case EX -> "Should I call my ex?";
            case FUTURE -> "Will I be happy?";
            default -> "Is this game really working?";
        };
    }

    public static Questions getRandom(){
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
