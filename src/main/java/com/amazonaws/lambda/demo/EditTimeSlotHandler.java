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

import com.amazonaws.lambda.db.TimeSlotsDAO;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;

public class EditTimeSlotHandler implements RequestStreamHandler {
	public LambdaLogger logger = null;

	boolean editTimeSlot(String timeSlotID) throws Exception{
		TimeSlotsDAO tsDAO = new TimeSlotsDAO();
		if(tsDAO.editTimeSlot(timeSlotID)) {
			return true;
		}
		return false;
	}
	/**
	 * Handles http request to add/update schedule
	 */
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
		EditTimeSlotResponse response = null;

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
		} catch (ParseException pe) {
			logger.log(pe.toString());
			response = new EditTimeSlotResponse("Bad Request:" + pe.getMessage(), 422); // unable to process input
			responseJson.put("body", new Gson().toJson(response));
			processed = true;
			body = null;
		}
		if (!processed) {
			EditTimeSlotRequest req = new Gson().fromJson(body, EditTimeSlotRequest.class);
			logger.log(req.toString());

			EditTimeSlotResponse resp;
			try {
				if (editTimeSlot(req.timeSlotID)) {
					resp = new EditTimeSlotResponse(1,200);

				} else {
					resp = new EditTimeSlotResponse("Unable to edit time slot: " + req.timeSlotID, 403);

				}
			} catch (Exception e) {
				resp = new EditTimeSlotResponse("Unable to edit time slot: " + req.timeSlotID, 403);

			}
			responseJson.put("body", new Gson().toJson(resp));
		}

		logger.log("end result:" + responseJson.toJSONString());


		OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
		writer.write(responseJson.toJSONString());
		writer.close();

	}

}
