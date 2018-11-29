package com.amazonaws.lambda.testdb;

import static org.junit.Assert.fail;

import java.util.UUID;

import org.junit.Test;

import com.amazonaws.lambda.db.SchedulesDAO;
import com.amazonaws.lambda.model.Schedule;

public class TestSchedulesDAO {

	@Test
	public void testFind() {
	    SchedulesDAO cd = new SchedulesDAO();
	    try {
	    	Schedule c = cd.getSchedule("e");
	    	System.out.println("constant " + c.scheduleId);
	    } catch (Exception e) {
	    	fail ("didn't work:" + e.getMessage());
	    }
	}

	@Test
	public void testCreate() {
		SchedulesDAO cd = new SchedulesDAO();
	    try {
	    	// can add it
	    	String id = UUID.randomUUID().toString().substring(0, 20); // no more than 20 because of DB restrictions...
	    	Schedule schedule = new Schedule("e",null,"8","testId",null,null,"8","10","20","testCode");
	    	boolean b = cd.addSchedule(schedule);
	    	System.out.println("add constant: " + b);

	    	// can retrieve it
	    	Schedule c2 = cd.getSchedule(schedule.scheduleId);
	    	System.out.println("C2:" + c2.scheduleId);

	    	// can delete it
	    	//assertTrue (cd.deleteConstant(c2));
	    } catch (Exception e) {
	    	fail ("didn't work:" + e.getMessage());
	    }
	}
}
