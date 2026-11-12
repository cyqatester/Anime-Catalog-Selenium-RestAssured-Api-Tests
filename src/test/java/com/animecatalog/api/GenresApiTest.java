package com.animecatalog.api;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Author: cyqatester
 */

public class GenresApiTest {
    private final GenresApi genresApi = new GenresApi();

    @Test
    public void shouldListGenres() {
        Response response = genresApi.list();
        response.then().statusCode(200);
        assertThat(response.jsonPath().getList("$"), is(notNullValue()));
    }

    @Test
    public void shouldSearchCreateUpdateAndDeleteGenre() {
        String suffix = Long.toString(System.nanoTime());
        String original = "Java Genre " + suffix;
        String updated = "Java Genre Updated " + suffix;
        Long id = null;
        try {
            Response created = genresApi.create(original);
            created.then().statusCode(200);
            id = created.jsonPath().getLong("id");
            assertThat(created.jsonPath().getString("name"), is(original));

            Response searched = genresApi.search(original);
            searched.then().statusCode(200);
            assertThat(searched.jsonPath().getList("id", Long.class), hasItem(id));

            Response changed = genresApi.update(id, updated);
            changed.then().statusCode(200);
            assertThat(changed.jsonPath().getString("name"), is(updated));

            genresApi.delete(id).then().statusCode(anyOf(is(200), is(204)));
            id = null;
        } finally {
            if (id != null) genresApi.delete(id);
        }
    }

    @Test
    public void shouldRejectUnknownActorCreatingGenre() {
        Response response = new GenresApi(ApiClient.requestAs("unknown-" + System.nanoTime()))
                .create("Must Not Exist " + System.nanoTime());
        response.then().statusCode(401);
    }
}
