package com.amazonaws.lambda.model;

import java.sql.Date;
import java.sql.Time;

public class Schedule {

	public final Date initialDate;
	public final Time initialTime;
	public final String organizerId;
	public final String scheduleId;
	public final int timeslotDuration;
	public final String secretCode;
	public Date startDate;
	public Date endDate;
	public final String startTime;
	public final String endTime;


	public Schedule(String sId, Date initDate, Time initTime, String orgId, Date startDate,Date endDate,
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
