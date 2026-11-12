package com.animecatalog.api;

import com.animecatalog.models.AnimeRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * Author: cyqatester
 */

public class AnimeApi {
    private static final String PATH = "/api/anime";
    private final RequestSpecification request;

    public AnimeApi() {
        this(ApiClient.adminRequest());
    }

    public AnimeApi(RequestSpecification request) {
        this.request = request;
    }

    public Response list() {
        return RestAssured.given()
                .spec(request)
                .when()
                .get(PATH);
    }

    public Response search(String title) {
        return RestAssured.given()
                .spec(request)
                .queryParam("title", title)
                .when()
                .get(PATH);
    }

    public Response create(AnimeRequest payload) {
        return RestAssured.given()
                .spec(request)
                .body(payload)
                .when()
                .post(PATH);
    }

    public Response update(long id, AnimeRequest payload) {
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
