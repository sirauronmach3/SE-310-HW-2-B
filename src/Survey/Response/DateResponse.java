package Survey.Response;

import utils.DaysInMonth;
import utils.In;
import utils.Out;

public class DateResponse extends Response {
    /**
     * ID for serialization
     */
    private final static long serialVersionUID = 11L;
    /**
     * The answer
     */
    private String answer;

    @Override
    public void respond() {
        // Setup
        In in = In.getInstance();
        Out out = Out.getInstance();
        boolean valid = false;
        String input = null;

        // loop until a valid date is given
        while (!valid) {
            out.say("A date should be entered in the following format: mm/dd/yyyy");
            input = in.readStr();
            valid = validateDate(input);
        }

        answer = input;
    }

    private boolean validateDate(String input) {
        boolean valid = true;
        try {
            String[] date = input.split("/");
            int[] n = {0, 0, 0};
            for (int i = 0; i < 3; i++) {
                n[i] = Integer.parseInt(date[i]);
            }
            valid &= intWithinRange(n[0], 1, 12);
            valid &= intWithinRange(n[2], 0, 2023);
            if ((n[0] == 2) && (n[2] % 4 == 0)) {
                valid &= intWithinRange(n[1], 1, 29);
            } else {
                valid &= intWithinRange(n[1], 1, DaysInMonth.DAYS_IN_MONTH[n[0]]);
            }
        } catch (Exception e) {
            return false;
        } finally {
            return valid;
        }
    }

    private boolean intWithinRange(int value, int lowerBound, int upperBound) {
        return (value <= upperBound) && (value >= lowerBound);
    }

    @Override
    public void display() {
        Out out = Out.getInstance();
        if (answer == null) {
            out.say(unanswered);
        } else {
            out.say(answer);
        }
    }

}
