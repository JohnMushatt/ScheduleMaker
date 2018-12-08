package com.amazonaws.lambda.db;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.amazonaws.lambda.model.Schedule;
import com.amazonaws.lambda.model.TimeSlot;

public class SchedulesDAO {
	java.sql.Connection conn;

	public SchedulesDAO() {
		try {
			conn = DatabaseUtil.connect();
		} catch (Exception e) {
			conn = null;
		}
	}
	/**
	 *
	 * @param scheduleId
	 * @return
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

	public boolean deleteSchedule(String scheduleId) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM Schedules WHERE sId = ?;");
			ps.setString(1, scheduleId);

			int numAffected = ps.executeUpdate();
			ps.close();
			return (numAffected == 1);
		} catch (Exception e) {
			throw new Exception("Failed to delete schedule: " + e.getMessage());

		}
	}

	public boolean updateSchedule(Schedule schedule) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE Schedules SET (startDate,endDate) values(?,?);");
			ps.setString(1, schedule.startDate);
			ps.setString(2, schedule.endDate);

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
		return new Schedule(sId, initDate, initTime, orgId, startDate, endDate, startTime, endTime, timeSlotDuration,
				secretCode);
	}

	/**
	 * Add schedule to db and fill TimeSlots db with time slots
	 *
	 * @param 	schedule	Schedule to add to Schedules table
	 * @return 				True if successful, false if bad schedule
	 * @throws 	Exception
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
							+ "tsDuration,secretCode) values(?,?,?,?,?,?,?,?,?,?);");
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
			ps.execute();

			System.out.println("Succesfully added schedule: " + schedule.scheduleId);

			return true;
		} catch (Exception e) {
			throw new Exception("Failed to insert schedule: " + e.getMessage());
		}
	}

	/**
	 * Fill TimeSlot table after a new schedule has been created
	 * @param schedule Schedule to add time slots for
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
			Integer endDayVal = new Integer(endDate.substring(8,10));
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
			Date currentDateObject = new Date(startYearVal, startMonthVal, startDayVal);
			Random r = new Random();
			String currentTime = startTime;
			TimeSlotsDAO tsDAO = new TimeSlotsDAO();
			Time currentTimeObject = new Time(startHour, startMin, 00);
			Time endTimeObject = new Time(endHour, endMin, 00);
			Date endDateObject = new Date(endYearVal, endMonthVal, endDayVal);
			// Build time slots for schedule
			int day=1;
			String id = schedule.scheduleId;
			// If the current date is before the schedule's end date
			while (currentDateObject.compareTo(endDateObject) <= 0) {
				// If the current time is before the day's ending time
				while (currentTimeObject.compareTo(endTimeObject) < 0) {
					// Get random id;
					id= schedule.scheduleId+currentDateObject.getDate();

					// Get week day of the date
					int weekDay = currentDateObject.getDay();
					// Check if it not satuday or sunday
					while (weekDay == 0 || weekDay == 6) {
						// if it is increment, Date object takes care of updating the date correctly
						currentDateObject.setDate(currentDateObject.getDay() + 1);
						// Update weekDay
						weekDay = currentDateObject.getDay();
					}
					String timeSlotID = id + currentTimeObject.toLocalTime().toString();
					String nextTime = getNextTime(currentTime, startTime, endTime, tsDuration);
					TimeSlot currentTimeSlot = new TimeSlot(timeSlotID, 1, currentTime, nextTime, 0, weekDay,
							schedule.scheduleId, getCurrentDate(currentDateObject.getYear(),
									currentDateObject.getMonth(), currentDateObject.getDate()));

					tsDAO.addTimeSlot(currentTimeSlot);
					currentTime = nextTime;
					currentTimeObject.setMinutes(currentTimeObject.getMinutes() + tsDuration);
				}
				currentTimeObject = new Time(startHour, startMin, 0);
				currentDateObject.setDate(currentDateObject.getDate() + 1);
				currentTime = startTime;
			}

		} catch (Exception e) {
			throw new Exception("Failed to insert schedule: " + e.getMessage());
		}
		return false;
	}
	/**
	 * Returns String format of the next time
	 * @param time 			String format of curreunt time
	 * @param startTime 	String format of the start time of the day
	 * @param endTime		String format of the end time of the day
	 * @param tsDuration	Int duration of each time slot
	 * @return				String format of the next time
	 */
	private static String getNextTime(String time, String startTime, String endTime, int tsDuration) {
		Integer currentHr = new Integer(time.substring(0, 2));
		Integer currentMin = new Integer(time.substring(3));
		Integer startHr = new Integer(startTime.substring(0,2));
		Integer startMin = new Integer(startTime.substring(3));
		Integer endHr = new Integer(endTime.substring(0, 2));
		Integer endMin = new Integer(endTime.substring(3));
		//If new day
		if(currentHr==endHr && currentMin + tsDuration ==60) {
			currentHr = startHr;
			currentMin= 00;
			return "" + currentHr +":" + "00";
		}
		//If new hr
		else if(currentMin+tsDuration==60){
			currentHr++;
			currentMin=00;
			return "" + currentHr +":" + "00";

		}
		//If new min
		else {
			currentMin+=tsDuration;
			return "" + currentHr +":" + currentMin;

		}
	}
	/**
	 * Convert integer form of next date to string for db and object
	 * @param y	Year value
	 * @param m	Month value
	 * @param d	Day value
	 * @return	String form of next date
	 */
	private static String getNextDate(int y, int m, int d) {
		String date=null;

		//if december 31st
		if(m==12 && d==31) {
			date = "" + (y+1) +"-"+"01"+"-"+"01";
		}
		//if end of normal month
		else if((m==1 || m==3 ||m==5||m==7||m==8||m==10||m==12) && d==31) {
			date = "" + y +"-"+(m+1)+"-"+"01";
		}
		//Feb
		else if((m==2) && d==28) {
			date = "" + y +"-"+"03"+"-"+"01";

		}
		//
		else {
			date = "" + y +"-"+m+"-"+"01";

		}
		return date;
	}
	public List<TimeSlot> getTimeSlotsInWeek(String scheduleId, String date) throws Exception {

        try {

             //SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

             String year = date.substring(0, 4);

             String month = date.substring(5,7);

             String day = date.substring(8,10);



             int y = Integer.parseInt(year);

             int m = Integer.parseInt(month);

             int d = Integer.parseInt(day);



             Date thisDate = new Date(y, m, d);

             Date firstDay = new Date(y, m, d+1);

             Date secondDay = new Date(y, m, d+2);

             Date thirdDay = new Date(y, m, d+3);

             Date fourthDay = new Date(y, m, d+4);



             String firstDayString = firstDay.toString();

             String secondDayString = secondDay.toString();

             String thirdDayString = thirdDay.toString();

             String fourthDayString = fourthDay.toString();



             List<TimeSlot> timeslots = new ArrayList<>();

             TimeSlotsDAO dao = new TimeSlotsDAO();

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM TimeSlots WHERE sId=? AND date=?;");

            ps.setString(1, scheduleId);

            ps.setString(2, date);

            ResultSet resultSet = ps.executeQuery();



            while(resultSet.next()) {

            TimeSlot timeslot = dao.generateTimeSlot(resultSet);

            timeslots.add(timeslot);

            }



            ps.setString(2, firstDayString);

            resultSet = ps.executeQuery();



            while(resultSet.next()) {

            TimeSlot timeslot = dao.generateTimeSlot(resultSet);

            timeslots.add(timeslot);

            }



            ps.setString(2, secondDayString);

            resultSet = ps.executeQuery();



            while(resultSet.next()) {

            TimeSlot timeslot = dao.generateTimeSlot(resultSet);

            timeslots.add(timeslot);

            }



            ps.setString(2, thirdDayString);

            resultSet = ps.executeQuery();



            while(resultSet.next()) {

            TimeSlot timeslot = dao.generateTimeSlot(resultSet);

            timeslots.add(timeslot);

            }



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
	 * Get string form of current date
	 * @param y Year value
	 * @param m	Month value
	 * @param d	Day value
	 * @return	String form of current date
	 */
	private static String getCurrentDate(int y,int m ,int d) {
		String date = "";
		String month ="";
		String day = "";
		if(m<10) {
			month = "0"+m;
		}
		else {
			month = ""+m;
		}
		if(d < 10) {
			day = "0"+d;
		}
		else {
			day = ""+d;
		}
		date = y+"-"+month+"-"+day;
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
}
