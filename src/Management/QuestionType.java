package Management;

public enum QuestionType {
    MULTIPLE_CHOICE("multiple choice", true),
    TRUE_FALSE("T/F", true),
    ESSAY("essay", false),
    SHORT_ANSWER("short answer", false),
    MATCHING("matching", true),
    VALID_DATE("date", false);
    public final String label;
    public final boolean hasChoices;

    QuestionType(String label, boolean hasChoices) {
        this.label = label;
        this.hasChoices = hasChoices;
    }
}
