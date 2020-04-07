package com.schedule.restservice.Validations;

import com.schedule.restservice.Exception.RequestBodyException;
import com.schedule.restservice.bean.Slot;
import com.schedule.restservice.controller.SchedulerController;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ScheduleValidator {

    private static final Logger LOGGER = Logger.getLogger(SchedulerController.class.getName());

    public void validateDates(LocalDate passedDate) {
        //LocalDate passedDate = slot.getDate();

        if(passedDate == null) {
            LOGGER.log(Level.INFO, "the passed date is null");
            throw new RequestBodyException.DateException("the passed date is null");
            //return false;
        }

        if(passedDate.isBefore(LocalDate.now())) {
            System.out.println("passed date is beofre the today's date");
            throw new RequestBodyException.DateException("passed date is beofre th today's date");
            //return false;
        }
    }

    public void validateTime(Slot slot) {
        List<String> time = slot.getTime();

        if(time.size() == 0) {
            //throw new RuntimeException("there are no times passed");
            throw new RequestBodyException.TimeException("there are no times passed");
        }

        for(String t : time) {
            LocalTime lt = null;
            try {
                lt = LocalTime.parse(t);
            }catch(Exception e) {
                LOGGER.log(Level.INFO, "The time cannot be parsed");
                throw new RequestBodyException.TimeException("The time cannot be parsed");
                //return false;
            }

            if(LocalTime.now().isAfter(lt)) {
                System.out.println("Current time is after the passed time");
                throw new RequestBodyException.TimeException("Current time is after the passed time");
                //return false;
            }

            if(lt.getMinute() != 0 || lt.getHour() > 23) {
                System.out.println("the passed time is out of range");
                throw new RequestBodyException.TimeException("the passed time is out of range");
                //return false;
            }
        }
    }

    public void validateSlots(Slot slot) {

        validateDates(slot.getDate());

        if(!slot.getDate().isAfter(LocalDate.now())) {
            validateTime(slot);
        }
    }
}
