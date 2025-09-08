package com.bookapi.reportBuilder;

import com.bookapi.assertions.WrappedAssert;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ApiClient {

    private static Response sendRequest(String method, String endpoint,
                                        RequestSpecification spec,
                                        int expectedStatus,
                                        String schemaFileName) {

        Response response;

        switch (method.toUpperCase()) {
            case "GET":
                response = RestAssured.given().spec(spec)
                        .when().log().all().get(endpoint)
                        .then().log().all()
                        .extract().response();
                break;

            case "POST":
                response = RestAssured.given().spec(spec)
                        .when().log().all().post(endpoint)
                        .then().log().all()
                        .extract().response();
                break;

            case "PUT":
                response = RestAssured.given().spec(spec)
                        .when().log().all().put(endpoint)
                        .then().log().all()
                        .extract().response();
                break;

            case "DELETE":
                response = RestAssured.given().spec(spec)
                        .when().log().all().delete(endpoint)
                        .then().log().all()
                        .extract().response();
                break;

            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }

        // Common validations
        WrappedAssert.assertEquals(response.getStatusCode(), expectedStatus,
                "Validating HTTP Status code: " + expectedStatus);

        if (schemaFileName != null && !schemaFileName.trim().isEmpty()) {
            WrappedAssert.assertJsonSchema(response, "schemas/" + schemaFileName,
                    "Validating JSON schema for " + method + " " + endpoint);
        }

        return response;
    }

    public static Response get(RequestSpecification spec, String endpoint,
                               int expectedStatus, String schemaFileName) {
        return sendRequest("GET", endpoint, spec, expectedStatus, schemaFileName);
    }

    public static Response post(RequestSpecification spec, String endpoint,
                                int expectedStatus, String schemaFileName) {
        return sendRequest("POST", endpoint, spec, expectedStatus, schemaFileName);
    }

    public static Response put(RequestSpecification spec, String endpoint,
                               int expectedStatus, String schemaFileName) {
        return sendRequest("PUT", endpoint, spec, expectedStatus, schemaFileName);
    }

    public static Response delete(RequestSpecification spec, String endpoint,
                                  int expectedStatus, String schemaFileName) {
        return sendRequest("DELETE", endpoint, spec, expectedStatus, schemaFileName);
    }
}
