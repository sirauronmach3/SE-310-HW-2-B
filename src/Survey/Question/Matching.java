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


    public ArrayList<String> getLeftColumn() {
        ArrayList<String> leftColumnCopy = new ArrayList<>(leftColumn);
        return leftColumnCopy;
    }

    public ArrayList<String> getRightColumn() {
        ArrayList<String> rightColumnCopy = new ArrayList<>(rightColumn);
        return rightColumnCopy;
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

    @Override
    public boolean isEqual(Question otherQuestion) {
        // basic question elements
        if(!super.isEqual(otherQuestion)) {
            return false;
        }

        Matching otherMatching = (Matching) otherQuestion;

        // leftColumn
        if(this.leftColumn.size() == otherMatching.leftColumn.size()){
            /**
             * For every item in this left column, check for equivalent option in other
             */
            for(int i = 0; i < this.leftColumn.size(); i++){
                boolean found = false;
                for(int j = 0; j < this.leftColumn.size(); j++){
                    if(this.leftColumn.get(i).equalsIgnoreCase(otherMatching.leftColumn.get(j))){
                        found = true;
                        break;
                    }
                }
                if(!found){ // if no match found, they are not the same
                    return false;
                }
            }
        } else {
            return false;
        }

        // rightColumn
        if(this.rightColumn.size() == otherMatching.rightColumn.size()){
            /**
             * For every item in this left column, check for equivalent option in other
             */
            for(int i = 0; i < this.rightColumn.size(); i++){
                boolean found = false;
                for(int j = 0; j < this.rightColumn.size(); j++){
                    if(this.rightColumn.get(i).equalsIgnoreCase(otherMatching.rightColumn.get(j))){
                        found = true;
                        break;
                    }
                }
                if(!found){ // if no match found, they are not the same
                    return false;
                }
            }
        }

        return true;
    }
}
