package com.schedule.restservice.bean;

import java.time.LocalDate;
import java.util.List;

public class Slot {

    private LocalDate date;

    private List<String> time;

    public LocalDate getDate() {
        return date;
    }

    public List<String> getTime() {
        return time;
    }
}
