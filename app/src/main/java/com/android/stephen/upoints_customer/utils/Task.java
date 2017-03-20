package com.android.stephen.upoints_customer.utils;

public enum Task {
    STORE_DETAILS("getstoredetails"),
    ITEMS("getstoreitems"),
    PRODUCTS("getstoreproduct"),
    PRODUCT_ITEM("getstoreproductitem"),
    STOCKS("getstorestock"),
    ITEM_TYPE("getitemtypelookup"),
    UPOINTS_HISTORY("getstoreupointshistory"),
    STORE_UPOINTS("getstoreupoints"),
    STOCKS_LIST("getlistofallstorestocks"),
    PRODUCT_LIST("getlistofallproducts"),
    PRODUCT_CATEGORY_LIST("getlistofallprocat"),
    STORE_STOCKS_LIST("getlistofallstorestocks"),
    SAVE_NEW_STOCK("postsavenewstock"),
    GENERATE_CUSTOMER_ID("generatecustomerid"),
    GET_UPLINE("getupline"),
    SAVE_NEW_CUSTOMER("savenewcustomer"),
    CUSTOMER_LOGIN_DETAILS("getcustomerlogindetails"),
    CUSTOMER_PASSWORD_CHANGE("changecustomerpassword"),
    CUSTOMER_PICTURE_CHANGE("changecustomerpicture"),
    CUSTOMER_PROFILE_CHANGE("changecustomerprofile"),
    CUSTOMER_RECEIVED_POINTS("getlistofcustomerreceivedpoints"),
    CUSTOMER_PICTURE_HISTORY("getcustomerpicturehistory");

    private String value;

    public String getValue() {
        return value;
    }

    Task(String value) {
        this.value = value;
    }
}
