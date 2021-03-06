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
import com.amazonaws.lambda.model.Meeting;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
public class DeleteMeetingHandler implements RequestStreamHandler {
	LambdaLogger logger;
	Meeting currentMeeting;
	public boolean deleteMeeting(String meetingID) throws Exception{
		if(logger!=null) {
			logger.log("in deleteMeeting");
		}
		MeetingsDAO dao = new MeetingsDAO();
		Meeting exist = dao.getMeeting(meetingID);
		if(exist==null) {
			return false;
		}
		else {
			return dao.deleteMeeting(meetingID);
		}
	}
	/**
	 * Handle HTTP request from web
	 */
    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {

    	logger = context.getLogger();
		logger.log("Loading Java Lambda handler to delete meeting");

		JSONObject headerJson = new JSONObject();
		headerJson.put("Content-Type", "application/json");
		headerJson.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
		headerJson.put("Access-Control-Allow-Origin", "*");

		JSONObject responseJson = new JSONObject();
		responseJson.put("headers", headerJson);
		DeleteMeetingResponse response = null;

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
			response = new DeleteMeetingResponse("Bad Request:" + pe.getMessage(), 422); // unable to process input
			responseJson.put("body", new Gson().toJson(response));
			processed = true;
			body = null;
		}
		if (!processed) {
			DeleteMeetingRequest req = new Gson().fromJson(body, DeleteMeetingRequest.class);
			logger.log(req.toString());
			MeetingsDAO dao = new MeetingsDAO();
			DeleteMeetingResponse resp;
			try {
				if (dao.deleteMeeting(req.meetingID)) {
					resp = new DeleteMeetingResponse("meetingID: " +req.meetingID, 200);

					// System.out.println("JSON RESPONSE\n" + responseJson.toJSONString());
					logger.log("JSON Response updated");

				} else {
					resp = new DeleteMeetingResponse("Unable to delete meeting: " + req.meetingID, 403);
					logger.log(resp.toString());
					// System.out.println("JSON RESPONSE\n" + responseJson.toJSONString());
					logger.log("JSON Response updated");

				}
			} catch (Exception e) {
				resp = new DeleteMeetingResponse("Unable to delete meeting: " + req.meetingID, 403);
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
