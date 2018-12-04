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
var dates = "11-14";
var startTime = 1000;
var tsd = 15;
var hours = (1600-startTime)/100;
var id = 143532;

 		output = output + "<th class='button'>Monday " + dates + "</th>";
var dates = nextDay(dates);
	output = output + "<th class='button'>Tuesday " + dates+ "</th>";
var dates = nextDay(dates);
 	output = output + "<th class='button'>Wednesday " + dates+ "</th>";
var dates = nextDay(dates);
 	output = output + "<th class='button'>Thursday " + dates + "</th>";
var dates = nextDay(dates);
 	output = output + "<th class='button'>Friday " + dates + "</th></tr>";

 	for(var i =0; i<hours; i++){
 		output = output + "<tr> <th class='button'>" + startTime + "</th>";
 		for(var k=0; k<5; k++){
 			output = output + "<td class='button' id="+id+">open</td>";
 		}
 		output = output + "</tr>"
 		startTime=startTime+tsd;
 	}
 	/**<script type="text/javascript">
 	for(var k=0; k<6; k++){
 	document.write("<tr class='button'>")
 	 		document.write("<th>Time</th>");
 	 		for (var j=0; j<5; j++){
 	 	 		document.write("<td class='button'>test</td>")
 	 	 		}
	 	document.write("</tr>")
	 	}
 	</script> **/
 	
 	
output = output + "</table>";
 	tableData.innerHTML = output;
}

function nextDay(date){
    //var date = "02-27"
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
			if(day>=10){ 
				newDate = month+"-"+(parseInt(day)+1);
			} else { 
				newDate = month+"-0"+(parseInt(day)+1);
				}
		}
	}
	else if(month == 2) {
		if(day == 28){ 
			newDate = (month+1)+"-01"; 
		} else { 
			if(day>=10){ 
				newDate = month+"-"+(parseInt(day)+1);
			} else { 
				newDate = month+"-0"+(parseInt(day)+1);
			}
		}
	}
	else {
		if( day == 30){
			newMonth = month + 1;
			return newMonth+"-01";
		} else{
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