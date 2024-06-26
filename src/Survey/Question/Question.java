package Survey.Question;

import Management.QuestionType;
import Survey.Response.Response;
import utils.Out;
import utils.SerializationIDs;

import java.io.Serializable;
import java.util.ArrayList;

/***
 * Question Class
 * Abstract class which has the template for all types of questions.
 * All questions are serializable.
 * An enumerator QuestionType has a list of all the different types of questions.
 * Supports modifying the current question.
 */
public abstract class Question implements Serializable {
    /**
     * ID for serialization
     */
    private final static long serialVersionUID = SerializationIDs.QUESTION.uid;

    /**
     * Response storing the answer
     */
    protected Response answer = null;

    /**
     * boolean, if multiple answers are allowed for this question
     */
    protected boolean multipleAnswersAllowed = false;

    /**
     * enum storing the type of question this is.
     */
    protected QuestionType questionType = null;

    /**
     * String representing the prompt for the question.
     */
    protected String prompt = "";

    /**
     * Displays the question's prompt, choices (if any), and answer (if answered).
     */
    public void display() {
        displayPrompt();
        displayResponse();
    }

    /**
     * Display's this question's answer.
     */
    protected void displayResponse() {
        if (answer != null) {
            answer.display();
        } else {
            Out.getInstance().say("Question Unanswered");
        }
    }

    /**
     * Displays the question's prompt.
     */
    public void displayPrompt() {
        Out.getInstance().say(this.prompt);
    }

    /**
     * Replace current prompt with new prompt
     *
     * @param prompt String representing new prompt for the question.
     */
    public void newPrompt(String prompt) {
        this.prompt = prompt;
    }

    /**
     * Asks the question
     */
    public void ask() {
        displayPrompt();
        respondToPrompt();
    }

    /**
     * Method to get user's response to the question.
     * call answer's respond() method
     */
    protected void respondToPrompt() {
        answer.respond();
    }

    /**
     * Set the number of answers that are permitted
     *
     * @param questions number of questions allowed
     */
    public void setNumberOfAnswers(int questions) {
        answer.setNumberOfAnswers(questions);
    }

    /**
     * Gets the question type
     *
     * @return QuestionType Enum relating to this question.
     */
    public QuestionType getQuestionType() {
        return this.questionType;
    }

    /**
     * Display answer options
     */
    public  void displayChoices(){
        throw new UnsupportedOperationException("Invalid operation");
    };

    /**
     * Gets an array of the answer choices
     *
     * @return ArrayList<String> representing the answer choices.
     */
    public ArrayList<String> getChoices() {
        throw new UnsupportedOperationException("Invalid operation");
    }

    public int getNumberOfAnswers() {
        return answer.getNumberOfAnswers();
    }

    /**
     * Returns the answer to this question
     *
     * @return Response containing the given answer to the question
     */
    public Response getAnswer() {
        return answer;
    }

    /**
     * Returns whether the Question and the given Question are equal (ignoring user answer)
     * @param otherQuestion A question to be compared against this question
     * @return True if the two questions are sufficiently equivalent
     * <p></p>
     * False if the two questions are dissimilar.
     */
    public boolean isEqual(Question otherQuestion) {
        if (this.answer == null) {
            if (otherQuestion.answer != null) {
                return false;
            }
        }
        return (this.multipleAnswersAllowed == otherQuestion.multipleAnswersAllowed) &&
                (this.prompt.equalsIgnoreCase(otherQuestion.prompt)) &&
                (this.questionType == otherQuestion.questionType) &&
                (this.answer.getNumberOfAnswers() == otherQuestion.answer.getNumberOfAnswers());
    }

    public String getPrompt() {
        return new String(prompt);
    }
}
