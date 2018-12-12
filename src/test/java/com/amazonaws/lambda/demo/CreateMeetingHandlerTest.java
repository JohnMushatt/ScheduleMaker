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
public class CreateMeetingHandlerTest {

	private static final String SAMPLE_INPUT_STRING = "{\"foo\": \"bar\"}";
	private static final String EXPECTED_OUTPUT_STRING = "{\"FOO\": \"BAR\"}";

	Context createContext(String apiCall) {
		TestContext ctx = new TestContext();
		ctx.setFunctionName(apiCall);
		return ctx;
	}

	@Test
	public void TestCreateMeetingHander() throws IOException {
		System.out.println("RUNNING TestCreateMeetingHandler");
		CreateMeetingHandler handler = new CreateMeetingHandler();

		CreateMeetingRequest cmr = new CreateMeetingRequest("0a47d5001a7a4b9d89a49aa57dbdb511",
				"0037fc38030b4d6997ba5740c53d2f35",
				 "John");

		String meetingRequest = new Gson().toJson(cmr);
		String jsonRequest = new Gson().toJson(new PostRequest(meetingRequest));
		InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
		OutputStream output = new ByteArrayOutputStream();

		handler.handleRequest(input, output, createContext("createMeeting"));
		PostResponse post = new Gson().fromJson(output.toString(), PostResponse.class);
        CreateMeetingResponse resp = new Gson().fromJson(post.body, CreateMeetingResponse.class);
	}
}
