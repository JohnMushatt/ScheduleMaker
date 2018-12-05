package com.amazonaws.lambda.demo;

import java.util.List;

import com.amazonaws.lambda.model.TimeSlot;

public class CreateScheduleResponse {
	public final List<TimeSlot> list;
	public final String secretCode;
	public final String startDate;
	public final String startTime;
	public final String endTime;
	public final int tsDuration;
	public String body;
	int httpCode;
	/**
	 * http response for CreateScheduleHandler
	 * @param body JSON body to return to web
	 * @param code HTTP code to return
	 */
	public CreateScheduleResponse (List<TimeSlot> list, String secretCode, String startDate
			,String startTime, String endTime, int tsDuration, int code) {
		this.list=list;
		this.secretCode=secretCode;
		this.startDate=startDate;
		this.startTime=startTime;
		this.endTime=endTime;
		this.tsDuration=tsDuration;
		this.httpCode = code;
		this.body=null;
	}
	public CreateScheduleResponse(String body,int code) {
		this.list=null;
		this.secretCode=null;
		this.startDate=null;
		this.startTime=null;
		this.endTime=null;
		this.tsDuration=0;
		this.body=body;
		this.httpCode=code;
	}
	@Override
	public String toString() {
		return "CreateScheduleResponse(" + list + ","+ secretCode + ","+startDate+","+
	startTime+","+endTime+","+tsDuration+ ")";
	}
}
