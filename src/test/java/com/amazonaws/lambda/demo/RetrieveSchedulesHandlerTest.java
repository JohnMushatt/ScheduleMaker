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
public class RetrieveSchedulesHandlerTest {

    private static final String SAMPLE_INPUT_STRING = "{\"foo\": \"bar\"}";
    private static final String EXPECTED_OUTPUT_STRING = "{\"FOO\": \"BAR\"}";
    Context createContext(String apiCall) {
        TestContext ctx = new TestContext();
        ctx.setFunctionName(apiCall);
        return ctx;
    }
    @Test
    public void testRetrieveSchedules() throws IOException {
    	System.out.println("RUNNING testRetrieveSchedules");
        RetrieveSchedulesHandler handler = new RetrieveSchedulesHandler();
        RetrieveSchedulesRequest rsr = new RetrieveSchedulesRequest(1);

        String retrieveRequest = new Gson().toJson(rsr);
        String jsonRequest = new Gson().toJson(new PostRequest(retrieveRequest));
        InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
        OutputStream output = new ByteArrayOutputStream();

        handler.handleRequest(input, output, createContext("RetrieveSchedules"));
        PostResponse post = new Gson().fromJson(output.toString(),PostResponse.class);
        RetrieveSchedulesResponse resp = new Gson().fromJson(post.body, RetrieveSchedulesResponse.class);
    }
}
