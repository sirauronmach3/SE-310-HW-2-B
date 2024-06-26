package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputHelper {
    // If file is null, read from System.in
    public static String readStr(String file) {
        BufferedReader in = null;
        String line = "-1";
        try {
            if (file == null)
                in = new BufferedReader(new InputStreamReader(System.in));
            else
                in = new BufferedReader(new FileReader(file));
            line = in.readLine();
            // Verify the input exists
            while (line == null || line.length() == 0) {
                Out.getInstance().say("Please enter valid input of at least 1 char");
                line = in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    // If file is null, read from System.in
    public static int readInt(String file) {
        while (true) {
            BufferedReader in = null;
            String line = "-1";
            Out out = Out.getInstance();
            try {
                if (file == null)
                    in = new BufferedReader(new InputStreamReader(System.in));
                else
                    in = new BufferedReader(new FileReader(file));
                line = in.readLine();
                while (line == null || line.length() == 0 || !isInt(line)) {
                    out.say("Please enter a valid int");
                    line = in.readLine();
                }
            } catch (Exception e) {
                out.say("Please enter a valid int");
            }
            return Integer.parseInt(line);
        }
    }

    // If file is null, read from System.in
    public static double readDouble(String file) {
        BufferedReader in = null;
        String line = "-1";
        try {
            if (file == null)
                in = new BufferedReader(new InputStreamReader(System.in));
            else
                in = new BufferedReader(new FileReader(file));
            line = in.readLine();
            while (line == null || line.length() == 0 || !isDouble(line)) {
                Out.getInstance().say("Please enter a valid double");
                line = in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Double.parseDouble(line);
    }

    // Returns true if the input can be parsed to an int, else false
    private static boolean isInt(String num) {
        try {
            Integer.parseInt(num);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Returns true if the input can be parsed to a double, else false
    private static boolean isDouble(String num) {
        try {
            Double.parseDouble(num);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if input is a valid filename.
     * <p>Filenames may not include . or \ or / and may not start or end with - or _</p>
     *
     * @param input potential filename
     * @return boolean, true if input is a valid filename, false if not
     */
    public static boolean validFilename(String input) {
        if (input.contains(".") || input.contains("/") || input.contains("\\")) {
            return false;
        } else
            return !input.endsWith("_") && !input.substring(0, 1).contains("_")
                    && !input.endsWith("-") && !input.substring(0, 1).contains("-");
    }

    public static boolean validateDate(String input) {
        boolean valid = true;
        try {
            // translate string into integers
            String[] date = input.split("/");
            int[] dateArray = {0, 0, 0};
            for (int i = 0; i < 3; i++) {
                dateArray[i] = Integer.parseInt(date[i]);
            }

            // Validate month
            valid &= intWithinRange(dateArray[0], 1, 12);
            if (!valid) {
                Out.getInstance().say("Invalid month");
            }

            // Validate Year
            valid &= intWithinRange(dateArray[2], 0, 9999);
            if (!valid) {
                Out.getInstance().say("Invalid year");
            }

            // Validate day
            int dayMax = ((dateArray[0] == 2) && (dateArray[2] % 4 == 0)) ?
                    DaysInMonth.FEBRUARY_LEAP_YEAR : DaysInMonth.DAYS_IN_MONTH[dateArray[0] - 1];
            valid &= intWithinRange(dateArray[1], 1, dayMax);
            if (!valid) {
                Out.getInstance().say("Invalid day number");
            }

            return valid;
        } catch (NumberFormatException e) {
            Out.getInstance().say("Your response must use integers to represent the date.");
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean intWithinRange(int value, int lowerBound, int upperBound) {
        return (value <= upperBound) && (value >= lowerBound);
    }
}
