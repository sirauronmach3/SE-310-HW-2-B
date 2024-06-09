package Survey.Response;

import utils.In;
import utils.Out;
import utils.SerializationIDs;

import java.util.ArrayList;

public class ShortAnswerResponse extends OpenEndedResponse {
    /**
     * ID for serialization
     */
    private final static long serialVersionUID = SerializationIDs.SHORT_ANSWER_RESPONSE.uid;

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


    @Override
    public boolean isEqual(Response other) {
        if (this.getClass().equals(other.getClass())) {
            ArrayList<String> thisAnswer = this.getAnswer();
            ArrayList<String> otherAnswer = ((ShortAnswerResponse) other).getAnswer();

            if (thisAnswer.size() != otherAnswer.size()) {
                return false; /** If both responses do not have the same number of answers */
            }

            for (String string : thisAnswer) {
                if (otherAnswer.contains(string)) {
                    continue;
                } else {
                    return false; /** If this answer contains a string */
                }
            }

            return true;
        }
        return false; /** If other Response is different type */
    }

    @Override
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
