package com.amazonaws.lambda.model;

import java.sql.Time;

public class TimeSlot {
	boolean isOpen;
	Time startTime;
	Time endTime;
	boolean isBooked;
	Meeting meeting;
	String timeSlotID;
	int dayOfWeek;
	
	
	public TimeSlot(boolean isOpen, Time startTime, Time endTime, boolean isBooked, Meeting meeting, String timeSlotID,
			int dayOfWeek) {
		this.isOpen = isOpen;
		this.startTime = startTime;
		this.endTime = endTime;
		this.isBooked = isBooked;
		this.meeting = meeting;
		this.timeSlotID = timeSlotID;
		this.dayOfWeek = dayOfWeek;
	}
}
