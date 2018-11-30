package com.amazonaws.lambda.demo;

import java.sql.Date;
import java.sql.Time;

public class CreateScheduleRequest {
	Date initDate;
	Time initTime;
	String orgId;
	Date startDate;
	Date endDate;
	String startTime;
	String endTime;
	int tsDuration;
	String secretCode;

	public CreateScheduleRequest(Date initDate, Time initTime, Date startDate, Date endDate,
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
		return "CreateSchedule("+ "," + initDate + "," + initTime +"," + startDate+ "," + endDate + ",'"
	+ startTime + "," + endTime+  "," + tsDuration + "," +")";
	}
}
