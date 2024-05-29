import Survey.Response.DateResponse;
import Survey.Response.OpenEndedResponse;
import Survey.Response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OpenEndedResponseTest {

    Response response1;
    Response response2;

    @Test
    public void OpenEndedComparisonRespondsTrueAsLongAsAnotherOpenEnded() {
        response1 = new OpenEndedResponse();
        response2 = new OpenEndedResponse();

        assertTrue(response2.isEqual(response1));
        assertTrue(response1.isEqual(response2));
    }

    @Test
    public void OpenEndedComparisonResponseFalseAsLongAsNotOpenEnded() {
        response1 = new OpenEndedResponse();
        response2 = new DateResponse();

        assertFalse(response1.isEqual(response2));
    }
}
