package com.android.stephen.upoints_customer.utils;

public enum API {
    STORE("store"),
    ITEM("item"),
    PROCAT("procat"),
    CUSTOMER("customer");

    private String api;

    API(String api) {
        this.api = api;
    }

    public String getApi() {
        return api;
    }
}
