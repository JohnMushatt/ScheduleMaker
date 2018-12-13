function refreshScheduleView(){
	var xhr = new XMLHttpRequest();
	   xhr.open("GET", list_url, true);
	   xhr.send();
	   
	   console.log("sent");

	  // This will process results and update HTML as appropriate. 
	  xhr.onloadend = function () {
	    if (xhr.readyState == XMLHttpRequest.DONE) {
	      console.log ("XHR:" + xhr.responseText);
	      processListResponse(xhr.responseText);
	    } else {
	      processListResponse("N/A");
	    }
	  };
	}

function displayOrgSchedule(code, tsd, startTime, endTime, startDate,tslist, meetinglist){
	var tableData = document.getElementById('table');
//var js = JSON.parse(result);

var output ="<table ><tr><th class='button' onclick='javascript:handleRefreshScheduleClick("+'"'+code+'"'+")'>Refresh</th>";
var day1 = startDate.substr(5,10);
var startTime = parseInt(startTime.substr(0,2)+startTime.substr(3,4));
var endTime = parseInt(endTime.substr(0,2)+endTime.substr(3,4));
var hours = (endTime-startTime)/100;
var looper = ((hours*60)/tsd);
 		output = output + "<th class='button'>Monday " + day1 + "</th>";
var day2 = nextDay(day1);
	output = output + "<th class='button'>Tuesday " + day2+ "</th>";
var day3 = nextDay(day2);
 	output = output + "<th class='button'>Wednesday " + day3+ "</th>";
var day4 = nextDay(day3);
 	output = output + "<th class='button'>Thursday " + day4 + "</th>";
var day5 = nextDay(day4);
 	output = output + "<th class='button'>Friday " + day5 + "</th></tr>";

 	var v = 0;
 	var i = 0;
 	for(i =0; i<looper; i++){
 		if(startTime < 1000){
 			output = output + "<tr> <th>0" + startTime.toString().substr(0,1)+":"+startTime.toString().substr(1,4)+ "</th>";
 			var currentTime = "0"+ startTime.toString().substr(0,1)+":"+startTime.toString().substr(1,4);
 		}else{
 		output = output + "<tr> <th>" + startTime.toString().substr(0,2)+":"+startTime.toString().substr(2,4)+ "</th>";
 		var currentTime = startTime.toString().substr(0,2)+":"+startTime.toString().substr(2,4);
 		}
 		var k = 0;
 		for(k=0; k<5; k++){
 			var slot = findTimeSlot(currentTime, k+1, tslist);
 			if(slot == null){
 				output= output + "<td></td>"
 			} else{
 				var id = slot["timeSlotID"];
 				var stringId = String(id);
 				//console.log("this is the id" + stringId);
 				var isopen = slot["isOpen"];
 				var isbooked = slot["isBooked"];
 				if(isbooked == 1){
 					var meet = findMeeting(id, meetinglist);
 					var name = meet["participantName"];
 					var mId = meet["meetingID"];
 					output = output + "<td id='"+stringId+"' style='background-color:#67A6C5' onclick='javascript:handleDeleteMeetingOrgClick("+'"'+code+'",'+'"'+mId+'"'+")'>"+name+"</td>" 
 					v++;
 				} else{
 					if(isopen == 1){
 						output = output + "<td class='button' id='"+stringId+"' onclick='javascript:handleTimeSlotClick("+'"'+stringId+'"'+")'>open</td>";
 						//console.log(output);
 					} else{
 						output = output + "<td class='button' style='background-color:#E5771E' id='"+stringId+"' onclick='javascript:handleTimeSlotClick("+'"'+stringId+'"'+")'>closed</td>";
 					}
 					v++;
 				}
 			}
 		}
 		output = output + "</tr>"
 		if(startTime < 1000){
 			if(startTime.toString().substr(1,4) == (60-tsd)){
 	 			startTime=startTime+40+tsd;
 	 		}else{
 	 		startTime=startTime+tsd;
 	 		}
 		}else {
 			if(startTime.toString().substr(2,4) == (60-tsd)){
 				startTime=startTime+40+tsd;
 			}else{
 				startTime=startTime+tsd;
 			}
 		}
 	}
 	
 	
 	
output = output + "</table>";
console.log(output);
 	tableData.innerHTML = output;
}

function nextDay(date){
    //var date = "04-28"
    var month = parseInt(date);
    var newMonth = 0;
    var newDate = 0;
	var day = date.substr(3, 4);
	if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
		{
		if(day == 31){
			newMonth = month + 1;
			newDate = newMonth+"-01";
		} else {
			if(month<10){
				month = "0"+month;
			}
			if(day>=9){ 
				newDate = month+"-"+(parseInt(day)+1);
			} else { 
				newDate = month+"-0"+(parseInt(day)+1);
				}
		}
	}
	else if(month == 2) {
		if(day == 28){ 
			newDate = "0"+(month+1)+"-01"; 
		} else { 
			if(day>=9){ 
				newDate = "0"+month+"-"+(parseInt(day)+1);
			} else { 
				newDate = "0"+month+"-0"+(parseInt(day)+1);
			}
		}
	}
	else {
		if( day == 30){
			newMonth = month + 1;
			if(newMonth < 10){
				newMonth = "0"+newMonth;
			}
			return newMonth+"-01";
		} else{
			if(month<10){
				month = "0"+month;
			}
                if(day>=9)
				{
                    newDate = month+"-"+(parseInt(day)+1);
				}else{
					newDate = month+"-0"+(parseInt(day)+1);
				}
			}		
	}
	console.log(newDate);
	//alert();
	return newDate;
}

function displayParticipantSchedule(code, sId, tsd, startTime, endTime, startDate, tslist, meetinglist){
	var tableData = document.getElementById('table');
//var js = JSON.parse(result);

var output ="<table ><tr><th class='button' onclick='javascript:handleRefreshSchedulePartClick("+'"'+code+'"'+")'>Refresh</th>";
var day1 = startDate.substr(5,10);
//var startTime = 1000;
//var tsd = 20;
var startTime = parseInt(startTime.substr(0,2)+startTime.substr(3,4));
var endTime = parseInt(endTime.substr(0,2)+endTime.substr(3,4));
var hours = (endTime-startTime)/100;
//var id = 'slot';
var looper = ((hours*60)/tsd);
 		output = output + "<th>Monday " + day1 + "</th>";
var day2 = nextDay(day1);
	output = output + "<th>Tuesday " + day2+ "</th>";
var day3 = nextDay(day2);
 	output = output + "<th>Wednesday " + day3+ "</th>";
var day4 = nextDay(day3);
 	output = output + "<th>Thursday " + day4 + "</th>";
var day5 = nextDay(day4);
 	output = output + "<th>Friday " + day5 + "</th></tr>";

 	var v = 0;
 	var i = 0;
 	for(i =0; i<looper; i++){
 		if(startTime < 1000){
 			output = output + "<tr> <th>0" + startTime.toString().substr(0,1)+":"+startTime.toString().substr(1,4)+ "</th>";
 			var currentTime = "0"+ startTime.toString().substr(0,1)+":"+startTime.toString().substr(1,4);
 		}else{
 		output = output + "<tr> <th>" + startTime.toString().substr(0,2)+":"+startTime.toString().substr(2,4)+ "</th>";
 		var currentTime = startTime.toString().substr(0,2)+":"+startTime.toString().substr(2,4);
 		}
 		
 		var k = 0;
 		for(k=0; k<5; k++){
 			//var slot = tslist[v];
 			var slot = findTimeSlot(currentTime, k+1, tslist);
 			if(slot == null){
 				output= output + "<td></td>"
 			} else{
 				var id = slot["timeSlotID"];
 				var stringId = String(id);
 				//console.log("this is the id" + stringId);
 				var isopen = slot["isOpen"];
 				var isbooked = slot["isBooked"];
 				if(isbooked == 1){
 					var meet = findMeeting(id, meetinglist);
 					var name = meet["participantName"];
 					var mId = meet["meetingID"];
 					output = output + "<td id='"+mId+"' class='button' style='background-color:#67A6C5' onclick='javascript:handleDeleteMeetingParticipantClick("+'"'+code+'",'+'"'+mId+'"'+")'>"+name+"</td>" 
 					v++;
 				} else{
 					if(isopen == 1){
 						output = output + "<td class='button' id='"+stringId+"' onclick='javascript:handleCreateMeetingParticipantClick("+'"'+code+'",'+'"'+sId+'",'+'"'+stringId+'"'+")'>open</td>";
 						//console.log(output);
 					} else{
 						output = output + "<td style='background-color:#E5771E' id='"+stringId+"'>closed</td>";
 					}
 					v++;
 					}
 				}
 			}
 		output = output + "</tr>"
 		if(startTime < 1000){
 			if(startTime.toString().substr(1,4) == (60-tsd)){
 	 			startTime=startTime+40+tsd;
 	 		}else{
 	 		startTime=startTime+tsd;
 	 		}
 		}else {
 			if(startTime.toString().substr(2,4) == (60-tsd)){
 				startTime=startTime+40+tsd;
 			}else{
 				startTime=startTime+tsd;
 			}
 		}
 	}
 	
 	
 	
output = output + "</table>";
console.log(output);
 	tableData.innerHTML = output;
}

function findMeeting(id, list){
	for(i=0; i<list.length; i++){
		if(id == list[i]["timeSlotID"]){
			return list[i];
		}
	}
}

function findTimeSlot(time, day, list){
	for(i=0; i<list.length; i++){
		var listTime = list[i]["startTime"];
		var listDay = list[i]["dayOfWeek"];
		if(listTime == time && listDay == day){
			return list[i];
		} 
	}
	return null;
}