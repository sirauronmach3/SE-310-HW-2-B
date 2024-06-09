package Management;

import Survey.Question.Matching;
import Survey.Question.Question;
import Survey.QuestionEditor;
import Survey.Response.*;
import Survey.Test;
import utils.*;

import java.util.ArrayList;
import java.util.HashMap;

public class TestManager extends SurveyManager {
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

    /**
     * Handle the user selection from the top level menu for Test
     *
     * @param selection TestMangerMenuOptions enum representing the user's selection
     * @return boolean for whether to continue at the top menu or move back to the main driver.
     */
    protected boolean handleMenuSelection(TestManagerMenuOptions selection) {
        Out out = Out.getInstance();
        switch (selection) {
            case CREATE_NEW:
                out.say("Selection: " + TestManagerMenuOptions.CREATE_NEW.name);
                createTest();
                break;
            case DISPLAY_WITH_KEY:
                out.say("Selection: " + TestManagerMenuOptions.DISPLAY_WITH_KEY.name);
                displayWithKey();
                break;
            case DISPLAY_WITHOUT_KEY:
                out.say("Selection: " + TestManagerMenuOptions.DISPLAY_WITHOUT_KEY.name);
                displayNoKey();
                break;
            case LOAD:
                out.say("Selection: " + TestManagerMenuOptions.LOAD.name);
                loadSurvey(TypesOfSurvey.TEST);
                break;
            case SAVE:
                out.say("Selection: " + TestManagerMenuOptions.SAVE.name);
                saveSurvey(TypesOfSurvey.TEST);
                break;
            case TAKE:
                out.say("Selection: " + TestManagerMenuOptions.TAKE.name);
                takeTest();
                break;
            case MODIFY:
                out.say("Selection: " + TestManagerMenuOptions.MODIFY.name);
                modifyTest(); // TODO test modify for all question types
                break;
            case TABULATE:
                out.say("Selection: " + TestManagerMenuOptions.TABULATE.name);
                tabulate(); // TODO tabulate test
                break;
            case GRADE:
                out.say("Selection: " + TestManagerMenuOptions.GRADE.name);
                grade();
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

    private void modifyTest() {
        if (currentSurvey == null) {
            Out.getInstance().say("You must have a survey loaded in order to modify it.");
            return;
        }
        saved = false;
        Out out = Out.getInstance();
        In in = In.getInstance();
        int questionOrdinal;
        Question question;

        out.say("Enter the question number you wish to modify.");
        questionOrdinal = (in.readIntWithinRange(1, currentSurvey.getNumberOfQuestions()) - 1);
        question = currentSurvey.getQuestion(questionOrdinal);

        QuestionEditor.editQuestion(question);

        if (in.getYesNo("Do you want to change the correct answer for this question?")) {
            Response correctAnswer = currentSurvey.getCorrectAnswer(questionOrdinal);
            modifyCorrectAnswer(correctAnswer, question);
        }
    }

    private void modifyCorrectAnswer(Response correctAnswer, Question question) {
        QuestionType type = question.getQuestionType();
        switch (type) {
            case MULTIPLE_CHOICE: // fall through, multiple choice and true-false use the same response
            case TRUE_FALSE:
                multipleChoiceAnswer(correctAnswer, question);
                break;
            case ESSAY:
                break; // open-ended questions auto-grade to correct
            case SHORT_ANSWER:
                shortAnswer(correctAnswer, question);
                break;
            case MATCHING:
                matchingAnswer(correctAnswer, question);
                break;
            case VALID_DATE:
                validDateAnswer(correctAnswer);
                break;
        }
    }

    private void takeTest() {
        Out out = Out.getInstance();
        if (!saved) {
            if (!notSaved()) {
                out.say("Keeping current survey");
                return;
            }
        }

        loadSurvey(TypesOfSurvey.TEST);
        takeSurvey();
        grade();
    }

    /**
     * Grades the test if it has been taken
     */
    private void grade() {
        Out out = Out.getInstance();
        if (currentSurvey.isTaken()) {
            currentSurvey.gradeTest();
        } else {
            out.say("The test must be completed before grading.");
        }
    }

    /**
     * Displays the stored test with the answer key along with it
     */
    private void displayWithKey() {
        Out out = Out.getInstance();
        if (currentSurvey == null) {
            out.say("You must have a test loaded in order to display it.");
        } else {
            currentSurvey.displayWithKey();
        }
        out.say("\n\n"); // whitespace
    }

    /**
     * Displays the stored test
     */
    private void displayNoKey() {
        Out out = Out.getInstance();
        if (currentSurvey == null) {
            out.say("You must have a test loaded in order to display it.");
        } else {
            currentSurvey.display();
        }
        out.say("\n\n"); // whitespace
    }

    /**
     * Creates a test
     */
    private void createTest() {
        Out out = Out.getInstance();
        if (!saved) {
            if (!notSaved()) {
                out.say("Keeping current survey");
                return;
            }
        }
        currentSurvey = new Test();
        out.say("New Test Created");
        saved = false;

        addQuestionsToTest();
    }

    /**
     * Starts a loop of adding questions to the Test. Handles prompts, options, and correct answer for each question
     * added.
     */
    private void addQuestionsToTest() {
        Out out = Out.getInstance();
        In in = In.getInstance();
        boolean another = true;

        while (another) {
            Question newQuestion = QuestionEditor.createQuestion();
            if (newQuestion == null) { // error checking
                out.say("There was an issue creating a new question. Please try again." +
                        "\nIf this issue persists, troubleshoot.\n");
            } else {
                addCorrectAnswer(newQuestion);
                currentSurvey.addQuestion(newQuestion);

                out.say("Added a " + newQuestion.getQuestionType().label + " question to the survey.\n");
            }

            another = in.getYesNo("Would you like to add another question?");
        }
    }

    /**
     * Handles a new question to then create a correct answer to be stored in the test.
     *
     * @param newQuestion the new question that does not yet have a correct answer.
     */
    private void addCorrectAnswer(Question newQuestion) {
        // setup
        QuestionType type = newQuestion.getQuestionType();

        // get response to fill with correct answer
        Response correctAnswer = getNewResponse(newQuestion);

        // based on type of question, get correct answer from user
        modifyCorrectAnswer(correctAnswer, newQuestion);

        currentSurvey.addCorrectAnswer(correctAnswer);
    }

    /**
     * Populates the correct answer for a matching question
     *
     * @param correctAnswer Response to be populated
     * @param newQuestion   Question that is to have the correct answer created for it
     */
    private void matchingAnswer(Response correctAnswer, Question newQuestion) {
        // i/o
        Out out = Out.getInstance();
        In in = In.getInstance();

        // setup
        HashMap<String, String> answers = new HashMap<>();
        ArrayList<String> leftColumn = ((Matching) newQuestion).getLeftColumn();
        ArrayList<String> rightColumn = ((Matching) newQuestion).getRightColumn();

        int size, left, right;

        // getting answers
        out.say("Enter the correct answer ...");
        while ((size = leftColumn.size()) > 0) {
            out.say("Remaining options:");
            for (int j = 0; j < size; j++) {
                int number = j + 1;
                out.say(number + ") " + leftColumn.get(j) + "\t\t" + number + ") " + rightColumn.get(j));
            }

            // get options
            out.say("\nEnter number for left column of the pair:");
            left = in.readIntWithinRange(1, size);
            left--;
            out.say("Enter number for right column of the pair");
            right = in.readIntWithinRange(1, size);
            right--;

            // pop answers out of columns and into answer map
            answers.put(leftColumn.remove(left), rightColumn.remove(right));
        }

        correctAnswer.setAnswer(answers);
    }

    /**
     * Populates the correct answer for a Valid Date question
     *
     * @param correctAnswer Response to be populated
     */
    private void validDateAnswer(Response correctAnswer) {
        Out out = Out.getInstance();
        In in = In.getInstance();

        while (true) {
            out.say("Enter the correct date in the following format: mm/dd/yyyy");
            String correctDate = in.readStr();
            if (InputHelper.validateDate(correctDate)) { // validate date
                correctAnswer.setAnswer(correctDate); // add correct answer
                return;
            }
        }
    }

    /**
     * Populates the correct answer for a short answer question
     *
     * @param correctAnswer Response to be populated
     * @param newQuestion   Question that is to have the correct answer created for it
     */
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

    /**
     * Populates the correct answer for a true/false or multiple choice question
     *
     * @param correctAnswer Response to be populated
     * @param newQuestion   Question that is to have the correct answer created for it
     */
    private void multipleChoiceAnswer(Response correctAnswer, Question newQuestion) {
        Out out = Out.getInstance();
        In in = In.getInstance();

        ArrayList<String> answers = new ArrayList<>();
        int numberOfCorrectAnswers = newQuestion.getNumberOfAnswers();
        int numberOfOptions = newQuestion.getChoices().size();
        int selection;

        for (int i = 0; i < numberOfCorrectAnswers; i++) {
            out.say("Enter " + ((i > 0) ? "next" : "") + " correct answer");
            out.say("Select from the options:");

            for (int j = 0; j < newQuestion.getChoices().size(); j++) {
                out.say((j + 1) + ": " + newQuestion.getChoices().get(j));
            }

            out.say("Pick options by number");
            selection = (in.readIntWithinRange(1, numberOfOptions) - 1);

            answers.add(newQuestion.getChoices().get(selection));
        }
        correctAnswer.setAnswer(answers);
    }

    /**
     * Creates and returns a Response to store correct answer for the given Question
     *
     * @param newQuestion Question to have a correct answer assigned to it.
     * @return Response that still needs to have correct answer put into it
     */
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
                correctAnswer.updateMatches(((Matching) newQuestion).getLeftColumn(), ((Matching) newQuestion).getRightColumn());
                break;
            case VALID_DATE:
                correctAnswer = new DateResponse();
                break;
        }
        return correctAnswer;
    }
}
