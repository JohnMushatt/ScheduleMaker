package com.amazonaws.lambda.demo;

public class DeleteMeetingRequest {
	public String meetingID;
	public String secretCode;
	public DeleteMeetingRequest(String meetingID) {
		this.meetingID = meetingID;
		secretCode=null;
	}
	public DeleteMeetingRequest(String meetingID,String secretCode) {
		this.meetingID=meetingID;
		this.secretCode=secretCode;
	}
	@Override
	public String toString() {
		return "DeleteMeetingRequest("+ this.meetingID+")";
	}
}
