import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.omg.CORBA_2_3.portable.OutputStream;
import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;
import com.amazonaws.lambda.demo.http.PostRequest;
import com.amazonaws.lambda.demo.http.PostResponse;

class ReviewScheduleHandlerTest {

    private static final String SAMPLE_INPUT_STRING = "{\"foo\": \"bar\"}";
    private static final String EXPECTED_OUTPUT_STRING = "{\"FOO\": \"BAR\"}";
    
    Context createContext(String apiCall) {
    	TestContext ctx = new TestContext();
    	ctx.setFunctionName(apiCall);
    	return ctx;
    }
	@Test
	public void testReviewScheduleHandler() throws IOException {
		ReviewScheduleHandler handler = new ReviewScheduleHandler();
		
		ReviewScheduleRequest csr = new ReviewScheduleRequest("1998-01-14", "123456781234567812"); //ADD SCHEDULE ID
		String addRequest = new Gson().toJson(csr);
		String jsonRequest = new Gson().toJson(new PostRequest(addRequest));
		
		InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
		OutputStream output = new ByteArrayOutputStream();
		
		handler.handleRequest(input, output, createContext("reviewSchedule"));
		
		PostResponse post = new Gson().fromJson(output.toString(), PostResponse.class);
		ReviewScheduleResponse resp = new Gson().fromJson(post.body, ReviewScheduleResponse.class);
	}

}
