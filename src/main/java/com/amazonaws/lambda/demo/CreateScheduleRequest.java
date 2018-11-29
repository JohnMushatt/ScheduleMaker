package com.amazonaws.lambda.demo;

import java.sql.Date;
import java.sql.Time;

public class CreateScheduleRequest {
	String sId;
	Date initDate;
	Time initTime;
	String orgId;
	Date startDate;
	Date endDate;
	String startTime;
	String endTime;
	int tsDuration;
	String secretCode;

	public CreateScheduleRequest(String sId, Date initDate, Time initTime, String orgId, Date startDate, Date endDate,
			String startTime, String endTime,int tsDuration, String secretCode) {
		this.sId = sId;
		this.initDate = initDate;
		this.initTime = initTime;
		this.orgId = orgId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.tsDuration = tsDuration;
		this.secretCode = secretCode;
	}

	@Override
	public String toString() {
		return "CreateSchedule("+ sId + "," + initDate + "," + initTime + "," + orgId +"," + startDate+ "," + endDate + ",'"
	+ startTime + "," + endTime+  "," + tsDuration + "," + secretCode+")";
	}
}
