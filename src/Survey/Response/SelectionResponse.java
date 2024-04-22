package Survey.Response;

import utils.In;
import utils.Out;

import java.util.ArrayList;

public class SelectionResponse extends Response {
    /**
     * ID for serialization
     */
    private final static long serialVersionUID = 14L;
    /**
     * List of answer(s)
     */
    private final ArrayList<String> answer = new ArrayList<>();
    /**
     * List of choices for question
     */
    private ArrayList<String> choices;

    /**
     * SelectionResponse constructor
     */
    public SelectionResponse() {

    }

    @Override
    public void updateChoices(ArrayList<String> newChoices) {
        this.choices = newChoices;
    }


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
        Out out = Out.getInstance();
        In in = In.getInstance();
        boolean again = true;

        // get response
        while (again) { // while still allowed more answers, and want to provide additional answers
            out.say("Enter a number to indicate your response.");
            int response = in.readIntWithinRange(1, (choices.size() + 1));

            answer.add(choices.get(response - 1));

            if (answer.size() < numberOfAllowedAnswers) {
                again = in.getYesNo("Would you like to provide an additional answer?");
            } else {
                again = false;
            }
        }
    }

}
