package utils;

public enum SurveyManagerMenuOptions {
    CREATE_NEW("Create a new Survey"),
    DISPLAY("Display an existing Survey"),
    LOAD("Load an existing Survey"),
    SAVE("Save the current Survey"),
    TAKE("Take the current Survey"),
    MODIFY("Modify the current Survey"),
    TABULATE("Tabulate a Survey"),
    RETURN("Return to the previous menu");

    public final String name;

    SurveyManagerMenuOptions(String name) {
        this.name = name;
    }
}
