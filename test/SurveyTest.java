import Survey.Question.Essay;
import Survey.Question.Matching;
import Survey.Question.Question;
import Survey.Survey;
import Survey.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Drexel SE-310-001 sp 23-24, hw 2 part b
 * Main class
 * Author: Matthew Martin mjm836
 * Date: 4/17/2024
 */
public class SurveyTest {
    /**
     * Survey tests several elements of a Survey, and is sufficient to test these elements for Test as well.
     */

    private Survey survey1;
    private Survey survey2;
    private Question question1;
    private Question question2;

    @BeforeEach
    public void setUp() {
        survey1 = new Survey();
    }

    @org.junit.jupiter.api.Test
    public void nullSurveyIsNotEqual() {
        survey2 = null;

        assertFalse(survey1.isEqual(survey2));
    }

    @org.junit.jupiter.api.Test
    public void twoEmptySurveysIsEqual() {
        survey2 = new Survey();

        assertTrue(survey1.isEqual(survey2));
    }

    @org.junit.jupiter.api.Test
    public void surveyIsNotEqualWithTest() {
        survey2 = new Test();

        assertFalse(survey1.isEqual(survey2));
    }

    @org.junit.jupiter.api.Test
    public void surveyWithOneQuestionAndSameFilenameIsEqual() {
        survey2 = new Survey();
        question1 = new Essay();
        String filename = "one";

        survey1.addQuestion(question1);
        survey1.setFilename(filename);

        survey2.addQuestion(question1);
        survey2.setFilename(filename);

        assertTrue(survey1.isEqual(survey2));
    }

    @org.junit.jupiter.api.Test
    public void surveyWithDifferentFilenameIsNotEqual() {
        survey2 = new Survey();
        question1 = new Essay();
        String filename1 = "one";
        String filename2 = "two";

        survey1.addQuestion(question1);
        survey1.setFilename(filename1);

        survey2.addQuestion(question1);
        survey2.setFilename(filename2); // different filename

        assertFalse(survey1.isEqual(survey2));
    }

    @org.junit.jupiter.api.Test
    public void surveyWithDifferentNumberOfQuestionsIsNotEqual() {
        survey2 = new Survey();
        question1 = new Essay();
        question2 = new Essay();

        survey1.addQuestion(question1);
        survey1.addQuestion(question2);

        survey2.addQuestion(question1);

        assertFalse(survey1.isEqual(survey2));
    }

    @org.junit.jupiter.api.Test
    public void surveyWithDifferentQuestionsIsNotEqual() {
        survey2 = new Survey();
        question1 = new Essay();
        question2 = new Matching(); // different Question type is sufficiently different to fail the Question.isEqual comparison

        survey1.addQuestion(question1);
        survey2.addQuestion(question2);

        assertFalse(survey1.isEqual(survey2));
    }
}
