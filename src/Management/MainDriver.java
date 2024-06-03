package Management;

import utils.In;
import utils.Out;
import utils.TypesOfSurvey;

import static utils.TypesOfSurvey.*;

public class MainDriver {
    private final String path;
    private SurveyManager surveyManager;

    public MainDriver(String path) {
        this.path = path;
    }

    // TODO survery or Test
    public void displayTopLevelMenu() {
        Out out = Out.getInstance();
        In in = In.getInstance();
        int selection = 0;
        int sizeOfOptions = values().length;

        while (true) {
            out.say("Top Level Menu");
            out.say("What type of survey would you like to work on?");

            for (int i = 0; i < sizeOfOptions; i++) {
                out.say((values()[i].ordinal() + 1) + ": " + values()[i].toString());
            }
            try {
                out.say("Pick a menu option by number: ");
                selection = in.readIntWithinRange(1, (sizeOfOptions - 1));
            } catch (Exception e) {
                selection = -1;
            } finally {
                handleTopLevelMenu(values()[selection - 1]);
            }
        }
    }

    public void handleTopLevelMenu(TypesOfSurvey type){
        switch (type) {
            case TEST:
                testManagerStart();
                break;
            case SURVEY:
                surveyManagerStart();
                break;
            case EXIT:
                quit();
                break;
            default:
                Out.getInstance().say("Invalid selection");

        }
    }

    private void testManagerStart() {
        this.surveyManager = new TestManager(path);
        surveyManager.displayMenu();
    }

    private void quit() {
        Out.getInstance().say("Exiting");
        System.exit(0);
    }

    private void surveyManagerStart() {
        this.surveyManager = new SurveyManager(path);
        surveyManager.displayMenu();
    }
}
