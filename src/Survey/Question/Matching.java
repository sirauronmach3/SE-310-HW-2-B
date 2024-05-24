package Survey.Question;

import Management.QuestionType;
import Survey.Response.PairResponse;
import utils.Out;
import utils.SerializationIDs;

import java.util.ArrayList;

public class Matching extends Question {
    /**
     * ID for serialization
     */
    private final static long serialVersionUID = SerializationIDs.MATCHING.uid;
    private ArrayList<String> leftColumn = new ArrayList<>();
    private ArrayList<String> rightColumn = new ArrayList<>();

    /**
     * Constructor for Matching.
     */
    public Matching() {
        this.questionType = QuestionType.MATCHING;
        this.answer = new PairResponse();
    }

    @Override
    public void display() {
        displayPrompt();
        displayChoices();
        displayResponse();
    }

    public void displayChoices() {
        Out out = Out.getInstance();

        out.say("Choices:");
        for (int i = 0; i < leftColumn.size(); i++) {
            out.say(leftColumn.get(i) + "\t\t" + rightColumn.get(i));
        }
    }

    @Override
    public ArrayList<String> getChoices() {
        throw new UnsupportedOperationException("Invalid operation");
    }

    @Override
    public void ask() {
        displayPrompt();
        Out.getInstance().say("Match left column to right column in pairs");
        answer.updateMatches(leftColumn, rightColumn);
        answer.respond();
    }

    /**
     * Replaces current choices with new choices. Both columns must be of equal length.
     *
     * @param leftColumn  ArrayList<String>
     * @param rightColumn ArrayList<String>
     */
    public void updateChoices(ArrayList<String> leftColumn, ArrayList<String> rightColumn) {
        if (leftColumn != null && rightColumn != null) {
            if (leftColumn.size() == rightColumn.size()) {
                this.leftColumn = leftColumn;
                this.rightColumn = rightColumn;
            }
        }
    }
}
