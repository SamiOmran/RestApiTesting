package TestClasses;

import static org.junit.Assert.*;

import java.net.HttpURLConnection;

import org.json.simple.JSONObject;
import org.junit.Ignore;
import org.junit.Test;

import Links.FilesPaths;
import Links.URLs;
import Utils.JSONUtils;
import enums.HTTPMethod;
import enums.HTTPRequestsContentTypes;
import enums.ResponseFields;
import requestHandling.RestClientHandler;

public class TestPostMethod {
	@Ignore
	@Test
	public void testSendPost() throws Exception {	
		// preparing arguments.
		String url = URLs.APILINK;
		String filePath = FilesPaths.CreatPostJSONFile;
		String request_json = JSONUtils.readJSONObjectFromFile(filePath);	
		HTTPRequestsContentTypes content = HTTPRequestsContentTypes.JSON;
		HTTPMethod post = HTTPMethod.POST;

		//1. connect 
		HttpURLConnection connection = RestClientHandler.connectServer(url, post, content);
		int expected_code = 201, actual_code = 0;

		//2. post
		RestClientHandler.sendPost(connection, request_json, content);
		
		//3. get response as a string
		String responseStr = RestClientHandler.readResponse(connection);
		
		//3.1 convert response to json
		JSONObject actual_response = (JSONObject) JSONUtils.convertStringToJSON(responseStr);
		
		// remove id field to compare
		long id = (long) actual_response.remove("id");
		JSONObject expected_request = (JSONObject) JSONUtils.convertStringToJSON(request_json);
		
		// check if the connection is ok (201)
		actual_code = connection.getResponseCode();
		assertEquals(expected_code, actual_code);
		
		// check if the response is the same as the json added
		assertEquals(actual_response, expected_request);
		System.out.println(actual_response);

		url += "/"+id;
		connection = RestClientHandler.connectServer(url, post, content);
		RestClientHandler.sendGet(connection, "", content);
		//responseStr = RestClientHandler.readResponse(connection);
		//actual_response = (JSONObject) JSONUtils.convertStringToJSON(responseStr);
		//System.out.print(actual_response);
		
		expected_code = 200;
		actual_code = 0;
		
		actual_code = connection.getResponseCode();
		assertEquals("Can't get the added post",actual_code, expected_code);
	}
	
	
	@Test
	public void testPostEmpty() throws Exception{
		// preparing arguments.
		String url = URLs.APILINK;
		String filePath = FilesPaths.CreateEmptyPostJSONFile;
		
		String request_as_str = JSONUtils.readJSONObjectFromFile(filePath);	
		JSONObject request_as_json = (JSONObject) JSONUtils.convertStringToJSON(request_as_str);

		HTTPRequestsContentTypes content = HTTPRequestsContentTypes.JSON;
		HTTPMethod post = HTTPMethod.POST;
		
		HttpURLConnection connection = RestClientHandler.connectServer(url, post, content);
		RestClientHandler.sendPost(connection, request_as_str, content);

		String response_str = RestClientHandler.readResponse(connection);
		JSONObject response_json = (JSONObject) JSONUtils.convertStringToJSON(response_str);
		response_json.remove("id");
		
		assertEquals(response_json, request_as_json);
		System.out.println(response_json);
	}
	
	@Test
	public void testPost2() throws Exception{
		// preparing arguments.
		String url = URLs.APILINK;
		String filePath = FilesPaths.CreatePost2;
		
		String request_as_str = JSONUtils.readJSONObjectFromFile(filePath);	
		JSONObject request_as_json = (JSONObject) JSONUtils.convertStringToJSON(request_as_str);

		HTTPRequestsContentTypes content = HTTPRequestsContentTypes.JSON;
		HTTPMethod post = HTTPMethod.POST;
		
		HttpURLConnection connection = RestClientHandler.connectServer(url, post, content);
		RestClientHandler.sendPost(connection, request_as_str, content);
		
		
		String response_str = RestClientHandler.readResponse(connection);
		JSONObject response_json = (JSONObject) JSONUtils.convertStringToJSON(response_str);
		response_json.remove("id");
		
		assertEquals(response_json, request_as_json);
		System.out.println(response_json);
	}
}
