package com.app.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author InuHa
 */
public class TimeUtils {
    
    public static String now(String format) { 
	LocalDateTime now = LocalDateTime.now();
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return now.format(formatter);
    }
    
    public static String date(String format, long miliTime) { 
	Instant instant = Instant.ofEpochMilli(miliTime);
	LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return dateTime.format(formatter);
    }
	
    public static String currentDate() { 
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(new Date());
    }
    
}
