function processDeleteResponse(result) {
  // Can grab any DIV or SPAN HTML element and can then manipulate its
  // contents dynamically via javascript
  console.log("deleted :" + result);
  
  refreshConstantsList();
}

function requestDelete(val) {
   if (confirm("Request to delete " + val)) {
     processDelete(val);
   }
}

function handleDeleteScheduleClick(thing) {
  var code = prompt("Please enter your secret code:", "Bananas");
  
  var data = {};
  data["secret"]

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
  }; **/
}
