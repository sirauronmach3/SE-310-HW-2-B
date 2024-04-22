package Survey.Response;

import utils.In;
import utils.Out;

public class ShortAnswerResponse extends OpenEndedResponse {
    /**
     * ID for serialization
     */
    private final static long serialVersionUID = 15L;

    @Override
    public void display() {
        // setup
        Out out = Out.getInstance();

        // based on number of answers, output answers
        if (answer.size() == 0) {
            out.say(unanswered);
        } else if (answer.size() == 1) {
            out.say("Answer: " + answer.get(0));
        } else {
            for (int i = 0; i < answer.size(); i++) {
                out.say("Answer #" + (i + 1) + ": " + answer.get(i));
            }
        }
    }

    @Override
    public void respond() {
        // setup
        In in = In.getInstance();
        Out out = Out.getInstance();
        String input;
        boolean another = true;

        // get answer
        while (another && (answer.size() < numberOfAllowedAnswers)) {
            input = in.readStr();
            answer.add(input);

            another = multipleAnswersAllowed && in.getYesNo("Would you like to provide an additional answer?");
        }
        out.say("");
    }
}
