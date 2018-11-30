package com.amazonaws.lambda.demo;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.Time;

import org.junit.Test;

import com.amazonaws.lambda.demo.http.PostRequest;
import com.amazonaws.lambda.demo.http.PostResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;


/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class createScheduleHandlerTest {

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

        CreateScheduleRequest csr = new CreateScheduleRequest(new Date(10, 10, 10)
        		, new Time(8, 0, 0), new Date(10,10,10),new Date(10,10,10),
        		"20:00","10:00",20);
        String addRequest = new Gson().toJson(csr);
        String jsonRequest = new Gson().toJson(new PostRequest(addRequest));

        InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
        OutputStream output = new ByteArrayOutputStream();

        handler.handleRequest(input, output, createContext("add"));

        PostResponse post = new Gson().fromJson(output.toString(), PostResponse.class);
        CreateScheduleResponse resp = new Gson().fromJson(post.body, CreateScheduleResponse.class);
    }
}
