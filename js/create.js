function processCreateResponse(result) {
	  // Can grab any DIV or SPAN HTML element and can then manipulate its
	  // contents dynamically via javascript
	  console.log("result:" + result);
	  var js = JSON.parse(result);
	  var code = js.list[0];
	  console.log(code);

	  var httpResult = js["response"];

	  // Update computation result
	  document.statusForm.status.value = result;
	 
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
	data["slotDuration"] = slotDuration;

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