package utils;

public enum SerializationIDs {
    SURVEY(1L),
    QUESTION(2L),
    ESSAY(3L),
    MATCHING(4L),
    MULTIPLE_CHOICE(5L),
    SHORT_ANSWER(6L),
    TRUE_FALSE(7L),
    VALID_DATE(8L),
    RESPONSE(9L),
    DATE_RESPONSE(11L),
    OPEN_ENDED_RESPONSE(12L),
    PAIR_RESPONSE(13L),
    SELECTION_RESPONSE(14L),
    SHORT_ANSWER_RESPONSE(15L);

    public final long uid;
    SerializationIDs(long uid) {
        this.uid = uid;
    }
}
