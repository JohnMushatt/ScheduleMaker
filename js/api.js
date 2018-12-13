// all access driven through BASE
var base_url = "https://ki27k4tu62.execute-api.us-east-2.amazonaws.com/Alpha/"; 

var add_url    = base_url + "calculator";   // POST
var createSchedule_url = base_url + "scheduler";       // POST
var deleteSchedule_url = base_url + "deleteschedule";    // DELETE
var list_url   = base_url + "constants";    // GET
var reviewSchedule_url = base_url + "reviewschedule"; //GET
var editTimeSlot_url = base_url + "edittimeslot"; //POST
var participantReview_url = base_url + "participantreviewschedule"; //POST
var createMeeting_url = base_url + "createmeeting"; //POST
var deleteMeetingParticipant_url = base_url + "deletemeeting"; //POST