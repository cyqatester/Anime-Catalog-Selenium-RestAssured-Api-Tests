package com.animecatalog.models;

import java.util.List;

/**
 * Author: cyqatester
 */

public class AnimeRequest {
    private String title;
    private String alternateTitle;
    private List<Long> genreIds;
    private int yearAired;
    private String releaseCategory;
    private Integer episodes;
    private String season;

    public AnimeRequest() {
    }

    public AnimeRequest(String title, String alternateTitle, List<Long> genreIds,
            int yearAired, String releaseCategory, Integer episodes, String season) {
        this.title = title;
        this.alternateTitle = alternateTitle;
        this.genreIds = genreIds;
        this.yearAired = yearAired;
        this.releaseCategory = releaseCategory;
        this.episodes = episodes;
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlternateTitle() {
        return alternateTitle;
    }

    public void setAlternateTitle(String alternateTitle) {
        this.alternateTitle = alternateTitle;
    }

    public List<Long> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Long> genreIds) {
        this.genreIds = genreIds;
    }

    public int getYearAired() {
        return yearAired;
    }

    public void setYearAired(int yearAired) {
        this.yearAired = yearAired;
    }

    public String getReleaseCategory() {
        return releaseCategory;
    }

    public void setReleaseCategory(String releaseCategory) {
        this.releaseCategory = releaseCategory;
    }

    public Integer getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Integer episodes) {
        this.episodes = episodes;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }
}
