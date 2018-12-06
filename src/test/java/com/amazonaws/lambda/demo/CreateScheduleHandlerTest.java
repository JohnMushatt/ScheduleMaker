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
public class CreateScheduleHandlerTest {

    private static final String SAMPLE_INPUT_STRING = "{\"foo\": \"bar\"}";
    private static final String EXPECTED_OUTPUT_STRING = "{\"FOO\": \"BAR\"}";
    Context createContext(String apiCall) {
        TestContext ctx = new TestContext();
        ctx.setFunctionName(apiCall);
        return ctx;
    }
    @Test
    public void testCreateScheduleHandler() throws IOException {
        CreateScheduleHandler handler = new CreateScheduleHandler();

        CreateScheduleRequest csr = new CreateScheduleRequest("2000-10-10"
        		, "02:00", "2010-10-01","2010-10-11 ",
        		"10:00","20:00",20);
        String addRequest = new Gson().toJson(csr);
        String jsonRequest = new Gson().toJson(new PostRequest(addRequest));

        InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
        OutputStream output = new ByteArrayOutputStream();

        handler.handleRequest(input, output, createContext("createSchedule"));

        PostResponse post = new Gson().fromJson(output.toString(), PostResponse.class);
        CreateScheduleResponse resp = new Gson().fromJson(post.body, CreateScheduleResponse.class);
    }
}
