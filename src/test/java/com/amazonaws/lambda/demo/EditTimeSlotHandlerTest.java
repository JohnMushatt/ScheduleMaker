package com.amazonaws.lambda.demo;

import static org.junit.Assert.assertTrue;

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
public class EditTimeSlotHandlerTest {

    private static final String SAMPLE_INPUT_STRING = "{\"foo\": \"bar\"}";
    private static final String EXPECTED_OUTPUT_STRING = "{\"FOO\": \"BAR\"}";
    Context createContext(String apiCall) {
        TestContext ctx = new TestContext();
        ctx.setFunctionName(apiCall);
        return ctx;
    }
    @Test
    public void testEditTimeSlotHandler() throws IOException {
    	System.out.println("Running testEditTimeSlotHandler");
        EditTimeSlotHandler handler = new EditTimeSlotHandler();
        EditTimeSlotRequest etsr = new EditTimeSlotRequest("c0712e0a3cd14f9880524d7d55c48f67");

        String addRequest = new Gson().toJson(etsr);
        String jsonRequest = new Gson().toJson(new PostRequest(addRequest));



        InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());

        OutputStream output = new ByteArrayOutputStream();
        handler.handleRequest(input, output, createContext("editTimeSlot"));

        PostResponse post = new Gson().fromJson(output.toString(), PostResponse.class);
        EditTimeSlotResponse  resp = new Gson().fromJson(post.body, EditTimeSlotResponse.class);
        assertTrue(resp.isOpen==0 || resp.isOpen==1);
    }
}
