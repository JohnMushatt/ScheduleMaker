package com.amazonaws.lambda.demo;

public class EditTimeSlotResponse {
	public final int isOpen;
	public final String body;
	public final int httpCode;

	public EditTimeSlotResponse(int isOpen,int code) {
		this.isOpen=isOpen;
		this.httpCode=code;
		this.body=null;
	}
	public EditTimeSlotResponse(String body,int code) {
		this.isOpen=0;
		this.body=body;
		this.httpCode=code;
	}
	@Override
	public String toString() {
		return "EditTimeSlotResponse("+isOpen+","+httpCode+")";
	}
}
