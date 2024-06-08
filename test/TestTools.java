import java.util.ArrayList;

public class TestTools {

    public static ArrayList<String> toUpperCase(ArrayList<String> list) {
        ArrayList<String> result = new ArrayList<>();
        for (String s : list) {
            result.add(s.toUpperCase());
        }
        return result;
    }
    public static ArrayList<String> toLowerCase(ArrayList<String> list) {
        ArrayList<String> result = new ArrayList<>();
        for (String s : list) {
            result.add(s.toLowerCase());
        }
        return result;
    }
}
