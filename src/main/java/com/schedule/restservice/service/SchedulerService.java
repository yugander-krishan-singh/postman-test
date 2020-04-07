package com.schedule.restservice.service;

import com.schedule.restservice.bean.Slot;
import com.schedule.restservice.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SchedulerService {

    @Autowired
    private UserAuthService userAuthService;

    private static final Map<User, Map<LocalDate, Integer[]>> scheduleMap = new HashMap<>();
    //private static final Map<LocalDate, Map<User, Integer[]>> newScheduleMap = new HashMap<>();
    private static final Map<String, Integer[]> newScheduleMap = new HashMap<>();


    public void saveAvailableSlots(String token, Slot slot) {

        User user = userAuthService.findByToken(token);
        String userId = user.getUserName();

        LocalDate date = slot.getDate();
        String key = userId + ":" + date.toString();

        if(newScheduleMap.containsKey(key)) {

            Integer[] slotArr = newScheduleMap.get(key);

            for(String s : slot.getTime()) {
                int hour = LocalTime.parse(s).getHour();
                slotArr[hour-1] += 1;
            }

            newScheduleMap.put(key, slotArr);

        }else{

            Integer[] slotArr = new Integer[23];
            Arrays.fill(slotArr, 0);

            System.out.println(Arrays.toString(slotArr));

            for(String s : slot.getTime()) {
                System.out.println(s + " the time \n");
                int hour = LocalTime.parse(s).getHour();

                System.out.println("hour = " + hour);

                slotArr[hour-1] += 1;
            }

            newScheduleMap.put(key, slotArr);

        }
    }

    public Integer[] getAvailableSlots(String token, String date) {

        User user = userAuthService.findByToken(token);
        String userId = user.getUserName();

        String key = userId + ":" + date;

        return newScheduleMap.get(key);
    }

    public static void main(String[] args) {

       String s = "12:00";
       LocalTime time = LocalTime.parse(s);
       System.out.println(time.getMinute());

       String date = "2019-12-27";
       LocalDate parsedDate = LocalDate.parse(date);
       System.out.println(parsedDate);
    }
}
