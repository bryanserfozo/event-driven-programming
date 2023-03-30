package com.revature.models;

public class Response {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    private int httpCode;

    public Response() {
    }

    public Response(String message, int httpCode) {
        this.message = message;
        this.httpCode = httpCode;
    }

    @Override
    public String toString() {
        return "Response{" +
                "message='" + message + '\'' +
                ", httpCode='" + httpCode + '\'' +
                '}';
    }
}
