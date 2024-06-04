package Survey;

import Management.QuestionType;
import Survey.Question.*;
import utils.In;
import utils.Out;

import java.util.ArrayList;

/**
 * Collection of static methods which can create and edit Questions.
 */
public class QuestionEditor {
    /**
     * Method that controls the routine for creating a new Question
     *
     * @return new Question. Returns null on failure.
     */
    public static Question createQuestion() {
        // setup
        Question question = null;

        // prompt user for question type
        QuestionType type = displayQuestionOptions();

        // build question
        question = questionFromType(type);

        return question;
    }

    /**
     * Takes a QuestionType enum and returns a Question of the associated type. Uses the associated creation method.
     *
     * @param type QuestionType enum for type of Question to be created
     * @return Question of associated QuestionType.
     */
    private static Question questionFromType(QuestionType type) {
        if (type == null) return null;

        Question question = null;

        switch (type) {
            case MULTIPLE_CHOICE:
                question = createMultipleChoice();
                break;
            case TRUE_FALSE:
                question = createTrueFalse();
                break;
            case ESSAY:
                question = createEssay();
                break;
            case SHORT_ANSWER:
                question = createShortAnswer();
                break;
            case MATCHING:
                question = createMatching();
                break;
            case VALID_DATE:
                question = createValidDate();
                break;
            default:
                question = null;
        }

        return question;
    }

    /**
     * Creates a ShortAnswer Question
     *
     * @return new ShortAnswer Question
     */
    private static Question createShortAnswer() {
        // setup
        ShortAnswer question = new ShortAnswer();

        // prompt
        String prompt = getPrompt(QuestionType.SHORT_ANSWER);
        question.newPrompt(prompt);

        return question;
    }

    /**
     * Creates an Essay question.
     *
     * @return new Essay question.
     */
    private static Question createEssay() {
        // setup
        Essay question = new Essay();
        Out out = Out.getInstance();
        In in = In.getInstance();

        // prompt
        String prompt = getPrompt(QuestionType.ESSAY);
        question.newPrompt(prompt);

        return question;
    }

    /**
     * Create a Matching question.
     *
     * @return new Matching question.
     */
    private static Question createMatching() {
        // setup
        Matching question = new Matching();
        Out out = Out.getInstance();
        In in = In.getInstance();

        // prompt
        String prompt = getPrompt(QuestionType.MATCHING);
        question.newPrompt(prompt);

        // get pair choices
        boolean another = true;
        String left, right;
        ArrayList<String> leftColumn = new ArrayList<>();
        ArrayList<String> rightColumn = new ArrayList<>();
        while (another) {
            // left
            out.say("Enter information for the left column.");
            left = in.readStr();
            leftColumn.add(left);

            // right
            out.say("Enter information for the right column.");
            right = in.readStr();
            rightColumn.add(right);

            // another pair?
            another = in.getYesNo("Would you like to add another pair.");
        }
        // update the question
        question.updateChoices(leftColumn, rightColumn);

        return question;
    }

    /**
     * Creates a ValidDate question.
     *
     * @return a new ValidDate question.
     */
    private static Question createValidDate() {
        // setup
        ValidDate question = new ValidDate();

        // prompt
        String prompt = getPrompt(QuestionType.VALID_DATE);
        question.newPrompt(prompt);

        return question;
    }

    /**
     * Creates a TrueFalse question.
     *
     * @return a new TrueFalse question.
     */
    private static Question createTrueFalse() {
        // setup
        TrueFalse question = new TrueFalse();

        // prompt
        String prompt = getPrompt(QuestionType.TRUE_FALSE);
        question.newPrompt(prompt);

        return question;
    }

    /**
     * Creates a MultipleChoice question.
     *
     * @return a new MultipleChoice question.
     */
    private static MultipleChoice createMultipleChoice() {
        // setup
        MultipleChoice question = new MultipleChoice();
        Out out = Out.getInstance();
        In in = In.getInstance();

        // prompt
        String prompt = getPrompt(QuestionType.MULTIPLE_CHOICE);
        question.newPrompt(prompt);

        // number of choices
        out.say("\nEnter the number of choices for your multiple-choice question.");
        int questions = in.readIntWithinRange(1, Integer.MAX_VALUE);
        ArrayList<String> choices = new ArrayList<>();

        // choices
        for (int i = 0; i < questions; i++) {
            out.say("Enter choice #" + (i + 1) + ":");
            String choice = in.readStr();
            choices.add(choice);
        }
        question.newChoices(choices);

        // number of responses
        out.say("Enter the number of responses allowed for the question");
        questions = in.readIntWithinRange(1, Integer.MAX_VALUE);
        question.setNumberOfAnswers(questions);

        return question;
    }

    /**
     * Prompts the user for and gets a question prompt for the associated question type.
     *
     * @param questionType QuestionType for the type of question to get a prompt for.
     * @return String representing a question prompt.
     */
    private static String getPrompt(QuestionType questionType) {
        Out.getInstance().say("\nEnter the prompt for your " + questionType.label + " question:");
        return In.getInstance().readStr();
    }

    /**
     * Prompts the user to select the kind of question to create.
     * Verifies input, and returns a QuestionType Enum associated with that Question type.
     * Returns null if Previous Menu is selected.
     *
     * @return QuestionType Enum associated with the Question the user provides.
     * null, if previous menu is selected.
     */
    private static QuestionType displayQuestionOptions() {
        // setup
        QuestionType type = null;
        int selection = 0;
        Out out = Out.getInstance();
        In in = In.getInstance();

        // type of question
        out.say("\nWhat type of question would you like to add?:");
        for (QuestionType question : QuestionType.values()) {
            out.say((question.ordinal() + 1) + ") Add a new " + question.label + " question");
        }
        out.say((QuestionType.values().length + 1) + ") Previous Menu");

        // get input from user
        out.say("Pick a menu option by number: ");
        selection = in.readIntWithinRange(1, (QuestionType.values().length + 1)) - 1;
        try {
            type = QuestionType.values()[selection];
        } catch (Exception e) { // if select previous menu, or some other issue
            type = null;
        }

        return type;
    }

    /**
     * Edit the given question's prompt and/or choices.
     * <p>Choice editing is only available for MULTIPLE_CHOICE questions.</p>
     *
     * @param question Question to be edited.
     */
    public static void editQuestion(Question question) {
        // input verification
        if (question == null) {
            Out.getInstance().say("Question is null, no editing occurred.");
            return;
        }

        // setup
        QuestionType type = question.getQuestionType();
        In in = In.getInstance();
        Out out = Out.getInstance();

        // modify prompt
        question.displayPrompt();
        if (in.getYesNo("Do you wish to modify the prompt?")) {
            out.say("Enter a new prompt.");
            String prompt = in.readStr();

            question.newPrompt(prompt);
        }

        // modify choices
        if (type == QuestionType.MULTIPLE_CHOICE) {
            question.displayChoices();
            if (in.getYesNo("Do you wish to modify choices?")) {
                ArrayList<String> choices = question.getChoices();

                out.say("Which choice do you want to modify?");
                int choice = in.readIntWithinRange(1, choices.size());

                out.say("Enter the new choice for choice #" + choice + ".");
                String newChoice = in.readStr();
                choices.set((choice - 1), newChoice);
            }
        }
    }
}
