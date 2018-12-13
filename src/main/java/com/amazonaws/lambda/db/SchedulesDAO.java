package com.amazonaws.lambda.db;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.joda.time.LocalDate;

import com.amazonaws.lambda.model.Meeting;
import com.amazonaws.lambda.model.Schedule;
import com.amazonaws.lambda.model.TimeSlot;

public class SchedulesDAO {
	java.sql.Connection conn;

	/**
	 * Constructor for SchedulesDAO, establishes connection between AWS and RDS
	 */
	public SchedulesDAO() {
		try {
			conn = DatabaseUtil.connect();
		} catch (Exception e) {
			conn = null;
		}
	}
	String findScheduleId(ResultSet resultSet) throws Exception {
		String scheduleId = resultSet.getString("sId");
		return scheduleId;
	}

	String findStartDate(ResultSet resultSet) throws Exception{
		String startingDate = resultSet.getString("startDate");
		return startingDate;
	}

	int findTsDuration(ResultSet resultSet) throws Exception{
		int tsDuration = resultSet.getInt("tsDuration");
		return tsDuration;
	}

	String findStartTime(ResultSet resultSet) throws Exception{
		String startTime = resultSet.getString("startTime");
		return startTime;
	}

	String findEndTime(ResultSet resultSet) throws Exception{
		String endTime = resultSet.getString("endTime");
		return endTime;
	}

	String findStartingDate(ResultSet resultSet) throws Exception{
		String startingDate = resultSet.getString("startDate");
		return startingDate;
	}
	public int getTimeSlotDuration(String secretCode) throws Exception{
		try {
			int tsDuration = 0;
			PreparedStatement tsPs = conn.prepareStatement("SELECT * FROM Schedules WHERE secretCode=?");
			tsPs.setString(1, secretCode);
			ResultSet tsResultSet = tsPs.executeQuery();

			while(tsResultSet.next()) {
				tsDuration = findTsDuration(tsResultSet);
			}

			tsResultSet.close();
			tsPs.close();

			return tsDuration;
		}
		catch (Exception e) {
			throw new Exception("Failed to get ts duration");
		}
	}

	public String getStartTime(String secretCode) throws Exception{
		try {
			String startTime = "startTime??????";
			PreparedStatement startTimePs = conn.prepareStatement("SELECT * FROM Schedules WHERE secretCode=?");
			startTimePs.setString(1, secretCode);
			ResultSet startTimeResultSet = startTimePs.executeQuery();

			while(startTimeResultSet.next()) {
				startTime = findStartTime(startTimeResultSet);
			}

			startTimeResultSet.close();
			startTimePs.close();

			return startTime;
		}
		catch (Exception e) {
			throw new Exception("Failed to get start time");
		}
	}




	public List<TimeSlot> getTimeSlotsInWeek(String secretCode) throws Exception {

        try {

        	//SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        	String startDate = "no date gotten yet";
        	PreparedStatement datePs = conn.prepareStatement("SELECT * FROM Schedules WHERE secretCode=?");
        	datePs.setString(1, secretCode);
        	ResultSet dateResultSet = datePs.executeQuery();

        	while(dateResultSet.next()) {
        		startDate = findStartDate(dateResultSet);
        	}
        	dateResultSet.close();
        	datePs.close();

			String year = startDate.substring(0, 4);
			String month = startDate.substring(5,7);
			String day = startDate.substring(8,10);

			int y = Integer.parseInt(year);
			int m = Integer.parseInt(month);
			int d = Integer.parseInt(day);

			LocalDate thisDate = new LocalDate(y,m,d);
			//Date firstDay = thisDate.
			LocalDate firstDay = new LocalDate(y, m, d+1);
			LocalDate secondDay = new LocalDate(y, m, d+2);
			LocalDate thirdDay = new LocalDate(y, m, d+3);
			LocalDate fourthDay = new LocalDate(y, m, d+4);

			String firstDayString = getCurrentDate(firstDay.getYear(), firstDay.getMonthOfYear(), firstDay.getDayOfMonth());
			String secondDayString = getCurrentDate(secondDay.getYear(), secondDay.getMonthOfYear(), secondDay.getDayOfMonth());
			String thirdDayString = getCurrentDate(thirdDay.getYear(), thirdDay.getMonthOfYear(), thirdDay.getDayOfMonth());
			String fourthDayString = getCurrentDate(fourthDay.getYear(), fourthDay.getMonthOfYear(), fourthDay.getDayOfMonth());

			List<TimeSlot> timeslots = new ArrayList<>();
			TimeSlotsDAO dao = new TimeSlotsDAO();
			String scheduleId = "havent checked yet";

			//search for the schedule id for the schedule with the matching secret code
			PreparedStatement schedulePs = conn.prepareStatement("SELECT * FROM Schedules WHERE secretCode=?;");
			schedulePs.setString(1, secretCode);
			ResultSet scheduleResultSet = schedulePs.executeQuery();

			while (scheduleResultSet.next()) {
				scheduleId = findScheduleId(scheduleResultSet);
            }
			scheduleResultSet.close();
			schedulePs.close();

	        //Now search the Timeslot list for timeslots containing the same schedule id and date
	        PreparedStatement ps = conn.prepareStatement("SELECT * FROM TimeSlots WHERE sId=? AND date=?;");

	        ps.setString(1, scheduleId);
	        ps.setString(2, startDate);
	        ResultSet resultSet = ps.executeQuery();

	        while(resultSet.next()) {
	        	TimeSlot timeslot = dao.generateTimeSlot(resultSet);
	        	timeslots.add(timeslot);
	        }
	        resultSet.close();
	        ps.close();

	        ps = conn.prepareStatement("SELECT * FROM TimeSlots WHERE sId=? AND date=?;");
	        ps.setString(1, scheduleId);
	        ps.setString(2, firstDayString);
	        resultSet = ps.executeQuery();

	        while(resultSet.next()) {
	        	TimeSlot timeslot = dao.generateTimeSlot(resultSet);
	        	timeslots.add(timeslot);
	        }

	        resultSet.close();
	        ps.close();

	        ps = conn.prepareStatement("SELECT * FROM TimeSlots WHERE sId=? AND date=?;");
	        ps.setString(1, scheduleId);
	        ps.setString(2, secondDayString);
	        resultSet = ps.executeQuery();

	        while(resultSet.next()) {
	        	TimeSlot timeslot = dao.generateTimeSlot(resultSet);
	        	timeslots.add(timeslot);
	        }
	        resultSet.close();
	        ps.close();

	        ps = conn.prepareStatement("SELECT * FROM TimeSlots WHERE sId=? AND date=?;");
	        ps.setString(1, scheduleId);
	        ps.setString(2, thirdDayString);
	        resultSet = ps.executeQuery();

	        while(resultSet.next()) {
	        	TimeSlot timeslot = dao.generateTimeSlot(resultSet);
	        	timeslots.add(timeslot);
	        }
	        resultSet.close();
	        ps.close();

	        ps = conn.prepareStatement("SELECT * FROM TimeSlots WHERE sId=? AND date=?;");
	        ps.setString(1, scheduleId);
	        ps.setString(2, fourthDayString);
	        resultSet = ps.executeQuery();

	        while(resultSet.next()) {
	        	TimeSlot timeslot = dao.generateTimeSlot(resultSet);
	        	timeslots.add(timeslot);
	        }
	        resultSet.close();
	        ps.close();

            return timeslots;

        } catch (Exception e) {
        	throw new Exception("Failed to review schedule");
        }

    }
	/**
	 * Retrieves a schedules from the given id
	 *
	 * @param scheduleId
	 *            String id to search for in the db
	 * @return Schedule from the db
	 * @throws Exception
	 */
	public Schedule getSchedule(String scheduleId) throws Exception {
		try {
			Schedule schedule = null;

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Schedules WHERE sId=?;");
			ps.setString(1, scheduleId);

			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				schedule = generateSchedule(resultSet);
			}
			resultSet.close();
			ps.close();
			return schedule;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed in getting schedule: " + e.getMessage());
		}

	}

	/**
	 * Delete schedule with the given id
	 *
	 * @param scheduleId
	 *            String id of the schedule
	 * @return True if successfully deleted, false if not
	 * @throws Exception
	 */
	public boolean deleteSchedule(String secretCode) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM Schedules WHERE secretCode = ?;");
			ps.setString(1, secretCode);

			int numAffected = ps.executeUpdate();
			ps.close();
			return (numAffected == 1);
		} catch (Exception e) {
			throw new Exception("Failed to delete schedule: " + e.getMessage());

		}
	}

	/**
	 * Updates the schedule such as start date end date
	 *
	 * @param schedule
	 *            Schedule to update
	 * @return
	 * @throws Exception
	 */
	public boolean updateSchedule(String scheduleID) throws Exception {
		try {
			Schedule s = getSchedule(scheduleID);

			PreparedStatement ps = conn.prepareStatement("UPDATE Schedules SET (startDate,endDate) values(?,?);");
			ps.setString(1, s.startDate);
			ps.setString(2, s.endDate);

			int numAffected = ps.executeUpdate();

			ps.close();
			return (numAffected == 1);

		} catch (Exception e) {
			throw new Exception("Failed to update report: " + e.getMessage());
		}
	}

	/**
	 * Generates a schedule from the db info
	 *
	 * @param resultSet
	 *            Databse info
	 * @return Schedule with info from the db
	 * @throws Exception
	 */
	private Schedule generateSchedule(ResultSet resultSet) throws Exception {
		String sId = resultSet.getString("sId");
		String initDate = resultSet.getString("initDate");
		String initTime = resultSet.getString("initTime");
		String orgId = resultSet.getString("orgId");
		String startDate = resultSet.getString("startDate");
		String endDate = resultSet.getString("endDate");
		String startTime = resultSet.getString("startTime");
		String endTime = resultSet.getString("endTime");
		int timeSlotDuration = resultSet.getInt("tsDuration");
		String secretCode = resultSet.getString("secretCode");
		String accessCode = resultSet.getString("accessCode");
		String name = resultSet.getString("name");
		return new Schedule(sId, initDate, initTime, orgId, startDate, endDate, startTime, endTime, timeSlotDuration,
				secretCode, accessCode, name);
	}

	/**
	 * Add schedule to db and fill TimeSlots db with time slots
	 *
	 * @param schedule
	 *            Schedule to add to Schedules table
	 * @return True if successful, false if bad schedule
	 * @throws Exception
	 */
	public boolean addSchedule(Schedule schedule) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Schedules WHERE sId=?;");
			ps.setString(1, schedule.scheduleId);

			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Schedule s = generateSchedule(resultSet);
				resultSet.close();
				return false;
			}
			ps = conn.prepareStatement(
					"INSERT INTO Schedules (sId,initDate,initTime,orgId,startDate,endDate,startTime,endTime,"
							+ "tsDuration,secretCode,accessCode,name) values(?,?,?,?,?,?,?,?,?,?,?,?);");
			ps.setString(1, schedule.scheduleId);
			ps.setString(2, schedule.initialDate);
			ps.setString(3, schedule.initialTime);
			ps.setString(4, schedule.organizerId);
			ps.setString(5, schedule.startDate);
			ps.setString(6, schedule.endDate);
			ps.setString(7, schedule.startTime);
			ps.setString(8, schedule.endTime);
			ps.setInt(9, schedule.timeslotDuration);
			ps.setString(10, schedule.secretCode);
			ps.setString(11, schedule.accessCode);
			ps.setString(12, schedule.name);
			ps.execute();

			System.out.println("Succesfully added schedule: " + schedule.scheduleId);

			return true;
		} catch (Exception e) {
			throw new Exception("Failed to insert schedule: " + e.getMessage());
		}
	}

	/**
	 * Fill TimeSlot table after a new schedule has been created
	 *
	 * @param schedule
	 *            Schedule to add time slots for
	 * @return True if successfully added time slots
	 * @throws Exception
	 */
	public boolean addTimeSlots(Schedule schedule) throws Exception {
		try {
			// Start date
			String startDate = schedule.startDate;
			// End date
			String endDate = schedule.endDate;
			// Start time
			String startTime = schedule.startTime;
			// End time
			String endTime = schedule.endTime;
			// Time slot duration
			int tsDuration = schedule.timeslotDuration;
			// Parse the values of both dates and time
			Integer endYearVal = new Integer(endDate.substring(0, 4));
			Integer endMonthVal = new Integer(endDate.substring(5, 7));
			Integer endDayVal = new Integer(endDate.substring(8, 10));
			Integer startYearVal = new Integer(startDate.substring(0, 4));
			Integer startMonthVal = new Integer(startDate.substring(5, 7));
			Integer startDayVal = new Integer(startDate.substring(8));
			Integer startHour = new Integer(startTime.substring(0, 2));
			Integer startMin = new Integer(startTime.substring(3));
			Integer endHour = new Integer(endTime.substring(0, 2));
			Integer endMin = new Integer(endTime.substring(3));
			// Calculator difference between 2 dates
			int totalDays = calcDays(endYearVal, endMonthVal, endDayVal)
					- calcDays(startYearVal, startMonthVal, startDayVal);
			int totalTimeSlots = 0;

			// Calculate # of time slots
			if (totalDays < 0) {
				return false;
			} else if (totalDays == 0) {
				totalTimeSlots = ((endHour * 60 + endMin) - (startHour * 60 + startMin)) / tsDuration;
			} else {
				totalTimeSlots = totalDays * ((endHour * 60 + endMin) - (startHour * 60 + startMin)) / tsDuration;
			}
			/*
			 * Returns the day of the week represented by this date. The returned value (0 =
			 * Sunday, 1 = Monday, 2 = Tuesday, 3 = Wednesday, 4 =Thursday, 5 = Friday, 6 =
			 * Saturday) represents the day of the week that contains or begins with the
			 * instant in time represented by this Date object, as interpreted in the local
			 * time zone.
			 */
			LocalDate currentLocalDate = new LocalDate(startYearVal,startMonthVal,startDayVal);
			Random r = new Random();
			String currentTime = startTime;
			TimeSlotsDAO tsDAO = new TimeSlotsDAO();
			Time currentTimeObject = new Time(startHour, startMin, 00);
			Time endTimeObject = new Time(endHour, endMin, 00);
			LocalDate endLocalDate = new LocalDate(endYearVal,endMonthVal,endDayVal);
			// Build time slots for schedule
			int day = 1;
			// If the current date is before the schedule's end date
			while (currentLocalDate.compareTo(endLocalDate) <= 0) {
				// If the current time is before the day's ending time
				while (currentTimeObject.compareTo(endTimeObject) < 0) {
					// Get random id;

					// Get week day of the date
					int weekDay = currentLocalDate.getDayOfWeek();
					// Check if it not satuday or sunday
					while (weekDay == 6 || weekDay == 7) {
						// if it is increment, Date object takes care of updating the date correctly
						currentLocalDate = currentLocalDate.plusDays(1);
						//currentDateObject.setDate(currentDateObject.getDate() + 1);
						// Update weekDay
						weekDay = currentLocalDate.getDayOfWeek();
					}

					String timeSlotID = UUID.randomUUID().toString();
					timeSlotID = timeSlotID.substring(0, 8) + timeSlotID.substring(9, 13) + timeSlotID.substring(14, 18)
					+ timeSlotID.substring(19, 23) + timeSlotID.substring(24);
					String nextTime = getNextTime(currentTime, startTime, endTime, tsDuration);
					TimeSlot currentTimeSlot = new TimeSlot(timeSlotID, 1, currentTime, nextTime, 0, weekDay,
							schedule.scheduleId, getCurrentDate(currentLocalDate.getYear(),
									currentLocalDate.getMonthOfYear(), currentLocalDate.getDayOfMonth()));

					tsDAO.addTimeSlot(currentTimeSlot);
					//System.out.println(currentLocalDate.dayOfWeek().getAsText());

					currentTime = nextTime;
					currentTimeObject.setMinutes(currentTimeObject.getMinutes() + tsDuration);
				}
				currentTimeObject = new Time(startHour, startMin, 0);
				currentLocalDate = currentLocalDate.plusDays(1);
				currentTime = startTime;
			}

		} catch (Exception e) {
			throw new Exception("Failed to insert schedule: " + e.getMessage());
		}
		return false;
	}

	/**
	 * Returns String format of the next time
	 *
	 * @param time
	 *            String format of curreunt time
	 * @param startTime
	 *            String format of the start time of the day
	 * @param endTime
	 *            String format of the end time of the day
	 * @param tsDuration
	 *            Int duration of each time slot
	 * @return String format of the next time
	 */
	private static String getNextTime(String time, String startTime, String endTime, int tsDuration) {
		Integer currentHr = new Integer(time.substring(0, 2));
		Integer currentMin = new Integer(time.substring(3));
		Integer startHr = new Integer(startTime.substring(0, 2));
		Integer startMin = new Integer(startTime.substring(3));
		Integer endHr = new Integer(endTime.substring(0, 2));
		Integer endMin = new Integer(endTime.substring(3));
		// If new day
		if (currentHr == endHr && currentMin + tsDuration == 60) {
			currentHr = startHr;
			currentMin = 00;
			return "" + currentHr + ":" + "00";
		}
		// If new hr
		else if (currentMin + tsDuration == 60) {
			currentHr++;
			String hour;
			if(currentHr<10) {
				hour = "0"+currentHr;
			}
			else {
				hour = ""+currentHr;
			}
			currentMin = 00;
			return "" + hour + ":" + "00";

		}
		// If new min
		else {
			String hour;
			if(currentHr<10) {
				hour = "0"+currentHr;
			}
			else {
				hour = ""+currentHr;
			}
			currentMin += tsDuration;
			return "" + hour + ":" + currentMin;

		}
	}
	public boolean deleteSchedules(int days) throws Exception{
		try {
			//Setup query
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Schedules;");
			//Execute and cache
			ResultSet schedules = ps.executeQuery();
			//Create array list of scheduleIDs to delete
			ArrayList<String> deletableSchedules = new ArrayList<>();
			//While there are more rows to process
			while(schedules.next()) {
				//Generate schedule from row info
				Schedule s = generateSchedule(schedules);
				//Check if the current Schedule is deletable
				if(isOlder(s.initialDate,days)) {
					//Add it
					deletableSchedules.add(s.scheduleId);
				}
			}
			int numAffected =0;
			ps = conn.prepareStatement("DELETE FROM Schedules WHERE sId=?;");
			for(int i =0; i < deletableSchedules.size();i++) {
				ps.setString(1, deletableSchedules.get(i));
				numAffected +=ps.executeUpdate();
				System.out.println("Deleted Schedule with id: " + deletableSchedules.get(i));
			}
			System.out.println("Deleted " + numAffected + " schedule(s)");
			return numAffected==deletableSchedules.size();


		}
		catch(Exception e) {
			throw new Exception("Failed to insert schedule: " + e.getMessage());
		}
	}
	public boolean isOlder(String d1, int days) {
		Integer y = new Integer(d1.substring(0, 4));
		Integer m = new Integer(d1.substring(5,7));
		Integer d = new Integer(d1.substring(8));
		LocalDate currentDate = new LocalDate();
		LocalDate cutOff = currentDate.minusDays(days);
		LocalDate scheduleDate = new LocalDate(y,m,d);
		if(scheduleDate.compareTo(cutOff)<0) {
			return true;
		}
		return false;
	}
	/**
	 * Convert integer form of next date to string for db and object
	 *
	 * @param y
	 *            Year value
	 * @param m
	 *            Month value
	 * @param d
	 *            Day value
	 * @return String form of next date
	 */
	private static String getNextDate(int y, int m, int d) {
		String date = null;

		// if december 31st
		if (m == 12 && d == 31) {
			date = "" + (y + 1) + "-" + "01" + "-" + "01";
		}
		// if end of normal month
		else if ((m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) && d == 31) {
			date = "" + y + "-" + (m + 1) + "-" + "01";
		}
		// Feb
		else if ((m == 2) && d == 28) {
			date = "" + y + "-" + "03" + "-" + "01";

		}
		//
		else {
			date = "" + y + "-" + m + "-" + "01";

		}
		return date;
	}


	public String getScheduleId(String secretCode) throws Exception{
		try {
			String sId = "not gotten yet";
			PreparedStatement sIdPs = conn.prepareStatement("SELECT * FROM Schedules WHERE secretCode=?");
			sIdPs.setString(1, secretCode);
			ResultSet sIdResultSet = sIdPs.executeQuery();

			while(sIdResultSet.next()) {
				sId = findScheduleId(sIdResultSet);
			}

			sIdResultSet.close();
			sIdPs.close();

			return sId;
		}
		catch (Exception e) {
			throw new Exception("Failed to get schedule Id");
		}
	}


	public String getEndTime(String secretCode) throws Exception{
		try {
			String endTime = "endTime??????";
			PreparedStatement endTimePs = conn.prepareStatement("SELECT * FROM Schedules WHERE secretCode=?");
			endTimePs.setString(1, secretCode);
			ResultSet endTimeResultSet = endTimePs.executeQuery();

			while(endTimeResultSet.next()) {
				endTime = findEndTime(endTimeResultSet);
			}

			endTimeResultSet.close();
			endTimePs.close();

			return endTime;
		}
		catch (Exception e) {
			throw new Exception("Failed to get end time");
		}
	}

	public String getStartingDateOfWeek(String secretCode) throws Exception{
		try {
			String startDate = "startDate??????";
			PreparedStatement startDatePs = conn.prepareStatement("SELECT * FROM Schedules WHERE secretCode=?");
			startDatePs.setString(1, secretCode);
			ResultSet startDateResultSet = startDatePs.executeQuery();

			while(startDateResultSet.next()) {
				startDate = findStartingDate(startDateResultSet);
			}

			startDateResultSet.close();
			startDatePs.close();

			return startDate;
		}
		catch (Exception e) {
			throw new Exception("Failed to get start date of the week");
		}
	}

	public List<Meeting> getMeetingsInWeek(List<TimeSlot> times, String sId) throws Exception{
		try {

			List<Meeting> meetings = new ArrayList<>();
			List<String> timeSlotIds = new ArrayList<>();
			MeetingsDAO dao = new MeetingsDAO();

			for(TimeSlot ts: times) {
				timeSlotIds.add(ts.timeSlotID);
			}

			for(String timeSlotID: timeSlotIds) {
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM Meetings WHERE sId=? AND tsId=?;");

		        ps.setString(1, sId);
		        ps.setString(2, timeSlotID);
		        ResultSet resultSet = ps.executeQuery();

		        while(resultSet.next()) {
		        	Meeting meeting = dao.generateMeeting(resultSet);
		        	meetings.add(meeting);
		        }
		        resultSet.close();
		        ps.close();
			}

			return meetings;
		}
		catch (Exception e) {
			throw new Exception("Failed to get the meetings in a week");
		}
	}



	/**
	 * Get string form of current date
	 *
	 * @param y
	 *            Year value
	 * @param m
	 *            Month value
	 * @param d
	 *            Day value
	 * @return String form of current date
	 */
	private static String getCurrentDate(int y, int m, int d) {
		String date = "";
		String month = "";
		String day = "";
		if (m < 10) {
			month = "0" + m;
		} else {
			month = "" + m;
		}
		if (d < 10) {
			day = "0" + d;
		} else {
			day = "" + d;
		}
		date = y + "-" + month + "-" + day;
		return date;
	}

	/**
	 * Calculates the position of the year in Gregorian calendar form e.g. will
	 * return x where x is the position in a given year
	 *
	 * @param y
	 *            Year value
	 * @param m
	 *            Month value
	 * @param d
	 *            Day value
	 * @return Position in given year
	 */
	private static int calcDays(int y, int m, int d) {
		int days = 0;
		m = (m + 9) % 12;
		y = y - m / 10;
		days = 365 * y + y / 4 - y / 100 + y / 400 + (m * 306 + 5) / 10 + (d - 1);
		return days;
	}
	public List<TimeSlot> participantGetTimeSlotsInWeek(String accessCode) throws Exception {

        try {

        	String startDate = "no date gotten yet";
        	PreparedStatement datePs = conn.prepareStatement("SELECT * FROM Schedules WHERE accessCode=?");
        	datePs.setString(1, accessCode);
        	ResultSet dateResultSet = datePs.executeQuery();

        	while(dateResultSet.next()) {
        		startDate = findStartDate(dateResultSet);
        	}
        	dateResultSet.close();
        	datePs.close();

			String year = startDate.substring(0, 4);
			String month = startDate.substring(5,7);
			String day = startDate.substring(8,10);

			int y = Integer.parseInt(year);
			int m = Integer.parseInt(month);
			int d = Integer.parseInt(day);

			/*
			Calendar calFirst = Calendar.getInstance();
        	calFirst.set(y, m, d);
        	Calendar calSecond = Calendar.getInstance();
        	calSecond.set(y, m, d+1);
        	Calendar calThird = Calendar.getInstance();
        	calThird.set(y, m, d+2);
        	Calendar calFourth = Calendar.getInstance();
        	calFourth.set(y, m, d+3);
        	Calendar calFifth = Calendar.getInstance();
        	calFifth.set(y, m, d+4);

        	System.out.println(calFirst.get(Calendar.YEAR) + "-" + calFirst.get(Calendar.MONTH) + "-" + calFirst.get(Calendar.DATE) );
        	String firstDayString = getCurrentDate(calFirst.get(Calendar.YEAR), calFirst.get(Calendar.MONTH), calFirst.get(Calendar.DATE));
        	String secondDayString = getCurrentDate(calSecond.get(Calendar.YEAR), calSecond.get(Calendar.MONTH), calSecond.get(Calendar.DATE));
        	String thirdDayString = getCurrentDate(calThird.get(Calendar.YEAR), calThird.get(Calendar.MONTH), calThird.get(Calendar.DATE));
        	String fourthDayString = getCurrentDate(calFourth.get(Calendar.YEAR), calFourth.get(Calendar.MONTH), calFourth.get(Calendar.DATE));
        	String fifthDayString = getCurrentDate(calFifth.get(Calendar.YEAR), calFifth.get(Calendar.MONTH), calFifth.get(Calendar.DATE));
			*/

			LocalDate firstDay = new LocalDate(y, m, d);
			//Date firstDay = thisDate.
			LocalDate secondDay = new LocalDate(y, m, d+1);
			LocalDate thirdDay = new LocalDate(y, m, d+2);
			LocalDate fourthDay = new LocalDate(y, m, d+3);
			LocalDate fifthDay = new LocalDate(y, m, d+4);

			String firstDayString = getCurrentDate(firstDay.getYear(), firstDay.getMonthOfYear(), firstDay.getDayOfMonth());
			String secondDayString = getCurrentDate(secondDay.getYear(), secondDay.getMonthOfYear(), secondDay.getDayOfMonth());
			String thirdDayString = getCurrentDate(thirdDay.getYear(), thirdDay.getMonthOfYear(), thirdDay.getDayOfMonth());
			String fourthDayString = getCurrentDate(fourthDay.getYear(), fourthDay.getMonthOfYear(), fourthDay.getDayOfMonth());
			String fifthDayString = getCurrentDate(fifthDay.getYear(), fifthDay.getMonthOfYear(), fifthDay.getDayOfMonth());


			List<TimeSlot> timeslots = new ArrayList<>();
			TimeSlotsDAO dao = new TimeSlotsDAO();
			String scheduleId = "havent checked yet";

			//search for the schedule id for the schedule with the matching secret code
			PreparedStatement schedulePs = conn.prepareStatement("SELECT * FROM Schedules WHERE accessCode=?;");
			schedulePs.setString(1, accessCode);
			ResultSet scheduleResultSet = schedulePs.executeQuery();

			while (scheduleResultSet.next()) {
				scheduleId = findScheduleId(scheduleResultSet);
            }
			scheduleResultSet.close();
			schedulePs.close();

	        //Now search the Timeslot list for timeslots containing the same schedule id and date
	        PreparedStatement ps = conn.prepareStatement("SELECT * FROM TimeSlots WHERE sId=? AND date=?;");

	        ps.setString(1, scheduleId);
	        ps.setString(2, firstDayString); //used to be startDate
	        ResultSet resultSet = ps.executeQuery();

	        while(resultSet.next()) {
	        	TimeSlot timeslot = dao.generateTimeSlot(resultSet);
	        	timeslots.add(timeslot);
	        }
	        resultSet.close();
	        ps.close();

	        ps = conn.prepareStatement("SELECT * FROM TimeSlots WHERE sId=? AND date=?;");
	        ps.setString(1, scheduleId);
	        ps.setString(2, secondDayString);
	        resultSet = ps.executeQuery();

	        while(resultSet.next()) {
	        	TimeSlot timeslot = dao.generateTimeSlot(resultSet);
	        	timeslots.add(timeslot);
	        }

	        resultSet.close();
	        ps.close();

	        ps = conn.prepareStatement("SELECT * FROM TimeSlots WHERE sId=? AND date=?;");
	        ps.setString(1, scheduleId);
	        ps.setString(2, thirdDayString);
	        resultSet = ps.executeQuery();

	        while(resultSet.next()) {
	        	TimeSlot timeslot = dao.generateTimeSlot(resultSet);
	        	timeslots.add(timeslot);
	        }
	        resultSet.close();
	        ps.close();

	        ps = conn.prepareStatement("SELECT * FROM TimeSlots WHERE sId=? AND date=?;");
	        ps.setString(1, scheduleId);
	        ps.setString(2, fourthDayString);
	        resultSet = ps.executeQuery();

	        while(resultSet.next()) {
	        	TimeSlot timeslot = dao.generateTimeSlot(resultSet);
	        	timeslots.add(timeslot);
	        }
	        resultSet.close();
	        ps.close();

	        ps = conn.prepareStatement("SELECT * FROM TimeSlots WHERE sId=? AND date=?;");
	        ps.setString(1, scheduleId);
	        ps.setString(2, fifthDayString);
	        resultSet = ps.executeQuery();

	        while(resultSet.next()) {
	        	TimeSlot timeslot = dao.generateTimeSlot(resultSet);
	        	timeslots.add(timeslot);
	        }
	        resultSet.close();
	        ps.close();

            return timeslots;

        } catch (Exception e) {
        	throw new Exception("Failed to review schedule");
        }

    }

	public int participantGetTimeSlotDuration(String accessCode) throws Exception{
		try {
			int tsDuration = 0;
			PreparedStatement tsPs = conn.prepareStatement("SELECT * FROM Schedules WHERE accessCode=?");
			tsPs.setString(1, accessCode);
			ResultSet tsResultSet = tsPs.executeQuery();

			while(tsResultSet.next()) {
				tsDuration = findTsDuration(tsResultSet);
			}

			tsResultSet.close();
			tsPs.close();

			return tsDuration;
		}
		catch (Exception e) {
			throw new Exception("Failed to get ts duration");
		}
	}

	public String participantGetScheduleId(String accessCode) throws Exception{
		try {
			String sId = "not gotten yet";
			PreparedStatement sIdPs = conn.prepareStatement("SELECT * FROM Schedules WHERE accessCode=?");
			sIdPs.setString(1, accessCode);
			ResultSet sIdResultSet = sIdPs.executeQuery();

			while(sIdResultSet.next()) {
				sId = findScheduleId(sIdResultSet);
			}

			sIdResultSet.close();
			sIdPs.close();

			return sId;
		}
		catch (Exception e) {
			throw new Exception("Failed to get schedule Id");
		}
	}

	public String participantGetStartTime(String accessCode) throws Exception{
		try {
			String startTime = "startTime??????";
			PreparedStatement startTimePs = conn.prepareStatement("SELECT * FROM Schedules WHERE accessCode=?");
			startTimePs.setString(1, accessCode);
			ResultSet startTimeResultSet = startTimePs.executeQuery();

			while(startTimeResultSet.next()) {
				startTime = findStartTime(startTimeResultSet);
			}

			startTimeResultSet.close();
			startTimePs.close();

			return startTime;
		}
		catch (Exception e) {
			throw new Exception("Failed to get start time");
		}
	}

	public String participantGetEndTime(String accessCode) throws Exception{
		try {
			String endTime = "endTime??????";
			PreparedStatement endTimePs = conn.prepareStatement("SELECT * FROM Schedules WHERE accessCode=?");
			endTimePs.setString(1, accessCode);
			ResultSet endTimeResultSet = endTimePs.executeQuery();

			while(endTimeResultSet.next()) {
				endTime = findEndTime(endTimeResultSet);
			}

			endTimeResultSet.close();
			endTimePs.close();

			return endTime;
		}
		catch (Exception e) {
			throw new Exception("Failed to get end time");
		}
	}

	public String participantGetStartingDateOfWeek(String accessCode) throws Exception{
		try {
			String startDate = "startDate??????";
			PreparedStatement startDatePs = conn.prepareStatement("SELECT * FROM Schedules WHERE accessCode=?");
			startDatePs.setString(1, accessCode);
			ResultSet startDateResultSet = startDatePs.executeQuery();

			while(startDateResultSet.next()) {
				startDate = findStartingDate(startDateResultSet);
			}

			startDateResultSet.close();
			startDatePs.close();

			return startDate;
		}
		catch (Exception e) {
			throw new Exception("Failed to get start date of the week");
		}
	}
}


