

function processDeleteResponse(result) {
  // Can grab any DIV or SPAN HTML element and can then manipulate its
  // contents dynamically via javascript
	var js = JSON.parse(result);
	var code = js["code"];
	if (code == 200){
		console.log("deleted :" + result);
		alert("Schedule has been deleted");
		location.reload();
	}else{
		alert("Improper Secret Code.");
	}
}

function requestDelete(val) {
   if (confirm("Request to delete " + val)) {
     processDelete(val);
   }
}

function handleDeleteScheduleClick() {
  var code = prompt("Please enter your secret code:", "Oooh secret");
  
  if(code == null){
	  return null;
  }
  var data = {};
  data["secretCode"] = code;

  var js = JSON.stringify(data);
  console.log("JS:" + js);
  var xhr = new XMLHttpRequest();
  xhr.open("POST", deleteSchedule_url, true);

  // send the collected data as JSON
  xhr.send(js);

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
    console.log(xhr);
    console.log(xhr.request);
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processDeleteResponse(xhr.responseText);
    } else {
      processDeleteResponse("N/A");
    }
  }; 
}

function handleDeleteMeetingParticipantClick(acode, mId) {
	
	if (confirm("Are you sure you want to delete this meeting?")){
	  var code = prompt("Please enter your secret code:", "Oooh secret");
	  if(code == null){
		  code = 1;
	  }
	  var data = {};
	  data["meetingID"] = mId;
	  data["secretCode"] = code;

	  var js = JSON.stringify(data);
	  console.log("JS:" + js);
	  var xhr = new XMLHttpRequest();
	  xhr.open("POST", deleteMeetingParticipant_url, true);

	  // send the collected data as JSON
	  xhr.send(js);

	  // This will process results and update HTML as appropriate. 
	  xhr.onloadend = function () {
	    console.log(xhr);
	    console.log(xhr.request);
	    if (xhr.readyState == XMLHttpRequest.DONE) {
	      console.log ("XHR:" + xhr.responseText);
	      processDeleteMeetingResponse(acode, xhr.responseText);
	    } else {
	      processDeleteMeetingResponse(acode, "N/A");
	    }
	  }; 
	}else{
		return null;
	}
	}

function handleDeleteMeetingOrgClick(acode, mId) {
	if (confirm("Are you sure you want to delete this meeting?")){
	  var data = {};
	  data["meetingID"] = mId;

	  var js = JSON.stringify(data);
	  console.log("JS:" + js);
	  var xhr = new XMLHttpRequest();
	  xhr.open("POST", deleteMeetingParticipant_url, true);

	  // send the collected data as JSON
	  xhr.send(js);

	  // This will process results and update HTML as appropriate. 
	  xhr.onloadend = function () {
	    console.log(xhr);
	    console.log(xhr.request);
	    if (xhr.readyState == XMLHttpRequest.DONE) {
	      console.log ("XHR:" + xhr.responseText);
	      processDeleteMeetingOrgResponse(acode, xhr.responseText);
	    } else {
	      processDeleteMeetingOrgResponse(acode, "N/A");
	    }
	  }; 
	}else{
		return null;
	}
}

function processDeleteMeetingResponse(acode, result) {
	  // Can grab any DIV or SPAN HTML element and can then manipulate its
	  // contents dynamically via javascript
	 var js = JSON.parse(result);
	 var date = document.getElementById('startDate').innerHTML;
	  console.log("deleted :" + result);
	 var code = js["code"];
	 if(code == 200){
	  alert("Meeting Deleted.")
	  handleRefreshSchedulePartClick(acode, date);
	 }else{
		 alert("Improper Code. Check again.")
	 }
	 
	}

	function requestDelete(val) {
	   if (confirm("Request to delete " + val)) {
	     processDelete(val);
	   }
	}
	
	function processDeleteMeetingOrgResponse(acode, result) {
		  // Can grab any DIV or SPAN HTML element and can then manipulate its
		  // contents dynamically via javascript
		 var js = JSON.parse(result);
		 var date = document.getElementById('startDate');
		  console.log("deleted :" + result);
		 var code = js["code"];
		 if(code == 200){
		  alert("Meeting Deleted.")
		  handleRefreshScheduleClick(acode, date);
		 }else{
			 alert("Improper Code. Check again.")
		 }
		 
		}

		function requestDelete(val) {
		   if (confirm("Request to delete " + val)) {
		     processDelete(val);
		   }
		}
		
	function handleDeleteSysAdminClick(e){
		var int = document.deleteOld.daysOld.value;
		var data = {};
		  data["days"] = int;
		var js = JSON.stringify(data);
		console.log("JS:" + js);
		
		var xhr = new XMLHttpRequest();
		  xhr.open("POST", deleteScheduleSysAdmin_url, true);

		  // send the collected data as JSON
		  xhr.send(js);

		  // This will process results and update HTML as appropriate. 
		  xhr.onloadend = function () {
		    console.log(xhr);
		    console.log(xhr.request);
		    if (xhr.readyState == XMLHttpRequest.DONE) {
		      console.log ("XHR:" + xhr.responseText);
		      processDeleteSysResponse(xhr.responseText);
		    } else {
		      processDeleteSysResponse("N/A");
		    }
		  }; 
	}
	
	

	function processDeleteSysResponse(result) {
	  // Can grab any DIV or SPAN HTML element and can then manipulate its
	  // contents dynamically via javascript
		var js = JSON.parse(result);
		var code = js["code"];
		var num = js["totalDeleted"];
		if (code == 200){
			console.log("deleted :" + result);
			alert(num+ " schedules have been deleted.");
			//location.reload();
		}else{
			alert("No schedules deleted.");
		}
	}
