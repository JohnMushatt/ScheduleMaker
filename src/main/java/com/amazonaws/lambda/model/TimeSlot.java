package com.amazonaws.lambda.model;

import java.sql.Time;

public class TimeSlot {
	public String timeSlotID;
	public boolean isOpen;
	public Time startTime;
	public Time endTime;
	public boolean isBooked;
	public int dayOfWeek;
	
	
	public TimeSlot(String timeSlotID, boolean isOpen, Time startTime, Time endTime, boolean isBooked, int dayOfWeek) {
		this.timeSlotID = timeSlotID;
		this.isOpen = isOpen;
		this.startTime = startTime;
		this.endTime = endTime;
		this.isBooked = isBooked;
		this.dayOfWeek = dayOfWeek;
	}
}
