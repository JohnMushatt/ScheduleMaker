package com.amazonaws.lambda.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class ReviewScheduleRequest implements RequestHandler<S3Event, String> {
	
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