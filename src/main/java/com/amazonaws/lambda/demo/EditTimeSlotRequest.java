package com.amazonaws.lambda.demo;

public class EditTimeSlotRequest {
	String timeSlotID;
	String secretCode;
	String date;
	String weekStartDate;
	String startTime;
	int state;
	public EditTimeSlotRequest(String timeSlotID) {
		this.timeSlotID=timeSlotID;
	}
	public EditTimeSlotRequest(String secretCode, String date, int state) {
		this.secretCode=secretCode;
		this.date=date;
		this.state = state;

	}
	public EditTimeSlotRequest(String secretCode, String weekStartDate, String startTime, int state) {
		this.secretCode=secretCode;
		this.weekStartDate=weekStartDate;
		this.startTime=startTime;
		this.state=state;
	}
	@Override
	public String toString() {
		return "EditTimeSlotRequest("+this.timeSlotID+","+secretCode+","+date+","+weekStartDate+","+startTime+")";
	}
}
