package com.amazonaws.lambda.demo;

import java.util.List;

import com.amazonaws.lambda.model.TimeSlot;

public class ReviewScheduleResponse{

	public List<TimeSlot> timeslotsInWeek;
	public int httpCode;
	String body;
	public ReviewScheduleResponse (List<TimeSlot> timeslotsInWeek, int code) {
		this.timeslotsInWeek = timeslotsInWeek;
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
