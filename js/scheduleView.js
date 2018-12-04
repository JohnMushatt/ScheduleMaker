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

function displayOrgSchedule(){
	var tableData = document.getElementById('table');
//var js = JSON.parse(result);

var output ="<table><tr><TH />";
var dates = "04-28";
var startTime = 1000;
var tsd = 20;
var hours = (1600-startTime)/100;
var id = 143532;
var looper = (1+(hours*60)/tsd);
 		output = output + "<th class='button'>Monday " + dates + "</th>";
var dates = nextDay(dates);
	output = output + "<th class='button'>Tuesday " + dates+ "</th>";
var dates = nextDay(dates);
 	output = output + "<th class='button'>Wednesday " + dates+ "</th>";
var dates = nextDay(dates);
 	output = output + "<th class='button'>Thursday " + dates + "</th>";
var dates = nextDay(dates);
 	output = output + "<th class='button'>Friday " + dates + "</th></tr>";

 	for(var i =0; i<looper; i++){
 		output = output + "<tr> <th class='button'>" + startTime.toString().substr(0,2)+":"+startTime.toString().substr(2,3)+ "</th>";
 		for(var k=0; k<5; k++){
 			output = output + "<td class='button' id="+id+">open</td>";
 		}
 		output = output + "</tr>"
 		if(startTime.toString().substr(2,3) == (60-tsd)){
 			startTime=startTime+40+tsd;
 		}else{
 		startTime=startTime+tsd;
 		}
 	}
 	
 	
 	
output = output + "</table>";
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
			if(day>=10){ 
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
			if(month<10){
				month = "0"+month;
			}
			if(day>=10){ 
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
                if(day>=10)
				{
                    newDate = month+"-"+(parseInt(day)+1);
				}else{
					newDate = month+"-0"+(parseInt(day)+1);
				}
			}		
	}
	console.log(newDate);
	//alert(newDate);
	return newDate;
}