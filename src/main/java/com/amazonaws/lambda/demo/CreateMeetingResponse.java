package com.amazonaws.lambda.demo;

public class CreateMeetingResponse {
	String body;
	int code;

	public CreateMeetingResponse(String body, int code) {
		this.body=body;
		this.code=code;
	}

	public CreateMeetingResponse(String body) {
		this.body=body;
		this.code=200;
	}
}
