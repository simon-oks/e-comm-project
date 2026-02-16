package com.cagecfi.productservice.utils;

import com.cagecfi.productservice.exception.InvalidValueException;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Slf4j
public class DateConverter {

    /**
     *
     * @param date au format yyyy-MM-dd
     * @return {@link Instant}
     */
    public static Instant convertDateToInstant(String date) {

        if (date == null) {
            log.error("Invalid date: null");
            throw new IllegalArgumentException("Invalid date: null");
        }

        // Date au format yyyy-MM-dd
        String[] dateTab = date.split("-");

        if (dateTab.length != 3) {
            log.error("Invalid date: {}", date);
            throw new InvalidValueException("Invalid date");
        }

        //int year = Integer.parseInt(dateTab[0]);
        int month = Integer.parseInt(dateTab[1]);
        int day = Integer.parseInt(dateTab[2]);

        if (month > 12 || day > 31) {
            log.error("Invalid date: {}", date);
            throw new InvalidValueException("Invalid date");
        }

        try {
            LocalDate localDate = LocalDate.parse(date);
            // Fuseau horaire (par exemple, UTC)
            ZoneId zoneId = ZoneId.of("UTC");

            // Conversion en Instant
            return localDate.atStartOfDay(zoneId).toInstant();
        }catch (Exception e) {
            log.error("Invalid date: {}", date, e);
            throw new InvalidValueException("Invalid date");
        }
    }

    public static Instant instantStringToInstant(String instantString){
        try {
            return Instant.parse(instantString);
        }catch (Exception e){
            throw new InvalidValueException("Invalid instant");
        }
    }
}
