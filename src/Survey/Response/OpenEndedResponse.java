package Survey.Response;

import utils.In;
import utils.Out;
import utils.SerializationIDs;

import java.util.ArrayList;

public class OpenEndedResponse extends Response {
    /**
     * ID for serialization
     */
    private final static long serialVersionUID = SerializationIDs.OPEN_ENDED_RESPONSE.uid;

    protected ArrayList<String> answer = new ArrayList<>();

    @Override
    public void respond() {
        // setup
        Out out = Out.getInstance();
        In in = In.getInstance();
        String input;
        String comparison = "end";

        // get answer
        out.say("To finish answering, leave a line with only an '" + comparison + "'.");
        input = in.readStr();
        while (!comparison.equalsIgnoreCase(input)) {
            answer.add(input);
            input = in.readStr();
        }
    }

    @Override
    public void display() {
        // setup
        Out out = Out.getInstance();

        // output
        if (answer == null || answer.size() == 0) { // if there is no answer
            out.say(unanswered);
            return;
        }

        out.say("Answer:");
        answer.forEach(
                (paragraph) -> out.say("\t" + paragraph)
        );
    }

    @Override
    public boolean isEqual(Response other) {
        return other.getClass().equals(this.getClass());
    }
}
