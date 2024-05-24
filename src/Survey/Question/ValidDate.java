package Survey.Question;

import Management.QuestionType;
import Survey.Response.DateResponse;
import utils.SerializationIDs;

import java.util.ArrayList;

public class ValidDate extends Question {

    /**
     * ID for serialization
     */
    private final static long serialVersionUID = SerializationIDs.VALID_DATE.uid;

    public ValidDate() {
        this.questionType = QuestionType.VALID_DATE;

        this.answer = new DateResponse();
    }

    @Override
    public void ask() {
        displayPrompt();
        getResponse();
    }

    @Override
    public void displayChoices() {
        throw new UnsupportedOperationException("Invalid operation");
    }

    @Override
    public ArrayList<String> getChoices() {
        throw new UnsupportedOperationException("Invalid operation");
    }
}
