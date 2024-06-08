import Survey.Question.MultipleChoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MultipleChoiceTest {
    /**
     * TrueFalse Question inherits all the checks from MultipleChoice, so I'm not testing that right now.
     */

    MultipleChoice question1 = new MultipleChoice();
    String one = "one";
    String two = "two";
    String three = "three";

    @BeforeEach
    public void setUp() {
        question1 = new MultipleChoice();
    }

    @Test
    public void emptyMultipleChoiceIsEqual() {
        MultipleChoice question2 = new MultipleChoice();

        assertTrue(question1.isEqual(question2));
    }

    @Test
    public void equivalentMultipleChoiceWithOneChoiceIsEqual() {
        MultipleChoice question2 = new MultipleChoice();

        ArrayList<String> choices = new ArrayList<>();
        choices.add(one);

        question1.newChoices(TestTools.toLowerCase(choices));
        question2.newChoices(TestTools.toUpperCase(choices));

        assertTrue(question1.isEqual(question2));
    }

    @Test
    public void equivalentMultipleChoiceWithMultipleChoicesIsEqual() {
        MultipleChoice question2 = new MultipleChoice();

        ArrayList<String> choices = new ArrayList<>();
        choices.add(one);
        choices.add(two);

        question1.newChoices(TestTools.toLowerCase(choices));
        question2.newChoices(TestTools.toUpperCase(choices));

        assertTrue(question1.isEqual(question2));
    }

    @Test
    public void differentMultipleChoiceWithOneChoiceIsNotEqual() {
        MultipleChoice question2 = new MultipleChoice();

        ArrayList<String> choices1 = new ArrayList<>();
        ArrayList<String> choices2 = new ArrayList<>();

        choices1.add(one);
        choices2.add(two);

        question1.newChoices(choices1);
        question2.newChoices(choices2);

        assertFalse(question1.isEqual(question2));
    }

    @Test
    public void differentMultipleChoiceWithMultipleChoicesIsNotEqual() {
        MultipleChoice question2 = new MultipleChoice();

        ArrayList<String> choices1 = new ArrayList<>();
        ArrayList<String> choices2 = new ArrayList<>();

        choices1.add(one);
        choices1.add(two);

        choices2.add(new String(one));
        choices2.add(three);

        question1.newChoices(choices1);
        question2.newChoices(choices2);

        assertFalse(question1.isEqual(question2));
    }

    @Test
    public void differentMultipleChoiceWithDifferentNumberChoicesIsNotEqual() {
        MultipleChoice question2 = new MultipleChoice();
        ArrayList<String> choices1 = new ArrayList<>();
        ArrayList<String> choices2 = new ArrayList<>();

        choices1.add(one);
        choices1.add(two);
        choices2.add(new String(one));

        question1.newChoices(choices1);
        question2.newChoices(choices2);

        assertFalse(question1.isEqual(question2));
    }

    @Test
    public void differentNumberOfAllowableAnswersIsNotEqual() {
        MultipleChoice question2 = new MultipleChoice();

        ArrayList<String> choices1 = new ArrayList<>();
        int numb = 1;
        choices1.add(one);

        question1.newChoices(choices1);
        question2.newChoices(choices1);

        //otherwise identical except for this
        question1.setNumberOfAnswers(numb);
        question2.setNumberOfAnswers(numb + 1);

        assertFalse(question1.isEqual(question2));
    }
}
