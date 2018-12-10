package com.amazonaws.lambda.demo;

public class ReviewScheduleRequest {

	//String date;
	String secretCode;

    public ReviewScheduleRequest(String secretCode) {
    	//this.date=date;
    	this.secretCode=secretCode;
    }

    @Override
    public String toString() {
    	return "ReviewScheduleRequest(" + secretCode+")";
    }
}