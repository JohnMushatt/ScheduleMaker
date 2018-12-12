package com.amazonaws.lambda.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.amazonaws.lambda.model.Meeting;

public class MeetingsDAO {
	java.sql.Connection conn;

	public MeetingsDAO() {
		try {
			conn = DatabaseUtil.connect();
		} catch (Exception e) {
			conn = null;
		}
	}

	public Meeting getMeeting(String meetingId) throws Exception {
		try {
			Meeting meeting = null;

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Meetings WHERE mId=?;");
			ps.setString(1, meetingId);

			ResultSet resultSet  = ps.executeQuery();

			while(resultSet.next()) {
				meeting=generateMeeting(resultSet);
			}
			resultSet.close();
			ps.close();
			return meeting;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed in getting meeting: " + e.getMessage());
		}

	}
	public String getTimeSlotID(String meetingID) throws Exception {
		try {
			Meeting meeting = null;

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Meetings WHERE mId=?;");
			ps.setString(1, meetingID);

			ResultSet resultSet  = ps.executeQuery();

			while(resultSet.next()) {
				meeting=generateMeeting(resultSet);
			}
			resultSet.close();
			ps.close();
			return meeting.timeSlotID;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed in getting meeting: " + e.getMessage());
		}
	}
	public boolean deleteMeeting(String meetingID,String secretCode) throws Exception {
		if(secretCode!=null) {
			try {
				System.out.println("Deleting via secret code");
				PreparedStatement ps = conn.prepareStatement("DELETE FROM Meetings WHERE secretCode = ?;");
				ps.setString(1, secretCode);
				String id = this.getTimeSlotID(meetingID);
				int numAffected = ps.executeUpdate();
				ps.close();

				if(numAffected==1) {
					TimeSlotsDAO t = new TimeSlotsDAO();
					t.updateBooked(id);
				}
				return (numAffected==1);
			}
			catch (Exception e){
	            throw new Exception("Failed to delete meeting: " + e.getMessage());

			}
		}

		else {
			try {
				PreparedStatement ps = conn.prepareStatement("DELETE FROM Meetings WHERE mId = ?;");
				ps.setString(1, meetingID);
				String id = this.getTimeSlotID(meetingID);
				int numAffected = ps.executeUpdate();
				ps.close();

				if(numAffected==1) {
					TimeSlotsDAO t = new TimeSlotsDAO();
					t.updateBooked(id);
				}
				return (numAffected==1);
			}
			catch (Exception e){
	            throw new Exception("Failed to delete meeting: " + e.getMessage());

			}
		}

	}

	Meeting generateMeeting(ResultSet resultSet) throws Exception {
		String meetingID = resultSet.getString("mId");
		String organizerID = resultSet.getString("orgId");
		String timeSlotID = resultSet.getString("tsId");
		String participantName = resultSet.getString("parName");
		String scheduleID = resultSet.getString("sId");
		String secretCode = resultSet.getString("secretCode");
		return new Meeting(meetingID,organizerID, timeSlotID, participantName,scheduleID,secretCode);
	}

	public boolean addMeeting(Meeting meeting) throws Exception{
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Meetings WHERE mId=?;");
			ps.setString(1, meeting.meetingID);

			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				Meeting m = generateMeeting(resultSet);
				resultSet.close();
				return false;
			}
			ps = conn.prepareStatement("INSERT INTO Meetings (mId, orgId, tsId, parName,sId,secretCode) values(?,?,?,?,?,?);");
			ps.setString(1, meeting.meetingID);
			ps.setString(2, meeting.organizerID);
			ps.setString(3, meeting.timeSlotID);
			ps.setString(4, meeting.participantName);
			ps.setString(5, meeting.scheduleID);
			ps.setString(6, meeting.secretCode);
			ps.execute();
			System.out.println("Succesfully added meeting: " + meeting.meetingID);;
			return true;
		}
		catch(Exception e) {
			 throw new Exception("Failed to add meeting " + e.getMessage());		}
	}

}
