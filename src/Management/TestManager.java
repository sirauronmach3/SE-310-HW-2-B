package Management;

import Survey.Question.Question;
import Survey.QuestionEditor;
import Survey.Test;
import utils.In;
import utils.Out;
import utils.TestManagerMenuOptions;

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
//                createTest(); // TODO
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

            out.say(""); // whitespace
            another = in.getYesNo("Would you like to add another question?");
        }
    }

    private void addCorrectAnswer(Question newQuestion) {

    }
}
