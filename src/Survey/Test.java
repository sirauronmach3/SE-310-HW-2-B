package Survey;

import Survey.Response.Response;
import utils.SerializationIDs;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Test extends Survey{
    /**
     * ID for serialization
     */
    private final static long serialVersionUID = SerializationIDs.TEST.uid;
    private final ArrayList<Response> correctAnswers = new ArrayList<>();
}
