package com.bookapi.testcases;

import org.testng.annotations.Test;

import com.bookapi.assertions.WrappedAssert;
import com.bookapi.endPoints.EndPoints;
import com.bookapi.logs.WrappedReportLogger;
import com.bookapi.reportBuilder.ApiClient;
import com.bookapi.reportBuilder.RequestBuilder;

import io.restassured.response.Response;

public class IncorrectJsonFormat {
	
	
	@Test(priority=15, description = "Incorrectly formatted JSON file validation")
	 public void incorrectJSON() {
		WrappedReportLogger.trace("Sending an incorrectly validated JSON file....");
		
		String invalidJson = "{\n" +
				"  \"id\": 04121993,\n" +
				"  \"email\": \"vrushalsagane@gmail\",\n" +
				"  \"password\": \"vrushal123\",\n" +  // <- extra comma in end
				"}";
		
		Response response=ApiClient.post(RequestBuilder.withBodyAndNoAuthToken(invalidJson,null, null), EndPoints.SIGNUP, 422, "IncorrectJsonFomat.json");

		WrappedAssert.assertEquals(response.getHeader("server"), "uvicorn", "Validating Server header");
	    WrappedAssert.assertEquals(response.getHeader("content-type"), "application/json", "Validating Content-Type header");

		WrappedReportLogger.trace("Validated that user getting error on sending incorrect JSON!!!!");
		
		
		}

}
