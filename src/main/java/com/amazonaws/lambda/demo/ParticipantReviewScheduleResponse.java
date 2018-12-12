package com.amazonaws.lambda.demo;

import java.util.List;

import com.amazonaws.lambda.model.Meeting;
import com.amazonaws.lambda.model.TimeSlot;

public class ParticipantReviewScheduleResponse {
	
	public List<TimeSlot> timeslotsInWeek;
	public int tsDuration;
	public String scheduleId;
	public List<Meeting> meetingsInWeek;
	public String startTime;
	public String endTime;
	public String startingDateOfWeek;
	public int httpCode;
	String body;

	//returns the schedule id, the start and end times of that schedule, 
	//the starting date of the week. The time slot duration, and then the list of time slots
	public ParticipantReviewScheduleResponse (List<TimeSlot> timeslotsInWeek, int tsDuration, String scheduleId, List<Meeting> meetingsInWeek, String startTime, String endTime, String startingDateOfWeek, int code) {
		this.timeslotsInWeek = timeslotsInWeek;
		this.tsDuration = tsDuration;
		this.scheduleId = scheduleId;
		this.meetingsInWeek = meetingsInWeek;
		this.startTime = startTime;
		this.endTime = endTime;
		this.startingDateOfWeek = startingDateOfWeek;
		this.httpCode = code;
		this.body=null;
	}
	public ParticipantReviewScheduleResponse (String body, int code) {
		this.timeslotsInWeek = null;
		this.httpCode = code;
		this.body=body;

	}
	// 200 means success
	public ParticipantReviewScheduleResponse (int code) {
		timeslotsInWeek = null;
		this.httpCode = code;
	}
}
