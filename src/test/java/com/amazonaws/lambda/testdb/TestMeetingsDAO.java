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
			System.out.println(m);
		}
		catch (Exception e) {
			fail("didn't work:"+ e.getMessage());
		}
	}
	@Test
	public void testCreate() {
		MeetingsDAO md = new MeetingsDAO();
		try {
			Meeting m = new Meeting("testMeetingID","organizerID","timeslotID","Jordan","asd213","secretCode");
			boolean b = md.addMeeting(m);
			System.out.println("add meeting: " + b);
			Meeting m2 = md.getMeeting("testMeetingID");
			System.out.println("M2:" + m2.meetingID);
		}
		catch (Exception e) {
			fail("didn't work: " + e.getMessage());
		}
	}

}
