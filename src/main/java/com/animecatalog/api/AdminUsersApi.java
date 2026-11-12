package com.animecatalog.api;

import com.animecatalog.models.UserRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * Author: cyqatester
 */

public class AdminUsersApi {

    private static final String PATH = "/api/admin/users";

    private final RequestSpecification request;

    public AdminUsersApi() {
        this(ApiClient.adminRequest());
    }

    public AdminUsersApi(RequestSpecification request) {
        this.request = request;
    }

    public Response list() {
        return RestAssured.given()
                .spec(request)
                .when()
                .get(PATH);
    }

    public Response search(String search) {
        return RestAssured.given()
                .spec(request)
                .queryParam("search", search)
                .when()
                .get(PATH);
    }

    public Response create(UserRequest payload) {
        return RestAssured.given()
                .spec(request)
                .body(payload)
                .when()
                .post(PATH);
    }

    public Response update(long id, UserRequest payload) {
        return RestAssured.given()
                .spec(request)
                .body(payload)
                .when()
                .put(PATH + "/" + id);
    }

    public Response delete(long id) {
        return RestAssured.given()
                .spec(request)
                .when()
                .delete(PATH + "/" + id);
    }
}
