package com.animecatalog.api;

import com.animecatalog.models.GenreRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * Author: cyqatester
 */

public class GenresApi {
    private static final String PATH = "/api/genres";
    private final RequestSpecification request;

    public GenresApi() {
        this(ApiClient.adminRequest());
    }

    public GenresApi(RequestSpecification request) {
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

    public Response create(String name) {
        return RestAssured.given()
                .spec(request)
                .body(new GenreRequest(name))
                .when()
                .post(PATH);
    }

    public Response update(long id, String name) {
        return RestAssured.given()
                .spec(request)
                .body(new GenreRequest(name))
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
