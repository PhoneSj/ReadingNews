package com.phonesj.news.model.http.execption;

/**
 * Created by Phone on 2017/7/26.
 */

public class ApiException extends Exception {

    private int code;

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
