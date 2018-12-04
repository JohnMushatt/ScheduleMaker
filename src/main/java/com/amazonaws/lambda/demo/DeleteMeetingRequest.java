package com.amazonaws.lambda.demo;

public class DeleteMeetingRequest {
	public String meetingID;
	public DeleteMeetingRequest(String meetingID) {
		this.meetingID = meetingID;
	}
	@Override
	public String toString() {
		return "DeleteMeetingRequest("+ this.meetingID+")";
	}
}
