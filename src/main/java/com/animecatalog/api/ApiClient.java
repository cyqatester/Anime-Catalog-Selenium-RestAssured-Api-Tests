package com.animecatalog.api;

import com.animecatalog.config.Config;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

/**
 * Author: cyqatester
 */

public final class ApiClient {

    private ApiClient() {}

    public static RequestSpecification adminRequest() {
        return new RequestSpecBuilder()
                .setBaseUri(Config.apiBaseUrl())
                .setContentType("application/json")
                .setAccept("application/json")
                .addHeader("X-Username", Config.adminUsername())
                .build();
    }

    public static RequestSpecification requestAs(String username) {
        return new RequestSpecBuilder()
                .setBaseUri(Config.apiBaseUrl())
                .setContentType("application/json")
                .setAccept("application/json")
                .addHeader("X-Username", username)
                .build();
    }
}
