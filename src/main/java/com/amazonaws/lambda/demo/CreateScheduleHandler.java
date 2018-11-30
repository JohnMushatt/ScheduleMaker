package com.amazonaws.lambda.demo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.sql.Time;
import java.util.Random;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazonaws.lambda.db.SchedulesDAO;
import com.amazonaws.lambda.model.Schedule;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;


public class CreateScheduleHandler implements RequestStreamHandler {

	public LambdaLogger logger = null;

	boolean createSchedule(Date initDate, Time initTime,
			Date startDate,Date endDate,String startTime, String endTime,
			int tsDuration) throws Exception {
		if(logger !=null) {
			logger.log("in createSchedule");
		}
		SchedulesDAO dao = new SchedulesDAO();
		Random r = new Random();
		String organizerId = "" + (int) (r.nextDouble()*10000);
		String sId = initDate + organizerId + initTime;
		String secretCode = sId+organizerId;
		Schedule exist = dao.getSchedule(sId);
		Schedule schedule = new Schedule(sId,initDate,initTime,organizerId, startDate,
				endDate,startTime,endTime, tsDuration, secretCode);
		if(exist ==null) {
			return dao.addSchedule(schedule);
		}
		else {
			return dao.updateSchedule(schedule);
		}
	}
    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {

    	logger = context.getLogger();
    	logger.log("Loading Java Lambda handler to create schedule");

    	JSONObject headerJson = new JSONObject();
    	headerJson.put("Content-Type", "application/json");
    	headerJson.put("Access-Control-Allow-Methods", "get,Post,OPTIONS");
    	headerJson.put("Access-Control-Allow-Origin", "*");

    	JSONObject responseJson = new JSONObject();
    	responseJson.put("headers", headerJson);
    	CreateScheduleResponse response = null;

    	String body;

    	boolean processed =false;
    	try {
    		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			JSONParser parser = new JSONParser();
			JSONObject event = (JSONObject) parser.parse(reader);
			logger.log("event:" + event.toJSONString());

			String method = (String) event.get("httpMethod");
			if (method != null && method.equalsIgnoreCase("OPTIONS")) {
				logger.log("Options request");
				response = new CreateScheduleResponse("name", 200);  // OPTIONS needs a 200 response
		        responseJson.put("body", new Gson().toJson(response));
		        processed = true;
		        body = null;
			} else {
				body = (String)event.get("body");
				if (body == null) {
					body = event.toJSONString();  // this is only here to make testing easier
				}
			}

    	}
    	catch(ParseException pe) {
    		logger.log(pe.toString());
			response = new CreateScheduleResponse("Bad Request:" + pe.getMessage(), 422);  // unable to process input
	        responseJson.put("body", new Gson().toJson(response));
	        processed = true;
	        body = null;
    	}

    	if(!processed) {
    		CreateScheduleRequest req = new Gson().fromJson(body, CreateScheduleRequest.class);
    		logger.log(req.toString());

    		CreateScheduleResponse resp;

    		try {
    			if(createSchedule(req.initDate,req.initTime,req.startDate,req.endDate,
    					req.startTime,req.endTime,req.tsDuration));
    		}
    		catch(Exception e) {
    			resp = new CreateScheduleResponse("Unable to create schedule: " + req.initDate);
    		}


    	}
    	  logger.log("end result:" + responseJson.toJSONString());
          logger.log(responseJson.toJSONString());
          OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
          writer.write(responseJson.toJSONString());
          System.out.println("here");
          writer.close();
    }

}
