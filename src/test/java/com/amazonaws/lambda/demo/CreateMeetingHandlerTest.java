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

		CreateMeetingRequest cmr = new CreateMeetingRequest("8ea7a3625d00403582a3b1ec59832899", "a03ee63cb6c14989894fddfe9dcf763a", "42c8335f42ec4f7eae3717282cd4626c",
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
