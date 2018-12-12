package com.amazonaws.lambda.demo;

public class CreateMeetingRequest {
	String scheduleID;
	String timeslotID;
	String participantName;
	/**
	 * Create the HTTP meeting request to store data
	 * @param sID 	Schedule ID
	 * @param orgID OrganizerID
	 * @param tID 	Time slot ID
	 * @param pID	Participant ID
	 * @param pName	Name of the participant
	 */
	public CreateMeetingRequest(String scheduleID,String timeslotID,String participantName) {
		this.scheduleID=scheduleID;
		this.timeslotID=timeslotID;
		this.participantName=participantName;
	}

	@Override
	public String toString() {
		return "CreateMeetingRequest("+this.scheduleID+","+this.timeslotID+","+this.participantName+")";
	}
}
