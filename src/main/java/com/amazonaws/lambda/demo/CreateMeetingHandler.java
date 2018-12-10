package com.amazonaws.lambda.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Random;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazonaws.lambda.db.MeetingsDAO;
import com.amazonaws.lambda.db.TimeSlotsDAO;
import com.amazonaws.lambda.model.Meeting;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;

public class CreateMeetingHandler implements RequestStreamHandler {
	LambdaLogger logger;
	Meeting currentMeeting;

	/**
	 * Attempt to create meeting in database
	 * @param	participantID ID of the participant
	 * @param 	organizerID ID of the organizer
	 * @param 	timeSlotID ID of the timeslot
	 * @param 	participantName Name of the participant
	 * @return 	True if successfully added/updated, False if it did not
	 * @throws Exception
	 */
	private boolean createMeeting(String scheduleID, String organizerID, String timeSlotID, String participantName)
			throws Exception {

		if (logger != null) {
			logger.log("in createMeeting");
		}
		Random r = new Random();
		String meetingID = UUID.randomUUID().toString();
		meetingID= meetingID.substring(0, 8)+meetingID.substring(9,13)+meetingID.substring(14,18)+
				meetingID.substring(19,23)+meetingID.substring(24);
		String secretCode = UUID.randomUUID().toString();
		secretCode = secretCode.substring(0, 8)+secretCode.substring(9,13)+secretCode.substring(14,18)+
				secretCode.substring(19,23)+secretCode.substring(24);
		Meeting meeting = new Meeting(meetingID, scheduleID, organizerID, timeSlotID, participantName, secretCode);
		MeetingsDAO dao = new MeetingsDAO();
		Meeting exist = dao.getMeeting(meetingID);
		currentMeeting = meeting;
		TimeSlotsDAO tsDAO = new TimeSlotsDAO();
		//If the time slot is not closed, there is currently no meeting already booked, and there currently does not exist
		// a time slot with the given id
		int booked = tsDAO.getTimeSlot(timeSlotID).isBooked;
		int open = tsDAO.getTimeSlot(timeSlotID).isOpen;
		if ((exist == null) && (booked==0) && (open==1)) {
			tsDAO.updateBooked(timeSlotID);
			return dao.addMeeting(meeting);
		}
		return false;
	}
	/**
	 * Handles HTTP request from web
	 */
	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {

		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to create meeting");

		JSONObject headerJson = new JSONObject();
		headerJson.put("Content-Type", "application/json");
		headerJson.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
		headerJson.put("Access-Control-Allow-Origin", "*");

		JSONObject responseJson = new JSONObject();
		responseJson.put("headers", headerJson);
		CreateMeetingResponse response = null;

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
			response = new CreateMeetingResponse("Bad Request:" + pe.getMessage(), 422); // unable to process input
			responseJson.put("body", new Gson().toJson(response));
			processed = true;
			body = null;
		}
		if (!processed) {
			CreateMeetingRequest req = new Gson().fromJson(body, CreateMeetingRequest.class);
			logger.log(req.toString());

			CreateMeetingResponse resp;
			try {
				if (createMeeting(req.scheduleID, req.organizerID, req.timeslotID, req.participantName)) {
					resp = new CreateMeetingResponse(currentMeeting.meetingID,currentMeeting.participantName,
							currentMeeting.secretCode);
				} else {
					resp = new CreateMeetingResponse(
							"Unable to create meeting for " + req.participantName, 403);

				}
			} catch (Exception e) {
				resp = new CreateMeetingResponse(
						"Unable to create meeting for " + req.participantName, 403);

			}
			responseJson.put("body", new Gson().toJson(resp));
		}

		logger.log("end result:" + responseJson.toJSONString());

		logger.log(responseJson.toJSONString());

		OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
		writer.write(responseJson.toJSONString());
		writer.close();

	}

}
