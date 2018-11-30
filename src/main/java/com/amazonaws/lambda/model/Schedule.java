package com.amazonaws.lambda.model;

public class Schedule {

	public final String initialDate;
	public final String initialTime;
	public final String organizerId;
	public final String scheduleId;
	public final int timeslotDuration;
	public final String secretCode;
	public String startDate;
	public String endDate;
	public final String startTime;
	public final String endTime;


	public Schedule(String sId, String initDate, String initTime, String orgId, String startDate,String endDate,
			String startTime, String endTime, int tsDuration,String secretCode) {
		this.initialDate=initDate;
		this.initialTime=initTime;
		this.organizerId=orgId;
		this.scheduleId=sId;
		this.startDate=startDate;
		this.endDate=endDate;
		this.startTime=startTime;
		this.endTime=endTime;
		this.timeslotDuration=tsDuration;
		this.secretCode=secretCode;
	}
}
