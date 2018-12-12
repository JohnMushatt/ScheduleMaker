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
		System.out.println("RUNNING testDeleteMeetingHandler");

		DeleteMeetingHandler deleteHandler = new DeleteMeetingHandler();
		DeleteMeetingRequest dmr = new DeleteMeetingRequest("1a1f8b2af41e4b74b3ba1214790339a5","f81a2ef39189465f9bd6e961930db198");
		String deleteMeetingRequest = new Gson().toJson(dmr);
		String deleteJsonRequest = new Gson().toJson(new PostRequest(deleteMeetingRequest));
		InputStream deleteInput = new ByteArrayInputStream(deleteJsonRequest.getBytes());
		OutputStream deleteOutput = new ByteArrayOutputStream();

		deleteHandler.handleRequest(deleteInput, deleteOutput, createContext("deleteMeeting"));
		PostResponse post = new Gson().fromJson(deleteOutput.toString(), PostResponse.class);
        DeleteMeetingResponse resp = new Gson().fromJson(post.body, DeleteMeetingResponse.class);	}
}
