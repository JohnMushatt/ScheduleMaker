package com.amazonaws.lambda.demo;

public class DeleteScheduleResponse {
	public int code;
	int totalDeleted;
	public DeleteScheduleResponse(int totalDeleted, int code) {
		this.code = code;
		this.totalDeleted=totalDeleted;
	}

}
