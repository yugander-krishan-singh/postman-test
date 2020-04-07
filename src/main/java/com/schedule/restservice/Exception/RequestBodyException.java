package com.schedule.restservice.Exception;

public class RequestBodyException {

    public static class DateException extends RuntimeException {

        public DateException(String message) {
            super(message);
        }
    }

    public static class TimeException extends RuntimeException {

        public TimeException(String message) {
            super(message);
        }

    }

    public static class UserException extends RuntimeException {
        public UserException(String message) {
            super(message);
        }
    }

}
