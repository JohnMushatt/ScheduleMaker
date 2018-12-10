package com.amazonaws.lambda.demo;

public class CreateMeetingResponse {
	String body;
	String meetingID;
	String pName;
	String secretCode;
	int code;
	/**
	 * Create HTTP meeting response
	 * @param body JSON body
	 * @param code HTTP success/failure code
	 */
	public CreateMeetingResponse(String body, int code) {
		this.body=body;
		this.code=code;
		this.meetingID=null;
		this.pName=null;
		this.secretCode=null;
	}

	public CreateMeetingResponse(String meetingID,String pName,String secretCode) {
		this.code=200;
		this.meetingID=meetingID;
		this.pName=pName;
		this.secretCode=secretCode;
		this.body=null;
	}
}
