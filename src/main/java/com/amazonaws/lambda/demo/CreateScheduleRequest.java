package com.amazonaws.lambda.demo;

public class CreateScheduleRequest {
	String initDate;
	String initTime;
	String orgId;
	String startDate;
	String endDate;
	String startTime;
	String endTime;
	int tsDuration;
	String secretCode;

	public CreateScheduleRequest(String initDate, String initTime, String startDate, String endDate,
			String startTime, String endTime,int tsDuration) {
		this.initDate = initDate;
		this.initTime = initTime;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.tsDuration = tsDuration;
	}

	@Override
	public String toString() {
		return "CreateScheduleRequest(" + initDate + "," + initTime +"," + startDate+ "," + endDate + ","
	+ startTime + "," + endTime+  "," + tsDuration+")";
	}
}
