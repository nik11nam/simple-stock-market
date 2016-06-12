package com.stock.market.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by NikhilKoshi on 10/6/16.
 */
public class DateUtils {

    /**
     * Current time offset (+ / -) minutes
     *
     * @param minutes
     * @return
     */
    public static Date nowOffsetMinutes(int minutes) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE, minutes);
        return instance.getTime();
    }

}
