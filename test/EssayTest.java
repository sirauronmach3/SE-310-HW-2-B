import Survey.Question.Essay;
import Survey.Question.MultipleChoice;
import Survey.Question.Question;
import Survey.Question.ShortAnswer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EssayTest {
    /**
     * Essays do not have any additional functionality for isEqual, so this functions as a tester for isEqual() for:
     * Question, ValidDate, and ShortAnswer.
     * Because all isEqual calls Question.isEqual() this tests that element for all.
     */
    private Question question1;
    private Question question2;

    @BeforeEach
    public void setup() {
        question1 = new Essay();
    }

    @Test
    public void emptyEssaysAreEqual() {
        question2 = new Essay();

        assertTrue(question1.isEqual(question2));
    }

    @Test
    public void equivalentPromptsAreEqual() {
        question2 = new Essay();
        String prompt = "This is the prompt";

        question1.newPrompt(prompt.toLowerCase());
        question2.newPrompt(prompt.toUpperCase());

        assertTrue(question1.isEqual(question2));
    }

    @Test
    public void differentPromptsAreNotEqual() {
        question2 = new Essay();
        String prompt1 = "This is the prompt";
        String prompt2 = "This is not the prompt";

        question1.newPrompt(prompt1);
        question2.newPrompt(prompt2);

        assertFalse(question1.isEqual(question2));
    }

    @Test
    public void differentQuestionTypesAreNotEqual() {
        question2 = new ShortAnswer();

        assertFalse(question1.isEqual(question2));
    }

    @Test
    public void anotherDifferentQuestionTypesAreNotEqual() {
        question2 = new MultipleChoice();

        assertFalse(question1.isEqual(question2));
    }
}
