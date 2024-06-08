package Survey.Question;

import Management.QuestionType;
import Survey.Response.SelectionResponse;
import utils.Out;
import utils.SerializationIDs;

import java.util.ArrayList;

public class MultipleChoice extends Question {
    /**
     * ID for serialization
     */
    private final static long serialVersionUID = SerializationIDs.MULTIPLE_CHOICE.uid;
    protected ArrayList<String> choices;

    public MultipleChoice() {
        this.questionType = QuestionType.MULTIPLE_CHOICE;

        this.answer = new SelectionResponse();
        this.answer.setMultipleAnswersAllowed(super.multipleAnswersAllowed);
    }

    @Override
    public void display() {
        displayPrompt();
        displayChoices();
        displayResponse();
    }

    @Override
    public void ask() {
        displayPrompt();
        displayChoices();
        respondToPrompt();
    }

    /**
     * Swap out current question's choices with newChoices
     *
     * @param newChoices ArrayList<String> of new choices for question
     */
    public void newChoices(ArrayList<String> newChoices) {
        this.choices = newChoices;
        this.answer.updateChoices(newChoices);
    }

    /**
     * displays choices
     */
    public void displayChoices() {
        // setup
        String output = "Choices:";
        int count = 0;
        int total = this.choices.size();

        // add all choices to the output String
        while (count < total) {
            count++;
            output += "\n" + count + ") " + this.choices.get(count - 1);

            if (count < total) {
                count++;
                output += "\t\t" + count + ") " + this.choices.get(count - 1);
            }
        }

        // output the choices
        Out.getInstance().say(output);
    }

    @Override
    public ArrayList<String> getChoices() {
        return this.choices;
    }
}
