package utils;

public enum TypesOfSurvey {
    TEST("Test"),
    SURVEY("Survey"),
    EXIT("Quit");

    public final String name;
    TypesOfSurvey(String name) {this.name = name;}
}
