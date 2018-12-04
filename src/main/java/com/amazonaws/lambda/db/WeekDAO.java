package com.amazonaws.lambda.db;

import java.sql.ResultSet;

import com.amazonaws.lambda.model.Week;
import java.sql.PreparedStatement;

public class WeekDAO {
	java.sql.Connection conn;

	public WeekDAO() {
		try {
			conn = DatabaseUtil.connect();
		} catch (Exception e) {
			conn = null;
		}
	}
	
	public Week getWeek(String weekID) throws Exception {
		
		try {
			Week week = null;
			
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Weeks WHERE wId=?;");
			ps.setString(1, weekID);
			
			ResultSet resultSet = ps.executeQuery();
			
			while(resultSet.next()) {
				week=generateWeek(resultSet);
			}
			resultSet.close();
			ps.close();
			return week;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed in getting schedule: " + e.getMessage());	
		}
	}
	
	private Week generateWeek(ResultSet resultSet) throws Exception {
		String wId = resultSet.getString("wId");
		String startDate = resultSet.getString("startDate");
		String endDate = resultSet.getString("endDate");
		String sId = resultSet.getString("sId");
		return new Week(startDate, endDate);
	}
}
