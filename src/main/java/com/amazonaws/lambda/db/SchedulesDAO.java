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
	public boolean deleteSchedule(Schedule schedule) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM Schedules WHERE sId = ?;");
			ps.setString(1, schedule.scheduleId);

			int numAffected = ps.executeUpdate();
			ps.close();
			return (numAffected==1);
		}
		catch (Exception e){
            throw new Exception("Failed to delete schedule: " + e.getMessage());

		}
	}
	/*
	public boolean updateSchedule(Schedule schedule) throws Exception{
		try {
			String query = "UPDATE Schedules SET "
		}
		catch(Exception e) {

		}
	}
	*/
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
		String timeSlotDuration = resultSet.getString("tsDuration");
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
			ps.setDate(2, null);
			ps.setDate(3,null);
			ps.setString(4, schedule.organizerId);
			ps.setDate(5, null);
			ps.setDate(6, null);
			ps.setString(7, schedule.startTime);
			ps.setString(8, schedule.endTime);
			ps.setInt(9, 0);
			ps.setString(10,schedule.secretCode);
			ps.execute();
			System.out.println("Succesfully added schedule: " + schedule.scheduleId);;
			return true;
		}
		catch(Exception e) {
			 throw new Exception("Failed to insert constant: " + e.getMessage());		}
	}
}
