package com.amazonaws.lambda.demo;

import java.util.List;

import com.amazonaws.lambda.model.Schedule;

public class RetrieveSchedulesResponse {
	List<Schedule> schedules;
	int httpCode;
	public RetrieveSchedulesResponse(List<Schedule> schedules) {
		this.schedules= schedules;
		this.httpCode=200;
	}
	public RetrieveSchedulesResponse() {
		this.httpCode=403;
	}
}
