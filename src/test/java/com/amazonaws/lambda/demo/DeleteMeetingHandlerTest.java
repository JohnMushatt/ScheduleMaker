package com.amazonaws.lambda.demo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;

import com.amazonaws.lambda.demo.http.PostRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class DeleteMeetingHandlerTest {

	private static final String SAMPLE_INPUT_STRING = "{\"foo\": \"bar\"}";
	private static final String EXPECTED_OUTPUT_STRING = "{\"FOO\": \"BAR\"}";

	Context createContext(String apiCall) {
		TestContext ctx = new TestContext();
		ctx.setFunctionName(apiCall);
		return ctx;
	}

	@Test
	public void testDeleteMeetingHandler() throws IOException {
		CreateMeetingHandler createHandler = new CreateMeetingHandler();

		CreateMeetingRequest cmr = new CreateMeetingRequest("testScheduleID", "testOrganizerID", "testTimeSlotID",
				"jordanSuckz");

		String createMeetingRequest = new Gson().toJson(cmr);
		String createJsonRequest = new Gson().toJson(new PostRequest(createMeetingRequest));
		InputStream createInput = new ByteArrayInputStream(createJsonRequest.getBytes());
		OutputStream createOutput = new ByteArrayOutputStream();

		createHandler.handleRequest(createInput, createOutput, createContext("createMeeting"));

		DeleteMeetingHandler deleteHandler = new DeleteMeetingHandler();
		DeleteMeetingRequest dmr = new DeleteMeetingRequest("7365");
		String deleteMeetingRequest = new Gson().toJson(dmr);
		String deleteJsonRequest = new Gson().toJson(new PostRequest(deleteMeetingRequest));
		InputStream deleteInput = new ByteArrayInputStream(deleteJsonRequest.getBytes());
		OutputStream deleteOutput = new ByteArrayOutputStream();

		deleteHandler.handleRequest(deleteInput, deleteOutput, createContext("deleteMeeting"));
	}
}
