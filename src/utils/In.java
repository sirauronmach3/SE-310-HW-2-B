package utils;

import static utils.InputHelper.validFilename;

/**
 * Please note this class is here as an example and the error checking is minimal.
 *
 * @author Sean Grimes, sean@seanpgrimes.com
 */
public abstract class In {
    // The current flag value for abstractedInputOutput.abstractedInputOutput.ComType (console, file, audio, video) that this class
    // should use
    /*
        NOTE: Default is CONSOLE -- communicate with user on command line by default
        This will ultimately use the abstractedInputOutput.abstractedInputOutput.ConsoleIn class by default. Similar to how abstractedInputOutput.abstractedInputOutput.Out has
        abstractedInputOutput.abstractedInputOutput.ConsoleOut, abstractedInputOutput.abstractedInputOutput.FileOut, abstractedInputOutput.abstractedInputOutput.AudioOut, to use other abstractedInputOutput.abstractedInputOutput.In types you would need to write new
        classes that extend abstractedInputOutput.abstractedInputOutput.In (e.g. AudioIn, abstractedInputOutput.FileIn)
     */
    protected static ComType comType = ComType.CONSOLE;
    // The instance that gets returned for all getInstance calls
    private static In input;

    // No public c'tor to fit the singleton requirements
    protected In() {
    }

    public static ComType getComType() {
        return comType;
    }

    public static void setComType(ComType ct) {
        comType = ct;
    }

    public static In getInstance() {
        if (In.comType == ComType.CONSOLE) {
            input = ConsoleIn.getInstance();
        }

        // If other input types are implemented, uncomment similar to the
        // abstractedInputOutput.abstractedInputOutput.Out class and remove the UnsupportedOperationException
/*  Currently Unsupported
        else if(In.comType == ComType.FILE) {
            input = FileIn.getInstance();
        }*/
/*  Currently Unsupported
        else if(In.comType == ComType.AUDIO) {
            throw new UnsupportedOperationException("Audio Input not implemented");
            input = AudioIn.getInstance();
        }*/
        return input;
    }

    // The following abstract functions must be implemented in child classes
    public abstract String readStr();

    public abstract int readInt();

    public abstract double readDouble();

    /**
     * Gets an integer from the user within the low and high bound. Returns the value;
     *
     * @param low  minimum valid value
     * @param high maximum valid value
     * @return
     */
    public int readIntWithinRange(int low, int high) {
        int current;
        while (true) {
            current = readInt();
            if (current < low || current > high) {
                Out.getInstance().say("Please enter a number within the acceptable range of options: " + low + " - " + high);
            } else {
                return current;
            }
        }
    }

    /**
     * prompts user for a yes/no question. Will only accept yes, no, y, or n (ignoring case)
     *
     * @param prompt String representing prompt for user
     * @return boolean, true for yes, false for no
     */
    public boolean getYesNo(String prompt) {
        if (prompt == null) {
            return false;
        }

        Out out = Out.getInstance();
        In in = In.getInstance();

        out.say(prompt);
        while (true) {
            out.say("(Y/N): ");
            String selection = in.readStr().toLowerCase();

            if (selection.equals("y") || selection.equals("n") || selection.equals("yes") || selection.equals("no")) {
                return selection.charAt(0) == 'y';
            } else {
                out.say("Invalid choice");
            }
        }
    }

    /**
     * takes input from user until a valid filename is given.
     *
     * @return
     */
    public String readFilename() {
        Out out = Out.getInstance();
        In in = In.getInstance();
        boolean valid = false;
        String input = null;

        while (!valid) {
            out.say("Filenames may not include .or \\ or / and may not start or end with _ or -");
            input = in.readStr();
            valid = validFilename(input);
        }
        return input;
    }


}
