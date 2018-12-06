package com.amazonaws.lambda.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.amazonaws.lambda.model.TimeSlot;

public class TimeSlotsDAO {
	java.sql.Connection conn;

	public TimeSlotsDAO() {
		try {
			conn = DatabaseUtil.connect();
		} catch (Exception e) {
			conn = null;
		}
	}

	public TimeSlot getTimeSlot(String timeSlotId) throws Exception {
		try {
			TimeSlot timeSlot = null;

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM TimeSlots WHERE tsId=?;");
			ps.setString(1, timeSlotId);

			ResultSet resultSet  = ps.executeQuery();

			while(resultSet.next()) {
				timeSlot = generateTimeSlot(resultSet);
			}
			resultSet.close();
			ps.close();
			return timeSlot;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed in getting time slot: " + e.getMessage());
		}

	}

	public boolean deleteTimeSlot(TimeSlot timeSlot) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM TimeSlots WHERE tsId = ?;");
			ps.setString(1, timeSlot.timeSlotID);

			int numAffected = ps.executeUpdate();
			ps.close();
			return (numAffected==1);
		}
		catch (Exception e){
            throw new Exception("Failed to delete time slot: " + e.getMessage());

		}
	}

	private TimeSlot generateTimeSlot(ResultSet resultSet) throws Exception {
		String timeSlotID = resultSet.getString("tsId");
		int isOpen = resultSet.getInt("isOpen");
		String startTime = resultSet.getString("startTime");
		String endTime = resultSet.getString("endTime");
		int isBooked = resultSet.getInt("isBooked");
		int dayOfWeek = resultSet.getInt("day");
		String scheduleID = resultSet.getString("sId");
		return new TimeSlot(timeSlotID, isOpen, startTime, endTime, isBooked, dayOfWeek,scheduleID);
	}

	public boolean addTimeSlot(TimeSlot timeSlot) throws Exception{
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM TimeSlots WHERE tsId=?;");
			ps.setString(1, timeSlot.timeSlotID);

			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				TimeSlot ts = generateTimeSlot(resultSet);
				resultSet.close();
				return false;
			}
			ps = conn.prepareStatement("INSERT INTO TimeSlots (tsId, isOpen, startTime, endTime, isBooked, day, sId) values(?,?,?,?,?,?,?);");
			ps.setString(1, timeSlot.timeSlotID);
			ps.setInt(2, 	timeSlot.isOpen);
			ps.setString(3,	timeSlot.startTime);
			ps.setString(4, timeSlot.endTime);
			ps.setInt(5, 	timeSlot.isBooked);
			ps.setInt(6, 	timeSlot.dayOfWeek);
			ps.setString(7, timeSlot.scheduleID);
			ps.execute();
			System.out.println("Succesfully added time slot: " + timeSlot.timeSlotID);;
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Failed to add time slot " + e.getMessage());		}
	}

}
