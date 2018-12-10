package com.amazonaws.lambda.demo;

public class CreateScheduleResponse {
	public final String secretCode;
	public final String startDate;
	public final String startTime;
	public final String endTime;
	public final int tsDuration;
	public String body;
	public String accessCode;
	int httpCode;
	/**
	 * http response for CreateScheduleHandler
	 * @param body JSON body to return to web
	 * @param code HTTP code to return
	 */
	public CreateScheduleResponse (String secretCode, String startDate
			,String startTime, String endTime, int tsDuration,String accessCode, int code) {
		this.secretCode=secretCode;
		this.startDate=startDate;
		this.startTime=startTime;
		this.endTime=endTime;
		this.tsDuration=tsDuration;
		this.httpCode = code;
		this.accessCode=accessCode;
		this.body=null;
	}
	public CreateScheduleResponse(String body,int code) {
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
		return "CreateScheduleResponse("+secretCode + ","+accessCode+","+startDate+","+
	startTime+","+endTime+","+tsDuration+ ")";
	}
}
