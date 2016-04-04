package com.logs.reader;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.logs.reader.core.LogData;

public class LogUtils {

	private static Pattern pattern;
    private static Matcher matcher;
    
    private static final String START_PATTERN = "\\[(?<uuid>.*?)\\] Started (?<type>.*) \"(?<url>.*)\" for (?<ip>.*) at (?<datetime>.*) .{5}";
    private static final String COMPLETED_PATTERN = "\\[(?<uuid>.*?)\\] Completed (?<status>.*) in (?<time>[0-9]*)ms .*";
    private static final String DETAILS_PATTERN = "\\[(?<uuid>.*?)\\] (?<details>.*)";
	
	public static Timestamp getTimeStamp(String dateTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
		Timestamp ts = null;
		try {
			Date date = sdf.parse(dateTime);
			ts = new Timestamp(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			ts = null;
		}		
		return ts;
	}
	
	public static void setLogEntry(String logLine, LogData logData) {
		pattern = Pattern.compile(START_PATTERN);
		matcher = pattern.matcher(logLine);
		if(matcher.find()) {
			logData.setUuid(matcher.group("uuid"));
			logData.setRequestType(matcher.group("type"));
			logData.setUrl(matcher.group("url"));
			logData.setIpAddress(matcher.group("ip"));
			logData.setDateTime(LogUtils.getTimeStamp(matcher.group("datetime")));
		}
	}
	
	public static void setLogReturn(String logLine, LogData logData) {
		pattern = Pattern.compile(COMPLETED_PATTERN);
		matcher = pattern.matcher(logLine);
		if(matcher.find() && matcher.group("uuid").equals(logData.getUuid()) ) {
			logData.setReturnStatus(matcher.group("status"));
			logData.setExecutionTime(Integer.parseInt(matcher.group("time")));
		}
	}
	
	public static void setLogDetails(String logLine, LogData logData) {
		pattern = Pattern.compile(DETAILS_PATTERN);
		matcher = pattern.matcher(logLine);
		if(matcher.find() && matcher.group("uuid").equals(logData.getUuid())) {
			logData.setDetails(matcher.group("details"));
		}
	}
	
	public static boolean isLogLine(String logLine) {
		pattern = Pattern.compile(DETAILS_PATTERN);
		matcher = pattern.matcher(logLine);
		return matcher.find();
	}

	public static String getLogUuid(String logLine) {
		pattern = Pattern.compile(DETAILS_PATTERN);
		matcher = pattern.matcher(logLine);
		String rslt = ""; 
		if (matcher.find()) {
			rslt = matcher.group("uuid");
		}
		return rslt;
	}
	
	public static boolean isLogEntry(String logLine) {
		pattern = Pattern.compile(START_PATTERN);
		matcher = pattern.matcher(logLine);
		return matcher.find();
	}
	
	public static boolean isLogReturn(String logLine) {
		pattern = Pattern.compile(COMPLETED_PATTERN);
		matcher = pattern.matcher(logLine);
		return matcher.find();
	}
	
}
