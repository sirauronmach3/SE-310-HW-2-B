package Survey;

import Management.QuestionType;
import Survey.Question.Question;
import Survey.Response.OpenEndedResponse;
import Survey.Response.Response;
import utils.Out;
import utils.SerializationIDs;
import utils.TypesOfSurvey;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Test extends Survey{
    /**
     * ID for serialization
     */
    private final static long serialVersionUID = SerializationIDs.TEST.uid;
    /**
     * ArrayList of the correct answers
     */
    private final ArrayList<Response> correctAnswers = new ArrayList<>();
    private Double grade = null;
    private final String ungraded = "Ungraded";

    /**
     * Test constructor
     */
    public Test() {
        this.surveyType = TypesOfSurvey.TEST;
    }

    @Override
    public void addCorrectAnswer(Response correctAnswer) {
        this.correctAnswers.add(correctAnswer);
    }

    @Override
    public void displayWithKey() {
        Out out = Out.getInstance();
        int questionNumber = 0;
        Question question;
        Response answer;

        out.say("The Test with Correct Answers");
        out.say("---------------------------");

        for (int i = 0; i < questions.size(); i++) {
            // setup
            questionNumber++;
            question = questions.get(i);
            answer = correctAnswers.get(i);

            // display question and possibly given answer
            out.say("Question #" + questionNumber + " " + question.getQuestionType().label + " question: ");
            question.display();

            // display correct answer
            out.say("");
            if (question.getQuestionType() == QuestionType.ESSAY) {
                out.say("No specifically correct answer for this question.");
            } else {
                out.say("Correct answer for question #" + questionNumber +":");
                answer.display();
            }
        }

        displayGrade();
    }

    @Override
    public void display() {
        super.display();
        displayGrade();
    }

    private void displayGrade() {
        Out out = Out.getInstance();
        if (grade == null) {
            out.say(ungraded);
        } else {
            out.say("\nGrade: " + grade +"/100");
        }
    }

    @Override
    public Response getCorrectAnswer(int ordinal) {
        if (ordinal >= correctAnswers.size()) { // input validation
            return new OpenEndedResponse(); // dunno, this should respond true always for isEquals
        }
        return correctAnswers.get(ordinal);
    }

    @Override
    public void gradeTest() {
        // setup
        double totalQuestions = getNumberOfQuestions();
        double correctAnswers = 0;
        Response givenAnswer, correctAnswer;
        boolean correct;

        for (int i = 0; i < totalQuestions; i++) {
            // Get the given answer and the correct answer
            givenAnswer = getQuestion(i).getAnswer();
            correctAnswer = getCorrectAnswer(i);

            // Determine if the given answer is correct
            correct = givenAnswer.isEqual(correctAnswer);

            // if the answer is correct, increment the number of correct answers
            if (correct) {
                correctAnswers++;
            }
        }

        // update the grade based on this grading
        this.grade = (correctAnswers / totalQuestions) * 100;
    }

    /***********************************************Serialization****************************************************/

    /**
     * Saves the Survey.
     */
    public void save(String path, String filename) {
        try (ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(filename))) {
            objectOut.writeObject(this);
            System.out.println(this.getSurveyType().name + " has been serialized and saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
