package com.amazonaws.lambda.demo;

public class DeleteScheduleRequest {
	public String secretCode;
	public DeleteScheduleRequest(String secretCode) {
		this.secretCode = secretCode;
	}
	@Override
	public String toString() {
		return "DeleteScheduleRequest("+ this.secretCode+")";
	}

}
