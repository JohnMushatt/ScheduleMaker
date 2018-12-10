package com.amazonaws.lambda.demo;

import java.util.List;

import com.amazonaws.lambda.model.TimeSlot;

public class ReviewScheduleResponse{

	public List<TimeSlot> timeslotsInWeek;
	public int tsDuration;
	public String scheduleId;
	public String startTime;
	public String endTime;
	public String startingDateOfWeek;
	public int httpCode;
	String body;

	//returns the schedule id, the start and end times of that schedule,
	//the starting date of the week. The time slot duration, and then the list of time slots
	public ReviewScheduleResponse (List<TimeSlot> timeslotsInWeek, int tsDuration, String scheduleId, String startTime, String endTime, String startingDateOfWeek, int code) {
		this.timeslotsInWeek = timeslotsInWeek;
		this.tsDuration = tsDuration;
		this.scheduleId = scheduleId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.startingDateOfWeek = startingDateOfWeek;
		this.httpCode = code;
		this.body=null;
	}
	public ReviewScheduleResponse (String body, int code) {
		this.timeslotsInWeek = null;
		this.httpCode = code;
		this.body=body;

	}
	// 200 means success
	public ReviewScheduleResponse (int code) {
		timeslotsInWeek = null;
		this.httpCode = code;
	}
}
