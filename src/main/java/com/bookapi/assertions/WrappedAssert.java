package com.bookapi.assertions;

import java.io.File;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class WrappedAssert {

    // Existing assertEquals method
    public static void assertEquals(Object actual, Object expected, String message) {
        if ((actual == null && expected != null) || (actual != null && !actual.equals(expected))) {
            throw new AssertionError(message + " - Expected: " + expected + ", but got: " + actual);
        } else {
            System.out.println(message + " - Passed");
        }
    }

    // New reusable method for validating headers
    public static void assertHeader(Response response, String headerName, String expectedValue) {
        String actualValue = response.getHeader(headerName);
        assertEquals(actualValue, expectedValue, "Validating header: " + headerName);
    }

    // Optional: Overload to allow custom message
    public static void assertHeader(Response response, String headerName, String expectedValue, String message) {
        String actualValue = response.getHeader(headerName);
        assertEquals(actualValue, expectedValue, message);
    }
    public static void assertJsonSchema(Response response, String schemaFilePath, String message) {
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File(schemaFilePath)));
        System.out.println(message + " - Passed");
}}
