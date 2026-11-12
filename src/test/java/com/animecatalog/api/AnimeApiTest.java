package com.animecatalog.api;

import com.animecatalog.models.AnimeRequest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Author: cyqatester
 */

public class AnimeApiTest {
    private final AnimeApi animeApi = new AnimeApi();
    private final GenresApi genresApi = new GenresApi();

    @Test
    public void shouldReturnAnimeCollection() {
        Response response = animeApi.list();
        response.then().statusCode(200);
        assertThat(response.jsonPath().getList("$"), is(notNullValue()));
    }

    @Test
    public void shouldFilterAnimeByTitle() {
        Response response = animeApi.search("naruto");
        response.then().statusCode(200);
        assertThat(response.jsonPath().getList("$"), is(notNullValue()));
    }

    @Test
    public void shouldCreateUpdateAndDeleteAnime() {
        String suffix = Long.toString(System.nanoTime());
        Long genreId = null;
        Long animeId = null;
        try {
            Response genre = genresApi.create("Java Anime Genre " + suffix);
            genre.then().statusCode(200);
            genreId = genre.jsonPath().getLong("id");

            AnimeRequest payload = new AnimeRequest(
                    "Java Anime " + suffix, "Automation Anime " + suffix,
                    List.of(genreId), 2024, "TV_SERIES", 12, "SPRING");

            Response created = animeApi.create(payload);
            created.then().statusCode(200);
            animeId = created.jsonPath().getLong("id");
            assertThat(created.jsonPath().getString("title"), is(payload.getTitle()));
            assertThat(created.jsonPath().getString("alternateTitle"), is(payload.getAlternateTitle()));
            assertThat(created.jsonPath().getInt("yearAired"), is(2024));
            assertThat(created.jsonPath().getString("releaseCategory"), is("TV_SERIES"));

            AnimeRequest updatedPayload = new AnimeRequest(
                    "Java Anime Updated " + suffix, payload.getAlternateTitle(),
                    payload.getGenreIds(), payload.getYearAired(),
                    payload.getReleaseCategory(), payload.getEpisodes(), payload.getSeason());
            Response updated = animeApi.update(animeId, updatedPayload);
            updated.then().statusCode(200);
            assertThat(updated.jsonPath().getString("title"), is(updatedPayload.getTitle()));

            animeApi.delete(animeId).then().statusCode(anyOf(is(200), is(204)));
            animeId = null;
        } finally {
            if (animeId != null) animeApi.delete(animeId);
            if (genreId != null) genresApi.delete(genreId);
        }
    }
}
