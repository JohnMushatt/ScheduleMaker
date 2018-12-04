package com.amazonaws.lambda.demo;

public class DeleteMeetingResponse {
	public String body;
	public int code;
	public DeleteMeetingResponse(String body, int code) {
		this.body = body;
		this.code = code;
	}
	public DeleteMeetingResponse(String body) {
		this.body=body;
		this.code = 200;
	}
}
