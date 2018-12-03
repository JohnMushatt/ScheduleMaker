package com.amazonaws.lambda.demo;

public class CreateMeetingRequest {
	String scheduleID;
	String organizerID;
	String timeslotID;
	String participantID;
	String participantName;
	public CreateMeetingRequest(String sID, String orgID,String tID,String pID,String pName) {
		this.scheduleID=sID;
		this.organizerID=orgID;
		this.timeslotID=tID;
		this.participantID=pID;
		this.participantName=pName;
	}

	@Override
	public String toString() {
		return "CreateMeetingRequest("+this.scheduleID+","+this.organizerID+","+this.timeslotID+","+this.participantID+","+this.participantName+")";
	}
}
