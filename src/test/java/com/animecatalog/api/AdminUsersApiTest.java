package com.animecatalog.api;

import com.animecatalog.config.Config;
import com.animecatalog.models.UserRequest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Author: cyqatester
 */

public class AdminUsersApiTest {
    private final AdminUsersApi usersApi = new AdminUsersApi();

    @Test
    public void shouldListManagedUsersIncludingConfiguredAdmin() {
        Response response = usersApi.list();
        response.then().statusCode(200);
        assertThat(response.jsonPath().getList("username", String.class),
                hasItem(Config.adminUsername()));
    }

    @Test
    public void shouldCreateSearchUpdateAndDeleteStandardUser() {
        String suffix = Long.toString(System.nanoTime());
        String username = "java-user-" + suffix;
        Long userId = null;
        try {
            UserRequest payload = new UserRequest(
                    "Java Automation User " + suffix, username,
                    "Temporary-Password-123!", "STANDARD");
            Response created = usersApi.create(payload);
            created.then().statusCode(200);
            userId = created.jsonPath().getLong("id");
            assertThat(created.jsonPath().getString("username"), is(username));
            assertThat(created.jsonPath().getString("role"), is("STANDARD"));

            Response searched = usersApi.search(username);
            searched.then().statusCode(200);
            assertThat(searched.jsonPath().getList("id", Long.class), hasItem(userId));

            UserRequest updatedPayload = new UserRequest(
                    "Updated " + payload.getName(), username,
                    payload.getPassword(), payload.getRole());
            Response updated = usersApi.update(userId, updatedPayload);
            updated.then().statusCode(200);
            assertThat(updated.jsonPath().getString("name"), is(updatedPayload.getName()));

            usersApi.delete(userId).then().statusCode(anyOf(is(200), is(204)));
            userId = null;
        } finally {
            if (userId != null)
                usersApi.delete(userId);
        }
    }

    @Test
    public void shouldNotDeleteProtectedSuperAdmin() {
        Response list = usersApi.search(Config.adminUsername());
        list.then().statusCode(200);
        Long protectedId = null;
        for (int i = 0; i < list.jsonPath().getList("id", Long.class).size(); i++) {
            if (Config.adminUsername().equals(list.jsonPath().getList("username", String.class).get(i))
                    && Boolean.TRUE.equals(list.jsonPath().getList("superAdmin", Boolean.class).get(i))) {
                protectedId = list.jsonPath().getList("id", Long.class).get(i);
                break;
            }
        }
        if (protectedId == null) {
            throw new org.testng.SkipException("Configured administrator is not marked as protected superadmin.");
        }
        usersApi.delete(protectedId).then().statusCode(403);
    }
}
