package com.phonesj.news.model.http.response;

/**
 * Created by Phone on 2017/7/29.
 */

public class GoldHttpResponse<T> {

    private T results;

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
