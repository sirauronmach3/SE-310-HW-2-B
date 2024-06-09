package Management;

import Survey.Question.Question;
import Survey.QuestionEditor;
import Survey.Response.*;
import Survey.Survey;
import utils.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static utils.SurveyManagerMenuOptions.*;

/**
 * SurveyManager class is the main driver for the Surveys.
 * It provides menus for loading, saving, creating, displaying, and modifying different Survey objects.
 * It has a path to the file storage location.
 * <p>
 * SurveyManager is not Serializable
 */
public class SurveyManager {
    /**
     * Loaded Survey
     */
    protected Survey currentSurvey = null;
    /**
     * Path to directory storing saved Surveys
     */
    protected final String path;
    /**
     * boolean tracking if the Survey has been saved since its most recent change
     */
    protected boolean saved = true;

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
     * <p>
     * Does not explicitly change anything. Passes user input to handleMenuSelection method.
     * Takes no arguments.
     * Returns no values.
     */
    public void displayMenu() {
        int selection = 0;
        Out out = Out.getInstance();
        SurveyManagerMenuOptions[] options = SurveyManagerMenuOptions.values();
        int size = options.length;
        boolean cont = true;

        while (cont) {
            out.say("Survey Menu Options:");
            out.say("-------------------------------");
            for (int i = 0; i < size; i++) {
                out.say((i + 1) + ": " + options[i].name);
            }
            try {
                out.say("Pick a menu option by number: ");
                selection = In.getInstance().readIntWithinRange(1, size) - 1;
            } catch (Exception e) {
                selection = SurveyManagerMenuOptions.RETURN.ordinal();
            } finally {
                cont = handleMenuSelection(options[selection]);
            }
        }
    }

    /**
     * Takes value associated with top menu selection, and perform associated operation.
     * Outputs the selection to improve UX.
     *
     * @param selection int relating to menu selection.
     */
    protected boolean handleMenuSelection(SurveyManagerMenuOptions selection) {
        Out out = Out.getInstance();

        switch (selection) {
            case CREATE_NEW:
                out.say("Selection: " + CREATE_NEW.name);
                createSurvey();
                break;
            case DISPLAY:
                out.say("Selection: " + DISPLAY.name);
                displaySurvey();
                // display a survey
                break;
            case LOAD:
                out.say("Selection: " + LOAD.name);
                loadSurvey(TypesOfSurvey.SURVEY);
                // Load a survey
                break;
            case SAVE:
                out.say("Selection: " + SAVE.name);
                saveSurvey(TypesOfSurvey.SURVEY);
                // save survey
                break;
            case TAKE:
                out.say("Selection: " + TAKE.name);
                takeSurvey();
                // take a survey
                break;
            case MODIFY:
                out.say("Selection: " + MODIFY.name);
                modifySurvey();
                // modify a survey
                break;
            case TABULATE:
                out.say("Selection: " + TABULATE.name);
                tabulate();
                break;
            case RETURN:
                out.say("Selection: " + RETURN.name);
                return quit();
            default:
                out.say("Invalid selection");
                break;
        }
        return true;
    }

    /**
     * Searches through path and tabulates all responses to questions.
     */
    protected void tabulate() {
        // Setup
        In in = In.getInstance();
        Out out = Out.getInstance();
        ArrayList<Survey> surveys = new ArrayList<>();
        Survey survey;

        if (currentSurvey.isTaken()) {
            surveys.add(currentSurvey);
        }

        // get surveys from directory
        // compare against stored survey, if they are (and they have been taken) add them to the list of Surveys
        ArrayList<File> files = getListSavedSurveys(currentSurvey.getSurveyType());
        for (File file: files) {
            survey = loadFile(file);
            if (currentSurvey.isEqual(survey) && survey.isTaken()) {
                surveys.add(survey);
            }
        }

        if (surveys.size() == 0) {
            out.say("In the current directory, there are no versions of this " +
                    currentSurvey.getSurveyType().name + " that have been taken ");
            return;
        }

        // for each question, add responses to list of list of answers.
        ArrayList<String> tabulation = new ArrayList<>();
        int size = currentSurvey.getNumberOfQuestions();
        survey = null;
        for (int ordinal = 0; ordinal < size; ordinal++) {
            Map<String, Integer> map = new HashMap<>();
            QuestionType type = currentSurvey.getQuestion(ordinal).getQuestionType();

            for (int surveyNumber = 0; surveyNumber < surveys.size(); surveyNumber++) {
                survey = surveys.get(surveyNumber);
                Question question = survey.getQuestion(ordinal);
                handleQuestionTabulation(map, type, ordinal, question);
            }

            String string = transformTabulationMapIntoString(map, type);

            tabulation.add(ordinal, string);
        }

        // List each prompt in survey, and output tabulation for that question
        for (int i = 0; i < currentSurvey.getNumberOfQuestions(); i++) {
            out.say("Question " + (i + 1) + ": \n" + currentSurvey.getQuestion(i).getPrompt() + "\n" + tabulation.get(i));
        }
    }

    private String transformTabulationMapIntoString(Map<String, Integer> map, QuestionType type) {
        String string = new String();
        switch (type) {
            case MULTIPLE_CHOICE:
            case TRUE_FALSE:
            case VALID_DATE:
            case MATCHING:
            case SHORT_ANSWER:
                string = transformSelection(map);
                break;
            case ESSAY:
                string = transformEssay(map);
                break;
            default:
                break;
        }

        return string;
    }

    private String transformEssay(Map<String, Integer> map) {
        String result = "";

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            result += entry.getKey() + "\n";
        }

        return result;
    }

    private String transformSelection(Map<String, Integer> map) {
        String result = "";
        for (String key: map.keySet()) {
            String line = key + ": " + map.get(key) + "\n";
            result += line;
        }

        return result;
    }

    private void handleQuestionTabulation(Map<String, Integer> map, QuestionType type, int ordinal, Question question) {
        switch (type) {
            case MULTIPLE_CHOICE:
                multipleChoiceTabulation(map, ((SelectionResponse) question.getAnswer()));
                break;
            case TRUE_FALSE:
                map.put("true", 0);
                map.put("false", 0);
                multipleChoiceTabulation(map, ((SelectionResponse) question.getAnswer()));
                break;
            case ESSAY:
                writtenAnswerTabulation(map, ((OpenEndedResponse) question.getAnswer()));
            case SHORT_ANSWER:
                writtenAnswerTabulation(map, (OpenEndedResponse) question.getAnswer());
                break;
            case MATCHING:
                matchingTabulation(map, ((PairResponse) question.getAnswer()));
                break;
            case VALID_DATE:
                dateTabulation(map, (DateResponse) question.getAnswer());
                break;
            default:
                break;
        }
    }

    private void dateTabulation(Map<String, Integer> map, DateResponse answer) {
        int[] response = answer.getAnswer();
        String separator = "/";
        String date = response[0] + separator + response[1] + separator + response[2];

        if (map.containsKey(date)) {
            map.put(date, map.get(date) + 1);
        } else {
            map.put(date, 1);
        }
    }

    private void matchingTabulation(Map<String, Integer> map, PairResponse answer) {
        String answerString = "";
        HashMap<String, String> hashMap = answer.getAnswer();

        for (String key: hashMap.keySet()) {
            answerString += key + "\t" + hashMap.get(key) + "\n";
        }

        if (map.containsKey(answerString)) {
            map.put(answerString, map.get(answerString) + 1);
        } else {
            map.put(answerString, 1);
        }
    }

    private void writtenAnswerTabulation(Map<String, Integer> map, OpenEndedResponse answer) {
        ArrayList<String> answers = answer.getAnswer();

        for (int i = 0; i < answers.size(); i++) {
            String thisAnswer = answers.get(i);

            if (map.containsKey(thisAnswer)) {
                map.put(thisAnswer, map.get(thisAnswer) + 1);
            } else {
                map.put(thisAnswer, 1);
            }
        }
    }

    private void multipleChoiceTabulation(Map<String, Integer> map, SelectionResponse answer) {
        ArrayList<String> answers = answer.getAnswers();

        for (int i = 0; i < answers.size(); i++) {
            String thisAnswer = answers.get(i);

            if (map.containsKey(thisAnswer)) {
                map.put(thisAnswer, map.get(thisAnswer) + 1);
            } else {
                map.put(thisAnswer, 1);
            }
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
        out.say("Quitting ....");
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
    protected boolean notSaved() {
        return In.getInstance().getYesNo("Current survey has not been saved, do you wish to continue?\nCurrent survey will be lost.");
    }

    /**
     * Modify the loaded survey.
     * Prompts user for a question number, uses QuestionEditor class to edit the question.
     * <p>
     * If no survey is loaded, will output error message.
     * No special handling for surveys with no questions.
     * Marks the survey as not saved.
     */
    private void modifySurvey() {
        // setup
        saved = false;
        Out out = Out.getInstance();
        In in = In.getInstance();
        int questionNumber;
        Question question;

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
    protected void takeSurvey() {
        if (currentSurvey == null) {
            Out.getInstance().say("You must have a "
                    + currentSurvey.getSurveyType().name + " loaded in order to take it.");
        } else {
            currentSurvey.take();
            this.saved = false;
        }
    }

    /**
     * Lists saved surveys, selects survey, and loads it from path.
     */
    protected void loadSurvey(TypesOfSurvey type) {
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
        ArrayList<File> files = getListSavedSurveys(type);
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
            currentSurvey = loadFile(fileToBeLoaded);
            saved = true;
        }// else, selection was to leave menu
    }

    protected Survey loadFile(File fileToBeLoaded) {
        Survey results = new Survey();
        try (ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(fileToBeLoaded))) {
            Survey loadedSurvey = (Survey) objectIn.readObject();
            System.out.println("Survey object has been deserialized and loaded.");
            // Do something with the loadedSurvey object

            // Assign the loadedSurvey to the currentSurvey variable
            results = loadedSurvey;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return results;
    }

    protected void listSavedSurveys(ArrayList<File> files) {
        Out out = Out.getInstance();

        for (int i = 0; i < files.size(); i++) {
            out.say((i + 1) + ") " + files.get(i).getName());
        }
    }

    private ArrayList<File> getListSavedSurveys(TypesOfSurvey type) {
        File directory = new File(path);

        // check if the path is a directory
        if (!directory.isDirectory()) {
            Out.getInstance().say("Specified path is not a directory.");
            return new ArrayList<>();
        }

        // list files in the directory
        File[] filesArray = directory.listFiles((dir, name) ->
                (name.toLowerCase().endsWith(".ser") && name.startsWith(type.name))
        );

        // convert the array to an ArrayList
        ArrayList<File> fileList = null;
        fileList = new ArrayList<>(Arrays.asList(filesArray));

        if (fileList == null) { // error check
            fileList = new ArrayList<>();
        }

        return fileList;
    }

    /**
     * Save the currently loaded survey.
     */
    protected void saveSurvey(TypesOfSurvey type) {
        Out out = Out.getInstance();
        In in = In.getInstance();
        if (this.currentSurvey == null) { // verification
            out.say("You must have a " + type.name + " loaded in order to save it.");
        } else {
            out.say("What would you like to name this " + currentSurvey.getSurveyType().name + "?");
            String filename = type.name + "-" + in.readFilename() + ".ser";
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

            out.say("Added a " + newQuestion.getQuestionType().label + " question to the survey.");
            out.say(""); // whitespace
            another = in.getYesNo("Would you like to add another question?");
        }
    }
}