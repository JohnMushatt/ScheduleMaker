package com.amazonaws.lambda.model;

public class TimeSlot {
	public String timeSlotID;
	public int isOpen;
	public String startTime;
	public String endTime;
	public int isBooked;
	public int dayOfWeek;
	public String scheduleID;

	public TimeSlot(String timeSlotID, int isOpen, String startTime, String endTime, int isBooked, int dayOfWeek,
			String scheduleID) {
		this.timeSlotID = timeSlotID;
		this.isOpen = isOpen;
		this.startTime = startTime;
		this.endTime = endTime;
		this.isBooked = isBooked;
		this.dayOfWeek = dayOfWeek;
		this.scheduleID=scheduleID;
	}
}
