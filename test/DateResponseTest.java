import Survey.Response.DateResponse;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DateResponseTest {
    DateResponse date1;
    DateResponse date2;

    @Test
    public void isEqualsResponseTrueIfDatesSame() {
        date1 = new DateResponse();
        date2 = new DateResponse();

        String date = "01/02/89";
        String otherDate = "1/2/89";

        date1.setAnswer(date);
        date2.setAnswer(otherDate);

        assertTrue(date1.isEqual(date2));
    }

    @Test
    public void isEqualResponseFalseIfDatesDifferent() {
        date1 = new DateResponse();
        date2 = new DateResponse();

        String date = "1/2/34";
        String otherDate= "4/3/21";

        date1.setAnswer(date);
        date2.setAnswer(otherDate);

        assertFalse(date1.isEqual(date2));
    }
}
