package com.amazonaws.lambda.demo;

public class RetrieveSchedulesRequest {

	int hr;
	public RetrieveSchedulesRequest(int hr) {
		    this.hr = hr;
	}

	@Override
	public String toString() {
		return "RetrieveScheduleRequest("+this.hr+")";
	}
}
