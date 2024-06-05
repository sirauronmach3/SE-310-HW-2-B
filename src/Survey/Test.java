package Survey;

import Management.QuestionType;
import Survey.Question.Question;
import Survey.Response.Response;
import utils.Out;
import utils.SerializationIDs;
import utils.TypesOfSurvey;

import java.util.ArrayList;

public class Test extends Survey{
    /**
     * ID for serialization
     */
    private final static long serialVersionUID = SerializationIDs.TEST.uid;
    private final ArrayList<Response> correctAnswers = new ArrayList<>();

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
    }
}
