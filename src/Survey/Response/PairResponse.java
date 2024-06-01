package Survey.Response;

import utils.In;
import utils.Out;
import utils.SerializationIDs;

import java.util.ArrayList;
import java.util.HashMap;

public class PairResponse extends Response {
    /**
     * ID for serialization
     */
    private final static long serialVersionUID = SerializationIDs.PAIR_RESPONSE.uid;
    /**
     * HashMap containing the String pairs of the answer
     */
    private final HashMap<String, String> answer = new HashMap<>();
    /**
     * List of Strings that populate the left column of options.
     */
    private ArrayList<String> leftColumn = new ArrayList<>();
    /**
     * List of Strings that populate the right column of options.
     */
    private ArrayList<String> rightColumn = new ArrayList<>();

    /**
     * Constructor for PairResponse Question.
     */
    public PairResponse() {
        this.multipleAnswersAllowed = false;
        this.numberOfAllowedAnswers = 1;
    }

    @Override
    public void respond() {
        // setup
        Out out = Out.getInstance();
        In in = In.getInstance();

        // option loop
        int size, left, right;
        while ((size = leftColumn.size()) > 0) {
            // display remaining options
            out.say("Remaining options:");
            for (int i = 0; i < size; i++) {
                int number = i + 1;
                out.say(number + ") " + leftColumn.get(i) + "\t\t" + number + ") " + rightColumn.get(i));
            }

            // get options
            out.say("\nEnter number for left column of the pair:");
            left = in.readIntWithinRange(1, size);
            left--;
            out.say("Enter number for right column of the pair");
            right = in.readIntWithinRange(1, size);
            right--;

            // pop answers out of columns and into answer map
            answer.put(leftColumn.remove(left), rightColumn.remove(right));
        }
    }

    @Override
    public void display() {
        // setup
        Out out = Out.getInstance();

        // output
        if (answer.size() == 0) { // if unanswered
            out.say("Unanswered.");
        } else { // output answer
            out.say("Answer pairs:");
            for (HashMap.Entry<String, String> entry : answer.entrySet()) {
                String left = entry.getKey();
                String right = entry.getValue();
                out.say(left + " -> " + right);
            }
        }
    }

    @Override
    public void updateMatches(ArrayList<String> leftColumn, ArrayList<String> rightColumn) {
        this.leftColumn = (ArrayList<String>) leftColumn.clone();
        this.rightColumn = (ArrayList<String>) rightColumn.clone();
    }

    @Override
    public boolean isEqual(Response response) {
        if (!(response instanceof PairResponse)) {
            return false;
        }
        else {
            HashMap<String, String> pairResponse = ((PairResponse) response).getAnswer();
            return this.answer.equals(pairResponse);
        }
    }

    public HashMap<String, String> getAnswer() {
        return new HashMap<String, String>(answer);
    }

    public void setAnswer(HashMap<String, String> answer) {
        this.answer.clear();
        this.answer.putAll(answer);
    }
}
