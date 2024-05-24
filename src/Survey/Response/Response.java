package Survey.Response;

import utils.SerializationIDs;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Response implements Serializable {
    /**
     * String for unanswered questions.
     */
    protected static final String unanswered = "Unanswered.";
    /**
     * ID for serialization.
     */
    private static final long serialVersionUID = SerializationIDs.RESPONSE.uid;
    /**
     * boolean, if multiple answers are allowed for this question.
     */
    protected boolean multipleAnswersAllowed = false;
    /**
     * int representing the number of answers which are allowed for this response.
     */
    protected int numberOfAllowedAnswers = 1;

    /**
     * Outputs the contents of the answer.
     */
    public abstract void display();

    /**
     * Gets the user's answer(s) for the question and stores it.
     */
    public abstract void respond();

    /**
     * Set multipleAnswersAllowed
     *
     * @param multipleAnswersAllowed boolean
     */
    public void setMultipleAnswersAllowed(boolean multipleAnswersAllowed) {
        this.multipleAnswersAllowed = multipleAnswersAllowed;
    }

    /**
     * Set the number of answers that are permitted
     *
     * @param numberOfAnswers number of answers allowed
     */
    public void setNumberOfAnswers(int numberOfAnswers) {
        this.numberOfAllowedAnswers = numberOfAnswers;
    }

    /**
     * Not sure what this is supposed to be, but the rubric for the UML diagram required it. So it is here.
     */
    public void edit() {
        throw new UnsupportedOperationException("Invalid Operation");
    }

    /**
     * Replaces current choices for question with new list of choices.
     * <p>ONLY supported for MULTIPLE_CHOICE</p>
     *
     * @param newChoices ArrayList<String> of question answer options.
     */
    public void updateChoices(ArrayList<String> newChoices) {
        throw new UnsupportedOperationException("Invalid operation");
    }
    /**
     * Replaces current choices for question with new lists of choices.
     * <p>ONLY supported for MATCHING</p>
     *
     * @param leftColumn  ArrayList<String> of left column's options.
     * @param rightColumn ArrayList<String> of left column's options.
     */
    public void updateMatches(ArrayList<String> leftColumn, ArrayList<String> rightColumn) {
        throw new UnsupportedOperationException("Invalid Operation");
    }
}
