package com.amazonaws.lambda.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.amazonaws.lambda.model.Schedule;
public class SchedulesDAO {
	java.sql.Connection conn;

	public SchedulesDAO() {
		try {
			conn = DatabaseUtil.connect();
		} catch (Exception e) {
			conn = null;
		}
	}

	public Schedule getSchedule(String scheduleId) throws Exception {
		try {
			Schedule schedule = null;

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Schedules WHERE sId=?;");
			ps.setString(1, scheduleId);

			ResultSet resultSet  = ps.executeQuery();

			while(resultSet.next()) {
				schedule=generateSchedule(resultSet);
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
			return (numAffected==1);
		}
		catch (Exception e){
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
			return (numAffected==1);

		}
		catch(Exception e ) {
			throw new Exception("Failed to update report: " + e.getMessage());
		}
	}

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
		return new Schedule(sId,initDate,initTime,orgId,startDate,endDate,
				startTime,endTime,timeSlotDuration,secretCode);
	}
	public boolean addSchedule(Schedule schedule) throws Exception{
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Schedules WHERE sId=?;");
			ps.setString(1, schedule.scheduleId);

			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				Schedule s = generateSchedule(resultSet);
				resultSet.close();
				return false;
			}
			ps = conn.prepareStatement("INSERT INTO Schedules (sId,initDate,initTime,orgId,startDate,endDate,startTime,endTime,"
					+ "tsDuration,secretCode) values(?,?,?,?,?,?,?,?,?,?);");
			ps.setString(1, schedule.scheduleId);
			ps.setString(2, schedule.initialDate);
			ps.setString(3,	schedule.initialTime);
			ps.setString(4, schedule.organizerId);
			ps.setString(5, schedule.startDate);
			ps.setString(6, schedule.endDate);
			ps.setString(7, schedule.startTime);
			ps.setString(8, schedule.endTime);
			ps.setInt(9, 	schedule.timeslotDuration);
			ps.setString(10,schedule.secretCode);
			ps.execute();
			System.out.println("Succesfully added schedule: " + schedule.scheduleId);;
			String startDate = schedule.startDate;
			String endDate = schedule.endDate;
			int tsDuration = schedule.timeslotDuration;
			Integer endYearVal = new Integer(endDate.substring(0,4));
			Integer endMonthVal = new Integer(endDate.substring(5,7));
			Integer endDayVal = new Integer(endDate.substring(8));
			Integer startYearVal = new Integer(startDate.substring(0,4));
			Integer startMonthVal = new Integer(startDate.substring(5,7));
			Integer startDayVal = new Integer(startDate.substring(8));

			int totalTimeSlots = calcDays(endYearVal,endMonthVal,endDayVal)-
					calcDays(startYearVal,startMonthVal,startDayVal);

			ps = conn.prepareStatement("INSERT INTO TimeSlots;");
			return true;
		}
		catch(Exception e) {
			 throw new Exception("Failed to insert constant: " + e.getMessage());		}
	}
	/**
	 * Calculates the position of the year in Gregorian calendar
	 * form e.g. will return x/365 where x is the position in a given year
	 * @param y Year value
	 * @param m Month value
	 * @param d Day value
	 * @return Position in given year
	 */
	private int calcDays(int y, int m, int d) {
		int days = 0;
		m = (m+9) % 12;
		y = y - m/10;
		days = 365 * y + y/4 -y/100 + y/400 + (m*306 +5)/10 + (d-1);
		return days;
	}
}
