package com.amazonaws.lambda.model;

public class TimeSlot {
	public String timeSlotID;
	public boolean isOpen;
	public String startTime;
	public String endTime;
	public boolean isBooked;
	public int dayOfWeek;


	public TimeSlot(String timeSlotID, boolean isOpen, String startTime, String endTime, boolean isBooked, int dayOfWeek) {
		this.timeSlotID = timeSlotID;
		this.isOpen = isOpen;
		this.startTime = startTime;
		this.endTime = endTime;
		this.isBooked = isBooked;
		this.dayOfWeek = dayOfWeek;
	}
}
