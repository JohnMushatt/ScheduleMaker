package com.amazonaws.lambda.demo;

public class DeleteScheduleRequest {
	public String secretCode;
	public int days;
	public DeleteScheduleRequest(String secretCode) {
		this.secretCode = secretCode;
		this.days=0;
	}

	public DeleteScheduleRequest(int days) {
		this.secretCode=null;
		this.days=days;
	}
	@Override
	public String toString() {
		if(secretCode==null) {
			return "DeleteScheduleRequest("+ this.days+")";
		}
		else {
			return "DeleteScheduleRequest("+ this.secretCode+")";
		}
	}

}
