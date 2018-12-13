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
