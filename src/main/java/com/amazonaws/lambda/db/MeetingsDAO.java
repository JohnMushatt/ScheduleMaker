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

	//don't call deleteMeeting method yet
	public boolean deleteMeeting(String meetingID) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM Meetings WHERE mId = ?;");
			ps.setString(1, meetingID);

			int numAffected = ps.executeUpdate();
			ps.close();
			return (numAffected==1);
		}
		catch (Exception e){
            throw new Exception("Failed to delete meeting: " + e.getMessage());

		}
	}

	private Meeting generateMeeting(ResultSet resultSet) throws Exception {
		String meetingID = resultSet.getString("mId");
		String participantID = resultSet.getString("participantID");
		String organizerID = resultSet.getString("organizerID");
		String timeSlotID = resultSet.getString("timeSlotID");
		String participantName = resultSet.getString("participantName");
		String secretCode = resultSet.getString("sID");
		return new Meeting(meetingID, participantID, organizerID, timeSlotID, participantName,secretCode);
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
			ps = conn.prepareStatement("INSERT INTO Meetings (mId, parId, orgId, tsId, parName,sCode) values(?,?,?,?,?,?);");
			ps.setString(1, meeting.meetingID);
			ps.setString(2, meeting.participantID);
			ps.setString(3,meeting.organizerID);
			ps.setString(4, meeting.timeSlotID);
			ps.setString(5, meeting.participantName);
			ps.setString(6, meeting.secretCode);
			ps.execute();
			System.out.println("Succesfully added meeting: " + meeting.meetingID);;
			return true;
		}
		catch(Exception e) {
			 throw new Exception("Failed to add meeting " + e.getMessage());		}
	}

}
