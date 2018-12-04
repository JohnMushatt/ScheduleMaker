package com.amazonaws.lambda.demo;

import java.io.IOException;

import org.junit.Test;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class DeleteMeetingHandlerTest {

    private static final String SAMPLE_INPUT_STRING = "{\"foo\": \"bar\"}";
    private static final String EXPECTED_OUTPUT_STRING = "{\"FOO\": \"BAR\"}";

    @Test
    public void testDeleteMeetingHandler() throws IOException {
       DeleteMeetingHandler handler = new DeleteMeetingHandler();
       DeleteMeetingReqeust cmr = new DeleteMeetingRequest(String sID, String orgID,
       		String tID,String pID,String pName)
       DeleteMeetingRequest dmr = new DeleteMeetingRequest("deleteTestID");
    }
}
