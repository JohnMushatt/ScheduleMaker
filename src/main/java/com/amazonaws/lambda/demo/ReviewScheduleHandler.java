package com.amazonaws.lambda.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazonaws.lambda.db.SchedulesDAO;
import com.amazonaws.lambda.model.Meeting;
import com.amazonaws.lambda.model.Schedule;
import com.amazonaws.lambda.model.TimeSlot;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;


public class ReviewScheduleHandler implements RequestStreamHandler {

	//sends me scheduleid, date
	//return array of timeslots with array of attributes

	public LambdaLogger logger = null;
	public Schedule schedule;

	/*public double loadWeek(String arg) throws Exception {
		double id = 0;
		id = loadScheduleFromRDS(arg);
		return id;
	}

	double loadScheduleFromRDS(String arg) throws Exception {
		if (logger != null) { logger.log("in loadValue"); }
		Schedule dao = new ScheduleDAO();
		Week w = dao.getWeek(arg);
		return w.wId;
	}
	*/
	/*
	 * List<TimeSlot> list = getTimeSlots(secretCode);
				int tsDuration = getTimeSlotDuration(secretCode);
				String sId = getScheduleId(secretCode);
				String startTime = getStartTime(secretCode);
				String endTime = getEndTime(secretCode);
				String startingDateOfWeek = getStartingDateOfWeek(secretCode);
	 */

	List<TimeSlot> getTimeSlots(String secretCode) throws Exception{
		if(logger != null) { logger.log("getting timeslots in week");}
		SchedulesDAO dao = new SchedulesDAO();

		return dao.getTimeSlotsInWeek(secretCode);
	}
	
	List<Meeting> getMeetings(List<TimeSlot> times, String sId) throws Exception{
		if(logger != null) { logger.log("getting meetings in week");}
		SchedulesDAO dao = new SchedulesDAO();

		return dao.getMeetingsInWeek(times, sId);
	}
	
	int getTimeSlotDuration(String secretCode) throws Exception{
		if(logger != null) { logger.log("getting timeslot duration");}
		SchedulesDAO dao = new SchedulesDAO();

		return dao.getTimeSlotDuration(secretCode);
	}
	
	String getScheduleId(String secretCode) throws Exception{
		if(logger != null) { logger.log("getting schedule ID");}
		SchedulesDAO dao = new SchedulesDAO();

		return dao.getScheduleId(secretCode);
	}
	
	String getStartTime(String secretCode) throws Exception{
		if(logger != null) { logger.log("getting start time");}
		SchedulesDAO dao = new SchedulesDAO();

		return dao.getStartTime(secretCode);
	}
	
	String getEndTime(String secretCode) throws Exception{
		if(logger != null) { logger.log("getting end time");}
		SchedulesDAO dao = new SchedulesDAO();

		return dao.getEndTime(secretCode);
	}
	
	String getStartingDateOfWeek(String secretCode) throws Exception{
		if(logger != null) { logger.log("getting starting date of the week");}
		SchedulesDAO dao = new SchedulesDAO();
		
		return dao.getStartingDateOfWeek(secretCode);
	}

	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to create constant");

		JSONObject headerJson = new JSONObject();
		headerJson.put("Content-Type", "application/json");  // not sure if needed anymore?
		headerJson.put("Access-Control-Allow-Methods", "DELETE,GET,POST,OPTIONS");
	    headerJson.put("Access-Control-Allow-Origin",  "*");

		JSONObject responseJson = new JSONObject();
		responseJson.put("headers", headerJson);

		ReviewScheduleResponse response = null;

		// extract body from incoming HTTP DELETE request. If any error, then return 422 error
		String body;
		boolean processed = false;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			JSONParser parser = new JSONParser();
			JSONObject event = (JSONObject) parser.parse(reader);
			logger.log("event:" + event.toJSONString());

			body = (String)event.get("body");
			if (body == null) {
				body = event.toJSONString();  // this is only here to make testing easier
			}
		} catch (ParseException pe) {
			logger.log(pe.toString());
			response = new ReviewScheduleResponse("Bad Request:" + pe.getMessage(), 422);  // unable to process input
	        responseJson.put("body", new Gson().toJson(response));
	        processed = true;
	        body = null;
		}

		if (!processed) {
			ReviewScheduleRequest req = new Gson().fromJson(body, ReviewScheduleRequest.class);
			logger.log(req.toString());

			ReviewScheduleResponse resp;
			try {
				String secretCode = req.secretCode;
				//String date = req.date;
				List<TimeSlot> list = getTimeSlots(secretCode);
				int tsDuration = getTimeSlotDuration(secretCode);
				String sId = getScheduleId(secretCode);
				List<Meeting> listOfMeetings = getMeetings(list, sId);
				String startTime = getStartTime(secretCode);
				String endTime = getEndTime(secretCode);
				String startingDateOfWeek = getStartingDateOfWeek(secretCode);
				
				if(list!= null) {
					resp = new ReviewScheduleResponse(list, tsDuration, sId, listOfMeetings, startTime, endTime, startingDateOfWeek, 200);
				} 
				else {
					resp = new ReviewScheduleResponse("bad input: null list", 403);
				}
			} catch (Exception e) {
				resp = new ReviewScheduleResponse(403);
			}

			// compute proper response
	        responseJson.put("body", new Gson().toJson(resp));
		}
		
        logger.log("end result:" + responseJson.toJSONString());
        logger.log(responseJson.toJSONString());
        OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
        writer.write(responseJson.toJSONString());
        writer.close();
	}
}



















