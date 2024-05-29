package Survey.Response;

import utils.In;
import utils.Out;
import utils.SerializationIDs;

import java.util.ArrayList;

public class SelectionResponse extends Response {
    /**
     * ID for serialization
     */
    private final static long serialVersionUID = SerializationIDs.SELECTION_RESPONSE.uid;
    /**
     * List of answer(s)
     */
    private ArrayList<String> answer = new ArrayList<>();
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

    @Override
    public boolean isEqual(Response other) {
        if (this.getClass().equals(other.getClass())){
            ArrayList<String> thisAnswer = this.getAnswer();
            ArrayList<String> otherAnswer = ((SelectionResponse) other).getAnswer();

            if (thisAnswer.size() != otherAnswer.size()) {
                return false; /** If both responses do not have the same number of answers */
            }

            for (String string : thisAnswer) {
                if (otherAnswer.contains(string)) {
                    continue;
                }
                else {
                    return false; /** If this answer contains a string */
                }
            }

            return true;
        }
        return false; /** If other Response is different type */
    }

    public ArrayList<String> getAnswer() {
        ArrayList<String> answer = new ArrayList<>();
        for (String thisString : this.answer) {
            answer.add(thisString.toLowerCase());
        }

        return answer;
    }

    public void setAnswer(ArrayList<String> input) {
        this.answer = input;
    }
}
