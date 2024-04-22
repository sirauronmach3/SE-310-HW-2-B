package Survey.Question;

import Management.QuestionType;
import Survey.Response.OpenEndedResponse;

import java.util.ArrayList;

public class Essay extends Question {
    /**
     * ID for serialization
     */
    private final static long serialVersionUID = 3L;

    /**
     * Constructor for Essay
     */
    public Essay() {
        this.questionType = QuestionType.ESSAY;
        this.answer = new OpenEndedResponse();
    }

    @Override
    public void ask() {
        displayPrompt();
        answer.respond();
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
