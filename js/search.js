/**
 * 
 */

function processSearchResponse(arg1, arg2, result) {
  // Can grab any DIV or SPAN HTML element and can then manipulate its
  // contents dynamically via javascript
  console.log("result:" + result);
  var js = JSON.parse(result);

  var computation = js["value"];
  var status      = js["httpCode"];
  
  if (status == 200) {
    // Update computation result
    document.addForm.result.value = computation
  } else {
    var msg = js["errorMessage"];
    document.searchForm.result.value = "error:" + msg
  }
}

function handleFindTimeClick(e) {
  var form = document.findTimeForm;
  var Month = form.Month.value;
  var DayofWeek = form.DayofWeek.value;
  var TimeSlot = form.TimeSlot.value;
  var Year = form.Year.value;

  var data = {};
  data["Month"] = Month;
  data["DayofWeek"] = DayofWeek;
  data["TimeSlot"] = TimeSlot;
  data["Year"] = Year;

  var js = JSON.stringify(data);
  console.log("JS:" + js);
  var xhr = new XMLHttpRequest();
  xhr.open("POST", search_url, true);

  // send the collected data as JSON
  xhr.send(js);

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
    console.log(xhr);
    console.log(xhr.request);
    
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processAddResponse(arg1, arg2, xhr.responseText);
    } else {
      processAddResponse(arg1, arg2, "N/A");
    }
  };
}

function handleFindScheduleClick() {
	  var secretCode = prompt("Enter your secret code here:", "Oooh Secret");
	  var data = {};
	  data["secretCode"] = secretCode;

	  var js = JSON.stringify(data);
	  console.log("JS:" + js);
	  
	  var xhr = new XMLHttpRequest();
	  xhr.open("POST", reviewSchedule_url, true);

	  // send the collected data as JSON
	  xhr.send(js);

	  // This will process results and update HTML as appropriate. 
	  xhr.onloadend = function () {
	    console.log(xhr);
	    console.log(xhr.request);
	    
	    if (xhr.readyState == XMLHttpRequest.DONE) {
	      console.log ("XHR:" + xhr.responseText);
	      processFindResponse(secretCode, xhr.responseText);
	    } else {
	      processFindResponse(secretCode, "N/A");
	    }
	  };
	}

function processFindResponse(code, result) {
	  // Can grab any DIV or SPAN HTML element and can then manipulate its
	  // contents dynamically via javascript
	  console.log("result:" + result);
	  var js = JSON.parse(result);

	  var tsDuration = js["tsDuration"];
	  var scheduleId = js["scheduleId"];
	  var startTime = js["startTime"];
	  var endTime = js["endTime"];
	  var startDate = js["startingDateOfWeek"];
	  var statusCode = js["httpCode"];
	  var tslist = js["timeslotsInWeek"];
	  var meetinglist = js["meetingsInWeek"];
	  
	document.getElementById('secretCode').innerHTML = code;
	document.getElementById('sId').innerHTML= scheduleId;
	document.getElementById('startDate').innerHTML = startDate;
	  
	  if (statusCode == 200) {
	    // Update computation result
		  displayOrgSchedule(scheduleId, code, tsDuration, startTime, endTime, startDate, tslist, meetinglist);
	  } else {
	    var msg = js["errorMessage"];
	    alert("Schedule probably doesn't exist.");
	  }
	}

function handleFindSchedulePartClick() {
	  var accessCode = prompt("Enter your access code here:", "Oooh Access");
	  var data = {};
	  data["accessCode"] = accessCode;

	  var js = JSON.stringify(data);
	  console.log("JS:" + js);
	  
	  var xhr = new XMLHttpRequest();
	  xhr.open("POST", participantReview_url, true);

	  // send the collected data as JSON
	  xhr.send(js);

	  // This will process results and update HTML as appropriate. 
	  xhr.onloadend = function () {
	    console.log(xhr);
	    console.log(xhr.request);
	    
	    if (xhr.readyState == XMLHttpRequest.DONE) {
	      console.log ("XHR:" + xhr.responseText);
	      processPartFindResponse(accessCode, xhr.responseText);
	    } else {
	      processPartFindResponse(accessCode, "N/A");
	    }
	  };
	}

function processPartFindResponse(code, result) {
	  // Can grab any DIV or SPAN HTML element and can then manipulate its
	  // contents dynamically via javascript
	  console.log("result:" + result);
	  var js = JSON.parse(result);

	  var tsDuration = js["tsDuration"];
	  var sId = js["scheduleId"];
	  var scheduleId = js["scheduleId"];
	  var startTime = js["startTime"];
	  var endTime = js["endTime"];
	  var startDate = js["startingDateOfWeek"];
	  var statusCode = js["httpCode"];
	  var tslist = js["timeslotsInWeek"];
	  var meetinglist = js["meetingsInWeek"];
	  
	  document.getElementById('sId').innerHTML= scheduleId;
	  document.getElementById('startDate').innerHTML = startDate;
	  
	  if (statusCode == 200) {
	    // Update computation result
		  displayParticipantSchedule(code, sId, tsDuration, startTime, endTime, startDate, tslist, meetinglist);
	  } else {
	    var msg = js["errorMessage"];
	    alert("Schedule probably doesn't exist.");
	  }
	}

function handleRefreshSchedulePartClick(accessCode, date) {
	  //var accessCode = prompt("Enter your access code here:", "Oooh Access");
	  var data = {};
	  data["accessCode"] = accessCode;
	  data["mondayDate"] = date;

	  var js = JSON.stringify(data);
	  console.log("JS:" + js);
	  
	  var xhr = new XMLHttpRequest();
	  xhr.open("POST", participantReview_url, true);

	  // send the collected data as JSON
	  xhr.send(js);

	  // This will process results and update HTML as appropriate. 
	  xhr.onloadend = function () {
	    console.log(xhr);
	    console.log(xhr.request);
	    
	    if (xhr.readyState == XMLHttpRequest.DONE) {
	      console.log ("XHR:" + xhr.responseText);
	      processPartFindResponse(accessCode, xhr.responseText);
	    } else {
	      processPartFindResponse(accessCode, "N/A");
	    }
	  };
	}

function handleRefreshScheduleClick(secretCode, date) {
	  //var accessCode = prompt("Enter your access code here:", "Oooh Access");
	  var data = {};
	  data["secretCode"] = secretCode;
	  data["mondayDate"] = date;

	  var js = JSON.stringify(data);
	  console.log("JS:" + js);
	  
	  var xhr = new XMLHttpRequest();
	  xhr.open("POST", reviewSchedule_url, true);

	  // send the collected data as JSON
	  xhr.send(js);

	  // This will process results and update HTML as appropriate. 
	  xhr.onloadend = function () {
	    console.log(xhr);
	    console.log(xhr.request);
	    
	    if (xhr.readyState == XMLHttpRequest.DONE) {
	      console.log ("XHR:" + xhr.responseText);
	      processFindResponse(secretCode, xhr.responseText);
	    } else {
	      processFindResponse(secretCode, "N/A");
	    }
	  };
	}

function handleRetrieveSchedulesClick(e) {
	  //var accessCode = prompt("Enter your access code here:", "Oooh Access");
	  var form = document.findSchedulesForm;
	  var int = form.hoursOld.value;
	  
	  var d = new Date();
		var day;
		if(d.getDate()<10){
			day = "0"+d.getDate();
		} else{
			day = d.getDate();
		}
		var hour;
		if(d.getHours()<10){
			hour = "0"+d.getHours();
		} else{
			hour = d.getHours();
		}
		var min;
		if(d.getMinutes()<10){
			min = "0"+d.getMinutes();
		} else{
			min = d.getMinutes();
		}
		
	 	var createDate = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+day;
	 	var createTime = hour+":00";
		var data = {};
	  data["hr"] = int;
	  data["currentTime"] = createTime;

	  var js = JSON.stringify(data);
	  console.log("JS:" + js);
	  
	  var xhr = new XMLHttpRequest();
	  xhr.open("POST", retrieveSchedules_url, true);

	  // send the collected data as JSON
	  xhr.send(js);

	  // This will process results and update HTML as appropriate. 
	  xhr.onloadend = function () {
	    console.log(xhr);
	    console.log(xhr.request);
	    
	    if (xhr.readyState == XMLHttpRequest.DONE) {
	      console.log ("XHR:" + xhr.responseText);
	      processRetrieveResponse(xhr.responseText);
	    } else {
	      processRetrieveResponse("N/A");
	    }
	  };
	}

function processRetrieveResponse(result){
	 var js = JSON.parse(result);
	  var statusCode = js["httpCode"];
	  var sList = js["schedules"]
	  var output = "";
	  for(i=0;i<sList.length(); i++){
		  var time = sList["initialTime"];
		  var date = sList["initialDate"];
		  var name = sList["name"];
		  output = output + "The schedule: "+name+ " was created on "+ date+ " at "+ time+ " \r\n \r\n"
	  }
}

/**
 * getting the next week
 */

function handleNextWeekClick(code, date){
		var data = {};
	  data["secretCode"] = code;
	  data["mondayDate"] = date; 
	  
	  var js = JSON.stringify(data);
	  console.log("JS:" + js);
	  
	  var xhr = new XMLHttpRequest();
	  xhr.open("POST", reviewSchedule_url, true);

	  // send the collected data as JSON
	  xhr.send(js);

	  // This will process results and update HTML as appropriate. 
	  xhr.onloadend = function () {
	    console.log(xhr);
	    console.log(xhr.request);
	    
	    if (xhr.readyState == XMLHttpRequest.DONE) {
	      console.log ("XHR:" + xhr.responseText);
	      processFindResponse(code,xhr.responseText);
	    } else {
	      processFindResponse(code, "N/A");
	    }
	  };
}

function handleTimeSearchClick(e){
	var form = document.findTimeForm;
	var month = form.Month.value;
	var day = form.Day.value;
	var date = form.DayofMonth.value;
	var year = form.Year.value;
	var time = form.TimeSlot.value;
	var id = document.getElementById('sId').innerHTML;
	
	var data = {};
	if(month != "null"){
	  data["month"] = month;
	}
	if(day != "null"){
	  data["dayOfWeek"] = day; 
	}
	if(date != ""){
	  data["dayOfMonth"] = date;
	}
	if(year != ""){
	  data["year"] = year; 
	  }
	if(time != ""){
	  data["openTimeSlots"] = time; 
	  }
	  data["sId"] = id;
	  
	  var js = JSON.stringify(data);
	  console.log("JS:" + js);
	  
	  var xhr = new XMLHttpRequest();
	  xhr.open("POST", getTimeSlots_url, true);

	  // send the collected data as JSON
	  xhr.send(js);

	  // This will process results and update HTML as appropriate. 
	  xhr.onloadend = function () {
	    console.log(xhr);
	    console.log(xhr.request);
	    
	    if (xhr.readyState == XMLHttpRequest.DONE) {
	      console.log ("XHR:" + xhr.responseText);
	      processTimeSlotsResponse(xhr.responseText);
	    } else {
	      processTimeSlotsResponse("N/A");
	    }
	  };
}

function processTimeSlotsResponse(result) {
	  // Can grab any DIV or SPAN HTML element and can then manipulate its
	  // contents dynamically via javascript
	  console.log("result:" + result);
	  var js = JSON.parse(result);
	  var statusCode = js["httpCode"];
	  var output = "";
	  var tsList = js["openTimeSlots"];
	  var form = document.getElementById('searchResults');
	  for(i=0; i<tsList.length; i++){
		 var entry = tsList[i];
		var id = entry['timeSlotID'];
		var startTime = entry['startTime'];
		var date = entry['date'];
		output = output + "Meeting on: "+date+" at "+startTime+". \r\n Schedule with this code: "+id+"\r\n \r\n"; 
		
	  }
	  form.innerHTML = output;
	  
	  //document.getElementById('sId').innerHTML= scheduleId;
	  
	  if (statusCode == 200) {
	    // Update computation result
		  //alert(result);
		  //displayOrgSchedule(code, tsDuration, startTime, endTime, startDate, tslist, meetinglist);
	  } else {
	    var msg = js["errorMessage"];
	    alert("Schedule probably doesn't exist.");
	  }
	}

function handleNextWeekPartClick(code, date){
	var data = {};
  data["accessCode"] = code;
  data["mondayDate"] = date; 
  
  var js = JSON.stringify(data);
  console.log("JS:" + js);
  
  var xhr = new XMLHttpRequest();
  xhr.open("POST", participantReview_url, true);

  // send the collected data as JSON
  xhr.send(js);

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
    console.log(xhr);
    console.log(xhr.request);
    
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processPartFindResponse(code,xhr.responseText);
    } else {
      processPartFindResponse(code, "N/A");
    }
  };
}
