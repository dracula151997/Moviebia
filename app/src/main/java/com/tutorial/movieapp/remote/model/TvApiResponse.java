package com.tutorial.movieapp.remote.model;



import com.google.gson.annotations.SerializedName;
import com.tutorial.movieapp.local.entity.TvEntity;

import java.util.ArrayList;
import java.util.List;

public class TvApiResponse {

    public TvApiResponse() {
        this.results = new ArrayList<>();
    }

    private long page;

    @SerializedName("total_pages")
    private long totalPages;

    @SerializedName("total_results")
    private long totalResults;

    private List<TvEntity> results;

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    public List<TvEntity> getResults() {
        return results;
    }

    public void setResults(List<TvEntity> results) {
        this.results = results;
    }

    @Override
    public String toString()
    {
        return "TvApiResponse{" +
                "page=" + page +
                ", totalPages=" + totalPages +
                ", totalResults=" + totalResults +
                ", results=" + results +
                '}';
    }
}
