package com.amazonaws.lambda.demo;

import java.sql.Date;

import com.amazonaws.lambda.model.Week;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class ReviewScheduleResponse{

	public ArrayList<TimeSlots> timeslotsInWeek;
	public int httpCode;

	public ReviewScheduleResponse (ArrayList<TimeSlots> timeslotsInWeek, int code) {
		this.timeslotsInWeek = timeslotsInWeek;
		this.httpCode = code;
	}
	// 200 means success
	public ReviewScheduleResponse (int code) {
		timeslotsInWeek = null;
		this.httpCode = code;
	}
}
