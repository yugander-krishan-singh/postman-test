package com.schedule.restservice.controller;

import com.schedule.restservice.Exception.RequestBodyException;
import com.schedule.restservice.Validations.ScheduleValidator;
import com.schedule.restservice.bean.Slot;
import com.schedule.restservice.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class SchedulerController {

    private static final Logger LOGGER = Logger.getLogger(SchedulerController.class.getName());
    private static final String TOKEN = "access_token";

    @Autowired
    private SchedulerService schedulerService;

    @Autowired
    private ScheduleValidator scheduleValidator;

    @PostMapping("/slots")
    public ResponseEntity<String> saveAvailableSlots(@RequestHeader(value = TOKEN) String token, @RequestBody Slot slot) {

        scheduleValidator.validateSlots(slot);
        LOGGER.log(Level.INFO, "the dates are validated");

        schedulerService.saveAvailableSlots(token, slot);
        //schedulerService.saveAvailableSlots(slot);

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/slots")
    public ResponseEntity<Integer[]> getAvailableSlots(@RequestHeader(value = TOKEN) String token, @RequestParam("date") String date) {

        try {
            LocalDate.parse(date);
        }catch(Exception e) {
            throw new RequestBodyException.DateException("Date format is incorrect. Pass in YYYY-MM-DD format");
        }

        return new ResponseEntity<>(schedulerService.getAvailableSlots(token, date), HttpStatus.OK);
    }
}
