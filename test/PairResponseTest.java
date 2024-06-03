import Survey.Response.DateResponse;
import Survey.Response.PairResponse;
import static org.junit.jupiter.api.Assertions.*;

import Survey.Response.Response;
import org.junit.jupiter.api.Test;


import java.util.HashMap;

public class PairResponseTest {
    private PairResponse pair1;

    @Test
    public void pairResponseIsEqualsTrueIfSame() {
        PairResponse pair2 = new PairResponse();
        pair1 = new PairResponse();

        HashMap<String, String> response1 = new HashMap<>();
        HashMap<String, String> response2 = new HashMap<>();
        String left = "left";
        String right = "right";
        response1.put(left, right);
        response2.put(left, right);
        pair1.setAnswer(response1);
        pair2.setAnswer(response2);

        assertTrue(pair1.isEqual(pair2));
        assertTrue(pair2.isEqual(pair1));
    }

    @Test
    public void pairResponseIsEqualsFalseIfDifferent() {
        PairResponse pair2 = new PairResponse();
        pair1 = new PairResponse();

        HashMap<String, String> response1 = new HashMap<>();
        HashMap<String, String> response2 = new HashMap<>();
        String left1 = "left";
        String left2 = "LEFT";
        String right1 = "right";
        String right2 = "RIGHT";
        response1.put(left1, right1);
        response1.put(left2, right2);
        response2.put(left1, right2);
        response2.put(left2, right1);

        pair1.setAnswer(response1);
        pair2.setAnswer(response2);

        assertFalse(pair1.isEqual(pair2));
    }

    @Test
    public void pairResponseIsEqualsFalseIfDifferentType() {
        Response response2 = new DateResponse();
        pair1 = new PairResponse();

        assertFalse(pair1.isEqual(response2));
    }
}
