package Survey.Question;

import Management.QuestionType;

import java.util.ArrayList;

public class TrueFalse extends MultipleChoice {

    /**
     * ID for serialization
     */
    private final static long serialVersionUID = 7L;

    /**
     * TrueFalse constructor
     */
    public TrueFalse() {
        this.questionType = QuestionType.TRUE_FALSE;

        this.choices = new ArrayList<>(2);
        this.choices.add("True");
        this.choices.add("False");

        this.answer.setMultipleAnswersAllowed(false);
        this.answer.setNumberOfAnswers(1);
        this.answer.updateChoices(this.choices);
    }

    @Override
    public void newChoices(ArrayList<String> newChoices) {
        throw new UnsupportedOperationException("Invalid operation");
    }

    @Override
    public void display() {
        displayPrompt();
        displayResponse();
    }
}
