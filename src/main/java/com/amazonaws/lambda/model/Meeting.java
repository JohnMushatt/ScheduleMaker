package com.amazonaws.lambda.model;

public class Meeting {
	public String meetingID;
	public String organizerID;
	public String timeSlotID;
	public String participantName;
	public String secretCode;
	public String scheduleID;
	public Meeting(String meetingID, String organizerID, String timeSlotID, String participantName,String secretCode,String scheduleID) {
		this.meetingID = meetingID;
		this.organizerID = organizerID;
		this.timeSlotID = timeSlotID;
		this.participantName = participantName;
		this.secretCode = secretCode;
		this.scheduleID=scheduleID;
	}
}
