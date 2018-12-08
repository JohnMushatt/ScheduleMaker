/**
 * 
 */

function processSearchResponse(arg1, arg2, result) {
  // Can grab any DIV or SPAN HTML element and can then manipulate its
  // contents dynamically via javascript
  console.log("result:" + result);
  var js = JSON.parse(result);

  var computation = js["value"];
  var status      = js["httpCode"];
  
  if (status == 200) {
    // Update computation result
    document.addForm.result.value = computation
  } else {
    var msg = js["errorMessage"];
    document.searchForm.result.value = "error:" + msg
  }
}

function handleFindTimeClick(e) {
  var form = document.findTimeForm;
  var Month = form.Month.value;
  var DayofWeek = form.DayofWeek.value;
  var TimeSlot = form.TimeSlot.value;
  var Year = form.Year.value;

  var data = {};
  data["Month"] = Month;
  data["DayofWeek"] = DayofWeek;
  data["TimeSlot"] = TimeSlot;
  data["Year"] = Year;

  var js = JSON.stringify(data);
  console.log("JS:" + js);
  var xhr = new XMLHttpRequest();
  xhr.open("POST", search_url, true);

  // send the collected data as JSON
  xhr.send(js);

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
    console.log(xhr);
    console.log(xhr.request);
    
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processAddResponse(arg1, arg2, xhr.responseText);
    } else {
      processAddResponse(arg1, arg2, "N/A");
    }
  };
}
