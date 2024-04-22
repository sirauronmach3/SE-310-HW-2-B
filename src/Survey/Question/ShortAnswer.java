package Survey.Question;

import Management.QuestionType;
import Survey.Response.ShortAnswerResponse;

public class ShortAnswer extends Essay {
    /**
     * ID for serialization
     */
    private final static long serialVersionUID = 6L;
    private static final int NUMBER_OF_ANSWERS = 1;

    public ShortAnswer() {
        this.questionType = QuestionType.SHORT_ANSWER;

        this.answer = new ShortAnswerResponse();
        answer.setMultipleAnswersAllowed(false);
        answer.setNumberOfAnswers(NUMBER_OF_ANSWERS);
    }
}
