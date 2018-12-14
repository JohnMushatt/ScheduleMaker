package com.amazonaws.lambda.demo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;

import com.amazonaws.lambda.demo.http.PostRequest;
import com.amazonaws.lambda.demo.http.PostResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class DeleteScheduleHandlerTest {

	private static final String SAMPLE_INPUT_STRING = "{\"foo\": \"bar\"}";
	private static final String EXPECTED_OUTPUT_STRING = "{\"FOO\": \"BAR\"}";

	Context createContext(String apiCall) {
		TestContext ctx = new TestContext();
		ctx.setFunctionName(apiCall);
		return ctx;
	}

	@Test
	public void testDeleteSchedules() throws IOException{
		System.out.println("RUNNING testDeleteSchedules");

		DeleteScheduleHandler handler = new DeleteScheduleHandler();
		DeleteScheduleRequest dsr = new DeleteScheduleRequest(-1000);

		String deleteMeetingRequest = new Gson().toJson(dsr);
		String deleteJsonRequest = new Gson().toJson(new PostRequest(deleteMeetingRequest));
		InputStream deleteInput = new ByteArrayInputStream(deleteJsonRequest.getBytes());
		OutputStream deleteOutput = new ByteArrayOutputStream();

		handler.handleRequest(deleteInput, deleteOutput, createContext("deleteSchedules"));
		PostResponse post = new Gson().fromJson(deleteOutput.toString(), PostResponse.class);
		DeleteMeetingResponse resp = new Gson().fromJson(post.body, DeleteMeetingResponse.class);
	}

	@Test
	public void testDeleteScheduleHandler() throws IOException {
		System.out.println("RUNNING testDeleteMeetingHandler");

		DeleteScheduleHandler deleteHandler = new DeleteScheduleHandler();
		DeleteScheduleRequest dsr = new DeleteScheduleRequest("308d2daa04a34e99934a92090cbc8bc9");
		String deleteMeetingRequest = new Gson().toJson(dsr);
		String deleteJsonRequest = new Gson().toJson(new PostRequest(deleteMeetingRequest));
		InputStream deleteInput = new ByteArrayInputStream(deleteJsonRequest.getBytes());
		OutputStream deleteOutput = new ByteArrayOutputStream();

		deleteHandler.handleRequest(deleteInput, deleteOutput, createContext("deleteMeeting"));
		PostResponse post = new Gson().fromJson(deleteOutput.toString(), PostResponse.class);
		DeleteMeetingResponse resp = new Gson().fromJson(post.body, DeleteMeetingResponse.class);
	}
}
