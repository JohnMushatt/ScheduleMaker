package com.amazonaws.lambda.demo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.JsonObject;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class LambdaFunctionHandlerTest {
	Context createContext(String apiCall) {
		TestContext ctx = new TestContext();
		ctx.setFunctionName(apiCall);
		return ctx;
	}

    @Test
    public void testCreateCalender() throws IOException {
        CreateScheduleHandler handler = new CreateScheduleHandler();
        JsonObject test = new JsonObject();
        String initDate = "2020-10-10";
        String initTime = "06:21:22";
        String startDate = "2020-10-10";
        String endDate = "2020-10-11";
        String startTime = "08:00";
        String endTime = "11:00";
        int tsDuration = 20;
        test.addProperty("initDate", initDate);
        test.addProperty("initTime", initTime);
        test.addProperty("startDate", startDate);
        test.addProperty("endDate", endDate);
        test.addProperty("startTime", startTime);
        test.addProperty("endTime", endTime);
        test.addProperty("tsDuration", tsDuration);
        JsonObject body = new JsonObject();
        body.addProperty("body", test.toString());

        String jsonRequest = body.toString();

        InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());

        OutputStream output = new ByteArrayOutputStream();


        handler.handleRequest(input, output, createContext(""));
    }
}
