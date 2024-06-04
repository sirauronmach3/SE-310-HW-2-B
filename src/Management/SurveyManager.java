package Management;

import Survey.Question.Question;
import Survey.QuestionEditor;
import Survey.Survey;
import utils.ComType;
import utils.In;
import utils.Out;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * SurveyManager class is the main driver for the Surveys.
 * It provides menus for loading, saving, creating, displaying, and modifying different Survey objects.
 * It has a path to the file storage location.
 *
 * SurveyManager is not Serializable
 */
public class SurveyManager {
    /**
     * Loaded Survey
     */
    private Survey currentSurvey = null;
    /**
     * Path to directory storing saved Surveys
     */
    protected final String path;
    /**
     * boolean tracking if the Survey has been saved since its most recent change
     */
    private boolean saved = true;

    /**
     * Survey Manager constructor
     *
     * @param path path to directory holding surveys
     */
    public SurveyManager(String path) {
        this.path = path;
        In.setComType(ComType.CONSOLE);
        Out.setComType(ComType.CONSOLE);
    }

    /**
     * Display the top level menu.
     * Gets user input and perform logic as appropriate
     *
     * Does not explicitly change anything. Passes user input to handleMenuSelection method.
     * Takes no arguments.
     * Returns no values.
     */
    public void displayMenu() {
        int selection = 0;
        Out out = Out.getInstance();
        boolean cont = true;

        while (cont) {
            out.say("Survey Menu Options:");
            out.say("-------------------------------");
            out.say("1: Create a new Survey");
            out.say("2: Display Survey");
            out.say("3: Load an existing Survey");
            out.say("4: Save the current Survey");
            out.say("5: Take the current Survey");
            out.say("6: Modify the current Survey");
            out.say("7: Return to previous menu");
            try {
                out.say("Pick a menu option by number: ");
                selection = In.getInstance().readIntWithinRange(1, 7);
            } catch (Exception e) {
                selection = 0;
            } finally {
                cont = handleMenuSelection(selection);
            }
        }
    }

    /**
     * Takes value associated with top menu selection, and perform associated operation.
     * Outputs the selection to improve UX.
     *
     * @param selection int relating to menu selection.
     */
    protected boolean handleMenuSelection(int selection) {
        Out out = Out.getInstance();

        switch (selection) {
            case 1:
                out.say("Selection: Create a new Survey");
                createSurvey();
                return true;
            case 2:
                out.say("Selection: Display Survey");
                displaySurvey();
                // display a survey
                return true;
            case 3:
                out.say("Selection: Load an existing Survey");
                loadSurvey();
                // Load a survey
                return true;
            case 4:
                out.say("Selection: Save the current Survey");
                saveSurvey();
                // save survey
                return true;
            case 5:
                out.say("Selection: Take the current Survey");
                takeSurvey();
                // take a survey
                return true;
            case 6:
                out.say("Selection: Modify the current Survey");
                modifySurvey();
                // modify a survey
                return true;
            case 7:
                out.say("Selection: Return to previous menu");
                return quit();
            default:
                out.say("Invalid selection");
                return true;
        }
    }

    /**
     * Exits the program.
     * Calls notSaved() to perform a check if the currently loaded survey is not saved.
     */
    protected boolean quit() {
        Out out = Out.getInstance();
        if (!saved) {
            if (!notSaved()) {
                out.say("Keeping current survey");
                return true;
            }
        }
        out.say("Exiting ....");
        return false;
    }

    /**
     * Informs user that the operation they are attempting will irrevocably delete any unsaved changes.
     * Then asks the user if they wish to continue.
     *
     * @return boolean representing the user's desire to continue with the operation.
     * <p>true = continue</p>
     * <p>false = stop </p>
     */
    private boolean notSaved() {
        return In.getInstance().getYesNo("Current survey has not been saved, do you wish to continue?\nCurrent survey will be lost.");
    }

    /**
     * Modify the loaded survey.
     * Prompts user for a question number, uses QuestionEditor class to edit the question.
     *
     * If no survey is loaded, will output error message.
     * No special handling for surveys with no questions.
     * Marks the survey as not saved.
     */
    private void modifySurvey() {
        // setup
        saved = false;
        Out out = Out.getInstance();
        In in = In.getInstance();
        int questionNumber = -1;
        Question question = null;

        if (currentSurvey == null) { // verification
            out.say("You must have a survey loaded in order to modify it.");
        } else {
            out.say("Enter the question number you wish to modify.");
            questionNumber = in.readIntWithinRange(1, currentSurvey.getNumberOfQuestions());
            questionNumber--;

            question = currentSurvey.getQuestion(questionNumber);

            QuestionEditor.editQuestion(question);
        }
    }

    /**
     * Take the loaded survey.
     */
    private void takeSurvey() {
        if (currentSurvey == null) {
            Out.getInstance().say("You must have a survey loaded in order to take it.");
        } else {
            currentSurvey.take();
            this.saved = false;
        }
    }

    /**
     * Lists saved surveys, selects survey, and loads it from path.
     */
    private void loadSurvey() {
        // setup
        Out out = Out.getInstance();
        In in = In.getInstance();

        // if there is a current survey which has not been saved since last changed
        if (!saved) {
            if (!notSaved()) { // If user wishes to keep current survey, exit the method
                out.say("Keeping current survey");
                return;
            }
        }

        // get and list files in directory
        ArrayList<File> files = getListSavedSurveys();
        listSavedSurveys(files);

        // ask for which survey
        int size = files.size();
        out.say((size + 1) + ") Previous Menu");
        out.say("\nPick a file to load by number:");
        int selection = in.readIntWithinRange(1, size + 1);

        // load survey
        handleLoadMenuSelection(selection, files);
    }

    private void handleLoadMenuSelection(int selection, ArrayList<File> files) {
        if (selection <= files.size()) {
            File fileToBeLoaded = files.get(selection - 1);
            loadFile(fileToBeLoaded);
            saved = true;
        }// else, selection was to leave menu
    }

    private void loadFile(File fileToBeLoaded) {
        try (ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(fileToBeLoaded))) {
            Survey loadedSurvey = (Survey) objectIn.readObject();
            System.out.println("Survey object has been deserialized and loaded.");
            // Do something with the loadedSurvey object

            // Assign the loadedSurvey to the currentSurvey variable
            this.currentSurvey = loadedSurvey;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void listSavedSurveys(ArrayList<File> files) {
        Out out = Out.getInstance();

        for (int i = 0; i < files.size(); i++) {
            out.say((i + 1) + ") " + files.get(i).getName());
        }
    }

    private ArrayList<File> getListSavedSurveys() {
        File directory = new File(path);

        // check if the path is a directory
        if (!directory.isDirectory()) {
            System.err.println("Specified path is not a directory.");
            return new ArrayList<>();
        }

        // list files in the directory
        File[] filesArray = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".ser"));

        // convert the array to an ArrayList
        ArrayList<File> fileList = new ArrayList<>(Arrays.asList(filesArray));

        return fileList;
    }

    /**
     * Save the currently loaded survey.
     */
    private void saveSurvey() {
        Out out = Out.getInstance();
        In in = In.getInstance();
        if (this.currentSurvey == null) { // verification
            out.say("You must have a survey loaded in order to save it.");
        } else {
            out.say("What would you like to name this survey?");
            String filename = in.readFilename() + ".ser";
            this.currentSurvey.save(this.path, filename);

            this.saved = true;
        }
    }

    /**
     * Display current survey.
     */
    private void displaySurvey() {
        if (currentSurvey == null) {// verification
            Out.getInstance().say("You must have a survey loaded in order to display it.");
        } else {
            currentSurvey.display();
        }
    }

    /**
     * Create a new survey.
     */
    private void createSurvey() {
        if (!saved) {
            if (!notSaved()) {
                Out.getInstance().say("Keeping current survey");
                return;
            }
        }
        currentSurvey = new Survey();
        Out.getInstance().say("New Survey Created.");
        saved = false;

        addQuestionToSurvey();
    }

    /**
     * Adds a question(s) to the loaded survey. <p>Uses QuestionEditor.
     */
    private void addQuestionToSurvey() {
        Out out = Out.getInstance();
        In in = In.getInstance();
        boolean another = true;

        // loop while user wants to add questions
        while (another) {
            Question newQuestion = QuestionEditor.createQuestion();
            if (newQuestion == null) {
                break;
            }
            currentSurvey.addQuestion(newQuestion);

            out.say(""); // whitespace
            another = in.getYesNo("Would you like to add another question?");
        }
    }
}