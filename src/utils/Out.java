package utils;

/**
 * Please note this class is here as an example and the error checking is minimal.
 *
 * @author Sean Grimes, sean@seanpgrimes.com
 */
public abstract class Out {
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
    private static Out output;

    // No public c'tor to fit the singleton requirements
    protected Out() {
    }

    public static ComType getComType() {
        return comType;
    }

    public static void setComType(ComType ct) {
        comType = ct;
    }

    public static Out getInstance() {
        if (Out.comType == ComType.CONSOLE) {
            output = ConsoleOut.getInstance();
        }
/*  Currently unsupported
        else if(Out.comType == ComType.FILE) {
            output = FileOut.getInstance();
        }*/
 /* Currently unsupported
        else if(Out.comType == ComType.AUDIO) {
            output = AudioOut.getInstance();
        }*/
        return output;
    }

    // Function implemented in the child classes
    public abstract void say(Object msg);
}

