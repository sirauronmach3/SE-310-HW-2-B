package utils;

public enum TestManagerMenuOptions {
    CREATE_NEW("Create a new Test"),
    DISPLAY_WITH_KEY("Display an existing Test with correct Answers"),
    DISPLAY_WITHOUT_KEY("Display an existing Test without correct Answers"),
    LOAD("Load an existing Test"),
    SAVE("Save the current Test"),
    TAKE("Take the current Test"),
    MODIFY("Modify the current Test"),
    TABULATE("Tabulate the current Test"),
    GRADE("Grade a Test"),
    RETURN("Return to the previous menu");

    public final String name;
    TestManagerMenuOptions(String name) {
        this.name = name;
    }

}
