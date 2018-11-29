package com.amazonaws.lambda.model;

import java.sql.Date;

public class Week {
	Date startDate;
	Date endDate;
	
	public Week(Date startDate, Date endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}
}
