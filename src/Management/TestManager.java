package Management;

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
                selection = 0;
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
                createTest();
                break;
            case DISPLAY_WITH_KEY:
                out.say("Selection: " + TestManagerMenuOptions.DISPLAY_WITH_KEY.name);
                break;
            case DISPLAY_WITHOUT_KEY:
                out.say("Selection: " + TestManagerMenuOptions.DISPLAY_WITHOUT_KEY.name);
                break;
            case LOAD:
                out.say("Selection: " + TestManagerMenuOptions.LOAD.name);
                break;
            case SAVE:
                out.say("Selection: " + TestManagerMenuOptions.SAVE.name);
                break;
            case TAKE:
                out.say("Selection: " + TestManagerMenuOptions.TAKE.name);
                break;
            case MODIFY:
                out.say("Selection: " + TestManagerMenuOptions.MODIFY.name);
                break;
            case TABULATE:
                out.say("Selection: " + TestManagerMenuOptions.TABULATE.name);
                break;
            case GRADE:
                out.say("Selection: " + TestManagerMenuOptions.GRADE.name);
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

    }
}
