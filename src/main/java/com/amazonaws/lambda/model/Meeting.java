package com.amazonaws.lambda.model;

public class Meeting {
	String participantID;
	String organizerID;
	String timeSlotID;
	String participantName;
	
	public Meeting(String participantID, String organizerID, String timeSlotID, String participantName) {
		this.participantID = participantID;
		this.organizerID = organizerID;
		this.timeSlotID = timeSlotID;
		this.participantName = participantName;
	}
}
