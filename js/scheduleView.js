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

function displayOrgSchedule(result){
var js = JSON.parse(result);

var output ="<table><tr><TH />"
var dates = "11-14"
 		output = output + "<th class='button'>Monday" + dates + "</th>"
var dates = nextDay(dates);
	output = output + "<th class='button'>Tuesday " + dates+ "</th>"
var dates = nextDay(dates);
 	output = output + "<th class='button'>Wednesday " + dates+ "</th>"
var dates = nextDay(dates);
 	output = output + "<th class="button">Thursday " + dates "</th>"
var dates = nextDay(dates);
 	output = output + "<th class="button">Friday " + dates "</th></tr>"

 	<script type="text/javascript">
 	for(var k=0; k<6; k++){
 	document.write("<tr class='button'>")
 	 		document.write("<th>Time</th>");
 	 		for (var j=0; j<5; j++){
 	 	 		document.write("<td class='button'>test</td>")
 	 	 		}
	 	document.write("</tr>")
	 	}
 	</script>
 	
 	
 </table>
}

function nextDay(date){
	var month = parseInt(date);
	var date = date.substr(3, 4);
	if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
		{
		if(day == 31)
			{
			var newMonth = month + 1;
			return newMonth+"-01"
			}
		else
			{
		return month+"-"(day+1)
			}
		}
	if(month == 2 && day == 28)
		{
		return (month+1)+"-01"
		} else {
		return month+"-"+(day+1);
		} 
	else{
		if( day == 30){
			var newMonth = month + 1;
			return newMonth+"-01"
		} else{
			return month+"-"(day+1)
		}
			
	}
}
