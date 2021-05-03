package TestClasses;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Ignore;
import org.junit.Test;

import Links.FilesPaths;
import Links.URLs;
import Utils.JSONUtils;
import enums.HTTPMethod;
import enums.HTTPRequestsContentTypes;
import requestHandling.RestClientHandler;

public class TestPutMethod {

	@Test
	public void testSendPut() throws Exception, ParseException {
		String url = URLs.APILINK + "/1";
		String filePath = FilesPaths.UpdatePostJSONFile;
		String request_as_string = JSONUtils.readJSONObjectFromFile(filePath);
		
		JSONObject request_as_json = (JSONObject)JSONUtils.convertStringToJSON(request_as_string);
		HTTPRequestsContentTypes content = HTTPRequestsContentTypes.JSON;
		
		HTTPMethod put = HTTPMethod.PUT;
		HTTPMethod get = HTTPMethod.GET;

		HttpURLConnection connection = RestClientHandler.connectServer(url, get, content);
		String response_before =  RestClientHandler.readResponse(connection);
		JSONObject response_before_asJson = (JSONObject)JSONUtils.convertStringToJSON(response_before);
		System.out.println("Before update: "+ response_before_asJson);
		// connect 
		 connection = RestClientHandler.connectServer(url, put, content);
		int expected_code = 200, actual_code = 0;
		
		System.out.println();
		// send put request
		RestClientHandler.sendPut(connection, request_as_string, content);
		System.out.println();

		actual_code = connection.getResponseCode();
		// check connection is ok
		assertEquals(expected_code, actual_code);

		// get response after update
		String response_str = RestClientHandler.readResponse(connection);
		JSONObject response_json = (JSONObject)JSONUtils.convertStringToJSON(response_str);
		response_json.remove("id");
		System.out.println("After update: "+response_json);
		System.out.println();
		
		// check if request the same as response
		assertEquals(response_json,request_as_json);
		System.out.println();
		
		// get response from GET method
		connection = RestClientHandler.connectServer(url, get, content);
		//RestClientHandler.sendGet(connection, "", content);
		
		response_str = RestClientHandler.readResponse(connection);		
		response_json = (JSONObject)JSONUtils.convertStringToJSON(response_str);
	}

	@Test
	public void testPutInvalidPost() throws Exception {
		String url = URLs.APILINK + "/110";
		String filePath = FilesPaths.UpdatePostJSONFile;
		String request_as_string = JSONUtils.readJSONObjectFromFile(filePath);
		// preparing json
		JSONObject request_as_json = (JSONObject)JSONUtils.convertStringToJSON(request_as_string);
		
		HTTPRequestsContentTypes content = HTTPRequestsContentTypes.JSON;
		HTTPMethod put = HTTPMethod.PUT;
		HttpURLConnection connection = RestClientHandler.connectServer(url, put, content);

		RestClientHandler.sendPut(connection, request_as_string, content);
		assertNotEquals(connection.getResponseCode(), 200);
	}
}
