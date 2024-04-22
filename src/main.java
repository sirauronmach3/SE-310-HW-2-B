import Management.SurveyManager;

public class main {
    private static SurveyManager surveyManager;

    public static void main(String[] args) {
        String path = System.getProperty("user.dir");
        surveyManager = new SurveyManager(path);
        surveyManager.displayMenu();
    }
}
