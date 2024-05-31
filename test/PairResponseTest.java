import Survey.Response.PairResponse;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


import java.util.HashMap;

public class PairResponseTest {
    private PairResponse pair1;

    @Test
    public void PairResponseIsEqualsTrueIfSame() {
        PairResponse pair2 = new PairResponse();
        pair1 = new PairResponse();

        HashMap<String, String> response1 = new HashMap<>();
        HashMap<String, String> response2 = new HashMap<>();
        String left = "left";
        String right = "right";
        response1.put(left, right);
        response2.put(left, right);

        assertTrue(pair1.isEqual(pair2));
    }
}
