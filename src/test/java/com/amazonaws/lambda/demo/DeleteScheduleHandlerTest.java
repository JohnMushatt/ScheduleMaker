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

public class DeleteScheduleHandlerTest {
	
	private static final String SAMPLE_INPUT_STRING = "{\"foo\": \"bar\"}";
	private static final String EXPECTED_OUTPUT_STRING = "{\"FOO\": \"BAR\"}";
	Context createContext(String apiCall) {
		TestContext ctx = new TestContext();
		ctx.setFunctionName(apiCall);
		return ctx;
	}
	
	@Test
	public void testDeleteScheduleHandler() throws IOException {
		CreateScheduleHandler createHandler = new CreateScheduleHandler();

		CreateScheduleRequest cmr = new CreateScheduleRequest("2012-12-01", "08:00", "2012-12-01",
				"2012-12-23", "10:00", "18:00",  15);

		String createScheduleRequest = new Gson().toJson(cmr);
		String createJsonRequest = new Gson().toJson(new PostRequest(createScheduleRequest));
		InputStream createInput = new ByteArrayInputStream(createJsonRequest.getBytes());
		OutputStream createOutput = new ByteArrayOutputStream();

		createHandler.handleRequest(createInput, createOutput, createContext("createSchedule"));

		DeleteScheduleHandler deleteHandler = new DeleteScheduleHandler();
		DeleteScheduleRequest dmr = new DeleteScheduleRequest("7365");
		String deleteScheduleRequest = new Gson().toJson(dmr);
		String deleteJsonRequest = new Gson().toJson(new PostRequest(deleteScheduleRequest));
		InputStream deleteInput = new ByteArrayInputStream(deleteJsonRequest.getBytes());
		OutputStream deleteOutput = new ByteArrayOutputStream();

		deleteHandler.handleRequest(deleteInput, deleteOutput, createContext("deleteSchedule"));
	}
}
