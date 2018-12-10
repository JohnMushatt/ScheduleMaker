/**
 * 
 */
function closeSlot(num){
	console.log(num);
	if(document.getElementById(num).innerHTML == "closed"){
		document.getElementById(num).innerHTML  = "open";
		document.getElementById(num).style.backgroundColor = "#ffecb4"
	} else{
		
	document.getElementById(num).innerHTML = "closed";
	document.getElementById(num).style.backgroundColor = "#e5771e"
	}
}

function handleTimeSlotClick(val) {
	  var data = {};
	  data["tsId"] = val;

	  var js = JSON.stringify(data);
	  console.log("JS:" + js);
	  var xhr = new XMLHttpRequest();
	  xhr.open("POST", delete_url, true);

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
