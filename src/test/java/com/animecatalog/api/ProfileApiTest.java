package com.animecatalog.api;

import com.animecatalog.config.Config;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Author: cyqatester
 */

public class ProfileApiTest {
    private final ProfileApi profileApi = new ProfileApi();

    @Test
    public void shouldReturnAuthenticatedAdministratorProfile() {
        Response response = profileApi.getProfile();
        response.then().statusCode(200);
        assertThat(response.jsonPath().getString("username"), is(Config.adminUsername()));
        assertThat(response.jsonPath().getString("role"), is("ADMIN"));
        assertThat(response.jsonPath().getString("name"), not(blankOrNullString()));
    }

    @Test
    public void shouldRejectUnknownUsername() {
        Response response = new ProfileApi(ApiClient.requestAs("unknown-" + System.nanoTime())).getProfile();
        response.then().statusCode(401);
    }
}
