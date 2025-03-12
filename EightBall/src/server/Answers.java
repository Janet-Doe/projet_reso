package server;

import java.util.List;
import java.util.Random;

/**
 * Enumeration of possible answers.
 */
public enum Answers {
    AS_I_SEE_IT_YES,
    ASK_AGAIN_LATER,
    BETTER_NOT_TELL_YOU_NOW,
    CANNOT_PREDICT_NOW,
    CONCENTRATE_AND_ASK_AGAIN,
    DON_T_COUNT_ON_IT,
    IT_IS_CERTAIN,
    IT_IS_DECIDEDLY_SO,
    MOST_LIKELY,
    MY_REPLY_IS_NO,
    MY_SOURCES_SAY_NO,
    OUTLOOK_GOOD,
    OUTLOOK_NOT_SO_GOOD,
    REPLY_HAZY_TRY_AGAIN,
    SIGNS_POINT_TO_YES,
    VERY_DOUBTFUL,
    WITHOUT_A_DOUBT,
    YES,
    YES_DEFINITELY,
    YOU_MAY_RELY_ON_IT;

    private static final List<Answers> VALUES = List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public String toString () {
        return switch (this) {
            case AS_I_SEE_IT_YES -> "As i see it, yes.";
            case BETTER_NOT_TELL_YOU_NOW -> "Better not tell you now.";
            case CANNOT_PREDICT_NOW -> "Cannot predict now.";
            case CONCENTRATE_AND_ASK_AGAIN -> "Concentrate and ask again.";
            case DON_T_COUNT_ON_IT -> "Don't count on it.";
            case IT_IS_CERTAIN -> "It is certain.";
            case IT_IS_DECIDEDLY_SO -> "It is decidedly so.";
            case MOST_LIKELY -> "Most likely.";
            case MY_REPLY_IS_NO -> "My reply is no.";
            case MY_SOURCES_SAY_NO -> "My sources say no.";
            case OUTLOOK_GOOD -> "Outlook good.";
            case OUTLOOK_NOT_SO_GOOD -> "Outlook not so good.";
            case REPLY_HAZY_TRY_AGAIN -> "Reply hazy, try again.";
            case SIGNS_POINT_TO_YES -> "Signs point to yes.";
            case VERY_DOUBTFUL -> "Very doubtful.";
            case WITHOUT_A_DOUBT -> "Without a doubt.";
            case YES -> "Yes.";
            case YES_DEFINITELY -> "Yes definitely.";
            case YOU_MAY_RELY_ON_IT -> "You may rely on it.";
            default -> "Ask again later.";
        };
    }

    public static Answers getRandom(){
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public static int getSize(){
        return SIZE;
    }

    public static Answers getAnswer(int i){
        return VALUES.get(i);
    }

}