import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.DaysInMonth;
import utils.InputHelper;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InputHelperTest {
    private String day, month, year;

    @BeforeEach
    void setUp() {
        // Provide entries with all valid information, will be replaced in some instances;
        day = ((Integer) 1).toString();
        month = ((Integer) 1).toString();
        year = ((Integer) 1).toString();
    }

    @Test
    public void validDateMin() {
        day = ((Integer) 1).toString();
        year = "0000000";

        for (int month = 1; month <= 12; month++) {
            assertTrue(InputHelper.validateDate(month + "/" + day + "/" + year));
        }
    }

    @Test void validDateLeapYear() {
        day = "29";
        month = ((Integer) 2).toString();
        year = ((Integer) 2000).toString();

        assertTrue(InputHelper.validateDate(month + "/" + day + "/" + year));
    }

    @Test
    public void validDateMax() {
        year = ((Integer) 1).toString();

        for (int month = 1; month <= 12; month++) {
            day = ((Integer) DaysInMonth.DAYS_IN_MONTH[month - 1]).toString();
            assertTrue(InputHelper.validateDate(month + "/" + day + "/" + year));
        }
    }

    @Test
    public void invalidDateDayAtMin() {
        day = ((Integer) 0).toString();

        for (int month = 1; month <= 12; month++) {
            assertFalse(InputHelper.validateDate(month + "/" + day + "/" + year));
        }
    }

    @Test
    public void invalidDateDayBelowMin() {
        day = ((Integer) (-1)).toString();

        for (int month = 1; month <= 12; month++) {
            assertFalse(InputHelper.validateDate(month + "/" + day + "/" + year));
        }
    }

    @Test
    public void invalidDateDayAboveMax() {
        year = ((Integer) 1).toString();

        for (int month = 1; month <= 12; month++) {
            day = ((Integer) (DaysInMonth.DAYS_IN_MONTH[month - 1] + 1)).toString();
            assertFalse(InputHelper.validateDate(month + "/" + day + "/" + year));
        }
    }

    @Test
    public void invalidDateMonthAtMin() {
        month = ((Integer) 0).toString();

        assertFalse(InputHelper.validateDate(month + "/" + day + "/" + year));
    }

    @Test
    public void invalidDateMonthBelowMin() {
        month = ((Integer) (-1)).toString();

        assertFalse(InputHelper.validateDate(month + "/" + day + "/" + year));
    }

    @Test
    public void invalidDateMonthAboveMax() {
        month = ((Integer) 13).toString();

        assertFalse(InputHelper.validateDate(month + "/" + day + "/" + year));
    }

    @Test
    public void invalidDateYearBelowMin() {
        year = ((Integer) (-1)).toString();

        assertFalse(InputHelper.validateDate(month + "/" + day + "/" + year));
    }

    @Test
    public void invalidDateYearAboveMax() {
        year = ((Integer) 10000).toString();

        assertFalse(InputHelper.validateDate(month + "/" + day + "/" + year));
    }

    @Test
    public void invalidDateNotIntInDay() {
        day = "one";

        assertFalse(InputHelper.validateDate(month + "/" + day + "/" + year));
    }

    @Test
    public void invalidDateNotIntInMonth() {
        month = "january";

        assertFalse(InputHelper.validateDate(month + "/" + day + "/" + year));
    }

    @Test
    public void invalidDateNotIntInYear() {
        year = "2000AD";

        assertFalse(InputHelper.validateDate(month + "/" + day + "/" + year));
    }

    @Test
    public void invalidDateFloatInDay() {
        day = ((Float) 1.0f).toString();

        assertFalse(InputHelper.validateDate(month + "/" + day + "/" + year));
    }

    @Test
    public void invalidDateFloatInMonth() {
        month = ((Float) 1.0f).toString();

        assertFalse(InputHelper.validateDate(month + "/" + day + "/" + year));
    }

    @Test
    public void invalidDateFloatInYear() {
        year = ((Float) 1.0f).toString();

        assertFalse(InputHelper.validateDate(month + "/" + day + "/" + year));
    }
}
