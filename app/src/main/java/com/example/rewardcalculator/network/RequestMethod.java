package com.example.rewardcalculator.network;

public enum RequestMethod {

    Get("GET"),
    Post("POST");

    private String method;

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    private RequestMethod(String method) {
        this.method = method;
    }
}
