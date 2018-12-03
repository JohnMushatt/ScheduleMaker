package src.main.java.com.amazonaws.lambda.demo;

import src.main.java.com.amazonaws.lambda.model.Week;


public class ReviewScheduleHandler implements RequestStreamHandler {
	
	public LambdaLogger logger = null;
	
	public double loadWeek(String arg) throws Exception {
		double val = 0;
		val = loadValueFromRDS(arg);
		return val;
	}
	
	double loadValueFromRDS(String arg) throws Exception {
		if (logger != null) { logger.log("in loadValue"); }
		WeekDAO dao = new WeekDAO();
		Week w = dao.getWeek(arg);
		return w.value;
	}
	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
		LambdaLogger logger = context.getLogger();
		logger.log("Loading Java Lambda handler of RequestStreamHandler");

		JSONObject headerJson = new JSONObject();
		headerJson.put("Content-Type",  "application/json");  // not sure if needed anymore?
		headerJson.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
	    headerJson.put("Access-Control-Allow-Origin",  "*");
	        
		JSONObject responseJson = new JSONObject();
		responseJson.put("headers", headerJson);

		AddResponse response = null;
		
		// extract body from incoming HTTP POST request. If any error, then return 422 error
		String body;
		boolean processed = false;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			JSONParser parser = new JSONParser();
			JSONObject event = (JSONObject) parser.parse(reader);
			logger.log("event:" + event.toJSONString());
			
			body = (String)event.get("body");
			if (body == null) {
				body = event.toJSONString();  // this is only here to make testing easier
			}
		} catch (ParseException pe) {
			logger.log(pe.toString());
			response = new AddResponse(0, 422, pe.toString());  // unable to process input
	        responseJson.put("body", new Gson().toJson(response));
	        processed = true;
	        body = null;
		}

		if (!processed) {
			AddRequest req = new Gson().fromJson(body, AddRequest.class);
			logger.log(req.toString());

			boolean fail = false;
			String failMessage = "";
			double val1 = 0.0;
			try {
				val1 = Double.parseDouble(req.arg1);
			} catch (NumberFormatException e) {
				try {
					val1 = loadConstant(req.arg1);
				} catch (Exception ex) {
					failMessage = req.arg1 + " is an invalid constant.";
					fail = true;
				}
			}

			double val2 = 0.0;
			try {
				val2 = Double.parseDouble(req.arg2);
			} catch (NumberFormatException e) {
				try {
					val2 = loadConstant(req.arg2);
				} catch (Exception ex) {
					failMessage = req.arg2 + " is an invalid constant.";
					fail = true;
				}
			}

			// compute proper response
			AddResponse resp;
			if (fail) {
				resp = new AddResponse(0, 403, failMessage);
			} else {
				resp = new AddResponse(val1 + val2);  // success
			}
	        responseJson.put("body", new Gson().toJson(resp));  
		}
		
        logger.log("end result:" + responseJson.toJSONString());
        logger.log(responseJson.toJSONString());
        OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
        writer.write(responseJson.toJSONString());  
        writer.close();
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/*
    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException{
    	
    	logger = context.getLogger();
    	logger.log("Loading Java Lambda handler to create constant");
    	
    	JSONObject headerJson = new JSONObject();
    	headerJson.put("Content-Type", "application/json");
    	headerJson.put("Access-Control-Allow-Methods", "get,Post,OPTIONS");
    	headerJson.put("Access-Control-Allow-Origin", "*");
    	
    	JSONObject responseJson = new JSONObject();
    	responseJson.put("headers", headerJson);
    	ReviewScheduleResponse response = null;

    	String body;

    	boolean processed =false;
    	try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			JSONParser parser = new JSONParser();
			JSONObject event = (JSONObject) parser.parse(reader);
			logger.log("event:" + event.toJSONString());
			
//			String method = (String) event.get("httpMethod");
//			if (method != null && method.equalsIgnoreCase("OPTIONS")) {
//				logger.log("Options request");
//				response = new ReviewScheduleResponse("name", 200);  // OPTIONS needs a 200 response
//		        responseJson.put("body", new Gson().toJson(response));
//		        processed = true;
//		        body = null;
//			} else {
				body = (String)event.get("body");
				if (body == null) {
					body = event.toJSONString();  // this is only here to make testing easier
				}

//		} catch (ParseException pe) {
//			logger.log(pe.toString());
//			response = new ReviewScheduleResponse("Bad Request:" + pe.getMessage(), 422);  // unable to process input
//	        responseJson.put("body", new Gson().toJson(response));
//	        processed = true;
//	        body = null;
//		}

		if (!processed) {
			ReviewScheduleRequest req = new Gson().fromJson(body, ReviewScheduleRequest.class);
			logger.log(req.toString());

			ReviewScheduleResponse resp;
			try {
				Week w = getWeek();
				//database stuff, returns week
				resp = new ReviewScheduleResponse(w, 200);
			} catch (Exception e) {
				resp = new ReviewScheduleResponse(403);
			}

			// compute proper response
	        responseJson.put("body", new Gson().toJson(resp));  
		}
		
        logger.log("end result:" + responseJson.toJSONString());
        logger.log(responseJson.toJSONString());
        OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
        writer.write(responseJson.toJSONString());  
        writer.close();
        }
}
	*/