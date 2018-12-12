package com.amazonaws.lambda.demo;

public class ParticipantReviewScheduleRequest {

	//String date;
		String accessCode;

	    public ParticipantReviewScheduleRequest(String accessCode) {
	    	//this.date=date;
	    	this.accessCode=accessCode;
	    }

	    @Override
	    public String toString() {
	    	return "ParticipantReviewScheduleRequest(" + accessCode + "," +")";
	    }

}
