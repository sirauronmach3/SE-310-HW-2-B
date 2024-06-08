import Survey.Question.Matching;
import Survey.Question.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MatchingTest {
    private Matching question1;
    private Question question2;
    String numberOne = "number one";
    String numberTwo = "number two";
    String numberThree = "number three";
    String numberFour = "number four";
    String numberFive = "number five";
    String numberSix = "number six";
    String numberSeven = "number seven";
    String numberEight = "number eight";

    @BeforeEach
    public void setUp() {
        question1 = new Matching();
    }

    @Test
    public void emptyMatchingIsEqual() {
        question2 = new Matching();

        assertTrue(question1.isEqual(question2));
    }

    @Test
    public void equivalentMatchingIsEqual() {
        question2 = new Matching();

        ArrayList<String> left = new ArrayList<>();
        ArrayList<String> right = new ArrayList<>();
        left.add(numberOne);
        right.add(numberTwo);
        left.add(numberThree);
        right.add(numberFour);

        question1.updateChoices(toLowerCase(left), toLowerCase(right));
        ((Matching)question2).updateChoices(toUpperCase(left), toUpperCase(right));

        assertTrue(question1.isEqual(question2));
    }

    @Test
    public void differentOrderChoicesIsEqual() {
        question2 = new Matching();
        ArrayList<String> left1 = new ArrayList<>();
        ArrayList<String> right1 = new ArrayList<>();
        ArrayList<String> left2 = new ArrayList<>();
        ArrayList<String> right2 = new ArrayList<>();

        left1.add(numberOne);
        left1.add(numberTwo);
        right1.add(numberThree);
        right1.add(numberFour);

        // added in different order, but otherwise the same
        left2.add(numberTwo);
        left2.add(numberOne);
        right2.add(numberFour);
        right2.add(numberThree);

        question1.updateChoices(left1, right1);
        ((Matching) question2).updateChoices(left2, right2);

        assertTrue(question1.isEqual(question2));
    }

    @Test
    public void differentNumberOfChoicesIsNotEqual() {
        question2 = new Matching();

        ArrayList<String> left1 = new ArrayList<>();
        ArrayList<String> right1 = new ArrayList<>();
        left1.add(numberOne);
        left1.add(numberTwo);
        left1.add(numberThree);
        right1.add(numberFour);
        right1.add(numberFive);
        right1.add(numberSix);

        ArrayList<String> left2 = new ArrayList<>();
        ArrayList<String> right2 = new ArrayList<>();
        left2.add(numberOne);
        left2.add(numberTwo);
        // not adding three
        right2.add(numberFour);
        right2.add(numberFive);
        // not adding six

        question1.updateChoices(left1, right1);
        ((Matching) question2).updateChoices(left2, right2);

        assertFalse(question1.isEqual(question2));
    }

    @Test
    public void differentLeftChoicesIsNotEqual() {
        question2 = new Matching();
        ArrayList<String> left1 = new ArrayList<>();
        ArrayList<String> right1 = new ArrayList<>();
        ArrayList<String> left2 = new ArrayList<>();
        ArrayList<String> right2 = new ArrayList<>();

        left1.add(numberOne);
        left1.add(numberTwo);
        right1.add(numberThree);
        right1.add(numberFour);


        left2.add(numberOne);
        left2.add(numberFive); // only difference, not two
        right2.add(numberThree);
        right2.add(numberFour);

        question1.updateChoices(left1, right1);
        ((Matching) question2).updateChoices(left2, right2);

        assertFalse(question1.isEqual(question2));
    }

    @Test
    public void differentRightChoicesIsNotEqual() {
        question2 = new Matching();
        ArrayList<String> left1 = new ArrayList<>();
        ArrayList<String> right1 = new ArrayList<>();
        ArrayList<String> left2 = new ArrayList<>();
        ArrayList<String> right2 = new ArrayList<>();

        left1.add(numberOne);
        left1.add(numberTwo);
        right1.add(numberThree);
        right1.add(numberFour);


        left2.add(numberOne);
        left2.add(numberTwo);
        right2.add(numberThree);
        right2.add(numberFive); // only difference, not four

        question1.updateChoices(left1, right1);
        ((Matching) question2).updateChoices(left2, right2);

        assertFalse(question1.isEqual(question2));
    }

    private ArrayList<String> toUpperCase(ArrayList<String> list) {
        ArrayList<String> result = new ArrayList<>();
        for (String s : list) {
            result.add(s.toUpperCase());
        }
        return result;
    }
    private ArrayList<String> toLowerCase(ArrayList<String> list) {
        ArrayList<String> result = new ArrayList<>();
        for (String s : list) {
            result.add(s.toLowerCase());
        }
        return result;
    }
}
