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

public class ReviewScheduleHandlerTest {

    private static final String SAMPLE_INPUT_STRING = "{\"foo\": \"bar\"}";
    private static final String EXPECTED_OUTPUT_STRING = "{\"FOO\": \"BAR\"}";

    Context createContext(String apiCall) {
    	TestContext ctx = new TestContext();
    	ctx.setFunctionName(apiCall);
    	return ctx;
    }
	@Test
	public void testReviewScheduleHandler() throws IOException {
		System.out.println("RUNNING testReviewScheduleHandler");
		ReviewScheduleHandler handler = new ReviewScheduleHandler();

		ReviewScheduleRequest csr = new ReviewScheduleRequest("11e64d2acee242e7b8db2e19ee69256c"); //ScheduleId: 2000-10-10352202:00
		//^^^ used to be 2010-10-01
		String addRequest = new Gson().toJson(csr);
		String jsonRequest = new Gson().toJson(new PostRequest(addRequest));

		InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
		OutputStream output = new ByteArrayOutputStream();

		handler.handleRequest(input, output, createContext("reviewSchedule"));

		PostResponse post = new Gson().fromJson(output.toString(), PostResponse.class);
		ReviewScheduleResponse resp = new Gson().fromJson(post.body, ReviewScheduleResponse.class);
	}

}
