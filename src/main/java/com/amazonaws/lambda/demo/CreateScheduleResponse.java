package com.amazonaws.lambda.demo;

public class CreateScheduleResponse {
	String body;
	int httpCode;
	/**
	 * http response for CreateScheduleHandler
	 * @param body JSON body to return to web
	 * @param code HTTP code to return
	 */
	public CreateScheduleResponse (String body, int code) {
		this.body = body;
		this.httpCode = code;
	}

	// 200 means success
	public CreateScheduleResponse (String body) {

		this.body = body;
		this.httpCode = 200;
	}

	@Override
	public String toString() {
		return "Value(" + body + ")";
	}
}
