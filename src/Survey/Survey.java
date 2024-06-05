package Survey;

import Survey.Question.Question;
import Survey.Response.Response;
import utils.Out;
import utils.SerializationIDs;
import utils.TypesOfSurvey;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Survey implements Serializable {
    /**
     * ID for serialization
     */
    private final static long serialVersionUID = SerializationIDs.SURVEY.uid;
    /**
     * ArrayList of the questions
     */
    protected final ArrayList<Question> questions = new ArrayList<>();
    protected TypesOfSurvey surveyType;

    /**
     * Survey constructor.
     */
    public Survey() {
        this.surveyType = TypesOfSurvey.SURVEY;
    }

    /**
     * Display the survey by listing out the question(s), their choices (if applicable), and their response(s).
     */
    public void display() {
        // setup
        Out out = Out.getInstance();

        // output header
        out.say("The " + this.surveyType.name);
        out.say("---------------------------");

        // output the questions
        int questionNumber = 0;
        for (Question question : this.questions) {
            questionNumber++;
            out.say("Question #" + questionNumber + " " + question.getQuestionType().label + " question: ");
            question.display();
            out.say(""); // line break for whitespace
        }
    }

    /**
     * Add given question to survey.
     *
     * @param newQuestion Question to be added to survey questions.
     */
    public void addQuestion(Question newQuestion) {
        questions.add(newQuestion);
    }

    /**
     * Take the survey.
     */
    public void take() {
        // setup
        Out out = Out.getInstance();
        int questionNumber = 1;

        // output header
        out.say("The " + this.surveyType.name);
        out.say("---------------------------");

        // display question
        for (Question question : questions) {
            out.say("Question #" + questionNumber + ": ");
            questionNumber++;
            question.ask();
        }
    }

    /**
     * Gets the number of questions in the Survey.
     *
     * @return int representing the size of the Survey.
     */
    public int getNumberOfQuestions() {
        return questions.size();
    }

    /**
     * Returns the question at the given ordinal in the list of questions.
     *
     * @param questionOrdinal the ordinal of the question to be returned.
     * @return Question at the given ordinal. Returns null if no question at that ordinal.
     */
    public Question getQuestion(int questionOrdinal) {
        if (questionOrdinal < 0 || questionOrdinal >= questions.size()) {
            return null;
        }
        return questions.get(questionOrdinal);
    }

    /**
     * Add the given response to the list of correct answers
     * @param correctAnswer Response that stores the correct answer to a question
     */
    public void addCorrectAnswer(Response correctAnswer) {
        throw new UnsupportedOperationException("Invalid Operation");
    }

    /**
     * Display the test with the correct answers
     */
    public void displayWithKey() {
        throw new UnsupportedOperationException("Invalid Operation");
    }

    /***********************************************Serialization****************************************************/

    /**
     * Saves the Survey.
     */
    public void save(String path, String filename) {
        try (ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(filename))) {
            objectOut.writeObject(this);
            System.out.println(this.surveyType.name + " has been serialized and saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
