package com.amazonaws.lambda.model;

public class Meeting {
	public String meetingID;
	public String participantID;
	public String organizerID;
	public String timeSlotID;
	public String participantName;
	public String secretCode;

	public Meeting(String meetingID, String participantID, String organizerID, String timeSlotID, String participantName,String secretCode) {
		this.meetingID = meetingID;
		this.participantID = participantID;
		this.organizerID = organizerID;
		this.timeSlotID = timeSlotID;
		this.participantName = participantName;
		this.secretCode = secretCode;
	}
}
