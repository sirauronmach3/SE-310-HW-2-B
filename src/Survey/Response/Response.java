package Survey.Response;

import utils.SerializationIDs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

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

    /**
     *  Compares a response to the calling object's response and determines if they are sufficiently equivalent.
     *  For use in grading tests by comparing a given response to the correct answer.
     * @param other Response to be compared to calling Response Object
     * @return <ul>False if: <li>calling Response is not a concrete implementation of response.</li>
     *      <li>False if calling response and other Response are not the same response.</li>
     *      <li>False if calling response and other Response do not have identical (ignoring case) content. </li></ul>
     *      <p></p>
     *      True if calling response and other response are of the same type of response and have teh same content.
     */
    public boolean isEqual(Response other) {
        return false;
    }

    public int getNumberOfAnswers() {
        return this.numberOfAllowedAnswers;
    }

    public void setAnswer(ArrayList<String> answers){
        throw new UnsupportedOperationException("Invalid Operation");
    }

    public void setAnswer(HashMap<String, String> answer) {
        throw new UnsupportedOperationException("Invalid Operation");
    }
}
