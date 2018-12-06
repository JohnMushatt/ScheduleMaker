/**
 * 
 */
function closeSlot(data){
	if(document.getElementById(data).innerHTML == "closed"){
		document.getElementById(data).innerHTML  = "open";
		document.getElementById(data).style.backgroundColor = "ivory"
	} else{
		
	document.getElementById(data).innerHTML = "closed";
	document.getElementById(data).style.backgroundColor = "cyan"
	}
}

function handleTimeSlotClick(val) {
	  var data = {};
	  data["name"] = val;

	  var js = JSON.stringify(data);
	  console.log("JS:" + js);
	  var xhr = new XMLHttpRequest();
	  xhr.open("DELETE", delete_url, true);

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
