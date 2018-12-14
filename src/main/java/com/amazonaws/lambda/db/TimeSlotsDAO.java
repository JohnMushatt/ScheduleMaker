package com.amazonaws.lambda.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

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
	public boolean editByDate(String secretCode, String date, int state) throws Exception {
		try {
			SchedulesDAO s = new SchedulesDAO();
			//Get scheduleID
			String scheduleID = s.getScheduleId(secretCode);
			PreparedStatement ps = conn.prepareStatement("UPDATE TimeSlots SET isOpen=? WHERE sId=? AND date=?;");
			if(state==1) {
				ps.setInt(1, 1);
			}
			else {
				ps.setInt(1, 0);
			}
			ps.setString(2, scheduleID);
			ps.setString(3, date);
			int numAffected = ps.executeUpdate();
			ps.close();
			return numAffected!=0;
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Failed in getting time slot: " + e.getMessage());
		}
	}
	/**
	 * Edit the states of all time slots on a single day
	 * @param secretCode
	 * @param date
	 * @param state
	 * @return
	 * @throws Exception
	 */
	public boolean editByStartTime(String secretCode, String date, String time,int state) throws Exception {
		try {
			SchedulesDAO s = new SchedulesDAO();
			//Get scheduleID
			String scheduleID = s.getScheduleId(secretCode);
			PreparedStatement ps = conn.prepareStatement("UPDATE TimeSlots SET isOpen=? WHERE sId=? AND startTime=? AND date=?;");
			ps.setInt(1, state);
			ps.setString(2, scheduleID);
			ps.setString(3, time);
			int numAffected = 0;
			Integer year = new Integer(date.substring(0, 4));
			Integer month = new Integer(date.substring(5,7));
			Integer day = new Integer(date.substring(8));
			LocalDate temp = new LocalDate(year, month, day);
			for(int i = 0; i < 5; i++) {
				ps.setString(4, date);
				numAffected+=ps.executeUpdate();
				temp=temp.plusDays(1);
				date = getCurrentDate(temp.getYear(),temp.getMonthOfYear(),temp.getDayOfMonth());
			}
			ps.close();
			return numAffected!=0;
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Failed in getting time slot: " + e.getMessage());
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
	public TimeSlot getTimeSlot(String timeSlotId) throws Exception {
		try {
			TimeSlot timeSlot = null;

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM TimeSlots WHERE tsId=?;");
			ps.setString(1, timeSlotId);

			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
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
			return (numAffected == 1);
		} catch (Exception e) {
			throw new Exception("Failed to delete time slot: " + e.getMessage());

		}
	}
	public boolean editTimeSlot(String timeSlotID) throws Exception{

		try {
			if(this.getTimeSlot(timeSlotID).isOpen==1) {
				PreparedStatement ps = conn.prepareStatement("UPDATE TimeSlots SET isOpen=? WHERE tsId=?");
				ps.setInt(1, 0);
				ps.setString(2, timeSlotID);
				int numAffected = ps.executeUpdate();

				ps.close();
				return(numAffected==1);
			}
			else {
				PreparedStatement ps = conn.prepareStatement("UPDATE TimeSlots SET isOpen=? WHERE tsId=?");
				ps.setInt(1, 1);
				ps.setString(2, timeSlotID);
				int numAffected = ps.executeUpdate();

				ps.close();
				return(numAffected==1);
			}


		}
		catch (Exception e) {
			throw new Exception("Failed to close time slot: " + e.getMessage());
		}
	}
	public boolean updateBooked(String timeSlotID) throws Exception{

		try {
			if(this.getTimeSlot(timeSlotID).isBooked==1) {
				PreparedStatement ps = conn.prepareStatement("UPDATE TimeSlots SET isBooked=? WHERE tsId=?");
				ps.setInt(1, 0);
				ps.setString(2, timeSlotID);
				int numAffected = ps.executeUpdate();

				ps.close();
				return(numAffected==1);
			}
			else {
				PreparedStatement ps = conn.prepareStatement("UPDATE TimeSlots SET isBooked=? WHERE tsId=?");
				ps.setInt(1, 1);
				ps.setString(2, timeSlotID);
				int numAffected = ps.executeUpdate();

				ps.close();
				return(numAffected==1);
			}


		}
		catch (Exception e) {
			throw new Exception("Failed to close time slot: " + e.getMessage());
		}
	}
	TimeSlot generateTimeSlot(ResultSet resultSet) throws Exception {
		String timeSlotID = resultSet.getString("tsId");
		int isOpen = resultSet.getInt("isOpen");
		String startTime = resultSet.getString("startTime");
		String endTime = resultSet.getString("endTime");
		int isBooked = resultSet.getInt("isBooked");
		int dayOfWeek = resultSet.getInt("day");
		String scheduleID = resultSet.getString("sId");
		String date = resultSet.getString("date");
		return new TimeSlot(timeSlotID, isOpen, startTime, endTime, isBooked, dayOfWeek, scheduleID, date);
	}
	public List<TimeSlot> getTimeSlotsInWeek(String scheduleID) throws Exception {
		List<TimeSlot> timeSlots= new ArrayList<>();
        try {

             //want to get wId in TimeSlots

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM TimeSlots WHERE sId=?;");
            ps.setString(1,scheduleID);
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()) {
            	TimeSlot t = generateTimeSlot(resultSet);
            	timeSlots.add(t);

            }
            resultSet.close();
            ps.close();
            return timeSlots;
        } catch (Exception e) {

            throw new Exception("Failed to review schedule");

        }
	}
	public boolean addTimeSlot(TimeSlot timeSlot) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM TimeSlots WHERE tsId=?;");
			ps.setString(1, timeSlot.timeSlotID);

			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				TimeSlot ts = generateTimeSlot(resultSet);
				resultSet.close();
				return false;
			}
			ps = conn.prepareStatement(
					"INSERT INTO TimeSlots (tsId, isOpen, startTime, endTime, isBooked, day, sId,date) values(?,?,?,?,?,?,?,?);");
			ps.setString(1, timeSlot.timeSlotID);
			ps.setInt(2, timeSlot.isOpen);
			ps.setString(3, timeSlot.startTime);
			ps.setString(4, timeSlot.endTime);
			ps.setInt(5, timeSlot.isBooked);
			ps.setInt(6, timeSlot.dayOfWeek);
			ps.setString(7, timeSlot.scheduleID);
			ps.setString(8, timeSlot.date);
			ps.execute();
			System.out.println("Succesfully added time slot: startTime= " + timeSlot.startTime + "\tendTime= " + timeSlot.endTime + "\tdate= " + timeSlot.date + "tsId=" +timeSlot.timeSlotID);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed to add time slot " + e.getMessage());
		}
	}


}
