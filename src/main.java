/**
 * Drexel SE-310-001 sp 23-24, hw 2 part b
 * Main class
 * Author: Matthew Martin mjm836
 * Date: 4/17/2024
 */
import Management.SurveyManager;

public class main {
    private static SurveyManager surveyManager;

    public static void main(String[] args) {
        String path = System.getProperty("user.dir");
        surveyManager = new SurveyManager(path);
        surveyManager.displayMenu();
    }
}
