package com.amazonaws.lambda.demo;

public class DeleteScheduleResponse {
	public String secretCode;
	public int code;
	public DeleteScheduleResponse(String secretCode, int code) {
		this.secretCode = secretCode;
		this.code = code;
	}
	public DeleteScheduleResponse(String body) {
		this.secretCode = secretCode;
		this.code = 200;
	}
}
