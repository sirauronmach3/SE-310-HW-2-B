package Management;

import utils.In;
import utils.Out;

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

        while (true) {
            out.say("Test Menu Options:");
            Out.getInstance().say("-------------------------------");
            for (int i = 0; i < 3; i++) {}
        }
    }
}
