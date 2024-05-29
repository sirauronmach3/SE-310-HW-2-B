import Survey.Response.OpenEndedResponse;
import Survey.Response.Response;
import Survey.Response.ShortAnswerResponse;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShortAnswerResponseTest {
    ShortAnswerResponse response1;


    @Test
    public void ShortAnswerResponseIsEqualsFalseIfNotShortAnswer() {
        response1 = new ShortAnswerResponse();
        Response response2 = new OpenEndedResponse();

        assertFalse(response1.isEqual(response2));
    }

    @Test
    public void ShortAnswerResponseIsEqualFalseIfNotSameAnswer() {
        response1 = new ShortAnswerResponse();
        ShortAnswerResponse response2 = new ShortAnswerResponse();

        ArrayList<String> answer1 = new ArrayList<>();
        ArrayList<String> answer2 = new ArrayList<>();
        String one = "One";
        String two = "two";
        answer1.add(one);
        answer2.add(two);

        response1.setAnswer(answer1);
        response2.setAnswer(answer2);

        assertFalse(response1.isEqual(response2));
    }

    @Test
    public void ShortAnswerResponseIsEqualFalseIfNotSameAnswerNumber() {
        response1 = new ShortAnswerResponse();
        ShortAnswerResponse response2 = new ShortAnswerResponse();

        ArrayList<String> answer1 = new ArrayList<>();
        ArrayList<String> answer2 = new ArrayList<>();
        String one = "One";
        String two = "two";
        answer1.add(one);
        answer1.add(two);
        answer2.add(two);

        response1.setAnswer(answer1);
        response2.setAnswer(answer2);

        assertFalse(response1.isEqual(response2));
    }

    @Test
    public void ShortAnswerResponseIsEqualTrueIfSameAnswer() {
        response1 = new ShortAnswerResponse();
        ShortAnswerResponse response2 = new ShortAnswerResponse();

        ArrayList<String> answer1 = new ArrayList<>();
        ArrayList<String> answer2 = new ArrayList<>();
        String one = "ONE";
        String two = "one";
        answer1.add(one);
        answer2.add(two);

        response1.setAnswer(answer1);
        response2.setAnswer(answer2);

        assertTrue(response1.isEqual(response2));
    }

    @Test
    public void ShortAnswerResponseIsEqualTrueIfSameOfMultipleAnswers() {
        response1 = new ShortAnswerResponse();
        ShortAnswerResponse response2 = new ShortAnswerResponse();

        ArrayList<String> answer1 = new ArrayList<>();
        ArrayList<String> answer2 = new ArrayList<>();
        String one1 = "ONE";
        String one2 = "one";
        String two1 = "Two";
        String two2 = "TWO";
        answer1.add(one1);
        answer1.add(two1);
        answer2.add(two2);
        answer2.add(one2);

        response1.setAnswer(answer1);
        response2.setAnswer(answer2);

        assertTrue(response1.isEqual(response2));
    }
}
