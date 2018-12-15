/**
 * 
 */
function closeSlot(num, result){
	console.log(num);
	var js = JSON.parse(result);
	var status = js["httpCode"];
	if(status == 200){
		if(document.getElementById(num).innerHTML == "closed"){
			document.getElementById(num).innerHTML  = "open";
			document.getElementById(num).style.backgroundColor = "#ffecb4"
		} else{
		
			document.getElementById(num).innerHTML = "closed";
			document.getElementById(num).style.backgroundColor = "#e5771e"
	}
	}else{
		alert("TimeSlot failed to close");
	}
}

function handleTimeSlotClick(val) {
	  var data = {};
	  data["timeSlotID"] = val;

	  var js = JSON.stringify(data);
	  console.log("JS:" + js);
	  var xhr = new XMLHttpRequest();
	  xhr.open("POST", editTimeSlot_url, true);

	  // send the collected data as JSON
	  xhr.send(js);

	  // This will process results and update HTML as appropriate. 
	  xhr.onloadend = function () {
	    console.log(xhr);
	    console.log(xhr.request);
	    if (xhr.readyState == XMLHttpRequest.DONE) {
	      console.log ("XHR:" + xhr.responseText);
	      closeSlot(val, xhr.responseText);
	    } else {
	      closeSlot(val, "N/A");
	    }
	  };
	}

function handleCloseSlotsDayClick(code, date, day){
	var state = prompt("Would you like to open or close the TimeSlots?", "open/close");
	if(state == 'close'){
		state = 0;
	}else{
		state =1;
	}
	 var data = {};
	  data["secretCode"] = code;
	  data["date"] = date;
	  //data["day"] = day;
	  data["state"] = state;
	  
	  var js = JSON.stringify(data);
	  console.log("JS:" + js);
	  var xhr = new XMLHttpRequest();
	  xhr.open("POST", editTimeSlot_url, true);

	  // send the collected data as JSON
	  xhr.send(js);

	  // This will process results and update HTML as appropriate. 
	  xhr.onloadend = function () {
	    console.log(xhr);
	    console.log(xhr.request);
	    if (xhr.readyState == XMLHttpRequest.DONE) {
	      console.log ("XHR:" + xhr.responseText);
	      refreshSlots(code, xhr.responseText);
	    } else {
	      refreshSlots(code, "N/A");
	    }
	  };
	}

function handleCloseSlotTimeClick(code, date, time){
	var state = prompt("Would you like to open or close the TimeSlots?", "open/close");
	if(state == 'close'){
		state = 0;
	}else{
		state =1;
	}
	 var data = {};
	  data["secretCode"] = code;
	  data["weekStartDate"] = date;
	  data["startTime"] = time;
	  data["state"] = state;
	  
	  var js = JSON.stringify(data);
	  console.log("JS:" + js);
	  var xhr = new XMLHttpRequest();
	  xhr.open("POST", editTimeSlot_url, true);

	  // send the collected data as JSON
	  xhr.send(js);

	  // This will process results and update HTML as appropriate. 
	  xhr.onloadend = function () {
	    console.log(xhr);
	    console.log(xhr.request);
	    if (xhr.readyState == XMLHttpRequest.DONE) {
	      console.log ("XHR:" + xhr.responseText);
	      refreshSlots(code, xhr.responseText);
	    } else {
	      refreshSlots(code, "N/A");
	    }
	  };
	}

function refreshSlots(code, response){
 var date = document.getElementById('startDate').innerHTML;
	handleRefreshScheduleClick(code, date);
}
	  
function handleExtendDateClick(e){
	var form = document.modifyForm;
	var newStart = form.startDate.value;
	var newEnd = form.endDate.value;
	var code = document.getElementById('secretCode').innerHTML;
	var data = {};
	data["secretCode"] = code;
	if(newStart != ""){
		data["startDate"] = newStart;
	}
	if(newEnd != ""){
		data["endDate"] = newEnd;
	} else{
		data["endDate"] = null;
	}
	
	 var js = JSON.stringify(data);
	  console.log("JS:" + js);
	  var xhr = new XMLHttpRequest();
	  xhr.open("POST", modifySchedule_url, true);

	  // send the collected data as JSON
	  xhr.send(js);

	  // This will process results and update HTML as appropriate. 
	  xhr.onloadend = function () {
	    console.log(xhr);
	    console.log(xhr.request);
	    if (xhr.readyState == XMLHttpRequest.DONE) {
	      console.log ("XHR:" + xhr.responseText);
	      refreshSlots(code, xhr.responseText);
	    } else {
	      refreshSlots(code, "N/A");
	    }
	  };
}
