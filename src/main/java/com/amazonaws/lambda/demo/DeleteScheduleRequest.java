package com.amazonaws.lambda.demo;

public class DeleteScheduleRequest {
	public String scheduleId;
	public DeleteScheduleRequest(String scheduleId) {
		this.scheduleId = scheduleId;
	}
	@Override
	public String toString() {
		return "DeleteScheduleRequest("+ this.scheduleId+")";
	}

}
