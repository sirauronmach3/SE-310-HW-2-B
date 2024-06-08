import Survey.Response.OpenEndedResponse;
import Survey.Response.PairResponse;
import Survey.Response.Response;
import Survey.Response.ShortAnswerResponse;
import Survey.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTester {
    private Test test1;
    private Test test2;
    private Response answer1;
    private Response answer2;

    @BeforeEach
    public void setUp() {
        test1 = new Test();
    }

    @org.junit.jupiter.api.Test
    public void emptyTestsIsEqual() {
        test2 = new Test();

        assertTrue(test1.isEqual(test2));
    }

    @org.junit.jupiter.api.Test
    public void testWithSameCorrectAnswerIsEqual() {
        test2 = new Test();
        answer1 = new OpenEndedResponse();

        test1.addCorrectAnswer(answer1);
        test2.addCorrectAnswer(answer1);

        assertTrue(test1.isEqual(test2));
    }

    @org.junit.jupiter.api.Test
    public void testWithDifferentNumberCorrectAnswerIsNotEqual() {
        test2 = new Test();
        answer1 = new OpenEndedResponse();
        answer2 = new PairResponse();

        test1.addCorrectAnswer(answer1);
        test1.addCorrectAnswer(answer2);

        test2.addCorrectAnswer(answer2);

        assertFalse(test1.isEqual(test2));
    }

    @org.junit.jupiter.api.Test
    public void testWithDifferentCorrectAnswerIsNotEqual() {
        test2 = new Test();
        answer1 = new OpenEndedResponse();
        answer2 = new PairResponse();

        test1.addCorrectAnswer(answer1);
        test2.addCorrectAnswer(answer2);

        assertFalse(test1.isEqual(test2));
    }

    @org.junit.jupiter.api.Test
    public void testWithDifferentCorrectAnswerOfMultipleIsNotEqual() {
        test2 = new Test();
        answer1 = new OpenEndedResponse();
        answer2 = new PairResponse();
        Response answer3 = new ShortAnswerResponse();

        test1.addCorrectAnswer(answer1);
        test1.addCorrectAnswer(answer2);

        test2.addCorrectAnswer(answer1);
        test2.addCorrectAnswer(answer3);

        assertFalse(test1.isEqual(test2));
    }
}
