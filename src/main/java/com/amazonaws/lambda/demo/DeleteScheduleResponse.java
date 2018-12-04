package com.amazonaws.lambda.demo;

public class DeleteScheduleResponse {
	public String body;
	public int code;
	public DeleteScheduleResponse(String body, int code) {
		this.body = body;
		this.code = code;
	}
	public DeleteScheduleResponse(String body) {
		this.body = body;
		this.code = 200;
	}
}
