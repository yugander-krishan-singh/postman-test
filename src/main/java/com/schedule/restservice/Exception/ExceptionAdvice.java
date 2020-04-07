package com.schedule.restservice.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ExceptionAdvice {

    @ControllerAdvice
    public static class DateExceptionAdvice {

        @ResponseBody
        @ExceptionHandler(RequestBodyException.DateException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        String dateExceptionHandler(RequestBodyException.DateException ex) {
            return ex.getMessage();
        }
    }

    @ControllerAdvice
    public static class TimeExceptionAdvice {

        @ResponseBody
        @ExceptionHandler(RequestBodyException.TimeException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        String timeExceptionHandler(RequestBodyException.TimeException ex) {
            return ex.getMessage();
        }
    }

    @ControllerAdvice
    public static class UserExceptionAdvice {

        @ResponseBody
        @ExceptionHandler(RequestBodyException.UserException.class)
        @ResponseStatus(HttpStatus.FORBIDDEN)
        String userExceptionHandler(RequestBodyException.TimeException ex) {
            return ex.getMessage();
        }
    }

}
