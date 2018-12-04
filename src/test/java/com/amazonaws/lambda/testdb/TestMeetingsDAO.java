package com.amazonaws.lambda.testdb;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.amazonaws.lambda.db.MeetingsDAO;
import com.amazonaws.lambda.model.Meeting;
public class TestMeetingsDAO {

	@Test
	public void testFind() {
		MeetingsDAO md = new MeetingsDAO();

		try {
			Meeting m = md.getMeeting("testMeetingID");
		}
		catch (Exception e) {
			fail("didn't work:"+ e.getMessage());
		}
	}

}
