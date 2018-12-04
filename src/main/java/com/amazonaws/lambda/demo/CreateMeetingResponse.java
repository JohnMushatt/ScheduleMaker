package com.amazonaws.lambda.demo;

public class CreateMeetingResponse {
	String body;
	int code;
	/**
	 * Create HTTP meeting response
	 * @param body JSON body
	 * @param code HTTP success/failure code
	 */
	public CreateMeetingResponse(String body, int code) {
		this.body=body;
		this.code=code;
	}

	public CreateMeetingResponse(String body) {
		this.body=body;
		this.code=200;
	}
}
