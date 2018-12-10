package com.amazonaws.lambda.demo;

public class EditTimeSlotRequest {
	String timeSlotID;

	public EditTimeSlotRequest(String timeSlotID) {
		this.timeSlotID=timeSlotID;
	}

	@Override
	public String toString() {
		return "EditTimeSlotRequest("+this.timeSlotID+")";
	}
}
