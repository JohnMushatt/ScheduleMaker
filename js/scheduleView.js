

function displayOrgSchedule(scheduleId, code, tsd, startTime, endTime, startDate,tslist, meetinglist){
	var tableData = document.getElementById('table');
//var js = JSON.parse(result);

var startDate = makeMonday(startDate);
var output ="<table ><tr><th class='button' onclick='javascript:handleRefreshScheduleClick("+'"'+code+'","'+startDate+'"'+")'>Refresh</th>";
var day1 = startDate.substr(5,10);
var year = startDate.substr(0,4);
var startTime = parseInt(startTime.substr(0,2)+startTime.substr(3,4));
var endTime = parseInt(endTime.substr(0,2)+endTime.substr(3,4));
var hours = (endTime-startTime)/100;
var looper = ((hours*60)/tsd);
 		output = output + "<th class='button' onclick='javascript:handleCloseSlotsDayClick("+'"'+code+'",'+'"'+startDate+'",'+"1)'>Monday " + day1 + "</th>";
var day2 =nextDay(day1);
var clday2 = year+"-"+day2;
	output = output + "<th class='button' onclick='javascript:handleCloseSlotsDayClick("+'"'+code+'",'+'"'+clday2+'",'+"2)'>Tuesday " + day2+ "</th>";
var day3 = nextDay(day2);
var clday3 = year+"-"+day3;
 	output = output + "<th class='button' onclick='javascript:handleCloseSlotsDayClick("+'"'+code+'",'+'"'+clday3+'",'+"3)'>Wednesday " + day3+ "</th>";
var day4 = nextDay(day3);
var clday4 = year+"-"+day4;
 	output = output + "<th class='button' onclick='javascript:handleCloseSlotsDayClick("+'"'+code+'",'+'"'+clday4+'",'+"4)'>Thursday " + day4 + "</th>";
var day5 = nextDay(day4);
var clday5 = year+"-"+day5;
 	output = output + "<th class='button' onclick='javascript:handleCloseSlotsDayClick("+'"'+code+'",'+'"'+clday5+'",'+"5)'>Friday " + day5 + "</th></tr>";

 	var v = 0;
 	var i = 0;
 	for(i =0; i<looper; i++){
 		if(startTime < 1000){
 			var currentTime = "0"+ startTime.toString().substr(0,1)+":"+startTime.toString().substr(1,4);
 			output = output + "<tr> <th class='button' onclick='handleCloseSlotTimeClick("+'"'+code+'","'+startDate+'","'+currentTime+'"'+")'>0" + startTime.toString().substr(0,1)+":"+startTime.toString().substr(1,4)+ "</th>";
 		}else{
 			var currentTime = startTime.toString().substr(0,2)+":"+startTime.toString().substr(2,4);
 		output = output + "<tr> <th class='button' onclick='handleCloseSlotTimeClick("+'"'+code+'","'+startDate+'","'+currentTime+'"'+")'>" + startTime.toString().substr(0,2)+":"+startTime.toString().substr(2,4)+ "</th>";
 		
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
 

 var year= startDate.substr(0,4);
 var newDate = year+"-"+nextDay(nextDay(nextDay(day5)));
 var pastDate = year+"-"+previousDay(previousDay(previousDay(previousDay(previousDay(previousDay(day1))))));
 var buttons = document.getElementById("buttonBar");
 buttons.innerHTML = "<center> " +
 		"<input type='button' value='Create Schedule' onClick='JavaScript:handleCreateClick(this)'> " +
 		"<input type='button' value='Find Schedule' onClick='JavaScript:handleFindScheduleClick()'> " +
 		"<input type='button' value='Previous Week' onClick='JavaScript:handleNextWeekClick("+'"'+code+'",'+'"'+pastDate+'"'+")'> " +
	 "<input type='button' value='Next Week' onClick='JavaScript:handleNextWeekClick("+'"'+code+'",'+'"'+newDate+'"'+")'> " +
		"<input type='button' value='Extend Date' onClick='JavaScript:handleExtendDateClick(this)'> " +
		"<input type='button' value='Delete Schedule' onClick='JavaScript:handleDeleteScheduleClick()'>" +
"</center>"
}


function displayParticipantSchedule(code, sId, tsd, startTime, endTime, startDate, tslist, meetinglist){
	var tableData = document.getElementById('table');
//var js = JSON.parse(result);

var startDate = makeMonday(startDate);
var output ="<table ><tr><th class='button' onclick='javascript:handleRefreshSchedulePartClick("+'"'+code+'",'+'"'+startDate+'"'+")'>Refresh</th>";
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
 		
 		 var year= startDate.substr(0,4);
 		 var newDate = year+"-"+nextDay(nextDay(nextDay(day5)));
 		 var pastDate = year+"-"+previousDay(previousDay(previousDay(previousDay(previousDay(previousDay(day1))))));
 		 var buttons = document.getElementById("buttonBar");
 		 buttons.innerHTML = "<center> " +
 		 		"<input type = 'button' value = 'Find Schedule' onClick = 'handleFindSchedulePartClick()'>" +
 		 		"<input type='button' value='Previous Week' onClick='JavaScript:handleNextWeekPartClick("+'"'+code+'",'+'"'+pastDate+'"'+")'> " +
 			 "<input type='button' value='Next Week' onClick='JavaScript:handleNextWeekPartClick("+'"'+code+'",'+'"'+newDate+'"'+")'> " +
 				"<input type = 'button' value = 'Search for a Time' onClick ='javascript:handleTimeSearchClick(this)'> " +
 		"</center>"
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


function previousDay(date){
    //var date = "04-28"
    var month = parseInt(date);
    var newMonth = 0;
    var newDate = 0;
	var day = date.substr(3, 4);
	if(month == 1 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
		{
		if(day == 1){
			if(month == 1){
				newMonth = "12";
				newDate = newMonth+"-31";
			}
		} else {
			if(month<10){
				newMonth = "0"+month;
			}
			else {
				newMonth = month;
			}
			if(day == 1){
				newDate = newMonth+"-30";
			}
			if(day>=11){ 
				newDate = newMonth+"-"+(parseInt(day)-1);
			} else { 
				newDate = newMonth+"-0"+(parseInt(day)-1);
				}
		}
	}
	else if(month == 3) {
		if(day == 1){ 
			newDate = "02"+"-28"; 
		} else { 
			if(day>=11){ 
				newDate = "0"+month+"-"+(parseInt(day)-1);
			} else { 
				newDate = "0"+month+"-0"+(parseInt(day)-1);
			}
		}
	}
	else {
		if( day == 1){
			newMonth = month - 1;
			if(newMonth < 10){
				newMonth = "0"+newMonth;
			}
              newDate = newMonth+"-31";
		} else{
			if(month<10){
				month = "0"+month;
			}
                if(day>=11)
				{
                    newDate = month+"-"+(parseInt(day)-1);
				}else{
					newDate = month+"-0"+(parseInt(day)-1);
				}
			}		
	}
	console.log(newDate);
	//alert();
	return newDate;
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
			if(month == 12){
				newMonth = "01";
				newDate = newMonth+"-01";
			} else{
			newMonth = month + 1;
			newDate = newMonth+"-01";
			}
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

function makeMonday(date){
	var year = date.substr(0,4);
	var mmonth = parseInt(date.substr(5,7))-1;
	var mdate = parseInt(date.substr(8,10));
	var adate = new Date();
	adate.setFullYear(year);
	adate.setMonth(mmonth);
	adate.setDate(mdate);
	var day = adate.getDay();
	var goodDate = date.substr(5,10);
	if(day != 1){
		for(i=0; i<day-1;i++){
		goodDate = previousDay(goodDate);
		}
	return year+"-"+goodDate;
	}
	else return date;
}