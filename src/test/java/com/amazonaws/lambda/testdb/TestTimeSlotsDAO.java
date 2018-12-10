package com.amazonaws.lambda.testdb;

import static org.junit.Assert.fail;

import org.junit.Test;

import com.amazonaws.lambda.db.TimeSlotsDAO;
import com.amazonaws.lambda.model.TimeSlot;
public class TestTimeSlotsDAO {

	@Test
	public void testCreate() {
		TimeSlotsDAO tsDAO = new TimeSlotsDAO();
		try {
			TimeSlot t = new TimeSlot("testCreateTimeSlotID", 1, "10:00",
					"12:00", 0,
					3, "testCreateTimeSlotSID", "3000-12-31");

			boolean b = tsDAO.addTimeSlot(t);
			System.out.println("Added Time Slot? " +b);

			TimeSlot tn = tsDAO.getTimeSlot("testCreateTimeSlotID");
			System.out.println("Retrieved time slot: " + tn.timeSlotID);
		}
		catch(Exception e) {
			fail("Didn't work: " + e.getMessage());
		}
	}
	@Test
	public void testEdit() {
		testCreate();
		TimeSlotsDAO tsDAO = new TimeSlotsDAO();
		try {
			System.out.println("isOpen status of time slot before edit: "
		+ tsDAO.getTimeSlot("testCreateTimeSlotID").isOpen);

			tsDAO.editTimeSlot("testCreateTimeSlotID");

			System.out.println("isOpen status of time slot after edit: "
					+ tsDAO.getTimeSlot("testCreateTimeSlotID").isOpen);
		}
		catch(Exception e) {
			fail("Didn't work: " + e.getMessage());
		}
	}

}
