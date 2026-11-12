package com.animecatalog.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * Author: cyqatester
 */

public class ProfileApi {
    private final RequestSpecification request;

    public ProfileApi() {
        this(ApiClient.adminRequest());
    }

    public ProfileApi(RequestSpecification request) {
        this.request = request;
    }

    public Response getProfile() {
        return RestAssured.given()
                .spec(request)
                .when()
                .get("/api/me");
    }
}
