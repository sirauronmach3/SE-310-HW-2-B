package Management;

import Survey.Question.Matching;
import Survey.Question.Question;
import Survey.QuestionEditor;
import Survey.Response.*;
import Survey.Test;
import utils.In;
import utils.Out;
import utils.TestManagerMenuOptions;
import utils.InputHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class TestManager extends SurveyManager{
    /**
     * Survey Manager constructor
     *
     * @param path path to directory holding surveys
     */
    public TestManager(String path) {
        super(path);
    }

    public void displayMenu() {
        int selection = 0;
        Out out = Out.getInstance();
        In in = In.getInstance();
        int size = TestManagerMenuOptions.values().length;
        boolean cont = true;

        while (cont) {
            out.say("Test Menu Options:");
            Out.getInstance().say("-------------------------------");
            for (int i = 0; i < size; i++) {
                out.say((i + 1) + ": " + TestManagerMenuOptions.values()[i].name);
            }
            try {
                out.say("Pick a menu option by number");
                selection = in.readIntWithinRange(1, size);
            } catch (Exception e) {
                selection = TestManagerMenuOptions.RETURN.ordinal() + 1;
            } finally {
                cont = handleMenuSelection(TestManagerMenuOptions.values()[(selection - 1)]);
            }
        }
    }

    protected boolean handleMenuSelection(TestManagerMenuOptions selection) {
        Out out = Out.getInstance();
        switch (selection) {
            case CREATE_NEW:
                out.say("Selection: " + TestManagerMenuOptions.CREATE_NEW.name);
                createTest(); // TODO
                break;
            case DISPLAY_WITH_KEY:
                out.say("Selection: " + TestManagerMenuOptions.DISPLAY_WITH_KEY.name);
//                displayWithKey(); // TODO displayWithKey
                break;
            case DISPLAY_WITHOUT_KEY:
                out.say("Selection: " + TestManagerMenuOptions.DISPLAY_WITHOUT_KEY.name);
//                displayNoKey(); // TODO displayNoKey
                break;
            case LOAD:
                out.say("Selection: " + TestManagerMenuOptions.LOAD.name);
//                load(); // TODO load
                break;
            case SAVE:
                out.say("Selection: " + TestManagerMenuOptions.SAVE.name);
//                save(); // TODO save
                break;
            case TAKE:
                out.say("Selection: " + TestManagerMenuOptions.TAKE.name);
//                take(); // TODO take
                break;
            case MODIFY:
                out.say("Selection: " + TestManagerMenuOptions.MODIFY.name);
//                modify(); // TODO modify
                break;
            case TABULATE:
                out.say("Selection: " + TestManagerMenuOptions.TABULATE.name);
//                tabulate(); // TODO tabulate test
                break;
            case GRADE:
                out.say("Selection: " + TestManagerMenuOptions.GRADE.name);
//                grade(); // TODO grade
                break;
            case RETURN:
                out.say("Selection: " + TestManagerMenuOptions.RETURN.name);
                return quit();
            default:
                out.say("Invalid Selection");
                break;
        }
        return true;
    }

    private void createTest() {
        Out out = Out.getInstance();
        if(!saved) {
            out.say("Keeping current survey");
            return;
        }
        currentSurvey = new Test();
        out.say("New Test Created");
        saved = false;
        
        addQuestionsToTest();
    }

    private void addQuestionsToTest() {
        Out out = Out.getInstance();
        In in = In.getInstance();
        boolean another = true;

        while (another) {
            Question newQuestion = QuestionEditor.createQuestion();
            if (newQuestion == null) {
                break;
            }
            addCorrectAnswer(newQuestion);
            currentSurvey.addQuestion(newQuestion);


            out.say("Added a " + newQuestion.getQuestionType().label + " question to the survey.");
            out.say(""); // whitespace
            another = in.getYesNo("Would you like to add another question?");
        }
    }

    private void addCorrectAnswer(Question newQuestion) {
        // setup
        QuestionType type = newQuestion.getQuestionType();

        // get response to fill with correct answer
        Response correctAnswer = getNewResponse(newQuestion);

        // based on type of question, get correct answer from user
        switch(type) {
            case MULTIPLE_CHOICE: // fall through, multiple choice and true-false use the same response
            case TRUE_FALSE:
                multipleChoiceAnswer(correctAnswer, newQuestion);
                break;
            case ESSAY:
                break; // open-ended questions auto-grade to correct
            case SHORT_ANSWER:
                shortAnswer(correctAnswer, newQuestion);
                break;
            case MATCHING:
                matchingAnswer(correctAnswer, newQuestion);
                break;
            case VALID_DATE:
                validDateAnswer(correctAnswer);
                break;
        }
    }

    private void matchingAnswer(Response correctAnswer, Question newQuestion) {
        // i/o
        Out out = Out.getInstance();
        In in = In.getInstance();

        // setup
        HashMap<String, String> answers = new HashMap<>();
        ArrayList<String> leftColumn = ((Matching) newQuestion).getLeftColumn();
        ArrayList<String> rightColumn = ((Matching) newQuestion).getRightColumn();

        // list options
        // TODO

        // get pairs
        // TODO

        correctAnswer.setAnswer(answers);
    }

    private void validDateAnswer(Response correctAnswer) {
        Out out = Out.getInstance();
        In in = In.getInstance();

        while(true){
            out.say("Enter the correct date in the following format: mm/dd/yyyy");
            String correctDate = in.readStr();
            if(InputHelper.validateDate(correctDate)) { // validate date
                correctAnswer.setAnswer(correctDate); // add correct answer
                return;
            }
        }
    }

    private void shortAnswer(Response correctAnswer, Question newQuestion) {
        Out out = Out.getInstance();
        In in = In.getInstance();

        ArrayList<String> answers = new ArrayList<>();
        int numberOfCorrectAnswers = newQuestion.getNumberOfAnswers();

        for (int i = 0; i < numberOfCorrectAnswers; i++) {
            out.say("Enter " + ((i > 0) ? "next" : "") + " correct answer");
            String answer = in.readStr();
            answers.add(answer);
        }

        correctAnswer.setAnswer(answers);
    }

    private void multipleChoiceAnswer(Response correctAnswer, Question newQuestion) {
        Out out = Out.getInstance();
        In in = In.getInstance();

        ArrayList<String> answers = new ArrayList<>();
        int numberOfCorrectAnswers = newQuestion.getNumberOfAnswers();
        int numberOfOptions = newQuestion.getChoices().size();
        int selection = 0;

        for (int i = 0; i < numberOfCorrectAnswers; i++) {
            out.say("Enter " + ((i > 0) ? "next" : "") + " correct answer");
            out.say("Select from the options:");

            for (int j = 0; j < newQuestion.getChoices().size(); j++) {
                out.say((i + 1) + ": " + newQuestion.getChoices().get(j));
            }

            out.say("Pick options by number");
            selection = (in.readIntWithinRange(1, numberOfOptions) - 1);

            answers.add(newQuestion.getChoices().get(selection));
        }
        correctAnswer.setAnswer(answers);
    }

    private Response getNewResponse(Question newQuestion) {
        QuestionType type = newQuestion.getQuestionType();
        Response correctAnswer = null;

        switch (type) {
            case MULTIPLE_CHOICE:
            case TRUE_FALSE:
                correctAnswer = new SelectionResponse();
                correctAnswer.updateChoices(newQuestion.getChoices());
                break;
            case ESSAY:
                correctAnswer = new OpenEndedResponse();
                break;
            case SHORT_ANSWER:
                correctAnswer = new ShortAnswerResponse();
                break;
            case MATCHING:
                correctAnswer = new PairResponse();
                correctAnswer.updateChoices(newQuestion.getChoices());
                break;
            case VALID_DATE:
                correctAnswer = new DateResponse();
                break;
        }
        return correctAnswer;
    }
}
