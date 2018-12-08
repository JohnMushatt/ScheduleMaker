function processCreateResponse(result) {
	  // Can grab any DIV or SPAN HTML element and can then manipulate its
	  // contents dynamically via javascript
	  console.log("result:" + result);
	  var js = JSON.parse(result);
	  //var js = JSON.parse('{"secretCode":"2018-12-08420514:404205","startDate":"2018-01-01","startTime":"10:00","endTime":"14:00","tsDuration":30,"accessCode":"c5b5fa14-6419-43ec-8d34-22be076bd18a","httpCode":200}');
	  var responseCode = js.httpCode;
	  if(responseCode == 200){
	  var secretCode = js.secretCode;
	  var accessCode = js.accessCode;
	  console.log(secretCode);
	  alert("This is your code to access the Schedule. Please store it somewhere safe. " + secretCode);
	  alert("Send this code to those who you want to schedule meetings. " + accessCode);
	  }else{
		  alert("Improper Input Fields. Check Again.");
	  }
	  
	  //var httpResult = js["response"];

	  // Update computation result
	 // document.statusForm.status.value = result;
	 
	}


function handleCreateClick(e){
	
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
 	var createTime = hour+":"+min;
	var form = document.createScheduleForm;
	var startDate = form.startDate.value;
	var endDate = form.endDate.value;
	var startTime = form.startTime.value;
	var endTime = form.endTime.value;
	var slotDuration = form.slotDuration.value;

	var data = {};
	data["initDate"] = createDate;
	data["initTime"] = createTime;
	data["startDate"] = startDate;
	data["endDate"] = endDate;
	data["startTime"] = startTime;
	data["endTime"] = endTime;
	data["tsDuration"] = slotDuration;

	var js = JSON.stringify(data);
	console.log("JS" + js);

	var xhr = new XMLHttpRequest();
	  xhr.open("POST", createSchedule_url, true);

	  // send the collected data as JSON
	  xhr.send(js);

	  // This will process results and update HTML as appropriate. 
	  xhr.onloadend = function () {
	    console.log(xhr);
	    console.log(xhr.request);
	    if (xhr.readyState == XMLHttpRequest.DONE) {
	      console.log ("XHR:" + xhr.responseText);
	      processCreateResponse(xhr.responseText);
	    } else {
	      processCreateResponse("N/A");
	    }
	  };

};

function handleCreateMeetingClick(e){
	
}