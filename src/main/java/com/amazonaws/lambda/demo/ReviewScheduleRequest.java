package com.amazonaws.lambda.demo;

public class ReviewScheduleRequest {

	String date;
	String scheduleId;

    public ReviewScheduleRequest(String date, String scheduleId) {
    	this.date=date;
    	this.scheduleId=scheduleId;
    }

    @Override
    public String toString() {
    	return "ReviewScheduleRequest(" + date + "," + scheduleId + "," +")";
    }
}