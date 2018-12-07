package com.amazonaws.lambda.db;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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

	private TimeSlot generateTimeSlot(ResultSet resultSet) throws Exception {
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
			System.out.println("Succesfully added time slot: startTime= " + timeSlot.startTime + "\tendTime= " + timeSlot.endTime + "\tdate= " + timeSlot.date);
			;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed to add time slot " + e.getMessage());
		}
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

}
