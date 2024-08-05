package com.app.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
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
    
    public static String date(String format, Timestamp timestamp) { 
	LocalDateTime dateTime = timestamp.toLocalDateTime();
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return dateTime.format(formatter);
    }
	
    public static String date(String format, LocalDate localDate) { 
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
	return localDate.format(formatter);
    }
    
    public static String date(String format, String timestamp) { 
	boolean hasTime = timestamp.length() > 10;
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(hasTime ? "yyyy-MM-dd HH:mm:ss.S" : "yyyy-MM-dd");
	if (hasTime) {
	    LocalDateTime dateTime = LocalDateTime.parse(timestamp, inputFormatter);
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
	    return dateTime.format(formatter);
	} else {	    
	    LocalDate dateTime = LocalDate.parse(timestamp, inputFormatter);
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
	    return dateTime.format(formatter);
	}
    }
	
    public static String currentDate() { 
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(new Date());
    }
    
    public static String currentDateTime() { 
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(new Date());
    }
	
}
