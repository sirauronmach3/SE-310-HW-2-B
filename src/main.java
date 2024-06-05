/**
 * Drexel SE-310-001 sp 23-24, hw 2 part d
 * Main class
 * Author: Matthew Martin mjm836
 * Date: 4/17/2024
 */
import Management.MainDriver;
import Management.SurveyManager;

public class main {
    private static SurveyManager surveyManager;
    private static MainDriver mainDriver;

    public static void main(String[] args) {
        String path = System.getProperty("user.dir");
        mainDriver = new MainDriver(path);
        mainDriver.displayTopLevelMenu();
//        surveyManager = new SurveyManager(path);
//        surveyManager.displayMenu();
    }
}
