package com.amazonaws.lambda.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazonaws.lambda.db.MeetingsDAO;
import com.amazonaws.lambda.db.SchedulesDAO;
import com.amazonaws.lambda.model.Meeting;
import com.amazonaws.lambda.model.Schedule;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;

public class DeleteScheduleHandler implements RequestStreamHandler {
	LambdaLogger logger;
	Schedule currentSchedule;
	@Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {

    	logger = context.getLogger();
		logger.log("Loading Java Lambda handler to create schedule");
		
		JSONObject headerJson = new JSONObject();
		headerJson.put("Content-Type", "application/json");
		headerJson.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
		headerJson.put("Access-Control-Allow-Origin", "*");

		JSONObject responseJson = new JSONObject();
		responseJson.put("headers", headerJson);
		DeleteScheduleResponse response = null;
		
		String body;
		boolean processed = false;

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			JSONParser parser = new JSONParser();
			JSONObject event = (JSONObject) parser.parse(reader);
			logger.log("event:" + event.toJSONString());

			body = (String) event.get("body");
			if (body == null) {
				body = event.toJSONString(); // this is only here to make testing easier
			}
			logger.log("JSON request parsed!");
		} catch (ParseException pe) {
			logger.log(pe.toString());
			response = new DeleteScheduleResponse("Bad Request:" + pe.getMessage(), 422); // unable to process input
			responseJson.put("body", new Gson().toJson(response));
			processed = true;
			body = null;
		}
		if (!processed) {
			DeleteScheduleRequest req = new Gson().fromJson(body, DeleteScheduleRequest.class);
			logger.log(req.toString());
			SchedulesDAO dao = new SchedulesDAO();
			DeleteScheduleResponse resp;
			try {
				if (dao.deleteSchedule(req.scheduleId)) {
					resp = new DeleteScheduleResponse("scheduleID: " + currentSchedule.scheduleId, 200);

					// System.out.println("JSON RESPONSE\n" + responseJson.toJSONString());
					logger.log("JSON Response updated");

				} else {
					resp = new DeleteScheduleResponse("Unable to delete schedule: " + req.scheduleId, 403);
					logger.log(resp.toString());
					// System.out.println("JSON RESPONSE\n" + responseJson.toJSONString());
					logger.log("JSON Response updated");

				}
			} catch (Exception e) {
				resp = new DeleteScheduleResponse("Unable to delete schedule: " + req.scheduleId, 403);
				// System.out.println("JSON RESPONSE\n" + responseJson.toJSONString());
				logger.log("JSON Response updated");

			}
			responseJson.put("body", new Gson().toJson(resp));
		}
		
		// responseJson.put("body", new Gson().toJson(resp));
		// System.out.println("JSON RESPONSE\n" + responseJson.toJSONString());
		logger.log("end result:" + responseJson.toJSONString());

		logger.log(responseJson.toJSONString());

		OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
		writer.write(responseJson.toJSONString());
		writer.close();

		System.out.println("THIS IS THE JSON RESPONSE\n\n" + responseJson.toString());
    }


}
