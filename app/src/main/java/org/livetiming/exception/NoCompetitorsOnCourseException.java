package org.livetiming.exception;

public class NoCompetitorsOnCourseException extends RuntimeException {
    public NoCompetitorsOnCourseException(String message) {
        super(message);
    }
}
