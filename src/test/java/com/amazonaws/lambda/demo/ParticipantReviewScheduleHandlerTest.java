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

public class ParticipantReviewScheduleHandlerTest {
	
	    private static final String SAMPLE_INPUT_STRING = "{\"foo\": \"bar\"}";
	    private static final String EXPECTED_OUTPUT_STRING = "{\"FOO\": \"BAR\"}";

	    Context createContext(String apiCall) {
	    	TestContext ctx = new TestContext();
	    	ctx.setFunctionName(apiCall);
	    	return ctx;
	    }
		@Test
		public void testParticipantReviewScheduleHandler() throws IOException {
			ParticipantReviewScheduleHandler handler = new ParticipantReviewScheduleHandler();

			ParticipantReviewScheduleRequest csr = new ParticipantReviewScheduleRequest("ebb0c9e1dd4a4ee2ad64a48021490b79"); //ScheduleId: 2000-10-10352202:00 
			String addRequest = new Gson().toJson(csr);
			String jsonRequest = new Gson().toJson(new PostRequest(addRequest));

			InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
			OutputStream output = new ByteArrayOutputStream();

			handler.handleRequest(input, output, createContext("participantReviewSchedule"));

			PostResponse post = new Gson().fromJson(output.toString(), PostResponse.class);
			ParticipantReviewScheduleResponse resp = new Gson().fromJson(post.body, ParticipantReviewScheduleResponse.class);
		}
}

