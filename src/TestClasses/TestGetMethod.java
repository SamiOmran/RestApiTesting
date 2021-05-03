package TestClasses;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Ignore;
import org.junit.Test;

import Links.FilesPaths;
import Links.URLs;
import Utils.JSONUtils;
import enums.HTTPMethod;
import enums.HTTPRequestsContentTypes;
import requestHandling.RestClientHandler;

public class TestGetMethod {
	@Ignore
	@Test
	public void testGetAllPosts() throws IOException, Exception {
		String url = URLs.APILINK;
		HTTPRequestsContentTypes content = HTTPRequestsContentTypes.JSON;
		HTTPMethod get = HTTPMethod.GET;
		
		int expected_code = 200, actual_code = 0;
		
		// connect
		HttpURLConnection connection = RestClientHandler.connectServer(url, get, content);

		// send get
		//RestClientHandler.sendGet(connection, "", HTTPRequestsContentTypes.JSON);
		
		// check if the connection is ok
		actual_code = connection.getResponseCode();
		assertEquals(actual_code, expected_code);
		
		String response_str = RestClientHandler.readResponse(connection);
		JSONArray response_json = (JSONArray)JSONUtils.convertStringToJSON(response_str);
		print(response_json);
}
	
	@Test
	public void testGetPost() throws IOException, Exception {
		String url = URLs.APILINK + "/1";
		HTTPRequestsContentTypes content = HTTPRequestsContentTypes.JSON;
		HTTPMethod get = HTTPMethod.GET;
		
		int expected_code = 200, actual_code = 0;
		
		// connect
		HttpURLConnection connection = RestClientHandler.connectServer(url, get, content);
		
		// send get
		RestClientHandler.sendGet(connection, "", HTTPRequestsContentTypes.JSON);
		
		// check if the connection is ok
		actual_code = connection.getResponseCode();
		assertEquals(actual_code, expected_code);
		
		String response_str = RestClientHandler.readResponse(connection);
		System.out.println(response_str);
		JSONObject response_json = (JSONObject)JSONUtils.convertStringToJSON(response_str);

		print(response_json);
	}
	
	@Test
	public void testGetInvalidPost() throws Exception {
		String url = URLs.APILINK + "/101";
		HTTPRequestsContentTypes content = HTTPRequestsContentTypes.JSON;
		HTTPMethod get = HTTPMethod.GET;
		HttpURLConnection connection = RestClientHandler.connectServer(url, get, content);
		
		int expected_code = 404, actual_code = connection.getResponseCode();
		assertEquals(expected_code, actual_code);
	}
	
	public static void print(Object o) {
		System.out.println(o);
	}

}
